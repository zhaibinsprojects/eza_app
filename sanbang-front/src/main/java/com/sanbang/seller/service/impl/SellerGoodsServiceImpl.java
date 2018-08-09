package com.sanbang.seller.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.bson.util.StringRangeSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanbang.bean.ezs_accessory;
import com.sanbang.bean.ezs_goods;
import com.sanbang.bean.ezs_goods_audit_process;
import com.sanbang.bean.ezs_goods_cartography;
import com.sanbang.bean.ezs_goods_log;
import com.sanbang.bean.ezs_goods_photo;
import com.sanbang.bean.ezs_user;
import com.sanbang.dao.ezs_accessoryMapper;
import com.sanbang.dao.ezs_dictMapper;
import com.sanbang.dao.ezs_goodsMapper;
import com.sanbang.dao.ezs_goods_audit_processMapper;
import com.sanbang.dao.ezs_goods_cartographyMapper;
import com.sanbang.dao.ezs_goods_logMapper;
import com.sanbang.dao.ezs_goods_photoMapper;
import com.sanbang.seller.service.SellerGoodsService;
import com.sanbang.utils.EvenOddCheck;
import com.sanbang.utils.FilePathUtil;
import com.sanbang.utils.GoodsLogUtil;
import com.sanbang.utils.Page;
import com.sanbang.utils.Result;
import com.sanbang.utils.StringUtil;
import com.sanbang.utils.Tools;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.GoodsListInfo;
import com.sanbang.vo.userauth.AuthImageVo;

@Service
public class SellerGoodsServiceImpl implements SellerGoodsService {

	private Logger log = Logger.getLogger(SellerGoodsServiceImpl.class);

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	ezs_goodsMapper goodsMapper;

	@Autowired
	ezs_accessoryMapper accessoryMapper;

	@Autowired
	ezs_goods_logMapper goodsLogMapper;

	@Autowired
	ezs_goods_audit_processMapper goodsAuditProcessMapper;
	
	@Autowired
	private ezs_accessoryMapper ezs_accessoryMapper;
	
	@Autowired
	private ezs_goods_logMapper ezs_goods_logMapper;
	
	@Autowired
	ezs_goods_photoMapper photoMapper;
	
	@Autowired
	ezs_goods_cartographyMapper cartographyMapper;
	
	@Autowired
	ezs_dictMapper dictMapper;
	
	@Override
	public Map<String, Object> queryGoodsListBySellerId(Long sellerId, int status, String currentPage) {
		Map<String, Object> mmp = new HashMap<>();
		if(currentPage==null){
			currentPage = "1";    
		}
		// 获取总页数
		int totalCount = goodsMapper.selectCount(sellerId,status);
		Page page = new Page(totalCount, Integer.valueOf(currentPage));
		page.setPageSize(10);
		if ((Integer.valueOf(currentPage)>=1&&Integer.valueOf(currentPage)<=page.getTotalPageCount())||(page.getTotalPageCount()==0)) {
			List<GoodsListInfo> list = goodsMapper.selectGoodsListBySellerId(sellerId, status, page);
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			mmp.put("Page", page);
			mmp.put("Obj", list);
		} else {
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Page", page);
		}
		return mmp;
	}

