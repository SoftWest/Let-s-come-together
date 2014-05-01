package com.softwest.friendstogether.utils;

public class MenuNavDrawerItem {
	
	private String title;
	private int icon;
	private String count = "0";
	private boolean mCounterVisible = false;
	
	public MenuNavDrawerItem(){}

	public MenuNavDrawerItem(String title, int icon){
		this.title = title;
		this.icon = icon;
	}
	
	public MenuNavDrawerItem(String title, int icon, boolean mCounterVisible, String count){
		this.title = title;
		this.icon = icon;
		this.mCounterVisible = mCounterVisible;
		this.count = count;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public int getIcon(){
		return this.icon;
	}
	
	public String getCount(){
		return this.count;
	}
	
	public boolean getCounterVisibility(){
		return this.mCounterVisible;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public void setIcon(int icon){
		this.icon = icon;
	}
	
	public void setCount(String count){
		this.count = count;
	}
	
	public void setCounterVisibility(boolean mCounterVisible){
		this.mCounterVisible = mCounterVisible;
	}
}
