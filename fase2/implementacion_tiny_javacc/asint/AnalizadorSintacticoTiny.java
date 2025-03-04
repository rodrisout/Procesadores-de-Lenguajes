/* AnalizadorSintacticoTiny.java */
/* Generated By:JavaCC: Do not edit this line. AnalizadorSintacticoTiny.java */
package asint;

public class AnalizadorSintacticoTiny implements AnalizadorSintacticoTinyConstants {
    protected void newToken(Token t) {}

  final public void analiza() throws ParseException {
    trace_call("analiza");
    try {

      programa();
      jj_consume_token(0);
    } finally {
      trace_return("analiza");
    }
}

  final public void programa() throws ParseException {
    trace_call("programa");
    try {

      bloque();
    } finally {
      trace_return("programa");
    }
}

  final public void bloque() throws ParseException {
    trace_call("bloque");
    try {

      jj_consume_token(llaveApert);
      seccion_declaraciones_opt();
      seccion_instrucciones_opt();
      jj_consume_token(llaveCierre);
    } finally {
      trace_return("bloque");
    }
}

  final public void seccion_declaraciones_opt() throws ParseException {
    trace_call("seccion_declaraciones_opt");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case bool:
      case INT:
      case real:
      case string:
      case proc:
      case struct:
      case type:
      case identificador:
      case indireccion:{
        seccion_declaraciones();
        jj_consume_token(finalAsig);
        break;
        }
      default:
        jj_la1[0] = jj_gen;

      }
    } finally {
      trace_return("seccion_declaraciones_opt");
    }
}

  final public void seccion_declaraciones() throws ParseException {
    trace_call("seccion_declaraciones");
    try {

      declaracion();
      resto_sd();
    } finally {
      trace_return("seccion_declaraciones");
    }
}

  final public void resto_sd() throws ParseException {
    trace_call("resto_sd");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case puntoComa:{
        jj_consume_token(puntoComa);
        declaracion();
        resto_sd();
        break;
        }
      default:
        jj_la1[1] = jj_gen;

      }
    } finally {
      trace_return("resto_sd");
    }
}

  final public void declaracion() throws ParseException {
    trace_call("declaracion");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case bool:
      case INT:
      case real:
      case string:
      case struct:
      case identificador:
      case indireccion:{
        tipo_nombre();
        break;
        }
      case type:{
        jj_consume_token(type);
        tipo_nombre();
        break;
        }
      case proc:{
        jj_consume_token(proc);
        jj_consume_token(identificador);
        parametros_formales();
        bloque();
        break;
        }
      default:
        jj_la1[2] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("declaracion");
    }
}

  final public void parametros_formales() throws ParseException {
    trace_call("parametros_formales");
    try {

      jj_consume_token(parenApert);
      lista_parametros_opt();
      jj_consume_token(parenCierre);
    } finally {
      trace_return("parametros_formales");
    }
}

  final public void lista_parametros_opt() throws ParseException {
    trace_call("lista_parametros_opt");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case bool:
      case INT:
      case real:
      case string:
      case struct:
      case identificador:
      case indireccion:{
        lista_parametros();
        break;
        }
      default:
        jj_la1[3] = jj_gen;

      }
    } finally {
      trace_return("lista_parametros_opt");
    }
}

  final public void lista_parametros() throws ParseException {
    trace_call("lista_parametros");
    try {

      parametro();
      resto_lp();
    } finally {
      trace_return("lista_parametros");
    }
}

  final public void resto_lp() throws ParseException {
    trace_call("resto_lp");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case coma:{
        jj_consume_token(coma);
        parametro();
        resto_lp();
        break;
        }
      default:
        jj_la1[4] = jj_gen;

      }
    } finally {
      trace_return("resto_lp");
    }
}

  final public void parametro() throws ParseException {
    trace_call("parametro");
    try {

      tipo();
      ref_opt();
      jj_consume_token(identificador);
    } finally {
      trace_return("parametro");
    }
}

  final public void ref_opt() throws ParseException {
    trace_call("ref_opt");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case paramRef:{
        jj_consume_token(paramRef);
        break;
        }
      default:
        jj_la1[5] = jj_gen;

      }
    } finally {
      trace_return("ref_opt");
    }
}

  final public void tipo_nombre() throws ParseException {
    trace_call("tipo_nombre");
    try {

      tipo();
      jj_consume_token(identificador);
    } finally {
      trace_return("tipo_nombre");
    }
}

  final public void tipo() throws ParseException {
    trace_call("tipo");
    try {

      tipo0();
    } finally {
      trace_return("tipo");
    }
}

  final public void tipo0() throws ParseException {
    trace_call("tipo0");
    try {

      tipo1();
      resto_tipo0();
    } finally {
      trace_return("tipo0");
    }
}

  final public void resto_tipo0() throws ParseException {
    trace_call("resto_tipo0");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case corcheteApert:{
        jj_consume_token(corcheteApert);
        jj_consume_token(literalEntero);
        jj_consume_token(corcheteCierre);
        resto_tipo0();
        break;
        }
      default:
        jj_la1[6] = jj_gen;

      }
    } finally {
      trace_return("resto_tipo0");
    }
}

  final public void tipo1() throws ParseException {
    trace_call("tipo1");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case indireccion:{
        jj_consume_token(indireccion);
        tipo1();
        break;
        }
      case bool:
      case INT:
      case real:
      case string:
      case struct:
      case identificador:{
        tipo_base();
        break;
        }
      default:
        jj_la1[7] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("tipo1");
    }
}

  final public void tipo_base() throws ParseException {
    trace_call("tipo_base");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case struct:{
        jj_consume_token(struct);
        jj_consume_token(llaveApert);
        lista_campos();
        jj_consume_token(llaveCierre);
        break;
        }
      case INT:{
        jj_consume_token(INT);
        break;
        }
      case real:{
        jj_consume_token(real);
        break;
        }
      case bool:{
        jj_consume_token(bool);
        break;
        }
      case string:{
        jj_consume_token(string);
        break;
        }
      case identificador:{
        jj_consume_token(identificador);
        break;
        }
      default:
        jj_la1[8] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("tipo_base");
    }
}

  final public void lista_campos() throws ParseException {
    trace_call("lista_campos");
    try {

      tipo_nombre();
      resto_lc();
    } finally {
      trace_return("lista_campos");
    }
}

  final public void resto_lc() throws ParseException {
    trace_call("resto_lc");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case coma:{
        jj_consume_token(coma);
        tipo_nombre();
        resto_lc();
        break;
        }
      default:
        jj_la1[9] = jj_gen;

      }
    } finally {
      trace_return("resto_lc");
    }
}

  final public void seccion_instrucciones_opt() throws ParseException {
    trace_call("seccion_instrucciones_opt");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case IF:
      case WHILE:
      case NEW:
      case delete:
      case read:
      case write:
      case nl:
      case call:
      case llaveApert:
      case arroba:{
        seccion_instrucciones();
        break;
        }
      default:
        jj_la1[10] = jj_gen;

      }
    } finally {
      trace_return("seccion_instrucciones_opt");
    }
}

  final public void seccion_instrucciones() throws ParseException {
    trace_call("seccion_instrucciones");
    try {

      lista_instrucciones();
    } finally {
      trace_return("seccion_instrucciones");
    }
}

  final public void lista_instrucciones() throws ParseException {
    trace_call("lista_instrucciones");
    try {

      instruccion();
      resto_li();
    } finally {
      trace_return("lista_instrucciones");
    }
}

  final public void resto_li() throws ParseException {
    trace_call("resto_li");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case puntoComa:{
        jj_consume_token(puntoComa);
        instruccion();
        resto_li();
        break;
        }
      default:
        jj_la1[11] = jj_gen;

      }
    } finally {
      trace_return("resto_li");
    }
}

  final public void instruccion() throws ParseException {
    trace_call("instruccion");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case arroba:{
        jj_consume_token(arroba);
        expresion();
        break;
        }
      case IF:{
        if_ins();
        resto_ii();
        break;
        }
      case WHILE:{
        jj_consume_token(WHILE);
        exp_bloq();
        break;
        }
      case read:{
        jj_consume_token(read);
        expresion();
        break;
        }
      case write:{
        jj_consume_token(write);
        expresion();
        break;
        }
      case nl:{
        jj_consume_token(nl);
        break;
        }
      case NEW:{
        jj_consume_token(NEW);
        expresion();
        break;
        }
      case delete:{
        jj_consume_token(delete);
        expresion();
        break;
        }
      case call:{
        jj_consume_token(call);
        jj_consume_token(identificador);
        parametros_reales();
        break;
        }
      case llaveApert:{
        bloque();
        break;
        }
      default:
        jj_la1[12] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("instruccion");
    }
}

  final public void resto_ii() throws ParseException {
    trace_call("resto_ii");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case ELSE:{
        else_ins();
        break;
        }
      default:
        jj_la1[13] = jj_gen;

      }
    } finally {
      trace_return("resto_ii");
    }
}

  final public void if_ins() throws ParseException {
    trace_call("if_ins");
    try {

      jj_consume_token(IF);
      exp_bloq();
    } finally {
      trace_return("if_ins");
    }
}

  final public void else_ins() throws ParseException {
    trace_call("else_ins");
    try {

      jj_consume_token(ELSE);
      bloque();
    } finally {
      trace_return("else_ins");
    }
}

  final public void exp_bloq() throws ParseException {
    trace_call("exp_bloq");
    try {

      expresion();
      bloque();
    } finally {
      trace_return("exp_bloq");
    }
}

  final public void parametros_reales() throws ParseException {
    trace_call("parametros_reales");
    try {

      jj_consume_token(parenApert);
      lista_expresiones_opt();
      jj_consume_token(parenCierre);
    } finally {
      trace_return("parametros_reales");
    }
}

  final public void lista_expresiones_opt() throws ParseException {
    trace_call("lista_expresiones_opt");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case not:
      case TRUE:
      case FALSE:
      case NULL:
      case literalEntero:
      case literalReal:
      case identificador:
      case literalCadena:
      case resta:
      case parenApert:{
        lista_expresiones();
        break;
        }
      default:
        jj_la1[14] = jj_gen;

      }
    } finally {
      trace_return("lista_expresiones_opt");
    }
}

  final public void lista_expresiones() throws ParseException {
    trace_call("lista_expresiones");
    try {

      expresion();
      resto_le();
    } finally {
      trace_return("lista_expresiones");
    }
}

  final public void resto_le() throws ParseException {
    trace_call("resto_le");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case coma:{
        jj_consume_token(coma);
        expresion();
        resto_le();
        break;
        }
      default:
        jj_la1[15] = jj_gen;

      }
    } finally {
      trace_return("resto_le");
    }
}

  final public void expresion() throws ParseException {
    trace_call("expresion");
    try {

      E0();
    } finally {
      trace_return("expresion");
    }
}

  final public void E0() throws ParseException {
    trace_call("E0");
    try {

      E1();
      resto_E0();
    } finally {
      trace_return("E0");
    }
}

  final public void resto_E0() throws ParseException {
    trace_call("resto_E0");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case asig:{
        jj_consume_token(asig);
        E0();
        break;
        }
      default:
        jj_la1[16] = jj_gen;

      }
    } finally {
      trace_return("resto_E0");
    }
}

  final public void E1() throws ParseException {
    trace_call("E1");
    try {

      E2();
      resto_E1();
    } finally {
      trace_return("E1");
    }
}

  final public void resto_E1() throws ParseException {
    trace_call("resto_E1");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case menor:
      case mayor:
      case igual:
      case distinto:
      case menorIgual:
      case mayorIgual:{
        op_relacional();
        E2();
        resto_E1();
        break;
        }
      default:
        jj_la1[17] = jj_gen;

      }
    } finally {
      trace_return("resto_E1");
    }
}

  final public void E2() throws ParseException {
    trace_call("E2");
    try {

      E3();
      resto_E2_F();
      resto_E2_R();
    } finally {
      trace_return("E2");
    }
}

  final public void resto_E2_R() throws ParseException {
    trace_call("resto_E2_R");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case suma:{
        jj_consume_token(suma);
        E3();
        resto_E2_R();
        break;
        }
      default:
        jj_la1[18] = jj_gen;

      }
    } finally {
      trace_return("resto_E2_R");
    }
}

  final public void resto_E2_F() throws ParseException {
    trace_call("resto_E2_F");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case resta:{
        jj_consume_token(resta);
        E3();
        break;
        }
      default:
        jj_la1[19] = jj_gen;

      }
    } finally {
      trace_return("resto_E2_F");
    }
}

  final public void E3() throws ParseException {
    trace_call("E3");
    try {

      E4();
      resto_E3();
    } finally {
      trace_return("E3");
    }
}

  final public void resto_E3() throws ParseException {
    trace_call("resto_E3");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case and:{
        jj_consume_token(and);
        E3();
        break;
        }
      case or:{
        jj_consume_token(or);
        E4();
        break;
        }
      default:
        jj_la1[20] = jj_gen;

      }
    } finally {
      trace_return("resto_E3");
    }
}

  final public void E4() throws ParseException {
    trace_call("E4");
    try {

      E5();
      resto_E4();
    } finally {
      trace_return("E4");
    }
}

  final public void resto_E4() throws ParseException {
    trace_call("resto_E4");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case mul:
      case div:
      case mod:{
        op_mult();
        E5();
        resto_E4();
        break;
        }
      default:
        jj_la1[21] = jj_gen;

      }
    } finally {
      trace_return("resto_E4");
    }
}

  final public void E5() throws ParseException {
    trace_call("E5");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case resta:{
        jj_consume_token(resta);
        E5();
        break;
        }
      case not:{
        jj_consume_token(not);
        E5();
        break;
        }
      case TRUE:
      case FALSE:
      case NULL:
      case literalEntero:
      case literalReal:
      case identificador:
      case literalCadena:
      case parenApert:{
        E6();
        break;
        }
      default:
        jj_la1[22] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("E5");
    }
}

  final public void E6() throws ParseException {
    trace_call("E6");
    try {

      E7();
      resto_E6();
    } finally {
      trace_return("E6");
    }
}

  final public void resto_E6() throws ParseException {
    trace_call("resto_E6");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case punto:
      case indireccion:
      case corcheteApert:{
        op_dirs();
        resto_E6();
        break;
        }
      default:
        jj_la1[23] = jj_gen;

      }
    } finally {
      trace_return("resto_E6");
    }
}

  final public void E7() throws ParseException {
    trace_call("E7");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case TRUE:
      case FALSE:
      case NULL:
      case literalEntero:
      case literalReal:
      case identificador:
      case literalCadena:{
        expresion_basica();
        break;
        }
      case parenApert:{
        jj_consume_token(parenApert);
        E0();
        jj_consume_token(parenCierre);
        break;
        }
      default:
        jj_la1[24] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("E7");
    }
}

  final public void expresion_basica() throws ParseException {
    trace_call("expresion_basica");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case literalEntero:{
        jj_consume_token(literalEntero);
        break;
        }
      case literalReal:{
        jj_consume_token(literalReal);
        break;
        }
      case TRUE:{
        jj_consume_token(TRUE);
        break;
        }
      case FALSE:{
        jj_consume_token(FALSE);
        break;
        }
      case literalCadena:{
        jj_consume_token(literalCadena);
        break;
        }
      case identificador:{
        jj_consume_token(identificador);
        break;
        }
      case NULL:{
        jj_consume_token(NULL);
        break;
        }
      default:
        jj_la1[25] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("expresion_basica");
    }
}

  final public void op_relacional() throws ParseException {
    trace_call("op_relacional");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case menor:{
        jj_consume_token(menor);
        break;
        }
      case menorIgual:{
        jj_consume_token(menorIgual);
        break;
        }
      case mayor:{
        jj_consume_token(mayor);
        break;
        }
      case mayorIgual:{
        jj_consume_token(mayorIgual);
        break;
        }
      case igual:{
        jj_consume_token(igual);
        break;
        }
      case distinto:{
        jj_consume_token(distinto);
        break;
        }
      default:
        jj_la1[26] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("op_relacional");
    }
}

  final public void op_mult() throws ParseException {
    trace_call("op_mult");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case mul:{
        jj_consume_token(mul);
        break;
        }
      case div:{
        jj_consume_token(div);
        break;
        }
      case mod:{
        jj_consume_token(mod);
        break;
        }
      default:
        jj_la1[27] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("op_mult");
    }
}

  final public void op_dirs() throws ParseException {
    trace_call("op_dirs");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case corcheteApert:{
        jj_consume_token(corcheteApert);
        expresion();
        jj_consume_token(corcheteCierre);
        break;
        }
      case punto:{
        jj_consume_token(punto);
        jj_consume_token(identificador);
        break;
        }
      case indireccion:{
        jj_consume_token(indireccion);
        break;
        }
      default:
        jj_la1[28] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("op_dirs");
    }
}

  /** Generated Token Manager. */
  public AnalizadorSintacticoTinyTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[29];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
	   jj_la1_init_0();
	   jj_la1_init_1();
	}
	private static void jj_la1_init_0() {
	   jj_la1_0 = new int[] {0x20881e00,0x0,0x20881e00,0x801e00,0x0,0x0,0x0,0x801e00,0x801e00,0x0,0x5f500000,0x0,0x5f500000,0x200000,0x80078000,0x0,0x0,0x0,0x0,0x0,0x6000,0x0,0x80078000,0x0,0x80070000,0x80070000,0x0,0x0,0x0,};
	}
	private static void jj_la1_init_1() {
	   jj_la1_1 = new int[] {0x1000002,0x100000,0x1000002,0x1000002,0x200000,0x2000000,0x4000000,0x1000002,0x2,0x200000,0x840000,0x100000,0x840000,0x0,0x10017,0x200000,0x4000,0x3f00,0x8,0x10,0x0,0xe0,0x10017,0x5400000,0x10007,0x7,0x3f00,0xe0,0x5400000,};
	}

  {
      enable_tracing();
  }
  /** Constructor with InputStream. */
  public AnalizadorSintacticoTiny(java.io.InputStream stream) {
	  this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public AnalizadorSintacticoTiny(java.io.InputStream stream, String encoding) {
	 try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
	 token_source = new AnalizadorSintacticoTinyTokenManager(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 29; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
	  ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
	 try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
	 token_source.ReInit(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 29; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public AnalizadorSintacticoTiny(java.io.Reader stream) {
	 jj_input_stream = new SimpleCharStream(stream, 1, 1);
	 token_source = new AnalizadorSintacticoTinyTokenManager(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 29; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
	if (jj_input_stream == null) {
	   jj_input_stream = new SimpleCharStream(stream, 1, 1);
	} else {
	   jj_input_stream.ReInit(stream, 1, 1);
	}
	if (token_source == null) {
 token_source = new AnalizadorSintacticoTinyTokenManager(jj_input_stream);
	}

	 token_source.ReInit(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 29; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public AnalizadorSintacticoTiny(AnalizadorSintacticoTinyTokenManager tm) {
	 token_source = tm;
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 29; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(AnalizadorSintacticoTinyTokenManager tm) {
	 token_source = tm;
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 29; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
	 Token oldToken;
	 if ((oldToken = token).next != null) token = token.next;
	 else token = token.next = token_source.getNextToken();
	 jj_ntk = -1;
	 if (token.kind == kind) {
	   jj_gen++;
	   trace_token(token, "");
	   return token;
	 }
	 token = oldToken;
	 jj_kind = kind;
	 throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
	 if (token.next != null) token = token.next;
	 else token = token.next = token_source.getNextToken();
	 jj_ntk = -1;
	 jj_gen++;
	   trace_token(token, " (in getNextToken)");
	 return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
	 Token t = token;
	 for (int i = 0; i < index; i++) {
	   if (t.next != null) t = t.next;
	   else t = t.next = token_source.getNextToken();
	 }
	 return t;
  }

  private int jj_ntk_f() {
	 if ((jj_nt=token.next) == null)
	   return (jj_ntk = (token.next=token_source.getNextToken()).kind);
	 else
	   return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
	 jj_expentries.clear();
	 boolean[] la1tokens = new boolean[60];
	 if (jj_kind >= 0) {
	   la1tokens[jj_kind] = true;
	   jj_kind = -1;
	 }
	 for (int i = 0; i < 29; i++) {
	   if (jj_la1[i] == jj_gen) {
		 for (int j = 0; j < 32; j++) {
		   if ((jj_la1_0[i] & (1<<j)) != 0) {
			 la1tokens[j] = true;
		   }
		   if ((jj_la1_1[i] & (1<<j)) != 0) {
			 la1tokens[32+j] = true;
		   }
		 }
	   }
	 }
	 for (int i = 0; i < 60; i++) {
	   if (la1tokens[i]) {
		 jj_expentry = new int[1];
		 jj_expentry[0] = i;
		 jj_expentries.add(jj_expentry);
	   }
	 }
	 int[][] exptokseq = new int[jj_expentries.size()][];
	 for (int i = 0; i < jj_expentries.size(); i++) {
	   exptokseq[i] = jj_expentries.get(i);
	 }
	 return new ParseException(token, exptokseq, tokenImage);
  }

  private boolean trace_enabled;

/** Trace enabled. */
  final public boolean trace_enabled() {
	 return trace_enabled;
  }

  private int trace_indent = 0;
/** Enable tracing. */
  final public void enable_tracing() {
	 trace_enabled = true;
  }

/** Disable tracing. */
  final public void disable_tracing() {
	 trace_enabled = false;
  }

  protected void trace_call(String s) {
	 if (trace_enabled) {
	   for (int i = 0; i < trace_indent; i++) { System.out.print(" "); }
	   System.out.println("Call:	" + s);
	 }
	 trace_indent = trace_indent + 2;
  }

  protected void trace_return(String s) {
	 trace_indent = trace_indent - 2;
	 if (trace_enabled) {
	   for (int i = 0; i < trace_indent; i++) { System.out.print(" "); }
	   System.out.println("Return: " + s);
	 }
  }

  protected void trace_token(Token t, String where) {
	 if (trace_enabled) {
	   for (int i = 0; i < trace_indent; i++) { System.out.print(" "); }
	   System.out.print("Consumed token: <" + tokenImage[t.kind]);
	   if (t.kind != 0 && !tokenImage[t.kind].equals("\"" + t.image + "\"")) {
		 System.out.print(": \"" + TokenMgrError.addEscapes(t.image) + "\"");
	   }
	   System.out.println(" at line " + t.beginLine + " column " + t.beginColumn + ">" + where);
	 }
  }

  protected void trace_scan(Token t1, int t2) {
	 if (trace_enabled) {
	   for (int i = 0; i < trace_indent; i++) { System.out.print(" "); }
	   System.out.print("Visited token: <" + tokenImage[t1.kind]);
	   if (t1.kind != 0 && !tokenImage[t1.kind].equals("\"" + t1.image + "\"")) {
		 System.out.print(": \"" + TokenMgrError.addEscapes(t1.image) + "\"");
	   }
	   System.out.println(" at line " + t1.beginLine + " column " + t1.beginColumn + ">; Expected token: <" + tokenImage[t2] + ">");
	 }
  }

}
