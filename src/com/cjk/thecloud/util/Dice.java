package com.cjk.thecloud.util;

import java.util.Random;

public class Dice {
	
	private static Dice instance;	
	private Random random;
	
	private Dice() {
		random = new Random();
	}
	
	public static Dice getInstance() {
		if (instance == null) {
			instance = new Dice();
		}
		return instance;
	}
	
	public int getRoll(int n) {
		return random.nextInt(n);
	}

}
