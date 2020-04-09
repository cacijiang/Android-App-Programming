/*
 HW1 Taboo problem class.
 Taboo encapsulates some rules about what objects
 may not follow other objects.
 (See handout).
*/
package assign1;

import java.util.*;

public class Taboo<T> {
	private Map<T, Set<T>> map;
	/**
	 * Constructs a new Taboo using the given rules (see handout.)
	 * @param rules rules for new Taboo
	 */
	public Taboo(List<T> rules) {
		map = new HashMap<T,Set<T>> ();
		for(int i = 0; i < rules.size()-1; i++) {
			T t1 = rules.get(i);
			T t2 = rules.get(i+1);
			if(t1 == null || t2 == null) {
				continue;
			}
			if(!map.containsKey(t1)) {
				map.put(t1, new HashSet<T> ());
			}
			map.get(t1).add(t2);
		}
	}
	
	/**
	 * Returns the set of elements which should not follow
	 * the given element.
	 * @param elem
	 * @return elements which should not follow the given element
	 */
	public Set<T> noFollow(T elem) {
		if(map.containsKey(elem)) {
			return map.get(elem);
		}
		return Collections.emptySet(); // TODO YOUR CODE HERE
	}
	
	/**
	 * Removes elements from the given list that
	 * violate the rules (see handout).
	 * @param list collection to reduce
	 */
	
	public void reduce(List<T> list) {
		if(list.isEmpty() || list.size() == 1) {
			return;
		}
		T prev = list.get(0);
		int i = 1;
		while(i < list.size()) {
			T curr = list.get(i);
			if(prev != null && curr != null &&
					map.containsKey(prev) && map.get(prev).contains(curr)) {
				list.remove(i);
			} else {
				prev = curr;
				i++;
			}
		}
	}
}