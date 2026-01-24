package procesa;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;

import asint.SintaxisAbstractaTiny.Bloq;
import asint.SintaxisAbstractaTiny.Dec;
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
import asint.SintaxisAbstractaTiny.LCampos;
import asint.SintaxisAbstractaTiny.LParamFs;
import asint.SintaxisAbstractaTiny.LParamRs;
import asint.SintaxisAbstractaTiny.Muchas_decs;
import asint.SintaxisAbstractaTiny.Muchas_ins;
import asint.SintaxisAbstractaTiny.Muchos_campos;
import asint.SintaxisAbstractaTiny.Muchos_params_f;
import asint.SintaxisAbstractaTiny.Muchos_params_r;
import asint.SintaxisAbstractaTiny.No_decs;
import asint.SintaxisAbstractaTiny.No_ins;
import asint.SintaxisAbstractaTiny.No_params_f;
import asint.SintaxisAbstractaTiny.No_params_r;
import asint.SintaxisAbstractaTiny.No_refparam_f;
import asint.SintaxisAbstractaTiny.Nodo;
import asint.SintaxisAbstractaTiny.ParamF;
import asint.SintaxisAbstractaTiny.ParamFs;
import asint.SintaxisAbstractaTiny.ParamRs;
import asint.SintaxisAbstractaTiny.Si_decs;
import asint.SintaxisAbstractaTiny.Si_ins;
import asint.SintaxisAbstractaTiny.Si_params_f;
import asint.SintaxisAbstractaTiny.Si_params_r;
import asint.SintaxisAbstractaTiny.Si_refparam_f;
import asint.SintaxisAbstractaTiny.Tipo;
import asint.SintaxisAbstractaTiny.TipoNom;
import asint.SintaxisAbstractaTiny.Tipo_array;
import asint.SintaxisAbstractaTiny.Tipo_bool;
import asint.SintaxisAbstractaTiny.Tipo_error;
import asint.SintaxisAbstractaTiny.Tipo_indir;
import asint.SintaxisAbstractaTiny.Tipo_int;
import asint.SintaxisAbstractaTiny.Tipo_null;
import asint.SintaxisAbstractaTiny.Tipo_ok;
import asint.SintaxisAbstractaTiny.Tipo_real;
import asint.SintaxisAbstractaTiny.Tipo_string;
import asint.SintaxisAbstractaTiny.Tipo_struct;
import asint.SintaxisAbstractaTiny.Tipo_type;
import asint.SintaxisAbstractaTiny.Un_campo;
import asint.SintaxisAbstractaTiny.Un_param_f;
import asint.SintaxisAbstractaTiny.Un_param_r;
import asint.SintaxisAbstractaTiny.Una_dec;
import asint.SintaxisAbstractaTiny.Una_ins;
import errors.Errores;

public class Etiquetado extends ProcesamientoDef {
	
	private int etq;
	private Stack<Dec_proc> sub_pendientes;

	private boolean claseDe(Object o, Class c) {
		return o == null ? false : o.getClass() == c;
	}  
	
	public Etiquetado() {
		this.etq = 0;
		this.sub_pendientes = new Stack<>();
	}
	
	public void procesa(Bloq bloq){
        bloq.setPrim(etq);
        bloq.secDecs().procesa(this);
        bloq.secIs().procesa(this);
        if(bloq.esPrograma()) {
        	etq++; // stop
        	while (!sub_pendientes.isEmpty()) {
        		Dec_proc sub = sub_pendientes.pop();
        		sub.setPrim(etq);
        		etq++; // desapilad(sub.nivel)
        		sub.bloq().procesa(this);
        		etq += 2; // desactiva + ir_ind
        		sub.setSig(etq);
        	}
        }
        bloq.setSig(etq);
	}
	
	public void procesa(Si_decs decs){
		decs.lDecs().procesa(this);
	}
	
