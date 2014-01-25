package com.cjk.thecloud.broadcastreceivers;

import java.io.IOException;
import java.net.SocketTimeoutException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.cjk.thecloud.controllers.WifiController;

public class WifiBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.d("WifiReceiver", "onRecieve: Reached");
		
		boolean connected = this.isConnected(context,intent);
		int strength = 0;
		boolean internetAvailable = false;
		
		if (connected) {
			strength = getConnectionStrength(context,intent);
			internetAvailable = isInternetAvailable(context, intent);
		}
		
		WifiController controller = WifiController.getInstance();
		controller.setConnectivity(connected, strength, internetAvailable);
	}
	
	public boolean isConnected(Context context, Intent intent) {
		ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE); 
        NetworkInfo netInfo = conMan.getActiveNetworkInfo();
        if (netInfo != null && netInfo.getType() == ConnectivityManager.TYPE_WIFI) {	
            Log.d("WifiReceiver", "Have Wifi Connection");
        	return true;
        }
        else {
            Log.d("WifiReceiver", "Don't have Wifi Connection");   
            return false;
        }
	}
	
	public int getConnectionStrength(Context context, Intent intent) {
		//Get Wifi Service
		int numberOfLevels = 5;
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		int level=WifiManager.calculateSignalLevel(wifiInfo.getRssi(), numberOfLevels);
		Log.d("WifiReceiver", "getInternetStrength: " + level);
	    return level;
	}
	
	public boolean isInternetAvailable(Context context, Intent intent) {
		ConnectivityManager connectivityManager;
		boolean connected = false;
		try {
            connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
	        connected = networkInfo != null && networkInfo.isAvailable() &&
	                networkInfo.isConnected();
	        return connected;
        } catch (Exception e) {
        	Log.d("WifiReciever","You do not have internet access");
            return false;    
        }
	}

}
