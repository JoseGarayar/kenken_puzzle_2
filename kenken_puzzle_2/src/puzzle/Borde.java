package puzzle;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Borde<T> {
	private Deque<T> borde; // FIFO or LIFO
	private Set<T> nodosBorde;
	
	public Borde(boolean fifo){
		if (fifo) {
			borde = new LinkedList<>(); // FIFO
		} else {
			borde = new ArrayDeque<>(); // LIFO
		}
		nodosBorde = new HashSet<>();
	}
	
	public void insert(T nodo){
		if (!nodosBorde.contains(nodo)) {
			borde.add(nodo);
			nodosBorde.add(nodo);
		}
	}
	
	public T pop() {
		if (borde.isEmpty()){
			return null;
		}
		T nodo = borde.poll();
		nodosBorde.remove(nodo);
		return nodo;
	}
	
	public boolean buscar(T nodo) {
		return nodosBorde.contains(nodo);
	}
	
	public boolean isEmpty(){
		return borde.isEmpty();
	}
}
