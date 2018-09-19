package com.sanbang.goods.service.imp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanbang.bean.ezs_goods;
import com.sanbang.dao.ezs_goodsMapper;
import com.sanbang.goods.service.GoodTaskService;
import com.sanbang.utils.Result;
import com.sanbang.utils.StockHelper;
import com.sanbang.vo.DictionaryCode;

@Service("goodTaskService")
public class GoodTaskServiceImpl implements GoodTaskService {
	private static Logger log = Logger.getLogger(GoodTaskServiceImpl.class);

	// 默认除法运算精度
	private static final Integer DEF_DIV_SCALE = 2;
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Value("${common.sendMessage.zhizhen}")
	private String msgurl;
	private static String phone = "18500409847";
	private static String phone1 = "18310980587";

	@Autowired
	private ezs_goodsMapper ezs_goodsMapper;

	@Override
	public void syncGoodsInventory() {

		synchronized (this) {
			log.info("同步库存定时任务开始执行，时间为" + sdf.format(new Date()).toString());
			// 待处理商品编码
			List<Map<String, Object>> selfNumbers = new ArrayList<>();
			Map<String, Object> localhistory = new HashMap<>();

			// 自营数据处理
			List<Map<String, Object>> selfGoodslist = ezs_goodsMapper.getSelfGoodsList();
			StringBuffer sb = new StringBuffer();

			for (Map<String, Object> map : selfGoodslist) {
				String good_no = String.valueOf(map.get("good_no"));
				String inventory = String.valueOf(map.get("inventory"));
				localhistory.put(good_no, inventory);
				sb.append("'" + good_no + "',");
			}

			// u8自营商品数据
			String goodsNums = sb.toString().substring(0, sb.toString().lastIndexOf(","));
			List<Map<String, Object>> u8selflist = StockHelper.getSelfListForSqlServer(goodsNums);

			// 待处理数据清洗
			for (Map<String, Object> map : u8selflist) {
				String good_no = String.valueOf(map.get("cInvAddCode"));
				Double inventory = round(divide(Double.valueOf(String.valueOf(map.get("iQuantity"))), (double) 1000), DEF_DIV_SCALE);;
				if (!localhistory.containsKey(good_no)) {
					continue;
				}
				Double loinventory = round(Double.valueOf(String.valueOf(localhistory.get(good_no))), DEF_DIV_SCALE);;
				if (localhistory.containsKey(good_no)) {
					if (sub(inventory, loinventory) != 0) {
						Map<String, Object> chace = new HashMap<>();
						chace.put("inventory", inventory);
						chace.put("loinventory", loinventory);
						chace.put("good_no", good_no);
						chace.put("res", false);
						selfNumbers.add(chace);
						log.info("商品" + good_no + "本地库存:" + loinventory + ",u8库存:" + inventory + "比价结果" + false
								+ "需要处理");
					} else {
						log.info("商品" + good_no + "本地库存:" + loinventory + ",u8库存:" + inventory + "比价结果" + true
								+ "不需要处理");
					}
				}
			}

			StringBuffer failernums = new StringBuffer();
			int num = 0;
			for (Map<String, Object> map : selfNumbers) {
				Double inventory = Double.valueOf(String.valueOf(map.get("inventory")));
				Double loinventory = Double.valueOf(String.valueOf(map.get("loinventory")));
				String good_no = String.valueOf(map.get("good_no"));
				Result result = Result.failure();
				result = updateGoodsInventory(result, good_no);
				if (!result.getSuccess()) {
					failernums.append(good_no).append(",");
					num++;
				}
			}
			log.info("同步库存定时任务结束，需处理" + selfNumbers.size() + "条,失败" + num + "条，失败商品编号为" + failernums.toString()
					+ "。结束时间时间为" + sdf.format(new Date()).toString());
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED, timeout = 100)
	public Result updateGoodsInventory(Result result, String good_no) {
		try {
			List<Map<String, Object>> u8selflist = StockHelper.getSelfListForSqlServer("'" + good_no + "'");
			if (u8selflist.size() == 0) {
				result.setSuccess(true);
				result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				result.setMsg("操作成功");
				return result;
			}
			Map<String, Object> u8goods = u8selflist.get(0);
			Map<String, Object> selfg = ezs_goodsMapper.getSelfGoodsListByGoodsNo(good_no);
			Double inventory = round(divide(Double.valueOf(String.valueOf(u8goods.get("iQuantity"))), (double) 1000), DEF_DIV_SCALE);
			Double loinventory = round(Double.valueOf(String.valueOf(selfg.get("inventory"))), DEF_DIV_SCALE);
			long id = Long.valueOf(String.valueOf(selfg.get("id")));
			if (sub(inventory, loinventory) != 0) {
				Double stocknum = Double.valueOf(String.valueOf(selfg.get("stocknum")));
				Double val = round(sub(inventory, stocknum), DEF_DIV_SCALE);
				if (val >= 0) {
					ezs_goods record = new ezs_goods();
					record.setId(id);
					record.setInventory(val);
					ezs_goodsMapper.updateByPrimaryKeySelective(record);
					result.setSuccess(true);
					result.setMsg("操作成功");
					result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				} else {
					ezs_goods record = new ezs_goods();
					record.setId(id);
					record.setInventory(Double.valueOf("0.00"));
					ezs_goodsMapper.updateByPrimaryKeySelective(record);
					
					String content = "商品编号为" + good_no + "超卖,原始库存为" + loinventory + ",u8库存为" + inventory + ",锁库库存为"
							+ stocknum + "计算库存为" + val + "时间为" + sdf.format(new Date()).toString();
//					 SendMobileMessage.sendMsg(phone, content,msgurl);
//					 SendMobileMessage.sendMsg(phone1, content,msgurl);
					log.info(content);
					result.setSuccess(false);
					result.setMsg("出现超卖");
					result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
					
				}
			}else {
				result.setSuccess(true);
				result.setMsg("操作成功");
				result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg("操作成功");
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
		}

		return result;
	}

	/**
	 * 提供精确的减法运算。
	 *
	 * @param value1 被减数
	 * @param value2 减数
	 * @return 两个参数的差
	 */
	public static double sub(Double value1, Double value2) {
		BigDecimal b1 = new BigDecimal(Double.toString(value1));
		BigDecimal b2 = new BigDecimal(Double.toString(value2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时， 精确到小数点以后10位，以后的数字四舍五入。
	 *
	 * @param dividend 被除数
	 * @param divisor  除数
	 * @return 两个参数的商
	 */
	public static Double divide(Double dividend, Double divisor) {
		return divide(dividend, divisor, DEF_DIV_SCALE);
	}

	/**
	 * 提供（相对）精确的除法运算。 当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入。
	 *
	 * @param dividend 被除数
	 * @param divisor  除数
	 * @param scale    表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static Double divide(Double dividend, Double divisor, Integer scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(dividend));
		BigDecimal b2 = new BigDecimal(Double.toString(divisor));
		return b1.divide(b2, scale, RoundingMode.HALF_UP).doubleValue();
	}
	
	
	public static double round(double d,int len) {     // 进行四舍五入操作
        BigDecimal b1 = new BigDecimal(d);
        BigDecimal b2 = new BigDecimal(1);
       // 任何一个数字除以1都是原数字
       // ROUND_HALF_UP是BigDecimal的一个常量，表示进行四舍五入的操作
       return b1.divide(b2, len,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

}
