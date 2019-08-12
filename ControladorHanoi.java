package TorresHanoi;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ControladorHanoi implements ActionListener, ChangeListener {
	private VistaHanoi vista;
	private ModeloHanoi modelo;
	private int contador = 0, velocidad = 100;
	private String movimientoActual = "";
	private boolean movimientoEntero = false;

	public ControladorHanoi(VistaHanoi vista, ModeloHanoi modelo) {
		this.vista = vista;
		this.modelo = modelo;
	}

	public void stateChanged(ChangeEvent e) {
		JSlider aux = (JSlider) e.getSource();
		if (aux == vista.sliderDiscos) {
			vista.numDiscos = vista.sliderDiscos.getValue();
			vista.pintaJuego();
			return;
		}
		if (aux == vista.sliderVelocidad) {
			velocidad = 1010 - vista.sliderVelocidad.getValue();
			vista.t.setDelay(velocidad);
			vista.btnAutomatico.setVelocidad(velocidad+50);
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == vista.btnEmpezar) {
			vista.empezarJuego();
			modelo.hanoi('A', 'B', 'C', vista.numDiscos);
			return;
		}
		if (e.getSource() == vista.btnSiguiente || e.getSource() == vista.t) {
			if (e.getSource() == vista.btnSiguiente) {
				movimientoEntero = true;
				vista.t.start();
				return;
			}
			movimientoActual = modelo.movimientos.get(contador);

			String[] partes = movimientoActual.split(":");
			String movimiento = partes[0];
			
			int disco = Integer.parseInt(partes[1]) - 1;
			char torreDestino = partes[2].charAt(0);
			char torreInicial = partes[3].charAt(0);

			vista.lblPaso.setText("Movimiento " + movimiento+" de "+modelo.movimientos.size());

			if (!vista.bajando && vista.subirDisco(disco, torreInicial)) 
				return;
			
			if (!vista.bajando && vista.ladoDisco(disco, torreInicial, torreDestino)) 
				return;
			
			vista.llegaPorPrimeraVez = true;
			if (vista.bajarDisco(disco, torreDestino)) 
				return;
			
			if (contador == modelo.movimientos.size() - 1) {
				vista.t.stop();
				vista.btnAutomatico.setAnimado(false);
				vista.btnAutomatico.setEnabled(false);
				vista.btnSiguiente.setEnabled(false);
				vista.btnAutomatico.setBackground(Color.GREEN);
				return;
			}
			
			if (movimientoEntero) {
				movimientoEntero = false;
				vista.t.stop();
			}
			
			contador++;
			vista.llegaPorPrimeraVez = true;
		}
		if (e.getSource() == vista.btnAutomatico) {
			vista.automatizar();
			return;
		}
		if (e.getSource() == vista.btnReiniciar) {
			vista.btnAutomatico.setAnimado(false);
			vista.btnEmpezar.setAnimado(true);
			modelo.movimientos.clear();
			contador = 0;
			modelo.contador=0;
			movimientoActual="";
			vista.reiniciarJuego();
			return;
		}
	}
}
