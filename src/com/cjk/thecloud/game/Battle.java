package com.cjk.thecloud.game;

import com.cjk.thecloud.controllers.BattleController;
import com.cjk.thecloud.game.elements.Jammer;

public class Battle {
	
	private Jammer activeJammer;
	private BattleController controller;
	
	public void battleLoop(BattleController controller) {
		this.controller = controller;
		
		
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
	
	
	
	public void sendMyPackets() {
		
	}
	
	
	public boolean challenge() {
		return true;
	}
	
	public boolean run() {
		return false;
	}
	
	public double attack() {
		return 0;
	}
	
	public double defend() {
		return 0;
	}
	
	public void sendDamage() {
		
	}
	
	public double getDamage() {
		return 0;
	}
	
	
}
