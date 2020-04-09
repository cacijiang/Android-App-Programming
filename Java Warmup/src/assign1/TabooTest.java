// TabooTest.java
// Taboo class tests -- nothing provided.
package assign1;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.*;

public class TabooTest {

	// TODO ADD TESTS
	@Test
	public void testTaboo1() {
		List<String> rules = new ArrayList<> (Arrays.asList(new String[] {"a", "c", "a", "b"}));
		Set<String> seta = new HashSet<> (Arrays.asList(new String[] {"c", "b"}));
		Set<String> setx = new HashSet<> ();
		List<String> before = new ArrayList<> (Arrays.asList(new String[] {"a", "c", "b", "x", "c", "a"}));
		List<String> after = new ArrayList<> (Arrays.asList(new String[] {"a", "x", "c"}));
		
		Taboo<String> tb = new Taboo<>(rules);
		assertEquals(seta, tb.noFollow("a"));
		assertEquals(setx, tb.noFollow("x"));
		tb.reduce(before);
		assertEquals(after, before);
	}
	
	@Test
	public void testTaboo2() {
		List<String> rules = new ArrayList<> (Arrays.asList(new String[] {"a", "c", "b", "x"}));
		Set<String> seta = new HashSet<> (Arrays.asList(new String[] {"c"}));
		Set<String> setb = new HashSet<> (Arrays.asList(new String[] {"x"}));
		List<String> before = new ArrayList<> (Arrays.asList(new String[] {"a", "c", "b", "x", "c", "a"}));
		List<String> after = new ArrayList<> (Arrays.asList(new String[] {"a", "b", "c", "a"}));
		
		Taboo<String> tb = new Taboo<> (rules);
		assertEquals(seta, tb.noFollow("a"));
		assertEquals(setb, tb.noFollow("b"));
		tb.reduce(before);
		assertEquals(after, before);
	}
	
	@Test
	public void testTaboo3() {
		List<String> rules = new ArrayList<> (Arrays.asList(new String[] {"a", "b", null, "c", "d"}));
		Set<String> seta = new HashSet<> (Arrays.asList(new String[] {"b"}));
		Set<String> setb = new HashSet<> (Arrays.asList(new String[] {}));
		List<String> before = new ArrayList<> (Arrays.asList(new String[] {"a", "c", "b", "c", "c", "d"}));
		List<String> after = new ArrayList<> (Arrays.asList(new String[] {"a", "c", "b", "c", "c"}));
		
		Taboo<String> tb = new Taboo<> (rules);
		assertEquals(seta, tb.noFollow("a"));
		assertEquals(setb, tb.noFollow("b"));
		tb.reduce(before);
		assertEquals(after, before);
	}
	
	@Test
	public void testTaboo4() {
		List<Integer> rules = new ArrayList<> (Arrays.asList(new Integer[] {1, 3, 1, 2}));
		Set<Integer> seta = new HashSet<> (Arrays.asList(new Integer[] {2, 3}));
		Set<Integer> setb = new HashSet<> (Arrays.asList(new Integer[] {}));
		List<Integer> before = new ArrayList<> (Arrays.asList(new Integer[] {1, 3, 2, 6, 3, 1}));
		List<Integer> after = new ArrayList<> (Arrays.asList(new Integer[] {1, 6, 3}));
		
		Taboo<Integer> tb = new Taboo<> (rules);
		assertEquals(seta, tb.noFollow(1));
		assertEquals(setb, tb.noFollow(6));
		tb.reduce(before);
		assertEquals(after, before);
	}
	
	@Test
	public void testTaboo5() {
		List<Integer> rules = new ArrayList<> (Arrays.asList(new Integer[] {}));
		Set<Integer> seta = new HashSet<> (Arrays.asList(new Integer[] {}));
		Set<Integer> setb = new HashSet<> (Arrays.asList(new Integer[] {}));
		List<Integer> before = new ArrayList<> (Arrays.asList(new Integer[] {1, 3, 2, 6, 3, 1}));
		List<Integer> after = new ArrayList<> (Arrays.asList(new Integer[] {1, 3, 2, 6, 3, 1}));
		
		Taboo<Integer> tb = new Taboo<> (rules);
		assertEquals(seta, tb.noFollow(1));
		assertEquals(setb, tb.noFollow(6));
		tb.reduce(before);
		assertEquals(after, before);
	}
}