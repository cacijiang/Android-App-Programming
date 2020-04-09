package edu.stanford.cs108.tetris;

import static org.junit.Assert.*;
import java.util.*;

import org.junit.*;

/*
  Unit test for Piece class -- starter shell.
 */
public class PieceTest {
	// You can create data to be used in the your
	// test cases like this. For each run of a test method,
	// a new PieceTest object is created and setUp() is called
	// automatically by JUnit.
	// For example, the code below sets up some
	// pyramid and s pieces in instance variables
	// that can be used in tests.
	private Piece pyr1, pyr2, pyr3, pyr4;
	private Piece s, sRotated;
	
	private Piece pyr5;
	private Piece l1, l2, l3, l4, l5;
	private Piece[] pieces;

	@Before
	public void setUp() throws Exception {
		
		pyr1 = new Piece(Piece.PYRAMID_STR);
		pyr2 = pyr1.computeNextRotation();
		pyr3 = pyr2.computeNextRotation();
		pyr4 = pyr3.computeNextRotation();
		
		s = new Piece(Piece.S1_STR);
		sRotated = s.computeNextRotation();
		
		pyr5 = pyr4.computeNextRotation();
		
		l1 = new Piece(Piece.L1_STR);
		l2 = l1.computeNextRotation();
		l3 = l2.computeNextRotation();
		l4 = l3.computeNextRotation();
		l5 = l4.computeNextRotation();
		
		pieces = Piece.getPieces();
	}
	
	// Here are some sample tests to get you started
	
	@Test
	public void testSampleSize() {
		// Check size of pyr piece
		assertEquals(3, pyr1.getWidth());
		assertEquals(2, pyr1.getHeight());
		
		// Now try after rotation
		// Effectively we're testing size and rotation code here
		assertEquals(2, pyr2.getWidth());
		assertEquals(3, pyr2.getHeight());
		
		// Now try with some other piece, made a different way
		Piece l = new Piece(Piece.STICK_STR);
		assertEquals(1, l.getWidth());
		assertEquals(4, l.getHeight());
	}
	
	
	// Test the skirt returned by a few pieces
	@Test
	public void testSampleSkirt() {
		// Note must use assertTrue(Arrays.equals(... as plain .equals does not work
		// right for arrays.
		assertTrue(Arrays.equals(new int[] {0, 0, 0}, pyr1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0, 1}, pyr3.getSkirt()));
		
		assertTrue(Arrays.equals(new int[] {0, 0, 1}, s.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0}, sRotated.getSkirt()));
	}
	
	@Test
	public void testSampleSize2() {
		assertEquals(3, pyr3.getWidth());
		assertEquals(2, pyr3.getHeight());
		assertEquals(2, pyr4.getWidth());
		assertEquals(3, pyr4.getHeight());
		
		assertTrue(!pyr1.equals(pyr4));
		assertTrue(pyr5.equals(pyr1));
	}
	
	@Test
	public void testSampleSkirt2() {
		assertTrue(Arrays.equals(new int[] {1, 0}, pyr2.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 1}, pyr4.getSkirt()));
	}
	
	@Test
	public void testSampleSize3() {
		assertEquals(2, l1.getWidth());
		assertEquals(3, l1.getHeight());
		assertEquals(3, l2.getWidth());
		assertEquals(2, l2.getHeight());
		
		assertTrue(!l1.equals(l3));
		assertTrue(!l1.equals(l4));
		assertTrue(l1.equals(l5));
	}
	
	@Test
	public void testSampleSkirt3() {
		assertTrue(Arrays.equals(new int[] {0, 0}, l1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {2, 0}, l3.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 1, 1}, l4.getSkirt()));
	}
	
	@Test
	public void testPieces4() {
		// l2
		assertTrue(!pieces[2].equals(pieces[2].fastRotation()));
		assertTrue(!pieces[2].equals(pieces[2].fastRotation().fastRotation()));
		assertTrue(!pieces[2].equals(pieces[2].fastRotation().fastRotation().fastRotation()));
		assertTrue(pieces[2].equals(pieces[2].fastRotation().fastRotation().fastRotation().fastRotation()));
		
		// s2
		assertTrue(!pieces[4].equals(pieces[4].fastRotation()));
		assertTrue(pieces[4].equals(pieces[4].fastRotation().fastRotation()));
		
		//square
		assertTrue(pieces[5].equals(pieces[5].fastRotation()));
	}
	
	@Test
	public void testPieces5() {
		// stick
		Piece l = pieces[0];
		assertTrue(Arrays.equals(new int[] {0}, l.getSkirt()));
		assertTrue(!l.equals(l.fastRotation()));
		assertEquals(4, l.fastRotation().getWidth());
		assertEquals(1, l.fastRotation().getHeight());
		assertTrue(Arrays.equals(new int[] {0, 0, 0, 0}, l.fastRotation().getSkirt()));
		assertTrue(l.equals(l.fastRotation().fastRotation()));
	}
}
