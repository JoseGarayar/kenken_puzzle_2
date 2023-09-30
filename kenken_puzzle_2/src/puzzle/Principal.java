package puzzle;

import java.util.List;

public class Principal {
	public static void main(String[] args) {
		System.out.println("Starting program...");
		KekenParser parser = new KekenParser();
		List<KenkenPuzzle>  kenken2 = parser.readFile(System.getProperty("user.dir") + "\\src\\puzzle\\kenken2.txt");
		
		for (KenkenPuzzle solver : kenken2) {
			System.out.println(solver);
			solver.printBoardWithRegions();
			solver.solveByBFS();
//			solver.printBoardWithRegions();
			if (solver.isSolutionValid()) {
	            System.out.println("Solución válida:");
	            solver.printBoard();
	        } else {
	            System.out.println("No se encontró una solución válida.");
	        }
		}
	}
}
