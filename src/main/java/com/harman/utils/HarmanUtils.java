package com.harman.utils;

import java.util.Random;

public class HarmanUtils {

	public static String generateSessionID(String macid, int productId) {
		Random rand = new Random();
		int value = rand.nextInt(50);
		return macid + productId + value;
	}
}