	@Override
	public ezs_goods queryGoodsInfoById(long id) {

		return goodsMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<ezs_accessory> queryPhotoById(Long goodsId) {

		return ezs_accessoryMapper.selectPhotoById(goodsId);
	}

	@Override
	public Result addGoodsInfo(Result result, ezs_user upi, HttpServletRequest request, HttpServletResponse response) {
		try {
			result = checkParam(request);
			//添加商品
			String goodsurls = request.getParameter("goodsurls");// 商品图片
			String processgoodsurls = request.getParameter("processgoodsurls");// 商品图片
			List<AuthImageVo> list = new ArrayList<>();
			savepic(goodsurls, list);
			savepic(processgoodsurls, list);
			long mainAccid = 0 ;
			if (null != list && list.size() > 0) {
				
				//检查保存商品图片是否必填
				if(!checkIsMustForGoodsAdd("goods", list)){
					result.setMsg("至少上传一张商品图片");
					result.setSuccess(false);
					result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
					return result;
				};
				
			}
			
			if (result.getSuccess()) {
				String goodClass_id = request.getParameter("goodClass_id");// 分类
				String name = request.getParameter("name");// 货品名称
				String price = request.getParameter("price");// 价格
				//String validity = request.getParameter("validity");// 有效期
				String cncl_num = request.getParameter("cncl_num");//样品库存数量
				String inventory = request.getParameter("inventory");//商品库存数量
				String area_id = request.getParameter("area_id");// 库存地区（县市）
				String addess = request.getParameter("addess");// 库存地区详细地址
				String supply_id = request.getParameter("supply_id");// 供应情况（是否稳定供货）
				String color_id = request.getParameter("color_id");// 颜色
				String form_id = request.getParameter("form_id");// 形态
				String source = request.getParameter("source");// 来源
				String purpose = request.getParameter("purpose");// 用途
				String protection = request.getParameter("protection");// 是否环保
				String density = request.getParameter("density");// 密度
				String cantilever = request.getParameter("cantilever");// 悬臂梁缺口冲击
				String freely = request.getParameter("freely");// 简支梁缺口冲击
				String lipolysis = request.getParameter("lipolysis");// 溶指
				String ash = request.getParameter("ash");// 灰份
				String water = request.getParameter("water");// 水分
				String tensile = request.getParameter("tensile");// 拉伸强度
				String crack = request.getParameter("crack");// 断裂伸长率
				String bending = request.getParameter("bending");// 弯曲强度
				String flexural = request.getParameter("flexural");// 弯曲模量
				String burning = request.getParameter("burning");// 燃烧等级
				String seo_description = request.getParameter("seo_description");// 货品详细描述（武汉方面设计，这个字段没有使用）
				String content = request.getParameter("content");// 货品详细描述（PC端含有富文本编辑器，手机端只接收纯文字使用，不考虑图片相关信息）
					
				ezs_goods goods = new ezs_goods();
				goods.setAddTime(new Date());
				goods.setGoodClass_id(Long.valueOf(goodClass_id));
				goods.setName(name);
				goods.setPrice(new BigDecimal(price));
				if (null == cncl_num || cncl_num.equals("")) {
					goods.setCncl_num((double)0);
				}else{
					goods.setCncl_num(Double.valueOf(cncl_num));
				}
				goods.setValidity(100);
				goods.setInventory(Double.valueOf(inventory));
				goods.setArea_id(Long.valueOf(area_id));
				goods.setAddess(addess);
				goods.setPurpose(purpose);
				goods.setUtil_id((long) 23);
				goods.setSupply_id(Long.valueOf(supply_id));
				goods.setSource(source);
				goods.setClick(0);
				goods.setCollect(0);
				goods.setGoods_salenum(0);
				goods.setStatus(0);
				goods.setLockStatus(false);
				goods.setMemberLook(false);
				// 添加商品编码
				log.info("sss===>"+goodClass_id);
		        String goods_no = "GE" + classid2str(Long.valueOf(goodClass_id));
		        log.info("sss===>"+goods_no);
		       
		        if(StringUtil.isEmpty(protection)||StringUtil.isBlank(protection)){
		        	protection="false";
		        }
				if (Boolean.valueOf(protection)) {
					goods.setProtection(true);
				} else {
					goods.setProtection(false);
				} 
				goods.setDensity(density);
				goods.setCantilever(cantilever);
				goods.setFreely(freely);
				goods.setLipolysis(lipolysis);
				goods.setAsh(ash);
				goods.setWater(water);
				goods.setTensile(tensile);
				goods.setCrack(crack);
				goods.setBending(bending);
				goods.setFlexural(flexural);
				goods.setBurning(burning);
				goods.setSeo_description(seo_description);
				goods.setRecommend(false);
				goods.setGood_self(false);
				goods.setColor_id(Long.valueOf(color_id));
				goods.setForm_id(Long.valueOf(form_id));
				goods.setContent(content);
				goods.setPurpose(purpose);
				goods.setUser_id(upi.getId());
				goods.setDeleteStatus(false);
				goods.setSaleprice(new BigDecimal(0));
				try {
					goodsMapper.insert(goods);
					result.setSuccess(true);
					result.setMsg("添加货品成功");
				} catch (Exception e) {
					e.printStackTrace();
					result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
					result.setSuccess(false);
					result.setMsg("参数错误");
				}

				 Long goodid = goods.getId();
		        goods_no += goodsid2str(goodid);
		        // 奇校验
		        goods_no = new EvenOddCheck().toOdd(goods_no);
		        ezs_goods g=new ezs_goods();
		        g.setGood_no(goods_no);
				g.setId(goods.getId());
				goodsMapper.updateByPrimaryKeySelective(g);
				
				// 图片信息
				ezs_goods_photo goodsPhoto = null ;
				ezs_goods_cartography  cartography = null;
				
				for (AuthImageVo img : list) {
					ezs_accessory ezs_accessory = new ezs_accessory();
					ezs_accessory.setAddTime(new Date());
					ezs_accessory.setDeleteStatus(false);
					ezs_accessory.setExt("");
					ezs_accessory.setHeight(0);
					ezs_accessory.setInfo(null);
					ezs_accessory.setName(FilePathUtil.getimageName(img.getImgurl()));
					ezs_accessory.setPath(FilePathUtil.getmiddelPath(img.getImgurl()));
					ezs_accessory.setSize((float) 100);
					ezs_accessory.setWidth(100);
					ezs_accessory.setUser_id(upi.getId());
					ezs_accessoryMapper.insertSelective(ezs_accessory);
					// upi记录
					img.setAccid(ezs_accessory.getId());
					//aaa
					if("goods".equals(img.getImgcode())){
						goodsPhoto=new ezs_goods_photo();
						goodsPhoto.setGoods_id(goods.getId());
						goodsPhoto.setPhoto_id(img.getAccid());
						photoMapper.insertSelective(goodsPhoto);
					}else if("process".equals(img.getImgcode())){
						cartography=new ezs_goods_cartography();
						cartography.setGoods_id(goods.getId());
						cartography.setCartography_id(img.getAccid());
						cartographyMapper.insertSelective(cartography);
					}
				}
				mainAccid = list.get(0).getAccid();
				goods.setGoods_main_photo_id(mainAccid);
				goodsMapper.updateByPrimaryKeySelective(goods);
				result.setObj(goods);
			}
		} catch (Exception e) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setSuccess(false);
			result.setMsg("系统错误");
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 检查保存商品图片是否必填
	 * @param type
	 * @param list
	 * @return
	 */
	private boolean checkIsMustForGoodsAdd(String type, List<AuthImageVo> list) {
		Boolean status=false;
		for (AuthImageVo authImageVo : list) {
			if(type.equals(authImageVo.getImgcode())){
				 status=true;
				 break;
			}
		}
		return status;
	}

	private static String classid2str(Long classid) {
        if (classid < 10) {
            return "0" + classid;
        } else {
            return "" + classid;
        }
    }

    private static String goodsid2str(Long goodsid) {
        if (goodsid < 10) {
            return "0000" + goodsid;
        } else if (goodsid < 100) {
            return "000" + goodsid;
        } else if (goodsid < 1000) {
            return "00" + goodsid;
        } else if (goodsid < 10000) {
            return "0" + goodsid;
        } else {
            return "" + goodsid;
        }
    }
	
	

	private static List<AuthImageVo> savepic(String param, List<AuthImageVo> list) throws ParseException {
		if (!Tools.isEmpty(param)) {
			String[] aa = param.split(";");
			if (null == aa || aa.length == 0) {
				return list;
			}
			for (String bb : aa) {
				String[] cc = bb.split(",");
				if (null == cc || cc.length < 2) {
					return list;
				}
				AuthImageVo ImageVo = new AuthImageVo();
				ImageVo.setImgcode(cc[0]);

				if (cc[1].indexOf("@") > 0 && cc[1].split("@").length == 3) {
					ImageVo.setImgurl(cc[1].split("@")[0]);
					ImageVo.setName(cc[1].split("@")[1]);
					ImageVo.setUsetime(sdf.parse(cc[1].split("@")[2]));
					list.add(ImageVo);
					System.out.println(ImageVo.toString());
				} else {
					ImageVo.setImgurl(cc[1].split("@")[0]);
					list.add(ImageVo);
					System.out.println(ImageVo.toString());
				}
			}
		}
		return list;
	}


	Result checkParam(HttpServletRequest request) {
		Result result = Result.success();
		String goodClass_id = request.getParameter("goodClass_id");// 分类
		String name = request.getParameter("name");// 货品名称
		String price = request.getParameter("price");// 价格
		String validity = request.getParameter("validity");// 有效期
		String inventory = request.getParameter("inventory");// 库存
		String area_id = request.getParameter("area_id");// 库存地区（县市）
		String addess = request.getParameter("addess");// 库存地区详细地址
		String supply_id = request.getParameter("supply_id");// 供应情况（是否稳定供货）
		String color_id = request.getParameter("color_id");// 颜色
		String form_id = request.getParameter("form_id");// 形态
		String source = request.getParameter("source");// 来源
		String purpose = request.getParameter("purpose");// 用途
		String protection = request.getParameter("protection");// 是否环保
		// 非必填
		/*String density = request.getParameter("density");// 密度
		String cantilever = request.getParameter("cantilever");// 悬臂梁缺口冲击
		String freely = request.getParameter("freely");// 简支梁缺口冲击
		String lipolysis = request.getParameter("lipolysis");// 溶指
		String ash = request.getParameter("ash");// 灰份
		String water = request.getParameter("water");// 水分
		String tensile = request.getParameter("tensile");// 拉伸强度
		String crack = request.getParameter("crack");// 断裂伸长率
		String bending = request.getParameter("bending");// 弯曲强度
		String flexural = request.getParameter("flexural");// 弯曲模量
		String burning = request.getParameter("burning");// 燃烧等级
*/
		if (Tools.isEmpty(goodClass_id)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请选择货品类别");
			return result;
		}
		if (Tools.isEmpty(name)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入货品名称");
			return result;
		}
		if (Tools.isEmpty(price)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入货品价格");
			if (!Tools.isNum(price)) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("请输入有效货品价格");
				return result;
			}
			return result;
		}
		/*if (Tools.isEmpty(validity)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入货品有效期");
			return result;
		}*/
		if (Tools.isEmpty(inventory)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入货品库存");
			return result;
		}
		if (Tools.isEmpty(area_id)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入货品库存地区");
			return result;
		}
		if (Tools.isEmpty(addess)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入货品库存详细地址");
			return result;
		}
		if (Tools.isEmpty(supply_id)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请选择货品供应情况");
			return result;
		}
		if (Tools.isEmpty(color_id)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请选择货品颜色");
			return result;
		}
		if (Tools.isEmpty(form_id)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请选择货品形态");
			return result;
		}
		if (Tools.isEmpty(source)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入货品来源");
			return result;
		}
		if (Tools.isEmpty(purpose)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入货品用途");
			return result;
		}
		/*if (Tools.isEmpty(protection)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请选择货品是否环保");
			return result;
		}*/
		result.setSuccess(true);
		return result;
	}

	@Override
	public Result pullOffShelvesById(Result result, long goodsId) {

		int aa = 0;

		try {
			ezs_goods goods = goodsMapper.selectByPrimaryKey(goodsId);
			if(null==goods){
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("未找到该商品记录");
				return result;
			}
			aa = goodsMapper.pullOffShelves(goodsId);
			if (aa <= 0) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("下架操作异常");
			} else {
				ezs_goods_log log=GoodsLogUtil.goodsLog(goods.getId(), goods.getUser_id(), "用户操作:添加商品"+goods.getName()+"成功");
				ezs_goods_logMapper.insertSelective(log);
				result.setSuccess(true);
				result.setMsg("下架成功");
			}
		} catch (Exception e) {
			log.info("商品下架操作出错" + e.toString());
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setSuccess(false);
			result.setMsg("系统错误！");
		}
		return result;
	}

