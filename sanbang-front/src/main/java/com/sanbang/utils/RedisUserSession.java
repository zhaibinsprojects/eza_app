package com.sanbang.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.sanbang.bean.User_Proinfo;
import com.sanbang.bean.ezs_user;
import com.sanbang.bean.ezs_userinfo;
import com.sanbang.redis.RedisConstants;
import com.sanbang.redis.RedisResult;

public class RedisUserSession {
	
	private static Logger log=Logger.getLogger(RedisUserSession.class.getName());

	/**
	 * 若存在当前用户则 返回UserProInfo 不存在 返回null
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public static ezs_user getLoginUserInfo(HttpServletRequest request){
		if(null==request){
			return null;
		}
		Cookie [] cookies=request.getCookies();
		String userKey="";
		if(cookies!=null){
			for(Cookie ck:cookies){
				if(ck.getName().equals("USERKEY")){
					userKey=ck.getValue();
					// String tempCached=(String)arg0.getSession().getAttribute("USERKEY");
					@SuppressWarnings("unchecked")
					RedisResult<ezs_user> tempCached=(RedisResult<ezs_user>) RedisUtils.get(userKey,ezs_user.class);
					if(tempCached!=null&&tempCached.getCode()==RedisConstants.SUCCESS){
						//缓存中已经存在了  说明该用户已经登陆了
						ezs_user result = tempCached.getResult();
						result.setUserkey(userKey);;
						return result;
					}else{
						// log.info("获取用户信息,缓存中用户信息过期或异常");
						break;
					}
				}
			}
			log.debug("获取用户信息,cookie中未有身份标识.cookie:"+cookies.toString());
		}
		log.debug("获取用户信息,未找到cookie");
		return null;
	}
	
	
	
	/**
	 * 若存在当前用户则 返回UserProInfo 不存在 返回null
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public static ezs_user getRegistUserInfo(HttpServletRequest request){
		if(null==request){
			return null;
		}
		Cookie [] cookies=request.getCookies();
		String userKey="";
		if(cookies!=null){
			for(Cookie ck:cookies){
				if(ck.getName().equals("SANBANGREGISTCARD")){
					userKey=ck.getValue();
					// String tempCached=(String)arg0.getSession().getAttribute("USERKEY");
					@SuppressWarnings("unchecked")
					RedisResult<ezs_user> tempCached=(RedisResult<ezs_user>) RedisUtils.get(userKey,ezs_user.class);
					if(tempCached!=null&&tempCached.getCode()==RedisConstants.SUCCESS){
						//缓存中已经存在了  说明该用户已经登陆了
						ezs_user result = tempCached.getResult();
						result.setUserkey(userKey);;
						return result;
					}else{
						// log.info("获取用户信息,缓存中用户信息过期或异常");
						break;
					}
				}
			}
			log.debug("获取用户信息,cookie中未有身份标识.cookie:"+cookies.toString());
		}
		log.debug("获取用户信息,未找到cookie");
		return null;
	}
	
	/**
	 * 若存在当前用户则 返回UserProInfo 不存在 返回null
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public static ezs_user getUserInfoByKey(String userKey){
			@SuppressWarnings("unchecked")
			RedisResult<ezs_user> tempCached=(RedisResult<ezs_user>) RedisUtils.get(userKey,ezs_user.class);
			if(tempCached!=null&&tempCached.getCode()==RedisConstants.SUCCESS){
				//缓存中已经存在了  说明该用户已经登陆了
				ezs_user result = tempCached.getResult();
				result.setUserkey(userKey);
				return result;
			}else{
				log.info("获取用户信息,缓存中用户信息过期或异常");
			}
			log.debug("获取用户信息,userKey为:"+userKey);
		return null;
	}
	/**
	 * 获取用户 cookie userKey
	 * @param request
	 * @return
	 */
	public static String getUserKey(String flag,HttpServletRequest request){
		Cookie [] cookies=request.getCookies();
		String userKey="";
		if(cookies!=null){
			for(Cookie ck:cookies){
				if(ck.getName().equals(flag)){
					userKey=ck.getValue();
					return userKey;
				}
			}
		}
		return null;
	}
	/**
	 * 更新用户信息到缓存 成功返回true  失败false
	 * @param userKey
	 * @param upi
	 * @param timeOut
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean updateUserInfo(String userKey,User_Proinfo upi,Long timeOut){
		RedisResult<String> rrt;
		rrt=(RedisResult<String>) RedisUtils.set(userKey,upi, timeOut);
		if(rrt.getCode()==RedisConstants.SUCCESS){
			log.debug("用户"+upi.getName()+"：userKey保存到redis成功执行");
			return true;
		}else{
			log.debug("用户"+upi.getName()+"：userKey保存到redis失败");
			return false;
		}
	}

}
