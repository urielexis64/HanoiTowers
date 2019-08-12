package TorresHanoi;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

import JComponentes.*;
import Reuso.Colores;

public class VistaHanoi extends JFrameCerrar {
	protected JLabel lblPaso, lblVelocidad, lblDiscos;
	protected JButton btnSiguiente, btnReiniciar;
	protected JButtonGira btnEmpezar, btnAutomatico;
	private Graphics g;
	private Image back = null;
	protected JPanel panelConfig;
	protected Timer t;
	protected JSlider sliderDiscos, sliderVelocidad;
	protected TorreHanoi torre1, torre2, torre3;
	protected boolean simulacionEmpezada = false, bajando = false, llegaPorPrimeraVez = true;
	private DiscoHanoi[] infoDiscos;
	protected int numDiscos = 3, movLado = 0;

	public VistaHanoi() {
		super("Hanoi", 900, 700);
		hazInterfaz();
		back = createImage(getWidth(), getHeight());
		g = back.getGraphics();
		pintaJuego();
	}

	private void hazInterfaz() {
		setLayout(null);
		getContentPane().setBackground(Color.DARK_GRAY);

		torre1 = new TorreHanoi('A');
		torre2 = new TorreHanoi('B');
		torre3 = new TorreHanoi('C');

		panelConfig = new JPanel(null);
		sliderDiscos = new JSlider(1, 11, 3);
		sliderVelocidad = new JSlider(500, 1000, 500);

		btnEmpezar = new JButtonGira("EMPEZAR", JButtonGira.DERECHA_IZQUIERDA);
		btnSiguiente = new JButton("SIGUIENTE");
		btnAutomatico = new JButtonGira("AUTOMÁTICO", JButtonGira.IZQUIERDA_DERECHA);
		btnReiniciar = new JButton("REINICIAR");

		lblPaso = new JLabel("Movimiento -");
		lblDiscos = new JLabel("Número de discos");
		lblVelocidad = new JLabel("Velocidad");

		lblPaso.setBounds(300, 10, 300, 40);
		lblPaso.setFont(new Font("Impact", Font.PLAIN, 24));

		panelConfig.setBounds(0, 525, 893, 145);
		panelConfig.setBorder(BorderFactory.createDashedBorder(
				new GradientPaint(0, 550, new Color(30, 50, 255), 900, 550, Color.BLACK), 2, 10, 5, true));
		panelConfig.setOpaque(false);

		lblDiscos.setBounds(45, 25, 150, 30);
		lblDiscos.setFont(new Font("Impact", Font.PLAIN, 15));

		sliderDiscos.setPaintTrack(true);
		sliderDiscos.setPaintLabels(true);
		sliderDiscos.setPaintTicks(true);
		sliderDiscos.setMajorTickSpacing(1);
		sliderDiscos.setFont(new Font("Serif", Font.PLAIN, 13));
		sliderDiscos.setForeground(Color.YELLOW);
		sliderDiscos.setBounds(20, 30, 190, 90);
		sliderDiscos.setBorder(LineBorder.createBlackLineBorder());

		lblVelocidad.setBounds(740, 25, 260, 30);
		lblVelocidad.setFont(new Font("Impact", Font.PLAIN, 18));

		sliderVelocidad.setPaintTrack(true);
		sliderVelocidad.setPaintLabels(true);
		sliderVelocidad.setPaintTicks(true);
		sliderVelocidad.setMajorTickSpacing(50);
		sliderVelocidad.setMinorTickSpacing(25);
		sliderVelocidad.setFont(new Font("Serif", Font.PLAIN, 13));
		sliderVelocidad.setForeground(Color.YELLOW);
		sliderVelocidad.setBounds(630, 30, 260, 90);
		sliderVelocidad.setBorder(LineBorder.createBlackLineBorder());
		sliderVelocidad.setEnabled(false);

		btnEmpezar.setBounds(250, 60, 100, 30);
		btnEmpezar.setBackground(Color.GRAY);

		btnSiguiente.setBounds(350, 60, 100, 30);
		btnSiguiente.setEnabled(false);
		btnSiguiente.setBackground(Color.CYAN);

		btnAutomatico.setBounds(450, 60, 120, 30);
		btnAutomatico.setEnabled(false);
		btnAutomatico.setBackground(Color.GREEN);
		btnAutomatico.setAnimado(false);

		btnReiniciar.setBounds(326, 100, 150, 35);
		btnReiniciar.setEnabled(false);
		btnReiniciar.setBackground(Color.ORANGE);

		panelConfig.add(lblVelocidad);
		panelConfig.add(lblDiscos);
		panelConfig.add(sliderDiscos);
		panelConfig.add(sliderVelocidad);
		panelConfig.add(btnEmpezar);
		panelConfig.add(btnSiguiente);
		panelConfig.add(btnAutomatico);
		panelConfig.add(btnReiniciar);
		panelConfig.add(lblPaso);

		add(panelConfig);
		setVisible(true);
	}

