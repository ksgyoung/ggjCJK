package controllers;

import elements.Jammer;

public class BattleController {
	
	private Jammer activeJammer;
	
	public void battleLoop() {
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
	
	public double getEnemyPackets() {
		return 0;
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
