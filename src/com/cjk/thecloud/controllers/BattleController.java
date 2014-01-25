package com.cjk.thecloud.controllers;

import android.content.Context;
import android.content.Intent;

import com.cjk.thecloud.BattleActivity;

public class BattleController {
	
	private static BattleController instance;
	private BattleActivity activity;
	
	private BattleController(){}
	
	public static BattleController getInstance() {
		if (instance == null) {
			instance = new BattleController();
		}
		return instance;
	}
	
	public void startBattle(Context context) {
		Intent intent = new Intent(context, BattleActivity.class);
		context.startActivity(intent);
	}

	public BattleActivity getActivity() {
		return activity;
	}

	public void setActivity(BattleActivity activity) {
		this.activity = activity;
	}

}
