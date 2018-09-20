package simulador;

import javax.swing.JPanel;

public abstract class ElementoEcosistema {
	protected String titulo;
	
	public abstract JPanel getPanel();

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
}
