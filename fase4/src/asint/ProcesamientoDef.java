package asint;

import asint.SintaxisAbstractaTiny.Bloq;
import asint.SintaxisAbstractaTiny.Dec_base;
import asint.SintaxisAbstractaTiny.Dec_proc;
import asint.SintaxisAbstractaTiny.Dec_type;
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
import asint.SintaxisAbstractaTiny.No_decs;
import asint.SintaxisAbstractaTiny.No_ins;
import asint.SintaxisAbstractaTiny.No_params_f;
import asint.SintaxisAbstractaTiny.No_params_r;
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

public class ProcesamientoDef implements Procesamiento {
	public void procesa(Bloq bloq){}
	public void procesa(Si_decs decs){}
	public void procesa(No_decs decs){}
	public void procesa(Muchas_decs decs){}
	public void procesa(Una_dec dec){}
	public void procesa(Dec_base dec){}
	public void procesa(Dec_type dec){}
	public void procesa(Dec_proc dec){}
	public void procesa(Si_params_f paramfs){}
	public void procesa(No_params_f paramfs){}
	public void procesa(Muchos_params_f paramfs){}
	public void procesa(Un_param_f paramf){}
	public void procesa(Si_refparam_f paramf){}
	public void procesa(No_refparam_f paramf){}
	public void procesa(TipoNom tiponom){}
	public void procesa(Tipo_array tipo){}
	public void procesa(Tipo_indir tipo){}
	public void procesa(Tipo_struct tipo){}
	public void procesa(Tipo_int tipo){}
	public void procesa(Tipo_real tipo){}
	public void procesa(Tipo_bool tipo){}
	public void procesa(Tipo_string tipo){}
	public void procesa(Tipo_type tipo){}
	public void procesa(Muchos_campos campos){}
	public void procesa(Un_campo campo){}
	public void procesa(Si_ins ins){}
	public void procesa(No_ins ins){}
	public void procesa(Muchas_ins ins){}
	public void procesa(Una_ins in){}
	public void procesa(Ins_eval in){}
	public void procesa(Ins_if in){}
	public void procesa(Ins_if_else in){}
	public void procesa(Ins_while in){}
	public void procesa(Ins_read in){}
	public void procesa(Ins_write in){}
	public void procesa(Ins_nl in){}
	public void procesa(Ins_new in){}
	public void procesa(Ins_delete in){}
	public void procesa(Ins_call in){}
	public void procesa(Ins_bloque in){}
	public void procesa(Si_params_r paramrs){}
	public void procesa(No_params_r paramrs){}
	public void procesa(Muchos_params_r paramrs){}
	public void procesa(Un_param_r paramr){}
	public void procesa(Exp_asig exp){}
	public void procesa(Exp_menor exp){}
	public void procesa(Exp_menor_ig exp){}
	public void procesa(Exp_mayor exp){}
	public void procesa(Exp_mayor_ig exp){}
	public void procesa(Exp_ig exp){}
	public void procesa(Exp_dist exp){}
	public void procesa(Exp_suma exp){}
	public void procesa(Exp_resta exp){}
	public void procesa(Exp_and exp){}
	public void procesa(Exp_or exp){}
	public void procesa(Exp_mul exp){}
	public void procesa(Exp_div exp){}
	public void procesa(Exp_mod exp){}
	public void procesa(Exp_menos exp){}
	public void procesa(Exp_not exp){}
	public void procesa(Exp_index exp){}
	public void procesa(Exp_reg exp){}
	public void procesa(Exp_indir exp){}
	public void procesa(Exp_entero exp){}
	public void procesa(Exp_real exp){}
	public void procesa(Exp_true exp){}
	public void procesa(Exp_false exp){}
	public void procesa(Exp_cadena exp){}
	public void procesa(Exp_iden exp){}
	public void procesa(Exp_null exp){}
}
