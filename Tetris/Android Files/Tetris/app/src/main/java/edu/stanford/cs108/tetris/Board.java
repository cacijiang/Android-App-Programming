// Board.java
package edu.stanford.cs108.tetris;

/**
 CS108 Tetris Board.
 Represents a Tetris board -- essentially a 2-d grid
 of booleans. Supports tetris pieces and row clearing.
 Has an "undo" feature that allows clients to add and remove pieces efficiently.
 Does not do any drawing or have any idea of pixels. Instead,
 just represents the abstract 2-d board.
 */
public class Board	{
	// Some ivars are stubbed out for you:
	private int width;
	private int height;
	private boolean[][] grid;
	private boolean DEBUG = true;
	private int maxHeight;
	boolean committed;
	private int[] widths;
	private int[] heights;
	private boolean[][] xGrid;
	private int[] xWidths;
	private int[] xHeights;
	private int xMaxHeight;

	// Here a few trivial methods are provided:

	/**
	 Creates an empty board of the given width and height
	 measured in blocks.
	 */
	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		grid = new boolean[width][height];
		maxHeight = 0;
		committed = true;

		// YOUR CODE HERE
		widths = new int[height];
		heights = new int[width];
		xGrid = new boolean[width][height];
		xWidths = new int[height];
		xHeights = new int[width];
		xMaxHeight = 0;
	}


	/**
	 Returns the width of the board in blocks.
	 */
	public int getWidth() {
		return width;
	}


	/**
	 Returns the height of the board in blocks.
	 */
	public int getHeight() {
		return height;
	}


	/**
	 Returns the max column height present in the board.
	 For an empty board this is 0.
	 */
	public int getMaxHeight() {
		return maxHeight; // YOUR CODE HERE
	}


	/**
	 Checks the board for internal consistency -- used
	 for debugging.
	 */
	public void sanityCheck() {
		if (DEBUG) {
			// YOUR CODE HERE
			// check widths
			for(int c = 0; c < height; c++) {
				int num = 0;
				for(int r = 0; r < width; r++) {
					if(grid[r][c]) {
						num++;
					}
				}
				if(num != widths[c]) {
					throw new RuntimeException(String.format("widths at y = %d is %d, checks should be %d", c, widths[c], num));
				}
			}

			// check heights and max height
			int checkMaxHeight = 0;
			for(int r = 0; r < width; r++) {
				int highest = -1;
				for(int c = 0; c < height; c++) {
					if(grid[r][c] && c > highest) {
						highest = c;
					}
				}
				checkMaxHeight = Math.max(highest+1, checkMaxHeight);
				if(highest+1 != heights[r]) {
					throw new RuntimeException(String.format("heights at x = %d is %d, checks should be %d", r, heights[r], highest+1));
				}
			}

			if(checkMaxHeight != maxHeight) {
				throw new RuntimeException(String.format("max height is %d, checks should be %d", maxHeight, checkMaxHeight));
			}
		}
	}

	/**
	 Given a piece and an x, returns the y
	 value where the piece would come to rest
	 if it were dropped straight down at that x.

	 <p>
	 Implementation: use the skirt and the col heights
	 to compute this fast -- O(skirt length).
	 */
	public int dropHeight(Piece piece, int x) {
		// YOUR CODE HERE
		int rest = 0;
		for (int i = 0; i < piece.getWidth(); i++) {
			rest = Math.max(heights[x+i]-piece.getSkirt()[i], rest);
		}
		return rest;
	}


	/**
	 Returns the height of the given column --
	 i.e. the y value of the highest block + 1.
	 The height is 0 if the column contains no blocks.
	 */
	public int getColumnHeight(int x) {
		return heights[x]; // YOUR CODE HERE
	}


	/**
	 Returns the number of filled blocks in
	 the given row.
	 */
	public int getRowWidth(int y) {
		return widths[y]; // YOUR CODE HERE
	}


	/**
	 Returns true if the given block is filled in the board.
	 Blocks outside of the valid width/height area
	 always return true.
	 */
	public boolean getGrid(int x, int y) {
		if(x < 0 || x >= width || y < 0 || y >= height || grid[x][y]) {
			return true;
		}
		return false; // YOUR CODE HERE
	}


	public static final int PLACE_OK = 0;
	public static final int PLACE_ROW_FILLED = 1;
	public static final int PLACE_OUT_BOUNDS = 2;
	public static final int PLACE_BAD = 3;

	/**
	 Attempts to add the body of a piece to the board.
	 Copies the piece blocks into the board grid.
	 Returns PLACE_OK for a regular placement, or PLACE_ROW_FILLED
	 for a regular placement that causes at least one row to be filled.

	 <p>Error cases:
	 A placement may fail in two ways. First, if part of the piece may falls out
	 of bounds of the board, PLACE_OUT_BOUNDS is returned.
	 Or the placement may collide with existing blocks in the grid
	 in which case PLACE_BAD is returned.
	 In both error cases, the board may be left in an invalid
	 state. The client can use undo(), to recover the valid, pre-place state.
	 */
	public int place(Piece piece, int x, int y) {
		// flag !committed problem
		if (!committed) throw new RuntimeException("place commit problem");

		int result = PLACE_OK;
		// back up
		for(int r = 0; r < width; r++) {
			System.arraycopy(grid[r], 0, xGrid[r], 0, height);
		}
		System.arraycopy(widths, 0, xWidths, 0, height);
		System.arraycopy(heights, 0, xHeights, 0, width);
		xMaxHeight = maxHeight;

		if(x + piece.getWidth() > width || y + piece.getHeight() > height || x < 0 || y < 0) {
			result = PLACE_OUT_BOUNDS;
		} else {
			for(TPoint point : piece.getBody()) {
				int r = point.x+x;
				int c = point.y+y;
//				System.out.println(String.format("(%d, %d)", r, c));
				if(grid[r][c]) {
					result =  PLACE_BAD;
					committed = false;
					return result;
				}
				grid[r][c] = true;
				widths[c]++;
				if(c >= heights[r]) {
					heights[r] = c+1;
					maxHeight = Math.max(maxHeight, heights[r]);
				}
				if(widths[c] == width) {
					result = PLACE_ROW_FILLED;
				}
			}

		}
		committed = false;
		// YOUR CODE HERE
		sanityCheck();

		return result;
	}
	/**
	 Deletes rows that are filled all the way across, moving
	 things above down. Returns the number of rows cleared.
	 */
	public int clearRows() {
		int rowsCleared = 0;
		// YOUR CODE HERE
		if(committed) {
			for(int r = 0; r < width; r++) {
				System.arraycopy(grid[r], 0, xGrid[r], 0, height);
			}
			System.arraycopy(widths, 0, xWidths, 0, height);
			System.arraycopy(heights, 0, xHeights, 0, width);
			xMaxHeight = maxHeight;
		}
		int idx = 0;
		heights = new int[width];
		for(int c = 0; c < maxHeight; c++) {
			if(widths[c] < width) {
				for(int r = 0; r < width; r++) {
					grid[r][idx] = grid[r][c];
					if(grid[r][idx] == true) {
						heights[r] = idx+1;
					}
				}
				widths[idx] = widths[c];
				idx++;
			}
		}
		rowsCleared = maxHeight-idx;
		while(idx < maxHeight) {
			for(int r = 0; r < width; r++) {
				grid[r][idx] = false;
			}
			widths[idx] = 0;
			idx++;
		}
		maxHeight = heights[0];
		for(int hi : heights) {
			maxHeight = Math.max(maxHeight, hi);
		}
		committed = false;
		sanityCheck();
		return rowsCleared;
	}

	/**
	 Reverts the board to its state before up to one place
	 and one clearRows();
	 If the conditions for undo() are not met, such as
	 calling undo() twice in a row, then the second undo() does nothing.
	 See the overview docs.
	 */
	public void undo() {
		// YOUR CODE HERE
		if(!committed) {
			boolean[][] tempGrid = grid;
			grid = xGrid;
			xGrid = tempGrid;

			int[] tempWidths = widths;
			widths = xWidths;
			xWidths = tempWidths;

			int[] tempHeights = heights;
			heights = xHeights;
			xHeights = tempHeights;

			int tempMaxHeight = maxHeight;
			maxHeight = xMaxHeight;
			xMaxHeight = tempMaxHeight;

			commit();
		}
		sanityCheck();
	}


	/**
	 Puts the board in the committed state.
	 */
	public void commit() {
		committed = true;
	}

	/*
	 Renders the board state as a big String, suitable for printing.
	 This is the sort of print-obj-state utility that can help see complex
	 state change over time.
	 (provided debugging utility)
	 */
	public String toString() {
		StringBuilder buff = new StringBuilder();
		for (int y = height-1; y>=0; y--) {
			buff.append('|');
			for (int x=0; x<width; x++) {
				if (getGrid(x,y)) buff.append('+');
				else buff.append(' ');
			}
			buff.append("|\n");
		}
		for (int x=0; x<width+2; x++) buff.append('-');
		return(buff.toString());
	}
}


