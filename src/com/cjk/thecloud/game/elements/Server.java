package com.cjk.thecloud.game.elements;

import java.util.ArrayList;
import java.util.List;

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
	
	@Override
	protected void setSharedPrefId() {
		this.sharedPrefID = this.getClass().getSimpleName();		
	}
	
}
