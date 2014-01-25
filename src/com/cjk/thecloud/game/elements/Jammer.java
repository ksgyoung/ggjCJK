package com.cjk.thecloud.game.elements;

import java.util.List;

import com.cjk.thecloud.game.Parameters;


public class Jammer extends GameElement {

	public enum Type {DEFENCE, ATTACK};
	
	private List<Packet> packets;
	private Type type;	
	
	public Jammer(Type type) {
		super();
	}
	
	public List<Packet> getPackets() {
		return packets;
	}
	
	public void addPacket() {
		if (packets.size() < Parameters.MAX_PACKETS) {
			
		}
	}
	
	public Type getType() {
		return type;
	}		
	
}