	public void paint(Graphics g) {
		g.drawImage(back, 0, 0, getWidth(), getHeight(), null);
	}

	public void pintaJuego() {
		super.paint(g);
		g.setColor(new Color(0, 0, 0, 100));
		g.fillRect(50, 50, 800, 500);
		g.setColor(new Color(0, 0, 0, 200));
		g.fillRect(60, 500, 780, 30);

		g.setFont(new Font("Impact", Font.PLAIN, 80));
		g.drawString("Torres de Hanoi", 200, 120);

		g.fillRect(225, 240, 15, 260);
		g.fillRect(445, 240, 15, 260);
		g.fillRect(665, 240, 15, 260);

		if (!simulacionEmpezada)
			pintaDiscosIniciales(numDiscos);

		for (int i = 0; i < numDiscos; i++) {
			g.setColor(Colores.colores[i]);
			g.fillRoundRect(infoDiscos[i].x, infoDiscos[i].y, infoDiscos[i].w, 20, 25, 25);
		}
		repaint();
	}

	private void pintaDiscosIniciales(int n) {
		infoDiscos = new DiscoHanoi[n];
		int x = 135, y = 480, w = 180;
		for (int i = n - 1; i >= 0; i--) {
			g.setColor(Colores.colores[i]);
			g.fillRoundRect(x, y, w, 20, 25, 25);
			x += 8;
			y -= 20;
			w -= 15;
		}
		iniciaValoresDiscos();
	}

	public void iniciaValoresDiscos() {
		int x = 135, y = 480, w = 180;
		for (int i = numDiscos - 1; i >= 0; i--) {
			infoDiscos[i] = new DiscoHanoi();
			infoDiscos[i].x = x;
			infoDiscos[i].y = y;
			infoDiscos[i].w = w;
			x += 8;
			y -= 20;
			w -= 15;
		}
	}

	public void empezarJuego() {
		btnEmpezar.setEnabled(false);
		sliderDiscos.setEnabled(false);
		btnSiguiente.setEnabled(true);
		btnAutomatico.setEnabled(true);
		sliderVelocidad.setEnabled(true);
		btnReiniciar.setEnabled(true);
		simulacionEmpezada = true;
		torre1.posicionYFinal = 480 - (numDiscos * 20);
		btnEmpezar.setAnimado(false);
		btnAutomatico.setAnimado(true);
	}

	public void automatizar() {
		if (btnAutomatico.getBackground() == Color.red) {
			btnAutomatico.setBackground(Color.GREEN);
			btnSiguiente.setBackground(Color.CYAN);
			btnSiguiente.setEnabled(true);
			t.stop();
		} else {
			btnAutomatico.setBackground(Color.red);
			btnSiguiente.setBackground(Color.BLUE);
			btnSiguiente.setEnabled(true);
			t.start();
			
		}
		sliderVelocidad.setEnabled(true);
		btnEmpezar.setEnabled(false);
	}

