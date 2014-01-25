package com.cjk.thecloud.game.elements;

import com.cjk.thecloud.game.Parameters;

public class Packet extends GameElement {

	private int health = Parameters.MAX_HEALTH;
	private Jammer.Type type;
	
	public Packet(String id, Jammer.Type type) {
		super(id);
		this.type = type;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	public void subtractHealth(int amount) {
		this.health -= amount;
	}

	public Jammer.Type getType() {
		return type;
	}
	
	@Override
	protected void setSharedPrefId() {
		this.sharedPrefID = this.getClass().getSimpleName();		
	}	
	
}
