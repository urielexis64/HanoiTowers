package TorresHanoi;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class PrincipalHanoi {
	public static void main(String... arg) {
		try {
			UIManager.setLookAndFeel(new NimbusLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		VistaHanoi vista=new VistaHanoi();
		ModeloHanoi modelo =new ModeloHanoi();
		ControladorHanoi controlador=new ControladorHanoi(vista, modelo);
		vista.setControlador(controlador);		
	}
}
