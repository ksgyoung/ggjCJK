package com.cjk.thecloud.game.elements;

import java.util.ArrayList;
import java.util.List;

import com.cjk.thecloud.game.elements.Jammer.Type;

public class Server extends GameElement {
	
	private List<Jammer> jammers = new ArrayList<Jammer>();
	
	public Server(String id) {
		super(id);
	}
	
	public void addJammer(Jammer jammer) {
		jammers.add(jammer);
	}
	
	public void removeJammer(Jammer jammer) {
		jammers.remove(jammer);
	}
	
	public int getNumPackets() {
		int numPackets = 0;
		for (Jammer jammer: jammers) {
			numPackets += jammer.getNumPackets();
		}
		return numPackets;
	}
	
	public int getAttackRate() {
		int total = 0;
		for (Jammer jammer : jammers) {
			total += jammer.getAttackRate();
		}
		return total;
	}
	
	public int getDefenceRate() {
		int total = 0;
		for (Jammer jammer : jammers) {
			total += jammer.getDefenceRate();
		}
		return total;
	}
	
	@Override
	protected void setSharedPrefId() {
		this.sharedPrefID = this.getClass().getSimpleName();		
	}
	
	
	
}
