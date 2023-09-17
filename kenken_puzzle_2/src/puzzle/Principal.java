package puzzle;

public class Principal {
	public static void main(String[] args) {
		KenkenPuzzle solver = new KenkenPuzzle(3); // Tamaño del tablero (4x4 en este caso)

        // Ingresa los datos del tablero y define las regiones aquí
        solver.setCellValue(0, 0, 2);
        solver.setCellValue(0, 1, 3);
        solver.setCellValue(0, 2, 1);
        solver.setCellValue(1, 0, 1);
        solver.setCellValue(1, 1, 2);
        solver.setCellValue(1, 2, 3);
        solver.setCellValue(2, 0, 3);
        solver.setCellValue(2, 1, 1);
        solver.setCellValue(2, 2, 2);
        // ...
        solver.addRegion(3, '+', 0, 0, 1, 0);
        solver.addRegion(5, '+', 0, 1, 1, 1);
        solver.addRegion(1, '+', 0, 2);
        solver.addRegion(4, '+', 2, 0, 2, 1);
        solver.addRegion(5, '+', 1, 2, 2, 2);
        //solver.printBoard();
        solver.printBoardWithRegions();

        // ...

        solver.solve();

        if (solver.isSolutionValid()) {
            System.out.println("Solución válida:");
            solver.printBoard();
        } else {
            System.out.println("No se encontró una solución válida.");
        }
	}
}
