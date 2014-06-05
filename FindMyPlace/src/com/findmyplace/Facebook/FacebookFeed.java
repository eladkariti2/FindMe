package com.findmyplace.Facebook;

import java.util.Map;

public class FacebookFeed {

	String name;
	String caption;
	String description;
	String picture;
	String link;
	String to;


	public FacebookFeed(Map<String, Object> data){
		to = (String) data.get("to");
		name = (String) data.get("fb-name");
		caption = (String) data.get("fb-caption");
		description = (String) data.get("fb-description");
		picture = (String) data.get("fb-picture-url");
		link = (String) data.get("fb-link");

	}

	public FacebookFeed(String name,String description,String caption,String picture,String link){
		this(name,description,caption,picture,link,null);
	}

	public FacebookFeed(String name,String description,String caption,String picture,String link, String to){
		this.to = to;
		this.name = name;
		this.caption = caption;
		this.description = description;
		this.picture = picture;
		this.link = link;
	}

	public String getName() {
		return name;
	}
	public String getCaption() {
		return caption;
	}
	public String getDescription() {
		return description;
	}
	public String getPicture() {
		return picture;
	}
	public String getLink() {
		return link;
	}
	public String getTo() {
		return to;
	}

}
