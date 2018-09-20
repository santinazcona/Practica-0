package simulador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlantacionFlores extends ElementoEcosistema implements Evolucionable {
	protected long cantidad;
	private Point posicion;
	private Dimension dimension;
	
	public PlantacionFlores( String titulo, int x, int y, int anch, int alt ) {
		this.titulo = titulo;
		cantidad = (long) (Math.sqrt( anch * alt )); 
		posicion = new Point( x, y );
		dimension = new Dimension( anch, alt );
	}
	
	private JPanel miPanel = null;
	private JLabel lTitulo = new JLabel("", JLabel.CENTER);
	private JLabel lCantidad = new JLabel("", JLabel.CENTER);
	@Override
	public JPanel getPanel() {
		if (miPanel == null) {
			miPanel = new JPanel();
			miPanel.setLayout( new BorderLayout() );
			miPanel.add( lTitulo, BorderLayout.NORTH );
			miPanel.add( lCantidad, BorderLayout.CENTER );
			miPanel.add( new JLabel("Flores", JLabel.CENTER), BorderLayout.SOUTH );
			lCantidad.setText( ""+cantidad );
			lTitulo.setText( titulo );
			miPanel.setLocation( posicion );
			miPanel.setSize( dimension );
			miPanel.setBackground( Color.green );
		}
		return miPanel;
	}
	
	public long getCantidad() {
		return cantidad;
	}

	public void setCantidad(long poblacion) {
		this.cantidad = poblacion;
	}

	@Override
	public void evoluciona(int dias) {
		double factorCrecimiento = 0.75;
		for (ElementoEcosistema ee : Ecosistema.getMundo().getElementos()) {
			int dist = Ecosistema.distancia( this, ee );
			if (ee instanceof ColoniaAbejas) {
				if (dist < 500) factorCrecimiento = factorCrecimiento / dist * 500;
			} else if (ee instanceof Agua) {
				if (dist < 500) factorCrecimiento = factorCrecimiento / dist * 500;
			}
		}
		cantidad = (long) (cantidad * factorCrecimiento * dias);
		if (cantidad > 5000) cantidad = 5000;
		lCantidad.setText( "" + cantidad );
	}

}
