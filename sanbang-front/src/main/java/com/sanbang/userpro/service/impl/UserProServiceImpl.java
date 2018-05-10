package com.sanbang.userpro.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanbang.bean.User_Proinfo;
import com.sanbang.bean.ezs_user;
import com.sanbang.bean.ezs_userinfo;
import com.sanbang.dao.ezs_userinfoMapper;
import com.sanbang.redis.RedisConstants;
import com.sanbang.redis.RedisResult;
import com.sanbang.userpro.service.UserProService;
import com.sanbang.utils.DateUtils;
import com.sanbang.utils.EzaishengUCException;
import com.sanbang.utils.MD5Util;
import com.sanbang.utils.RandomStr32;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.RedisUtils;
import com.sanbang.utils.Result;
import com.sanbang.utils.SendMobileMessage;
import com.sanbang.utils.Tools;
import com.sanbang.vo.Dictionary;
import com.sanbang.vo.DictionaryCode;


/**
 * 用户相关处理
 * 
 * @author 
 *  
 * 2016年7月28日
 */
@Service("userProService")
public class UserProServiceImpl implements UserProService{
	
	//日志
	private static Logger log = Logger.getLogger(UserProServiceImpl.class.getName());
	
	@Resource(name="ezs_userinfoMapper")
	private com.sanbang.dao.ezs_userinfoMapper ezs_userinfoMapper;


	@Value("${consparam.cookie.useridcard}")
	private String USERIDCARD;
	
	@Value("${consparam.cookie.cookieuserkeyexpir}")
	private String cookieuserkeyexpir;
	
	@Value("${consparam.redis.redisuserkeyexpir}")
	private String redisuserkeyexpir;
	
	@Value("${consparam.cookie.userkey}")
	private String cookieuserkey;
	
	@Value("${consparam.mobile.sendtimes}")
	private String mobilesendtimes;
	
	@Value("${consparam.mobile.sendcodeexpir}")
	private String mobilesendcodeexpir;
	
	@Value("${consparam.mobile.recode}")
	private String mobilerecode;
	
	@Value("${consparam.mobile.interval}")
	private String mobileinterval;
	
	@Value("${consparam.mobile.upmocode}")
	private String mobileupmocode;
	
	@Value("${consparam.mobile.ftcode}")
	private String mobileftcode;
	
	@Value("${consparam.cookie.usertrailidcard}")
	private String cookieusertrailidcard;
	
	@Value("${consparam.cookie.userstaticidcard}")
	private String cookieuserstaticidcard;
	
	@Value("${consparam.reg.passwd}")
	private String randpasswd;


	/**
	 * 更新缓存中的值
	 * @param username
	 * @param mobile
	 * @return
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	@Override
	public Object updateUserSessionInfo(String username,String mobile ){
		Map<String,Object> map=new HashMap<>();
		RedisResult<Set<String>> results=(RedisResult<Set<String>>) RedisUtils.getMoHu("*"+username+mobile+"*");
		if(results.getCode()==RedisConstants.SUCCESS){
			Set<String> sets=results.getResult();
			Set<String> newsets=new HashSet<String>();
			for(String tempstr:sets){
				if(tempstr.length()>43){
					ezs_userinfo userProInfo =null ;//ezs_userinfoMapper.getUserInfoByUserName(username.trim());
					if(userProInfo!=null){
						RedisUtils.set(tempstr,userProInfo, Long.parseLong(redisuserkeyexpir));
					}
				}
			}
		}
		map.put("status", "success");
		map.put("message", "ok");
		return map;
	}
	
	
	/**
	 * 登录
	 */
	@Override
	public Map<String, Object> login(String userName, String pd,String code,String userAgent,String ip,HttpServletRequest request,HttpServletResponse response,Integer flag) {
		Date date=new Date();
		StringBuilder sbd=new StringBuilder("用户");
		sbd.append(userName).append("请求登陆，passwd=").append(pd).append(" &userAgent=").append(userAgent).append(" &ip=").append(ip).append(" &登录,时间:").append(new SimpleDateFormat("yyyyMMddHHmmss").format(date));
        log.info(sbd.toString());
        Map<String, Object> result = null;
        String useridcard=RedisUserSession.getUserKey(USERIDCARD, request);
        result=loginParam(userName, pd, code,useridcard, request,flag);
       if(result.size()==0){
    	   loginuser(userName, request, response, result, userAgent, pd, ip, date,flag);

       }else{
    	    result.put("code", "888");
			result.put("message", "参数错误");
       }
	  log.debug("用户" + userName +"登陆结果"+result.get("code")+"  "+result.get("message"));
	  return result;
	}
	
