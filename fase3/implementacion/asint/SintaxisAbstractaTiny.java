package asint;

public class SintaxisAbstractaTiny {

    public static abstract class Nodo  {
       public Nodo() {
		   fila=col=-1;
       }   
	   private int fila;
	   private int col;
	   public Nodo ponFila(int fila) {
		    this.fila = fila;
            return this;			
	   }
	   public Nodo ponCol(int col) {
		    this.col = col;
            return this;			
	   }
	   public int leeFila() {
		  return fila; 
	   }
	   public int leeCol() {
		  return col; 
	   }
	   public abstract void imprime();
    }

    public static class Prog extends Nodo {
        private Bloq bloq;
        public Prog(Bloq bloq) {
            super();
            this.bloq = bloq;
        }
        public String toString() {
            return "prog(+"+bloq+")";
        }
        public void imprime() {
            this.bloq.imprime();
        }
    }
    
    public static class Bloq extends Nodo {
    	private SecDecs decs;
    	private SecIs ins;
        public Bloq(SecDecs decs, SecIs ins) {
 		   super();
 		   this.decs = decs;
 		   this.ins = ins;
        }
        public String toString() {
            return "bloq("+decs+","+ins+")";
        } 
        //RECURSIVO
        public SecDecs SecDecs() {return this.decs;}
        public SecIs SecIs() {return this.ins;}
        //INTERPRETE
        public void imprime() {
            println("{");
			this.decs.imprime();
            this.ins.imprime();
            println("}");
		}
        //VISITANTE
        // TODO copiar y pegar esto asi tal cual en todas las clases
        public void procesa(Procesamiento p) {
        	p.procesa(this);
        }
    }
    
    // Declaraciones
    
    public static abstract class SecDecs extends Nodo {
        public SecDecs() {
        	super();
        }
        public LDecs LDecs() {throw new UnsupportedOperationException();}
    }
    
    public static class Si_decs extends SecDecs {
		private LDecs decs;
		public Si_decs(LDecs decs) {
		   super();
		   this.decs = decs;
		}   
		public String toString() {
			return "si_decs("+decs+")";
		} 
		//RECURSIVO
        public LDecs LDecs() {return this.decs;}
        //INTERPRETE
        public void imprime() {
            decs.imprime();
            println("&&");
		}
        //VISITANTE
    }
    
	public static class No_decs extends SecDecs {
	    public No_decs() {
	       super();
	    }   
	    public String toString() {
	    	return "no_decs()";
	    } 
	    //INTERPRETE
        public void imprime() {}
	}
	
	public static abstract class LDecs extends Nodo {
       public LDecs() {
		   super();
       }
       public LDecs LDecs() {throw new UnsupportedOperationException();}
       public Dec Dec() {throw new UnsupportedOperationException();}
    }
	
	public static class Muchas_decs extends LDecs {
       private LDecs decs;
       private Dec dec;
       public Muchas_decs(LDecs decs, Dec dec) {
          super();
          this.dec = dec;
          this.decs = decs;
       }
       public String toString() {
           return "muchas_decs("+decs+","+dec+")";
       } 
       //RECURSIVO
       public LDecs LDecs() {return this.decs;}
       public Dec Dec() {return this.dec;}
       //INTERPRETE
       public void imprime() {
            this.decs.imprime();
            println(";");
            this.dec.imprime();
	    }
    }

    public static class Una_dec extends LDecs {
       private Dec dec;
       public Una_dec(Dec dec) {
          super();
          this.dec = dec;
       }
       public String toString() {
           return "una_dec("+dec+")";
       } 
       //RECURSIVO
       public Dec Dec() {return this.dec;}
       //INTERPRETE
       public void imprime() {
        this.dec.imprime();
		}
       //VISITANTE
       
    }
    
    public static abstract class Dec extends Nodo {
        public Dec() {
 		   super();
        }
        public TipoNom TipoNom() {throw new UnsupportedOperationException();}
        public String ID() {throw new UnsupportedOperationException();}
        public ParamFs ParamFs() {throw new UnsupportedOperationException();}
        public Bloq Bloq() {throw new UnsupportedOperationException();}
     }
    
    public static class Dec_base extends Dec {
        private TipoNom tiponom;
        public Dec_base(TipoNom tiponom) {
        	super();
        	this.tiponom = tiponom;
        }
        public String toString() {
            return "dec_base("+tiponom+")";
        } 
        //RECURSIVO
        public TipoNom TipoNom() {return this.tiponom;}
        //INTERPRETE
        public void imprime() {
            this.tiponom.imprime();
		}
        //VISITANTE
        
    }
    
    public static class Dec_type extends Dec {
        private TipoNom tiponom;
        public Dec_type(TipoNom tiponom) {
        	super();
        	this.tiponom = tiponom;
        }
        public String toString() {
            return "dec_type("+tiponom+")";
        } 
        //RECURSIVO
        public TipoNom TipoNom() {return this.tiponom;}
        //INTERPRETE
        public void imprime() {
            println("type");
            this.tiponom.imprime();
		}
        //VISITANTE
        
    }
    
    public static class Dec_proc extends Dec {
        private String id;
        private ParamFs paramfs;
        private Bloq bloq;
        public Dec_proc(String id, ParamFs paramfs, Bloq bloq) {
        	super();
        	this.id = id;
        	this.paramfs = paramfs;
        	this.bloq = bloq;
        }
        public String toString() {
        	return "dec_proc("+id+","+paramfs+","+bloq+")";
        } 
        //RECURSIVO
        public String ID() {return this.id;}
        public ParamFs ParamFs() {return this.paramfs;}
        public Bloq Bloq() {return this.bloq;}
        //INTERPRETE
        public void imprime() {
            println("proc");
            println(id);
            println("(");
            this.paramfs.imprime();
            println(")");
            this.bloq.imprime();
		}
        //VISITANTE
        
    }
    
