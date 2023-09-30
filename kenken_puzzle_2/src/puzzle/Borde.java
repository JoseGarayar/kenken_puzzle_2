package puzzle;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Borde {
	private Queue<Nodo> queue;
	private HashSet<int[][]> nodosBorde;
	
	public Borde(boolean fifo){
		if (fifo) {
			queue = new LinkedList<>(); // FIFO
		} else {
			queue = new ArrayDeque<>(); // LIFO
		}
		nodosBorde = new HashSet<int[][]>();
	}
	
	public void insert(Nodo nodo){
		if (!nodosBorde.contains(nodo)) {
			queue.add(nodo);
			nodosBorde.add(nodo.state.board);
		}
	}
	
	public Nodo pop() {
		if (queue.isEmpty()){
			return null;
		}
		Nodo nodo = queue.poll();
		nodosBorde.remove(nodo.state.board);
		return nodo;
	}
	
	public boolean buscar(Nodo nodo) {
		return nodosBorde.contains(nodo.state.board);
	}
	
	public boolean isEmpty(){
		return queue.isEmpty();
	}
	
	public void showQueue() {
		System.out.println(queue);
	}
}