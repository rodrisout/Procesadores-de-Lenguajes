package alex;

public class ALexOperations {
  public static class ECaracterInesperado extends RuntimeException {
      public ECaracterInesperado(String msg) {
          super(msg);
      }
  }  
  private AnalizadorLexicoTiny alex;
  public ALexOperations(AnalizadorLexicoTiny alex) {
   this.alex = alex;   
  }
  public UnidadLexica unidadBool() {
	  return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(),ClaseLexica.BOOL);
  } 
  public UnidadLexica unidadInt() {
     return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(),ClaseLexica.INT); 
  } 
  public UnidadLexica unidadReal() {
     return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(),ClaseLexica.REAL); 
  } 
  public UnidadLexica unidadString() {
     return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(),ClaseLexica.STRING); 
  } 
  public UnidadLexica unidadAnd() {
     return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(),ClaseLexica.AND); 
  } 
  public UnidadLexica unidadOr() {
     return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(),ClaseLexica.OR); 
  } 
  public UnidadLexica unidadNot() {
     return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(),ClaseLexica.NOT); 
  } 
  public UnidadLexica unidadTrue() {
     return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(),ClaseLexica.TRUE); 
  } 
  public UnidadLexica unidadFalse() {
     return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(),ClaseLexica.FALSE); 
  } 
  public UnidadLexica unidadNull() {
     return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(),ClaseLexica.NULL); 
  } 
  public UnidadLexica unidadProc() {
     return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(),ClaseLexica.PROC); 
  } 
  public UnidadLexica unidadIf() {
     return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(),ClaseLexica.IF); 
  }
  public UnidadLexica unidadElse() {
     return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(),ClaseLexica.ELSE); 
  }
  public UnidadLexica unidadWhile() {
     return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(),ClaseLexica.WHILE); 
  }
  public UnidadLexica unidadStruct() {
     return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(),ClaseLexica.STRUCT); 
  }
  public UnidadLexica unidadNew() {
     return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(),ClaseLexica.NEW); 
  }
  public UnidadLexica unidadDelete() {
     return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(),ClaseLexica.DELETE); 
  }
  public UnidadLexica unidadRead() {
     return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(),ClaseLexica.READ); 
  }
  public UnidadLexica unidadWrite() {
     return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(),ClaseLexica.WRITE); 
  }
  public UnidadLexica unidadNL() {
     return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(),ClaseLexica.NL); 
  }
  public UnidadLexica unidadType() {
     return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(),ClaseLexica.TYPE); 
  }
  public UnidadLexica unidadCall() {
     return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(),ClaseLexica.CALL); 
  }
  public UnidadLexica unidadLitEnt() {
     return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(),ClaseLexica.LITENT,alex.lexema()); 
  }
  public UnidadLexica unidadLitReal() {
     return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(),ClaseLexica.LITREAL,alex.lexema()); 
  }
  public UnidadLexica unidadLitCadena() {
     return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(),ClaseLexica.LITCADENA,alex.lexema()); 
  }
  public UnidadLexica unidadIden() {
     return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(),ClaseLexica.IDEN,alex.lexema()); 
  }
  public UnidadLexica unidadSuma() {
     return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(),ClaseLexica.SUMA); 
  } 
  public UnidadLexica unidadResta() {
     return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(),ClaseLexica.RESTA); 
  } 
  public UnidadLexica unidadMul() {
     return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(),ClaseLexica.MUL); 
  } 
  public UnidadLexica unidadDiv() {
     return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(),ClaseLexica.DIV); 
  } 
  public UnidadLexica unidadMod() {
     return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(),ClaseLexica.MOD); 
  } 
  public UnidadLexica unidadMenor() {
     return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(),ClaseLexica.MENOR); 
  } 
  public UnidadLexica unidadMayor() {
     return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(),ClaseLexica.MAYOR); 
  } 
  public UnidadLexica unidadIgual() {
     return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(),ClaseLexica.IGUAL); 
  }
  public UnidadLexica unidadDistinto() {
     return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(),ClaseLexica.DISTINTO); 
  }
  public UnidadLexica unidadMenorIgual() {
     return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(),ClaseLexica.MENORIG); 
  }
  public UnidadLexica unidadMayorIgual() {
     return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(),ClaseLexica.MAYORIG); 
  }
  public UnidadLexica unidadAsig() {
     return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(),ClaseLexica.ASIG); 
  } 
  public UnidadLexica unidadFinalAsig() {
     return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(),ClaseLexica.FINASIG); 
  } 
  public UnidadLexica unidadParenApert() {
     return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(),ClaseLexica.PAPERT); 
  } 
  public UnidadLexica unidadParenCierre() {
     return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(),ClaseLexica.PCIERRE); 
  }
  public UnidadLexica unidadLlaveApert() {
     return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(),ClaseLexica.LLAPERT); 
  }
  public UnidadLexica unidadLlaveCierre() {
     return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(),ClaseLexica.LLCIERRE); 
  }
  public UnidadLexica unidadPuntoComa() {
     return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(),ClaseLexica.PCOMA); 
  }
  public UnidadLexica unidadComa() {
     return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(),ClaseLexica.COMA); 
  }
  public UnidadLexica unidadPunto() {
     return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(),ClaseLexica.PUNTO); 
  }
  public UnidadLexica unidadArroba() {
     return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(),ClaseLexica.ARROBA); 
  }
  public UnidadLexica unidadIndireccion() {
     return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(),ClaseLexica.INDIR); 
  }
  public UnidadLexica unidadParamRef() {
     return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(),ClaseLexica.PARAMREF); 
  }
  public UnidadLexica unidadCorcheteApert() {
     return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(),ClaseLexica.CAPERT); 
  }
  public UnidadLexica unidadCorcheteCierre() {
     return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(),ClaseLexica.CCIERRE); 
  }  
  public UnidadLexica unidadEof() {
     return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(),ClaseLexica.EOF); 
  }
  public void error()  {
      throw new ECaracterInesperado("***"+alex.fila()+","+alex.columna()+": Caracter inexperado: "+alex.lexema());
  }
}
