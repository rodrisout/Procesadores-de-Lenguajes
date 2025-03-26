package asint;

import asint.SintaxisAbstractaTiny.Bloq;
import asint.SintaxisAbstractaTiny.Dec_base;
import asint.SintaxisAbstractaTiny.Dec_proc;
import asint.SintaxisAbstractaTiny.Dec_type;
import asint.SintaxisAbstractaTiny.Exp;
import asint.SintaxisAbstractaTiny.Exp_and;
import asint.SintaxisAbstractaTiny.Exp_asig;
import asint.SintaxisAbstractaTiny.Exp_cadena;
import asint.SintaxisAbstractaTiny.Exp_dist;
import asint.SintaxisAbstractaTiny.Exp_div;
import asint.SintaxisAbstractaTiny.Exp_entero;
import asint.SintaxisAbstractaTiny.Exp_false;
import asint.SintaxisAbstractaTiny.Exp_iden;
import asint.SintaxisAbstractaTiny.Exp_ig;
import asint.SintaxisAbstractaTiny.Exp_index;
import asint.SintaxisAbstractaTiny.Exp_indir;
import asint.SintaxisAbstractaTiny.Exp_mayor;
import asint.SintaxisAbstractaTiny.Exp_mayor_ig;
import asint.SintaxisAbstractaTiny.Exp_menor;
import asint.SintaxisAbstractaTiny.Exp_menor_ig;
import asint.SintaxisAbstractaTiny.Exp_menos;
import asint.SintaxisAbstractaTiny.Exp_mod;
import asint.SintaxisAbstractaTiny.Exp_mul;
import asint.SintaxisAbstractaTiny.Exp_not;
import asint.SintaxisAbstractaTiny.Exp_null;
import asint.SintaxisAbstractaTiny.Exp_or;
import asint.SintaxisAbstractaTiny.Exp_real;
import asint.SintaxisAbstractaTiny.Exp_reg;
import asint.SintaxisAbstractaTiny.Exp_resta;
import asint.SintaxisAbstractaTiny.Exp_suma;
import asint.SintaxisAbstractaTiny.Exp_true;
import asint.SintaxisAbstractaTiny.Ins_bloque;
import asint.SintaxisAbstractaTiny.Ins_call;
import asint.SintaxisAbstractaTiny.Ins_delete;
import asint.SintaxisAbstractaTiny.Ins_eval;
import asint.SintaxisAbstractaTiny.Ins_if;
import asint.SintaxisAbstractaTiny.Ins_if_else;
import asint.SintaxisAbstractaTiny.Ins_new;
import asint.SintaxisAbstractaTiny.Ins_nl;
import asint.SintaxisAbstractaTiny.Ins_read;
import asint.SintaxisAbstractaTiny.Ins_while;
import asint.SintaxisAbstractaTiny.Ins_write;
import asint.SintaxisAbstractaTiny.Muchas_decs;
import asint.SintaxisAbstractaTiny.Muchas_ins;
import asint.SintaxisAbstractaTiny.Muchos_campos;
import asint.SintaxisAbstractaTiny.Muchos_params_f;
import asint.SintaxisAbstractaTiny.Muchos_params_r;
import asint.SintaxisAbstractaTiny.No_refparam_f;
import asint.SintaxisAbstractaTiny.Si_decs;
import asint.SintaxisAbstractaTiny.Si_ins;
import asint.SintaxisAbstractaTiny.Si_params_f;
import asint.SintaxisAbstractaTiny.Si_params_r;
import asint.SintaxisAbstractaTiny.Si_refparam_f;
import asint.SintaxisAbstractaTiny.TipoNom;
import asint.SintaxisAbstractaTiny.Tipo_array;
import asint.SintaxisAbstractaTiny.Tipo_bool;
import asint.SintaxisAbstractaTiny.Tipo_indir;
import asint.SintaxisAbstractaTiny.Tipo_int;
import asint.SintaxisAbstractaTiny.Tipo_real;
import asint.SintaxisAbstractaTiny.Tipo_string;
import asint.SintaxisAbstractaTiny.Tipo_struct;
import asint.SintaxisAbstractaTiny.Tipo_type;
import asint.SintaxisAbstractaTiny.Un_campo;
import asint.SintaxisAbstractaTiny.Un_param_f;
import asint.SintaxisAbstractaTiny.Un_param_r;
import asint.SintaxisAbstractaTiny.Una_dec;
import asint.SintaxisAbstractaTiny.Una_ins;

