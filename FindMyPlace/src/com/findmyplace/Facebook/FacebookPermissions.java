package com.findmyplace.Facebook;

import java.util.ArrayList;
import java.util.List;


public class FacebookPermissions{
	private List<String> readPermissions;
	private List<String> publishPermissions;

	public List<String> getPermissions(){
		ArrayList<String> permissions = new ArrayList<String>();
		if(publishPermissions!= null){
			permissions.addAll(publishPermissions);
		}
		if(readPermissions!= null){
			permissions.addAll(readPermissions);
		}

		return permissions;
	}

	public List<String> getReadPermissions() {
		return readPermissions;
	}
	public void setReadPermissions(List<String> readPermissions) {
		this.readPermissions = readPermissions;
	}
	public List<String> getPublishPermissions() {
		return publishPermissions;
	}
	public void setPublishPermissions(List<String> publishPermissions) {
		this.publishPermissions = publishPermissions;
	}


}