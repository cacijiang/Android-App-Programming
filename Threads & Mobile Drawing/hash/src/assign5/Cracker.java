package assign5;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.*;
import java.util.concurrent.*;

public class Cracker {
	// Array of chars used to produce strings
	public static final char[] CHARS = "abcdefghijklmnopqrstuvwxyz0123456789.,-!".toCharArray();
	private static List<String> passwords;
	private static byte[] hashBytes;
	private static int numWorkers;
	private static int maxLength;
	private static CountDownLatch latch;
	
	
	public static class Worker extends Thread {
		private int start, end;
		
		public Worker(int start, int end) {
			this.start = start;
			this.end = end;
		}
		
		@Override
		public void run() {	
			for(int i = start; i <= end; i++) {
				String first = Character.toString(CHARS[i]);
				if(isPassword(first)) {
					passwords.add(first);
				} 
				crackerHelper(first);
			}
//			System.out.println(this.getName()+" is done.");
			latch.countDown();
		}
		
		public void crackerHelper(String prev) {
			if(prev.length() == maxLength) {
				return;
			}
			for(int i = 0; i < CHARS.length; i++) {
				String curr = prev+Character.toString(CHARS[i]);
				if(isPassword(curr)) {
					passwords.add(curr);
				}
				crackerHelper(curr);
			}
		}
	}
	/*
	 Given a byte[] array, produces a hex String,
	 such as "234a6f". with 2 chars for each byte in the array.
	 (provided code)
	*/
	public static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}
		
	/*
	 Given a string of hex byte values such as "24a26f", creates
	 a byte[] array of those values, one byte value -128..127
	 for each 2 chars.
	 (provided code)
	*/
	public static byte[] hexToArray(String hex) {
		byte[] result = new byte[hex.length()/2];
		for (int i=0; i<hex.length(); i+=2) {
			result[i/2] = (byte) Integer.parseInt(hex.substring(i, i+2), 16);
		}
		return result;
	}
		
	public static byte[] generationMode(String inputPassword) {
		byte[] bytes = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			bytes = md.digest(inputPassword.getBytes(StandardCharsets.UTF_8));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return bytes;
	}
	
	public static void startWorkers() {
		int range = CHARS.length/numWorkers;
		for(int i = 0; i < CHARS.length; i += range) {
			Worker iWorker = new Worker(i, Math.min(i+range-1, CHARS.length-1));
			iWorker.start();
		}
	}
	
	public static void printPasswords() {
		if(passwords.size() == 0) {
			System.out.println("No matched password within max length.");
		} else {
			for(String password : passwords) {
				System.out.println(password);
			}
		}
	}
	
	public static void crackerMode(String inputHashVal) {
		passwords = new ArrayList<String> ();
		hashBytes = hexToArray(inputHashVal);
		latch = new CountDownLatch(numWorkers);
		startWorkers();
		try {
            latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		printPasswords();
		System.out.println("all done");
	}
		
	public static void main(String[] args) {
		if(args.length == 1) {
			// generation mode
			System.out.println(hexToString(generationMode(args[0])));
		} else if(args.length == 3) {
			// cracker mode
			String inputHashVal = args[0];
			maxLength = Integer.parseInt(args[1]);
			numWorkers = Integer.parseInt(args[2]);
			crackerMode(inputHashVal);
		} else {
			System.err.println("Invalid input format.");
		}
		
	}
	
	private static boolean isPassword(String curr) {
		byte[] bytes = generationMode(curr);
		if(Arrays.equals(hashBytes, bytes)) {
			return true;
		}
		return false;
	}

	// possible test values:
	// a ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb
	// fm 440f3041c89adee0f2ad780704bcc0efae1bdb30f8d77dc455a2f6c823b87ca0
	// a! 242ed53862c43c5be5f2c5213586d50724138dea7ae1d8760752c91f315dcd31
	// xyz 3608bca1e44ea6c4d268eb6db02260269892c0b42b86bbf1e77a6fa16c3c9282

}
