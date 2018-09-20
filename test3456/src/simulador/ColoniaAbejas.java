package simulador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ColoniaAbejas extends ElementoEcosistema implements Evolucionable {
	protected long poblacion;
	private Point posicion;
	private Dimension dimension;
	public ColoniaAbejas( String titulo, int x, int y, int anch, int alt ) {
		this.titulo = titulo;
		poblacion = (long) (Math.sqrt( anch * alt )); 
		posicion = new Point( x, y );
		dimension = new Dimension( anch, alt );
	}
	
	private JPanel miPanel = null;
	private JLabel lTitulo = new JLabel("", JLabel.CENTER);
	private JLabel lPoblacion = new JLabel("", JLabel.CENTER);
	@Override
	public JPanel getPanel() {
		if (miPanel == null) {
			miPanel = new JPanel();
			miPanel.setLayout( new BorderLayout() );
			miPanel.add( lTitulo, BorderLayout.NORTH );
			miPanel.add( lPoblacion, BorderLayout.CENTER );
			miPanel.add( new JLabel("Abejas", JLabel.CENTER), BorderLayout.SOUTH );
			lPoblacion.setText( ""+poblacion );
			lTitulo.setText( titulo );
			miPanel.setLocation( posicion );
			miPanel.setSize( dimension );
			miPanel.setBackground( Color.white );
		}
		return miPanel;
	}
	
	public long getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(long poblacion) {
		this.poblacion = poblacion;
	}

	@Override
	public void evoluciona(int dias) {
		double factorCrecimiento = 1.0;
		int numFlores = 0;
		for (ElementoEcosistema ee : Ecosistema.getMundo().getElementos()) {
			int dist = Ecosistema.distancia( this, ee );
			if (ee instanceof ColoniaAbejas && ee!=this) {
				if (dist < 500) factorCrecimiento = factorCrecimiento * dist / 500;
			} else if (ee instanceof PlantacionFlores) {
				if (dist < 500) factorCrecimiento = factorCrecimiento / dist * 500;
				numFlores += ((PlantacionFlores)ee).getCantidad();
			}
		}
		if (numFlores < 50) factorCrecimiento *= 0.1;
		poblacion = (long) (poblacion * factorCrecimiento * dias);
		if (poblacion > 5000) poblacion = 5000;
		lPoblacion.setText( "" + poblacion );
	}

}
