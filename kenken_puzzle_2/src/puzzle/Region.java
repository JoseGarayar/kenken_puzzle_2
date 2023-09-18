package puzzle;

import java.util.ArrayList;
import java.util.List;

public class Region {
	List<Nodo<Coordenada>> nodos;
    int target;
    char operator;

    public Region() {
    	nodos = new ArrayList<>();
    }

    public void addCell(int row, int col) {
    	Coordenada coordenada = new Coordenada(row, col);
    	nodos.add(new Nodo<Coordenada>(coordenada));
    }
}
