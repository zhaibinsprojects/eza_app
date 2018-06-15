package com.sanbang.userpro.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanbang.bean.destoon_area;
import com.sanbang.bean.destoon_member;
import com.sanbang.bean.ezs_accessory;
import com.sanbang.bean.ezs_area;
import com.sanbang.bean.ezs_bill;
import com.sanbang.bean.ezs_card_dict;
import com.sanbang.bean.ezs_companyType_dict;
import com.sanbang.bean.ezs_dict;
import com.sanbang.bean.ezs_industry_dict;
import com.sanbang.bean.ezs_paper;
import com.sanbang.bean.ezs_store;
import com.sanbang.bean.ezs_user;
import com.sanbang.bean.ezs_user_role;
import com.sanbang.bean.ezs_userinfo;
import com.sanbang.dao.destoon_areaMapper;
import com.sanbang.dao.destoon_memberMapper;
import com.sanbang.dao.ezs_accessoryMapper;
import com.sanbang.dao.ezs_card_dictMapper;
import com.sanbang.dao.ezs_industry_dictMapper;
import com.sanbang.dao.ezs_paperMapper;
import com.sanbang.dao.ezs_positionMapper;
import com.sanbang.dao.ezs_storeMapper;
import com.sanbang.dao.ezs_userMapper;
import com.sanbang.dao.ezs_user_roleMapper;
import com.sanbang.dict.service.DictService;
import com.sanbang.userpro.service.TestService;
import com.sanbang.utils.FilePathUtil;
import com.sanbang.utils.Result;
import com.sanbang.utils.Tools;
import com.sanbang.vo.DictionaryCate;
import com.sanbang.vo.userauth.AuthImageVo;

@Service("test")
public class TestServiceImpl  implements TestService {

	//日志
		private static Logger log = Logger.getLogger(TestServiceImpl.class.getName());
		
		@Autowired
		private DictService dictService;
		
		@Autowired
		private destoon_memberMapper  destoon_memberMapper;

		@Resource(name="ezs_userinfoMapper")
		private com.sanbang.dao.ezs_userinfoMapper ezs_userinfoMapper;
		
		//用户登陆信息
		@Resource(name="ezs_userMapper")
		private ezs_userMapper ezs_userMapper;
		
		//商铺
		@Resource(name="ezs_storeMapper")
		private  ezs_storeMapper ezs_storeMapper;
		
		//关于用户公司类型存储
		@Resource(name="ezs_companyType_dictMapper")
		private com.sanbang.dao.ezs_companyType_dictMapper  ezs_companyType_dictMapper;

		//关于用户公司主营行业存储
		@Resource(name="ezs_industry_dictMapper")
		private ezs_industry_dictMapper  ezs_industry_dictMapper;
		
		@Resource(name="ezs_positionMapper")
		private ezs_positionMapper ezs_positionMapper;
		
		@Resource(name="destoon_areaMapper")
		private destoon_areaMapper destoon_areaMapper;
		
		@Resource(name="ezs_areaMapper")
		private com.sanbang.dao.ezs_areaMapper ezs_areaMapper;
		
		@Resource(name="ezs_billMapper")
		private com.sanbang.dao.ezs_billMapper ezs_billMapper;
		
		
		@Autowired
		private ezs_accessoryMapper ezs_accessoryMapper;
		
		@Autowired
		private ezs_paperMapper ezs_paperMapper;
		
		@Autowired
		private ezs_card_dictMapper ezs_card_dictMapper;
		
		@Autowired
		private ezs_user_roleMapper ezs_user_roleMapper;
		
