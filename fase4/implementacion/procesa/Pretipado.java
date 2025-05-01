package procesa;

import java.util.HashSet;
import java.util.Set;

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
import errors.Errores;

public class Pretipado extends ProcesamientoDef {
	
	private Set<String> set;
	private Errores errores; 
	
	public Pretipado(Errores errores) {
		this.errores = errores;
		this.set = null;
	}
	
	private boolean claseDe(Object o, Class c) {
        return o == null ? false : o.getClass() == c;
    }   
	
	public void procesa(Bloq bloq){		
		bloq.secDecs().procesa(this);
        bloq.secIs().procesa(this);
	}
	public void procesa(Si_decs decs){
		decs.lDecs().procesa(this);
	}
	public void procesa(Muchas_decs decs){
		decs.lDecs().procesa(this);
		decs.dec().procesa(this);
	}
	public void procesa(Una_dec dec){
		dec.dec().procesa(this);
	}
	public void procesa(Dec_base dec){
		dec.tipoNom().procesa(this);
	}
	public void procesa(Dec_type dec){
		dec.tipoNom().procesa(this);
	}
	public void procesa(Dec_proc dec){
		dec.paramFs().procesa(this);
		dec.bloq().procesa(this);
	}
	public void procesa(Si_params_f paramfs){
		paramfs.lParamFs().procesa(this);
	}
	public void procesa(Muchos_params_f paramfs){
		paramfs.lParamFs().procesa(this);
		paramfs.paramF().procesa(this);
	}
	public void procesa(Un_param_f paramf){
		paramf.paramF().procesa(this);
	}
	public void procesa(Si_refparam_f paramf){
		paramf.tipo().procesa(this);
	}
	public void procesa(No_refparam_f paramf){
		paramf.tipo().procesa(this);
	}
	public void procesa(TipoNom tiponom){
		tiponom.tipo().procesa(this);
	}
	public void procesa(Tipo_array tipo){
		if(Integer.valueOf(tipo.litEntero()) < 0) {
			errores.error(tipo, "la dimension no puede ser negativa");
		}
		tipo.tipo().procesa(this);
	}
	public void procesa(Tipo_indir tipo){
		tipo.tipo().procesa(this);
	}
	public void procesa(Tipo_struct tipo){
		Set<String> tmp = set;
		set = new HashSet<String>();
		tipo.lCampos().procesa(this);
		set = tmp;
	}
	public void procesa(Tipo_type tipo){
		if(!claseDe(tipo.getVinculo(), Dec_type.class)){
			errores.error(tipo, tipo.ID() + " no esta declarado como un tipo");
		}
	}
	public void procesa(Muchos_campos campos){
		campos.lCampos().procesa(this);
		campos.tipoNom().procesa(this);
		if(set.contains(campos.tipoNom().ID())) {
			errores.error(campos.tipoNom(), "campo duplicado:"+ campos.tipoNom().ID());
		}
		else {
			set.add(campos.tipoNom().ID());
		}
	}
	public void procesa(Un_campo campo){
		campo.tipoNom().procesa(this);
		if(set.contains(campo.tipoNom().ID())) {
			errores.error(campo.tipoNom(), "campo duplicado:"+ campo.tipoNom().ID());
		}
		else {
			set.add(campo.tipoNom().ID());
		}
	}
	public void procesa(Si_ins ins){
		ins.lIs().procesa(this);
	}
	public void procesa(Muchas_ins ins){
		ins.lIs().procesa(this);
		ins.i().procesa(this);
	}
	public void procesa(Una_ins in){
		in.i().procesa(this);
	}
	public void procesa(Ins_if in){
		in.bloq().procesa(this);
	}
	public void procesa(Ins_if_else in){
		in.i().procesa(this);
		in.bloq().procesa(this);
	}
	public void procesa(Ins_while in){
		in.bloq().procesa(this);
	}
	public void procesa(Ins_bloque in){
		in.bloq().procesa(this);
	}
}
