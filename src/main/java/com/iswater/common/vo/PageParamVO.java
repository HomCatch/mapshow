package com.iswater.common.vo;

public class PageParamVO {

	private int pagesize = 10;
	private int pageCurrent = 1;

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public int getStartrow() {
		return (pageCurrent - 1) * pagesize;
	}

	public int getPageCurrent() {
		return pageCurrent;
	}

	public void setPageCurrent(int pageCurrent) {
		this.pageCurrent = pageCurrent;
	}
}
