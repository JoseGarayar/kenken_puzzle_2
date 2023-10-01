package puzzle;

import java.util.List;

public class Principal {
	
	
	public static void main(String[] args) {
		//System.out.println("Hola mundo!");
		
		
		PerformKenkenPuzzle("\\kenken_puzzle_2\\src\\puzzle\\kenken5.txt", SearchType.BFS);
		PerformKenkenPuzzle("\\kenken_puzzle_2\\src\\puzzle\\kenken5.txt", SearchType.DFS);
		
		
	}
	
	private static void PerformKenkenPuzzle(String fileName, SearchType searchType){
		
		KekenParser parser = new KekenParser();
		List<KenKenState>  puzzles = parser.readFile(System.getProperty("user.dir") + fileName);
		
		for (KenKenState initialState : puzzles) {
			KenKenSolver solver = new KenKenSolver();
			Nodo initialNode = new Nodo(initialState, null, 0, 0, "left");
			long startTime = System.nanoTime();
			Nodo solution = solver.solve(initialNode,searchType);
			long endTime = (System.nanoTime()-startTime)/1000000;
			
			
			System.out.println(searchType== SearchType.BFS? "Breadth First Search - BFS": "Depth First Search - DFS");
			System.out.println("Kenken Puzzle!");
			solution.state.printBoardWithRegion();
			System.out.println(" ");
			
			if (solution != null) {
				
				System.out.println("There is a solution!.");
				solution.state.printBoard();
				System.out.println(" ");
				System.out.println("Length of the path found for the solution : "+Integer.toString(solution.costoCamino));				
				System.out.println("Number of nodes expanded : "+Integer.toString(solver.getTotalExpandedNodes()));				
				System.out.print("Maximum depth achieved : ");
				System.out.println(solver.getmaxPathdepth());
				System.out.print("Execution time : ");
				System.out.println(Long.toString(endTime)+" milliseconds." );
				System.out.println(" ");
				
	        } else {
	            System.out.println("No se encontró una solución.");
	        }
		}
	}
	
	
}
