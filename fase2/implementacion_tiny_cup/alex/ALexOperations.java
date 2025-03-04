package alex;

import asint.ClaseLexica;
import errors.GestionErroresTiny;

public class ALexOperations {
    
  private AnalizadorLexicoTiny alex;
  private GestionErroresTiny errores;
  public ALexOperations(AnalizadorLexicoTiny alex) {
   this.alex = alex;   
   errores = new GestionErroresTiny();
  }
  public UnidadLexica unidadBool() {
	  return new UnidadLexica(alex.fila(), alex.columna(),ClaseLexica.BOOL,"<bool>");
  } 
  public UnidadLexica unidadInt() {
     return new UnidadLexica(alex.fila(), alex.columna(),ClaseLexica.INT, "<int>"); 
  } 
  public UnidadLexica unidadReal() { 
	   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.REAL, "<real>"); 
	} 
	public UnidadLexica unidadString() {
	   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.STRING, "<string>"); 
	} 
	public UnidadLexica unidadAnd() {
	   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.AND, "<and>"); 
	} 
	public UnidadLexica unidadOr() {
	   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.OR, "<or>"); 
	} 
	public UnidadLexica unidadNot() {
	   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.NOT, "<not>"); 
	} 
	public UnidadLexica unidadTrue() {
	   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.TRUE, "<true>"); 
	} 
	public UnidadLexica unidadFalse() {
	   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.FALSE, "<false>"); 
	} 
	public UnidadLexica unidadNull() {
	   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.NULL, "<null>"); 
	} 
	public UnidadLexica unidadProc() {
	   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.PROC, "<proc>"); 
	} 
	public UnidadLexica unidadIf() {
	   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.IF, "<if>"); 
	}
	public UnidadLexica unidadElse() {
	   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.ELSE, "<else>"); 
	}
	public UnidadLexica unidadWhile() {
	   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.WHILE, "<while>"); 
	}
	public UnidadLexica unidadStruct() {
	   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.STRUCT, "<struct>"); 
	}
	public UnidadLexica unidadNew() {
	   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.NEW, "<new>"); 
	}
	public UnidadLexica unidadDelete() {
	   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.DELETE, "<delete>"); 
	}
	public UnidadLexica unidadRead() {
	   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.READ, "<read>"); 
	}
	public UnidadLexica unidadWrite() {
	   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.WRITE, "<write>"); 
	}
	public UnidadLexica unidadNL() {
	   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.NL, "<nl>"); 
	}
	public UnidadLexica unidadType() {
	   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.TYPE, "<type>"); 
	}
	public UnidadLexica unidadCall() {
	   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.CALL, "<call>"); 
	}
  public UnidadLexica unidadLitEnt() {
     return new UnidadLexica(alex.fila(), alex.columna(),ClaseLexica.LITENT,alex.lexema()); 
  }
  public UnidadLexica unidadLitReal() {
     return new UnidadLexica(alex.fila(), alex.columna(),ClaseLexica.LITREAL,alex.lexema()); 
  }
  public UnidadLexica unidadLitCadena() {
     return new UnidadLexica(alex.fila(), alex.columna(),ClaseLexica.LITCADENA,alex.lexema()); 
  }
  public UnidadLexica unidadIden() {
     return new UnidadLexica(alex.fila(), alex.columna(),ClaseLexica.IDEN,alex.lexema()); 
  }
  public UnidadLexica unidadSuma() {
	   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.SUMA, "+"); 
	} 
	public UnidadLexica unidadResta() {
	   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.RESTA, "-"); 
	} 
	public UnidadLexica unidadMul() {
	   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MUL, "*"); 
	} 
	public UnidadLexica unidadDiv() {
	   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.DIV, "/"); 
	} 
	public UnidadLexica unidadMod() {
	   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MOD, "%"); 
	} 
	public UnidadLexica unidadMenor() {
	   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MENOR, "<"); 
	} 
	public UnidadLexica unidadMayor() {
	   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MAYOR, ">"); 
	} 
	public UnidadLexica unidadIgual() {
	   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.IGUAL, "=="); 
	}
	public UnidadLexica unidadDistinto() {
	   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.DISTINTO, "!="); 
	}
	public UnidadLexica unidadMenorIgual() {
	   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MENORIG, "<="); 
	}
	public UnidadLexica unidadMayorIgual() {
	   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MAYORIG, ">="); 
	}
	public UnidadLexica unidadAsig() {
	   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.ASIG, "="); 
	} 
	public UnidadLexica unidadFinalAsig() {
	   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.FINALASIG, "&&"); 
	} 
	public UnidadLexica unidadParenApert() {
	   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.PAPERT, "("); 
	} 
	public UnidadLexica unidadParenCierre() {
	   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.PCIERRE, ")"); 
	}
  public UnidadLexica unidadLlaveApert() {
	     return new UnidadLexica(alex.fila(), alex.columna(),ClaseLexica.LLAPERT, "{"); 
	  }
	  public UnidadLexica unidadLlaveCierre() {
	     return new UnidadLexica(alex.fila(), alex.columna(),ClaseLexica.LLCIERRE, "}"); 
	  }
	  public UnidadLexica unidadPuntoComa() {
	     return new UnidadLexica(alex.fila(), alex.columna(),ClaseLexica.PCOMA, ";"); 
	  }
	  public UnidadLexica unidadComa() {
	     return new UnidadLexica(alex.fila(), alex.columna(),ClaseLexica.COMA, ","); 
	  }
	  public UnidadLexica unidadPunto() {
	     return new UnidadLexica(alex.fila(), alex.columna(),ClaseLexica.PUNTO, "."); 
	  }
	  public UnidadLexica unidadArroba() {
	     return new UnidadLexica(alex.fila(), alex.columna(),ClaseLexica.ARROBA, "@"); 
	  }
	  public UnidadLexica unidadIndireccion() {
	     return new UnidadLexica(alex.fila(), alex.columna(),ClaseLexica.INDIR, "^"); 
	  }
	  public UnidadLexica unidadParamRef() {
	     return new UnidadLexica(alex.fila(), alex.columna(),ClaseLexica.PARAMREF, "&"); 
	  }
	  public UnidadLexica unidadCorcheteApert() {
	     return new UnidadLexica(alex.fila(), alex.columna(),ClaseLexica.CAPERT, "["); 
	  }
	  public UnidadLexica unidadCorcheteCierre() {
	     return new UnidadLexica(alex.fila(), alex.columna(),ClaseLexica.CCIERRE, "]"); 
	  }  
	public UnidadLexica unidadEof() {
	   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.EOF, "<EOF>"); 
	}
	public void error() {
		errores.errorLexico(alex.fila(), alex.columna(), alex.lexema());
		
	}

}