	@Override
	public Result updateGoodsInfoById(Result result, long goodsId,ezs_user upi, HttpServletRequest request,
			HttpServletResponse response) {
		ezs_goods_audit_process goodsAudit = goodsAuditProcessMapper.selectByGoodsId(goodsId); 
		Integer status = goodsAudit.getStatus();
		long mainAccid = 0 ;
		if ( status != 544 &&status != 547) {
			result.setSuccess(false);
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setMsg("该商品正在等在审核或已经通过审核， 不能修改属性");
			return result;
		}
		
		try {
			result = checkParam(request);
			//添加商品
			String goodsurls = request.getParameter("goodsurls");// 商品图片
			String processgoodsurls = request.getParameter("processgoodsurls");// 商品图片
			List<AuthImageVo> list = new ArrayList<>();
			savepic(goodsurls, list);
			savepic(processgoodsurls, list);
			
			if (null != list && list.size() > 0) {
				
				//检查保存商品图片是否必填
				if(!checkIsMustForGoodsAdd("goods", list)){
					result.setMsg("至少上传一张商品图片");
					result.setSuccess(false);
					result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
					return result;
				};
				mainAccid = list.get(0).getAccid();
			}
			
			if (result.getSuccess()) {
				String goodClass_id = request.getParameter("goodClass_id");// 分类
				String name = request.getParameter("name");// 货品名称
				String price = request.getParameter("price");// 价格
				String validity = request.getParameter("validity");// 有效期
				String cncl_num = request.getParameter("cncl_num");//样品库存数量
				String inventory = request.getParameter("inventory");// 货品库存量
				String area_id = request.getParameter("area_id");// 库存地区（县市）
				String addess = request.getParameter("addess");// 库存地区详细地址
				String supply_id = request.getParameter("supply_id");// 供应情况（是否稳定供货）
				String color_id = request.getParameter("color_id");// 颜色
				String form_id = request.getParameter("form_id");// 形态
				String source = request.getParameter("source");// 来源
				String purpose = request.getParameter("purpose");// 用途
				String protection = request.getParameter("protection");// 是否环保
				String density = request.getParameter("density");// 密度
				String cantilever = request.getParameter("cantilever");// 悬臂梁缺口冲击
				String freely = request.getParameter("freely");// 简支梁缺口冲击
				String lipolysis = request.getParameter("lipolysis");// 溶指
				String ash = request.getParameter("ash");// 灰份
				String water = request.getParameter("water");// 水分
				String tensile = request.getParameter("tensile");// 拉伸强度
				String crack = request.getParameter("crack");// 断裂伸长率
				String bending = request.getParameter("bending");// 弯曲强度
				String flexural = request.getParameter("flexural");// 弯曲模量
				String burning = request.getParameter("burning");// 燃烧等级
				String seo_description = request.getParameter("seo_description");// 货品详细描述（武汉方面设计，这个字段没有使用）
				String content = request.getParameter("content");// 货品详细描述（PC端含有富文本编辑器，手机端只接收纯文字使用，不考虑图片相关信息）
				
				ezs_goods goods = goodsMapper.selectByPrimaryKey(goodsId);
				goods.setDeleteStatus(false);
				goods.setAddTime(new Date());
				goods.setGoodClass_id(Long.valueOf(goodClass_id));
				goods.setName(name);
				goods.setPrice(new BigDecimal(price));
				if (null == cncl_num || cncl_num.equals("")) {
					goods.setCncl_num((double)0);
				}else{
					goods.setCncl_num(Double.valueOf(cncl_num));
				}
				goods.setValidity(Integer.valueOf(validity));
				goods.setInventory(Double.valueOf(inventory));
				goods.setArea_id(Long.valueOf(area_id));
				goods.setAddess(addess);
				goods.setSupply_id(Long.valueOf(supply_id));
				goods.setSupply_id(Long.valueOf(color_id));
				goods.setSupply_id(Long.valueOf(form_id));
				goods.setSource(source);
				goods.setClick(0);
				goods.setCollect(0);
				goods.setGoods_salenum(0);
				goods.setStatus(0);
				if (Boolean.valueOf(protection)) {
					goods.setProtection(true);
				} else {
					goods.setProtection(false);
				}
				goods.setDensity(density);
				goods.setCantilever(cantilever);
				goods.setFreely(freely);
				goods.setLipolysis(lipolysis);
				goods.setAsh(ash);
				goods.setWater(water);
				goods.setTensile(tensile);
				goods.setCrack(crack);
				goods.setBending(bending);
				goods.setFlexural(flexural);
				goods.setBurning(burning);
				goods.setRecommend(false);
				goods.setGood_self(false);
				goods.setColor_id(Long.valueOf(color_id));
				goods.setForm_id(Long.valueOf(form_id));
				goods.setContent(content);
				goods.setSeo_description(seo_description);
				goods.setLastModifyDate(new Date());
				goods.setPurpose(purpose);
				goods.setUtil_id((long) 23);
				goods.setUser_id(upi.getId());
				goods.setStatus(0);
				int aa = goodsMapper.updateByPrimaryKeySelective(goods);
				
				if (aa > 0) {
					result.setSuccess(true);
					result.setMsg("修改货品成功");
					result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				} else {
					result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
					result.setSuccess(false);
					result.setMsg("参数错误");
				}
				// 图片信息
				ezs_goods_photo goodsPhoto = null ;
				ezs_goods_cartography  cartography = null;
				photoMapper.deleteByGoodsId(goodsId);
				cartographyMapper.deleteByGoodsId(goodsId);
				for (AuthImageVo img : list) {
					ezs_accessory ezs_accessory = new ezs_accessory();
					ezs_accessory.setAddTime(new Date());
					ezs_accessory.setDeleteStatus(false);
					ezs_accessory.setExt("");
					ezs_accessory.setHeight(0);
					ezs_accessory.setInfo(null);
					ezs_accessory.setName("");
					ezs_accessory.setName(FilePathUtil.getimageName(img.getImgurl()));
					ezs_accessory.setPath(FilePathUtil.getmiddelPath(img.getImgurl()));
					ezs_accessory.setSize((float) 100);
					ezs_accessory.setWidth(100);
					ezs_accessory.setUser_id(upi.getId());
					ezs_accessoryMapper.insertSelective(ezs_accessory);
					// upi记录
					img.setAccid(ezs_accessory.getId());
					//aaa
					if("goods".equals(img.getImgcode())){
						goodsPhoto=new ezs_goods_photo();
						goodsPhoto.setGoods_id(goods.getId());
						goodsPhoto.setPhoto_id(img.getAccid());
						photoMapper.insertSelective(goodsPhoto);
					}else if("process".equals(img.getImgcode())){
						cartography=new ezs_goods_cartography();
						cartography.setGoods_id(goods.getId());
						cartography.setCartography_id(img.getAccid());
						cartographyMapper.insertSelective(cartography);
					}
				}
				mainAccid = list.get(0).getAccid();
				goods.setGoods_main_photo_id(mainAccid);
				goodsMapper.updateByPrimaryKeySelective(goods);
			}
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(true);
			result.setMsg("修改货品成功，请静待审核");
		} catch (Exception e) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setSuccess(false);
			result.setMsg("系统错误");
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Result submitGoodsForAudit(Result result, long goodsId, HttpServletRequest request,
			HttpServletResponse response,ezs_goods_log log1,ezs_user upi) {
		
		
		//查询商品显示金额
		ezs_goods goods = goodsMapper.selectByPrimaryKeyIsGoodsPrice(goodsId);
		if ( null != goods ) {
			log.info("货品信息=="+goods.toString());
			ezs_goods_audit_process goodsAudit =  goodsAuditProcessMapper.selectByGoodsId(goodsId);
			if (null == goodsAudit) {
				//向ezs_goods_audit_process表中查入数据
				 goodsAudit = new ezs_goods_audit_process();
				goodsAudit.setAddTime(new Date());
				goodsAudit.setDeleteStatus(false);
				goodsAudit.setGoods_id(Long.valueOf(goods.getId()));
				goodsAudit.setPriceStatus(600);
				goodsAudit.setSalePrice(new BigDecimal("0"));
				goodsAudit.setStatus(540);
				goodsAudit.setSupplyPrice(goods.getPrice());
				goodsAudit.setPercent(0);
				goodsAuditProcessMapper.insertSelective(goodsAudit);
				try {
				} catch (Exception e) {
					e.printStackTrace();
					result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
					result.setSuccess(false);
					result.setMsg("系统错误");
				}
			}else{
				ezs_goods_audit_process goodsAudit1 = new ezs_goods_audit_process();
				goodsAudit1.setAddTime(new Date());
				goodsAudit1.setDeleteStatus(false);
				goodsAudit1.setGoods_id(Long.valueOf(goods.getId()));
				goodsAudit1.setSupplyPrice(goods.getPrice());
				//只在待审核状态或白名单客户  恢复初始化状态
				if(goodsAudit.getStatus()==544||isValidateCompany(upi)){
					goodsAudit1.setPriceStatus(600);
					goodsAudit1.setStatus(540);
					goodsAudit1.setId(goodsAudit.getId());
				}else{
					goodsAudit1.setPriceStatus(goodsAudit.getPriceStatus());
					goodsAudit1.setStatus(goodsAudit.getStatus());
					goodsAudit1.setId(goodsAudit.getId());
				}
				
				//未设置售价 不进行关联表操作
				if(null!=goodsAudit.getSalePrice()&&goodsAudit.getSalePrice().compareTo(BigDecimal.ZERO)==1){
					BigDecimal oldprice=goods.getPrice();
					BigDecimal SupplyPrice=goods.getPrice();
					int Percent=goodsAudit.getPercent();
					if(Percent>0){
						BigDecimal bb=new BigDecimal(Percent).divide(new BigDecimal("100"));
						BigDecimal Percent1=bb.add(new BigDecimal("1"));
						SupplyPrice=oldprice.multiply(Percent1);
					}
					goodsAudit1.setSalePrice(SupplyPrice);
					
					//更新商品售价表
					ezs_goods goods1 =new ezs_goods();
					goods1.setId(goods.getId());
					goods1.setSaleprice(SupplyPrice);
					goodsMapper.updateByPrimaryKeySelective(goods1);
				}
				
				goodsAuditProcessMapper.updateByPrimaryKeySelective(goodsAudit1);
				result.setSuccess(true);
				result.setMsg("操作完成");
				ezs_goods_logMapper.insertSelective(log1);
			}
		}else{
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("系统错误");
		}
		return result;
	}
	
	//判断是否为白名单客户
	@SuppressWarnings("unused")
	private  boolean isValidateCompany(ezs_user upi){
		if(null!=upi&&(null!=upi.getEzs_store())&&upi.getEzs_store().getName_status()==1){
			return true;
		}
		return false;
	}

	@Override
	public List<ezs_accessory> queryCartographyById(Long goodsId) {
		return cartographyMapper.selectCartographyById(goodsId);
	}

	@Override
	public String getGoodsProperty(Long propertyId) {
		
		return dictMapper.selectPropertyById(propertyId);
	}

	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
	public Result updateGoodsPriceAndNumById(Result result, long goodsId, Long userId,
			 HttpServletRequest request,HttpServletResponse response) {
		ezs_goods goods = goodsMapper.selectByPrimaryKey(goodsId);
		
		String inventory = request.getParameter("inventory");//样品库存数量
		String price = request.getParameter("price");// 价格
		if (Tools.isEmpty(inventory)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入货品库存");
			return result;
		}
		if (Tools.isEmpty(price)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入货品价格");
			if (!Tools.isNum(price)) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("请输入有效货品价格");
			}
			return result;
		}
		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		result.setSuccess(true);
		result.setMsg("操作完成");
		goods.setLastModifyDate(new Date());
		goods.setInventory(Double.valueOf(inventory));
		goods.setPrice(new BigDecimal(price));
		goodsMapper.updateByPrimaryKeySelective(goods);
		
		return result;
	}


