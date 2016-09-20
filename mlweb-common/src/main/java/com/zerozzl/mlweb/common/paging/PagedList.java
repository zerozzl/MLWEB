package com.zerozzl.mlweb.common.paging;

import java.util.Collections;
import java.util.List;
import java.io.Serializable;

public class PagedList implements Serializable {

	private static final long serialVersionUID = -5229476144923962753L;
	private Number rowCount;
	private String pageCount;
	private String currentPage;
	private String backPage;
	private String fwdPage;
	private String pageSize;
	@SuppressWarnings("rawtypes")
	private List currentPageList;
	private String[] pagesDisplayed;
	private boolean empty;

	public PagedList() {
		this.setRowCount(new Integer(0));
		this.setCurrentPageList(Collections.EMPTY_LIST);
	}

	public Number getRowCount() {
		return rowCount;
	}

	public void setRowCount(Number rowCount) {
		this.rowCount = rowCount;
	}
	
	@SuppressWarnings("rawtypes")
	public List getCurrentPageList() {
		return currentPageList;
	}

	@SuppressWarnings("rawtypes")
	public void setCurrentPageList(List currentPageList) {
		this.currentPageList = currentPageList;
	}

	public String getPageCount() {
		return pageCount;
	}

	public void setPageCount(String pageCount) {
		this.pageCount = pageCount;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public String getBackPage() {
		return backPage;
	}

	public void setBackPage(String backPage) {
		this.backPage = backPage;
	}

	public String getFwdPage() {
		return fwdPage;
	}

	public void setFwdPage(String fwdPage) {
		this.fwdPage = fwdPage;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String[] getPagesDisplayed() {
		return pagesDisplayed;
	}

	public void setPagesDisplayed(String[] pagesDisplayed) {
		this.pagesDisplayed = pagesDisplayed;
	}

	public boolean isEmpty() {
		return empty;
	}

	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

}
