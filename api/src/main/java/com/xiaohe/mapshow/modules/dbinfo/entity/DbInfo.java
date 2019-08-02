package com.xiaohe.mapshow.modules.dbinfo.entity;

import java.io.Serializable;
import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import javax.persistence.Table;

/**
 * <p>
 *
 * </p>
 *
 * @author gmq
 * @since 2019-07-10
 */

@TableName("db_info")
public class DbInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId
	private Integer id;
	/**
	 * 数据库名称
	 */
	private String dbName;
	/**
	 * ip地址
	 */
	private String ip;
	/**
	 * 端口号
	 */
	private String port;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 数据库类型
	 */
	private String dbType;

	public DbInfo() {
	}

	public DbInfo(String ip, String port, String username, String password, String dbType) {
		this.ip = ip;
		this.port = port;
		this.username = username;
		this.password = password;
		this.dbType = dbType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}


}