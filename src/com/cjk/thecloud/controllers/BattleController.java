package com.cjk.thecloud.controllers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.cjk.thecloud.WarMapActivity;
import com.cjk.thecloud.game.Battle;
import com.cjk.thecloud.game.Game;
import com.cjk.thecloud.util.Dice;

public class BattleController {
	
	private static BattleController instance;
	private WarMapActivity activity;
	private Battle battle;
	private String enemyName;
	private String TAG = "BattleController";
	
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
		
		Intent intent = new Intent(context, WarMapActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
		activity.disappearEnemy();
		return false;
	}
	
	public double getEnemyPackets() {
		return Game.getInstance().getEnemyServer().getNumPackets();
	}

	public void startBattle() {
		battle = new Battle();
		battle.battleLoop(this);
	}
	
	public WarMapActivity getActivity() {
		return activity;
	}

	public void setActivity(WarMapActivity activity) {
		this.activity = activity;
	}

}
