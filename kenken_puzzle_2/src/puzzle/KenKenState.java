package puzzle;

import java.util.ArrayList;
import java.util.List;

public class KenKenState {
	int[][] board;
	private List<Region> regions;
	
	public KenKenState(int size){
		board = new int[size][size];
        regions = new ArrayList<>();
	}
	
	public void setCellValue(int row, int col, int value) {
        board[row][col] = value;
    }
	
	public void addRegion(int target, char operator, List<Coordenada> nodos) {
        Region region = new Region();
        region.target = target;
        region.operator = operator;
        region.nodos = nodos;

        regions.add(region);
    }
	
	public List<Region> getRegions() {
		return regions;
	}
	
	public void  setRegions(List<Region> region) {
		regions = region;
	}
	
	public void printBoard(){
		for (int i = 0; i < board.length; i++) {
            // Loop through the columns
            for (int j = 0; j < board[i].length; j++) {
                // Print the element at matrix[i][j] followed by a space
                System.out.print(board[i][j] + " ");
            }
            // Print a newline after each row
            System.out.println();
        }
	}
}
