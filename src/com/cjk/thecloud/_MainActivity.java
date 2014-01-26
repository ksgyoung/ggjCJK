package com.cjk.thecloud;

import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.cjk.thecloud.networking.BluetoothClient;
import com.cjk.thecloud.networking.BluetoothServer;
import com.cjk.thecloud.networking.BluetoothService2;
import com.cjk.thecloud.networking.ConnectThread;
import com.cjk.thecloud.networking.ConnectedThread;
import com.cjk.thecloud.util.BluetoothUtils;

public class _MainActivity extends Activity {

	Button buttonSearchBattle;
	private final int REQUEST_PAIR = 1;
	private final int REQUEST_ENABLE = 0;
	private final String EXTRA_DEVICE_ADDRESS  = "device_address";	
	private BluetoothService2 bluetoothService;
	protected static final int SUCCESS_CONNECT = 0;
    protected static final int MESSAGE_READ = 1;
    
    private class CommunicationHandler extends Handler {
    	@Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            Log.i("BLUETOOTH", "in handler");
            super.handleMessage(msg);
            switch(msg.what){
            case SUCCESS_CONNECT:
                // DO something
            	Toast.makeText(_MainActivity.this, "UUUUuuuuuuu! I am a client", Toast.LENGTH_LONG).show();
            	Log.d("BLUETOOTH", "I am a client");
                ConnectedThread connectedThread = new ConnectedThread((BluetoothSocket)msg.obj);
                Toast.makeText(_MainActivity.this, "CONNECT", 0).show();
                String s = "successfully connected";
                connectedThread.write(s.getBytes());
                Log.i("BLUETOOTH", "connected");
                break;
            case MESSAGE_READ:
                byte[] readBuf = (byte[])msg.obj;
                String string = new String(readBuf);
                Toast.makeText(_MainActivity.this, string, 0).show();
                break;
            }
        }
    }
        
    CommunicationHandler mHandler;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		BluetoothUtils.getInstance().setContext(getApplicationContext());
		
		buttonSearchBattle = (Button) findViewById(R.id.buttonSearchBattle);
		buttonSearchBattle.setOnClickListener(searchBattleListener);
		bluetoothService = new BluetoothService2(getApplicationContext());
		
		mHandler = new CommunicationHandler();
		
	}
	
	OnClickListener searchBattleListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			BluetoothUtils utils = BluetoothUtils.getInstance();
			
			Log.d("BLUETOOTH", "search battle pressed");
			
			if(!utils.getBluetoothAdapter().isEnabled()) {
				Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(turnOn, REQUEST_ENABLE); 
			}
			
			Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
		    startActivityForResult(intent, REQUEST_PAIR);
			
			utils.setInitiatedConnection(true);
			
		}
	};
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("BLUETOOTH", "activity result: " + requestCode);
		switch (requestCode) {
		case REQUEST_PAIR:
			Log.d("BLUETOOTH", "Paired with a device");
			
			
				Toast.makeText(this, "UUUUuuuuuuu! I am a server", Toast.LENGTH_LONG).show();
				Log.d("BLUETOOTH", "I am a server");
				
				Set<BluetoothDevice> devices  = BluetoothUtils.getInstance().getPairedDevices();
				
				Log.d("BLUETOOTH", "paired with " + devices.size());
				
				BluetoothDevice device = devices.iterator().next();
				BluetoothDevice actual = BluetoothUtils.getInstance().getBluetoothAdapter().getRemoteDevice(device.getAddress());
				
		        ConnectThread server = new ConnectThread(actual, mHandler);
		        //server.start();			
		        server.sendMessageToDevice("Test message");
			
			break;

		default:
			break;
		}
	}

	private void startDiscovery() {
		BluetoothAdapter adapter = BluetoothUtils.getInstance().getBluetoothAdapter();
		adapter.cancelDiscovery();
		adapter.startDiscovery();
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
