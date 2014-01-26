package com.cjk.thecloud.util;

import java.util.Set;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;

public class BluetoothUtils {
	private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	private static BluetoothUtils instance = null;
	private Context context;
	private boolean initiatedConnection = true;
	
	public static BluetoothUtils getInstance() {
		if(instance == null) {
			instance = new BluetoothUtils();
		}
		
		return instance;
	}

	public void setContext(Context context) {
		this.context = context;
	}
	
	public BluetoothAdapter getBluetoothAdapter() {
		return bluetoothAdapter;
	}
	

	public Set<BluetoothDevice> getPairedDevices() {
		return bluetoothAdapter.getBondedDevices();
	}

	public boolean initiatedConnection() {
		return initiatedConnection;
	}

	public void setInitiatedConnection(boolean initiatedConnection) {
		this.initiatedConnection = initiatedConnection;
	}
	
	
	
	
}
