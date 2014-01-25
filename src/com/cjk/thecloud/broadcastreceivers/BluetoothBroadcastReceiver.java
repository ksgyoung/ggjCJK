package com.cjk.thecloud.broadcastreceivers;

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
		Log.d("BluetoothBroadcastReceiver", "Inside broadcast receiver");
		String actionString = intent.getAction();
		
		if(actionString.equals(BluetoothDevice.ACTION_ACL_CONNECTED)) {
			Log.d("BluetoothBroadcastReceiver", "Connected to bluetooth");
			Toast.makeText(context, "Connected to Bluetooth!!!", Toast.LENGTH_SHORT).show();
		} else if(actionString.equals(BluetoothDevice.ACTION_FOUND)) {
			Log.d("BluetoothBroadcastReceiver", "Found devices");
			Toast.makeText(context, "Blutooth device found!", Toast.LENGTH_SHORT).show();
		}
	}

}
