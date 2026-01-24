package errors;

import asint.SintaxisAbstractaTiny.Nodo;

public class ErroresDJ extends Errores {
	public void error(Nodo n, String msg) {
		hay_error = true;
		n.ponError();
	}
}
