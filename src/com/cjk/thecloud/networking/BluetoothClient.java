package com.cjk.thecloud.networking;

import java.io.IOException;
import java.util.UUID;

import com.cjk.thecloud.util.BluetoothUtils;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.provider.Settings.Secure;
import android.util.Log;

public class BluetoothClient extends Thread {
	private BluetoothSocket bluetoothSocket;
	private BluetoothDevice bluetoothDevice;
	
	public BluetoothClient(BluetoothDevice device) {
		bluetoothDevice = device;
		try {
			bluetoothSocket = device.createRfcommSocketToServiceRecord(UUID.fromString(Secure.ANDROID_ID));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void run() {
		BluetoothUtils.BLUETOOTH_ADAPTER.cancelDiscovery();
		
		try {
			bluetoothSocket.connect();
		} catch (IOException e) {
			try {
				bluetoothSocket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		//connected, do somehting
		Log.d("BLUETOOTH", "Client connected");
	}
	
	public void cancel() {
		try {
			bluetoothSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