    public static abstract class ParamFs extends Nodo {
        public ParamFs() {
        	super();
        }
        public LParamFs LParamFs() {throw new UnsupportedOperationException();}
    }
    
    public static class Si_params_f extends ParamFs {
		private LParamFs paramfs; 
		public Si_params_f(LParamFs paramfs) {
		   super();
		   this.paramfs = paramfs;
		}   
		public String toString() {
			return "si_params_f("+paramfs+")";
		} 
		//RECURSIVO
        public LParamFs LParamFs() {return this.paramfs;}
        //INTERPRETE
        public void imprime() {
            this.paramfs.imprime();
		}
        //VISITANTE
    }
    
	public static class No_params_f extends ParamFs {
	    public No_params_f() {
	       super();
	    }   
	    public String toString() {
	    	return "no_params_f()";
	    } 
	    //INTERPRETE
        public void imprime() {}
	}
    
	public static abstract class LParamFs extends Nodo {
       public LParamFs() {
		   super();
       }
       public LParamFs LParamFs() {throw new UnsupportedOperationException();}
       public ParamF ParamF() {throw new UnsupportedOperationException();}
    }
	
	public static class Muchos_params_f extends LParamFs {
       private LParamFs paramfs;
       private ParamF paramf;
       public Muchos_params_f(LParamFs paramfs, ParamF paramf) {
          super();
          this.paramfs = paramfs;
          this.paramf = paramf;
       }
       public String toString() {
            return "muchos_params_f("+paramfs+","+paramf+")";
       } 
       //RECURSIVO
       public LParamFs LParamFs() {return this.paramfs;}
       public ParamF ParamF() {return this.paramf;}
       //INTERPRETE
       public void imprime() {
        this.paramfs.imprime();
        println(",");
        this.paramf.imprime();
		}
       //VISITANTE
    }

    public static class Un_param_f extends LParamFs {
    	private ParamF paramf;
    	public Un_param_f(ParamF paramf) {
    		super();
    		this.paramf = paramf;
    	}
    	public String toString() {
            return "un_param_f("+paramf+")";
        } 
    	//RECURSIVO
        public ParamF ParamF() {return this.paramf;}
        //INTERPRETE
        public void imprime() {
            this.paramf.imprime();
		}
        //VISITANTE
    }
    
    public static abstract class ParamF extends Nodo {
        public ParamF() {
        	super();
        }
        public Tipo Tipo() {throw new UnsupportedOperationException();}
        public String ID() {throw new UnsupportedOperationException();}
    }
    
    public static class Si_refparam_f extends ParamF {
		private Tipo tipo;
		private String id; 
		public Si_refparam_f(Tipo tipo, String id) {
		   super();
		   this.tipo = tipo;
		   this.id = id;
		}   
		public String toString() {
			return "si_refparam_f("+tipo+","+id+")";
		} 
		//RECURSIVO
        public Tipo Tipo() {return this.tipo;}
        public String ID() {return this.id;}
        //INTERPRETE
        public void imprime() {
            this.tipo.imprime();
            println("&");
            println(id);
		}
        //VISITANTE
    }
    
    public static class No_refparam_f extends ParamF {
		private Tipo tipo;
		private String id; 
		public No_refparam_f(Tipo tipo, String id) {
		   super();
		   this.tipo = tipo;
		   this.id = id;
		}   
		public String toString() {
			return "no_refparam_f("+tipo+","+id+")";
		} 
		//RECURSIVO
        public Tipo Tipo() {return this.tipo;}
        public String ID() {return this.id;}
        //INTERPRETE
        public void imprime() {
            this.tipo.imprime();
            println(id);
		}
        //VISITANTE
    }
    
    // Tipos
    
    public static class TipoNom extends Nodo {
    	private Tipo tipo;
    	private String id;
        public TipoNom(Tipo tipo, String id) {
 		   super();
 		   this.tipo = tipo;
 		   this.id = id;
        }   
        public String toString() {
	         return "tipo_nombre("+tipo+","+id+")";
	    }
        //RECURSIVO
        public Tipo Tipo() {return this.tipo;}
        public String ID() {return this.id;}
        //INTERPRETE
        public void imprime() {
            this.tipo.imprime();
            println(id);
		}
        //VISITANTE
    }
    
    public static abstract class Tipo extends Nodo {
        public Tipo() {
        	super();
        }
        public Tipo Tipo() {throw new UnsupportedOperationException();}
        public String litEntero() {throw new UnsupportedOperationException();}
        public LCampos LCampos() {throw new UnsupportedOperationException();}
        public String ID() {throw new UnsupportedOperationException();}
    }
    
    public static class Tipo_array extends Tipo {
    	private Tipo tipo;
    	private String litEntero;
        public Tipo_array(Tipo tipo, String litEntero) {
 		   	super();
 		   	this.tipo = tipo;
 		   	this.litEntero = litEntero;
        }   
        public String toString() {
        	return "tipo_array("+tipo+","+litEntero+")";
	    } 
        //RECURSIVO
        public Tipo Tipo() {return this.tipo;}
        public String litEntero() {return this.litEntero;}
        //INTERPRETE
        public void imprime() {
            this.tipo.imprime();
            println("[");
            println(litEntero);
            println("]");
		}
        //VISITANTE
    }
    