public class Impresion extends ProcesamientoDef {
	private void print_info(String token, int fila, int col) {
    	System.out.format("%s$f:%d,c:%d$%n", token, fila, col);
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
    	Opnd.procesa(this);
    	if(Opnd.prioridad() < MinPrior) {
    		System.out.println(")");
    	}
    }
	public void procesa(Bloq bloq){
		System.out.println("{");
        bloq.secDecs().procesa(this);
        bloq.secIs().procesa(this);
        System.out.println("}");
	}
	public void procesa(Si_decs decs){
		decs.lDecs().procesa(this);
		System.out.println("&&");
	}
	public void procesa(Muchas_decs decs){
		decs.lDecs().procesa(this);
		System.out.println(";");
		decs.dec().procesa(this);
	}
	public void procesa(Una_dec dec){
		dec.dec().procesa(this);
	}
	public void procesa(Dec_base dec){
		dec.tipoNom().procesa(this);
	}
	public void procesa(Dec_type dec){
		System.out.println("<type>");
		dec.tipoNom().procesa(this);
	}
	public void procesa(Dec_proc dec){
		System.out.println("<proc>");
		print_info(dec.ID(), dec.leeFila(), dec.leeCol());
		System.out.println("(");
		dec.paramFs().procesa(this);
		System.out.println(")");
		dec.bloq().procesa(this);
	}
	public void procesa(Si_params_f paramfs){
		paramfs.lParamFs().procesa(this);
	}
	public void procesa(Muchos_params_f paramfs){
		paramfs.lParamFs().procesa(this);
		System.out.println(",");
		paramfs.paramF().procesa(this);
	}
	public void procesa(Un_param_f paramf){
		paramf.paramF().procesa(this);
	}
	public void procesa(Si_refparam_f paramf){
		paramf.tipo().procesa(this);
		System.out.println("&");
    	print_info(paramf.ID(), paramf.leeFila(), paramf.leeCol());
	}
	public void procesa(No_refparam_f paramf){
		paramf.tipo().procesa(this);
    	print_info(paramf.ID(), paramf.leeFila(), paramf.leeCol());
	}
	public void procesa(TipoNom tiponom){
		tiponom.tipo().procesa(this);
    	print_info(tiponom.ID(), tiponom.leeFila(), tiponom.leeCol());
	}
	public void procesa(Tipo_array tipo){
		tipo.tipo().procesa(this);
		System.out.println("[");
		System.out.println(tipo.litEntero());
		print_info("]", tipo.leeFila(), tipo.leeCol());
	}
	public void procesa(Tipo_indir tipo){
		System.out.println("^");
		tipo.tipo().procesa(this);
	}
	public void procesa(Tipo_struct tipo){
		System.out.println("<struct>");
		System.out.println("{");
		tipo.lCampos().procesa(this);
		System.out.println("}");
	}
	public void procesa(Tipo_int tipo){
		System.out.println("<int>");
	}
	public void procesa(Tipo_real tipo){
		System.out.println("<real>");
	}
	public void procesa(Tipo_bool tipo){
		System.out.println("<bool>");
	}
	public void procesa(Tipo_string tipo){
		System.out.println("<string>");
	}
	public void procesa(Tipo_type tipo){
		print_info(tipo.ID(), tipo.leeFila(), tipo.leeCol());
	}
	public void procesa(Muchos_campos campos){
		campos.lCampos().procesa(this);
		System.out.println(",");
		campos.tipoNom().procesa(this);
	}
	public void procesa(Un_campo campo){
		campo.tipoNom().procesa(this);
	}
	public void procesa(Si_ins ins){
		ins.lIs().procesa(this);
	}
	public void procesa(Muchas_ins ins){
		ins.lIs().procesa(this);
		System.out.println(";");
		ins.i().procesa(this);
	}
	public void procesa(Una_ins in){
		in.i().procesa(this);
	}
	public void procesa(Ins_eval in){
		System.out.println("@");
		in.exp().procesa(this);
	}
	public void procesa(Ins_if in){
		System.out.println("<if>");
		in.exp().procesa(this);
		in.bloq().procesa(this);
	}
	public void procesa(Ins_if_else in){
		in.i().procesa(this);
		System.out.println("<else>");
		in.bloq().procesa(this);
	}
	public void procesa(Ins_while in){
		System.out.println("<while>");
		in.exp().procesa(this);
		in.bloq().procesa(this);
	}
	public void procesa(Ins_read in){
		System.out.println("<read>");
		in.exp().procesa(this);
	}
	public void procesa(Ins_write in){
		System.out.println("<write>");
		in.exp().procesa(this);
	}
	public void procesa(Ins_nl in){
		System.out.println("<nl>");
	}
	public void procesa(Ins_new in){
		System.out.println("<new>");
		in.exp().procesa(this);
	}
	public void procesa(Ins_delete in){
		System.out.println("<delete>");
		in.exp().procesa(this);
	}
	public void procesa(Ins_call in){
		System.out.println("<call>");
		print_info(in.ID(), in.leeFila(), in.leeCol());
		System.out.println("(");
		in.paramRs().procesa(this);
		System.out.println(")");
	}
	public void procesa(Ins_bloque in){
		in.bloq().procesa(this);
	}
	public void procesa(Si_params_r paramrs){
		paramrs.lParamRs().procesa(this);
	}
	public void procesa(Muchos_params_r paramrs){
		paramrs.lParamRs().procesa(this);
		System.out.println(",");
		paramrs.exp().procesa(this);
	}
	public void procesa(Un_param_r paramr){
		paramr.exp().procesa(this);
	}
	public void procesa(Exp_asig exp){
		imprimeExpBin(exp.Opnd0(), "=", exp.Opnd1(), 1, 0, exp.leeFila(), exp.leeCol());
	}
	public void procesa(Exp_menor exp){
		imprimeExpBin(exp.Opnd0(), "<", exp.Opnd1(), 1, 2, exp.leeFila(), exp.leeCol());
	}
	public void procesa(Exp_menor_ig exp){
		imprimeExpBin(exp.Opnd0(), "<=", exp.Opnd1(), 1, 2, exp.leeFila(), exp.leeCol());
	}
	public void procesa(Exp_mayor exp){
		imprimeExpBin(exp.Opnd0(), ">", exp.Opnd1(), 1, 2, exp.leeFila(), exp.leeCol());
	}
	public void procesa(Exp_mayor_ig exp){
		imprimeExpBin(exp.Opnd0(), ">=", exp.Opnd1(), 1, 2, exp.leeFila(), exp.leeCol());
	}
	public void procesa(Exp_ig exp){
		imprimeExpBin(exp.Opnd0(), "==", exp.Opnd1(), 1, 2, exp.leeFila(), exp.leeCol());
	}
	public void procesa(Exp_dist exp){
		imprimeExpBin(exp.Opnd0(), "!=", exp.Opnd1(), 1, 2, exp.leeFila(), exp.leeCol());
	}
	public void procesa(Exp_suma exp){
		imprimeExpBin(exp.Opnd0(), "+", exp.Opnd1(), 2, 3, exp.leeFila(), exp.leeCol());
	}
	public void procesa(Exp_resta exp){
		imprimeExpBin(exp.Opnd0(), "-", exp.Opnd1(), 3, 3, exp.leeFila(), exp.leeCol());
	}
	public void procesa(Exp_and exp){
		imprimeExpBin(exp.Opnd0(), "<and>", exp.Opnd1(), 4, 3, exp.leeFila(), exp.leeCol());
	}
	public void procesa(Exp_or exp){
		imprimeExpBin(exp.Opnd0(), "<or>", exp.Opnd1(), 4, 4, exp.leeFila(), exp.leeCol());
	}
	public void procesa(Exp_mul exp){
		imprimeExpBin(exp.Opnd0(), "*", exp.Opnd1(), 4, 5, exp.leeFila(), exp.leeCol());
	}
	public void procesa(Exp_div exp){
		imprimeExpBin(exp.Opnd0(), "/", exp.Opnd1(), 4, 5, exp.leeFila(), exp.leeCol());
	}
	public void procesa(Exp_mod exp){
		imprimeExpBin(exp.Opnd0(), "%", exp.Opnd1(), 4, 5, exp.leeFila(), exp.leeCol());
	}
	public void procesa(Exp_menos exp){
		print_info("-", exp.leeFila(), exp.leeCol());
		imprimeOpnd(exp.Opnd(), 5);
	}
	public void procesa(Exp_not exp){
		print_info("<not>", exp.leeFila(), exp.leeCol());
		imprimeOpnd(exp.Opnd(), 5);
	}
	public void procesa(Exp_index exp){
		imprimeOpnd(exp.Opnd0(), 6);
		print_info("[", exp.leeFila(), exp.leeCol());
		imprimeOpnd(exp.Opnd1(), 0);
		System.out.println("]");
	}
	public void procesa(Exp_reg exp){
		imprimeOpnd(exp.Opnd(), 6);
		System.out.println(".");
		print_info(exp.ID(), exp.leeFila(), exp.leeCol());
	}
	public void procesa(Exp_indir exp){
		imprimeOpnd(exp.Opnd(), 6);
		print_info("^", exp.leeFila(), exp.leeCol());
	}
	public void procesa(Exp_entero exp){
		print_info(exp.litEntero(), exp.leeFila(), exp.leeCol());
	}
	public void procesa(Exp_real exp){
		print_info(exp.litReal(), exp.leeFila(), exp.leeCol());
	}
	public void procesa(Exp_true exp){
		print_info("<true>", exp.leeFila(), exp.leeCol());
	}
	public void procesa(Exp_false exp){
		print_info("<false>", exp.leeFila(), exp.leeCol());
	}
	public void procesa(Exp_cadena exp){
		print_info(exp.litCadena(), exp.leeFila(), exp.leeCol());
	}
	public void procesa(Exp_iden exp){
		print_info(exp.ID(), exp.leeFila(), exp.leeCol());
	}
	public void procesa(Exp_null exp){
		print_info("<null>", exp.leeFila(), exp.leeCol());
	}
}
