package com.sanbang.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.lang.time.DateFormatUtils;

import com.alibaba.fastjson.JSONObject;
import com.sanbang.vo.CertSignInfoBean;

/**
 * 链接erp商品实际库存量
 * 
 * @author lianfu
 *
 */
public class StockHelper {
	private static String JDriver = null;
	//private static String url = "jdbc:sqlserver://39.107.205.12:1433;DatabaseName=SD_EDS_DATAS_CS";
	//private static String url = "jdbc:sqlserver://39.107.205.12:1433;DatabaseName=UFDATA_001_2018";
	private static String url = null;
	private static String user = null;
	private static String psw = null;
	
	static {
		try {
			
			Properties properties = new Properties();
			// 使用ClassLoader加载properties配置文件生成对应的输入流
			InputStream in = StockHelper.class.getClassLoader().getResourceAsStream("jdbc.properties");
			// 使用properties对象加载输入流 /sanbang-front/src/main/resources/jdbc.properties
			properties.load(in);
			//获取key对应的value值
			JDriver = properties.getProperty("SqlServer.driverClass");
			url = properties.getProperty("SqlServer.url");
			user = properties.getProperty("SqlServer.username");
			psw = properties.getProperty("SqlServer.password");
			Class.forName(JDriver);
		} catch (Exception e) {
			System.out.println("数据库驱动加载异常");
			e.printStackTrace();
		}
	}

	/**
	 * 获取数据库链接
	 * 
	 * @return
	 */
	public static Connection getConnection() {
		try {
			Connection connm = (Connection) DriverManager.getConnection(url, user, psw);
			return connm;
		} catch (Exception e) {
			System.out.println("数据链接出现异常！");
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 关闭数据库
	 * 
	 * @param connm
	 * @param pstmt
	 * @param rs
	 */
	public static void closeResources(Connection connm, Statement pstmt, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connm != null) {
				try {
					connm.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					connm = null;
				}
			}
		}
	}