    public static class Tipo_indir extends Tipo {
    	private Tipo tipo;
        public Tipo_indir(Tipo tipo) {
 		   	super();
 		   	this.tipo = tipo;
        }   
        public String toString() {
        	return "tipo_indir("+tipo+")";
	    }
        //RECURSIVO
        public Tipo Tipo() {return this.tipo;}
        //INTERPRETE
        public void imprime() {
            println("^");
            this.tipo.imprime();
		}
        //VISITANTE
    }
    
    public static class Tipo_struct extends Tipo {
    	private LCampos campos;
        public Tipo_struct(LCampos campos) {
 		   	super();
 		   	this.campos = campos;
        }   
        public String toString() {
        	return "tipo_struct("+campos+")";
	    }
        //RECURSIVO
        public LCampos LCampos() {return this.campos;}
        //INTERPRETE
        public void imprime() {
            println("<struct>");
            println("{");
            this.campos.imprime();
            println("}");
		}
        //VISITANTE
    }
    
    public static class Tipo_int extends Tipo {
        public Tipo_int() {
 		   	super();
        }   
        public String toString() {
        	return "tipo_int()";
	    } 
        //INTERPRETE
        public void imprime() {println("<int>");}
    }
    
    public static class Tipo_real extends Tipo {
        public Tipo_real() {
 		   	super();
        }   
        public String toString() {
        	return "tipo_real()";
	    } 
        //INTERPRETE
        public void imprime() {println("<real>");}
    }
    
    public static class Tipo_bool extends Tipo {
        public Tipo_bool() {
 		   	super();
        }   
        public String toString() {
        	return "tipo_bool()";
	    } 
        //INTERPRETE
        public void imprime() {println("<bool>");}
    }
    
    public static class Tipo_string extends Tipo {
        public Tipo_string() {
 		   	super();
        }   
        public String toString() {
        	return "tipo_string()";
	    } 
        //INTERPRETE
        public void imprime() {println("<string>");}
    }
    
    public static class Tipo_type extends Tipo {
    	private String id;
        public Tipo_type(String id) {
 		   	super();
 		   	this.id = id;
        }   
        public String toString() {
        	return "tipo_type()";
	    } 
        //RECURSIVO
        public String ID() {return this.id;}
        //INTERPRETE
        public void imprime() {println(this.id);}
        //VISITANTE
    }
    
    public static abstract class LCampos extends Nodo {
        public LCampos() {
 		   super();
        }
        public LCampos LCampos() {throw new UnsupportedOperationException();}
        public TipoNom TipoNom() {throw new UnsupportedOperationException();}
     }
 	
 	public static class Muchos_campos extends LCampos {
        private LCampos campos;
        private TipoNom campo;
        public Muchos_campos(LCampos campos, TipoNom campo) {
        	super();
        	this.campos = campos;
        	this.campo = campo;
        }
        public String toString() {
        	return "muchos_campos("+campos+","+campo+")";
        } 
        //RECURSIVO
        public LCampos LCampos() {return this.campos;}
        public TipoNom TipoNom() {return this.campo;}
        //INTERPRETE
        public void imprime() {
            this.campos.imprime();
            println(",");
            this.campo.imprime();
        }
        //VISITANTE
     }

     public static class Un_campo extends LCampos {
    	 private TipoNom campo;
     	public Un_campo(TipoNom campo) {
     		super();
     		this.campo = campo;
     	}
     	public String toString() {
     		return "un_campo("+campo+")";
        } 
     	//RECURSIVO
        public TipoNom TipoNom() {return this.campo;}
        //INTERPRETE
        public void imprime() {
            this.campo.imprime();
		}
        //VISITANTE
     }
    
    // Instrucciones
     
     public static abstract class SecIs extends Nodo {
         public SecIs() {
         	super();
         }
         public LIs LIs() {throw new UnsupportedOperationException();}
     }
     
     public static class Si_ins extends SecIs {
 		private LIs ins; 
 		public Si_ins(LIs ins) {
 		   super();
 		   this.ins = ins;
 		}   
 		public String toString() {
 			return "si_ins("+ins+")";
 		} 
 		//RECURSIVO
        public LIs LIs() {return this.ins;}
        //INTERPRETE
        public void imprime() {
            this.ins.imprime();
		}
        //VISITANTE
     }
     
 	public static class No_ins extends SecIs {
 	    public No_ins() {
 	       super();
 	    }   
 	    public String toString() {
 	    	return "no_ins()";
 	    } 
        //INTERPRETE
        public void imprime() {}
 	}
 	
 	public static abstract class LIs extends Nodo {
        public LIs() {
 		   super();
        }
        public LIs LIs() {throw new UnsupportedOperationException();}
        public I I() {throw new UnsupportedOperationException();}
     }
 	
 	public static class Muchas_ins extends LIs {
        private LIs ins;
        private I in;
        public Muchas_ins(LIs ins, I in) {
        	super();
           	this.ins = ins;
           	this.in = in;
        }
        public String toString() {
        	return "muchas_ins("+ins+","+in+")";
        } 
        //RECURSIVO
        public LIs LIs() {return this.ins;}
        public I I() {return this.in;}
        //INTERPRETE
        public void imprime() {
            this.ins.imprime();
            println(";");
            this.in.imprime();
		}
        //VISITANTE
     }

     public static class Una_ins extends LIs {
        private I in;
        public Una_ins(I in) {
        	super();
           	this.in = in;
        }
        public String toString() {
        	return "una_ins("+in+")";
        } 
        //RECURSIVO
        public I I() {return this.in;}
        //INTERPRETE
        public void imprime() {
            this.in.imprime();
		}
        //VISITANTE
     }
     
