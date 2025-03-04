package asint;

import alex.UnidadLexica;
import alex.AnalizadorLexicoTiny0;
import alex.ClaseLexica;
import errors.GestionErroresTiny0;
import java.io.IOException;
import java.io.Reader;
import java.util.EnumSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnalizadorSintacticoTiny0 {
   private UnidadLexica anticipo;       // token adelantado
   private AnalizadorLexicoTiny0 alex;   // analizador léxico 
   private GestionErroresTiny0 errores;  // gestor de errores
   private Set<ClaseLexica> esperados;  // clases léxicas esperadas
   
   public AnalizadorSintacticoTiny0(Reader input) throws IOException {
        // se crea el gestor de errores
      errores = new GestionErroresTiny0();
        // se crea el analizador léxico
      alex = new AnalizadorLexicoTiny0(input, errores);
      esperados = EnumSet.noneOf(ClaseLexica.class);
        // Se lee el primer token adelantado
      sigToken();                      
   }
   public void analiza() {
      programa();
      empareja(ClaseLexica.EOF);
   }
   
   private void programa() {
       bloque();
   }
   
   private void bloque() {
      switch(anticipo.clase()) {
       case LLAVEAP: 
           empareja(ClaseLexica.LLAVEAP);
           seccion_declaraciones_opt();
           seccion_instrucciones_opt();
           empareja(ClaseLexica.LLAVECIERRE);
           break;
       default:        
          esperados(ClaseLexica.LLAVEAP);
          error();
          break;
       } 
   }
   
   private void seccion_declaraciones_opt() {
	  switch(anticipo.clase()) {
       case INT: case REAL: case BOOL: 
    	   seccion_declaraciones();
    	   empareja(ClaseLexica.FINAL_ASIG);
           break;
       default:        
            esperados(ClaseLexica.INT, ClaseLexica.REAL, ClaseLexica.BOOL);
            break;
	   }
   }
   
   private void seccion_declaraciones() {
	   declaracion();
	   resto_sd();
   }

   private void resto_sd() {
	  switch(anticipo.clase()) {
	   case PCOMA: 
		   empareja(ClaseLexica.PCOMA);
		   declaracion();
		   resto_sd();
		   break;
	   default:        
		   esperados(ClaseLexica.PCOMA);
		   break;
	   }
   }
   
   private void declaracion() {
	   tipo_nombre();
   }
   
   private void tipo_nombre() {
	   tipo_base();
	   empareja(ClaseLexica.IDEN);
   }
   
   private void tipo_base() {
	  switch(anticipo.clase()) {
	   case INT: empareja(ClaseLexica.INT); break;
	   case REAL: empareja(ClaseLexica.REAL); break;
	   case BOOL: empareja(ClaseLexica.BOOL); break;
	   default:        
		   esperados(ClaseLexica.INT, ClaseLexica.REAL, ClaseLexica.BOOL);
		   error();
		   break;
	   }
   }
   private void seccion_instrucciones_opt() {
	  switch(anticipo.clase()) {
       case ARROBA: 
    	   seccion_instrucciones();
           break;
       default:        
           esperados(ClaseLexica.ARROBA);
           break;
	   }
   }
   
   private void seccion_instrucciones() {
       lista_instrucciones();
   }
   
   private void lista_instrucciones() {
       instruccion();
       resto_li();
   }
   
   private void resto_li() {
	  switch(anticipo.clase()) {
       case PCOMA: 
    	   empareja(ClaseLexica.PCOMA);
    	   instruccion();
    	   resto_li();
           break;
       default:        
           esperados(ClaseLexica.PCOMA);
           break;
	   }
   }
   
   private void instruccion() {
	  switch(anticipo.clase()) {
       case ARROBA: 
    	   empareja(ClaseLexica.ARROBA);
    	   expresion();
           break;
       default:        
           esperados(ClaseLexica.ARROBA);
           break;
	   }
   }
   
   private void expresion() {
       E0();
   }
   
   private void E0() {
       E1();
       resto_E0();
   }
   
   private void resto_E0() {
      switch(anticipo.clase()) {
       case ASIG:
    	  empareja(ClaseLexica.ASIG);
          E0();
          break;
       default: 
          esperados(ClaseLexica.ASIG);
          break;
      }    
    }
   

   private void E1() {
       E2();
       resto_E1();
   }
   
   private void resto_E1() {
       switch(anticipo.clase()) {
  		case IGUAL: case DISTINTO: 
  		case MENOR: case MENOR_IGUAL:
  		case MAYOR: case MAYOR_IGUAL:
  			op_relacional();
  			E2();
  			resto_E1();
  			break;
      default: 
          	esperados(ClaseLexica.IGUAL,ClaseLexica.DISTINTO, 
          			  ClaseLexica.MENOR,ClaseLexica.MENOR_IGUAL, 
          			  ClaseLexica.MAYOR,ClaseLexica.MAYOR_IGUAL);
          	break;
       }    
    }
   
   private void E2() {
	   E3();
	   resto_E2_F();
	   resto_E2_R();
   }

   private void resto_E2_R() {
       switch(anticipo.clase()) {
  		case MAS:
  		   empareja(ClaseLexica.MAS);
  		   E3();
  		   resto_E2_R();
  		   break;
  		default: 
	       esperados(ClaseLexica.MAS);
	       break;
       }    
   }
   
   private void resto_E2_F() {
	   switch(anticipo.clase()) {
  		case MENOS:
   			empareja(ClaseLexica.MENOS);
   			E3();
   			break;
		default: 
			esperados(ClaseLexica.MENOS);
			break;
   	} 	  
   }
   
   private void E3() {
	   E4();
	   resto_E3();
   }
   
   private void resto_E3() {
	   switch(anticipo.clase()) {
  		case AND:
   			empareja(ClaseLexica.AND);
   			E3();
   			break;
   		case OR:
   			empareja(ClaseLexica.OR);
   			E4();
   			break;
  		default: 
			esperados(ClaseLexica.AND, ClaseLexica.OR );
			break;
       	} 	  
   }
   
   private void E4() {
	   E5();
	   resto_E4();
   }
   
   private void resto_E4() {
	   switch(anticipo.clase()) {
  		case MUL: case DIV: 
   			op_mult();
   			E5();
   			resto_E4();
   			break;
  		default: 
			esperados(ClaseLexica.MUL, ClaseLexica.DIV );
			break;
       	} 	  
   }
   
   private void E5() {
	   switch(anticipo.clase()) {
  		case MENOS:
   			empareja(ClaseLexica.MENOS);
   			E5();
   			break;
   		case NOT:
   			empareja(ClaseLexica.NOT);
   			E5();
   			break;
   		case LIT_ENT: case LIT_REAL: case TRUE: case FALSE: case IDEN: case PAP:
   			E6();
   			break;
  		default: 
  			esperados(ClaseLexica.LIT_ENT,ClaseLexica.LIT_REAL, 
  					  ClaseLexica.TRUE,ClaseLexica.FALSE, 
  					  ClaseLexica.IDEN);
			error();
			break;
       	} 
   }
   
   private void E6() {
	   switch(anticipo.clase()) {
  		case LIT_ENT: case LIT_REAL: case TRUE: case FALSE: case IDEN: 
  			expresion_basica();
   			break;
   		case PAP:
   			empareja(ClaseLexica.PAP);
   			E0();
   			empareja(ClaseLexica.PCIERRE); 
   			break;
  		default: 
          	esperados(ClaseLexica.LIT_ENT,ClaseLexica.LIT_REAL, 
        			  ClaseLexica.TRUE,ClaseLexica.FALSE, 
        			  ClaseLexica.IDEN);
			error();
			break;
       	} 
   }
   
   private void expresion_basica() {
	   switch(anticipo.clase()) {
  		case LIT_ENT: 
  			empareja(ClaseLexica.LIT_ENT);
   			break;
  		case LIT_REAL:
  			empareja(ClaseLexica.LIT_REAL);
   			break;
  		case TRUE:
  			empareja(ClaseLexica.TRUE);
   			break;
  		case FALSE:
  			empareja(ClaseLexica.FALSE);
   			break;
  		case IDEN:
  			empareja(ClaseLexica.IDEN);
   			break;
  		default: 
          	esperados(ClaseLexica.LIT_ENT,ClaseLexica.LIT_REAL, 
        			  ClaseLexica.TRUE,ClaseLexica.FALSE, 
        			  ClaseLexica.IDEN);
			error();
			break;
       	} 
   }
   
   private void op_relacional() {
	   switch(anticipo.clase()) {
  		case MENOR: 
  			empareja(ClaseLexica.MENOR);
   			break;
  		case MENOR_IGUAL:
  			empareja(ClaseLexica.MENOR_IGUAL);
   			break;
  		case MAYOR:
  			empareja(ClaseLexica.MAYOR);
   			break;
  		case MAYOR_IGUAL:
  			empareja(ClaseLexica.MAYOR_IGUAL);
   			break;
  		case IGUAL:
  			empareja(ClaseLexica.IGUAL);
   			break;
  		case DISTINTO:
  			empareja(ClaseLexica.DISTINTO);
   			break;
  		default: 
          	esperados(ClaseLexica.MENOR,ClaseLexica.MENOR_IGUAL, 
        			  ClaseLexica.MAYOR,ClaseLexica.MAYOR_IGUAL, 
        			  ClaseLexica.IGUAL,ClaseLexica.DISTINTO);
			error();
			break;
       	}  
   }
   
   private void op_mult() {
	   switch(anticipo.clase()) {
  		case MUL: 
  			empareja(ClaseLexica.MUL);
   			break;
  		case DIV:
  			empareja(ClaseLexica.DIV);
   			break;
  		default: 
          	esperados(ClaseLexica.MUL,ClaseLexica.DIV);
			error();
			break;
       	}  
   }     

   private void esperados(ClaseLexica ... esperadas) {
       for(ClaseLexica c: esperadas) {
           esperados.add(c);
       }
   }
   
   
   private void empareja(ClaseLexica claseEsperada) {
      if (anticipo.clase() == claseEsperada) {
          traza_emparejamiento(anticipo);
          sigToken();
      }    
      else {
          esperados(claseEsperada);
          error();
      }
   }
   private void sigToken() {
      try {
        anticipo = alex.sigToken();
        esperados.clear();
      }
      catch(IOException e) {
        errores.errorFatal(e);
      }
   }
   
    private void error() {
        errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), esperados);
    }
    
    protected void traza_emparejamiento(UnidadLexica anticipo) {}  
}
