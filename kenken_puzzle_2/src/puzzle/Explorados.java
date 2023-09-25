package puzzle;

import java.util.HashSet;

public class Explorados<T> {
	private HashSet<T> nodosExplorados;
	
	public Explorados(){
		nodosExplorados = new HashSet<>();
	}
	
	public void insert(T nodo){
		if (!nodosExplorados.contains(nodo)) {
			nodosExplorados.add(nodo);
		}
	}
	
	public T pop(T nodo) {
		nodosExplorados.remove(nodo);
		return nodo;
	}
	
	public boolean buscar(T nodo) {
		return nodosExplorados.contains(nodo);
	}
}
