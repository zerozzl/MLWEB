package com.zerozzl.mlweb.common.paging;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class PagedBean implements Serializable {

	private static final long serialVersionUID = 8081251758368320976L;
	private int pageNo; // 页码
	private int pageSize; // 每页显示数量, 默认20
	private String sortColumn; // 排序字段
	private int sortOrder; // 排序方式: 0:DESC, 1:ASC
	private int pagesDisplayed; // 页面显示的以当前页为中间，左右两边加起来的显示的总页数, 默认5

	public PagedBean() {
		this(1);
	}

	public PagedBean(int page) {
		this(page, 20);
	}

	public PagedBean(int page, int pagesize) {
		this(page, pagesize, null, 0, 5);
	}
	
	public PagedBean(int page, int pagesize, String sortCol, int sortOrd) {
		this(page, pagesize, sortCol, sortOrd, 5);
	}
	
	public PagedBean(int page, int pagesize, String sortcolumn, int sortorder, int pagedisplay) {
		setPageNo(page);
		setPageSize(pagesize);
		setSortColumn(sortcolumn);
		setSortOrder(sortorder);
		setPagesDisplayed(pagedisplay);
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize > 0 ? pageSize : 20;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = StringUtils.isNotBlank(sortColumn) ? sortColumn.trim() : "";
	}

	public int getSortOrder() {
		return sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		if(sortOrder == 0 || sortOrder == 1) {
			this.sortOrder = sortOrder;
		} else {
			this.sortOrder = 0;
		}
	}

	public int getPagesDisplayed() {
		return pagesDisplayed;
	}

	public void setPagesDisplayed(int pagesDisplayed) {
		this.pagesDisplayed = pagesDisplayed > 0 ? pagesDisplayed : 5;
	}

	public int getFirstResult() {
		int firstResult = 0;
		if (pageNo > 0) {
			firstResult = (pageNo - 1) * pageSize;
		}
		return firstResult;
	}
	
	public boolean doPaged() {
		return this.pageNo > 0;
	}
	
}
