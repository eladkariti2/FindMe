package com.findmyplace.inferface;

public interface AsyncTaskListener<T> {
	
	public void handleException(Exception e);

	
	public void onTaskComplete(T result);
	
}
