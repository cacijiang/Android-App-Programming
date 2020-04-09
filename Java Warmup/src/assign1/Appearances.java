package assign1;

import java.util.*;

public class Appearances {
	
	/**
	 * Returns the number of elements that appear the same number
	 * of times in both collections. Static method. (see handout).
	 * @return number of same-appearance elements
	 */
	public static <T> int sameCount(Collection<T> a, Collection<T> b) {
		int count = 0;
		Map<T, Integer> map1 = new HashMap<> ();
		for(T ele : a) {
			map1.put(ele, map1.getOrDefault(ele, 0)+1);
		}
		Map<T, Integer> map2 = new HashMap<> ();
		for(T ele : b) {
			map2.put(ele, map2.getOrDefault(ele, 0)+1);
		}
		for(T key : map1.keySet()) {
			if(map2.containsKey(key) && map1.get(key) == map2.get(key)) {
				count++;
			}
		}
		return count; // TODO ADD CODE HERE
	}
	
}
