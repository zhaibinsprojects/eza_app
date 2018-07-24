//package com.sanbang.userpro.service.impl;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.http.Header;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.message.BasicHeader;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//
//import com.alibaba.fastjson.JSONObject;
//import com.sanbang.userpro.service.HunXinService;
//import com.sanbang.utils.httpclient.HttpRemoteRequestUtils;
//
//
///**
// * 环信业务相关
// * 
// *  
// * 2017年7月20日
// */
//@Service("hunXinService")
//public class HunXinServiceImpl implements HunXinService{
//	
//	private static Logger log = Logger.getLogger(HunXinServiceImpl.class.getName());
//	
//	private static String huanxintoken=null;
//	
//	private static String huanxintokenexpires_in=null;
//	
//	public static String passwordefault="ezaishengtohuanxinpas";
//	
//	private String client_id="YXA6WXg70GtaEeeUHgEm8LZoEQ";
//	
//	private String client_secret="YXA6h9u6_INjaBFXNFp05Gc0cB0MNZ8";
//	
//	private String grant_type="client_credentials";
//	
//	private String hxbaseuri="https://a1.easemob.com/1140170718115940/ezaisheng/";
//
//	@Autowired
//	UserProDao userProDao;
//	
//	@Override
//	public void queryHuanxinToken(){
//		
//		Map<String,Object> map=new HashMap<>();
//		map.put("grant_type", grant_type);
//		map.put("client_id", client_id);
//		map.put("client_secret", client_secret);
//		JSONObject jsonobj=(JSONObject) JSONObject.toJSON(map);
//		net.sf.json.JSONObject jsonobjstr=null;
//		try {
//			
//			jsonobjstr=HttpRemoteRequestUtils.doPostJsonToString(hxbaseuri+"token", jsonobj,null);
//			huanxintoken=jsonobjstr.getString("access_token");
//			huanxintokenexpires_in=jsonobjstr.getString("expires_in");
//			log.info("请求环信token结果:"+jsonobjstr.toString());
//		} catch (ClientProtocolException e) {
//			log.info("请求环信token结果失败"+e.toString());
//			e.printStackTrace();
//		} catch (IOException e) {
//			log.info("请求环信token结果失败"+e.toString());
//			e.printStackTrace();
//		}
//		
//	}
//	/**
//	 * 查询环信用户 
//	 * @param username
//	 * @param times
//	 * @return
//	 */
//	public net.sf.json.JSONObject queryHuanxinUserInfo(String username,String flag){
//		int timetem=0;
//		return queryHuanxinUserInfoChild(username,timetem,flag);
//	}
//	/**
//	 * 查询环信用户 
//	 * @param username
//	 * @param times
//	 * @return
//	 */
//	public net.sf.json.JSONObject queryHuanxinUserInfoChild(String username,int times,String flag){
//		Map<String,Object> map=new HashMap<>();
//		JSONObject jsonobj=(JSONObject) JSONObject.toJSON(map);
//		net.sf.json.JSONObject jsonobjstr=null;
//		try {
//			Header header=new BasicHeader("Authorization", "Bearer "+huanxintoken);
//			String tourl=hxbaseuri+"users/"+username;
//			if(flag!=null){
//				tourl=tourl+"/"+flag;
//			}
//			jsonobjstr=HttpRemoteRequestUtils.doGetJsonToString(tourl, jsonobj,header);
//			if(jsonobjstr!=null&&jsonobjstr.getInt("httpstatus")==200){
//				//成功
//				log.info("查询结果："+jsonobjstr);
//				return jsonobjstr;
//			}else if(jsonobjstr!=null&&jsonobjstr.getInt("httpstatus")==401){
//				queryHuanxinToken();
//				if(times<=0){
//					queryHuanxinUserInfoChild(username,++times,flag);
//				}
//			}else if(jsonobjstr!=null&&jsonobjstr.getInt("httpstatus")==404){
//				return null;
//			}
//			log.info("请求环信用户信息结果:"+jsonobjstr.toString());
//		} catch (ClientProtocolException e) {
//			log.info("请求环信用户信息结果失败"+e.toString());
//			e.printStackTrace();
//		} catch (IOException e) {
//			log.info("请求环信用户信息结果失败"+e.toString());
//			e.printStackTrace();
//		}
//		return null;
//	}
//	
//	/**
//	 * 异步请求数据
//	 */
//	@Override
//	@Async
//	public net.sf.json.JSONObject regHuanxinSingle(String username,String password,Long userid){
//		return regHuanxinSingleInterval(username, password, 0,userid);
//	}
//	
//	public net.sf.json.JSONObject regHuanxinSingleInterval(String username,String password,int timetem,Long userid){
//		Map<String,Object> map=new HashMap<>();
//		map.put("username", username);
//		map.put("password", password);
//		JSONObject jsonobj=(JSONObject) JSONObject.toJSON(map);
//		net.sf.json.JSONObject jsonobjstr=null;
//		try {
////			queryHuanxinToken();
//			Header header=new BasicHeader("Authorization", "Bearer "+huanxintoken);
//			jsonobjstr=HttpRemoteRequestUtils.doPostJsonToString(hxbaseuri+"users", jsonobj,header);
//			if(jsonobjstr!=null&&jsonobjstr.getInt("httpstatus")==200){
//				//成功
//				if(userid!=null){
//					net.sf.json.JSONObject jsonobjobj=null;
//					net.sf.json.JSONArray jsonarr=null;
//					if(jsonobj!=null&&jsonobjstr.has("entities")){
//						jsonarr=jsonobjstr.getJSONArray("entities");
//						jsonobjobj=(net.sf.json.JSONObject) jsonarr.get(0);
//					}
//					String account=null;
//					if(jsonobjobj!=null&&jsonobjobj.has("uuid")){
//						account=jsonobjobj.getString("uuid");
//					}
//					List<UserProInfo> userlistback=new ArrayList<>();
//					//单个注册
//					UserProInfo upif=new UserProInfo();
//					upif.setUserid(userid);
////					upif.setUsername(userProInfo.getUsername());
//					upif.setAccount(account);
//					userlistback.add(upif);
//					if(userProDao!=null){
//						userProDao.updateUserYingYe(userlistback);
//						log.info("更新用户环信id");
//					}
//				}
//			}else if(jsonobjstr!=null&&jsonobjstr.getInt("httpstatus")==401){
//				//token 失效
//				queryHuanxinToken();
//				if(timetem<=1){
//					regHuanxinSingleInterval(username, password,++timetem,userid);
//				}
//			}else if(jsonobjstr!=null&&jsonobjstr.getInt("httpstatus")==400){
//				//可能注册重复
//				//获取用户信息 保存
//				jsonobjstr=queryHuanxinUserInfo(username, null);
//				if(jsonobjstr!=null&&jsonobjstr.getInt("httpstatus")==200){
//					//成功
//					if(userid!=null){
//						net.sf.json.JSONObject jsonobjobj=null;
//						net.sf.json.JSONArray jsonarr=null;
//						if(jsonobj!=null&&jsonobjstr.has("entities")){
//							jsonarr=jsonobjstr.getJSONArray("entities");
//							jsonobjobj=(net.sf.json.JSONObject) jsonarr.get(0);
//						}
//						String account=null;
//						if(jsonobjobj!=null&&jsonobjobj.has("uuid")){
//							account=jsonobjobj.getString("uuid");
//						}
//						List<UserProInfo> userlistback=new ArrayList<>();
//						//单个注册
//						UserProInfo upif=new UserProInfo();
//						upif.setUserid(userid);
////						upif.setUsername(userProInfo.getUsername());
//						upif.setAccount(account);
//						userlistback.add(upif);
//						if(userProDao!=null){
//							userProDao.updateUserYingYe(userlistback);
//							log.info("更新用户环信id");
//						}
//					}
//					if(jsonobjstr!=null)
//						log.info("请求环信查询重复注册结果:"+jsonobjstr.toString());
//					return jsonobjstr;
//				}
//			}
//			if(jsonobjstr!=null)
//			log.info("请求环信单个注册结果:"+jsonobjstr.toString());
//		} catch (ClientProtocolException e) {
//			log.info("请求环信单个注册失败"+e.toString());
//			e.printStackTrace();
//		} catch (IOException e) {
//			log.info("请求环信单个注册失败"+e.toString());
//			e.printStackTrace();
//		}
//		
//		return jsonobjstr;
//	}
//	/*
//	 * 同步请求
//	 */
//	@Override
//	public net.sf.json.JSONObject regHuanxinSinglesyn(String username,String password){
//		return regHuanxinSingleInterval(username, password, 0,null);
//		
//	}
//	
//	@Override
//	@Async
//	public void handlMemberData(){
//		String pageNo="0";
//		Short pageSize=40;
//		UserProInfo userinfo=new UserProInfo();
////		userinfo.setStatus((short) 4);
//		userinfo.setGusersex(pageSize);
////		userinfo.setGroupid((short) 6);
//		String licensemark="";
//		int totals=0;
//		int totale=0;
//		while(true){
//			//取出数据，直到为空
//			userinfo.setGuser(pageNo);
//			List<UserProInfo> userlist=userProDao.queryusers(userinfo);
//			List<UserProInfo> userlistback=new ArrayList<>();
//			if(userlist.size()>0){
//				try {
//					
//					for(UserProInfo userProInfo:userlist){
//						System.out.println(userProInfo.getUserid());
//						net.sf.json.JSONObject jsonobj=this.regHuanxinSinglesyn(userProInfo.getUsername(), HunXinServiceImpl.passwordefault);
//						net.sf.json.JSONObject jsonobjobj=null;
//						net.sf.json.JSONArray jsonarr=null;
//						if(jsonobj!=null&&jsonobj.has("entities")){
//							jsonarr=jsonobj.getJSONArray("entities");
//							jsonobjobj=(net.sf.json.JSONObject) jsonarr.get(0);
//						}
//						String account=null;
//						if(jsonobj==null||!jsonobj.getString("httpstatus").equals("200")){
//							continue;
//						}
//						if(jsonobjobj!=null&&jsonobjobj.has("uuid")){
//							account=jsonobjobj.getString("uuid");
//						}
////					try {
//						UserProInfo upif=new UserProInfo();
//						upif.setUserid(userProInfo.getUserid());
////						upif.setUsername(userProInfo.getUsername());
//						upif.setAccount(account);
//						userlistback.add(upif);
////						
////					} catch (Exception e) {
////						System.out.println("异常发生，"+userProInfo.getUserid()+"更新用户环信id失败");
////						totale=totale+1;
////					}
//					}
//					int tempa=0;
//					if(userlistback.size()!=0){
//						
//						tempa=userProDao.updateUserYingYe(userlistback);
//						log.info("更新用户环信id");
//					}
//					totals=totals+tempa;
//					System.out.println("成功处理"+totals+"条");
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}else{
//				System.out.println("处理完毕");
//				System.out.println("成功处理"+totals+"条");
//				System.out.println("失败"+totale+"条");
//				break;
//			}
//			
//			pageNo=String.valueOf(Integer.parseInt(pageNo)+pageSize);
//		}
//	}
//	public static void main(String[] args) {
//		HunXinServiceImpl hxsi=new HunXinServiceImpl();
////		hxsi.regHuanxinSingle("w0007",passwordefault,23011l);
//		hxsi.queryHuanxinUserInfo("1234567890",null);
//	}
//}
