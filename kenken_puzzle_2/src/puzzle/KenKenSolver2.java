package puzzle;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

class Region2 {
	List<Coordenada> nodos;
    int target;
    char operator;

    public Region2() {
    	nodos = new ArrayList<>();
    }

    public void addCell(int row, int col) {
    	Coordenada coordenada = new Coordenada(row, col);
    	nodos.add(coordenada);
    }
}

class KenKenState {
	int[][] board;
	private List<Region2> regions;
	
	public KenKenState(int size){
		board = new int[size][size];
        regions = new ArrayList<>();
	}
	
	public void setCellValue(int row, int col, int value) {
        board[row][col] = value;
    }
	
	public void addRegion(int target, char operator, List<Coordenada> nodos) {
        Region2 region = new Region2();
        region.target = target;
        region.operator = operator;
        region.nodos = nodos;

        regions.add(region);
    }
	
	public List<Region2> getRegions() {
		return regions;
	}
	
}

class Nodo2 {
	KenKenState state;
	Nodo2 parent;
	int costoCamino;
	int profundidad;
	String accion;
	
	public Nodo2(KenKenState state, Nodo2 parent, int costoCamino, int profundidad, String accion) {
		this.state = state;
		this.parent = parent;
		this.costoCamino = costoCamino;
		this.profundidad = profundidad;
		this.accion = accion;
	}	
}

class Borde2 {
	private Queue<Nodo2> queue;
	private HashSet<int[][]> nodosBorde;
	
	public Borde2(boolean fifo){
		if (fifo) {
			queue = new LinkedList<>(); // FIFO
		} else {
			queue = new ArrayDeque<>(); // LIFO
		}
		nodosBorde = new HashSet<int[][]>();
	}
	
	public void insert(Nodo2 nodo){
		if (!nodosBorde.contains(nodo)) {
			queue.add(nodo);
			nodosBorde.add(nodo.state.board);
		}
	}
	
	public Nodo2 pop() {
		if (queue.isEmpty()){
			return null;
		}
		Nodo2 nodo = queue.poll();
		nodosBorde.remove(nodo.state.board);
		return nodo;
	}
	
	public boolean buscar(Nodo2 nodo) {
		return nodosBorde.contains(nodo.state.board);
	}
	
	public boolean isEmpty(){
		return queue.isEmpty();
	}
	
	public void showQueue() {
		System.out.println(queue);
	}
}

class Explorados2 {
	private HashSet<int[][]> nodosExplorados;
	
	public Explorados2(){
		nodosExplorados = new HashSet<>();
	}
	
	public void insert(Nodo2 nodo){
		if (!nodosExplorados.contains(nodo.state.board)) {
			nodosExplorados.add(nodo.state.board);
		}
	}
	
	public Nodo2 pop(Nodo2 nodo) {
		nodosExplorados.remove(nodo.state.board);
		return nodo;
	}
	
	public boolean buscar(Nodo2 nodo) {
		return nodosExplorados.contains(nodo.state.board);
	}
}


public class KenKenSolver2 {
	Explorados2 explored;
	
	public KenKenSolver2(){
		explored = new Explorados2();
	}
	
	public KenKenState solve(Nodo2 initialNode) {
        Borde2 frontier = new Borde2(true); // Change to false to use LIFO (stack)
        frontier.insert(initialNode);
        while (!frontier.isEmpty()) {
        	Nodo2 currentNode = frontier.pop();
            KenKenState currentState = currentNode.state;
            if (isGoalState(currentState)) {
                return currentState; // ¡We found the solution!
            }
            explored.insert(currentNode);

            // Generate and add new neighbor nodes to frontier
            List<Nodo2> neighborNodes = generateNeighborNodes(currentNode);

            for (Nodo2 neighborNode : neighborNodes) {
            	for (int i = 0; i < neighborNode.state.board.length; i++) {
                    // Loop through the columns
                    for (int j = 0; j < neighborNode.state.board[i].length; j++) {
                        // Print the element at matrix[i][j] followed by a space
                        System.out.print(neighborNode.state.board[i][j] + " ");
                    }
                    // Print a newline after each row
                    System.out.println();
                }
                if (!explored.buscar(neighborNode) && !isValueRepeatedInRowOrColumn(neighborNode.state.board)) {
                	System.out.println("Hola!");
                    frontier.insert(neighborNode);
                }
            }
            frontier.showQueue();
        }

        return null; // Solution not found
    }
	