	/**
	 * 通过标识 检验验证码
	 * @param code 验证码
	 * @param mobile 手机号
	 * @param mobilerecodestr 模块标识
	 * @return
	 */
	public boolean ckcodemobile(String code,String mobile,String mobilerecodestr){
		RedisResult<String> recode = null;
		Map<String,Object> map=new HashMap<>();
		recode = (RedisResult<String>) RedisUtils.get(mobile+mobilerecodestr,String.class);
		if(recode!=null&&recode.getCode()==RedisConstants.SUCCESS){
			String mCode=null;
			mCode=recode.getResult();
		if((mCode!=null&&mCode.equals(code.trim()))){
			return true;
		 }
		}
		return false;
	}
	
	
	Map<String, Object> loginParam(String userName, String pd,String randImgCode,String useridcard,HttpServletRequest request,Integer flag){
		Map<String, Object> result=new HashMap<String,Object>();
		
		if(flag==null){
			if(StringUtils.isEmpty(pd)){
				result.put("passwdtip", "登陆密码不能为空");
			}
			if(StringUtils.isEmpty(userName)||!Tools.paramValidate(userName, 7)){
	        	result.put("usernametip", "用户名格式错误");
	        }
			if(StringUtils.isEmpty(pd)||pd.trim().length()!=32){
				result.put("passwdtip", "密码格式错误");
			}
		}else{
			//短信验证码
			if(StringUtils.isEmpty(userName)||!Tools.paramValidate(userName,1)){
	        	result.put("usernametip", "手机号码格式错误");
	        }
			if(StringUtils.isEmpty(pd)){
				result.put("passwdtip", "登陆码不能为空");
			}else{
				boolean b=ckcodemobile(pd, userName, "MOBILELOGINFLAG");
				if(!b){
					result.put("passwdtip", "登陆码错误！");
				}
				RedisUtils.del(userName+"MOBILELOGINFLAG");
			}
		}
        if(StringUtils.isEmpty(randImgCode)){
			result.put("randimgtip", "验证码不能为空");
		}else{
			//检验验证码
			
			if(StringUtils.isEmpty(useridcard)){
				result.put("randimgtip", "请重新输入验证码");
			}else{
				RedisResult<String> vacode=null;
				vacode=(RedisResult<String>) RedisUtils.get(useridcard+"validatecode",String.class);
				if(vacode!=null&&vacode.getCode()==RedisConstants.SUCCESS){
					String valicode=vacode.getResult();
					if(valicode.equalsIgnoreCase(randImgCode)){
					}else{
						result.put("randimgtip", "验证码错误，请重新获取");
					}
					RedisUtils.del(useridcard+"validatecode");
				}else{
					result.put("randimgtip", "验证码失效");
				}
			}
		}
		return result;
	}
	
