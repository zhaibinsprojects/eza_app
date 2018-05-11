package com.sanbang.interceptors;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.sanbang.bean.ezs_user;
import com.sanbang.redis.RedisConstants;
import com.sanbang.redis.RedisResult;
import com.sanbang.utils.HttpRequestDeviceUtils;
import com.sanbang.utils.RandomStr32;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.RedisUtils;


public class LoginInterceptor implements HandlerInterceptor{

	private Logger log=Logger.getLogger(LoginInterceptor.class);
	
	@Value("${consparam.redis.redisuserkeyexpir}")
	private String redisuserkeyexpir;
	
	@Value("${consparam.cookie.userkey}")
	private String cookieuserkey;
	
	
	
	@Value("${urlparam.filter.urls}")
	private String filterurls;
	
	@Value("${urlparam.urltrail.urls}")
	private String urltrails;
	
	
	
	
	@Value("${consparam.cookie.usertrailidcard}")
	private String cookieusertrailidcard;
	
	@Value("${consparam.cookie.usertrailidcardexpir}")
	private String usertrailidcardexpir;
	
	@Value("${consparam.redis.usertrailidcardexpir}")
	private String redisusertrailidcardexpir;
	
	
	
	//项目基础路径
	@Value("${consparam.ezaisheng.base.url}")
	private String baseurl;
	
	//h5基础路径
	@Value("${consparam.ser.baseurl}")
	private String serurl;
	
	
	private Set<String> filterUrls=null;
	
	private Set<String> urltrailurls=null;
	
	//加载需要过滤的方法
	@PostConstruct 
	public void initMethod(){
		String [] tempFilterUrls=StringUtils.split(filterurls, ",");
		filterUrls=new HashSet<>();
		for(int i=0;i<tempFilterUrls.length;i++){
			filterUrls.add(tempFilterUrls[i].trim().replaceAll("\\s*|\t|\r|\n", ""));
		}
		String [] tempTrailUrls=StringUtils.split(urltrails, ",");
		urltrailurls=new HashSet<>();
		for(int i=0;i<tempTrailUrls.length;i++){
			urltrailurls.add(tempTrailUrls[i].trim());
		}
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2, ModelAndView arg3) throws Exception {
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		
		log.debug("开始拦截");
		String saveLinkInfoUrl=request.getServletPath();
		log.debug("当前地址："+saveLinkInfoUrl);
		if(filterUrls.contains(saveLinkInfoUrl)){
			log.debug("直接放行："+saveLinkInfoUrl);
			return true;
		}
		return gStatus(saveLinkInfoUrl, request, response);
		
	}
	
	public boolean gStatus(String saveLinkInfoUrl,HttpServletRequest request,HttpServletResponse response) throws IOException{
		if(saveLinkInfoUrl.indexOf("randCode")!=-1&&saveLinkInfoUrl.indexOf("randImg")!=-1){
			return true;
		}else{
			Cookie [] cookies=request.getCookies();
			if(cookies!=null){
						String userKey=RedisUserSession.getUserKey(cookieuserkey, request);
						if(userKey!=null){
							@SuppressWarnings("unchecked")
							RedisResult<ezs_user> tempCached=(RedisResult<ezs_user>) RedisUtils.get(userKey,ezs_user.class);
							if(tempCached!=null&&tempCached.getCode()==RedisConstants.SUCCESS){
								//缓存中已经存在了  说明该用户已经登陆了  放行
								ezs_user upi=tempCached.getResult();
								return judgeUri(upi, saveLinkInfoUrl, userKey, response,request);
							}else{
								//调转到登录页 可能服务器session已经过期
								rememberPath(request, response);
								log.debug("用户未登陆");
								if(HttpRequestDeviceUtils.isMobileDevice(request)){
									response.sendRedirect(serurl+"index.html");
								}
								return false;
							}
						}else{
							//调转到登录页  没有对应cookie key
							rememberPath(request, response);
							log.debug("用户未登陆");
							if(HttpRequestDeviceUtils.isMobileDevice(request)){
								response.sendRedirect(serurl+"index.html");
							}
							return false;
						}
			}else{
				//调转到登录页  没有cookie
				rememberPath(request, response);
				log.debug("用户未登陆");
				if(HttpRequestDeviceUtils.isMobileDevice(request)){
					response.sendRedirect(baseurl+"apph5/userPro/toLoginPage.htm");
				}else{
					response.sendRedirect(baseurl+"userPro/toLoginPage.htm");
				}
				return false;
			}
		}
	}
	