	@Override
	public Result pullNoShelvesById(Result result, long goodsId) {

		int aa = 0;

		try {
			ezs_goods goods = goodsMapper.selectByPrimaryKey(goodsId);
			if(null==goods){
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("未找到该商品记录");
				return result;
			}
			aa = goodsMapper.pullNoShelves(goodsId);
			if (aa <= 0) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("上架操作异常");
			} else {
				result.setSuccess(true);
				result.setMsg("上架成功");
				ezs_goods_log log=GoodsLogUtil.goodsLog(goods.getId(), goods.getUser_id(), "用户操作:商品下架"+goods.getName()+"成功");
				ezs_goods_logMapper.insertSelective(log);
			}
		} catch (Exception e) {
			log.info("商品上架操作出错" + e.toString());
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setSuccess(false);
			result.setMsg("系统错误！");
		}
		return result;
	}
	
	
	public static void main(String[] args) {
		BigDecimal oldprice=new BigDecimal("120");
		BigDecimal SupplyPrice=new BigDecimal("120");
		int Percent=10;
		if(Percent>0){
			BigDecimal bb=new BigDecimal(Percent).divide(new BigDecimal("100"));
			BigDecimal Percent1=bb.add(new BigDecimal("1"));
			SupplyPrice=oldprice.multiply(Percent1);
		}
		System.out.println(SupplyPrice);
	}
}
