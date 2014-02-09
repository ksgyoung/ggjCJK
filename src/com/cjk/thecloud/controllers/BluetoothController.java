package com.cjk.thecloud.controllers;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.cjk.thecloud.BattleActivity;
import com.cjk.thecloud.R;
import com.cjk.thecloud.game.elements.Server;
import com.cjk.thecloud.networking.bluetooth.BluetoothChatService;

public class BluetoothController {
	
	private static final String TAG = BluetoothController.class.getSimpleName();
	private static final boolean D = true;
	
	// Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    // Intent request codes
    public static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    public static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    public static final int REQUEST_ENABLE_BT = 3;
    
    //private String enemyBluetoothId;
	private BattleActivity battleActivity;
	private BluetoothChatService bluetoothService;
	
	private BluetoothDevice enemyDevice;
	private String enemyBluetoothId;
	
	private StringBuffer outStringBuffer;
    
	public BluetoothController(BattleActivity battleActivity) {
		this.battleActivity = battleActivity;
		//this.enemyDevice = enemyDevice;
		this.bluetoothService = new BluetoothChatService(battleActivity, mHandler);
		this.outStringBuffer = new StringBuffer("");
	}
    
	public void startListeners() {
		if (bluetoothService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (bluetoothService.getState() == BluetoothChatService.STATE_NONE) {
            	// Start the Bluetooth chat services
            	bluetoothService.start();
            }
        }
	}
	
	public void stopListeners() {
		if (bluetoothService != null) {
			bluetoothService.stop();
		}
	}
	
	public void connect(boolean secure) {
		if (enemyDevice == null) {
			Log.e(TAG, "No enemy device");
			return;
		}
		bluetoothService.connect(enemyDevice, secure);
	}
	
    
	// The Handler that gets information back from the BluetoothChatService
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MESSAGE_STATE_CHANGE:
                if(D) Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                switch (msg.arg1) {
                case BluetoothChatService.STATE_CONNECTED:
                	if (battleActivity != null) 
                		battleActivity.setEnemyName("Connected to: " + enemyBluetoothId);
                    break;
                case BluetoothChatService.STATE_CONNECTING:
                	if (battleActivity != null) 
                		battleActivity.setEnemyName("Connecting...");
                    break;
                case BluetoothChatService.STATE_LISTEN:
                	//battleActivity.setEnemyName("Listening...");
                case BluetoothChatService.STATE_NONE:
                	if (battleActivity != null) 
                		battleActivity.setEnemyName("Not connected!");
                    break;
                }
                break;
            case MESSAGE_WRITE:
                byte[] writeBuf = (byte[]) msg.obj;
                // construct a string from the buffer
                String writeMessage = new String(writeBuf);
                Toast toast = Toast.makeText(battleActivity, "Me: " + writeMessage, Toast.LENGTH_SHORT);
        		toast.show();
                break;
            case MESSAGE_READ:
                byte[] readBuf = (byte[]) msg.obj;
                // construct a string from the valid bytes in the buffer
                String readMessage = new String(readBuf, 0, msg.arg1);
                toast = Toast.makeText(battleActivity, "Enemy: " + readMessage, Toast.LENGTH_SHORT);
        		toast.show();
                break;
            case MESSAGE_DEVICE_NAME:
                // save the connected device's name
                enemyBluetoothId = msg.getData().getString(DEVICE_NAME);
                //battleActivity.setEnemyName(enemyBluetoothId);
                //Toast.makeText(getApplicationContext(), "Connected to "
               //                + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                break;
            case MESSAGE_TOAST:
                //Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
                //               Toast.LENGTH_SHORT).show();
            	//toast = Toast.makeText(battleActivity, msg.getData().getString(TOAST), Toast.LENGTH_SHORT);
        		//toast.show();
                break;
            }
        }
    };
    
    /**
     * Sends a message.
     * @param message  A string of text to send.
     */
    public void sendMessage(String message) {
        // Check that we're actually connected before trying anything
        if (bluetoothService.getState() != BluetoothChatService.STATE_CONNECTED) {
            Log.e(TAG, "Not connected");
        	//Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }

        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = message.getBytes();
            bluetoothService.write(send);

            // Reset out string buffer to zero and clear the edit text field
            outStringBuffer.setLength(0);
            //mOutEditText.setText(mOutStringBuffer);
        }
    }

    
    public BluetoothDevice getEnemyDevice() {
		return enemyDevice;
	}

	public void setEnemyDevice(BluetoothDevice enemyDevice) {
		this.enemyDevice = enemyDevice;
	}

	public BattleActivity getBattleActivity() {
		return battleActivity;
	}

	public void setBattleActivity(BattleActivity battleActivity) {
		this.battleActivity = battleActivity;
	}
	
	/*public Intent startBluetoothSettingsActivity() {
		return new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
	}
	
	public Intent getBluetoothConnectIntent() {
		return new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	};
	
	public boolean bluetoothIsEnabled() {
		return BluetoothUtils.getInstance().getBluetoothAdapter().isEnabled();
		
	}*/

}