	public static JSONObject getStock(String goodNo, String strockType) {
		// 01样品库存，02商品库存
		Connection connm = null;
		Statement pstmt = null;
		ResultSet rs = null;
		JSONObject object = null;
		try {

			StringBuffer sqlBuffer = new StringBuffer(
					"select cInvAddCode,cInvCode,cInvName,cWhName,cWhAddress,iQuantity,cWhCode, ");
			sqlBuffer.append("(select cDCName from DistrictClass where cDCCode=cProvince)as Province, ");
			sqlBuffer.append("(select cDCName from DistrictClass where cDCCode=cCity)as City,");
			sqlBuffer.append("(select cDCName from DistrictClass where cDCCode=cCounty)as County ");
			sqlBuffer.append("from(select b.cInvAddCode,a.cInvCode,b.cInvName,c.cWhCode,c.cWhName,c.cWhAddress,sum(a.iQuantity) as iQuantity,cProvince,cCity,cCounty ");
			sqlBuffer.append("from CurrentStock a left join  inventory b on a.cInvCode=b.cInvCode left join  Warehouse  c on a.cwhcode=c.cWhCode ");
			sqlBuffer.append("where SUBSTRING(c.cWhCode,3,2)='"+strockType+"' and b.cInvAddCode ='"+goodNo+"'  group by b.cInvAddCode,a.cInvCode,b.cInvName,c.cWhCode,c.cWhName,c.cWhAddress,cProvince,cCity,cCounty)a");
			//sqlBuffer.append(" group by b.cInvAddCode,a.cInvCode,b.cInvName,c.cWhCode,c.cWhName,c.cWhAddress,cProvince,cCity,cCounty)a");
			connm = getConnection();
			pstmt = connm.createStatement();
			rs = pstmt.executeQuery(sqlBuffer.toString());
			while (rs.next()) {
				object = new JSONObject();
				object.put("cInvAddCode", rs.getString("cInvAddCode"));
				object.put("cInvCode", rs.getString("cInvCode"));
				object.put("cInvName", rs.getString("cInvName"));
				object.put("cWhName", rs.getString("cWhName"));
				object.put("cWhAddress", rs.getString("cWhAddress"));
				object.put("iQuantity", rs.getString("iQuantity"));
				object.put("cWhCode", rs.getString("cWhCode"));
				break;
				//System.out.println(object);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			StockHelper.closeResources(connm, pstmt, rs);
		}
		return object;
	}
	
	/**
	 * 得到自营库存
	 * @return
	 */
	public  static  List<Map<String, Object>> getSelfListForSqlServer(String goodsNums) {
		// 01样品库存，02商品库存
			Connection connm = null;
			Statement pstmt = null;
			ResultSet rs = null;
			List<Map<String, Object>> list = null;
		try {
			String sql="SELECT cInvAddCode,a.cInvCode,b.cInvName,sum(a.iQuantity) AS iQuantity	FROM CurrentStock a"
					+ "  LEFT JOIN inventory b ON a.cInvCode = b.cInvCode LEFT JOIN Warehouse c ON "
					+ "a.cwhcode = c.cWhCode WHERE  SUBSTRING(c.cWhCode, 3, 2) = '02'  AND b.cInvAddCode"
					+ " in ("+goodsNums+")  GROUP BY b.cInvAddCode, a.cInvCode,b.cInvName,c.cWhCode,c.cWhName,c.cWhAddress";
			System.out.println(sql);
			connm = getConnection();
			pstmt = connm.createStatement();
			rs = pstmt.executeQuery(sql);
			list=convertList(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	private static List<Map<String, Object>> convertList(ResultSet rs) throws SQLException{
		List<Map<String, Object>> list = new ArrayList<>();
		ResultSetMetaData md = rs.getMetaData();//获取键名
		int columnCount = md.getColumnCount();//获取行的数量
		while (rs.next()) {
			Map<String, Object> rowData = new HashMap<>();
			for (int i = 1; i <= columnCount; i++) {
			rowData.put(md.getColumnName(i), rs.getObject(i));//获取键名及值
			}
			list.add(rowData);
		}
		return list;
		}

	public static double getStockNum(String goodno, String strockType) {
		double stockNum = 0.0;
		Connection connm = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			StringBuffer sqlBuffer = new StringBuffer("select cInvAddCode,cInvCode,cInvName,cWhName,cWhAddress,iQuantity,cWhCode, ");
			sqlBuffer.append("(select cDCName from DistrictClass where cDCCode=cProvince)as Province, ");
			sqlBuffer.append("(select cDCName from DistrictClass where cDCCode=cCity)as City,");
			sqlBuffer.append("(select cDCName from DistrictClass where cDCCode=cCounty)as County ");
			sqlBuffer.append("from(select b.cInvAddCode,a.cInvCode,b.cInvName,c.cWhCode,c.cWhName,c.cWhAddress,sum(a.iQuantity) as iQuantity,cProvince,cCity,cCounty ");
			sqlBuffer.append("from CurrentStock a left join  inventory b on a.cInvCode=b.cInvCode left join  Warehouse  c on a.cwhcode=c.cWhCode ");
			sqlBuffer.append("where SUBSTRING(c.cWhCode,3,2)='"+strockType+"' and b.cInvAddCode ='"+goodno+"'  group by b.cInvAddCode,a.cInvCode,b.cInvName,c.cWhCode,c.cWhName,c.cWhAddress,cProvince,cCity,cCounty)a");
			connm = getConnection();
			pstmt = (PreparedStatement) connm.prepareStatement(sqlBuffer.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				stockNum = CommUtil.null2Double(rs.getString("iQuantity"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stockNum;
	}
	
	
	public static void main(String[] args) {/*
		System.out.println("访问U8库存");
		JSONObject object = StockHelper.getStock("GE17002110","02");
		if (object != null) {
			// 现有库存量
			//System.out.println(object);
		}else{
			System.out.println(object);
			System.out.println("结果集为NULL");
		}
		
	*/
		List<Map<String, Object>> list=getSelfListForSqlServer("'ZGE17000511'");
	for (Map<String, Object> map : list) {
		for (Entry<String, Object> map2 : map.entrySet()) {
			System.out.println(map2.getKey()+map2.getValue());
		}
	}
	}
}
