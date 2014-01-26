package com.cjk.thecloud;

import com.cjk.thecloud.controllers.BattleController;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class BattleActivity extends Activity {

	private BattleController controller;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_battle);
		getActionBar().hide();
		controller = BattleController.getInstance();
		controller.setActivity(this);
		controller.doBattle();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.battle, menu);
		return true;
	}

}
