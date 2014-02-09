package com.cjk.thecloud.broadcastreceivers;

import java.util.Set;

import com.cjk.thecloud.controllers.BattleController;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


public class BluetoothBroadcastReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		String actionString = intent.getAction();
		Log.d("BluetoothBroadcastReceiver", "ActionString: " + actionString);
		
		if (actionString.equals(BluetoothDevice.ACTION_ACL_CONNECTED)) {
			BattleController.getInstance().startBattleActivityNoBluetooth(context, "BroadcastReceiver");
		}
		
//		if(actionString.equals(BluetoothDevice.ACTION_ACL_CONNECTED)) {
//			Log.d("BluetoothBroadcastReceiver", "Connected to bluetooth");
//			
//			Set<BluetoothDevice> pairedDevices = BluetoothUtils.getInstance().getPairedDevices();
//			
//			if(pairedDevices.size() > 0) {
//				Log.d("BluetoothBroadcastReceiver", "paired with some device");
//				
//				if(BluetoothUtils.getInstance().initiatedConnection()) {
//					Toast.makeText(context, "I am a server", Toast.LENGTH_SHORT).show();
//					Log.d("BluetoothBroadcastReceiver", "I am a server");
////					BluetoothServer server = new BluetoothServer();
////					server.run();
//				} else {
//					Toast.makeText(context, "I am a client", Toast.LENGTH_SHORT).show();
//					Log.d("BluetoothBroadcastReceiver", "I am a client");
//					BluetoothClient client = new BluetoothClient(pairedDevices.iterator().next());
//					client.run();
//				}
//				
//			} else {
//				Log.d("BluetoothBroadcastReceiver", "not paired");
//			}
//			
//			Toast.makeText(context, "Connected to Bluetooth!!!", Toast.LENGTH_SHORT).show();
//		} else if(actionString.equals(BluetoothDevice.ACTION_FOUND)) {
//			Log.d("BluetoothBroadcastReceiver", "Found devices");
//			Toast.makeText(context, "Blutooth device found!", Toast.LENGTH_SHORT).show();
//		
//		}
	}

}