		private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
	@Override
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED,isolation=Isolation.SERIALIZABLE)
	public Result addUserInfo(long num) {
		
		int  num1=0;
		//destoon_member destoon_membr=destoon_memberMapper.selectByPrimaryKey((long) 1);
		
		List<destoon_member> list=destoon_memberMapper.selectdestoon_memberList(num);
		
		log.info("注册：导入条数" +list.size());
		
		for (destoon_member destoon_member : list) {
			List<ezs_user> upis=ezs_userMapper.getUserInfoByUserNameFromBack(destoon_member.getUsername());
			if(upis!=null&&upis.size()!=0){
				continue;
			}
			//用户登陆信息
			ezs_user upi=new ezs_user();
			ezs_bill ezs_bill=new com.sanbang.bean.ezs_bill();
			upi.setName(destoon_member.getUsername());
			upi.setPassword(destoon_member.getPassword());
			upi.setAddTime(new Date());
			upi.setLastLoginDate(new Date());
			upi.setLastLoginIp(destoon_member.getLoginip());
			upi.setLoginCount(1);
			upi.setLoginDate(new Date());
			upi.setLoginIp(destoon_member.getLoginip());
			upi.setUserRole("BUYER");
			upi.setDeleteStatus(false);
			
			// 商铺信息
			ezs_store story = new ezs_store();
			story.setAddress(destoon_member.getOpersperegion());
			story.setRegAddress(destoon_member.getOpersperegion());
			story.setAddTime(new Date());
			story.setUserType("BUYER");
			
//			String capitalPrice = request.getParameter("capitalPrice");// 注册资本
//			String unifyCode = request.getParameter("unifyCode");// 社会信用代码
//			String persion = request.getParameter("persion");// 法人
//			String trueName = request.getParameter("trueName");// 联系人
			
//			String idCardNum = request.getParameter("idCardNum");// 经营者省份证号
//			String account = request.getParameter("account");// 注册号
//			String persion = request.getParameter("persion");// 经营者
			
			if(null!=destoon_member.getQiyeziben()&&!Tools.isEmpty(destoon_member.getQiyeziben())){
				story.setCapitalPrice(Double.valueOf(destoon_member.getQiyeziben()));
			}
			
		
			
			
			//企业
			String getijingyinguser = destoon_member.getGetijingyinguser();
			String getijingyingshen = destoon_member.getGetijingyingshen();
			//个体
			//getizhizhao` varchar(255) DEFAULT '' COMMENT '营业执照照片',
			//企业
			// `qiyezhizhao` varchar(255) DEFAULT '' COMMENT '企业营业执照地址',
			//  `gshenz` varchar(255) DEFAULT '' COMMENT '身份证正面照',
			// `gshenf` varchar(255) DEFAULT '' COMMENT '身份证反面照',
			
//			|身份证，正面：IDCARD_FONT|
//			|身份证，反面：IDCARD_BACK|
//			|开户许可证：ACCOUNT_OPENING_LICENSE|
//			
//			|其他资质：OTHER_QUALIFICATIONS|
//			
//		    |营业执照|BUSINESS_LICENSE| ```
			
			String zhizhao="";//,
			String shengfz="";//,

			//企业
			if (StringUtils.isBlank(getijingyinguser) && StringUtils.isBlank(getijingyingshen)) {
				if(!StringUtils.isBlank(destoon_member.getQiyezhizhao())){
					zhizhao="BUSINESS_LICENSE,"+destoon_member.getQiyezhizhao();
				}
				ezs_bill.setCompanyName(destoon_member.getCompany());
				story.setCompanyName(destoon_member.getQiyename());
				story.setPerson(destoon_member.getFarend());  
				upi.setTrueName(destoon_member.getQiyeuser());
				story.setIdCardNum(destoon_member.getFarencardid());
				story.setAccount(destoon_member.getQiyeziben());
				story.setUnifyCode(destoon_member.getQiyedaima());
				story.setAccountType(1);
			//个体
			}else{
				if(!StringUtils.isBlank(destoon_member.getGetizhizhao())){
					zhizhao="BUSINESS_LICENSE,"+destoon_member.getGetizhizhao();
				}
				ezs_bill.setCompanyName(destoon_member.getCompany());
				story.setCompanyName(destoon_member.getGetizihao());
				story.setPerson(destoon_member.getGetijingyinguser());  
				story.setIdCardNum(destoon_member.getGetijingyingshen());
				story.setAccount(destoon_member.getGetizhuhao());
				upi.setTrueName(destoon_member.getGetijingyinguser());
				story.setAccountType(2);
			}
			if(!StringUtils.isBlank(destoon_member.getGshenz())){
				shengfz="IDCARD_FONT,"+destoon_member.getGshenz()+";"+"IDCARD_BACK"+destoon_member.getGshenf();
			}
			
			//地址处理
			
			destoon_area destoon_area=	destoon_areaMapper.selectByPrimaryKey(destoon_member.getAreaid());
			if(null!=destoon_area){
				ezs_area  ezs_area=ezs_areaMapper.selectParentByareaname(destoon_area.getAreaname());
				if(null!=ezs_area){
					story.setArea_id(ezs_area.getId());
					story.setRegArea_id(ezs_area.getId());
				}else{
					List<ezs_area>  ezs_area1=ezs_areaMapper.getAreaParentList();
					story.setArea_id(ezs_area1.get(0).getId());
					story.setRegArea_id(ezs_area1.get(0).getId());
				}
			}else{
				List<ezs_area>  ezs_area=ezs_areaMapper.getAreaParentList();
				story.setArea_id(ezs_area.get(0).getId());
				story.setRegArea_id(ezs_area.get(0).getId());
			}
			
			story.setDeleteStatus(false);
			story.setAssets(0.0);
			story.setCovered(0.0);
			story.setDevice_num(0);
			story.setEmployee_num(0);
			story.setFixed_assets(0.0);
			story.setObtainYear(0);
			story.setRent(false);
			story.setStatus(0);
			story.setyTurnover(0.0);
			story.setAdmin_status(0);
			story.setAuditingusertype_id(dictService.getDictByParentId(DictionaryCate.CRM_USR_TYPE_CLUE).get(0).getId());
			story.setCreditScore(0);
			
			
			// 用户详情
			ezs_userinfo userinfo = new ezs_userinfo();
			userinfo.setAddTime(new Date());
			userinfo.setDeleteStatus(false);
			userinfo.setEmail(destoon_member.getEmail());
			userinfo.setPhone(destoon_member.getMobile());
			userinfo.setSex_id(dictService.getDictByParentId(DictionaryCate.EZS_SEX).get(0).getId());
			userinfo.setStatus(0);
			userinfo.setUpdateStatus(0);
		 
			//经营范围
			 List<ezs_dict> Industrysl=dictService.getDictByParentId(DictionaryCate.EZS_INDUSTRY);
			 List<ezs_dict> cmtypesl=dictService.getDictByParentId(DictionaryCate.EZS_COMPANYTYPE);
			
			 String[] Industrys=new String[1];
			 String[] cmtypes=new String[1];
			 
			 Industrys[0]= String.valueOf(Industrysl.get(0).getId());
			 cmtypes[0]= String.valueOf(cmtypesl.get(0).getId());
			
			int aa = 0;
			try {
				long storyid = ezs_storeMapper.insert(story);
				storyid=story.getId();
				//主营行业部分
				Industry(Industrys, storyid);;
				//经营类型部分
				companyType(cmtypes, storyid);
				
				long userinfoid = ezs_userinfoMapper.insert(userinfo);
				upi.setUserInfo_id(userinfo.getId());
				upi.setStore_id(storyid);
				upi.setUserRole("BUYER");
				upi.setTrueName(destoon_member.getTruename());
				upi.setStore_id(storyid);
				aa = ezs_userMapper.insert(upi);
				
				
				//发票信息
//				String companyName = request.getParameter("companyName");// 企业名称
//				String dutyNo = request.getParameter("dutyNo");// 税号
//				String number = request.getParameter("number");// 开户账号
//				String phone = request.getParameter("phone");// 电话号码
//				String address = request.getParameter("address");// 单位地址
//				String bank = request.getParameter("bank");// 开户行
				ezs_bill.setUser_id(upi.getId());
			
				ezs_bill.setDutyNo(destoon_member.getTaxreg());
				ezs_bill.setNumber(destoon_member.getReceiverCardNo());
				ezs_bill.setDeleteStatus(false);
				ezs_bill.setPhone(destoon_member.getIphone());
				ezs_bill.setAddress(destoon_member.getOpersperegion());
				ezs_bill.setBank(destoon_member.getBankName());
				ezs_bill.setUser_id(upi.getId());
				ezs_bill.setAddTime(new Date());
				ezs_billMapper.insertSelective(ezs_bill);
				
				ezs_user_role role=new ezs_user_role();
				role.setRole_id((long) 4);
				role.setUser_id(upi.getId());
				ezs_user_roleMapper.insert(role);
				//资质信息
				
				
				List<AuthImageVo> List=new ArrayList<>();
				List=savepic(zhizhao, List);
				List=savepic(shengfz, List);	
					List=savepic(zhizhao, List);
					List=savepic(shengfz, List);
					if(null!=List&&List.size()>0){
					//资质信息
					for (AuthImageVo img : List) {
						ezs_accessory ezs_accessory=new com.sanbang.bean.ezs_accessory();
						if(null!=upi.getAuthimg()&&upi.getAuthimg().size()>0
								&&checkisExt(img.getImgcode(), upi.getAuthimg())){
							ezs_accessory.setPath(img.getImgurl());
							ezs_accessory.setId(checkisExtId(img.getImgcode(), upi.getAuthimg()));
							ezs_accessoryMapper.updateByPrimaryKeySelective(ezs_accessory);
						}else{
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
							
							//upi记录
							img.setAccid(ezs_accessory.getId());
							
							ezs_paper paper=new ezs_paper();
							paper.setAddTime(new Date());
							paper.setCertificate_id(ezs_accessory.getId());
							paper.setDeleteStatus(false);
							paper.setValidDate(img.getUsetime());
							paper.setPaperType(img.getImgcode());
							ezs_paperMapper.insertSelective(paper);
							
							ezs_card_dict card=new ezs_card_dict();
							card.setPaper_id(paper.getId());
							card.setStore_id(upi.getStore_id());
							ezs_card_dictMapper.insertSelective(card);
						}
						}
					}
				
				
				num1++;
				log.info("注册：保存用户username:"+destoon_member.getUsername()+"注册信息成功");
			} catch (Exception e) {
				log.info("注册：保存用户username:"+destoon_member.getUsername()+"注册信息错误" + e.toString());
			}
		}
		
		log.info("注册：成功条数" +num1);
		
		return null;
	}

	private void Industry(String[] Industrys,long store){
		ezs_industry_dictMapper.delIndustryDictByStoreId(store);
		for (String long1 : Industrys) {
			ezs_industry_dictMapper.insert(new ezs_industry_dict(store, Long.valueOf(long1)));
		}
	}
	private void companyType(String[] cmtypes,long store){
		ezs_companyType_dictMapper.delCompanyTypeByStoreId(store);
		for (String long1 : cmtypes) {
			ezs_companyType_dictMapper.insert(new ezs_companyType_dict(store, Long.valueOf(long1)) );
		}
		
	}
	
	private static List<AuthImageVo> savepic(String param,List<AuthImageVo> list) throws ParseException{
		param=param.replaceAll("http://www.ezaisheng.com/file/", "http://zs.ezaisheng.com/");
		if(!Tools.isEmpty(param)){
		String[] aa=param.split(";");
		if(null==aa||aa.length==0){
			return list;
		}
		for (String bb : aa) {
			String[] cc=bb.split(",");
			if(null==cc||cc.length<2){
				return list;
			}
			AuthImageVo ImageVo=new AuthImageVo();
			ImageVo.setImgcode(cc[0]);
			
			if(cc[1].indexOf("@")>0&&cc[1].split("@").length==3){
				ImageVo.setImgurl(cc[1].split("@")[0]);
				ImageVo.setName(cc[1].split("@")[1]);
				ImageVo.setUsetime(sdf.parse(cc[1].split("@")[2]));
				list.add(ImageVo);
				System.out.println(ImageVo.toString());
			}else{
				ImageVo.setImgurl(cc[1].split("@")[0]);
				list.add(ImageVo);
				System.out.println(ImageVo.toString());
			}
		}
		}
		return list;
	}
	private  boolean checkisExt(String type,List<AuthImageVo> list){
		boolean status=false;
		for (AuthImageVo authImageVo : list) {
			if(authImageVo.getImgcode().equals(type)){
				status=true;
				break;
			}
		}
		return status;
	}
	
	private  long checkisExtId(String type,List<AuthImageVo> list){
		long id=0;
		for (AuthImageVo authImageVo : list) {
			if(authImageVo.getImgcode().equals(type)){
				id=authImageVo.getAccid();
				break;
			}
		}
		return id;
	}
}
