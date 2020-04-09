//
// TetrisGrid encapsulates a tetris board and has
// a clearRows() capability.
package assign1;

public class TetrisGrid {
	private boolean[][] grid;
	
	/**
	 * Constructs a new instance with the given grid.
	 * Does not make a copy.
	 * @param grid
	 */
	public TetrisGrid(boolean[][] grid) {
		this.grid = grid;
	}
	
	
	/**
	 * Does row-clearing on the grid (see handout).
	 */
	public void clearRows() {
		if(grid.length == 0 || grid[0].length == 0) {
			return;
		}
		int idx = 0;
		search: for(int c = 0; c < grid[0].length; c++) {
			boolean allT = true, allF = true;
			for(int r = 0; r < grid.length; r++) {
				if(!grid[r][c]) {
					allT = false;
				} else {
					allF = false;
				}
			}
			if(!(allT || allF)) {
				for(int r = 0; r < grid.length; r++) {
					grid[r][idx] = grid[r][c];
				}
				idx++;
			}
			if(allF) {
				break search;
			}
		}
		while(idx < grid[0].length) {
			for(int r = 0; r < grid.length; r++) {
				grid[r][idx] = false;
			}
			idx++;
		}
	}
	
	/**
	 * Returns the internal 2d grid array.
	 * @return 2d grid array
	 */
	boolean[][] getGrid() {
		return grid; // TODO YOUR CODE HERE
	}
}
