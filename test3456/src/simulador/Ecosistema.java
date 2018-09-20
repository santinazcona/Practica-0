package simulador;

import java.util.ArrayList;

public class Ecosistema {
	private static Ecosistema ecoSistema = new Ecosistema();
	private ArrayList<ElementoEcosistema> listaElemsEcosistema;
	public Ecosistema() {
		listaElemsEcosistema = new ArrayList<>();
	}
	public static Ecosistema getMundo() {
		return ecoSistema;
	}
	
	public ArrayList<ElementoEcosistema> getElementos() {
		return listaElemsEcosistema;
	}
	
	public static int distancia( ElementoEcosistema ee1, ElementoEcosistema ee2 ) {
		return (int) (Math.sqrt( Math.pow( ee1.getPanel().getX() - ee2.getPanel().getX(), 2) + 
				                 Math.pow( ee1.getPanel().getY() - ee2.getPanel().getY(), 2) ) );
	}
	
}
