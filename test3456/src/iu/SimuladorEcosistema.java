package iu;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;

import simulador.*;

public class SimuladorEcosistema extends JFrame {
	private JPanel pMundo;
	private JToggleButton tbMover;
	private JToggleButton tbCrear;
	
	private Hilo hilo;
	
	public SimuladorEcosistema() {
		setSize(800,600);
		setTitle( "Simulador ecosistema - Prog III" );
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		setLocationRelativeTo( null );
		
		pMundo = new JPanel();
		JPanel pBotonera = new JPanel();
		JToggleButton tbMover = new JToggleButton( "Mover" );
		JToggleButton tbCrear = new JToggleButton( "Crear" );
		JComboBox<String> cbElemento = new JComboBox<>( new String[] { 
			"Abejas", "Flores", "Agua"
		} );
		JButton bVida = new JButton( "Vida" );
		
		pMundo.setLayout( null );
		tbCrear.setSelected( true );
		
		pBotonera.add( tbMover );
		pBotonera.add( tbCrear );
		pBotonera.add( cbElemento );
		pBotonera.add( bVida );
		getContentPane().add( pMundo, BorderLayout.CENTER );
		getContentPane().add( pBotonera, BorderLayout.SOUTH );
		
		tbMover.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tbMover.isSelected()) tbCrear.setSelected( false );
			}
		});
		tbCrear.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tbCrear.isSelected()) tbMover.setSelected( false );
			}
		});
		pMundo.addMouseListener( new MouseListener() {
			Point coordPulsacion = null;
			@Override
			public void mouseReleased(MouseEvent e) {
				Point coordSuelta = e.getPoint();
				if (tbMover.isSelected()) { 
					for (ElementoEcosistema ee : Ecosistema.getMundo().getElementos()) {
						if (ee.getPanel().getBounds().contains( coordPulsacion )) { 
							coordSuelta.translate( -coordPulsacion.x, -coordPulsacion.y);
							coordSuelta.translate( ee.getPanel().getLocation().x, ee.getPanel().getLocation().y );
							System.out.println( "mover " + ee.getPanel().getLocation() + " a " + coordSuelta );
							ee.getPanel().setLocation( coordSuelta );
							pMundo.add( ee.getPanel() );
							pMundo.revalidate();
							break; 
						}
					}
				} else if (tbCrear.isSelected()) { 
					ElementoEcosistema ee = null;
					if (cbElemento.getSelectedItem().equals("Abejas")) {
						ee = new ColoniaAbejas( "Colonia " + (Ecosistema.getMundo().getElementos().size()+1), 
								coordPulsacion.x, coordPulsacion.y, Math.abs(coordSuelta.x-coordPulsacion.x), Math.abs(coordSuelta.y-coordPulsacion.y) );
					} else if (cbElemento.getSelectedItem().equals("Flores")) {
						ee = new PlantacionFlores( "Pradera " + (Ecosistema.getMundo().getElementos().size()+1), 
								coordPulsacion.x, coordPulsacion.y, Math.abs(coordSuelta.x-coordPulsacion.x), Math.abs(coordSuelta.y-coordPulsacion.y) );
					} else if (cbElemento.getSelectedItem().equals("Agua")) {
						ee = new Agua( "Lago " + (Ecosistema.getMundo().getElementos().size()+1), 
								coordPulsacion.x, coordPulsacion.y, Math.abs(coordSuelta.x-coordPulsacion.x), Math.abs(coordSuelta.y-coordPulsacion.y) );
					}
					Ecosistema.getMundo().getElementos().add( ee );
					JPanel pNuevo = ee.getPanel();
					pMundo.add( pNuevo );
					pMundo.validate();
				}
			}
			@Override
			public void mousePressed(MouseEvent e) {
				coordPulsacion = e.getPoint();
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		
		bVida.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (bVida.getText().equals("Vida")) { 
					bVida.setText( "Parar" );
					hilo = new Hilo();
					hilo.start();
				} else { 
					bVida.setText( "Vida" );
					if (hilo != null) hilo.parar();
				}
			}
		});
		
		addWindowListener( new WindowAdapter() {
			@Override
			public void windowDeactivated(WindowEvent e) {
				if (hilo!=null) hilo.parar();
			}
		});
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			public void run() {
				SimuladorEcosistema se = new SimuladorEcosistema();
				se.setVisible( true  );
			}
		});
	}

	private class Hilo extends Thread {
		boolean activo = true;
		long tick = 1000;
		public Hilo() {
		}
		public void parar() {
			activo = false;
		}
		@Override
		public void run() {
			while (activo) {
				try {
					Thread.sleep( tick );
				} catch (InterruptedException e) {}
				for (ElementoEcosistema ee : Ecosistema.getMundo().getElementos()) {
					if (ee instanceof Evolucionable) {
						Evolucionable ev = (Evolucionable) ee;
						ev.evoluciona( 1 );
					}
				}
				SimuladorEcosistema.this.pMundo.validate();
			}
		}
	}
	
}