	/**
	 * 判断当前用户是否是首次登陆 若是首次登陆 则跳转到  注册联系人 否则 验证登陆缓存时间有效期
	 * @param upi
	 * @param saveLinkInfoUrl
	 * @param userKey
	 * @param response
	 * @return
	 * @throws IOException
	 */
	private boolean judgeUri(ezs_user upi,String saveLinkInfoUrl,String userKey,HttpServletResponse response,HttpServletRequest request) throws IOException{
		if(upi.getStatus()==null||upi.getStatus()==0){
			if(upi.getLastLoginDate().getTime()*1000l>1479052799000l||
					(StringUtils.isEmpty(upi.getTrueName())&&upi.getLastLoginDate().getTime()*1000l<1479052799000l)){
				log.info("用户信息未记入缓存 拦截 并重定向到登陆页");
				//跳到登陆页
				log.info("用户"+upi.getName()+"首次登陆");
				if(saveLinkInfoUrl.indexOf("userLinkMan")!=-1&&saveLinkInfoUrl.indexOf("memberAuth")!=-1){
					log.info("用户"+upi.getName()+"保存联系人资料的链接");
					//为用户 延长session有效期
					RedisUtils.setExpireln(userKey,Long.parseLong(redisuserkeyexpir));
					return true;
				}else if(saveLinkInfoUrl.indexOf("toUserLinkMan")!=-1&&saveLinkInfoUrl.indexOf("memberAuth")!=-1){
					log.info("用户"+upi.getName()+"跳转到注册联系人的链接");
					//为用户 延长session有效期
					RedisUtils.setExpireln(userKey,Long.parseLong(redisuserkeyexpir));
					return true;
				}else{
					log.info("用户"+upi.getName()+"首次登陆的链接");
					if(HttpRequestDeviceUtils.isMobileDevice(request)){
						response.sendRedirect(baseurl+"memberAuth/toUserLinkMan.do");
					}else{
						response.sendRedirect(baseurl+"memberAuth/toUserLinkMan.do");
					}
					return false;
				}
			}else{
				//为用户 延长session有效期
				RedisUtils.setExpireln(userKey,Long.parseLong(redisuserkeyexpir));
				log.debug("用户"+upi.getName()+"存在 放行");
				return true;
			}
		}else{
			//为用户 延长session有效期
			RedisUtils.setExpireln(userKey,Long.parseLong(redisuserkeyexpir));
			log.debug("用户"+upi.getName()+"存在 放行");
			return true;
		}
	}
	
	public void rememberPath(HttpServletRequest request,HttpServletResponse response){
		String pathtrail=request.getServletPath();
		String pathparam=request.getQueryString();
		
		if(urltrailurls.contains(pathtrail)){
			//记录轨迹
//			String useridcard=RedisUserSession.getUserKey(cookieusertrailidcard, request);
			String tempKey=RandomStr32.getStr32()+RandomStr32.getStr32();
			Cookie cookie=new Cookie(cookieusertrailidcard, tempKey);
			cookie.setMaxAge(Integer.parseInt(usertrailidcardexpir));
			cookie.setPath("/");
			response.addCookie(cookie);
			RedisResult<String> rrt;
			if(StringUtils.isNotEmpty(pathparam)){
				rrt=(RedisResult<String>) RedisUtils.set(tempKey,pathtrail+"?"+pathparam, Long.parseLong(redisusertrailidcardexpir));
			}else{
				rrt=(RedisResult<String>) RedisUtils.set(tempKey,pathtrail, Long.parseLong(redisusertrailidcardexpir));
			}
		}
		
	}
	
	
}
