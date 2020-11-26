package busquedas;

import java.util.Comparator;

import dominio.Nodo;

public class OrdenarFrontera implements Comparator<Nodo> {

	public int compare(Nodo old,Nodo actual) {
		if(old.getValor() > actual.getValor())
				return 1;
		else if(old.getValor() < actual.getValor())
				return -1;
		else if(old.getEstado().getFila() > actual.getEstado().getFila())
			return 1;
		else if(old.getEstado().getFila() < actual.getEstado().getFila())
			return -1;
		else if(old.getEstado().getColumna() > actual.getEstado().getColumna())
			return 1;
		else if(old.getEstado().getColumna() < actual.getEstado().getColumna())
			return -1;
		else if(old.getId() > actual.getId())
			return 1;
		else if(old.getId() < actual.getId())
			return -1;
		else
			return 0;
	}
}


