package com.poscodx.jblog.vo;

public class PostVo {
	private long no;
	private String title;
	private String contents;
	private String categoryNo;
	private String regDate;
	
	public long getNo() {
		return no;
	}
	public void setNo(long no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getCategoryNo() {
		return categoryNo;
	}
	public void setCategoryNo(String categoryNo) {
		this.categoryNo = categoryNo;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	
	@Override
	public String toString() {
		return "PostVo [no=" + no + ", title=" + title + ", contents=" + contents + ", categoryNo=" + categoryNo
				+ ", regDate=" + regDate + "]";
	}
}
