// Test cases for CharGrid -- a few basic tests are provided.
package assign1;

import static org.junit.Assert.*;
import org.junit.Test;

public class CharGridTest {
	
	@Test
	public void testCharArea1() {
		char[][] grid = new char[][] {
				{'a', 'y', ' '},
				{'x', 'a', 'z'},
			};
		
		
		CharGrid cg = new CharGrid(grid);
				
		assertEquals(4, cg.charArea('a'));
		assertEquals(1, cg.charArea('z'));
	}
	
	
	@Test
	public void testCharArea2() {
		char[][] grid = new char[][] {
				{'c', 'a', ' '},
				{'b', ' ', 'b'},
				{' ', ' ', 'a'},
			};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(6, cg.charArea('a'));
		assertEquals(3, cg.charArea('b'));
		assertEquals(1, cg.charArea('c'));
	}
	
	// TODO Add more tests
	@Test
	public void testCharArea3() {
		char[][] grid = new char[][] {
				{'2', 'c', '0'},
				{'b', '2', 'e'},
				{'i', 'c', 'l'},
				{'e', 'i', 'a'},
				{'n', 'i', 'c'},
			};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(10, cg.charArea('c'));
		assertEquals(6, cg.charArea('i'));
		assertEquals(1, cg.charArea('0'));
		assertEquals(4, cg.charArea('2'));
	}
	
	@Test
	public void testCharArea4() {
		char[][] grid = new char[][] {
				{' ', ' '},
				{' ', ' '},
			};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(4, cg.charArea(' '));
		assertEquals(0, cg.charArea('i'));
		assertEquals(0, cg.charArea('0'));
	}
	
	@Test
	public void testCharArea5() {
		char[][] grid = new char[][] {
			{'2', 'c', ' ', '!'},
			{'@', 'p', 'e', '@'},
			{'i', 'c', 'l', 'i'},
			{'e', 'i', 'a', 'l'},
			{'n', ' ', 'u', '2'},
			};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(20, cg.charArea('2'));
		assertEquals(4, cg.charArea('@'));
		assertEquals(8, cg.charArea('i'));
	}
	
	@Test
	public void testCharArea6() {
		char[][] grid = new char[][] {
			{},
			};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(0, cg.charArea('2'));
		assertEquals(0, cg.charArea('@'));
		assertEquals(0, cg.charArea('i'));
	}
	
	@Test
	public void testCountPlus1() {
		char[][] grid = new char[][] {
			{' ', ' ', 'p', ' ', ' ', ' ', ' ', ' ', ' '},
			{' ', ' ', 'p', ' ', ' ', ' ', ' ', 'x', ' '},
			{'p', 'p', 'p', 'p', 'p', ' ', 'x', 'x', 'x'},
			{' ', ' ', 'p', ' ', ' ', 'y', ' ', 'x', ' '},
			{' ', ' ', 'p', ' ', 'y', 'y', 'y', ' ', ' '},
			{'z', 'z', 'z', 'z', 'z', 'y', 'z', 'z', 'z'},
			{' ', ' ', 'x', 'x', ' ', 'y', 'y', ' ', ' '},
			};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(2, cg.countPlus());
	}
	
	@Test
	public void testCountPlus2() {
		char[][] grid = new char[][] {
			{' ', ' ', 'p', ' ', ' ', ' ', ' ', ' ', ' '},
			{' ', ' ', 'p', ' ', ' ', ' ', ' ', 'x', ' '},
			{'p', 'p', 'p', 'p', 'p', ' ', 'x', 'x', 'x'},
			{' ', ' ', 'p', ' ', ' ', 'y', ' ', 'x', ' '},
			{' ', ' ', 'p', ' ', 'y', 'y', 'y', 'x', ' '},
			{'z', 'z', 'z', 'z', 'z', 'y', 'z', 'z', 'z'},
			{' ', ' ', 'x', 'x', ' ', 'y', 'y', ' ', ' '},
			};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(1, cg.countPlus());
	}
	
	@Test
	public void testCountPlus3() {
		char[][] grid = new char[][] {
			{' ', ' ', 'p', ' ', ' ', ' ', ' ', ' ', ' '},
			{' ', ' ', 'p', ' ', ' ', ' ', ' ', 'x', ' '},
			{'p', 'p', 'p', 'p', 'p', 'p', 'x', 'x', 'x'},
			{' ', ' ', 'p', ' ', ' ', 'y', ' ', 'x', ' '},
			{' ', ' ', 'p', ' ', 'y', 'y', 'y', 'x', ' '},
			{'z', 'z', 'z', 'z', 'z', 'y', 'z', 'z', 'z'},
			{' ', ' ', 'x', 'x', ' ', 'y', 'y', ' ', ' '},
			};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(0, cg.countPlus());
	}
	
	@Test
	public void testCountPlus4() {
		char[][] grid = new char[][] {
			{' ', ' ', 'p', ' ', ' ', ' ', ' ', ' ', ' '},
			{' ', ' ', 'p', ' ', ' ', ' ', ' ', '.', ' '},
			{'p', 'p', 'p', 'p', 'p', 'p', '.', '.', '.'},
			{' ', ' ', 'p', ' ', ' ', 'y', ' ', '.', ' '},
			{' ', ' ', 'p', ' ', 'y', 'y', 'y', ' ', ' '},
			{'z', 'z', 'z', 'z', 'z', 'y', 'z', 'z', 'z'},
			{' ', ' ', 'x', 'x', ' ', 'y', 'y', ' ', ' '},
			};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(1, cg.countPlus());
	}
	
	@Test
	public void testCountPlus5() {
		char[][] grid = new char[][] {
			{'x', 'x'},
			{'x', 'x'},
			};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(0, cg.countPlus());
	}
	
	@Test
	public void testCountPlus6() {
		char[][] grid = new char[][] {
			{},
			};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(0, cg.countPlus());
	}
}