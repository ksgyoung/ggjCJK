package com.cjk.thecloud;

import com.cjk.thecloud.controllers.BattleController;
import com.cjk.thecloud.game.Game;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class BattleActivity extends Activity {

	private BattleController controller;
	ProgressBar enemyHealthBar, myHealthBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_battle);
		getActionBar().hide();
		controller = BattleController.getInstance();
		controller.setActivity(this);
		enemyHealthBar = (ProgressBar) findViewById(R.id.activity_battle_enemy_health);
		myHealthBar = (ProgressBar) findViewById(R.id.activity_battle_my_health);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.battle, menu);
		return true;
	}
	
	public void onAttack1Clicked(View v) {
		int db = 0;
		
		if (controller.attemptHit()) {
			controller.attack();	
		}
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (!controller.attemptDodge()) {
			controller.getAttacked();
		}
	}
	
	public void setEnemyHealth(int health) {
		enemyHealthBar.setProgress(health);
	}
	
	public void setMyHealth(int health) {
		myHealthBar.setProgress(health);
	}

}
