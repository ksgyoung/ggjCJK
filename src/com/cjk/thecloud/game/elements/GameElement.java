package com.cjk.thecloud.game.elements;

import android.util.Log;


public abstract class GameElement {
	
	protected String sharedPrefID;
	protected String id;
	
	public GameElement(String id) {
		setSharedPrefId();
		setID(id);
		Log.d(sharedPrefID, "Created.");
	}
	
	protected abstract void setSharedPrefId();
	
	protected void setID(String id) {
		this.id = id;
	}

}
