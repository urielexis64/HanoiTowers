package JComponentes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.Timer;

import Rutinas.Rutinas;

public class JButtonGira extends JButton implements ActionListener {

	private Timer t;
	public static final int DERECHA_IZQUIERDA = 0;
	public static final int IZQUIERDA_DERECHA = 1;
	private String textoOriginal;
	private int sentido = DERECHA_IZQUIERDA, velocidad=200;

	public JButtonGira(String texto, int sentido) {
		super(texto);
		textoOriginal = texto;
		this.sentido = sentido;
		setBorder(null);
		t = new Timer(velocidad, this);
		t.start();
	}

	public void actionPerformed(ActionEvent e) {
		String texto;
		if (sentido == 0) {
			texto = Rutinas.ponEspacios(getText(), 12);
			setText(texto.substring(1) + texto.charAt(0));
		} else {
			texto = Rutinas.rellenaCaracter(getText(), ' ', 12, Rutinas.IZQUIERDA);
			setText(texto.charAt(texto.length() - 1) + texto.substring(0, texto.length() - 1));
		}
	}

	public void setAnimado(boolean animado) {
		if (animado)
			t.start();
		else {
			t.stop();
			setText(textoOriginal);
		}
	}

	public void setSentido(int sentido) {
		if (sentido > 1 || sentido < 0)
			sentido = 0;
		else
			this.sentido = sentido;
	}
	
	public void setVelocidad(int velocidad) {
		this.velocidad=velocidad;
		t.setDelay(this.velocidad);
		t.restart();
	}

}
