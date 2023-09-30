package puzzle;

public class Nodo {
	KenKenState state;
	Nodo parent;
	int costoCamino;
	int profundidad;
	String accion;
	
	public Nodo(KenKenState state, Nodo parent, int costoCamino, int profundidad, String accion) {
		this.state = state;
		this.parent = parent;
		this.costoCamino = costoCamino;
		this.profundidad = profundidad;
		this.accion = accion;
	}	
}