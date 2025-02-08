package alex;

import java.io.FileInputStream;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.IOException;

public class AnalizadorLexicoTiny {
    
   public static class ECaracterInesperado extends RuntimeException {
       public ECaracterInesperado(String msg) {
           super(msg);
       }
   }; 

   private Reader input;
   private StringBuffer lex;
   private int sigCar;
   private int filaInicio;
   private int columnaInicio;
   private int filaActual;
   private int columnaActual;
   private static String NL = System.getProperty("line.separator");
   
   private static enum Estado {
    INICIO, REC_POR, REC_DIV, REC_PAP, REC_PCIERR, REC_PCOMA, REC_ARROBA, REC_IGUAL, REC_IGUAL_IGUAL, REC_COMMENT, REC_ALMOHADILLA,
    REC_LLAVEAP, REC_LLAVE_CIERRE, REC_MAYOR, REC_MAYOR_IGUAL, REC_MENOR, REC_MENOR_IGUAL, REC_DISTINTO, REC_EXCLAMACION, 
    REC_ET, REC_ET_ET, REC_MAS, REC_MENOS, REC_ID, REC_ENT, REC_0, REC_PUNTO, REC_DEC, REC_0DEC,
    REC_E, REC_E_ZERO, REC_E_POST, REC_E_ANT, REC_EOF
   }

   private Estado estado;

   public AnalizadorLexicoTiny(Reader input) throws IOException {
    this.input = input;
    lex = new StringBuffer();
    sigCar = input.read();
    filaActual=1;
    columnaActual=1;
   }
   