	public void procesa(No_decs decs){}
	
	public void procesa(Muchas_decs decs){
		decs.lDecs().procesa(this);
		decs.dec().procesa(this);
	}
	
	public void procesa(Una_dec dec){
		dec.dec().procesa(this);
	}
	
	public void procesa(Dec_proc dec){
		sub_pendientes.push(dec);
	}
	
	public void procesa(Si_ins ins){
		ins.setPrim(etq);
		ins.lIs().procesa(this);
		ins.setSig(etq);
	}
		
	public void procesa(Muchas_ins ins){
		ins.setPrim(etq);
		ins.lIs().procesa(this);
		ins.i().procesa(this);
		ins.setSig(etq);
	}
	
	public void procesa(Una_ins in){
		in.setPrim(etq);
		in.i().procesa(this);
		in.setSig(etq);
	}
	
	public void procesa(Ins_eval in){
		in.setPrim(etq);
		in.exp().procesa(this);
		//etiquetado_acc_val(in.exp());
		etq++;
		in.setSig(etq);
	}
	
	public void procesa(Ins_if in){
		in.setPrim(etq);
		in.exp().procesa(this);
		etiquetado_acc_val(in.exp());
		etq++; // ir_f
		in.bloq().procesa(this);
		in.setSig(etq);
	}
	
	public void procesa(Ins_if_else in){
		in.setPrim(etq);
		in.i().procesa(this);
		etq++; //ir_a
		in.bloq().procesa(this);
		in.setSig(etq);
	}
	
	public void procesa(Ins_while in){
        in.setPrim(etq);
        in.exp().procesa(this);
        etiquetado_acc_val(in.exp());
        etq++; // ir_f
        in.bloq().procesa(this);
        etq++; // ir_a
        in.setSig(etq);
	}
	
	public void procesa(Ins_read in){
		in.setPrim(etq);
		in.exp().procesa(this);
		//etiquetado_acc_val(in.exp());
		etq += 2; // entrada_std + desapila_ind
		in.setSig(etq);
	}

	public void procesa(Ins_write in){
		in.setPrim(etq);
		in.exp().procesa(this);
		etiquetado_acc_val(in.exp());
		etq++; // salida_std
		in.setSig(etq);
	}
	
	public void procesa(Ins_nl in){
		in.setPrim(etq);
		etq++; // nl
		in.setSig(etq);
	}
	
	public void procesa(Ins_new in){
		in.setPrim(etq);
		in.exp().procesa(this);
		//etiquetado_acc_val(in.exp());
		etq += 2; // alloc & desapilaind
		in.setSig(etq);
	}
	
	public void procesa(Ins_delete in){
		in.setPrim(etq);
		in.exp().procesa(this);
		//etiquetado_acc_val(in.exp());
		etq += 2; // dealloc & apilaind
		in.setSig(etq);
	}
	
	public void procesa(Ins_call in){
		in.setPrim(etq);
		Dec_proc vinc = (Dec_proc) in.getVinculo();
		etq++; // activa
        if(claseDe(in.paramRs(), Si_params_r.class)) {
        	etiquetado_paso_params(in.paramRs().lParamRs(), vinc.paramFs().lParamFs());       	
        }
		etq++; // ir_a
		in.setSig(etq);
	}
	
	public void procesa(Ins_bloque in){
		in.setPrim(etq);
		in.bloq().procesa(this);
		in.setSig(etq);
	}
	
	public void procesa(Exp_asig exp){
		exp.setPrim(etq);
		exp.Opnd0().procesa(this);
		etq++;
    	exp.Opnd1().procesa(this);
    	//etiquetado_acc_val(exp.Opnd1());
		etq++; // copia o desapila_ind
		if(!es_designador(exp.Opnd1()) && claseDe(exp.Opnd0(), Tipo_real.class) && claseDe(exp.Opnd1(), Tipo_int.class)) {
			etq++;
		}
		exp.setSig(etq);
	}

