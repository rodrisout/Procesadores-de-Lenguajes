import asint.SintaxisAbstractaTiny.Bloq;
import c_ast_ascendente.AnalizadorLexicoTiny;
//import c_ast_descendente.ConstructorASTsTiny;
//import c_ast_descendente.ParseException;
//import c_ast_descendente.SimpleCharStream;
//import c_ast_descendente.TokenMgrError;
import errors.GestionErroresTiny.ErrorLexico;
import errors.GestionErroresTiny.ErrorSintactico;
import evaluador.Impresor;
//import impresion.Impresion;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;

public class DomJudge {
   public static void main(String[] args) throws Exception {
	   try {
	        // Reader input = new InputStreamReader(System.in);
	        Reader input  = new InputStreamReader(new FileInputStream("./pruebas_tiny/sample5a.in"));
	        BufferedReader br = new BufferedReader(input);
	        Bloq p;
//	        if(br.readLine().startsWith("a")) {
	            System.out.println("CONSTRUCCION AST ASCENDENTE");
	            AnalizadorLexicoTiny alex = new AnalizadorLexicoTiny(br);
	            c_ast_ascendente.AnalizadorSintacticoTinyDJ asint = new c_ast_ascendente.AnalizadorSintacticoTinyDJ(alex);
	            p = (Bloq)asint.debug_parse().value;
//	        }
//	        else {
//	            System.out.println("CONSTRUCCION AST DESCENDENTE");
//	             c_ast_descendente.ConstructorASTsTiny asint = new c_ast_descendente.ConstructorASTsTinyDJ(br);
//	             asint.disable_tracing();
//	             p = asint.analiza();
//	        }
	
	        System.out.println("IMPRESION RECURSIVA");
	        new Impresor().imprime(p);
	
//	        System.out.println("IMPRESION INTERPRETE");
//	        p.imprime();
//	
//	        System.out.println("IMPRESION VISITANTE");
//	        p.procesa(new Impresion());
	   }
//	   catch(TokenMgrError e) {
//	      System.out.println("ERROR_LEXICO"); 
//	   }
//	   catch(ParseException e) {
//	      System.out.println("ERROR_SINTACTICO"); 
//	   }
	   catch(ErrorLexico e) {
	      System.out.println("ERROR_LEXICO"); 
	   }
	   catch(ErrorSintactico e) {
	      System.out.println("ERROR_SINTACTICO"); 
	   }
    }
}   
