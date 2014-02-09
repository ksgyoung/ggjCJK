package com.cjk.thecloud;

import java.util.Set;

import com.cjk.thecloud.controllers.BattleController;
import com.cjk.thecloud.controllers.BluetoothController;
import com.cjk.thecloud.game.Game;
import com.cjk.thecloud.game.GameCreator;

import android.os.Bundle;
import android.provider.Settings;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final String TAG = MainActivity.class.getSimpleName();
	
	private final int REQUEST_PAIR = 1;
	private final int REQUEST_ENABLE = 0;
	
	private BattleController battleController;
	private BluetoothAdapter mBluetoothAdapter;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		getActionBar().hide();
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		GameCreator.createGame();
		
		// Get local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
		
		
		String bluetoothName = mBluetoothAdapter.getName();
		
		battleController = BattleController.getInstance();
		battleController.setMyBluetoothName(bluetoothName);
		//battleController.startBluetoothListeners();
		//battleController.startBluetoothListeners();
		
		updateText(bluetoothName);

		if (getIntent().getExtras() != null && getIntent().getExtras().containsKey("WifiConnected")) {
			createDialog();
		} 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onButtonClick(View view) {
		BattleController.getInstance().startBattleActivityNoBluetooth(getApplicationContext(), "Test");
	}
	
	public void createDialog() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		 
		// set title
		alertDialogBuilder.setTitle("Found a jammer!");

		// set dialog message
		alertDialogBuilder
			.setMessage("Oh look! A jammer has crossed your path! Would you like to battle it to catch it?")
			.setCancelable(false)
			.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// if this button is clicked, close
					// current activity
					 //Get 
           			//GameCreator.createGame();
        			BattleController.getInstance().startBattleActivityNoBluetooth(getApplicationContext(),"Wild Wifi Jammer");
				}
			  })
			.setNegativeButton("No",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// if this button is clicked, just close
					// the dialog box and do nothing
					dialog.cancel();
					BattleController.getInstance().battleStarted = false;
				}
			});

			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();
	}

	public void onBluetoothClick(View view) {
					
			Log.d("BLUETOOTH", "search battle pressed");
			
			if(!mBluetoothAdapter.isEnabled()) {
				Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(turnOn, REQUEST_ENABLE); 
			}
			
			//Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
		    //startActivityForResult(intent, REQUEST_PAIR);
			
			Intent serverIntent = new Intent(this, DeviceListActivity.class);
            startActivityForResult(serverIntent, BluetoothController.REQUEST_CONNECT_DEVICE_INSECURE);
            return;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d(TAG, "onActivityResult " + resultCode);
        switch (requestCode) {
        case BluetoothController.REQUEST_CONNECT_DEVICE_SECURE:
            // When DeviceListActivity returns with a device to connect
            if (resultCode == Activity.RESULT_OK) {
                connectDevice(data, true);
            }
            break;
        case BluetoothController.REQUEST_CONNECT_DEVICE_INSECURE:
            // When DeviceListActivity returns with a device to connect
            if (resultCode == Activity.RESULT_OK) {
                connectDevice(data, false);
            }
            break;
        case BluetoothController.REQUEST_ENABLE_BT:
            // When the request to enable Bluetooth returns
            if (resultCode == Activity.RESULT_OK) {
                // Bluetooth is now enabled, so set up a chat session
                //setupChat();
            } else {
                // User did not enable Bluetooth or an error occurred
                Log.d(TAG, "BT not enabled");
                Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
                finish();
            }
        }
	}
	
	private void connectDevice(Intent data, boolean secure) {
        // Get the device MAC address
        String address = data.getExtras()
            .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        // Get the BluetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
       // BattleController.getInstance().setEnemyDevice(device);
        BattleController.getInstance().startBattleActivityBluetooth(this, device);
    }
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		BattleController.getInstance().startBluetoothListeners();
		//updateText(BluetoothUtils.getInstance().getLocalBluetoothName());
		
	}
	
	@Override
	protected void onDestroy() {
		BattleController.getInstance().stopBluetoothListeners();
		super.onDestroy();	
	}
	
	private void updateText(String bluetoothName) {
		TextView textView = (TextView) findViewById(R.id.activity_main_packet_count);
		textView.setText("> Your server:\n" + 
				"> Name: " + bluetoothName + "\n" + 
				"> # Jammers: " + Game.getInstance().getMyServer().getNumJammers() + "\n" + 
				"> # Packets: " + Game.getInstance().getMyServer().getNumPackets() + "\n"
				 + ">\n" + ">\n" + ">\n" + ">\n"
				);
	}
}
