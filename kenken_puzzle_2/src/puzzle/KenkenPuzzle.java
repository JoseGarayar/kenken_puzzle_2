package puzzle;

import java.util.ArrayList;
import java.util.List;

public class KenkenPuzzle {
	private int[][] board; // KenKen board representation
    private List<Region> regions; // List of regions

    public KenkenPuzzle(int size) {
        board = new int[size][size];
        regions = new ArrayList<>();
    }
    
    // Método para ingresar los datos del tablero
    public void setCellValue(int row, int col, int value) {
        board[row][col] = value;
    }
    
 // Método para definir una región con su objetivo y operador
    public void addRegion(int target, char operator, List<Nodo<Coordenada>> nodos) {
        Region region = new Region();
        region.target = target;
        region.operator = operator;
        region.nodos = nodos;

        regions.add(region);
    }

    // Método para validar si una solución es correcta
    public boolean isSolutionValid() {
    	 // Verificar que cada celda contenga un número válido (1 al N)
        int size = board.length;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                int cellValue = board[row][col];
                if (cellValue < 1 || cellValue > size) {
                    return false; // La celda contiene un número inválido
                }
            }
        }

        // Verificar que se cumplan las reglas de las regiones
        for (Region region : regions) {
            int target = region.target;
            char operator = region.operator;
            List<Nodo<Coordenada>> nodos = region.nodos;
            int result = (operator == '+') ? 0 : 1;

            for (Nodo<Coordenada> nodo : nodos) {
                int cellValue = board[nodo.getEstado().getRow()][nodo.getEstado().getCol()];

            
                switch (operator) {
                    case '+':
                        result += cellValue;
                        break;

                    case '*':
                        result *= cellValue;
                        break;
                }

            }

            if (result != target) {
                return false; // No se cumple el objetivo de la región
            }
        }

        return true;// Cambia esto según tu implementación
    }

    // Método para resolver el KenKen mediante BFS
    public void solveByBFS() {
    	System.out.println("Solving program...");
        Borde<Nodo<Coordenada>> borde = new Borde<Nodo<Coordenada>>(true);
        Nodo<Coordenada> nodoInicial = new Nodo<Coordenada>(new Coordenada(0,0));
        borde.insert(nodoInicial);
        while (!borde.isEmpty()) {
        	Nodo<Coordenada> nodoActual = borde.pop();
        	System.out.println(nodoActual.getEstado());
        	int row = nodoActual.getEstado().getRow();
        	int col = nodoActual.getEstado().getCol();
        	
        	// if we get here, we solve the kenken puzzle
        	if (row == board.length) {
        		System.out.println("Solución encontrada");
        		printBoard();
        		return;
        	}
        	
        	// if cell has a value, go to next row
        	if (col == board.length) {
        		borde.insert(new Nodo<Coordenada>(new Coordenada(row + 1,0)));
        		continue;
        	}
        	
        	// if cell has a value, go to next column
        	if (board[row][col] != 0) {
        		borde.insert(new Nodo<Coordenada>(new Coordenada(row,col + 1)));
        		continue;
        	}
        	
        	// Test all possibilities in this cell
        	for(int num = 1; num <= board.length; num++) {
        		if (isValidMove(row,col,num)){
        			setCellValue(row, col, num);
        			printBoard();
        			borde.insert(new Nodo<Coordenada>(new Coordenada(row,col + 1)));
        		}
        	}
        	
        	// if no valid solution
        	setCellValue(row, col, 0);
        }
        System.out.println("No se encontró una solución válida");
    }
    
    private boolean isValidMove(int row, int col, int num) {
    	// Verify if number is in the same row or column
        for (int i = 0; i < board.length; i++) {
            if (board[row][i] == num || board[i][col] == num) {
                return false;
            }
        }

        // Verify game rules (regions)
        for (Region region : regions) {
            if (region.nodos.stream().anyMatch(
            		nodo -> nodo.getEstado().getRow() == row && nodo.getEstado().getCol() == col)
            	) {
                int regionResult = calculateRegionResult(region);
                char operator = region.operator;

                switch (operator) {
                    case '+':
                        return regionResult + num <= region.target;
                    case '-':
                        return regionResult - num == region.target || num - regionResult == region.target;
                    case '*':
                        return regionResult * num <= region.target;
                    case '/':
                        return regionResult % num == 0 && regionResult / num == region.target;
                }
            }
        }

        return true;
    }

    // Función para calcular el resultado de una región
    private int calculateRegionResult(Region region) {
        int result = 0;

        for (Nodo<Coordenada> nodo : region.nodos) {
            result += board[nodo.getEstado().getRow()][nodo.getEstado().getCol()];
        }

        return result;
    }


    // Método para imprimir el tablero
    public void printBoard() {
        for (int[] row : board) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }// Método para imprimir el tablero y las regiones
    public void printBoardWithRegions() {
        int size = board.length;

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                System.out.print(board[row][col] + " ");
            }
            System.out.println();
        }

        System.out.println("Regiones:");
        for (Region region : regions) {
            System.out.println("Objetivo: " + region.target + ", Operador: " + region.operator);
            for (Nodo<Coordenada> nodo : region.nodos) {
                System.out.println("   - Coordenadas (" + nodo.getEstado().getRow() + ", " + nodo.getEstado().getCol() + ")");
            }
        }
    }

}

