package puzzle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
                System.out.print(board[i][j] + "\t");
            }
            // Print a newline after each row
            System.out.println();
        }
	}
	
	public void printBoardWithRegion(){
		
		String [][] boardRegion = new String[board.length][board.length];
		
		for (int i = 0; i < board.length; i++) {            
            for (int j = 0; j < board[i].length; j++) {            
                boardRegion[i][j] = Integer.toString( board[i][j]);
            }            
        }
		for (Region region : regions){
			for (Coordenada coordenada: region.nodos){
				boardRegion[coordenada.getRow()][coordenada.getCol()] =  Character.toString(region.operator)+Integer.toString(region.target);				
			}
		}
		
				
		for (int i = 0; i < boardRegion.length; i++) {
            // Loop through the columns
            for (int j = 0; j < boardRegion[i].length; j++) {
                // Print the element at matrix[i][j] followed by a space
                System.out.print(boardRegion[i][j] + " \t");
            }
            // Print a newline after each row
            System.out.println();
        }
	}
	
	public boolean isGoalState(){
		//int [][] board = currentState.board;
		
		if (isValueRepeatedInRowOrColumn()) {
			return false;
		}
		
	
		// Evaluating regions
		for (Region region : getRegions()) {
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
	
	private boolean isValueRepeatedInRowOrColumn() {
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
	
	
	//pruningStrategy return true if the node must be eliminate in the tree 
	public boolean pruningStrategy(){
			
			//int [][] board = currentState.board;
			
			if (isValueRepeatedInRowOrColumn()) {
				return true;
			}
			
			for (Region region : getRegions()) {
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
	                	
	                	if (Math.abs(firstValue - secondValue) != region.target && firstValue>0 && secondValue>0) {
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
