package puzzle;

import java.util.ArrayList;
import java.util.List;

public class Nodo {
	KenKenState state;
	Nodo parent;
	int costoCamino;
	int profundidad;
	String accion;
	
	public Nodo(KenKenState state, Nodo parent, int costoCamino, int profundidad, String accion) {
		this.state = state;
		this.parent = parent;
		this.costoCamino = costoCamino;
		this.profundidad = profundidad;
		this.accion = accion;
	}	
	
	public List<Nodo> generateNeighborNodes() {
		KenKenState currentState = this.state;
		int[][] currentBoard = currentState.board;
		// this values represent the next coordinate from where we generate new nodes
		int rowCoordenada = -1;
		int colCoordenada = -1;
		
		// Find next 0 value in matrix
		outerLoop:
		for (int row = 0; row < currentBoard.length; row++){
			for (int col = 0; col < currentBoard.length; col++) {
				if (currentBoard[row][col] == 0) {
					colCoordenada = col;
					rowCoordenada = row;
					break outerLoop;
				}
			}
		}
		
		// Generate new nodes
		List<Nodo> listNewNodes = new ArrayList<>();
		// Case there is no more nodes to create
		if (rowCoordenada == -1 || colCoordenada == -1) {
			return listNewNodes;
		}
		for (int value = 1; value <= currentBoard.length; value++) {
			currentBoard[rowCoordenada][colCoordenada] = value;
			int [][] newBoard = new int[currentBoard.length][currentBoard.length];
			// Copy values from the source matrix to the destination matrix
	        for (int i = 0; i < currentBoard.length; i++) {
	            for (int j = 0; j < currentBoard[i].length; j++) {
	            	newBoard[i][j] = currentBoard[i][j];
	            }
	        }
	        // Setting new value
	        newBoard[rowCoordenada][colCoordenada] = value;
			KenKenState NeighbourState = new KenKenState(newBoard.length);
			NeighbourState.board = newBoard;
			NeighbourState.setRegions(currentState.getRegions());
			Nodo neighbourNode = new Nodo(NeighbourState, this, this.costoCamino+1, this.profundidad + 1, "");
			listNewNodes.add(neighbourNode);
		}
		return listNewNodes;
		
	}
}