     public static abstract class I extends Nodo {
         public I() {
  		   super();
         }
         public Exp Exp() {throw new UnsupportedOperationException();}
         public Bloq Bloq() {throw new UnsupportedOperationException();}
         public I I() {throw new UnsupportedOperationException();}
         public String ID() {throw new UnsupportedOperationException();}
         public ParamRs ParamRs() {throw new UnsupportedOperationException();}
      }
     
     public static class Ins_eval extends I {
         private Exp exp;
         public Ins_eval(Exp exp) {
        	super();
         	this.exp = exp;
         }
         public String toString() {
             return "ins_eval("+exp+")";
         } 
         //RECURSIVO
         public Exp Exp() {return this.exp;}
         //INTERPRETE
         public void imprime() {
            println("<@>");
            this.exp.imprime();
 		}
         //VISITANTE
     }
     
     public static class Ins_if extends I {
         private Exp exp;
         private Bloq bloq;
         public Ins_if(Exp exp, Bloq bloq) {
        	super();
         	this.exp = exp;
         	this.bloq = bloq;
         }
         public String toString() {
             return "ins_if("+exp+","+bloq+")";
         } 
         //RECURSIVO
         public Exp Exp() {return this.exp;}
         public Bloq Bloq() {return this.bloq;}
         //INTERPRETE
         public void imprime() {
            println("<if>");
            this.exp.imprime();
            this.bloq.imprime();
 		}
         //VISITANTE
     }
     
     public static class Ins_if_else extends I {
         private I in;
         private Bloq bloq;
         public Ins_if_else(I in, Bloq bloq) {
        	super();
         	this.in = in;
         	this.bloq = bloq;
         }
         public String toString() {
             return "ins_if_else("+in+","+bloq+")";
         } 
         //RECURSIVO
         public I I() {return this.in;}
         public Bloq Bloq() {return this.bloq;}
         //INTERPRETE
         public void imprime() {
            this.in.imprime();
            println("<else>");
            this.bloq.imprime();
 		}
         //VISITANTE
     }
     
     public static class Ins_while extends I {
         private Exp exp;
         private Bloq bloq;
         public Ins_while(Exp exp, Bloq bloq) {
        	super();
         	this.exp = exp;
         	this.bloq = bloq;
         }
         public String toString() {
             return "ins_while("+exp+","+bloq+")";
         } 
         //RECURSIVO
         public Exp Exp() {return this.exp;}
         public Bloq Bloq() {return this.bloq;}
         //INTERPRETE
         public void imprime() {
            println("<while>");
            this.exp.imprime();
            this.bloq.imprime();
         }
         //VISITANTE
     }
     
     public static class Ins_read extends I {
         private Exp exp;
         public Ins_read(Exp exp) {
        	super();
         	this.exp = exp;
         }
         public String toString() {
             return "ins_read("+exp+")";
         } 
         //RECURSIVO
         public Exp Exp() {return this.exp;}
         //INTERPRETE
         public void imprime() {
            println("<read>");
            this.exp.imprime();
         }
         //VISITANTE
     }
     
     public static class Ins_write extends I {
         private Exp exp;
         public Ins_write(Exp exp) {
        	super();
         	this.exp = exp;
         }
         public String toString() {
             return "ins_write("+exp+")";
         } 
         //RECURSIVO
         public Exp Exp() {return this.exp;}
         //INTERPRETE
         public void imprime() {
            println("<write>");
            this.exp.imprime();
         }
         //VISITANTE
     }
     
     public static class Ins_nl extends I {
         public Ins_nl() {
        	super();
         }
         public String toString() {
             return "ins_nl()";
         } 
         //INTERPRETE
         public void imprime() { println("<nl>"); }
     }
     
     public static class Ins_new extends I {
         private Exp exp;
         public Ins_new(Exp exp) {
        	super();
         	this.exp = exp;
         }
         public String toString() {
             return "ins_new("+exp+")";
         } 
         //RECURSIVO
         public Exp Exp() {return this.exp;}
         //INTERPRETE
         public void imprime() {
            println("<new>");
            this.exp.imprime();
         }
         //VISITANTE
     }
     
     public static class Ins_delete extends I {
         private Exp exp;
         public Ins_delete(Exp exp) {
        	super();
         	this.exp = exp;
         }
         public String toString() {
             return "ins_delete("+exp+")";
         } 
         //RECURSIVO
         public Exp Exp() {return this.exp;}
         //INTERPRETE
         public void imprime() {
            println("<delete>");
            this.exp.imprime();
         }
         //VISITANTE
     }
     
     public static class Ins_call extends I {
         private String id;
         private ParamRs params;
         public Ins_call(String id, ParamRs params) {
        	super();
         	this.id = id;
         	this.params = params;
         }
         public String toString() {
             return "ins_call("+id+","+params+")";
         } 
         //RECURSIVO
         public String ID() {return this.id;}
         public ParamRs ParamRs() {return this.params;}
         //INTERPRETE
         public void imprime() {
            println("<call>");
            println(this.id);
            println("(");
            this.params.imprime();
            println(")");
         }
         //VISITANTE
     }
     
     public static class Ins_bloque extends I {
         private Bloq bloq;
         public Ins_bloque(Bloq bloq) {
        	super();
         	this.bloq = bloq;
         }
         public String toString() {
             return "ins_bloque("+bloq+")";
         } 
         //RECURSIVO
         public Bloq Bloq() {return this.bloq;}
         //INTERPRETE
         public void imprime() {
            this.bloq.imprime();
         }
         //VISITANTE
     }
     
     public static abstract class ParamRs extends Nodo {
         public ParamRs() {
         	super();
         }
         public LParamRs LParamRs() {throw new UnsupportedOperationException();}
     }
     
