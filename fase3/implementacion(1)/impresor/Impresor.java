package impresor;

import asint.SintaxisAbstractaTiny;

public class Impresor extends SintaxisAbstractaTiny {
    public Impresor() {
    	
    }
    private boolean claseDe(Object o, Class c) {
        return o.getClass() == c;
    }   
    private void print_info(String token, int fila, int col) {
    	System.out.format("%s$f:%d,c:%d$%n", token, fila, col);
    }
    public void imprime(Bloq bloq) {
        System.out.println("{");
        imprime(bloq.secDecs());
        imprime(bloq.secIs());
        System.out.println("}");
    }
    // DECLARACIONES
    private void imprime(SecDecs decs) {
    	if(claseDe(decs,Si_decs.class)) {
    		imprime(decs.lDecs());
    		System.out.println("&&");
    	}
    }
    private void imprime(LDecs decs) {
    	if(claseDe(decs,Muchas_decs.class)) {
    		imprime(decs.lDecs());
    		System.out.println(";");
    	}
    	imprime(decs.dec());
    }
    private void imprime(Dec dec) {
    	if(claseDe(dec,Dec_base.class)) {
    		imprime(dec.tipoNom());
    	}
    	else if(claseDe(dec,Dec_type.class)) {
    		System.out.println("<type>");
    		imprime(dec.tipoNom());
    	}
    	else if(claseDe(dec,Dec_proc.class)) {
    		System.out.println("<proc>");
    		print_info(dec.ID(), dec.leeFila(), dec.leeCol());
    		System.out.println("(");
    		imprime(dec.paramFs());
    		System.out.println(")");
    		imprime(dec.bloq());
    	}
    }
    private void imprime(ParamFs paramfs) {
    	if(claseDe(paramfs,Si_params_f.class)) {
    		imprime(paramfs.lParamFs());
    	}
    }
    private void imprime(LParamFs paramfs) {
    	if(claseDe(paramfs,Muchos_params_f.class)) {
    		imprime(paramfs.lParamFs());
    		System.out.println(",");
    	}
    	imprime(paramfs.paramF());
    }
    private void imprime(ParamF paramf) {
    	imprime(paramf.tipo());
    	if(claseDe(paramf,Si_refparam_f.class)) {
    		System.out.println("&");
    	}
    	print_info(paramf.ID(), paramf.leeFila(), paramf.leeCol());
    }
    // TIPOS
    private void imprime(TipoNom tiponom) {
    	imprime(tiponom.tipo());
    	print_info(tiponom.ID(), tiponom.leeFila(), tiponom.leeCol());
    }
    private void imprime(Tipo tipo) {
    	if(claseDe(tipo,Tipo_array.class)) {
    		imprime(tipo.tipo());
    		System.out.println("[");
    		System.out.println(tipo.litEntero());
    		print_info("]", tipo.leeFila(), tipo.leeCol());
    	}
    	else if(claseDe(tipo,Tipo_indir.class)) {
    		System.out.println("^");
    		imprime(tipo.tipo());
    	}
    	else if(claseDe(tipo,Tipo_struct.class)) {
    		System.out.println("<struct>");
    		System.out.println("{");
    		imprime(tipo.lCampos());
    		System.out.println("}");
    	}
    	else if(claseDe(tipo,Tipo_int.class)) {
    		System.out.println("<int>");
    	}
    	else if(claseDe(tipo,Tipo_real.class)) {
    		System.out.println("<real>");
    	}
    	else if(claseDe(tipo,Tipo_bool.class)) {
    		System.out.println("<bool>");
    	}
    	else if(claseDe(tipo,Tipo_string.class)) {
    		System.out.println("<string>");
    	}
    	else if(claseDe(tipo,Tipo_type.class)) {
    		print_info(tipo.ID(), tipo.leeFila(), tipo.leeCol());
    	}
    }
    private void imprime(LCampos campos) {
    	if(claseDe(campos,Muchos_campos.class)) {
    		imprime(campos.lCampos());
    		System.out.println(",");
    	}
    	imprime(campos.tipoNom());
    }
    // INSTRUCCIONES
    private void imprime(SecIs ins) {
    	if(claseDe(ins,Si_ins.class)) {
    		imprime(ins.lIs());
    	}
    }
    private void imprime(LIs ins) {
    	if(claseDe(ins,Muchas_ins.class)) {
    		imprime(ins.lIs());
    		System.out.println(";");
    	}
    	imprime(ins.i());
    }
    private void imprime(I in) {
    	if(claseDe(in,Ins_eval.class)) {
    		System.out.println("<@>");
    		imprime(in.exp());
    	}
    	else if(claseDe(in,Ins_if.class)) {
    		System.out.println("<if>");
    		imprime(in.exp());
    		imprime(in.bloq());
    	}
		else if(claseDe(in,Ins_if_else.class)) {
			imprime(in.i());
			System.out.println("<else>");
    		imprime(in.bloq());
		}
		else if(claseDe(in,Ins_while.class)) {
			System.out.println("<while>");
    		imprime(in.exp());
    		imprime(in.bloq());
		}
		else if(claseDe(in,Ins_read.class)) {
			System.out.println("<read>");
    		imprime(in.exp());
		}
		else if(claseDe(in,Ins_write.class)) {
			System.out.println("<write>");
    		imprime(in.exp());
		}
		else if(claseDe(in,Ins_nl.class)) {
			System.out.println("<nl>");
		}
		else if(claseDe(in,Ins_new.class)) {
			System.out.println("<new>");
    		imprime(in.exp());
		}
		else if(claseDe(in,Ins_delete.class)) {
			System.out.println("<delete>");
    		imprime(in.exp());
		}
		else if(claseDe(in,Ins_call.class)) {
			System.out.println("<call>");
			print_info(in.ID(), in.leeFila(), in.leeCol());
    		System.out.println("(");
    		imprime(in.paramRs());
    		System.out.println(")");
		}
		else if(claseDe(in,Ins_bloque.class)) {
    		imprime(in.bloq());
		}
    }
    private void imprime(ParamRs paramrs) {
    	if(claseDe(paramrs,Si_params_r.class)) {
    		imprime(paramrs.lParamRs());
    	}
    }
    private void imprime(LParamRs paramrs) {
    	if(claseDe(paramrs,Muchos_params_r.class)) {
    		imprime(paramrs.lParamRs());
    		System.out.println(",");
    	}
    	imprime(paramrs.exp());
    }
    // EXPRESIONES
    private void imprime(Exp exp) {
    	if(claseDe(exp,Exp_asig.class)) {
    		imprimeExpBin(exp.Opnd0(), "=", exp.Opnd1(), 1, 0, exp.leeFila(), exp.leeCol());
    	}
    	else if(claseDe(exp,Exp_menor.class)) {
    		imprimeExpBin(exp.Opnd0(), "<", exp.Opnd1(), 1, 2, exp.leeFila(), exp.leeCol());
    	}
    	else if(claseDe(exp,Exp_menor_ig.class)) {
    		imprimeExpBin(exp.Opnd0(), "<=", exp.Opnd1(), 1, 2, exp.leeFila(), exp.leeCol());
    	}
    	else if(claseDe(exp,Exp_mayor.class)) {
    		imprimeExpBin(exp.Opnd0(), ">", exp.Opnd1(), 1, 2, exp.leeFila(), exp.leeCol());
    	}
    	else if(claseDe(exp,Exp_mayor_ig.class)) {
    		imprimeExpBin(exp.Opnd0(), ">=", exp.Opnd1(), 1, 2, exp.leeFila(), exp.leeCol());
    	}
    	else if(claseDe(exp,Exp_ig.class)) {
    		imprimeExpBin(exp.Opnd0(), "==", exp.Opnd1(), 1, 2, exp.leeFila(), exp.leeCol());
    	}
    	else if(claseDe(exp,Exp_dist.class)) {
    		imprimeExpBin(exp.Opnd0(), "!=", exp.Opnd1(), 1, 2, exp.leeFila(), exp.leeCol());
    	}
    	else if(claseDe(exp,Exp_suma.class)) {
    		imprimeExpBin(exp.Opnd0(), "+", exp.Opnd1(), 2, 3, exp.leeFila(), exp.leeCol());
    	}
    	else if(claseDe(exp,Exp_resta.class)) {
    		imprimeExpBin(exp.Opnd0(), "-", exp.Opnd1(), 3, 3, exp.leeFila(), exp.leeCol());
    	}
    	else if(claseDe(exp,Exp_and.class)) {
    		imprimeExpBin(exp.Opnd0(), "and", exp.Opnd1(), 4, 3, exp.leeFila(), exp.leeCol());
    	}
    	else if(claseDe(exp,Exp_or.class)) {
    		imprimeExpBin(exp.Opnd0(), "or", exp.Opnd1(), 4, 4, exp.leeFila(), exp.leeCol());
    	}
    	else if(claseDe(exp,Exp_mul.class)) {
    		imprimeExpBin(exp.Opnd0(), "*", exp.Opnd1(), 4, 5, exp.leeFila(), exp.leeCol());
    	}
    	else if(claseDe(exp,Exp_div.class)) {
    		imprimeExpBin(exp.Opnd0(), "/", exp.Opnd1(), 4, 5, exp.leeFila(), exp.leeCol());
    	}
    	else if(claseDe(exp,Exp_mod.class)) {
    		imprimeExpBin(exp.Opnd0(), "%", exp.Opnd1(), 4, 5, exp.leeFila(), exp.leeCol());
    	}
    	else if(claseDe(exp,Exp_menos.class)) {
    		print_info("-", exp.leeFila(), exp.leeCol());
    		imprimeOpnd(exp.Opnd(), 5);
    	}
    	else if(claseDe(exp,Exp_not.class)) {
    		print_info("<not>", exp.leeFila(), exp.leeCol());
    		imprimeOpnd(exp.Opnd(), 5);
    	}
    	else if(claseDe(exp,Exp_index.class)) {
    		imprimeOpnd(exp.Opnd0(), 6);
    		print_info("[", exp.leeFila(), exp.leeCol());
    		imprimeOpnd(exp.Opnd1(), 0);
    		System.out.println("]");
    	}
    	else if(claseDe(exp,Exp_reg.class)) {
    		imprimeOpnd(exp.Opnd(), 6);
    		System.out.println(".");
    		print_info(exp.ID(), exp.leeFila(), exp.leeCol());
    	}
    	else if(claseDe(exp,Exp_indir.class)) {
    		imprimeOpnd(exp.Opnd(), 6);
    		print_info("^", exp.leeFila(), exp.leeCol());
    	}
    	else if(claseDe(exp,Exp_entero.class)) {
    		print_info(exp.litEntero(), exp.leeFila(), exp.leeCol());
    	}
    	else if(claseDe(exp,Exp_real.class)) {
    		print_info(exp.litReal(), exp.leeFila(), exp.leeCol());
    	}
    	else if(claseDe(exp,Exp_true.class)) {
    		System.out.println("<true>");
    	}
    	else if(claseDe(exp,Exp_false.class)) {
    		System.out.println("<false>");
    	}
    	else if(claseDe(exp,Exp_cadena.class)) {
    		print_info(exp.litCadena(), exp.leeFila(), exp.leeCol());
    	}
    	else if(claseDe(exp,Exp_iden.class)) {
    		print_info(exp.ID(), exp.leeFila(), exp.leeCol());
    	}
    	else if(claseDe(exp,Exp_null.class)) {
    		System.out.println("<null>");
    	}
    }
    private void imprimeExpBin(Exp Opnd0, String Op, Exp Opnd1, int np0, int np1, int fila, int col) {
    	imprimeOpnd(Opnd0, np0);
    	print_info(Op, fila, col);
		imprimeOpnd(Opnd1, np1);
    }
    private void imprimeOpnd(Exp Opnd, int MinPrior) {
    	if(Opnd.prioridad() < MinPrior) {
    		System.out.println("(");
    	}
    	imprime(Opnd);
    	if(Opnd.prioridad() < MinPrior) {
    		System.out.println(")");
    	}
    }
}