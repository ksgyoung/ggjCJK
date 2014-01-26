package com.cjk.thecloud.controllers;

import android.app.AlertDialog;
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
	
	private static BattleController instance;
	private String enemyName;
	private String TAG = "BattleController";
	private BattleActivity activity;
	private String myBluetoothName;
	public boolean battleStarted = false;
	private BattleController(){}
	
	public static BattleController getInstance() {
		if (instance == null) {
			instance = new BattleController();
		}
		return instance;
	}
	
	public void startBattleActivity(Context context, String enemyName) {
		Log.d(TAG, "Battling " + enemyName);
		this.enemyName = enemyName;
		
		Intent intent = new Intent(context, BattleActivity.class);

		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("enemy_name", enemyName);
		context.startActivity(intent);	
	}
	
	public void doBattle() {
		
		
		//String enemyPacketText = "Spotted an enemy with " + getEnemyPackets() + " packets. Do you want to attack?";
		//activity.appendTextToConsole(enemyPacketText);
		// Find server
		// Send total packets
		// Get total packets
		// Challenge
		// Run?
		// Select unit
		// Attack
		// Defend
		// Successful attack = packet damage
		// No packets = faint
	}	
	
	public boolean challenge() {
		int enemyDefenceRate = Game.getInstance().getEnemyServer().getDefenceRate();
		int myAttackRate = Game.getInstance().getMyServer().getAttackRate();
		Dice dice = Dice.getInstance();
		if (dice.getRoll(enemyDefenceRate) < dice.getRoll(myAttackRate)) {
			Toast toast = Toast.makeText(activity, "Enemy failed to run away.", Toast.LENGTH_LONG);
			toast.show();
			return true;
		}
		Toast toast = Toast.makeText(activity, "Enemy ran away.", Toast.LENGTH_LONG);
		toast.show();
		return false;
	}
	
	public boolean attemptHit() {
		int enemyDefenceRate = Game.getInstance().getEnemyServer().getDefenceRate()/2;
		int myAttackRate = Game.getInstance().getMyServer().getAttackRate();
		Dice dice = Dice.getInstance();
		if (dice.getRoll(enemyDefenceRate) < dice.getRoll(myAttackRate)) {
			Toast toast = Toast.makeText(activity, "I hit the enemy!", Toast.LENGTH_SHORT);
			toast.show();
			return true;
		}
		Toast toast = Toast.makeText(activity, "I missed the enemy!", Toast.LENGTH_SHORT);
		toast.show();
		return false;
	}
	
	public boolean attemptDodge() {
		int myDefenceRate = Game.getInstance().getMyServer().getDefenceRate()/2;
		int enemyAttackRate = Game.getInstance().getEnemyServer().getAttackRate();
		Dice dice = Dice.getInstance();
		if (dice.getRoll(myDefenceRate) < dice.getRoll(enemyAttackRate)) {
			Toast toast = Toast.makeText(activity, "Enemy hit me!", Toast.LENGTH_SHORT);
			toast.show();
			return false;
		}
		Toast toast = Toast.makeText(activity, "I dodged!", Toast.LENGTH_SHORT);
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
		Toast toast = Toast.makeText(activity, "Damage to enemy: " + damage, Toast.LENGTH_SHORT);
		toast.show();
		
		Jammer enemyJammer = Game.getInstance().getEnemyServer().getJammer();
		enemyJammer.addDamage(damage);
		
		activity.setEnemyHealth(enemyJammer.getHealth());
		
		if(jammerIsDead(enemyJammer)) {
			Jammer myJammer = myServer.getJammer();
			Toast.makeText(activity, "Enemy died", Toast.LENGTH_SHORT).show();
			
			myJammer.addPacket(enemyJammer.takeAttackPacket());
			myJammer.addPacket(enemyJammer.takeDefencePacket());
		
			showDeathDialog("Congratulations! You have defeated your enemy");
		}
		
		return damage;
	}
	
	private void showDeathDialog(String message) {
		 AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int id) {
		                // User clicked OK button
		            	activity.finish();
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
		
		activity.setMyHealth(myJammer.getHealth());
		
		if(jammerIsDead(myJammer)) {
			Jammer enemyJammer = enemyServer.getJammer();
			Toast.makeText(activity, "I died", Toast.LENGTH_SHORT).show();
			
			enemyJammer.addPacket(myJammer.takeAttackPacket());
			enemyJammer.addPacket(myJammer.takeDefencePacket());
			
			showDeathDialog("You have lost - shame on you");
		}
		
		Toast toast = Toast.makeText(activity, "Enemy attack: " + damage, Toast.LENGTH_SHORT);
		toast.show();
		return damage;
	}
	
	public double getEnemyPackets() {
		return Game.getInstance().getEnemyServer().getNumPackets();
	}

	public BattleActivity getActivity() {
		return activity;
	}

	public void setActivity(BattleActivity activity) {
		this.activity = activity;
	}

	public String getMyBluetoothName() {
		return myBluetoothName;
	}

	public void setMyBluetoothName(String myBluetoothName) {
		this.myBluetoothName = myBluetoothName;
	}

}
