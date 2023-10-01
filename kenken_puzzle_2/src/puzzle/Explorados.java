package puzzle;

import java.util.HashSet;

public class Explorados {
	private HashSet<int[][]> nodosExplorados;
	
	public Explorados(){
		nodosExplorados = new HashSet<>();
		
	}
	
	public void insert(Nodo nodo){
		if (!nodosExplorados.contains(nodo.state.board)) {
			nodosExplorados.add(nodo.state.board);
		}
	}
	
	public Nodo pop(Nodo nodo) {
		nodosExplorados.remove(nodo.state.board);
		return nodo;
	}
	
	public boolean buscar(Nodo nodo) {
		return nodosExplorados.contains(nodo.state.board);
	}
	
	public int size(){
		return nodosExplorados.size(); 
	}
	
	public void clear(){
		nodosExplorados.clear();
	}
}