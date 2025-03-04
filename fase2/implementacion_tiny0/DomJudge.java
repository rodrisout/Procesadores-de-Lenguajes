import asint.AnalizadorSintacticoTiny0;
import asint.AnalizadorSintacticoTiny0DJ;
import errors.GestionErroresTiny0.ErrorLexico;
import errors.GestionErroresTiny0.ErrorSintactico;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
public class DomJudge{
   public static void main(String[] args) throws Exception {
     try{  
//      Reader input  = new InputStreamReader(new FileInputStream("./pruebas_tiny0/sample3.in"));
//      AnalizadorSintacticoTiny0 asint = new AnalizadorSintacticoTiny0DJ(input);
      AnalizadorSintacticoTiny0 asint = new AnalizadorSintacticoTiny0DJ(new InputStreamReader(System.in));
      asint.analiza();
     }
     catch(ErrorSintactico e) {
        System.out.println("ERROR_SINTACTICO"); 
     }
     catch(ErrorLexico e) {
        System.out.println("ERROR_LEXICO"); 
     }
   }
}