     public static class Si_params_r extends ParamRs {
 		private LParamRs paramrs; 
 		public Si_params_r(LParamRs paramrs) {
 		   super();
 		   this.paramrs = paramrs;
 		}   
 		public String toString() {
 			return "si_params_r("+paramrs+")";
 		} 
 		//RECURSIVO
        public LParamRs LParamRs() {return this.paramrs;}
        //INTERPRETE
        public void imprime() {
            this.paramrs.imprime();
        }
        //VISITANTE
    }
     
 	public static class No_params_r extends ParamRs {
 	    public No_params_r() {
 	       super();
 	    }   
 	    public String toString() {
 	    	return "no_params_r()";
 	    } 
        //INTERPRETE
        public void imprime() {}
 	}
     
 	public static abstract class LParamRs extends Nodo {
        public LParamRs() {
 		   super();
        }
        public LParamRs LParamRs() {throw new UnsupportedOperationException();}
        public Exp Exp() {throw new UnsupportedOperationException();}
    }
 	
 	public static class Muchos_params_r extends LParamRs {
        private LParamRs paramrs;
        private Exp exp;
        public Muchos_params_r(LParamRs paramrs, Exp exp) {
           super();
           this.paramrs = paramrs;
           this.exp = exp;
        }
        public String toString() {
             return "muchos_params_r("+paramrs+","+exp+")";
        } 
 		//RECURSIVO
        public LParamRs LParamRs() {return this.paramrs;}
        public Exp Exp() {return this.exp;}
        //INTERPRETE
        public void imprime() {
            this.paramrs.imprime();
            println(",");
            this.exp.imprime();
        }
        //VISITANTE
 	}

     public static class Un_param_r extends LParamRs {
     	private Exp exp;
     	public Un_param_r(Exp exp) {
     		super();
     		this.exp = exp;
     	}
     	public String toString() {
             return "un_param_r("+exp+")";
        } 
 		//RECURSIVO
        public Exp Exp() {return this.exp;}
        //INTERPRETE
        public void imprime() {
            this.exp.imprime();
        }
        //VISITANTE
    }
     
    // Expresiones
   
    public static abstract class Exp extends Nodo {
         public Exp() {
  		   super();
         }
         public abstract int prioridad();
         public Exp Opnd0() {throw new UnsupportedOperationException();}
         public Exp Opnd1() {throw new UnsupportedOperationException();}
         public Exp Opnd() {throw new UnsupportedOperationException();}
         public String ID() {throw new UnsupportedOperationException();}
         public String litEntero() {throw new UnsupportedOperationException();}
         public String litReal() {throw new UnsupportedOperationException();}
         public String litCadena() {throw new UnsupportedOperationException();}
    }
    
    private static abstract class ExpBin extends Exp {
        protected Exp opnd0;
        protected Exp opnd1;
        public ExpBin(Exp opnd0, Exp opnd1) {
            super();
            this.opnd0 = opnd0;
            this.opnd1 = opnd1;
        }
        //RECURSIVO
        public Exp Opnd0() {return this.opnd0;}
        public Exp Opnd1() {return this.opnd1;}
        //VISITANTE
    }
  	
  	public static class Exp_asig extends ExpBin {
         public Exp_asig(Exp opnd0, Exp opnd1) {
            super(opnd0, opnd1);
         }
         public String toString() {
              return "exp_asig("+opnd0+","+opnd1+")";
         }
         public int prioridad() {return 0;}
         public void imprime() {
            imprimeExpBin(opnd0, "=", opnd1, 1, 0);
         }
    }
  	
  	public static class Exp_menor extends ExpBin {
        public Exp_menor(Exp opnd0, Exp opnd1) {
           super(opnd0, opnd1);
        }
        public String toString() {
             return "exp_menor("+opnd0+","+opnd1+")";
         } 
        public int prioridad() {return 1;}
        public void imprime() {
           imprimeExpBin(opnd0, "<", opnd1, 1, 2);
        }
   }
  	
  	public static class Exp_menor_ig extends ExpBin {
        public Exp_menor_ig(Exp opnd0, Exp opnd1) {
           super(opnd0, opnd1);
        }
        public String toString() {
             return "exp_menor_ig("+opnd0+","+opnd1+")";
         } 
        public int prioridad() {return 1;}
        public void imprime() {
           imprimeExpBin(opnd0, "<=", opnd1, 1, 2);
        }
   }
  	
  	public static class Exp_mayor extends ExpBin {
        public Exp_mayor(Exp opnd0, Exp opnd1) {
           super(opnd0, opnd1);
        }
        public String toString() {
             return "exp_mayor("+opnd0+","+opnd1+")";
         } 
        public int prioridad() {return 1;}
        public void imprime() {
           imprimeExpBin(opnd0, ">", opnd1, 1, 2);
        }
   }
  	
  	public static class Exp_mayor_ig extends ExpBin {
        public Exp_mayor_ig(Exp opnd0, Exp opnd1) {
           super(opnd0, opnd1);
        }
        public String toString() {
             return "exp_mayor_ig("+opnd0+","+opnd1+")";
         } 
        public int prioridad() {return 1;}
        public void imprime() {
           imprimeExpBin(opnd0, ">=", opnd1, 1, 2);
        }
   }
  	
  	public static class Exp_ig extends ExpBin {
        public Exp_ig(Exp opnd0, Exp opnd1) {
           super(opnd0, opnd1);
        }
        public String toString() {
             return "exp_ig("+opnd0+","+opnd1+")";
         } 
        public int prioridad() {return 1;}
        public void imprime() {
           imprimeExpBin(opnd0, "==", opnd1, 1, 2);
        }
   }
  	
