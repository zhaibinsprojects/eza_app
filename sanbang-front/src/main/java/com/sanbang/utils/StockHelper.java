package com.sanbang.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.alibaba.fastjson.JSONObject;

/**
 * 链接erp商品实际库存量
 * 
 * @author lianfu
 *
 */
public class StockHelper {
	private static String JDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static String url = "jdbc:sqlserver://39.107.205.12:1433;DatabaseName=UFDATA_004_2018";
	private static String user = "sa";
	private static String psw = "123qweQWE";
	static {
		try {
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
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			StockHelper.closeResources(connm, pstmt, rs);
		}
		return object;
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
			//sqlBuffer.append("where SUBSTRING(c.cWhCode,3,2)='?' and b.cInvAddCode =?  group by b.cInvAddCode,a.cInvCode,b.cInvName,c.cWhCode,c.cWhName,c.cWhAddress,cProvince,cCity,cCounty)a");
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
	
	
	public static void main(String[] args) {
		System.err.println("访问U8库存");
		JSONObject object = StockHelper.getStock("01","02");
		if (object != null) {
			// 现有库存量
			System.out.println(object);
		}else{
			System.out.println("结果集为NULL");
		}
		
	}
}
