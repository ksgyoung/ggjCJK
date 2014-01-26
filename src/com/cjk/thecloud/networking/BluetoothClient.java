package com.cjk.thecloud.networking;

import java.io.IOException;
import java.util.UUID;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.cjk.thecloud.util.BluetoothUtils;

public class BluetoothClient extends Thread {
	private BluetoothSocket bluetoothSocket;
	private BluetoothDevice bluetoothDevice;
	
	public BluetoothClient(BluetoothDevice device) {
		BluetoothUtils bluetoothUtils = BluetoothUtils.getInstance();
		
		bluetoothDevice = device;
		
		try {
			bluetoothSocket = device.createRfcommSocketToServiceRecord(UUID.randomUUID());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void run() {
		BluetoothUtils.getInstance().getBluetoothAdapter().cancelDiscovery();
		
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
