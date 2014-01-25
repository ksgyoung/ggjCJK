package com.cjk.thecloud.game;

import android.util.Log;

import com.cjk.thecloud.game.elements.Jammer;
import com.cjk.thecloud.game.elements.Jammer.Type;
import com.cjk.thecloud.game.elements.Packet;
import com.cjk.thecloud.game.elements.Server;

public class GameCreator {
	
	private static final String TAG = GameCreator.class.getSimpleName();
	
	Game game = Game.getInstance();
	
	public static void createGame() {
		Server myServer = new Server("my_server");
		Jammer jammer1 = new Jammer("attack_jammer", Type.ATTACK);
		for (int i = 0; i < 5; i++) {
			Packet packet1 = new Packet("attack_packet", Type.ATTACK);
			jammer1.addPacket(packet1);
		}
		for (int i = 0; i < 5; i++) {
			Packet packet1 = new Packet("defence_packet", Type.DEFENCE);
			jammer1.addPacket(packet1);
		}
		
		Log.d(TAG, "Jammer1, Attack: " + jammer1.getAttackRate());
		Log.d(TAG, "Jammer1, Defence: " +jammer1.getDefenceRate());
		
		myServer.addJammer(jammer1);
		
		Server enemyServer = new Server("enemy_server");
		Jammer jammer2 = new Jammer("defence_jammer", Type.DEFENCE);
		for (int i = 0; i < 3; i++) {
			Packet packet2 = new Packet("attack_packet", Type.DEFENCE);
			jammer2.addPacket(packet2);
		}
		for (int i = 0; i < 3; i++) {
			Packet packet2 = new Packet("defence_packet", Type.ATTACK);
			jammer2.addPacket(packet2);
		}
		
		Log.d(TAG, "Jammer2, Attack: " + jammer2.getAttackRate());
		Log.d(TAG, "Jammer2, Defence: " + jammer2.getDefenceRate());
		
		enemyServer.addJammer(jammer2);
	}
	
	public static void loadGame() {
		
	}

}
