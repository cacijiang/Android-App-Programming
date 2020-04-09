// HW1 2-d array Problems
// CharGrid encapsulates a 2-d grid of chars and supports
// a few operations on the grid.

package assign1;

public class CharGrid {
	private char[][] grid;

	/**
	 * Constructs a new CharGrid with the given grid.
	 * Does not make a copy.
	 * @param grid
	 */
	public CharGrid(char[][] grid) {
		this.grid = grid;
	}
	
	/**
	 * Returns the area for the given char in the grid. (see handout).
	 * @param ch char to look for
	 * @return area for given char
	 */
	public int charArea(char ch) {
		if(grid.length == 0 || grid[0].length == 0) {
			return 0;
		}
		int left = Integer.MAX_VALUE, right = Integer.MIN_VALUE, top = Integer.MAX_VALUE,
				bottom = Integer.MIN_VALUE;
		for(int i = 0; i < grid.length; i ++) {
			for(int j = 0; j < grid[0].length; j++) {
				if(grid[i][j] == ch) {
					if(i < left) {
						left = i;
					}
					if(i > right) {
						right = i;
					}
					if(j < top) {
						top = j;
					}
					if(j > bottom) {
						bottom = j;
					}
				}
			}
		}
		
		return left == Integer.MAX_VALUE ? 0 : (right-left+1)*(bottom-top+1); // TODO ADD YOUR CODE HERE
	}
	
	/**
	 * Returns the count of '+' figures in the grid (see handout).
	 * @return number of + in grid
	 */
	public int countPlus() {
		if(grid.length == 0 || grid[0].length == 0) {
			return 0;
		}
		int count = 0;
		for(int i = 1; i < grid.length; i++) {
			for(int j = 1; j < grid[0].length; j++) {
				if(isPlus(i, j)) {
					count++;
				}
			}
		}
		return count; // TODO ADD YOUR CODE HERE
	}
	
	private boolean isPlus(int x, int y) {
		char ch = grid[x][y];
		int arm1 = 0, k = x;
		// top arm
		while(k >= 0) {
			if(grid[k][y] != ch) {
				break;
			}
			arm1++;
			k--;
		}
		if(arm1 == 1) {
			return false;
		}
		// bottom arm
		k = x;
		int arm2 = 0;
		while(k < grid.length) {
			if(grid[k][y] != ch) {
				break;
			}
			arm2++;
			k++;
		}
		if(arm1 != arm2) {
			return false;
		}
		k = y;
		arm2 = 0;
		// left arm
		while(k >= 0) {
			if(grid[x][k] != ch) {
				break;
			}
			arm2++;
			k--;
		}
		if(arm1 != arm2) {
			return false;
		}
		k = y;
		arm2 = 0;
		// right arm
		while(k < grid[0].length) {
			if(grid[x][k] != ch) {
				break;
			}
			arm2++;
			k++;
		}
		if(arm1 != arm2) {
			return false;
		}
		return true;
	}
}
