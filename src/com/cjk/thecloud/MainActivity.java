package com.cjk.thecloud;

import java.util.Set;

import com.cjk.thecloud.controllers.BattleController;
import com.cjk.thecloud.game.GameCreator;
import com.cjk.thecloud.networking.ConnectThread;
import com.cjk.thecloud.util.BluetoothUtils;

import android.os.Bundle;
import android.provider.Settings;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class MainActivity extends Activity {

	private final int REQUEST_PAIR = 1;
	private final int REQUEST_ENABLE = 0;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		getActionBar().hide();
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		GameCreator.createGame();
		BattleController.getInstance().setMyBluetoothName(BluetoothUtils.getInstance().getLocalBluetoothName());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onButtonClick(View view) {
		BattleController.getInstance().startBattleActivity(getApplicationContext(), "Test");
	}

	public void onBluetoothClick(View view) {
		
			BluetoothUtils utils = BluetoothUtils.getInstance();
			
			Log.d("BLUETOOTH", "search battle pressed");
			
			if(!utils.getBluetoothAdapter().isEnabled()) {
				Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(turnOn, REQUEST_ENABLE); 
			}
			
			Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
		    startActivityForResult(intent, REQUEST_PAIR);			
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("BLUETOOTH", "activity result: " + requestCode);
		switch (requestCode) {
		case REQUEST_PAIR:
			
			Log.d("BLUETOOTH", "Paired with a device");
			
			Set<BluetoothDevice> devices = BluetoothUtils.getInstance().getPairedDevices();
			
			if(devices.size() > 0) {
				BluetoothDevice device = devices.iterator().next();
				BattleController.getInstance().startBattleActivity(this, device.getName());
			}
			
			break;

		default:
			break;
		}
	}
}
