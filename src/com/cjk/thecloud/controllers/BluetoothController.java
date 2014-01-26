package com.cjk.thecloud.controllers;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.provider.Settings;

import com.cjk.thecloud.game.elements.Server;
import com.cjk.thecloud.util.BluetoothUtils;

public class BluetoothController {
	
	public Server getEnemy() {
		return new Server("bluetooth_id");
	}
	
	public Intent startBluetoothSettingsActivity() {
		return new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
	}
	
	public Intent getBluetoothConnectIntent() {
		return new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	};
	
	public boolean bluetoothIsEnabled() {
		return BluetoothUtils.getInstance().getBluetoothAdapter().isEnabled();
		
	}

}
