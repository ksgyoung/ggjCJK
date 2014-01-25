package elements;

import android.util.Log;

public class GameElement {
	
	private static final String SHARED_PREF_ID = GameElement.class.getSimpleName();
	
	public GameElement() {
		Log.d(SHARED_PREF_ID, "Created.");
	}

}
