package com.xiaohe.mapshow.utils;

import com.xiaohe.mapshow.config.ApplicationContextUtils;
import com.xiaohe.mapshow.modules.dbinfo.entity.DbInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

/**
 * @author Gmq
 * @since 2019-07-09 16:26
 **/
public class SqlUtil {
	private static Logger log = LoggerFactory.getLogger(SqlUtil.class);

	private static String MYSQLDRIVER = "com.mysql.jdbc.Driver";
	//去掉mysql警告信息
	private static String PARAMETER = "?useUnicode=true&characterEncoding=utf-8&useSSL=false";


	public static void main(String[] args) {
		for (int i = 0; i < 1; i++) {
			String database = "testdb"+i;
			DbInfo dbInfo = new DbInfo("iotsvr.he-live.com", "3309", "root", "iswater", "mysql");
			dbInfo.setDbName(database);
			getConn(dbInfo);
		}
	}

	public static void getConn(DbInfo info) {

		Connection conn;
		Connection newConn;
		try {
			Class.forName(MYSQLDRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			String dbName = info.getDbName();
			String username = info.getUsername();
			String password = info.getPassword();
			String ip = info.getIp();
			String port = ":" + info.getPort();
			String databaseSql = "create database " + dbName;
			String url = "jdbc:mysql://" + ip + port + "/";
			conn = DriverManager.getConnection(url + "mysql" + PARAMETER, username, password);
			Statement smt = conn.createStatement();
			smt.executeUpdate(databaseSql);
			log.info("创建库成功成功！等待执行sql脚本...");
			//执行shell脚本
			boolean callScript = callScript("create_db.sh", dbName, "/opt/jssaas/shell");
			if(callScript){
				log.info("sql脚本执行成功！");
			}
			conn.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}

	private static boolean callScript(String script, String dbName, String workspace) {
		Boolean flag=false;
		try {
			RestTemplate restTemplate = ApplicationContextUtils.getRestTemplate();
			flag= restTemplate.getForObject("http://iotsvr.he-live.com:9194/jssaasBb/runShell?script=" + script + "&dbName=" + dbName + "&workspace=" + workspace, Boolean.class);
			if(flag==null){
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
}
