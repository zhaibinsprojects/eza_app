package com.sanbang.system;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;


@Service("systemMessageService")
public class SystemMessageService {
	
	private static Logger log = Logger.getLogger(SystemMessageService.class.getName());
	
	@Resource(name="ezsuserMapper")
	private com.sanbang.dao.ezs_userMapper ezsuserMapper;
	
	public void countOrderInfo(){

		log.info("时间:"+new SimpleDateFormat("HH:mm:ss").format(new Date())+",定时任务触发");
		
		Map<String,Object> param = new HashMap<>();
		
		String starTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		starTime += " 00:00:00";
		String endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		
		param.put("starTime", starTime);
		param.put("endTime", endTime);

		/*// 查询公司交易额
		List<SystemMessageInfo> companyTrading = systemMessageDao.querycompanyTradingInfo(param);
		
		// 查询范围内的交易额,交易笔数
		SystemMessageInfo allTrading = systemMessageDao.queryallTradingInfo(param);
		
		// 查询最大单信息
		List<SystemMessageInfo> maxOrder = systemMessageDao.querymaxOrderInfo(param);
		
		//System.out.println(starTime+","+endTime);
*/	}
	public static void main(String[] args) {
		new SystemMessageService().countOrderInfo();
	}
}
