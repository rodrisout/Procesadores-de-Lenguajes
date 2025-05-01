package procesa;

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
import asint.SintaxisAbstractaTiny.Nodo;
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

public class ImpresionErrores extends ProcesamientoDef {
	
	private String tipo;
	
	public ImpresionErrores(String tipo) {
		this.tipo = tipo;
	}
	
	private void print_error(Nodo nodo) {
		if(nodo.error())
			System.out.println(this.tipo + " fila:" + nodo.leeFila() + " col:" + nodo.leeCol());
	}

	public void procesa(Bloq bloq){
		print_error(bloq);
		
		bloq.secDecs().procesa(this);
        bloq.secIs().procesa(this);
	}
	public void procesa(Si_decs decs){
		print_error(decs);
		
		decs.lDecs().procesa(this);
	}
	public void procesa(Muchas_decs decs){
		print_error(decs);
		
		decs.lDecs().procesa(this);
		decs.dec().procesa(this);
	}
	public void procesa(Una_dec dec){
		print_error(dec);
		
		dec.dec().procesa(this);
	}
	public void procesa(Dec_base dec){
		print_error(dec);
		
		dec.tipoNom().procesa(this);
	}
	public void procesa(Dec_type dec){
		print_error(dec);
		
		dec.tipoNom().procesa(this);
	}
	public void procesa(Dec_proc dec){
		print_error(dec);
		
		dec.paramFs().procesa(this);
		dec.bloq().procesa(this);
	}
	public void procesa(Si_params_f paramfs){
		print_error(paramfs);
		
		paramfs.lParamFs().procesa(this);
	}
	public void procesa(Muchos_params_f paramfs){
		print_error(paramfs);
		
		paramfs.lParamFs().procesa(this);
		paramfs.paramF().procesa(this);
	}
	public void procesa(Un_param_f paramf){
		print_error(paramf);
		
		paramf.paramF().procesa(this);
	}
	public void procesa(Si_refparam_f paramf){
		print_error(paramf);

		paramf.tipo().procesa(this);
	}
	public void procesa(No_refparam_f paramf){
		print_error(paramf);

		paramf.tipo().procesa(this);
	}
	public void procesa(TipoNom tiponom){		
		tiponom.tipo().procesa(this);
		
		print_error(tiponom);
	}
	public void procesa(Tipo_array tipo){
		print_error(tipo);
		
		tipo.tipo().procesa(this);
	}
	public void procesa(Tipo_indir tipo){
		print_error(tipo);
		
		tipo.tipo().procesa(this);
	}
	public void procesa(Tipo_struct tipo){
		print_error(tipo);
		
		tipo.lCampos().procesa(this);
	}
	public void procesa(Tipo_int tipo){
		print_error(tipo);
	}
	public void procesa(Tipo_real tipo){
		print_error(tipo);
	}
	public void procesa(Tipo_bool tipo){
		print_error(tipo);
	}
	public void procesa(Tipo_string tipo){
		print_error(tipo);
	}
	public void procesa(Tipo_type tipo){
		print_error(tipo);
	}
	public void procesa(Muchos_campos campos){
		print_error(campos);
		
		campos.lCampos().procesa(this);
		campos.tipoNom().procesa(this);
	}
	public void procesa(Un_campo campo){
		print_error(campo);
		
		campo.tipoNom().procesa(this);
	}
	public void procesa(Si_ins ins){
		print_error(ins);
		
		ins.lIs().procesa(this);
	}
	public void procesa(Muchas_ins ins){
		print_error(ins);
		
		ins.lIs().procesa(this);
		ins.i().procesa(this);
	}
	public void procesa(Una_ins in){
		print_error(in);
		
		in.i().procesa(this);
	}
	public void procesa(Ins_eval in){
		print_error(in);
		
		in.exp().procesa(this);
	}
	public void procesa(Ins_if in){
		print_error(in);
		
		in.exp().procesa(this);
		in.bloq().procesa(this);
	}
	public void procesa(Ins_if_else in){
		print_error(in);
		
		in.i().procesa(this);
		in.bloq().procesa(this);
	}
	public void procesa(Ins_while in){
		print_error(in);
		
		in.exp().procesa(this);
		in.bloq().procesa(this);
	}
	public void procesa(Ins_read in){
		print_error(in);
		
		in.exp().procesa(this);
	}
	public void procesa(Ins_write in){
		print_error(in);
		
		in.exp().procesa(this);
	}
	public void procesa(Ins_nl in){
		print_error(in);
	}
	public void procesa(Ins_new in){
		print_error(in);
		
		in.exp().procesa(this);
	}
	public void procesa(Ins_delete in){
		print_error(in);
		
		in.exp().procesa(this);
	}
	public void procesa(Ins_call in){
		print_error(in);

		in.paramRs().procesa(this);
	}
	public void procesa(Ins_bloque in){
		print_error(in);
		
		in.bloq().procesa(this);
	}
	public void procesa(Si_params_r paramrs){
		print_error(paramrs);
		
		paramrs.lParamRs().procesa(this);
	}
	public void procesa(Muchos_params_r paramrs){
		print_error(paramrs);
		
		paramrs.lParamRs().procesa(this);
		paramrs.exp().procesa(this);
	}
	public void procesa(Un_param_r paramr){
		print_error(paramr);
		
		paramr.exp().procesa(this);
	}
	public void procesa(Exp_asig exp){
		print_error(exp);
		
		exp.Opnd0().procesa(this);
		exp.Opnd1().procesa(this);
	}
	public void procesa(Exp_menor exp){
		print_error(exp);
		
		exp.Opnd0().procesa(this);
		exp.Opnd1().procesa(this);
	}
	public void procesa(Exp_menor_ig exp){
		print_error(exp);
		
		exp.Opnd0().procesa(this);
		exp.Opnd1().procesa(this);
	}
	public void procesa(Exp_mayor exp){
		print_error(exp);
		
		exp.Opnd0().procesa(this);
		exp.Opnd1().procesa(this);
	}
	public void procesa(Exp_mayor_ig exp){
		print_error(exp);
		
		exp.Opnd0().procesa(this);
		exp.Opnd1().procesa(this);
	}
	public void procesa(Exp_ig exp){
		print_error(exp);
		
		exp.Opnd0().procesa(this);
		exp.Opnd1().procesa(this);
	}
	public void procesa(Exp_dist exp){
		print_error(exp);
		
		exp.Opnd0().procesa(this);
		exp.Opnd1().procesa(this);
	}
	public void procesa(Exp_suma exp){
		print_error(exp);
		
		exp.Opnd0().procesa(this);
		exp.Opnd1().procesa(this);
	}
	public void procesa(Exp_resta exp){
		print_error(exp);
		
		exp.Opnd0().procesa(this);
		exp.Opnd1().procesa(this);
	}
	public void procesa(Exp_and exp){
		print_error(exp);
		
		exp.Opnd0().procesa(this);
		exp.Opnd1().procesa(this);
	}
	public void procesa(Exp_or exp){
		print_error(exp);
		
		exp.Opnd0().procesa(this);
		exp.Opnd1().procesa(this);
	}
	public void procesa(Exp_mul exp){
		print_error(exp);
		
		exp.Opnd0().procesa(this);
		exp.Opnd1().procesa(this);
	}
	public void procesa(Exp_div exp){
		print_error(exp);
		
		exp.Opnd0().procesa(this);
		exp.Opnd1().procesa(this);
	}
	public void procesa(Exp_mod exp){
		print_error(exp);
		
		exp.Opnd0().procesa(this);
		exp.Opnd1().procesa(this);
	}
	public void procesa(Exp_menos exp){
		print_error(exp);
		
		exp.Opnd().procesa(this);
	}
	public void procesa(Exp_not exp){
		print_error(exp);
		
		exp.Opnd().procesa(this);
	}
	public void procesa(Exp_index exp){
		print_error(exp);
		
		exp.Opnd0().procesa(this);
		exp.Opnd1().procesa(this);
	}
	public void procesa(Exp_reg exp){
		print_error(exp);
		
		exp.Opnd().procesa(this);
	}
	public void procesa(Exp_indir exp){
		print_error(exp);
		
		exp.Opnd().procesa(this);
	}
	public void procesa(Exp_entero exp){
		print_error(exp);
	}
	public void procesa(Exp_real exp){
		print_error(exp);
	}
	public void procesa(Exp_true exp){
		print_error(exp);
	}
	public void procesa(Exp_false exp){
		print_error(exp);
	}
	public void procesa(Exp_cadena exp){
		print_error(exp);
	}
	public void procesa(Exp_iden exp){
		print_error(exp);
	}
	public void procesa(Exp_null exp){
		print_error(exp);
	}
}
