package com.iswater.common.vo;


import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PageVO {

	private int total;

	private int pageCurrent = 1;

	private int pageCount = 1;

	private static LinkedList<Integer> nums = new LinkedList<Integer>();

	private List<?> list;

	static {
		for (int i = 1; i <= 10; i++) {
			nums.add(i);
		}
	}

	public PageVO() {

	}

	public PageVO(int pageCurrent, int total) {
		this.pageCurrent = pageCurrent;
		this.total = total;
	}

	public PageVO(int pageCurrent, int total, List<?> list) {
		this.pageCurrent = pageCurrent;
		this.total = total;
		this.list = list;
	}

	public PageVO(int pageCurrent, int total, int pageCount, List<?> list) {
		this.pageCurrent = pageCurrent;
		this.total = total;
		this.pageCount = pageCount;
		this.list = list;
	}

	public PageVO(int pageCurrent) {
		this.pageCurrent = pageCurrent;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPageCurrent() {
		return pageCurrent;
	}

	public void setPageCurrent(int pageCurrent) {
		this.pageCurrent = pageCurrent;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public LinkedList<Integer> getNums() {
		if (nums.size() > this.pageCount) {
			nums.clear();
			for (int i = 1; i <= this.pageCount; i++) {
				nums.add(i);
			}
		}
		if (this.pageCurrent == 1) {
			nums.clear();
			int count = 10;
			if (this.pageCount < count) {
				count = this.pageCount;
			}
			for (int i = 1; i <= count; i++) {
				nums.add(i);
			}
		} else if (this.pageCurrent > 1 && this.pageCurrent < this.pageCount) {
			if (this.pageCurrent == nums.getLast()) {
				nums.removeFirst();
				nums.add(this.pageCurrent + 1);
			} else if (this.pageCurrent == nums.getFirst()) {
				nums.removeLast();
				nums.add(0, this.pageCurrent - 1);
			}
		} else if (this.pageCurrent == this.pageCount) {
			nums.clear();
			int count = 1;
			if (this.pageCount > 10) {
				count = this.pageCount - 9;
			}
			for (int i = this.pageCount; i >= count; i--) {
				nums.add(i);
				Collections.sort(nums);
			}
		}
		return nums;
	}
}
