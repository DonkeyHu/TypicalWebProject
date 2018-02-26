package com.software.job.util;

import java.util.List;


@SuppressWarnings("rawtypes")
public class PageNation {
	//传参数或指定
	private int pageNum; //当前页号数
	private int size; //页面显示记录的数目
	
	//数据库查找出来的数据
	private int rowCount; //数据库所有的记录
	private List list;
	
	private int pageCount; //页号总数
	private int startRow; //当前页面开始行
	private int first; //第一页 页号
	private int last; //最后一页 页号
	private int next; //后一页 页号
	private int previous; //前一页 页号
	private int startNav; //页号式导航 起始页号
	private int endNav; //页号式导航 结束页号
	private int navCount; //页号式导航，最多显示的页号数
	public PageNation(String pageNum, int size, int rowCount) {
		this.pageNum = (pageNum==null)?1:(Integer.parseInt(pageNum));
		this.size = size;
		this.rowCount = rowCount;
		
		this.pageCount=(int) Math.ceil((double)rowCount/size);
		this.pageNum=Math.min(this.pageNum, pageCount);
		this.pageNum=Math.max(1, this.pageNum);
	//	this.startRow=(this.pageNum-1)*10+1;
		this.startRow=(this.pageNum-1)*10;
		this.first=1;
		this.last=this.pageCount;
		this.previous=Math.max(1, this.pageNum-1);
		this.next=Math.min(this.pageCount, this.pageNum+1);
		
		//导航处理
		this.navCount=10;
		this.startNav=((this.pageNum-this.navCount/2)<1)?1:this.pageNum-this.navCount/2;
		this.endNav=startNav+navCount;
		this.endNav=Math.min(this.endNav, this.pageCount);
	}
	
	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getStartRow() {
		return startRow;
	}
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	public int getFirst() {
		return first;
	}
	public void setFirst(int first) {
		this.first = first;
	}
	public int getLast() {
		return last;
	}
	public void setLast(int last) {
		this.last = last;
	}
	public int getNext() {
		return next;
	}
	public void setNext(int next) {
		this.next = next;
	}
	public int getPrevious() {
		return previous;
	}
	public void setPrevious(int previous) {
		this.previous = previous;
	}
	public int getStartNav() {
		return startNav;
	}
	public void setStartNav(int startNav) {
		this.startNav = startNav;
	}
	public int getEndNav() {
		return endNav;
	}
	public void setEndNav(int endNav) {
		this.endNav = endNav;
	}
	public int getNavCount() {
		return navCount;
	}
	public void setNavCount(int navCount) {
		this.navCount = navCount;
	}
}
