package com.cjk.thecloud;

import com.cjk.thecloud.controllers.BattleController;
import com.cjk.thecloud.util.SystemUiHider;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class WarMapActivity extends Activity {
	
	private BattleController controller;
	private RelativeLayout enemyLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_war_map);
		getActionBar().hide();
		
		enemyLayout = (RelativeLayout) findViewById(R.id.activity_war_map_enemy_layout);
		
		controller = BattleController.getInstance();
		//controller.setActivity(this);
		controller.doBattle();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void jamButtonClicked(View v) {
		controller.challenge();		
	}
	
	public void disappearEnemy() {
		enemyLayout.setVisibility(View.GONE);
	}
	
	
}
