package main;

import java.io.Reader;

import asint.SintaxisAbstractaTiny.Bloq;
import c_ast_ascendente.AnalizadorLexicoTiny;
import c_ast_descendente.ParseException;
import c_ast_descendente.TokenMgrError;
import errors.Errores;
import errors.GestionErroresTiny.ErrorLexico;
import errors.GestionErroresTiny.ErrorSintactico;
import procesa.Pretipado;
import procesa.Tipado;
import procesa.Vinculador;

public class Main {

	public static void main(String[] args) throws Exception {
		char constructor = (char)System.in.read();
		Reader r = new BISReader(System.in);
//		char constructor = (char)new FileInputStream("./pruebas/casos_errores/01_errores_vinculacion_a.in").read();
//		Reader r = new BISReader(new FileInputStream("./pruebas/casos_errores/01_errores_vinculacion_a.in"));
		Bloq prog = construye_ast(r,constructor);
		if(prog != null) {
			procesa(prog, r);
		} 
	}
	
	private static Bloq construye_ast(Reader input, char constructor) throws Exception {
		if(constructor == 'a') {
			 try {
				 AnalizadorLexicoTiny alex = new AnalizadorLexicoTiny(input);
				 c_ast_ascendente.AnalizadorSintacticoTinyDJ asint = new c_ast_ascendente.AnalizadorSintacticoTinyDJ(alex);
				 Bloq p = (Bloq)asint.parse().value;
				 return p;
			 }
			 catch(ErrorLexico e) {
				 System.out.println("ERROR_LEXICO");
			 }
			 catch(ErrorSintactico e) {
				 System.out.println("ERROR_SINTACTICO");
				 System.exit(0);
			 }
		}
		else if(constructor == 'd') {
			 try {
				 c_ast_descendente.AnalizadorSintacticoTinyDJ asint = new c_ast_descendente.AnalizadorSintacticoTinyDJ(input);
				 asint.disable_tracing();
				 return asint.analiza();
			 }
			 catch(TokenMgrError e) {
				 System.out.println("ERROR_LEXICO");
			 }
			 catch(ParseException e) {
				 System.out.println("ERROR_SINTACTICO");
				 System.exit(0);
			 }
		}
		else {
			 System.err.println("Metodo de construccion no soportado:"+constructor);
		 }
		 return null;
	}
	
	private static void procesa(Bloq prog, Reader datos) throws Exception {
		Errores errores = new Errores();
		new Vinculador(errores).vincula(prog);
		if(!errores.hayError()) {
			prog.procesa(new Pretipado(errores));
			if(!errores.hayError()) {
				prog.procesa(new Tipado(errores));
				if(!errores.hayError()) {
//					prog.procesa(new AsignacionEspacio());
//					new Etiquetado().procesa(p);
//					MaquinaP m = new MaquinaP(datos,500,5000,5000,10);
//					new GeneracionCodigo(m).procesa(p);
//					m.ejecuta();
				}
			}
		}
	}

}
