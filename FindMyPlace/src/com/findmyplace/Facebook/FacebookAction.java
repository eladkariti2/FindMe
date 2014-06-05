package com.findmyplace.Facebook;

public class FacebookAction {

	private String name;
	private String link;
	private String description;
	private String caption;
	private String picture;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getCaption() {
		return caption;
	}
	
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	public String getPicture() {
		return picture;
	}
	
	public void setPicture(String url) {
		this.picture = url;
	}
}
