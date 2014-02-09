package com.cjk.thecloud.controllers;

import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.cjk.thecloud.BattleActivity;
import com.cjk.thecloud.R;
import com.cjk.thecloud.game.Game;
import com.cjk.thecloud.game.elements.Jammer;
import com.cjk.thecloud.game.elements.Server;
import com.cjk.thecloud.util.Dice;

public class BattleController {
	
	public static final String IS_BLUETOOTH_BATTLE = "IS_BLUETOOTH_BATTLE";
	public static final String ENEMY_NAME = "ENEMY_NAME";
	
	private static final boolean DEBUG = true;
	
	private static BattleController instance;
	private String enemyName;
	private String TAG = "BattleController";
	private BattleActivity battleActivity;
	private String myBluetoothName;
	public boolean battleStarted = false;
	
	private BluetoothController bluetoothController = null;
	private BluetoothDevice enemyDevice;
	
	private BattleController(){}
	
	public static BattleController getInstance() {
		if (instance == null) {
			instance = new BattleController();
		}
		return instance;
	}
	
	public void startBattleActivityNoBluetooth(Context context, String enemyName) {
		Intent intent = new Intent(context, BattleActivity.class);
		
		intent.putExtra(IS_BLUETOOTH_BATTLE, false);
		intent.putExtra(ENEMY_NAME, enemyName);
		
		context.startActivity(intent);
	}
	
	public void startBattleActivityBluetooth(Context context, BluetoothDevice device) {		
		this.enemyDevice = device;
		Intent intent = new Intent(context, BattleActivity.class);
		context.startActivity(intent);
	}	
	
	public void startBluetoothListeners() {
		if (bluetoothController == null) {
			bluetoothController = new BluetoothController(battleActivity);
		}
		bluetoothController.startListeners();
	}
	
	public void stopBluetoothListeners() {
		if (bluetoothController != null) {
			bluetoothController.stopListeners();
		}
	}
	
	public void connectBluetooth(boolean secure) {
		if (bluetoothController == null) {
			bluetoothController = new BluetoothController(battleActivity);
		}
		bluetoothController.setEnemyDevice(enemyDevice);
		bluetoothController.connect(true);
	}
	
	public boolean challenge() {
		int enemyDefenceRate = Game.getInstance().getEnemyServer().getDefenceRate();
		int myAttackRate = Game.getInstance().getMyServer().getAttackRate();
		Dice dice = Dice.getInstance();
		if (dice.getRoll(enemyDefenceRate) < dice.getRoll(myAttackRate)) {
			Toast toast = Toast.makeText(battleActivity, "Enemy failed to run away.", Toast.LENGTH_LONG);
			toast.show();
			return true;
		}
		Toast toast = Toast.makeText(battleActivity, "Enemy ran away.", Toast.LENGTH_LONG);
		toast.show();
		return false;
	}
	
	public boolean attemptHit() {
		int enemyDefenceRate = Game.getInstance().getEnemyServer().getDefenceRate()/2;
		int myAttackRate = Game.getInstance().getMyServer().getAttackRate();
		Dice dice = Dice.getInstance();
		if (dice.getRoll(enemyDefenceRate) < dice.getRoll(myAttackRate)) {
			Toast toast = Toast.makeText(battleActivity, "I hit the enemy!", Toast.LENGTH_SHORT);
			toast.show();
			return true;
		}
		Toast toast = Toast.makeText(battleActivity, "I missed the enemy!", Toast.LENGTH_SHORT);
		toast.show();
		return false;
	}
	
	public boolean attemptDodge() {
		int myDefenceRate = Game.getInstance().getMyServer().getDefenceRate()/2;
		int enemyAttackRate = Game.getInstance().getEnemyServer().getAttackRate();
		Dice dice = Dice.getInstance();
		if (dice.getRoll(myDefenceRate) < dice.getRoll(enemyAttackRate)) {
			Toast toast = Toast.makeText(battleActivity, "Enemy hit me!", Toast.LENGTH_SHORT);
			toast.show();
			return false;
		}
		Toast toast = Toast.makeText(battleActivity, "I dodged!", Toast.LENGTH_SHORT);
		toast.show();
		return true;
	}
	
