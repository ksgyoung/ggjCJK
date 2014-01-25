package com.cjk.thecloud.game.elements;

import java.util.ArrayList;
import java.util.List;

public class Network extends GameElement {
	
	private List<Packet> packets = new ArrayList<Packet>();

	public Network(String id) {
		super(id);
	}
	
	public List<Packet> getPackets() {
		return packets;
	}

	public void setPackets(List<Packet> packets) {
		this.packets = packets;
	}
	
	@Override
	protected void setSharedPrefId() {
		this.sharedPrefID = this.getClass().getSimpleName();		
	}

	@Override
	protected void setID(String id) {
		// TODO Auto-generated method stub
		
	}

}
