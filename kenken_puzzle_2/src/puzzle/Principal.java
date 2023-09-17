package puzzle;

public class Principal {
	public static void main(String[] args) {
		KenkenPuzzle solver = new KenkenPuzzle(4); // Tamaño del tablero (4x4 en este caso)

        // Ingresa los datos del tablero y define las regiones aquí
        solver.setCellValue(0, 0, 1);
        solver.setCellValue(0, 1, 2);
        // ...

        solver.addRegion(3, '+', 0, 0, 0, 1);
        solver.addRegion(3, '+', 1, 0, 1, 1);
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
