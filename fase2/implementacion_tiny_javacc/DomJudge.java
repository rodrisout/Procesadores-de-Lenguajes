import asint.AnalizadorSintacticoTiny;
import asint.AnalizadorSintacticoTinyDJ;
import asint.ParseException;
import asint.TokenMgrError;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
public class DomJudge{
   public static void main(String[] args) throws Exception {
     try{  
      
//      Reader input  = new InputStreamReader(new FileInputStream("./pruebas_tiny/sample3.in"));
//      AnalizadorSintacticoTiny asint = new AnalizadorSintacticoTinyDJ(input);
      AnalizadorSintacticoTiny asint = new AnalizadorSintacticoTinyDJ(new InputStreamReader(System.in));
      asint.analiza();
     }
     catch(ParseException e) {
        System.out.println("ERROR_SINTACTICO"); 
     }
     catch(TokenMgrError e) {
        System.out.println("ERROR_LEXICO"); 
     }
   }
}