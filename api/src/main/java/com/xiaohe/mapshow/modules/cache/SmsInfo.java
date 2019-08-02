package com.xiaohe.mapshow.modules.cache;

/**
 * 短信实体
 * @author Gmq
 * @since 2019-04-17 17:13
 **/
public class SmsInfo {

	String id;

	String verifiCode;

	public SmsInfo() {
	}

	public SmsInfo(String id, String verifiCode) {
		this.id = id;
		this.verifiCode = verifiCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVerifiCode() {
		return verifiCode;
	}

	public void setVerifiCode(String verifiCode) {
		this.verifiCode = verifiCode;
	}
}
