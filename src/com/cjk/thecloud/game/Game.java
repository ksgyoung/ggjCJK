package com.cjk.thecloud.game;

import com.cjk.thecloud.game.elements.Server;

public class Game {
	
	private Server myServer;
	private Server enemyServer;
	
	private static Game instance;
	
	private Game() {
		
	}
	
	public static Game getInstance() {
		if (instance == null) {
			instance = new Game();
		} 
		return instance;
	}
	
	public Server getMyServer() {
		return myServer;
	}
	public void setMyServer(Server myServer) {
		this.myServer = myServer;
	}
	public Server getEnemyServer() {
		return enemyServer;
	}
	public void setEnemyServer(Server enemyServer) {
		this.enemyServer = enemyServer;
	}
	
}