	public void reiniciarJuego() {
		t.stop();
		movLado = 0;
		llegaPorPrimeraVez = true;
		bajando = false;
		simulacionEmpezada = false;
		sliderDiscos.setEnabled(true);
		btnEmpezar.setEnabled(true);
		sliderVelocidad.setEnabled(false);
		btnAutomatico.setEnabled(false);
		btnSiguiente.setEnabled(false);
		btnReiniciar.setEnabled(false);
		lblPaso.setText("Movimiento -");
		torre2.posicionYFinal = 480;
		torre3.posicionYFinal = 480;
		pintaJuego();
	}

	public void setControlador(ControladorHanoi c) {
		sliderDiscos.addChangeListener(c);
		sliderVelocidad.addChangeListener(c);
		btnSiguiente.addActionListener(c);
		btnEmpezar.addActionListener(c);
		btnAutomatico.addActionListener(c);
		btnReiniciar.addActionListener(c);
		t = new Timer(500, c);
	}

	public boolean subirDisco(int disco, char torreInicial) {
		if (infoDiscos[disco].y == 190 && !bajando) {
			if (!llegaPorPrimeraVez)
				return false;
			switch (torreInicial) {
			case 'A':
				torre1.posicionYFinal += 20;
				llegaPorPrimeraVez = false;
				return false;
			case 'B':
				torre2.posicionYFinal += 20;
				llegaPorPrimeraVez = false;
				return false;
			case 'C':
				torre3.posicionYFinal += 20;
				llegaPorPrimeraVez = false;
				return false;
			}
		}
		infoDiscos[disco].y -= 10;
		pintaJuego();
		return true;
	}

	public boolean bajarDisco(int disco, char torreDestino) {
		bajando = true;
		switch (torreDestino) {
		case 'A':
			if (infoDiscos[disco].y == torre1.posicionYFinal) {
				if (llegaPorPrimeraVez) {
					torre1.posicionYFinal -= 20;
					llegaPorPrimeraVez = false;
				}
				bajando = false;
				return false;
			}
			infoDiscos[disco].y += 10;
			pintaJuego();
			return true;
		case 'B':
			if (infoDiscos[disco].y == torre2.posicionYFinal) {
				if (llegaPorPrimeraVez) {
					torre2.posicionYFinal -= 20;
					llegaPorPrimeraVez = false;
				}
				bajando = false;
				return false;
			}
			infoDiscos[disco].y += 10;
			pintaJuego();
			return true;
		case 'C':
			if (infoDiscos[disco].y == torre3.posicionYFinal) {
				if (llegaPorPrimeraVez) {
					torre3.posicionYFinal -= 20;
					llegaPorPrimeraVez = false;
				}
				bajando = false;
				return false;
			}
			infoDiscos[disco].y += 10;
			pintaJuego();
			return true;
		}
		return false;
	}

	public boolean ladoDisco(int disco, char torreInicial, char torreDestino) {
		switch (torreInicial) {
		case 'A':
			if (torreDestino == 'B' && movLado < 220) {
				infoDiscos[disco].x += 10;
				movLado += 10;
				pintaJuego();
				return true;
			}
			if (torreDestino == 'C' && movLado < 440) {
				infoDiscos[disco].x += 10;
				movLado += 10;
				pintaJuego();
				return true;
			}
			movLado = 0;
			return false;
		case 'B':
			if (torreDestino == 'A' && movLado > -220) {
				infoDiscos[disco].x -= 10;
				movLado -= 10;
				pintaJuego();
				return true;
			}
			if (torreDestino == 'C' && movLado < 220) {
				infoDiscos[disco].x += 10;
				movLado += 10;
				pintaJuego();
				return true;
			}
			movLado = 0;
			return false;
		default:
			if (torreDestino == 'A' && movLado > -440) {
				infoDiscos[disco].x -= 10;
				movLado -= 10;
				pintaJuego();
				return true;
			}
			if (torreDestino == 'B' && movLado > -220) {
				infoDiscos[disco].x -= 10;
				movLado -= 10;
				pintaJuego();
				return true;
			}
			movLado = 0;
			return false;
		}
	}
}