package com.cjk.thecloud.game.elements;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.cjk.thecloud.game.Parameters;

public class Jammer extends GameElement {

	public enum Type {DEFENCE, ATTACK};
	
	private List<Packet> attackPackets = new ArrayList<Packet>();
	private List<Packet> defencePackets = new ArrayList<Packet>();
	private Type type;	
	private int damage = 0;
	
	public Jammer(String id, Type type) {
		super(id);
		this.type = type;
	}
	
	public void addPacket(Packet packet) {
		List<Packet> packets;
		if (packet.getType() == Type.ATTACK) {
			packets = attackPackets;
		} else {
			packets = defencePackets;
		}
		if (packets.size() < Parameters.MAX_PACKETS) {
			packets.add(packet);
		} else {
			Log.d(sharedPrefID, "Already has max packets.");
		}
	}
	
	public void removePacket(Packet packet) {
		List<Packet> packets;
		if (packet.getType() == Type.ATTACK) {
			packets = attackPackets;
		} else {
			packets = defencePackets;
		}		
		if (packets.size() > 0) {
			packets.remove(packet);
		}
		if (packets.size() == 0) {
			Log.d(sharedPrefID, "Fainting.");
		}
	}
	
	public void addDamage(int amount) {
		damage += amount;
	}
	
	public void removeDamage(int amount) {
		damage -= amount;
	}
	
	public int getHealth() {
		return getNumPackets() - damage;
	}
	
	public Type getType() {
		return type;
	}
	
	public int getAttackRate() {
		int numPackets = attackPackets.size();
		if (type == Type.ATTACK) {
			return numPackets * 2;
		}
		else return numPackets;
	}
	
	public int getDefenceRate() {
		int numPackets = defencePackets.size();
		if (type == Type.DEFENCE) {
			return numPackets * 2;
		}
		else return numPackets;
	}
	
	public int getNumPackets() {
		return defencePackets.size() + attackPackets.size();
	}

	@Override
	protected void setSharedPrefId() {
		this.sharedPrefID = this.getClass().getSimpleName();		
	}		
	
}