   public UnidadLexica sigToken() throws IOException {
     estado = Estado.INICIO;
     filaInicio = filaActual;
     columnaInicio = columnaActual;
     lex.delete(0,lex.length());
     while(true) {
         switch(estado) {
           case INICIO: 
              if(hayLetra()|| haySub())  transita(Estado.REC_ID);
              else if (hayDigitoPos()) transita(Estado.REC_ENT);
              else if (hayCero()) transita(Estado.REC_0);
              else if (haySuma()) transita(Estado.REC_MAS);
              else if (hayResta()) transita(Estado.REC_MENOS);
              else if (hayMul()) transita(Estado.REC_POR);
              else if (hayDiv()) transita(Estado.REC_DIV);
              else if (hayPAp()) transita(Estado.REC_PAP);
              else if (hayPCierre()) transita(Estado.REC_PCIERR);
              else if (hayLlaveAp()) transita(Estado.REC_LLAVEAP);
              else if (hayLlaveCierre()) transita(Estado.REC_LLAVE_CIERRE);
              else if (hayIgual()) transita(Estado.REC_IGUAL);
              else if (hayMayor()) transita(Estado.REC_MAYOR);
              else if (hayMenor()) transita(Estado.REC_MENOR);
              else if (hayExclamacion()) transita(Estado.REC_EXCLAMACION);
              else if (hayEt()) transita(Estado.REC_ET);
              else if (hayPComa()) transita(Estado.REC_PCOMA);
              else if (hayArroba()) transita(Estado.REC_ARROBA);
              else if (hayAlmohadilla()) transitaIgnorando(Estado.REC_ALMOHADILLA);
              else if (haySep()) transitaIgnorando(Estado.INICIO);
              else if (hayEOF()) transita(Estado.REC_EOF);
              else error();
              break;
           case REC_ID: 
              if (hayLetra() || hayDigito()|| haySub()) transita(Estado.REC_ID);
              else return unidadId();               
              break;
           case REC_ENT:
               if (hayDigito()) transita(Estado.REC_ENT);
               else if (hayPunto()) transita(Estado.REC_PUNTO);
               else if (hayE()) transita(Estado.REC_E);
               else return unidadLitEnt();
               break;
           case REC_0:
               if (hayPunto()) transita(Estado.REC_PUNTO);
               else if(hayE()) transita(Estado.REC_E); 
               else return unidadLitEnt();
               break;
           case REC_MAS:
               if (hayDigitoPos()) transita(Estado.REC_ENT);
               else if(hayCero()) transita(Estado.REC_0);
               else return unidadMas();
               break;
           case REC_MENOS: 
               if (hayDigitoPos()) transita(Estado.REC_ENT);
               else if(hayCero()) transita(Estado.REC_0);
               else return unidadMenos();
               break;
           case REC_E:
               if (hayDigitoPos()) transita(Estado.REC_E_POST);
               else if(haySuma() || hayResta()) transita(Estado.REC_E_ANT);
               else if(hayCero()) transita(Estado.REC_E_ZERO);
               else error();
               break;
           case REC_E_POST:
               if (hayDigito()) transita(Estado.REC_E_POST);
               else return unidadLitReal();
               break;
           case REC_E_ZERO: return unidadLitReal();
           case REC_E_ANT:
               if (hayDigitoPos()) transita(Estado.REC_E_POST);
               else if(hayCero()) transita(Estado.REC_E_ZERO);
               else error();
               break;
           case REC_POR: return unidadMul();
           case REC_DIV: return unidadDiv();              
           case REC_PAP: return unidadPAp();
           case REC_PCIERR: return unidadPCierre();
           case REC_LLAVEAP: return unidadLlaveAp();
           case REC_LLAVE_CIERRE: return unidadLlaveCierre();
           case REC_PCOMA: return unidadPComa();
           case REC_ARROBA: return unidadArroba();
           case REC_ALMOHADILLA: 
        	   if (hayAlmohadilla()) transitaIgnorando(Estado.REC_COMMENT);
        	   else error();
        	   break;
           case REC_IGUAL: 
        	   if(hayIgual()) transita(Estado.REC_IGUAL_IGUAL);
        	   else return unidadAsig();
        	   break;
           case REC_IGUAL_IGUAL: return unidadIgual();
           case REC_MAYOR:
        	   if(hayIgual()) transita(Estado.REC_MAYOR_IGUAL);
        	   else return unidadMayor();
        	   break;
           case REC_MAYOR_IGUAL: return unidadMayorIgual();
           case REC_MENOR: 
        	   if (hayIgual()) transita(Estado.REC_MENOR_IGUAL);
        	   else return unidadMenor();
        	   break;
           case REC_MENOR_IGUAL: return unidadMenorIgual();
           case REC_EXCLAMACION: 
        	   if(hayIgual()) transita(Estado.REC_DISTINTO);
        	   else error();
        	   break;
           case REC_DISTINTO: return unidadDistinto();
           case REC_ET: 
        	   if(hayEt()) transita(Estado.REC_ET_ET);
        	   else error();
        	   break;
           case REC_ET_ET: return unidadEtEt();
           case REC_COMMENT: 
               if (hayNL()) transitaIgnorando(Estado.INICIO);
               else if (hayEOF()) transita(Estado.REC_EOF);
               else transitaIgnorando(Estado.REC_COMMENT);
               break;
           case REC_EOF: return unidadEof();
           case REC_PUNTO:
               if (hayDigito()) transita(Estado.REC_DEC);
               else error();
               break;
           case REC_DEC: 
               if (hayDigitoPos()) transita(Estado.REC_DEC);
               else if(hayE()) transita(Estado.REC_E);
               else if (hayCero()) transita(Estado.REC_0DEC);
               else return unidadLitReal();
               break;
           case REC_0DEC: 
               if (hayDigitoPos()) transita(Estado.REC_DEC);
               else if (hayCero()) transita(Estado.REC_0DEC);
               else error();
               break;

         }
     }    
   }
   private void transita(Estado sig) throws IOException {
     lex.append((char)sigCar);
     sigCar();         
     estado = sig;
   }
   private void transitaIgnorando(Estado sig) throws IOException {
     sigCar();         
     filaInicio = filaActual;
     columnaInicio = columnaActual;
     estado = sig;
   }
   private void sigCar() throws IOException {
     sigCar = input.read();
     if (sigCar == NL.charAt(0)) saltaFinDeLinea();
     if (sigCar == '\n') {
        filaActual++;
        columnaActual=0;
     }
     else {
       columnaActual++;  
     }
   }
   private void saltaFinDeLinea() throws IOException {
      for (int i=1; i < NL.length(); i++) {
          sigCar = input.read();
          if (sigCar != NL.charAt(i)) error();
      }
      sigCar = '\n';
   }
   
