package edu.stanford.cs108.tetris;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BoardTest {
	private Board board;
	private Piece s, pyr;
	private Piece[] pieces;
	
	@Before
	public void setUp() throws Exception {
		board = new Board(10, 20);
		s = new Piece(Piece.S1_STR);
		pyr = new Piece(Piece.PYRAMID_STR);
		pieces = Piece.getPieces();
	}
	
	@Test
	public void test1() {
		// check basic dimensions
		assertEquals(10, board.getWidth());
		assertEquals(20, board.getHeight());
		assertEquals(0, board.getMaxHeight());
		assertEquals(0, board.getRowWidth(0));
		assertEquals(0, board.getColumnHeight(0));
	}
	
	@Test
	public void test2() {
		// test droHeight()
		assertEquals(0, board.dropHeight(s, 0));
		assertEquals(0, board.dropHeight(pyr, 5));
		assertEquals(0, board.dropHeight(pieces[0], 8));
		assertEquals(0, board.dropHeight(pieces[4], 7));
		assertEquals(0, board.dropHeight(pieces[6].fastRotation(), 3)); 
	}
	
	@Test
	public void test3() {
		// check place()
		assertEquals(Board.PLACE_OK, board.place(s, 0, 0));
		board.commit();
		assertEquals(2, board.getRowWidth(0));
		assertEquals(2, board.getRowWidth(1));
		assertEquals(0, board.getRowWidth(2));
		assertEquals(1, board.getColumnHeight(0));
		assertEquals(2, board.getColumnHeight(1));
		assertEquals(2, board.getColumnHeight(2));
		assertEquals(2, board.getMaxHeight());
		assertTrue(board.getGrid(0, 0));
		assertTrue(!board.getGrid(0, 1));
		
		Piece pyr4 = pieces[6].fastRotation().fastRotation().fastRotation();
		assertEquals(2, board.dropHeight(pyr, 0));
		assertEquals(1, board.dropHeight(pyr4, 0));
		
		Piece l = pieces[0];
		assertEquals(Board.PLACE_OK, board.place(l, 3, 0));
		board.commit();
		assertEquals(3, board.getRowWidth(0));
		assertEquals(3, board.getRowWidth(1));
		assertEquals(1, board.getRowWidth(3));
		assertEquals(4, board.getColumnHeight(3));
		assertEquals(0, board.getColumnHeight(4));
		assertEquals(4, board.getMaxHeight());
		
		Piece s2 = pieces[4].fastRotation();
		assertEquals(3, board.dropHeight(s2, 2));
		assertEquals(Board.PLACE_OK, board.place(s2, 2, 3));
		board.commit();
		assertEquals(2, board.getRowWidth(3));
		assertEquals(2, board.getRowWidth(4));
		assertEquals(1, board.getRowWidth(5));
		assertEquals(5, board.getColumnHeight(2));
		assertEquals(6, board.getColumnHeight(3));
		assertEquals(6, board.getMaxHeight());
	}
	
	@Test
	public void test4() {
		// check undo
		assertEquals(Board.PLACE_OK, board.place(pyr, 0, 0));
		board.commit();
		
		assertEquals(Board.PLACE_OUT_BOUNDS,  board.place(s, 11, 0));
		board.commit();
		
		Piece l = pieces[0];
		assertEquals(Board.PLACE_BAD, board.place(l, 0, 0));
		board.undo();
		
		
		assertEquals(Board.PLACE_OK, board.place(l, 2, 1));
		board.commit();
		board.undo();
		assertEquals(3, board.getRowWidth(0));
		assertEquals(2, board.getRowWidth(1));
		assertEquals(5, board.getColumnHeight(2));
		assertEquals(5, board.getMaxHeight());
		
		Piece square = pieces[5];
		assertEquals(Board.PLACE_OK, board.place(square, 8, 0));
		assertEquals(5, board.getRowWidth(0));
		assertEquals(4, board.getRowWidth(1));
		assertEquals(2, board.getColumnHeight(8));
		assertEquals(2, board.getColumnHeight(9));
		board.undo();
		assertEquals(3, board.getRowWidth(0));
		assertEquals(2, board.getRowWidth(1));
		assertEquals(0, board.getColumnHeight(8));
		assertEquals(0, board.getColumnHeight(9));
	}
	
	@Test
	public void test5() {
		// check clearRows()
		Piece l2 = pieces[0].fastRotation();	
		assertEquals(Board.PLACE_OK, board.place(l2, 0, 0));
		board.commit();
		assertEquals(Board.PLACE_OK, board.place(l2, 4, 0));
		board.commit();
		Piece square = pieces[5];
		assertEquals(Board.PLACE_ROW_FILLED, board.place(square, 8, 0));
		board.commit();
		assertEquals(10, board.getRowWidth(0));
		assertEquals(2, board.getMaxHeight());
		
		assertEquals(1, board.clearRows());
		board.commit();
		assertEquals(2, board.getRowWidth(0));
		assertEquals(1, board.getMaxHeight());
		
		assertEquals(Board.PLACE_OK, board.place(l2, 0, 0));
		board.commit();
		assertEquals(Board.PLACE_ROW_FILLED, board.place(l2, 4, 0));
		board.commit();
		assertEquals(Board.PLACE_OK, board.place(pyr, 0, 1));
		board.commit();
		assertEquals(Board.PLACE_OK, board.place(pyr, 3, 1));
		board.commit();
		assertEquals(Board.PLACE_OK, board.place(pyr, 6, 1));
		board.commit();
		assertEquals(9, board.getRowWidth(1));
		assertEquals(3, board.getRowWidth(2));
		assertEquals(1, board.getColumnHeight(9));
		assertEquals(3, board.getMaxHeight());
		
		Piece l = pieces[0];
		assertEquals(Board.PLACE_ROW_FILLED, board.place(l, 9, 1));
		assertEquals(10, board.getRowWidth(1));
		assertEquals(4, board.getRowWidth(2));
		assertEquals(5, board.getColumnHeight(9));
		assertEquals(5, board.getMaxHeight());
		
		assertEquals(2, board.clearRows());
		assertEquals(4, board.getRowWidth(0));
		assertEquals(1, board.getRowWidth(1));
		assertEquals(3, board.getColumnHeight(9));
		assertEquals(3, board.getMaxHeight());
		
		board.undo();
		assertEquals(9, board.getRowWidth(1));
		assertEquals(3, board.getRowWidth(2));
		assertEquals(1, board.getColumnHeight(9));
		assertEquals(3, board.getMaxHeight());
	}
	
	@Test
	public void test6() {
		Piece l2 = pieces[0].fastRotation();
		Piece square = pieces[5];
		assertEquals(Board.PLACE_OK, board.place(l2, 0, 0));
		board.commit();
		assertEquals(Board.PLACE_OK, board.place(l2, 4, 0));
		board.commit();
		assertEquals(Board.PLACE_OK, board.place(l2, 6, 1));
		board.commit();
		assertEquals(Board.PLACE_OK, board.place(l2, 2, 1));
		board.commit();
		assertEquals(Board.PLACE_ROW_FILLED, board.place(square, 0, 1));
		board.commit();
		assertEquals(Board.PLACE_OK, board.place(pyr, 2, 2));
		board.commit();
		
		assertEquals(1, board.clearRows());
		board.commit();
	}
}