	public void procesa(Exp_menor exp){
		exp.setPrim(etq);
		etiquetado_opnds(exp.Opnd0(), exp.Opnd1());
		etq++;
		exp.setSig(etq);
	}
	
	public void procesa(Exp_menor_ig exp){
		exp.setPrim(etq);
		etiquetado_opnds(exp.Opnd0(), exp.Opnd1());
		etq++;
		exp.setSig(etq);
	}
	
	public void procesa(Exp_mayor exp){
		exp.setPrim(etq);
		etiquetado_opnds(exp.Opnd0(), exp.Opnd1());
		etq++;
		exp.setSig(etq);
	}
	
	public void procesa(Exp_mayor_ig exp){
		exp.setPrim(etq);
		etiquetado_opnds(exp.Opnd0(), exp.Opnd1());
		etq++;
		exp.setSig(etq);
	}
	
	public void procesa(Exp_ig exp){
		exp.setPrim(etq);
		etiquetado_opnds(exp.Opnd0(), exp.Opnd1());
		etq++;
		exp.setSig(etq);
	}
	
	public void procesa(Exp_dist exp){
		exp.setPrim(etq);
		etiquetado_opnds(exp.Opnd0(), exp.Opnd1());
		etq++;
		exp.setSig(etq);
	}
	
	public void procesa(Exp_suma exp){
		exp.setPrim(etq);
		etiquetado_opnds(exp.Opnd0(), exp.Opnd1());
		etq++;
		exp.setSig(etq);
	}
	
	public void procesa(Exp_resta exp){
		exp.setPrim(etq);
		etiquetado_opnds(exp.Opnd0(), exp.Opnd1());
		etq++;
		exp.setSig(etq);
	}
	
	public void procesa(Exp_and exp){
		exp.setPrim(etq);
		etiquetado_opnds(exp.Opnd0(), exp.Opnd1());
		etq++;
		exp.setSig(etq);
	}
	
	public void procesa(Exp_or exp){
		exp.setPrim(etq);
		etiquetado_opnds(exp.Opnd0(), exp.Opnd1());
		etq++;
		exp.setSig(etq);
	}
	
	public void procesa(Exp_mul exp){
		exp.setPrim(etq);
		etiquetado_opnds(exp.Opnd0(), exp.Opnd1());
		etq++;
		exp.setSig(etq);
	}
	
	public void procesa(Exp_div exp){
		exp.setPrim(etq);
		etiquetado_opnds(exp.Opnd0(), exp.Opnd1());
		etq++;
		exp.setSig(etq);
	}
	
	public void procesa(Exp_mod exp){
		exp.setPrim(etq);
		etiquetado_opnds(exp.Opnd0(), exp.Opnd1());
		etq++;
		exp.setSig(etq);
	}
	
	public void procesa(Exp_menos exp){
		exp.setPrim(etq);
		exp.Opnd().procesa(this);
		etiquetado_acc_val(exp.Opnd());
		etq++;
		exp.setSig(etq);
	}
	
	public void procesa(Exp_not exp){
		exp.setPrim(etq);
		exp.Opnd().procesa(this);
		etiquetado_acc_val(exp.Opnd());
		etq++;
		exp.setSig(etq);
	}
	
	public void procesa(Exp_index exp){
		exp.setPrim(etq);
    	exp.Opnd0().procesa(this);
    	exp.Opnd1().procesa(this);
    	etiquetado_acc_val(exp.Opnd1());
		etq+= 3; // apila_int + mul + suma
		exp.setSig(etq);
	}
	
	public void procesa(Exp_reg exp){
		exp.setPrim(etq);
    	exp.Opnd().procesa(this);
		etq+=2; // apila_int + suma
		exp.setSig(etq);
	}
	
	
	public void procesa(Exp_indir exp){
		exp.setPrim(etq);
    	exp.Opnd().procesa(this);
		etq++; // apila_ind
		exp.setSig(etq);
	}

