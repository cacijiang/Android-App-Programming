// StringCodeTest
// Some test code is provided for the early HW1 problems,
// and much is left for you to add.

package assign1;

import static org.junit.Assert.*;
import org.junit.Test;

public class StringCodeTest {
	//
	// blowup
	//
	@Test
	public void testBlowup1() {
		// basic cases
		assertEquals("xxaaaabb", StringCode.blowup("xx3abb"));
		assertEquals("xxxZZZZ", StringCode.blowup("2x3Z"));
	}
	
	@Test
	public void testBlowup2() {
		// things with digits
		
		// digit at end
		assertEquals("axxx", StringCode.blowup("a2x3"));
		
		// digits next to each other
		assertEquals("a33111", StringCode.blowup("a231"));
		
		// try a 0
		assertEquals("aabb", StringCode.blowup("aa0bb"));
	}
	
	@Test
	public void testBlowup3() {
		// weird chars, empty string
		assertEquals("AB&&,- ab", StringCode.blowup("AB&&,- ab"));
		assertEquals("", StringCode.blowup(""));
		
		// string with only digits
		assertEquals("", StringCode.blowup("2"));
		assertEquals("33", StringCode.blowup("23"));
	}
	
	@Test
	public void testBlowup4() {
		// string without digit
		assertEquals("aabbbcaxx", StringCode.blowup("aabbbcaxx"));
		assertEquals("#$^%$#@", StringCode.blowup("#$^%$#@"));
		
		// string with white spaces
		assertEquals("  ajjjjjkkkk", StringCode.blowup("  a3jj3k2"));
		assertEquals("   abcee", StringCode.blowup("2 abc1e"));
	}
	
	@Test
	public void testBlowup5() {
		// more test cases
		assertEquals("djjlxxxjka", StringCode.blowup("djjl2xjk0a"));
		assertEquals("higkddd  -@!", StringCode.blowup("higk2d  -@!"));
	}
	
	//
	// maxRun
	//
	@Test
	public void testRun1() {
		assertEquals(2, StringCode.maxRun("hoopla"));
		assertEquals(3, StringCode.maxRun("hoopllla"));
	}
	
	@Test
	public void testRun2() {
		assertEquals(3, StringCode.maxRun("abbcccddbbbxx"));
		assertEquals(0, StringCode.maxRun(""));
		assertEquals(3, StringCode.maxRun("hhhooppoo"));
	}
	
	@Test
	public void testRun3() {
		// "evolve" technique -- make a series of test cases
		// where each is change from the one above.
		assertEquals(1, StringCode.maxRun("123"));
		assertEquals(2, StringCode.maxRun("1223"));
		assertEquals(2, StringCode.maxRun("112233"));
		assertEquals(3, StringCode.maxRun("1112233"));
	}
	
	@Test
	public void testRun4() {
		// mixed types of characters
		assertEquals(5, StringCode.maxRun("ceeeliie11111"));
		assertEquals(5, StringCode.maxRun("ceee     liie111"));
		assertEquals(6, StringCode.maxRun("ceee     liie111@@$#$@%******!"));
	}
	@Test
	public void testRun5() {
		// more test cases
		assertEquals(1, StringCode.maxRun("0"));
		assertEquals(1, StringCode.maxRun("a"));
		assertEquals(3, StringCode.maxRun("a0@@WWAAAaa"));
	}
}

