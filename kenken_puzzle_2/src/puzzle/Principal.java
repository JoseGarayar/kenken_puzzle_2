package puzzle;

import java.util.List;

public class Principal {
	public static void main(String[] args) {
		
		KekenParser parser = new KekenParser();
		List<KenkenPuzzle>  kenken2 = parser.readFile("C:\\Maestria\\Ciclo 2\\IA\\kenken.txt");
		
		for (KenkenPuzzle keken3 : kenken2) {
			keken3.printBoard();			
		}
		

			
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
