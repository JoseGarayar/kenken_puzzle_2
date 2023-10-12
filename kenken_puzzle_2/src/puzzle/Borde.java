package puzzle;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Borde {
	private Deque<Nodo> queue;
	private HashSet<int[][]> nodosBorde;
	private boolean fifo;
	
	public Borde(boolean fifo){
		this.fifo= fifo;
		queue =  new ArrayDeque<>();
		nodosBorde = new HashSet<int[][]>();
	}
	
	public void insert(Nodo nodo){
		if (!nodosBorde.contains(nodo)) {
			if (fifo)
				queue.offer(nodo);
			else
				queue.push(nodo);
			nodosBorde.add(nodo.state.board);
		}
	}
	
	public Nodo pop() {
		if (queue.isEmpty()){
			return null;
		}
		Nodo nodo;
		if (fifo)
			nodo = queue.poll();
		else 
			nodo = queue.pop();
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