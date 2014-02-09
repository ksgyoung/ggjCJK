package com.cjk.thecloud;

import com.cjk.thecloud.controllers.BattleController;
import com.cjk.thecloud.game.Game;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class BattleActivity extends Activity {

	private BattleController controller;
	private ProgressBar enemyHealthBar, myHealthBar;
	private TextView enemyNameTextView, myNameTextView;
	private boolean isBluetoothBattle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		/*--- ACTIVITY THINGS ---*/
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_battle);
		getActionBar().hide();
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		/*--- INITIALISE VIEWS ---*/
		enemyHealthBar = (ProgressBar) findViewById(R.id.activity_battle_enemy_health);
		myHealthBar = (ProgressBar) findViewById(R.id.activity_battle_my_health);
		enemyNameTextView = (TextView) findViewById(R.id.activity_battle_enemy_name);
		myNameTextView = (TextView) findViewById(R.id.activity_battle_my_name);

		/*--- BLUETOOTH SETUP ---*/
		this.isBluetoothBattle = this.getIntent().getBooleanExtra(
				BattleController.IS_BLUETOOTH_BATTLE, true);
		
		/*--- GAME SETUP ---*/
		controller = BattleController.getInstance();
		controller.setActivity(this);
		setMyName(controller.getMyBluetoothName());
		if (!isBluetoothBattle) {
			setEnemyName(this.getIntent().getStringExtra(
					BattleController.ENEMY_NAME));
		}

		/*
		 * setEnemyName(this.getIntent().getStringExtra("enemy_name"));
		 * setMyName(controller.getMyBluetoothName());
		 * 
		 * ImageView enemyImage = (ImageView) findViewById(R.id.enemyImage);
		 * TextView enemyText = (TextView)
		 * findViewById(R.id.activity_battle_enemy_name); ProgressBar
		 * enemyProgress = (ProgressBar)
		 * findViewById(R.id.activity_battle_enemy_health);
		 * 
		 * if(enemyName.getText().toString().contains("Wifi")) {
		 * enemyImage.setImageResource(R.drawable.elephant);
		 * enemyText.setTextColor(Color.parseColor("#44aa00"));
		 * enemyProgress.setProgressDrawable
		 * (getResources().getDrawable(R.drawable.progress_line_green)); }
		 */
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (isBluetoothBattle) {
			controller.startBluetoothListeners();
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (isBluetoothBattle) {
			controller.connectBluetooth(true);
		}
	}

	@Override
	public void finish() {
		BattleController.getInstance().battleStarted = false;
		super.finish();

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
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

		/*
		 * try { Thread.sleep(2000); } catch (InterruptedException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */

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

	public void setEnemyName(String name) {
		enemyNameTextView.setText(name);
	}

	public void setMyName(String name) {
		myNameTextView.setText(name);
	}

}
