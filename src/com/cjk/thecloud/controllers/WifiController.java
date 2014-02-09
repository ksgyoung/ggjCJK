package com.cjk.thecloud.controllers;

public class WifiController {
	
	public boolean isInternetConnected = false;
	public int connectionStrength = 0;
	public boolean isFirstConnected = false;
	public boolean isConnected = false;

	public void setConnectivity(boolean connected, int strength, boolean internetAccess) {
		this.connectionStrength = strength;
		this.isInternetConnected = internetAccess;
		this.isConnected = connected;
	}
	
	private static WifiController instance = null;
	
	private WifiController() {
		
	}
	
	public static WifiController getInstance() {
		 if(instance == null) {
	         instance = new WifiController();
	      }
	      return instance;
	}
	
	public double getConnectionStrength() {
		return this.connectionStrength;
	}
	
	public boolean isInternetConnected() {
		return this.isInternetConnected;
	}

	
	public boolean isFirstConnect() {
		return false;
	}

}