  	public static class Exp_dist extends ExpBin {
  		public Exp_dist(Exp opnd0, Exp opnd1) {
	       super(opnd0, opnd1);
	    }
	    public String toString() {
	         return "exp_dist("+opnd0+","+opnd1+")";
	     } 
	    public int prioridad() {return 1;}
        public void imprime() {
           imprimeExpBin(opnd0, "!=", opnd1, 1, 2);
        }
   }
  	
  	public static class Exp_suma extends ExpBin {
  		public Exp_suma(Exp opnd0, Exp opnd1) {
	       super(opnd0, opnd1);
	    }
	    public String toString() {
	         return "exp_suma("+opnd0+","+opnd1+")";
	     } 
	    public int prioridad() {return 2;}
        public void imprime() {
           imprimeExpBin(opnd0, "+", opnd1, 2, 3);
        }
   }
  	
  	public static class Exp_resta extends ExpBin {
  		public Exp_resta(Exp opnd0, Exp opnd1) {
	       super(opnd0, opnd1);
	    }
	    public String toString() {
	         return "exp_resta("+opnd0+","+opnd1+")";
	     } 
	    public int prioridad() {return 2;}
        public void imprime() {
           imprimeExpBin(opnd0, "-", opnd1, 3, 3);
        }
   }
  	
  	public static class Exp_and extends ExpBin {
  		public Exp_and(Exp opnd0, Exp opnd1) {
	       super(opnd0, opnd1);
	    }
	    public String toString() {
	         return "exp_and("+opnd0+","+opnd1+")";
	     } 
	    public int prioridad() {return 3;}
        public void imprime() {
           imprimeExpBin(opnd0, "and", opnd1, 4, 3);
        }
   }
  	
  	public static class Exp_or extends ExpBin {
  		public Exp_or(Exp opnd0, Exp opnd1) {
	       super(opnd0, opnd1);
	    }
	    public String toString() {
	         return "exp_or("+opnd0+","+opnd1+")";
	     } 
	    public int prioridad() {return 3;}
        public void imprime() {
            imprimeExpBin(opnd0, "or", opnd1, 4, 4);
         }
    }
  	public static class Exp_mul extends ExpBin {
  		public Exp_mul(Exp opnd0, Exp opnd1) {
	       super(opnd0, opnd1);
	    }
	    public String toString() {
	         return "exp_mul("+opnd0+","+opnd1+")";
	     } 
	    public int prioridad() {return 4;}
        public void imprime() {
            imprimeExpBin(opnd0, "/", opnd1, 4, 5);
         }
  	}
  	
  	public static class Exp_div extends ExpBin {
  		public Exp_div(Exp opnd0, Exp opnd1) {
	       super(opnd0, opnd1);
	    }
	    public String toString() {
	         return "exp_div("+opnd0+","+opnd1+")";
	     } 
	    public int prioridad() {return 4;}
        public void imprime() {
            imprimeExpBin(opnd0, "/", opnd1, 4, 5);
         }
    }
  	
  	public static class Exp_mod extends ExpBin {
  		public Exp_mod(Exp opnd0, Exp opnd1) {
	       super(opnd0, opnd1);
	    }
	    public String toString() {
	         return "exp_mod("+opnd0+","+opnd1+")";
	     } 
	    public int prioridad() {return 4;}
        public void imprime() {
            imprimeExpBin(opnd0, "%", opnd1, 4, 5);
         }
    }
  	
  	public static class Exp_menos extends Exp {
  		private Exp opnd;
  		public Exp_menos(Exp opnd) {
  			super();
  			this.opnd = opnd;
	    }
	    public String toString() {
	    	return "exp_menos("+opnd+")";
	    } 
	    public int prioridad() {return 5;}
	    //RECURSIVO
        public Exp Opnd() {return this.opnd;}
        //INTERPRETE
        public void imprime() {
            println("-");
            imprimeOpnd(this.opnd, 5);
        }
        //VISITANTE
  	}
  	
  	public static class Exp_not extends Exp {
  		private Exp opnd;
  		public Exp_not(Exp opnd) {
  			super();
  			this.opnd = opnd;
	    }
	    public String toString() {
	    	return "exp_not("+opnd+")";
	    } 
	    public int prioridad() {return 5;}
	    //RECURSIVO
        public Exp Opnd() {return this.opnd;}
        //INTERPRETE
        public void imprime() {
            println("<not>");
            imprimeOpnd(this.opnd, 5);
        }
        //VISITANTE
  	}
  	
  	public static class Exp_index extends Exp {
  		private Exp opnd0;
  		private Exp opnd1;
  		public Exp_index(Exp opnd0, Exp opnd1) {
	       super();
	       this.opnd0 = opnd0;
	       this.opnd1 = opnd1;
	    }
	    public String toString() {
	    	return "exp_index("+opnd0+","+opnd1+")";
	    } 
	    public int prioridad() {return 6;}
	    //RECURSIVO
        public Exp Opnd0() {return this.opnd0;}
        public Exp Opnd1() {return this.opnd1;}
        //INTERPRETE
        public void imprime() {
            this.opnd0.imprime();
            println("[");
            imprimeOpnd(this.opnd1, 0);
            println("]");
        }
        //VISITANTE
  	}
  	
  	public static class Exp_reg extends Exp {
  		private Exp opnd;
  		private String id;
  		public Exp_reg(Exp opnd, String id) {
	       super();
	       this.opnd = opnd;
	       this.id = id;
	    }
	    public String toString() {
	    	return "exp_reg("+opnd+","+id+")";
	    } 
	    public int prioridad() {return 6;}
	    //RECURSIVO
        public Exp Opnd() {return this.opnd;}
        public String ID() {return this.id;}
        //INTERPRETE
        public void imprime() {
            imprimeOpnd(this.opnd, 6);
            println(".");
            println(this.id);
        }
        //VISITANTE
  	}
  	
