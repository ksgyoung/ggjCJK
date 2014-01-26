package com.cjk.thecloud.broadcastreceivers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;

import com.cjk.thecloud.MainActivity;
import com.cjk.thecloud.R;
import com.cjk.thecloud.controllers.BattleController;
import com.cjk.thecloud.controllers.WifiController;
import com.cjk.thecloud.game.GameCreator;
import com.cjk.thecloud.util.StartBattleDialogFragment;

public class WifiBroadcastReceiver extends BroadcastReceiver {
	public Context context;
	@Override
	public void onReceive(Context context, Intent intent) {
		this.context = context;
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
		
		if (connected  && BattleController.getInstance().battleStarted == false) {	
			BattleController.getInstance().battleStarted = true;
			Intent dialogOpen = new Intent(context,MainActivity.class);
			dialogOpen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			dialogOpen.putExtra("WifiConnected", true);
			context.startActivity(dialogOpen);
			
			//Get 
   			//GameCreator.createGame();
			//BattleController.getInstance().startBattleActivity(context,"Wild Wifi Jammer");
		}
	}

	public boolean isConnected(Context context, Intent intent) {
		ConnectivityManager conMan = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = conMan.getActiveNetworkInfo();
		if (netInfo != null
				&& netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
			Log.d("WifiReceiver", "Have Wifi Connection");
			return true;
		} else {
			Log.d("WifiReceiver", "Don't have Wifi Connection");
			return false;
		}
	}

	public int getConnectionStrength(Context context, Intent intent) {
		// Get Wifi Service
		int numberOfLevels = 5;
		WifiManager wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		int level = WifiManager.calculateSignalLevel(wifiInfo.getRssi(),
				numberOfLevels);
		Log.d("WifiReceiver", "getInternetStrength: " + level);
		return level;
	}

	public boolean isInternetAvailable(Context context, Intent intent) {
		ConnectivityManager connectivityManager;
		boolean connected = false;
		try {
			connectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connectivityManager
					.getActiveNetworkInfo();
			connected = networkInfo != null && networkInfo.isAvailable()
					&& networkInfo.isConnected();
			return connected;
		} catch (Exception e) {
			Log.d("WifiReciever", "You do not have internet access");
			return false;
		}
	}

}


