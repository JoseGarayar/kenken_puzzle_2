package puzzle;

import java.util.List;


public class KenKenSolver {
	Explorados explored;
	private int maxPathdepth;
	
	public KenKenSolver(){
		explored = new Explorados();
		maxPathdepth=0;
	}
	
	public int getmaxPathdepth(){
		return maxPathdepth;
	}
	
	public Nodo solve(Nodo initialNode, SearchType searchType) {
		// Change to false to use LIFO (stack)
        Borde frontier = new Borde(searchType==SearchType.BFS? true: false); 
        explored.clear();
        maxPathdepth=0;
        frontier.insert(initialNode);
        while (!frontier.isEmpty()) {
        	Nodo currentNode = frontier.pop();
            
            explored.insert(currentNode);
            
            if (currentNode.profundidad>maxPathdepth) maxPathdepth = currentNode.profundidad;
                        
            if (currentNode.state.isGoalState()) {
                return currentNode; // ¡We found the solution!
            }
            // Generate and add new neighbor nodes to frontier            
            List<Nodo> neighborNodes = currentNode.generateNeighborNodes();            
            for (Nodo neighborNode : neighborNodes) {
                if (!explored.buscar(neighborNode) && !neighborNode.state.pruningStrategy()) {            
                    frontier.insert(neighborNode);
                }
            }         
        }
        return null; // Solution not found
    }
	
	public int getTotalExpandedNodes(){
		return explored.size();
	}
	
	
}
