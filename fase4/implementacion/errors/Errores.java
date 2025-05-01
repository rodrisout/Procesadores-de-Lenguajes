package errors;

import asint.SintaxisAbstractaTiny.Nodo;

public class Errores {
	protected boolean hay_error;
	public Errores() {
		hay_error = false;
	}
	public boolean hayError() {
		return hay_error;
	}

	public void error(Nodo n, String msg) {
		hay_error = true;
		System.err.println(n.leeFila()+","+n.leeCol()+":"+msg);
	}
}