	public double attack() {
		//int enemyDefenceRate = Game.getInstance().getEnemyServer().getDefenceRate() ;
		Server myServer = Game.getInstance().getMyServer();
		int myAttackRate = myServer.getAttackRate();
		Dice dice = Dice.getInstance();
		int damage = dice.getRoll(myAttackRate / 2 - 1) + 1;
		//float damageReductionRate = (float) (dice.getRoll(enemyDefenceRate / 2) / 10.0);
		//damage = (Integer) Math.round(damage * damageReductionRate);
		Toast toast = Toast.makeText(battleActivity, "Damage to enemy: " + damage, Toast.LENGTH_SHORT);
		toast.show();
		
		Jammer enemyJammer = Game.getInstance().getEnemyServer().getJammer();
		enemyJammer.addDamage(damage);
		
		battleActivity.setEnemyHealth(enemyJammer.getHealth());
		
		if(jammerIsDead(enemyJammer)) {
			Jammer myJammer = myServer.getJammer();
			Toast.makeText(battleActivity, "Enemy died", Toast.LENGTH_SHORT).show();
			
			myJammer.addPacket(enemyJammer.takeAttackPacket());
			myJammer.addPacket(enemyJammer.takeDefencePacket());
		
			showDeathDialog("Congratulations! You have defeated your enemy");
		}
		
		return damage;
	}
	
	private void showDeathDialog(String message) {
		 AlertDialog.Builder builder = new AlertDialog.Builder(battleActivity);
		        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int id) {
		                // User clicked OK button
		            	battleActivity.finish();
		            }
		        });
		       
		        AlertDialog dialog = builder.create();

		        dialog.setTitle(message);
		        dialog.show();
	}
	
	private boolean jammerIsDead(Jammer jammer) {
		if(jammer.getHealth() <= 0) {
			return true;
		}
		
		return false;
	}
	
	public double getAttacked() {
		Server enemyServer = Game.getInstance().getEnemyServer();
		
		int enemyAttackRate = enemyServer.getAttackRate();
		Dice dice = Dice.getInstance();
		int damage = dice.getRoll(enemyAttackRate / 2 - 1) + 1;
		
		Jammer myJammer = Game.getInstance().getMyServer().getJammer();
		myJammer.addDamage(damage);
		
		battleActivity.setMyHealth(myJammer.getHealth());
		
		if(jammerIsDead(myJammer)) {
			Jammer enemyJammer = enemyServer.getJammer();
			Toast.makeText(battleActivity, "I died", Toast.LENGTH_SHORT).show();
			
			enemyJammer.addPacket(myJammer.takeAttackPacket());
			enemyJammer.addPacket(myJammer.takeDefencePacket());
			
			showDeathDialog("You have lost - shame on you");
		}
		
		Toast toast = Toast.makeText(battleActivity, "Damage to me: " + damage, Toast.LENGTH_SHORT);
		toast.show();
		return damage;
	}
	
	public double getEnemyPackets() {
		return Game.getInstance().getEnemyServer().getNumPackets();
	}

	public BattleActivity getActivity() {
		return battleActivity;
	}

	public void setActivity(BattleActivity activity) {
		this.battleActivity = activity;
	}

	public String getMyBluetoothName() {
		return myBluetoothName;
	}

	public void setMyBluetoothName(String myBluetoothName) {
		this.myBluetoothName = myBluetoothName;
	}

	public BluetoothDevice getEnemyDevice() {
		return enemyDevice;
	}

	public void setEnemyDevice(BluetoothDevice enemyDevice) {
		this.enemyDevice = enemyDevice;
	}

}