  	public static class Exp_indir extends Exp {
  		private Exp opnd;
  		public Exp_indir(Exp opnd) {
	       super();
	       this.opnd = opnd;
	    }
	    public String toString() {
	         return "exp_indir("+opnd+")";
	    } 
	    public int prioridad() {return 6;}
	    //RECURSIVO
        public Exp Opnd() {return this.opnd;}
        //INTERPRETE
        public void imprime() {
            imprimeOpnd(this.opnd, 6);
            println("^");
        }
        //VISITANTE
  	}
  	
  	public static class Exp_entero extends Exp {
  		private String litEntero;
  		public Exp_entero(String litEntero) {
	       super();
	       this.litEntero = litEntero;
	    }
	    public String toString() {
	         return "exp_entero("+litEntero+")";
	    } 
	    public int prioridad() {return 7;}
	    //RECURSIVO
        public String litEntero() {return this.litEntero;}
        //INTERPRETE
        public void imprime() {
            println(this.litEntero);
        }
        //VISITANTE
  	}
  	
  	public static class Exp_real extends Exp {
  		private String litReal;
  		public Exp_real(String litReal) {
	       super();
	       this.litReal = litReal;
	    }
	    public String toString() {
	         return "exp_real("+litReal+")";
	    } 
	    public int prioridad() {return 7;}
	    //RECURSIVO
        public String litReal() {return this.litReal;}
        //INTERPRETE
        public void imprime() {
            println(this.litReal);
        }
        //VISITANTE
  	}
  	
  	public static class Exp_true extends Exp {
  		public Exp_true() {
	       super();
	    }
	    public String toString() {
	         return "exp_true()";
	    } 
	    public int prioridad() {return 7;}
        //INTERPRETE
        public void imprime() { println("<true>");}
  	}
  	
  	public static class Exp_false extends Exp {
  		public Exp_false() {
	       super();
	    }
	    public String toString() {
	         return "exp_false()";
	    } 
	    public int prioridad() {return 7;}
        //INTERPRETE
        public void imprime() { println("<false>");}
  	}
  	
  	public static class Exp_cadena extends Exp {
  		private String litCadena;
  		public Exp_cadena(String litCadena) {
	       super();
	       this.litCadena = litCadena;
	    }
	    public String toString() {
	         return "exp_cadena("+litCadena+")";
	    } 
	    public int prioridad() {return 7;}
	    //RECURSIVO
        public String litCadena() {return this.litCadena;}
        //INTERPRETE
        public void imprime() {
            println(this.litCadena);
        }
        //VISITANTE
  	}
  	
  	public static class Exp_iden extends Exp {
  		private String id;
  		public Exp_iden(String id) {
	       super();
	       this.id = id;
	    }
	    public String toString() {
	         return "exp_iden("+id+")";
	    } 
	    public int prioridad() {return 7;}
	    //RECURSIVO
        public String ID() {return this.id;}
        //INTERPRETE
        public void imprime() {
            println(this.id);
        }
        //VISITANTE
  	}
  	
  	public static class Exp_null extends Exp {
  		public Exp_null() {
	       super();
	    }
	    public String toString() {
	         return "exp_null()";
	    } 
	    public int prioridad() {return 7;}
        //INTERPRETE
        public void imprime() {println("<null>");}
  	}

    // Funciones constructoras    
    
    public Bloq bloq(SecDecs decs, SecIs ins) {
        return new Bloq(decs,ins);
    }
    
