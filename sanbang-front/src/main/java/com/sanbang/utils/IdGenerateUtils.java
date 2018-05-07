package com.sanbang.utils;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerateUtils {
	private static final AtomicInteger integer = new AtomicInteger(1000);

	public static String getSId() {
		char[] chs = { 'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', 'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L',
				'Z', 'X', 'C', 'V', 'B', 'N', 'M' };
		Random random = new Random();
		Integer index = random.nextInt(26);
		StringBuilder str = new StringBuilder(20);
		str.append(chs[index]);
		int intValue = integer.getAndIncrement();
		if (integer.get() >= 10000) {
			integer.set(0);
		}
		str.append(intValue);
		return str.toString();
	}

	public static void main(String[] args) {
		System.out.println(getSId());
	}
}
