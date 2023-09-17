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
    
    // M�todo para ingresar los datos del tablero
    public void setCellValue(int row, int col, int value) {
        board[row][col] = value;
    }
    
 // M�todo para definir una regi�n con su objetivo y operador
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

    // M�todo para validar si una soluci�n es correcta
    public boolean isSolutionValid() {
        // Implementa la l�gica de validaci�n aqu�
        // Verifica que las reglas del juego se cumplan
        return true; // Cambia esto seg�n tu implementaci�n
    }

    // M�todo para resolver el KenKen
    public void solve() {
        // Implementa el algoritmo de resoluci�n aqu�
    }

    // M�todo para imprimir el tablero
    public void printBoard() {
        for (int[] row : board) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
    
}

