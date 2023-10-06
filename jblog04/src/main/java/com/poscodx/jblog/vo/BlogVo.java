package com.poscodx.jblog.vo;

public class BlogVo {
	private String blogId;
	private String title;
	private String image;
	
	public String getBlogId() {
		return blogId;
	}
	public void setBlogId(String blogId) {
		this.blogId = blogId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	@Override
	public String toString() {
		return "BlogVo [blogId=" + blogId + ", title=" + title + ", image=" + image + "]";
	}
}
