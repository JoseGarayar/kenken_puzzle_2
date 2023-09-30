package puzzle;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;


public class KenKenSolver {
	Explorados explored;
	
	public KenKenSolver(){
		explored = new Explorados();
	}
	
	public Nodo solve(Nodo initialNode) {
        Borde frontier = new Borde(true); // Change to false to use LIFO (stack)
        frontier.insert(initialNode);
        while (!frontier.isEmpty()) {
        	Nodo currentNode = frontier.pop();
            KenKenState currentState = currentNode.state;
            if (isGoalState(currentState)) {
                return currentNode; // ¡We found the solution!
            }
            explored.insert(currentNode);

            // Generate and add new neighbor nodes to frontier
            List<Nodo> neighborNodes = generateNeighborNodes(currentNode);

            for (Nodo neighborNode : neighborNodes) {
            	/*for (int i = 0; i < neighborNode.state.board.length; i++) {
                    // Loop through the columns
                    for (int j = 0; j < neighborNode.state.board[i].length; j++) {
                        // Print the element at matrix[i][j] followed by a space
                        System.out.print(neighborNode.state.board[i][j] + " ");
                    }
                    // Print a newline after each row
                    System.out.println();
                }*/            	
                if (!explored.buscar(neighborNode) && !pruningStrategy(neighborNode.state)) {
                	neighborNode.state.printBoard(); 
                    frontier.insert(neighborNode);
                }
            }
            frontier.showQueue();
        }

        return null; // Solution not found
    }
	
	private List<Nodo> generateNeighborNodes(Nodo currentNode) {
		KenKenState currentState = currentNode.state;
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
			Nodo neighbourNode = new Nodo(NeighbourState, currentNode, 0, currentNode.profundidad + 1, "");
			listNewNodes.add(neighbourNode);
		}
		return listNewNodes;
		
	}
	
	private boolean isValueRepeatedInRowOrColumn(int[][] board) {
		// Evaluating rows and columns
		int filas = board.length;
        int columnas = board[0].length;
        // Verify rows
        for (int fila = 0; fila < filas; fila++) {
            Set<Integer> elementosVistos = new HashSet<>();
            for (int columna = 0; columna < columnas; columna++) {
                int elemento = board[fila][columna];
                if (elementosVistos.contains(elemento)) {
                    return true; // A repeated element was found in the row
                }
                if (elemento != 0){
                	elementosVistos.add(elemento);
                }
            }
        }
        // Verify columns
        for (int columna = 0; columna < columnas; columna++) {
            Set<Integer> elementosVistos = new HashSet<>();
            for (int fila = 0; fila < filas; fila++) {
                int elemento = board[fila][columna];
                if (elementosVistos.contains(elemento)) {
                    return true; // A repeated element was found in the column
                }
                if (elemento != 0){
                	elementosVistos.add(elemento);
                }
            }
        }
        return false;
	}
	
	private boolean isGoalState(KenKenState currentState){
		int [][] board = currentState.board;
		
		if (isValueRepeatedInRowOrColumn(board)) {
			return false;
		}
		
	
		// Evaluating regions
		for (Region region : currentState.getRegions()) {
			char operator = region.operator;
			
			// for "-" and "/" operators, only if needed
			Iterator <Coordenada> iterador = region.nodos.iterator();
			Coordenada firstCoordenada = iterador.next();
			Coordenada secondCoordenada=null;
			if (iterador.hasNext())
				secondCoordenada = iterador.next();
        	int firstValue = board[firstCoordenada.getRow()][firstCoordenada.getCol()];
        	int secondValue = board[secondCoordenada.getRow()][secondCoordenada.getCol()];

            switch (operator) {
                case '+':
                	int sum = 0;
                	for (Coordenada coordenada: region.nodos){
                		if (board[coordenada.getRow()][coordenada.getCol()]>0)
                			sum = sum + board[coordenada.getRow()][coordenada.getCol()];
                		else
                			return false;
                	}
                	if (sum != region.target) {
                		return false;
                	}
                	break;
                case '-':
                	if (firstValue==0 || secondValue==0) return false;
                	if (Math.abs(firstValue - secondValue) != region.target) {
                		return false;
                	}
                	break;
                case 'x':
                case '*':
                	int prod = 1;
                	for (Coordenada coordenada: region.nodos){
                		if (board[coordenada.getRow()][coordenada.getCol()]>0)
                			prod = prod * board[coordenada.getRow()][coordenada.getCol()];
                		else
                			return false;
                	}
                	if (prod != region.target) {
                		return false;
                	}
                	break;
                case '/':
                	if (firstValue==0 || secondValue==0) return false;
                	
                	if (firstValue > secondValue){
            			if (!(firstValue % secondValue==0 && firstValue / secondValue == region.target))
            				return false;
            		}
            		else if (firstValue < secondValue){
            			if (!(secondValue % firstValue ==0 && secondValue /firstValue  == region.target))
            				return false;
            		}
            		else{
            			if (region.target!=1)
            				return false;
            		}
                	
                	
            }
        }
		
		// If all validations passed, then return true
		return true;
	}
	
	//pruningStrategy return true if the node must be eliminate in the tree 
	private boolean pruningStrategy(KenKenState currentState){
		
		int [][] board = currentState.board;
		
		if (isValueRepeatedInRowOrColumn(board)) {
			return true;
		}
		
		for (Region region : currentState.getRegions()) {
			char operator = region.operator;
			
			// for "-" and "/" operators, only if needed
			Iterator <Coordenada> iterador = region.nodos.iterator();
			Coordenada firstCoordenada = iterador.next();
			Coordenada secondCoordenada=null;
			if (iterador.hasNext())
				secondCoordenada = iterador.next();
        	int firstValue = board[firstCoordenada.getRow()][firstCoordenada.getCol()];
        	int secondValue = board[secondCoordenada.getRow()][secondCoordenada.getCol()];

            switch (operator) {
                case '+':
                	int sum = 0;
                	for (Coordenada coordenada: region.nodos){
                		if (board[coordenada.getRow()][coordenada.getCol()]>0 )
                			sum = sum + board[coordenada.getRow()][coordenada.getCol()];
                	}
                	if (sum > region.target) {
                		return true;
                	}
                	break;
                case '-':
                	
                	if (Math.abs(firstValue - secondValue) < region.target && firstValue>0 && secondValue>0) {
                		return true;
                	}
                	break;
                case 'x':
                case '*':
                	int prod = 1;
                	for (Coordenada coordenada: region.nodos){
                		if (board[coordenada.getRow()][coordenada.getCol()]>0 )
                			prod = prod * board[coordenada.getRow()][coordenada.getCol()];
                	}
                	if (prod > region.target) {
                		return true;
                	}
                	break;
                case '/':
                	if (firstValue!=0 && secondValue!=0){ 
                		if (firstValue > secondValue){
                			if (!(firstValue % secondValue==0 && firstValue / secondValue == region.target))
                				return true;
                		}
                		else if (firstValue < secondValue){
                			if (!(secondValue % firstValue ==0 && secondValue /firstValue  == region.target))
                				return true;
                		}
                		else{
                			if (region.target!=1)
                				return true;
                		}
                		
                	}
                	break;
            }
        }
		
		
		
		return false;
	} 
	
}