    // Declaraciones
    public Prog prog(Bloq bloq) {
        return new Prog(bloq);
    }
    public SecDecs si_decs(LDecs decs) {
        return new Si_decs(decs);
    }
    public SecDecs no_decs() {
    	return new No_decs();
    }
    public LDecs muchas_decs(LDecs decs, Dec dec) {
        return new Muchas_decs(decs,dec);
    }
    public LDecs una_dec(Dec dec) {
        return new Una_dec(dec);
    }
    public Dec dec_base(TipoNom tiponom) {
        return new Dec_base(tiponom);
    }
    public Dec dec_type(TipoNom tiponom) {
        return new Dec_type(tiponom);
    }
    public Dec dec_proc(String id, ParamFs paramfs, Bloq bloq) {
        return new Dec_proc(id, paramfs, bloq);
    }
    public ParamFs si_params_f(LParamFs paramfs) {
        return new Si_params_f(paramfs);
    }
    public ParamFs no_params_f() {
    	return new No_params_f();
    }
    public LParamFs muchos_params_f(LParamFs paramfs, ParamF paramf) {
        return new Muchos_params_f(paramfs, paramf);
    }
    public LParamFs un_param_f(ParamF paramf) {
        return new Un_param_f(paramf);
    }
    public ParamF si_refparam_f(Tipo tipo, String id) {
        return new Si_refparam_f(tipo, id);
    }
    public ParamF no_refparam_f(Tipo tipo, String id) {
    	return new No_refparam_f(tipo, id);
    }
    // Tipos
    public TipoNom tipo_nombre(Tipo tipo, String id) {
    	return new TipoNom(tipo, id);
    }
    public Tipo tipo_array(Tipo tipo, String litEntero) {
    	return new Tipo_array(tipo, litEntero);
    }
    public Tipo tipo_indir(Tipo tipo) {
    	return new Tipo_indir(tipo);
    }
    public Tipo tipo_struct(LCampos campos) {
    	return new Tipo_struct(campos);
    }
    public Tipo tipo_int() {
    	return new Tipo_int();
    }
    public Tipo tipo_real() {
    	return new Tipo_real();
    }
    public Tipo tipo_bool() {
    	return new Tipo_bool();
    }
    public Tipo tipo_string() {
    	return new Tipo_string();
    }
    public Tipo tipo_type(String id) {
    	return new Tipo_type(id);
    }
    public LCampos muchos_campos(LCampos campos, TipoNom campo) {
    	return new Muchos_campos(campos, campo);
    }
    public LCampos un_campo(TipoNom campo) {
    	return new Un_campo(campo);
    }
    // Instrucciones
    public SecIs si_ins(LIs ins) {
        return new Si_ins(ins);
    }
    public SecIs no_ins() {
    	return new No_ins();
    }
    public LIs muchas_ins(LIs ins, I in) {
        return new Muchas_ins(ins,in);
    }
    public LIs una_ins(I in) {
        return new Una_ins(in);
    }
    public I ins_eval(Exp exp) {
        return new Ins_eval(exp);
    }
    public I ins_if(Exp exp, Bloq bloq) {
        return new Ins_if(exp, bloq);
    }
    public I ins_if_else(I in, Bloq bloq) {
        return new Ins_if_else(in, bloq);
    }
    public I ins_while(Exp exp, Bloq bloq) {
        return new Ins_while(exp, bloq);
    }
    public I ins_read(Exp exp) {
        return new Ins_read(exp);
    }
    public I ins_write(Exp exp) {
        return new Ins_write(exp);
    }
    public I ins_nl() {
        return new Ins_nl();
    }
    public I ins_new(Exp exp) {
        return new Ins_new(exp);
    }
    public I ins_delete(Exp exp) {
        return new Ins_delete(exp);
    }
    public I ins_call(String id, ParamRs params) {
        return new Ins_call(id, params);
    }
    public I ins_bloque(Bloq bloq) {
        return new Ins_bloque(bloq);
    }
    public ParamRs si_params_r(LParamRs paramrs) {
        return new Si_params_r(paramrs);
    }
    public ParamRs no_params_r() {
    	return new No_params_r();
    }
    public LParamRs muchos_params_r(LParamRs paramrs, Exp exp) {
        return new Muchos_params_r(paramrs, exp);
    }
    public LParamRs un_param_r(Exp exp) {
        return new Un_param_r(exp);
    }
    // Expresiones
    public Exp exp_asig(Exp opnd0, Exp opnd1) {
        return new Exp_asig(opnd0,opnd1);
    }
    public Exp exp_menor(Exp opnd0, Exp opnd1) {
        return new Exp_menor(opnd0,opnd1);
    }
    public Exp exp_menor_ig(Exp opnd0, Exp opnd1) {
        return new Exp_menor_ig(opnd0,opnd1);
    }
    public Exp exp_mayor(Exp opnd0, Exp opnd1) {
        return new Exp_mayor(opnd0,opnd1);
    }
    public Exp exp_mayor_ig(Exp opnd0, Exp opnd1) {
        return new Exp_mayor_ig(opnd0,opnd1);
    }
    public Exp exp_ig(Exp opnd0, Exp opnd1) {
        return new Exp_ig(opnd0,opnd1);
    }
    public Exp exp_dist(Exp opnd0, Exp opnd1) {
        return new Exp_dist(opnd0,opnd1);
    }
    public Exp exp_suma(Exp opnd0, Exp opnd1) {
        return new Exp_suma(opnd0,opnd1);
    }
    public Exp exp_resta(Exp opnd0, Exp opnd1) {
        return new Exp_resta(opnd0,opnd1);
    }
    public Exp exp_and(Exp opnd0, Exp opnd1) {
        return new Exp_and(opnd0,opnd1);
    }
    public Exp exp_or(Exp opnd0, Exp opnd1) {
        return new Exp_or(opnd0,opnd1);
    }
    public Exp exp_mul(Exp opnd0, Exp opnd1) {
        return new Exp_mul(opnd0,opnd1);
    }
    public Exp exp_div(Exp opnd0, Exp opnd1) {
        return new Exp_div(opnd0,opnd1);
    }
    public Exp exp_mod(Exp opnd0, Exp opnd1) {
        return new Exp_mod(opnd0,opnd1);
    }
    public Exp exp_menos(Exp opnd) {
        return new Exp_menos(opnd);
    }
    public Exp exp_not(Exp opnd) {
        return new Exp_not(opnd);
    }
    public Exp exp_index(Exp opnd0, Exp opnd1) {
        return new Exp_index(opnd0,opnd1);
    }
    public Exp exp_reg(Exp opnd, String id) {
        return new Exp_reg(opnd,id);
    }
    public Exp exp_indir(Exp opnd) {
        return new Exp_indir(opnd);
    }
    public Exp exp_entero(String litEntero) {
        return new Exp_entero(litEntero);
    }
    public Exp exp_real(String litReal) {
        return new Exp_real(litReal);
    }
    public Exp exp_true() {
        return new Exp_true();
    }
    public Exp exp_false() {
        return new Exp_false();
    }
    public Exp exp_cadena(String litCadena) {
        return new Exp_cadena(litCadena);
    }
    public Exp exp_iden(String id) {
        return new Exp_iden(id);
    }
    public Exp exp_null() {
        return new Exp_null();
    }

    private static void println(String s) {
        System.out.println(s);
    }
    private static void imprimeExpBin(Exp op0, String op, Exp op1, int np0, int np1) {
        imprimeOpnd(op0, np0);
        println(op);
        imprimeOpnd(op1, np1);
    }
    private static void imprimeOpnd(Exp op, int minPrior) {
        boolean surround = op.prioridad() < minPrior;

        if (surround) println("(");
        op.imprime();
        if (surround) println(")");
    }
}