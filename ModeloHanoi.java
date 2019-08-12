package TorresHanoi;

import java.util.ArrayList;

public class ModeloHanoi {
	protected ArrayList<String>movimientos=new ArrayList<String>();
	
	int contador = 0;

	public void hanoi(char torreInicial, char torreCentral, char torreFinal, int numeroDiscos) {

		if (numeroDiscos == 1) {
			contador++;
			movimientos.add(contador+":"+numeroDiscos+":"+torreFinal+":"+torreInicial);
			return;
		}
		hanoi(torreInicial, torreFinal, torreCentral, numeroDiscos - 1);
		contador++;
		movimientos.add(contador+":"+numeroDiscos+":"+torreFinal+":"+torreInicial);
		
		hanoi(torreCentral, torreInicial, torreFinal, numeroDiscos - 1);
	}
	
}
