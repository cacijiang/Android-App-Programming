package assign1;

import java.util.HashSet;
import java.util.Set;

// CS108 HW1 -- String static methods

public class StringCode {

	/**
	 * Given a string, returns the length of the largest run.
	 * A a run is a series of adajcent chars that are the same.
	 * @param str
	 * @return max run length
	 */
	public static int maxRun(String str) {
		if(str.length() == 0) {
			return 0;
		}
		int max = 1;
		int times = 1;
		char prev = str.charAt(0);
		for(int i = 1; i < str.length(); i++) {
			char curr = str.charAt(i);
			if(curr == prev) {
				times++;
			} else {
				max = Math.max(max, times);
				times = 1;
			}
			prev = curr;
		}
		max = Math.max(max, times);
		return max; // TODO ADD YOUR CODE HERE
	}

	
	/**
	 * Given a string, for each digit in the original string,
	 * replaces the digit with that many occurrences of the character
	 * following. So the string "a3tx2z" yields "attttxzzz".
	 * @param str
	 * @return blown up string
	 */
	public static String blowup(String str) {
		String ans = "";
		for(int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if(Character.isDigit(ch)) {
				if(i < str.length()-1) {
					int times = ch-'0';
					char next = str.charAt(i+1);
					while(times-- > 0) {
						ans += next;
					}
				}
			} else {
				ans += ch;
			}
		}
		return ans; // TODO ADD YOUR CODE HERE
	}
	
}
