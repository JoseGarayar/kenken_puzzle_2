package puzzle;

import java.util.ArrayList;
import java.util.List;

public class Principal {
	
	
	public static void main(String[] args) {
		System.out.println("Hola mundo!");
		
		
		KekenParser parser = new KekenParser();
		List<KenKenState>  puzzles = parser.readFile(System.getProperty("user.dir") + "\\kenken_puzzle_2\\src\\puzzle\\kenken1.txt");
		
		for (KenKenState initialState : puzzles) {
			KenKenSolver solver = new KenKenSolver();
			Nodo initialNode = new Nodo(initialState, null, 0, 0, "left");
			KenKenState solution = solver.solve(initialNode);
			
			if (solution != null) {
				System.out.println("There is a solution!.");
	            // Print the solution here
				for (int i = 0; i < solution.board.length; i++) {
	                // Loop through the columns
	                for (int j = 0; j < solution.board[i].length; j++) {
	                    // Print the element at matrix[i][j] followed by a space
	                    System.out.print(solution.board[i][j] + " ");
	                }
	                // Print a newline after each row
	                System.out.println();
	            }
	        } else {
	            System.out.println("No se encontró una solución.");
	        }
		}
		
		/*
//		KekenParser parser = new KekenParser();
//		String filePath = System.getProperty("user.dir") + "\\src\\puzzle\\kenken2.txt";
//		List<KenkenPuzzle> kenken2 = parser.readFile(filePath);
		KenKenState initialState = new KenKenState(3);
		initialState.setCellValue(0, 1, 1);
		initialState.setCellValue(2, 2, 1);
		List<Coordenada> nodos2 = new ArrayList<>();
		nodos2.add(new Coordenada(0,0));
		nodos2.add(new Coordenada(1,0));
		initialState.addRegion(2, '*', nodos2);
		nodos2 = new ArrayList<>();
		nodos2.add(new Coordenada(2,0));
		nodos2.add(new Coordenada(2,1));
		initialState.addRegion(6, '*', nodos2);
		nodos2 = new ArrayList<>();
		nodos2.add(new Coordenada(0,2));
		nodos2.add(new Coordenada(1,1));
		nodos2.add(new Coordenada(1,2));
		initialState.addRegion(8, '+', nodos2);
		
		KenKenSolver solver = new KenKenSolver();
		Nodo initialNode = new Nodo(initialState, null, 0, 0, "left");
		KenKenState solution = solver.solve(initialNode);
		
		if (solution != null) {
			System.out.println("There is a solution!.");
            // Print the solution here
			for (int i = 0; i < solution.board.length; i++) {
                // Loop through the columns
                for (int j = 0; j < solution.board[i].length; j++) {
                    // Print the element at matrix[i][j] followed by a space
                    System.out.print(solution.board[i][j] + " ");
                }
                // Print a newline after each row
                System.out.println();
            }
        } else {
            System.out.println("No se encontró una solución.");
        }
        */
	}
	
	/*
	public static void main(String[] args) {
		
		
		
	
		System.out.println("Starting program...");
		KekenParser parser = new KekenParser();
		List<KenkenPuzzle>  kenken2 = parser.readFile(System.getProperty("user.dir") + "\\kenken_puzzle_2\\src\\puzzle\\kenken2.txt");
		
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
	}*/
	
}
