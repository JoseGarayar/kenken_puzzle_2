package puzzle;

import java.util.List;

public class Principal {
	public static void main(String[] args) {
		
		KekenParser parser = new KekenParser();
		List<KenkenPuzzle>  kenken2 = parser.readFile(System.getProperty("user.dir") + "\\src\\puzzle\\kenken.txt");
		
		for (KenkenPuzzle solver : kenken2) {
			solver.solve();
			solver.printBoardWithRegions();
			if (solver.isSolutionValid()) {
	            System.out.println("Soluci�n v�lida:");
	            solver.printBoard();
	        } else {
	            System.out.println("No se encontr� una soluci�n v�lida.");
	        }
		}
	}
}