	private List<Nodo2> generateNeighborNodes(Nodo2 currentNode) {
		KenKenState currentState = currentNode.state;
		int[][] currentBoard = currentState.board;
		// this values represent the next coordinate from where we generate new nodes
		int rowCoordenada = -1;
		int colCoordenada = -1;
		
		// Find next 0 value in matrix
		outerLoop:
		for (int row = 0; row < currentBoard.length; row++){
			for (int col = 0; col < currentBoard.length; col++) {
				if (currentBoard[row][col] == 0) {
					colCoordenada = col;
					rowCoordenada = row;
					break outerLoop;
				}
			}
		}
		
		// Generate new nodes
		List<Nodo2> listNewNodes = new ArrayList<>();
		// Case there is no more nodes to create
		if (rowCoordenada == -1 || colCoordenada == -1) {
			return listNewNodes;
		}
		for (int value = 1; value <= currentBoard.length; value++) {
			currentBoard[rowCoordenada][colCoordenada] = value;
			int [][] newBoard = new int[currentBoard.length][currentBoard.length];
			// Copy values from the source matrix to the destination matrix
	        for (int i = 0; i < currentBoard.length; i++) {
	            for (int j = 0; j < currentBoard[i].length; j++) {
	            	newBoard[i][j] = currentBoard[i][j];
	            }
	        }
	        // Setting new value
	        newBoard[rowCoordenada][colCoordenada] = value;
			KenKenState NeighbourState = new KenKenState(newBoard.length);
			NeighbourState.board = newBoard;
			Nodo2 neighbourNode = new Nodo2(NeighbourState, currentNode, 0, currentNode.profundidad + 1, "");
			listNewNodes.add(neighbourNode);
		}
		return listNewNodes;
		
	}
	
	private boolean isValueRepeatedInRowOrColumn(int[][] board) {
		// Evaluating rows and columns
		int filas = board.length;
        int columnas = board[0].length;
        // Verify rows
        for (int fila = 0; fila < filas; fila++) {
            Set<Integer> elementosVistos = new HashSet<>();
            for (int columna = 0; columna < columnas; columna++) {
                int elemento = board[fila][columna];
                if (elementosVistos.contains(elemento)) {
                    return true; // A repeated element was found in the row
                }
                if (elemento != 0){
                	elementosVistos.add(elemento);
                }
            }
        }
        // Verify columns
        for (int columna = 0; columna < columnas; columna++) {
            Set<Integer> elementosVistos = new HashSet<>();
            for (int fila = 0; fila < filas; fila++) {
                int elemento = board[fila][columna];
                if (elementosVistos.contains(elemento)) {
                    return true; // A repeated element was found in the column
                }
                if (elemento != 0){
                	elementosVistos.add(elemento);
                }
            }
        }
        return false;
	}
	
	private boolean isGoalState(KenKenState currentState){
		int [][] board = currentState.board;
		
		if (isValueRepeatedInRowOrColumn(board)) {
			return false;
		}
		
		// Evaluating rows and columns
		int filas = board.length;
        int columnas = board[0].length;
        // Verify rows
        for (int fila = 0; fila < filas; fila++) {
            Set<Integer> elementosVistos = new HashSet<>();
            for (int columna = 0; columna < columnas; columna++) {
                int elemento = board[fila][columna];
                if (elementosVistos.contains(elemento) || elemento == 0) {
                    return false; // A repeated element was found in the row
                }
                elementosVistos.add(elemento);
            }
        }
        // Verify columns
        for (int columna = 0; columna < columnas; columna++) {
            Set<Integer> elementosVistos = new HashSet<>();
            for (int fila = 0; fila < filas; fila++) {
                int elemento = board[fila][columna];
                if (elementosVistos.contains(elemento) || elemento == 0) {
                    return false; // A repeated element was found in the column
                }
                elementosVistos.add(elemento);
            }
        }
		
		// Evaluating regions
		for (Region2 region : currentState.getRegions()) {
			char operator = region.operator;
			
			// for "-" and "/" operators, only if needed
			Coordenada firstCoordenada = region.nodos.iterator().next();
        	Coordenada secondCoordenada = region.nodos.iterator().next();
        	int firstValue = board[firstCoordenada.getRow()][firstCoordenada.getCol()];
        	int secondValue = board[secondCoordenada.getRow()][secondCoordenada.getCol()];

            switch (operator) {
                case '+':
                	int sum = 0;
                	for (Coordenada coordenada: region.nodos){
                		sum = sum + board[coordenada.getRow()][coordenada.getCol()];
                	}
                	if (sum != region.target) {
                		return false;
                	}
                case '-':
                	if (Math.abs(firstValue - secondValue) != region.target) {
                		return false;
                	}
                case '*':
                	int prod = 1;
                	for (Coordenada coordenada: region.nodos){
                		prod = prod * board[coordenada.getRow()][coordenada.getCol()];
                	}
                	if (prod != region.target) {
                		return false;
                	}
                case '/':
                	if (
                		(firstValue % secondValue != 0 || firstValue / secondValue != region.target) || 
                    	(secondValue % firstValue != 0 || secondValue / firstValue != region.target)
                    ) {
                		return false;
                	}
            }
        }
		
		// If all validations passed, then return true
		return true;
	}
	
	public static void main(String[] args) {
		System.out.println("Hola mundo!");
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
		
		KenKenSolver2 solver = new KenKenSolver2();
		Nodo2 initialNode = new Nodo2(initialState, null, 0, 0, "left");
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
}
