package puzzle;

public class Nodo<T> {
	private T estado;

    public Nodo(T estado) {
        this.estado = estado;
    }
    
    public T getEstado() {
    	return estado;
    }
    
    public void setEstado(T estado) {
    	this.estado = estado;
    }
    
    public void imprimirEstado() {
    	System.out.println("Estado: " + estado.toString());
    }
    
}
