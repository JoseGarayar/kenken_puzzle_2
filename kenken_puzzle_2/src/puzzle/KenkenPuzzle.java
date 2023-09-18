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
    
    

    // Region representation class
    private class Region {
        List<Cell> cells;
        int target;
        char operator;

        public Region() {
            cells = new ArrayList<>();
        }

        public void addCell(int row, int col) {
            cells.add(new Cell(row, col));
        }
    }
    
    // Método para ingresar los datos del tablero
    public void setCellValue(int row, int col, int value) {
        board[row][col] = value;
    }
    
 // Método para definir una región con su objetivo y operador
    public void addRegion(int target, char operator, int... cellCoords) {
        Region region = new Region();
        region.target = target;
        region.operator = operator;

        for (int i = 0; i < cellCoords.length; i += 2) {
            int row = cellCoords[i];
            int col = cellCoords[i + 1];
            region.addCell(row, col);
        }

        regions.add(region);
    }
    
    public void addRegion(int target, char operator, List<Cell> cells) {
        Region region = new Region();
        region.target = target;
        region.operator = operator;
        region.cells = cells;

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
            List<Cell> cells = region.cells;
            int result = (operator == '+') ? 0 : 1;

            for (Cell cell : cells) {
                int cellValue = board[cell.row][cell.col];

            
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

    // Método para resolver el KenKen
    public void solve() {
        // Implementa el algoritmo de resolución aquí
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
            for (Cell cell : region.cells) {
                System.out.println("   - Coordenadas (" + cell.row + ", " + cell.col + ")");
            }
        }
    }

}