	public void procesa(Exp_entero exp){
		exp.setPrim(etq);
		etq++;
		exp.setSig(etq);
	}
	
	public void procesa(Exp_real exp){
		exp.setPrim(etq);
		etq++;
		exp.setSig(etq);
	}
	
	public void procesa(Exp_true exp){
		exp.setPrim(etq);
		etq++;
		exp.setSig(etq);
	}
	
	public void procesa(Exp_false exp){
		exp.setPrim(etq);
		etq++;
		exp.setSig(etq);
	}
	
	public void procesa(Exp_cadena exp){
		exp.setPrim(etq);
		etq++;
		exp.setSig(etq);
	}
	
	public void procesa(Exp_iden exp){
		exp.setPrim(etq);
		if(claseDe(exp.getVinculo(), Dec_base.class)) {
			etiquetado_acc_id((Dec_base) exp.getVinculo());
    	}
    	else if(claseDe(exp.getVinculo(), Si_refparam_f.class)) {
    		etiquetado_acc_id((Si_refparam_f) exp.getVinculo());
    	}
    	else if(claseDe(exp.getVinculo(), No_refparam_f.class)) {
    		etiquetado_acc_id((No_refparam_f) exp.getVinculo());
    	}
		exp.setSig(etq);
	}
	
	public void procesa(Exp_null exp){
		exp.setPrim(etq);
		etq++;
		exp.setSig(etq);
	}
	
	// FUNCIONES AUXILIARES

	private void etiquetado_acc_val(Exp E){
		if (es_designador(E)) etq++; // apila_ind
	}

    private void etiquetado_opnds(Exp e1, Exp e2) {
        e1.procesa(this);
        etiquetado_acc_val(e1);
        if(claseDe(e1, Tipo_int.class) && claseDe(e2, Tipo_real.class)) {
        	etq++;
        }
        e2.procesa(this);
        etiquetado_acc_val(e2);
        if(claseDe(e1, Tipo_real.class) && claseDe(e2, Tipo_int.class)) {
        	etq++;
        }
    }
    
    private void etiquetado_acc_id(Dec_base dec) { 
    	if (dec.getNivel() == 0) {
    		etq++;
    	}
    	else {
    		etq += 3; //apilad + apila_int + suma
    	}
    }
    
    private void etiquetado_acc_id(No_refparam_f paramf) {
    	etq += 3;
    }
    
    private void etiquetado_acc_id(Si_refparam_f paramf) {
    	etq += 4;
    }
    
	private Tipo ref(Tipo T) {
		if(claseDe(T, Tipo_type.class)) {
			return ref(((Dec_type) T.getVinculo()).tipoNom().tipo());
		}
		else {
			return T;
		}
	}
	
	private boolean es_designador(Exp E) {
		return claseDe(E, Exp_iden.class) ||
			   claseDe(E, Exp_index.class) ||
			   claseDe(E, Exp_reg.class) ||
			   claseDe(E, Exp_indir.class);
	}

	private void etiquetado_paso_params(LParamRs paramRs, LParamFs paramFs) {
		if(claseDe(paramRs, Un_param_r.class) &&  claseDe(paramFs, Un_param_f.class)) {
			param_r_f(paramRs.exp(), paramFs.paramF());
		}
		else {
			param_r_f(paramRs.exp(), paramFs.paramF());
			etiquetado_paso_params(paramRs.lParamRs(), paramFs.lParamFs());
		}
	}
	
	private void param_r_f(Exp exp, ParamF paramF) {
        etq += 3; // dup + apila_int + suma
        exp.procesa(this);
        etq++; // desapila_ind  copia
        if(!es_designador(exp) && claseDe(paramF, Tipo_real.class) && claseDe(exp, Tipo_int.class)) {
			etq++;
		}
	}
	
}