	public void loginuser(String userName,HttpServletRequest request,HttpServletResponse response,Map<String,Object> result,
			String userAgent, String pd,String ip,Date date,Integer flag){
		User_Proinfo upi=RedisUserSession.getUserInfo(request);
	    if(upi!=null&&upi.getName().equals(userName.trim())){
	    	/*int tempStatus=upi.getStatus();
	    	if(tempStatus==0){
				//首次登陆 应该跳转到 注册联系人资料
				result.put("code", "111");
				result.put("message", "用户首次登陆");
			}else{
				result.put("code", "000");
				result.put("message", "登陆成功");
			}*/
	    }else{
	    	if(StringUtils.isEmpty(userAgent)){
				userAgent="unknownUserAgent";
			}
			// 根据用户名查询用户的信息
	    	ezs_user userProInfo=null;
			userProInfo = ezs_userinfoMapper.getUserInfoByUserName(userName.trim());
			if(userProInfo == null){
				 result.put("code", "888");
				 result.put("message", "用户不存在");
				 result.put("usernametip", "用户不存在");
			}else {
				boolean b=false;
				if(flag==null){
					b=pd.equals(userProInfo.getPassword());
				}else{
					b=true;
				}
				//密码验证正确
				if(b){/*
					// 判断是否是财务关联账户,如果是,将登陆信息切换到主账户.avatar:账户登录标识,true:主账户登录,false:财务账户登录,并保存在redis中
					// 如果当前为主账户,更改标识
					if(userProInfo.getAuthtime() == 3){
						userProInfo.setAvatar(true);
					}else{
						// 非主账户,是否有关联的主账户
						List<ezs_user> connectUser = ezs_userinfoMapper.getConnectUserInfo(userProInfo.getId());
						if(connectUser != null){
							if(connectUser.getAuthtime() == 3){
								connectUser.setAvatar(false);
								userProInfo = connectUser;
							}
						}
					}
					//判断是否是首次登陆
					int tempStatus=userProInfo.getStatus();
					String str32=RandomStr32.getStr32();
					String userKey=userProInfo.getUsername()+userProInfo.getMobile()+str32;
					Cookie cookie=new Cookie(cookieuserkey, userKey);
					cookie.setMaxAge(Integer.parseInt(cookieuserkeyexpir));
					cookie.setPath("/");
					if(userProInfo.getGroupid()==5||userProInfo.getGroupid()==6){
						userProInfo.setStatus((short) 4);
						//更新到数据库
						UserProInfo uppi=new UserProInfo();
						uppi.setUserkey(userKey);
						uppi.setUsername(userProInfo.getUsername());
						uppi.setStatus(userProInfo.getStatus());
						ezs_userinfoMapper.updateUserProInfo(uppi);
					}
					userProInfo.setUserkey(userKey);
					response.addCookie(cookie);
					try {
						if(tempStatus==0&&userProInfo.getRegtime()*1000l>1479052799000l){
							//首次登陆 应该跳转到 注册联系人资料
							result.put("code", "111");
							result.put("message", "用户首次登陆");
							RedisResult<String> rrt;
	    					rrt=(RedisResult<String>) RedisUtils.set(userKey,userProInfo, Long.parseLong(redisuserkeyexpir));
	    					RedisUtils.setHash("ACCOUNTCONNECTMAP", userKey+"avatar", userProInfo.getAvatar()?"1":"0");
	    					RedisUtils.setHash("ACCOUNTCONNECTMAP", userKey+"send", userProInfo.getSend()?"1":"0");
	    					if(rrt.getCode()==RedisConstants.SUCCESS){
	    						log.debug("用户"+userName+"：userKey保存到redis成功执行");
	    					}else{
	    						log.debug("用户"+userName+"：userKey保存到redis失败");
	    					}
						}else{
							//如果审核通过 应该更改 status状态
							//此时判断是否有需要跳转的url，有的话跳转
							remberPath(request, result,response);
							result.put("code", "000");
							result.put("message", "登陆成功");
							
							//添加 用户登陆后 判断当前用户类型
							Integer tempFlag=judgeUserType(userProInfo);
							result.put("userType",tempFlag );
							userProInfo.setAid(tempFlag);
							RedisResult<String> rrt;
	    					rrt=(RedisResult<String>) RedisUtils.set(userKey,userProInfo, Long.parseLong(redisuserkeyexpir));
	    					RedisUtils.setHash("ACCOUNTCONNECTMAP", userKey+"avatar", userProInfo.getAvatar()?"1":"0");
	    					RedisUtils.setHash("ACCOUNTCONNECTMAP", userKey+"send", userProInfo.getSend()?"1":"0");
	    					if(rrt.getCode()==RedisConstants.SUCCESS){
	    						log.debug("用户"+userName+"：userKey保存到redis成功执行");
	    					}else{
	    						log.debug("用户"+userName+"：userKey保存到redis失败");
	    					}
						}
					} catch (Exception e) {
						result.put("code", "999");
	    				result.put("message", "运行错误异常");
						log.info("系统错误", e);
					}
					String refadd = RedisUserSession.getUserKey("SOUNDSTATICREFPAGE", request);
					String userid = userProInfo.getId()+"";
					log.error("IP===========>"+ip);
					sLoginRec.saveLoginRec(userKey,1,refadd,userid,userAgent, ip, pd, userName);
				*/}else{
					//密码错误
					 result.put("code", "888");
					 result.put("message", "用户名密码错误");
					 result.put("usernametip", "用户名密码错误");
				}
			}
	    }
	}
	
/*	public Integer judgeUserType(UserProInfo upi){
		//0代表未确定 1代表买方类型  2代表卖方   3代表既是买方又是卖方 
		if(upi.getAid()==null||upi.getAid()==0){
			//未确定类型
			return judgeUserType3(upi);
		}else if(upi.getAid()==3){
			//跳转到 买方中心
			return 3;
		}else if(upi.getAid()==2){
			//查询是否是买方
			return judgeUserType2(upi, 2, "destoon_buy_6", "buyer");
			
		}else if(upi.getAid()==1){
			//查询是否是卖方
			return judgeUserType2(upi, 1, "destoon_sell", "seller");
		}else{
			return 0;
		}
	}*/
	
	
	
	
	/**
	 * 未登录状态下 恢复到登陆之前的状态
	 */
   public void resumePath(HttpServletRequest request,Map<String,Object> result,HttpServletResponse response){
		String toStaticURl=RedisUserSession.getUserKey(cookieuserstaticidcard, request);
		result.put("tostaticurl", toStaticURl);
		Cookie cookie = new Cookie(cookieuserstaticidcard, null);
		cookie.setDomain("/");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}
   /**
    * 对将要跳转的路径 做恢复
    */
   @SuppressWarnings("unchecked")
public void remberPath(HttpServletRequest request,Map<String,Object> result,HttpServletResponse response){
	 //此时判断是否有需要跳转的url，有的话跳转
		String usertourl=RedisUserSession.getUserKey(cookieusertrailidcard, request);
		if(usertourl!=null){
			RedisResult<String> temres=null;
			temres=(RedisResult<String>)  RedisUtils.get(usertourl,String.class);
			if(temres!=null&&temres.getCode()==RedisConstants.SUCCESS){
				String temStrstr=temres.getResult();
				result.put("tourl", temStrstr);
				RedisUtils.del(temStrstr);
				Cookie cookie = new Cookie(cookieusertrailidcard, null);
				cookie.setMaxAge(0);
				cookie.setDomain("/");
				response.addCookie(cookie);
			}else{
				resumePath(request, result,response);
			}
		}else{
			resumePath(request, result,response);
		}
		
   }

	
	public Map<String, Object> loginRandImgVali(String randImgCode,HttpServletRequest request) throws Exception{
		Map<String, Object> result=new HashMap<String,Object>();
		String useridcard=RedisUserSession.getUserKey(USERIDCARD, request);
		RedisResult<String> vacode=null;
		if(StringUtils.isEmpty(randImgCode)){
			 result.put("code", "888");
			 result.put("message", "验证码不能为空");
			result.put("randimgtip", "验证码不能为空");
		}else{
			vacode=(RedisResult<String>) RedisUtils.get(useridcard+"validatecode",String.class);
			if(vacode!=null&&vacode.getCode()==RedisConstants.SUCCESS){
				String valicode=vacode.getResult();
				if(valicode.equalsIgnoreCase(randImgCode)){
					result.put("code", "000");
					result.put("message", "操作成功");
				}else{
					 RedisUtils.del(useridcard+"validatecode");
					 result.put("code", "888");
					 result.put("message", "验证码错误，请重新填写");
					 result.put("randimgtip", "验证码错误，请重新填写");
				}
			}else{
				 result.put("code", "888");
				 result.put("message", "验证码错误");
				 result.put("randimgtip", "验证码错误");
			}
		}
		return result;
	}
	/**
	 * 注册新用户
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(isolation=Isolation.SERIALIZABLE,rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Map<String, Object> userAdd(String username, String passwd,String passwdA, String mobile,String code,String myip,Integer flag,HttpSession session,HttpServletRequest request,String imgcode) throws Exception{
		log.info("新用户注册 参数：username="+username+" &passwd="+passwd+" &mobile="+mobile+" &code="+code);
		Map<String, Object> map=null;
		if(flag==null){
			//用户自己注册
			map=userAddParam(username, passwd,passwdA, mobile, code,imgcode,request);
		}else{
			//其他注册方式  flag 为4qq注册 5微信注册
			//生成用户名密码
			if(flag==4){
				username="qq_"+RandomStr32.getStrDefined(7);
			}else if(flag==5){
				username="wx_"+RandomStr32.getStrDefined(7);
			}
			passwd=MD5Util.md5Encode(MD5Util.md5Encode(randpasswd));
		}
		if(map.size()==0){
			if(flag==null){
				map=regself(mobile, username, myip, passwd);
			}else{
				map=regbyauto(username, mobile, passwd , myip,flag);
			}
		}else{
			 map.put("code", "888");
			 map.put("message", "参数错误");
		}
		log.info("注册结果：code="+map.get("code")+" &message="+map.get("message") );
		return map;
	}

	Map<String, Object> userAddParam(String username, String passwd,String passwdA, String mobile,String code,String randImgCode,HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
			if(StringUtils.isEmpty(username)||!Tools.paramValidate(username, 0)||username.trim().length()>20){
				map.put("username", "请输入6-20位以内数字或字母！");
			}
			if(StringUtils.isEmpty(passwd)||passwd.trim().length()!=32){
				map.put("passwd", "密码格式错误");
			}
			if(StringUtils.isEmpty(passwdA)){
				map.put("passwdA", "确认密码不能为空");
			}else{
				if(StringUtils.isNotEmpty(passwd)&&!passwd.equals(passwdA)){
					map.put("passwdA", "两次密码输入不一致！");
				}
			}
			if(StringUtils.isEmpty(mobile)||!Tools.paramValidate(mobile, 1)){
				map.put("mobile", "格式有误，请输入正确的手机号码！");
			}
			if(StringUtils.isEmpty(code)){
				map.put("recode", "验证码不能为空");
			}else{
				RedisResult<String> recode = null;
				recode = (RedisResult<String>) RedisUtils.get(mobile+mobilerecode,String.class);
				if(recode!=null&&recode.getCode()==RedisConstants.SUCCESS){
					String mCode=null;
					mCode=recode.getResult();
				if((mCode!=null&&mCode.equals(code.trim()))){
							//验证码正确而且没有超时
							//手机号可以注册
							//此时可以保存数据
					}else{
						map.put("code", "888");
						map.put("message", "验证码错误");
						map.put("recode", "验证码错误");
					}
				}else{
					map.put("code", "888");
					map.put("message", "验证码错误");
					map.put("recode", "验证码错误");
				}
				try {
						RedisUtils.del(mobile+"RECODE");
//					    RedisUtils.del(mobile+"RESENDTIME");
				} catch (Exception e1) {
					log.info("删除redis数据失败",e1);
				}
			}
			if(StringUtils.isEmpty(randImgCode)){
				map.put("imgcode", "验证码不能为空");
			}else{
				//检验验证码
				String useridcard=RedisUserSession.getUserKey(USERIDCARD, request);
				if(StringUtils.isEmpty(useridcard)){
					map.put("imgcode", "请重新输入验证码");
				}else{
					RedisResult<String> vacode=null;
					vacode=(RedisResult<String>) RedisUtils.get(useridcard+"validatecode",String.class);
					if(vacode!=null&&vacode.getCode()==RedisConstants.SUCCESS){
						String valicode=vacode.getResult();
						if(valicode.equalsIgnoreCase(randImgCode)){
						}else{
							map.put("imgcode", "验证码错误，请重新获取");
						}
						RedisUtils.del(useridcard+"validatecode");
					}else{
						map.put("imgcode", "验证码失效");
					}
				}
			}
		return map;
	}
	public Map<String,Object> regself(String mobile,String username,String myip,String passwd){
		//用户自注册
		Map<String,Object> map=new HashMap<>();
		ezs_user upii=	ezs_userinfoMapper.getUserInfoByUserName(username);
			if(upii!=null){
				//已存在，不能注册
				map.put("code", "999");
				map.put("message", "注册失败,会员名称已存在");
				return map;
			}
			ezs_user upii2=	ezs_userinfoMapper.getUserInfoByUserName(mobile);
			if(upii2!=null){
				//已存在，不能注册
				map.put("code", "999");
				map.put("message", "注册失败,手机号码已存在");
				return map;
			}
			List<ezs_user> upis=ezs_userinfoMapper.getUserInfoByUserNameFromBack(username);
			if(upis!=null&&upis.size()!=0){
				//已存在，不能注册
				map.put("code", "999");
				map.put("message", "注册失败,会员名称已存在");
				return map;
			}
			
	

					ezs_user upi=new ezs_user();
					upi.setName(username);
					upi.setPassword(passwd);
					int a = 0;
						a=ezs_userinfoMapper.insert(upi);
					if(a==1){
						map.put("code", "000");
						map.put("message", "注册成功");
						
						//开通环信
						//hunXinService.regHuanxinSingle(username, HunXinServiceImpl.passwordefault, userInfo.getUserid());
						
					}else{
						map.put("code", "999");
						map.put("message", "注册失败");
					}
		try {
				RedisUtils.del(mobile+"RECODE");
//			    RedisUtils.del(mobile+"RESENDTIME");
		} catch (Exception e1) {
			log.info("删除redis数据失败",e1);
		}
		return map;
	}
	public Map<String,Object> regbyauto(String username,String mobile,String passwd,String myip,Integer flag){
		Map<String,Object> map=new HashMap<>();
		ezs_user upii=	ezs_userinfoMapper.getUserInfoByUserName(username);
			if(upii!=null){
				//已存在，不能注册
				map.put("code", "999");
				map.put("message", "注册失败,会员名称已存在");
				return map;
			}
			List<ezs_user> upis=ezs_userinfoMapper.getUserInfoByUserNameFromBack(username);
			if(upis!=null&&upis.size()!=0){
				//已存在，不能注册
				map.put("code", "999");
				map.put("message", "注册失败,会员名称已存在");
				return map;
			}
			ezs_user upi=new ezs_user();
			upi.setName(username);
			upi.setPassword(passwd);
				int a = 0;
					a=ezs_userinfoMapper.insert(upi);
					if(flag!=null){
						/*if(flag==3){
							//qq登陆
							upi.setGuser("3");
						}else if(flag==4){
							//微信登陆
							upi.setGuser("4");
						}*/
					}
				if(a==1){
					map.put("code", "000");
					map.put("message", "注册成功");
					
					//自动开通商铺
					ezs_user userInfo = ezs_userinfoMapper.getUserInfoByUserName(username);
					/*ShopInfo shopInfo =new ShopInfo();
					shopInfo.setIsopenshop("0");
					shopInfo.setUsername(username);
					shopInfo.setShopname(username);
					shopInfo.setUserid(userInfo.getUserid()+"");
					
					shopService.addShopInfo(shopInfo);*/
					
					
				}else{
					map.put("code", "999");
					map.put("message", "注册失败");
				}
		return map;
	}
	/**
	 * 发送注册，登陆短信验证码
	 * @param phone
	 * @param code
	 * @param mobilerecodestr 验证码标识
	 * @param mobilesendcodeexpirstr 验证码有效期 单位为 秒
	 * @param mobileintervalstr   验证码距离下一次点击的时间间隔
	 * @param mobilesendtimesstr  验证码 获取次数
	 * @return
	 */
	@Override
	public  Map<String, Object>  sendCode(String phone,String code,String mobilerecodestr,String mobilesendcodeexpirstr,String mobileintervalstr,String mobilesendtimesstr,Integer flag,String content) {
		 Map<String, Object> map=new HashMap<String,Object>();
		 if(StringUtils.isEmpty(phone)||!Tools.paramValidate(phone, 1)){
			 map.put("code", "888");
			 map.put("message", "格式有误，请输入正确的手机号码");
			 map.put("phone", "格式有误，请输入正确的手机号码");
		 }else{
			 //先判断发送的次数 和 时间间隔
			 Long totalcodetimes=Long.parseLong(mobilesendtimesstr);
			 RedisResult<Integer> rrtin=(RedisResult<Integer>) RedisUtils.get(phone+mobilerecodestr+"times",Integer.class);
			 Integer codetimes=0;
			 //先判断发送的次数
			 if(rrtin.getCode()==RedisConstants.SUCCESS){ 
				 codetimes=rrtin.getResult();
			 }
			 if(codetimes<totalcodetimes){
				//判断时间间隔
				 Long expir=Long.parseLong(mobilesendcodeexpirstr);
				 Long codeinterval=Long.parseLong(mobileintervalstr);
				 RedisResult<Long> reexpirresult=(RedisResult<Long>) RedisUtils.getExpir(phone+mobilerecodestr);
				 Long reexpir=0l;
				 if(reexpirresult.getCode()==RedisConstants.SUCCESS){
					 reexpir=reexpirresult.getResult();
				 }
				 if(expir-reexpir>codeinterval){
					 //间隔大于60 可以发送短信
					// 短信内容
			 			log.info("短信验证码,发送内容:"+content);
			 			try {
			 				SendMobileMessage.sendMsg(phone, content);
			 				map.put("code", "000");
			 				map.put("message", "验证码发送成功");
			 				RedisUtils.set(phone+mobilerecodestr, code, Long.parseLong(mobilesendcodeexpirstr));
			 				RedisUtils.set(phone+mobilerecodestr+"times", ++codetimes, DateUtils.getTimeValue());
			 			} catch (Exception e) {
			 				log.error("短信验证码功能失败");
			 				log.error(e.toString());
			 				map.put("code", "666");
			 				map.put("message", "验证码发送失败");
			 			}
				 }else{
					 map.put("code", "888");
					 map.put("message", "请等待"+mobileintervalstr+"s后再次点击");
					 map.put("phone", "请等待"+mobileintervalstr+"s后再次点击");
				 }
			}else{
				 map.put("code", "888");
				 map.put("message", "验证码已发送多次，请查收短信");
				 map.put("phone", "验证码已发送多次，请查收短信");
			}
			
			
		 }
	    	
	 	return map;
	}
	/**
	 * 发送修改手机号码验证码
	 */
	@Override
	public  Map<String, Object>  sendUpMoCode(String phone,String code) {
		Map<String, Object> map=new HashMap<String,Object>();
		if(StringUtils.isEmpty(phone)||!Tools.paramValidate(phone, 1)){
			 map.put("code", "888");
			 map.put("message", "手机号码格式错误");
			 map.put("phone", "手机号码格式错误");
		 }else{
			 
			//先判断发送的次数 和 时间间隔
			 Long totalcodetimes=Long.parseLong(mobilesendtimes);
			 //这里需要改
			 RedisResult<Integer> rrtin=(RedisResult<Integer>) RedisUtils.get(phone+mobileupmocode+"times",Integer.class);
			 Integer codetimes=0;
			 //先判断发送的次数
			 if(rrtin.getCode()==RedisConstants.SUCCESS){ 
				 codetimes=rrtin.getResult();
			 }
			 if(codetimes<totalcodetimes){
				//判断时间间隔
				 Long expir=Long.parseLong(mobilesendcodeexpir);
				 Long codeinterval=Long.parseLong(mobileinterval);
				 RedisResult<Long> reexpirresult=(RedisResult<Long>) RedisUtils.getExpir(phone+mobileupmocode);
				 Long reexpir=0l;
				 if(reexpirresult.getCode()==RedisConstants.SUCCESS){
					 reexpir=reexpirresult.getResult();
				 }
				 if(expir-reexpir>codeinterval){
					 //间隔大于60 可以发送短信
					 // 短信内容
					 String content = "您的短信验证码:"+code.toString()+",请勿告诉他人,有效时间为"+mobilesendcodeexpir+"分钟!";
					 log.info("短信验证码,发送内容:"+content);
					 try {
						 SendMobileMessage.sendMsg(phone, content);
						 map.put("code", "000");
						 map.put("message", "验证码发送成功");
						 RedisUtils.set(phone+mobileupmocode, code, Long.parseLong(mobilesendcodeexpir));
						 //这里需要改
						 RedisUtils.set(phone+mobileupmocode+"times", ++codetimes, DateUtils.getTimeValue());
					 } catch (Exception e) {
						 log.error("短信验证码功能失败");
						 log.error(e.toString());
						 map.put("code", "666");
						 map.put("message", "验证码发送失败");
					 } 
				 }else{
					 map.put("code", "888");
					 map.put("message", "请等待"+mobileinterval+"s后发送");
					 map.put("phone", "请等待"+mobileinterval+"s后发送");
				 }
			}else{
				 map.put("code", "888");
				 map.put("message", "验证码已发送多次，请查收短信");
				 map.put("phone", "验证码已发送多次，请查收短信");
			}
			 
			
		 }
		return map;
	}
	/**
	 * 发送忘记密码短信验证码
	 */
	@Override
	public  Map<String, Object>  sendFtCode(String phone,String code) {
		Map<String, Object> map=new HashMap<String,Object>();
		if(StringUtils.isNotEmpty(phone)&&Tools.paramValidate(phone, 1)){
			//先判断发送的次数 和 时间间隔
			 Long totalcodetimes=Long.parseLong(mobilesendtimes);
			 //这里需要改
			 RedisResult<Integer> rrtin=(RedisResult<Integer>) RedisUtils.get(phone+mobileftcode+"times",Integer.class);
			 Integer codetimes=0;
			 //先判断发送的次数
			 if(rrtin.getCode()==RedisConstants.SUCCESS){ 
				 codetimes=rrtin.getResult();
			 }
			 if(codetimes<totalcodetimes){
				//判断时间间隔
				 Long expir=Long.parseLong(mobilesendcodeexpir);
				 Long codeinterval=Long.parseLong(mobileinterval);
				 RedisResult<Long> reexpirresult=(RedisResult<Long>) RedisUtils.getExpir(phone+mobileftcode);
				 Long reexpir=0l;
				 if(reexpirresult.getCode()==RedisConstants.SUCCESS){
					 reexpir=reexpirresult.getResult();
				 }
				 if(expir-reexpir>codeinterval){
					 //间隔大于60 可以发送短信
					// 短信内容
						String content = "您的短信验证码:"+code.toString()+",请勿告诉他人,有效时间为10分钟!";
						
						log.info("短信验证码,发送内容:"+content);
						
						try {
							//SendMobileMessage.sendMsg(phone, content);
							map.put("code", "000");
							map.put("message", "验证码发送成功");
							RedisUtils.set(phone+mobileftcode, code, Long.parseLong(mobilesendcodeexpir));
							//这里需要改
							RedisUtils.set(phone+mobileftcode+"times", ++codetimes, DateUtils.getTimeValue());
						} catch (Exception e) {
							log.error("短信验证码功能失败");
							log.error(e.toString());
							map.put("code", "888");
							map.put("message", "验证码发送失败");
							map.put("ftcode", "验证码发送失败");
						}
					}else{
						 map.put("code", "888");
						 map.put("message", "请等待"+mobileinterval+"s后发送");
						 map.put("phone", "请等待"+mobileinterval+"s后发送");
					}
				 }else{
					 map.put("code", "888");
					 map.put("message", "验证码已发送多次，请查收短信");
					 map.put("phone", "验证码已发送多次，请查收短信");
				 }
			}else{
				map.put("code", "888");
				map.put("message", "参数错误");
				map.put("phone", "手机号码错误");
				
			}
			
		return map;
	}
	
	/**
	 * 通过手机号修改密码
	 * @throws EzaishengUCException 
	 */
	@Override
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED,isolation=Isolation.SERIALIZABLE)
	public Map<String, Object> chgPasswd(String passwd, String passwdA,HttpServletRequest request) throws EzaishengUCException {
		Map<String,Object> map=new HashMap<String,Object>();
		log.info("修改密码  passwd="+passwd+" &passwdA="+passwdA);
		if(StringUtils.isNotEmpty(passwd)){
			if(StringUtils.isNotEmpty(passwdA)){
				if(passwd.equals(passwdA)){
					//都没问题 应该修改密码
					String doFtCodde=RedisUserSession.getUserKey("DOFTCODE", request);
					@SuppressWarnings("unchecked")
					RedisResult<String> mobileres=(RedisResult<String>) RedisUtils.get(doFtCodde,String.class);
					if(mobileres!=null&&mobileres.getResult()!=null&&mobileres.getCode()==RedisConstants.SUCCESS){
						Map<String,Object> mp=new HashMap<String,Object>();
						mp.put("mobile", mobileres.getResult());
						mp.put("password", passwd);
						RedisUtils.del(doFtCodde);
						int a=0;
						try {
							a=ezs_userinfoMapper.updateUserPwdByMobile(mp);
						} catch (Exception e) {
							log.error("修改密码失败",e);
						}
						if(a>=1){
							map.put("code", "000");
							map.put("message", "操作成功");
						}else{
							map.put("code", "999");
							map.put("message", "操作异常");
							throw new EzaishengUCException("手机号修改异常");
						}
					}else{
						map.put("code", "999");
						map.put("message", "操作异常");
					}
					
				}else{
					map.put("code", "888");
					map.put("passwdA", "密码和确认密码必须相同");
					map.put("message", "密码和确认密码必须相同");
				}
			}else{
				map.put("code", "888");
				map.put("passwdA", "确认密码不能为空");
				map.put("message", "确认密码不能为空");
			}
		}else{
			map.put("code", "888");
			map.put("passwd", "密码不能为空");
			map.put("message", "密码不能为空");
		}
		log.info("修改密码结果：code="+map.get("code")+" &passwd="+map.get("message"));
		return map;
	}

	/**
	 * 检查用户名是否存在
	 */
	@Override
	public Map<String,Object> checkUserName(String userName) {
		Map<String,Object> map=new HashMap<String, Object>();
		log.info("检验账号:userName="+userName);
		if(StringUtils.isEmpty(userName)){
			map.put("code", "888");
			map.put("userName", "请输入6-20位以内数字或字母！");
			map.put("message", "请输入6-20位以内数字或字母！");
		}else if(!Tools.paramValidate(userName, 0)){
			map.put("code", "888");
			map.put("userName", "账号格式错误");
			map.put("message", "账号格式错误");
		}else{
			List<ezs_user>  count=null;
				try {
					count=ezs_userinfoMapper.getUserInfoByUserNameFromBack(userName);
					if(count!=null&&count.size()>0){
						//可以注册
						map.put("code", "000");
						map.put("userName", "账号不存在");
						map.put("message", "账号不存在");
					}else{
						map.put("code", "888");
						map.put("userName", "账号已经存在");
						map.put("message", "账号已经存在");
					}
				} catch (Exception e) {
					map.put("code", "999");
					map.put("message", "操作异常");
				}
		}
		log.info("检验账号结果:code="+map.get("code")+" &message="+map.get("message"));
		return map;
	}
	/**
	 * 检查手机号码是否存在
	 */
	@Override
	public Result checkMobile(String mobile) {
		Result result=Result.failure();
		if(StringUtils.isEmpty(mobile)||!Tools.paramValidate(mobile, 1)){
			result.setErrorcode(DictionaryCode.ERROR_WEB_PHONE_TYPE_REGISTERED);
			result.setSuccess(false);
			result.setMsg("格式有误，请输入正确的手机号码");
		}else{
			int count=0;
				try {
					count=ezs_userinfoMapper.checkMobile(mobile);
					if(count==0){
						result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
						result.setSuccess(true);
						result.setMsg("该手机号可用");
						//可以注册
					}else{
						result.setErrorcode(DictionaryCode.ERROR_WEB_PHONE_TYPE_REGISTERED);
						result.setSuccess(false);
						result.setMsg("手机号码已经被注册");
					}
				} catch (Exception e) {
					result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
					result.setSuccess(false);
					result.setMsg("服务器异常");
				}
		}
		return result;
	}

	/**
	 * 检查修改密码前的验证码(忘记密码模块修改密码)
	 */
	@Override
	public Map<String, Object> checkFtCode(String mobile, String code,HttpServletRequest request,HttpServletResponse response) throws Exception {
		log.info("修改密码前验证验证码：mobile="+mobile+"  &code="+code);
		Map<String,Object> map=null;
		map=checkFtCodeParam(mobile, code);
		if(map.size()==0){
			@SuppressWarnings("unchecked")
			RedisResult<String> ftcode=(RedisResult<String>) RedisUtils.get(mobile+"FTCODE",String.class);
			if(ftcode!=null&&ftcode.getResult()!=null&&ftcode.getCode()==RedisConstants.SUCCESS){
				String temCode=ftcode.getResult();
				if(temCode.trim().equals(code.trim())){
						//正确  进入下一步
					if(request!=null&&response!=null){
						String str32=RandomStr32.getStr32();
						String key=mobile+str32;
						Cookie cookie=new Cookie("DOFTCODE", key);
						cookie.setMaxAge(-1);
						cookie.setPath("/");
						response.addCookie(cookie);
						RedisResult<String> rrt;
						rrt=(RedisResult<String>) RedisUtils.set(key,mobile, Long.parseLong(mobilesendcodeexpir));
						if(rrt.getCode()==RedisConstants.SUCCESS){
							log.info("用户"+mobile+"：修改密码前手机号码信息保存到redis成功执行");
						}else{
							log.info("用户"+mobile+"：修改密码前手机号码信息保存到redis失败");
						}
					}
						map.put("code", "000");
						map.put("message", "操作成功");
				}else{
					map.put("code", "888");
					map.put("message", "参数错误");
					map.put("ftcode", "验证码错误");
				}
				//删除缓存中验证码
				RedisUtils.del(mobile+"FTCODE");
				RedisUtils.del(mobile+"FTSENDTIME");
			}else{
				map.put("code", "888");
				map.put("message", "参数错误");
				map.put("ftcode", "验证码失效");
			}
		}else{
			map.put("code", "888");
			map.put("message", "参数错误");
		}
		return map;
	}
	Map<String, Object> checkFtCodeParam(String mobile, String code){
		Map<String,Object> map=new HashMap<String,Object>();
		if(StringUtils.isEmpty(mobile)||!Tools.paramValidate(mobile, 1)){
			map.put("phone", "手机号码格式错误");
		}
		if(StringUtils.isEmpty(code)){
			map.put("ftcode", "验证码不能为空");
		}
		return map;
	}
	/**
	 * 修改手机号码
	 */
	@Override
	public Map<String, Object> checkUpMoCode(String mobile, String code,User_Proinfo upi,HttpServletRequest request) throws Exception{
		if(upi==null){
			throw new EzaishengUCException("系统异常");
		}
		log.info("修改手机号码前验证验证码：mobile="+mobile+"  &code="+code);
		Map<String,Object> map=null;
		map=checkUpMoCodeParam(mobile, code);
		if(map.size()==0){
			@SuppressWarnings("unchecked")
			RedisResult<String> ftcode=(RedisResult<String>) RedisUtils.get(mobile+"UPMOCODE",String.class);
			if(ftcode!=null&&ftcode.getResult()!=null&&ftcode.getCode()==RedisConstants.SUCCESS){
				String temCode=ftcode.getResult();
				if(temCode.trim().equals(code.trim())){
						//正确  进入下一步
					ezs_user uupi=new ezs_user();
						uupi.setName(upi.getName());
						uupi.setPhone(mobile);
						int temaa=ezs_userinfoMapper.updateByPrimaryKeySelective(uupi);
						if(temaa==1){
							//更新缓存
							upi.getUserInfo().setPhone(mobile);
							RedisUserSession.updateUserInfo(RedisUserSession.getUserKey(cookieuserkey, request), upi, Long.parseLong(redisuserkeyexpir));
							map.put("code", "000");
							map.put("message", "操作成功");
						}else{
							map.put("code", "999");
							map.put("message", "系统异常");
						}
						
				}else{
					map.put("code", "888");
					map.put("message", "参数错误");
					map.put("upmocode", "验证码错误");
				}
				//删除缓存中验证码
				RedisUtils.del(mobile+"UPMOCODE");
			}else{
				map.put("upmocode", "请重新获取验证码");
				map.put("code", "888");
				map.put("message", "参数错误");
			}
		}else{
			map.put("code", "888");
			map.put("message", "参数错误");
		}
		return map;
	}
	Map<String, Object> checkUpMoCodeParam(String mobile, String code){
		Map<String,Object> map=new HashMap<String,Object>();
		if(StringUtils.isEmpty(mobile)||!Tools.paramValidate(mobile, 1)){
			map.put("mobile", "手机号码格式错误");
		}
		if(StringUtils.isEmpty(code)){
			map.put("upmocode", "验证码不能为空");
		}
		return map;
	}
	
	/**
	 * 发送签章短信验证码
	 * flag 为1 交易保发送合同 短信验证码 为2 小易商城发送短信验证码
	 */
	@SuppressWarnings("unchecked")
	@Override
	public  Map<String, Object>  sendContractCode(String phone,String code,Integer flag) {
		Map<String, Object> map=new HashMap<String,Object>();
		if(StringUtils.isEmpty(phone)||!Tools.paramValidate(phone, 1)){
			map.put("status", "fail");
			map.put("message", "格式有误，请输入正确的手机号码");
			map.put("phone", "格式有误，请输入正确的手机号码");
		}else{
				//判断时间间隔
				Long expir=Long.parseLong(mobilesendcodeexpir);
				Long codeinterval=Long.parseLong(mobileinterval);
				RedisResult<Long> reexpirresult=(RedisResult<Long>) RedisUtils.getExpir(phone+"JYBSC");
				Long reexpir=0l;
				if(reexpirresult.getCode()==RedisConstants.SUCCESS){
					reexpir=reexpirresult.getResult();
				}
				if(expir-reexpir>codeinterval){
					//间隔大于60 可以发送短信
					// 短信内容
					String content = "您的短信验证码:"+code.toString()+",请勿告诉他人,有效时间为10分钟!";
					log.info("短信验证码,发送内容:"+content);
					try {
						//SendMobileMessage.sendMsg(phone, content);
						map.put("status", "success");
						map.put("message", "验证码发送成功");
						RedisUtils.set(phone+"JYBSC", code, Long.parseLong(mobilesendcodeexpir));
					} catch (Exception e) {
						log.error("短信验证码功能失败");
						log.error(e.toString());
						map.put("status", "fail");
						map.put("message", "验证码发送失败");
					}
				}else{
					map.put("status", "fail");
					map.put("message", "请等待"+mobileinterval+"s后再次点击");
					map.put("phone", "请等待"+mobileinterval+"s后再次点击");
				}

			
			
		}
		return map;
	}
	
	/**
	 * 检查修改密码前的验证码(忘记密码模块修改密码)
	 */
	@Override
	public Map<String, Object> checkSCCode(String mobile, String code) throws Exception {
		log.info("签合同验证验证码：mobile="+mobile+"  &code="+code);
		Map<String,Object> map=null;
		map=checkFtCodeParam(mobile, code);
		if(map.size()==0){
			@SuppressWarnings("unchecked")
			RedisResult<String> ftcode=(RedisResult<String>) RedisUtils.get(mobile+"JYBSC",String.class);
			if(ftcode!=null&&ftcode.getResult()!=null&&ftcode.getCode()==RedisConstants.SUCCESS){
				String temCode=ftcode.getResult();
				if(temCode.trim().equals(code.trim())){
						//正确  进入下一步
						map.put("status", "success");
						map.put("message", "操作成功");
				}else{
					map.put("status", "fail");
					map.put("message", "验证码错误");
				}
				//删除缓存中验证码
				RedisUtils.del(mobile+"JYBSC");
			}else{
				map.put("status", "fail");
				map.put("message", "验证码错误");
			}
		}else{
			map.put("status", "fail");
			map.put("message", "请检查手机号和验证码！");
		}
		return map;
	}
	
	@Override
	public ezs_user getUserInfoByUserName(String userName) {
		
		return ezs_userinfoMapper.getUserInfoByUserName(userName);
	}
	@Override
	public boolean userLogot(User_Proinfo upi,String cookieuserkey) throws Exception {
		if(upi!=null){
			RedisUtils.del(cookieuserkey);
		}
		return true;
		
	}

	@Override
	public String getUserMessage(String username) {
		return ezs_userinfoMapper.getUserMessage(username);
	}


	@Override
	public Map<String, Object> checkCompany(HttpServletRequest request, String company) {
		// TODO Auto-generated method stub
		return null;
	}
}
