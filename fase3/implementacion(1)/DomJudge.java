import asint.Impresion;
import asint.SintaxisAbstractaTiny.Bloq;
import c_ast_ascendente.AnalizadorLexicoTiny;
import c_ast_descendente.ParseException;
import c_ast_descendente.TokenMgrError;
import errors.GestionErroresTiny.ErrorLexico;
import errors.GestionErroresTiny.ErrorSintactico;
import impresor.Impresor;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class DomJudge {
   public static void main(String[] args) throws Exception {
	   try {
	        // Reader input = new InputStreamReader(System.in);
	        Reader input  = new InputStreamReader(new FileInputStream("./pruebas_tiny/sample5d.in"));
	        BufferedReader reader = new BufferedReader(input);
	        Bloq prog;
	        if(reader.readLine().startsWith("a")) {
	            System.out.println("CONSTRUCCION AST ASCENDENTE");
	            AnalizadorLexicoTiny alex = new AnalizadorLexicoTiny(reader);
	            c_ast_ascendente.AnalizadorSintacticoTinyDJ asint = new c_ast_ascendente.AnalizadorSintacticoTinyDJ(alex);
	            prog = (Bloq)asint.debug_parse().value;
	        }
	        else {
	            System.out.println("CONSTRUCCION AST DESCENDENTE");
	             c_ast_descendente.AnalizadorSintacticoTinyDJ asint = new c_ast_descendente.AnalizadorSintacticoTinyDJ(reader);
	             asint.disable_tracing();
	             prog = asint.analiza();
	        }
	
	        System.out.println("IMPRESION RECURSIVA");
	        new Impresor().imprime(prog);
	
	        System.out.println("IMPRESION INTERPRETE");
	        prog.imprime();
	
	        System.out.println("IMPRESION VISITANTE");
	        prog.procesa(new Impresion());
	   }
	   catch(TokenMgrError e) {
	      System.out.println("ERROR_LEXICO"); 
	   }
	   catch(ParseException e) {
	      System.out.println("ERROR_SINTACTICO"); 
	   }
	   catch(ErrorLexico e) {
	      System.out.println("ERROR_LEXICO"); 
	   }
	   catch(ErrorSintactico e) {
	      System.out.println("ERROR_SINTACTICO"); 
	   }
    }
}   