   private boolean hayLetra() {return sigCar >= 'a' && sigCar <= 'z' ||
                                      sigCar >= 'A' && sigCar <= 'Z';}
   private boolean haySub() {return sigCar == '_';}
   private boolean hayDigitoPos() {return sigCar >= '1' && sigCar <= '9';}
   private boolean hayCero() {return sigCar == '0';}
   private boolean hayDigito() {return hayDigitoPos() || hayCero();}
   private boolean haySuma() {return sigCar == '+';}
   private boolean hayResta() {return sigCar == '-';}
   private boolean hayMul() {return sigCar == '*';}
   private boolean hayDiv() {return sigCar == '/';}
   private boolean hayPAp() {return sigCar == '(';}
   private boolean hayPCierre() {return sigCar == ')';}
   private boolean hayLlaveAp() {return sigCar == '{';}
   private boolean hayLlaveCierre() {return sigCar == '}';}
   private boolean hayIgual() {return sigCar == '=';}
   private boolean hayMayor() {return sigCar == '>';}
   private boolean hayMenor() {return sigCar == '<';}
   private boolean hayExclamacion() {return sigCar == '!';}
   private boolean hayEt() {return sigCar == '&';}
   private boolean hayPComa() {return sigCar == ';';}
   private boolean hayPunto() {return sigCar == '.';}
   private boolean hayArroba() {return sigCar == '@';}
   private boolean hayAlmohadilla() {return sigCar == '#';}
   private boolean hayE() {return sigCar == 'e' || sigCar == 'E';}
   private boolean haySep() {return sigCar == ' ' || sigCar == '\t' || sigCar=='\n';}
   private boolean hayNL() {return sigCar == '\r' || sigCar == '\b' || sigCar == '\n';}
   private boolean hayEOF() {return sigCar == -1;}
   private UnidadLexica unidadId() {
     switch(lex.toString().toLowerCase()) {
         case "int":  
             return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.INT);
         case "real":  
             return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.REAL);
         case "and":  
             return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.AND);
         case "or":  
             return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.OR);
         case "not":  
             return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.NOT);
         case "true":  
             return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.TRUE);
         case "false":  
             return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.FALSE);
         case "bool":  
             return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.BOOL);
         default:    
            return new UnidadLexicaMultivaluada(filaInicio,columnaInicio,ClaseLexica.IDEN,lex.toString());     
      }
   }  
   private UnidadLexica unidadLitEnt() {
	     return new UnidadLexicaMultivaluada(filaInicio,columnaInicio,ClaseLexica.LIT_ENT,lex.toString());     
	   }    
   private UnidadLexica unidadLitReal() {
	     return new UnidadLexicaMultivaluada(filaInicio,columnaInicio,ClaseLexica.LIT_REAL,lex.toString());     
	   }       
   private UnidadLexica unidadMas() {
     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.MAS);     
   }    
   private UnidadLexica unidadMenos() {
     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.MENOS);     
   }    
   private UnidadLexica unidadMul() {
     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.MUL);     
   }    
   private UnidadLexica unidadDiv() {
     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.DIV);     
   } 
   private UnidadLexica unidadAsig() {
	     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.ASIG);     
	   }  
   private UnidadLexica unidadIgual() {
	     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.IGUAL);     
	   } 
   private UnidadLexica unidadDistinto() {
	     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.DISTINTO);     
	   } 
   private UnidadLexica unidadMayor() {
	     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.MAYOR);     
	   } 
   private UnidadLexica unidadMayorIgual() {
	     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.MAYOR_IGUAL);     
	   } 
   private UnidadLexica unidadMenorIgual() {
	     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.MENOR_IGUAL);     
	   }
   private UnidadLexica unidadMenor() {
	     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.MENOR);     
	   } 
   private UnidadLexica unidadPAp() {
     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.PAP);     
   }    
   private UnidadLexica unidadPCierre() {
     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.PCIERRE);     
   }  
   private UnidadLexica unidadLlaveAp() {
	     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.LLAVEAP);     
	   }  
   private UnidadLexica unidadLlaveCierre() {
	     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.LLAVECIERRE);     
	   }  
   private UnidadLexica unidadPComa() {
     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.PCOMA);     
   }   
   private UnidadLexica unidadArroba() {
	     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.ARROBA);     
	   }  
   private UnidadLexica unidadEtEt() {
	     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.FINAL_ASIG);     
	   } 
   private UnidadLexica unidadEof() {
     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.EOF);     
   }    
   private void error() {
     int curCar = sigCar;
     try{
       sigCar();
     }
     catch(IOException e) {}
     throw new ECaracterInesperado("("+filaActual+','+columnaActual+"):Caracter inexperado:"+(char)curCar); 
   }

   public static void main(String arg[]) throws IOException {
     Reader input = new InputStreamReader(new FileInputStream("./casos_manual/0.in"));
     AnalizadorLexicoTiny al = new AnalizadorLexicoTiny(input);
     UnidadLexica unidad;
     do {
       unidad = al.sigToken();
       System.out.println(unidad);
     }
     while (unidad.clase() != ClaseLexica.EOF);
    } 
}