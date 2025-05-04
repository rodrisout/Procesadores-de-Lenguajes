package procesa;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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

public class Tipado extends ProcesamientoDef {
	
	private class Pair {
	    private Tipo T0;
	    private Tipo T1;
	 
	    public Pair(Tipo T0, Tipo T1){
	        this.T0 = T0;
	        this.T1 = T1;
	    }
	    
	    public boolean equals(Pair p) {
	    	return ((claseDe(p.T0, Tipo_type.class) && claseDe(T0, Tipo_type.class) && p.T0.ID().equals(T0.ID()))
	    			||
	    			(p.T0.getClass() == T0.getClass()))
	    		   &&
	    		   ((claseDe(p.T0, Tipo_type.class) && claseDe(T0, Tipo_type.class) && p.T0.ID().equals(T0.ID()))
	   	    			||
		    		(p.T0.getClass() == T0.getClass()));
	        
	    }

	    public int hashCode() {
	        return Objects.hash(T0, T1);
	    }
	}
	
	private boolean claseDe(Object o, Class c) {
        return o == null ? false : o.getClass() == c;
    }  
	
	private Set<Pair> set;
	private Errores errores; 
	
	public Tipado(Errores errores) {
		this.errores = errores;
		this.set = null;
	}
	
	public void procesa(Bloq bloq){
		bloq.secDecs().procesa(this);
        bloq.secIs().procesa(this);
        bloq.setTipo(ambos_ok(bloq.secDecs().getTipo(), bloq.secIs().getTipo()));
	}
	public void procesa(Si_decs decs){
		decs.lDecs().procesa(this);
		decs.setTipo(decs.lDecs().getTipo());
	}
	public void procesa(No_decs decs){
		decs.setTipo(new Tipo_ok());
	}
	public void procesa(Muchas_decs decs){
		decs.lDecs().procesa(this);
		decs.dec().procesa(this);
        decs.setTipo(ambos_ok(decs.lDecs().getTipo(), decs.dec().getTipo()));
	}
	public void procesa(Una_dec dec){
		dec.dec().procesa(this);
		dec.setTipo(dec.dec().getTipo());
	}
	public void procesa(Dec_base dec){
		dec.setTipo(new Tipo_ok());
	}
	public void procesa(Dec_type dec){
		dec.setTipo(new Tipo_ok());
	}
	public void procesa(Dec_proc dec){
		dec.paramFs().procesa(this);
		dec.bloq().procesa(this);
		dec.setTipo(dec.bloq().getTipo());
	}
	public void procesa(Si_ins ins){
		ins.lIs().procesa(this);
		ins.setTipo(ins.lIs().getTipo());
	}
	public void procesa(No_ins ins){
		ins.setTipo(new Tipo_ok());
	}
	public void procesa(Muchas_ins ins){
		ins.lIs().procesa(this);
		ins.i().procesa(this);
        ins.setTipo(ambos_ok(ins.lIs().getTipo(), ins.i().getTipo()));
	}
	public void procesa(Una_ins in){
		in.i().procesa(this);
		in.setTipo(in.i().getTipo());
	}
	public void procesa(Ins_eval in){
		in.exp().procesa(this);
		if(!claseDe(in.exp().getTipo(), Tipo_error.class)) {
			in.setTipo(new Tipo_ok());
		}
		else {
			in.setTipo(new Tipo_error());
		}
		
	}
	public void procesa(Ins_if in){
		in.exp().procesa(this);
		in.bloq().procesa(this);
		if(claseDe(ref(in.exp().getTipo()), Tipo_bool.class) && claseDe(in.bloq().getTipo(), Tipo_ok.class)){
			in.setTipo(new Tipo_ok());
		}
		else {
			in.setTipo(new Tipo_error());
			errores.error(in.exp(), " esperada expresion booleana");
		}
	}
	public void procesa(Ins_if_else in){
		in.i().procesa(this);
		in.bloq().procesa(this);
		in.setTipo(ambos_ok(in.i().getTipo(), in.bloq().getTipo()));
	}
	public void procesa(Ins_while in){
		in.exp().procesa(this);
		in.bloq().procesa(this);
		if(claseDe(ref(in.exp().getTipo()), Tipo_bool.class) && claseDe(in.bloq().getTipo(), Tipo_ok.class)){
			in.setTipo(new Tipo_ok());
		}
		else {
			in.setTipo(new Tipo_error());
			errores.error(in.exp(), " esperada expresion booleana");
		}
	}
	public void procesa(Ins_read in){
		in.exp().procesa(this);
		Tipo T = ref(in.exp().getTipo());
		if(claseDe(T, Tipo_int.class) ||
		   claseDe(T, Tipo_real.class) ||
		   claseDe(T, Tipo_string.class)) {
			if(es_designador(in.exp())) {
				in.setTipo(new Tipo_ok());
			}
			else {
				in.setTipo(new Tipo_error());
				errores.error(in.exp(), "designador esperado");
			}
		}
		else {
			in.setTipo(new Tipo_error());
			errores.error(in.exp(), "valor no legible");
		}
	}

	public void procesa(Ins_write in){
		in.exp().procesa(this);
		Tipo T = ref(in.exp().getTipo());
		if(claseDe(T, Tipo_int.class) ||
		   claseDe(T, Tipo_real.class) ||
		   claseDe(T, Tipo_bool.class) ||
		   claseDe(T, Tipo_string.class)) {
			in.setTipo(new Tipo_ok());
		}
		else {
			in.setTipo(new Tipo_error());
			errores.error(in.exp(), "valor no imprimible");
		}
	}
	public void procesa(Ins_nl in){
		in.setTipo(new Tipo_ok());
	}
	public void procesa(Ins_new in){
		in.exp().procesa(this);
		if(claseDe(ref(in.exp().getTipo()), Tipo_indir.class)) {
			in.setTipo(new Tipo_ok());
		}
		else {
			in.setTipo(new Tipo_error());
			errores.error(in.exp(), "esperado tipo puntero");
		}
	}
	public void procesa(Ins_delete in){
		in.exp().procesa(this);
		if(claseDe(ref(in.exp().getTipo()), Tipo_indir.class)) {
			in.setTipo(new Tipo_ok());
		}
		else {
			in.setTipo(new Tipo_error());
			errores.error(in.exp(), "esperado tipo puntero");
		}
	}
	public void procesa(Ins_call in){
		in.paramRs().procesa(this);
		Dec vinculo = (Dec) in.getVinculo();
		if(claseDe(vinculo, Dec_proc.class)) {
			if(claseDe(in.paramRs(), No_params_r.class) && claseDe(vinculo.paramFs(), No_params_f.class)) {
				in.setTipo(new Tipo_ok());
			}
			else if(claseDe(in.paramRs(), Si_params_r.class) && claseDe(vinculo.paramFs(), Si_params_f.class)) {
				if(num_params(in.paramRs().lParamRs(), vinculo.paramFs().lParamFs())) {
					if(compatibles_params(in.paramRs().lParamRs(), vinculo.paramFs().lParamFs())) {
						in.setTipo(new Tipo_ok());
					}
					else {
						in.setTipo(new Tipo_error());
					}
				}
				else {
					in.setTipo(new Tipo_error());
					errores.error(in, "el numero de parametros reales no coincide con el numero de parametros formales");
				}
			}
			else {
				in.setTipo(new Tipo_error());
				errores.error(in, "el numero de parametros reales no coincide con el numero de parametros formales");
			}
		}
		else {
			in.setTipo(new Tipo_error());
			errores.error(in, in.ID() + " no es un subprograma");
		}
	}

	public void procesa(Ins_bloque in){
		in.bloq().procesa(this);
		in.setTipo(in.bloq().getTipo());
	}
	public void procesa(Si_params_r paramrs){
		paramrs.lParamRs().procesa(this);
	}
	public void procesa(Muchos_params_r paramrs){
		paramrs.lParamRs().procesa(this);
		paramrs.exp().procesa(this);
	}
	public void procesa(Un_param_r paramr){
		paramr.exp().procesa(this);
	}
	public void procesa(Exp_asig exp){
		exp.Opnd0().procesa(this);
		exp.Opnd1().procesa(this);
		if(es_designador(exp.Opnd0())) {
			if(compatibles(exp.Opnd0().getTipo(), exp.Opnd1().getTipo())) {
				exp.setTipo(new Tipo_ok());
			}
			else {
				exp.setTipo(new Tipo_error());
				errores.error(exp, "tipos incompatibles en asignacion");
			}
		}
		else {
			exp.setTipo(new Tipo_error());
			errores.error(exp, "la parte izquierda debe ser un designador");
		}
	}

	public void procesa(Exp_menor exp){
		tipado_rel(exp);
	}
	public void procesa(Exp_menor_ig exp){
		tipado_rel(exp);
	}
	public void procesa(Exp_mayor exp){
		tipado_rel(exp);
	}
	public void procesa(Exp_mayor_ig exp){
		tipado_rel(exp);
	}
	public void procesa(Exp_ig exp){
		tipado_rel(exp);
	}
	public void procesa(Exp_dist exp){
		tipado_rel(exp);
	}
	public void procesa(Exp_suma exp){
		tipado_arit(exp);
	}
	public void procesa(Exp_resta exp){
		tipado_arit(exp);
	}
	public void procesa(Exp_and exp){
		tipado_logic(exp);
	}
	public void procesa(Exp_or exp){
		tipado_logic(exp);
	}
	public void procesa(Exp_mul exp){
		tipado_arit(exp);
	}
	public void procesa(Exp_div exp){
		tipado_arit(exp);
	}
	public void procesa(Exp_mod exp){
		exp.Opnd0().procesa(this);
		exp.Opnd1().procesa(this);
		Tipo T0 = ref(exp.Opnd0().getTipo());
		Tipo T1 = ref(exp.Opnd1().getTipo());
		if (claseDe(T0, Tipo_int.class) && claseDe(T1, Tipo_int.class)) {
			exp.setTipo(new Tipo_int());
		}
		else {
			exp.setTipo(new Tipo_error());
			aviso_error_bin(T0, T1, exp, "tipos incompatibles en operacion");
		}
	}
	public void procesa(Exp_menos exp){
		exp.Opnd().procesa(this);
		Tipo T = ref(exp.Opnd().getTipo());
		if (claseDe(T, Tipo_int.class)) {
			exp.setTipo(new Tipo_int());
		}
		else if (claseDe(T, Tipo_real.class)) {
			exp.setTipo(new Tipo_real());
		}
		else {
			exp.setTipo(new Tipo_error());
			aviso_error_un(T, exp, "tipo incompatible en operacion");
		}
	}
	public void procesa(Exp_not exp){
		exp.Opnd().procesa(this);
		Tipo T = ref(exp.Opnd().getTipo());
		if (claseDe(T, Tipo_bool.class)) {
			exp.setTipo(new Tipo_bool());
		}
		else {
			exp.setTipo(new Tipo_error());
			aviso_error_un(T, exp, "tipo incompatible en operacion");
		}
	}
	public void procesa(Exp_index exp){
		exp.Opnd0().procesa(this);
		exp.Opnd1().procesa(this);
		Tipo T0 = ref(exp.Opnd0().getTipo());
		Tipo T1 = ref(exp.Opnd1().getTipo());
		if (claseDe(T0, Tipo_array.class) && claseDe(T1, Tipo_int.class)) {
			exp.setTipo(T0.tipo());
		}
		else {
			exp.setTipo(new Tipo_error());
			errores.error(exp, "tipos incompatibles en indexacion");
		}
	}
	public void procesa(Exp_reg exp){
		exp.Opnd().procesa(this);
		Tipo T = ref(exp.Opnd().getTipo());
		if (claseDe(T, Tipo_struct.class)) {
			Tipo C = campo_struct(T.lCampos(), exp.ID());
			if(claseDe(C, Tipo_error.class)) {
				errores.error(exp, "campo inexistente:"+exp.ID());
			}
			exp.setTipo(C);
		}
		else {
			exp.setTipo(new Tipo_error());
			errores.error(exp, "se trata de acceder a un campo de un objeto que no es un registro");
		}
	}

	public void procesa(Exp_indir exp){
		exp.Opnd().procesa(this);
		Tipo T = ref(exp.Opnd().getTipo());
		if (claseDe(T, Tipo_indir.class)) {
			exp.setTipo(T.tipo());
		}
		else {
			exp.setTipo(new Tipo_error());
			errores.error(exp, "tipos incompatibles en operacion");
		}
	}
	public void procesa(Exp_entero exp){
		exp.setTipo(new Tipo_int());
	}
	public void procesa(Exp_real exp){
		exp.setTipo(new Tipo_real());
	}
	public void procesa(Exp_true exp){
		exp.setTipo(new Tipo_bool());
	}
	public void procesa(Exp_false exp){
		exp.setTipo(new Tipo_bool());
	}
	public void procesa(Exp_cadena exp){
		exp.setTipo(new Tipo_string());
	}
	public void procesa(Exp_iden exp){
		Nodo vinculo = exp.getVinculo();
		if(claseDe(vinculo, No_refparam_f.class) || claseDe(vinculo, Si_refparam_f.class)) {
			exp.setTipo(((ParamF) vinculo).tipo());
		}
		else if(claseDe(vinculo, Dec_base.class)) {
			exp.setTipo(((Dec_base) vinculo).tipoNom().tipo());
		}
		else {
			exp.setTipo(new Tipo_error());
			errores.error(exp, exp.ID()+" no es variable ni parametro");
		}
		
	}
	public void procesa(Exp_null exp){
		exp.setTipo(new Tipo_null());
	}
	
	private void tipado_logic(Exp Exp) {
		Exp.Opnd0().procesa(this);
		Exp.Opnd1().procesa(this);
		Tipo T0 = ref(Exp.Opnd0().getTipo());
		Tipo T1 = ref(Exp.Opnd1().getTipo());
		if (claseDe(T0, Tipo_bool.class) && claseDe(T1, Tipo_bool.class)) {
			Exp.setTipo(new Tipo_bool());
		}
		else {
			Exp.setTipo(new Tipo_error());
			aviso_error_bin(T0, T1, Exp, "tipos incompatibles en operacion");
		}
	}
	
	private void tipado_arit(Exp Exp) {
		Exp.Opnd0().procesa(this);
		Exp.Opnd1().procesa(this);
		Tipo T0 = ref(Exp.Opnd0().getTipo());
		Tipo T1 = ref(Exp.Opnd1().getTipo());
		if (claseDe(T0, Tipo_int.class) && claseDe(T1, Tipo_int.class)) {
			Exp.setTipo(new Tipo_int());
		}
		else if((claseDe(T0, Tipo_real.class) || claseDe(T0, Tipo_int.class)) &&
				(claseDe(T1, Tipo_real.class) || claseDe(T1, Tipo_int.class))) {
			Exp.setTipo(new Tipo_real());
		}
		else {
			Exp.setTipo(new Tipo_error());
			aviso_error_bin(T0, T1, Exp, "tipos incompatibles en operacion");
		}
	}
	
	private void tipado_rel(Exp Exp) {
		Exp.Opnd0().procesa(this);
		Exp.Opnd1().procesa(this);
		Tipo T0 = ref(Exp.Opnd0().getTipo());
		Tipo T1 = ref(Exp.Opnd1().getTipo());
		if((claseDe(T0, Tipo_int.class) || claseDe(T0, Tipo_real.class)) &&
		   (claseDe(T1, Tipo_int.class) || claseDe(T1, Tipo_real.class))){
			Exp.setTipo(new Tipo_bool());
		}
		else if (claseDe(T0, Tipo_bool.class) && claseDe(T1, Tipo_bool.class)) {
			Exp.setTipo(new Tipo_bool());
		}
		else if(claseDe(T0, Tipo_string.class) && claseDe(T1, Tipo_string.class)) {
			Exp.setTipo(new Tipo_bool());
		}
		else {
			if(claseDe(Exp, Exp_ig.class) || claseDe(Exp, Exp_dist.class)) {
				tipado_rel_indir(T0, T1, Exp);
			}
			else {
				Exp.setTipo(new Tipo_error());
				aviso_error_bin(T0, T1, Exp, "tipos incompatibles en operacion");
			}
		}
	}
	
	private void tipado_rel_indir(Tipo T0, Tipo T1, Exp Exp) {
		if((claseDe(T0, Tipo_null.class) || claseDe(T0, Tipo_indir.class)) &&
		   (claseDe(T1, Tipo_null.class) || claseDe(T1, Tipo_indir.class))) {
			Exp.setTipo(new Tipo_bool());
		}
		else {
			Exp.setTipo(new Tipo_error());
			aviso_error_bin(T0, T1, Exp, "tipos incompatibles en operacion");
		}
	}

	// FUNCIONES AUXILIARES
	
	private void aviso_error_bin(Tipo T0, Tipo T1, Exp Exp, String error) {
		if(!claseDe(T0, Tipo_error.class) && !claseDe(T1, Tipo_error.class)) {
			errores.error(Exp, error);
		}
	}
	
	private void aviso_error_un(Tipo T, Exp Exp, String error) {
		if(!claseDe(T, Tipo_error.class)) {
			errores.error(Exp, error);
		}
	}
	
	private Tipo ambos_ok(Tipo T0, Tipo T1) {
		if(claseDe(T0, Tipo_ok.class) && claseDe(T1, Tipo_ok.class)) {
			return new Tipo_ok();
		}
		else {
			return new Tipo_error();
		}
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
	
	private boolean compatibles(Tipo T0, Tipo T1) {
		set = new HashSet<Pair>();
		set.add(new Pair(T0, T1));
		return unificables(T0, T1);
	}

	private boolean unificables(Tipo T0, Tipo T1) {
		Tipo T0p = ref(T0);
		Tipo T1p = ref(T1);
		if(claseDe(T0p, Tipo_array.class) && claseDe(T1p, Tipo_array.class)) {
			return son_unificables(T0p.tipo(), T1p.tipo());
		}
		else if(claseDe(T0p, Tipo_struct.class) && claseDe(T1p, Tipo_struct.class)) {
			return son_unificables_struct(T0p.lCampos(), T1p.lCampos());
		}
		else if(claseDe(T0p, Tipo_indir.class) && claseDe(T1p, Tipo_null.class)) {
			return true;
		}
		else if(claseDe(T0p, Tipo_indir.class) && claseDe(T1p, Tipo_indir.class)) {
			return son_unificables(T0p.tipo(), T1p.tipo());
		}
		else if(T0p.getClass() == T1p.getClass() || (claseDe(T0p, Tipo_real.class) && claseDe(T1p, Tipo_int.class))) {
			return true;
		}
		else {
			return false;
		}
	}

	private boolean son_unificables(Tipo T0, Tipo T1) {
		if(set.contains(new Pair(T0, T1))) {
			return true;
		}
		else {
			set.add(new Pair(T0, T1));
			return unificables(T0, T1);
		}
	}
	
	private boolean son_unificables_struct(LCampos LCampos0, LCampos LCampos1) {
		if(claseDe(LCampos0, Un_campo.class) &&  claseDe(LCampos1, Un_campo.class)) {
			return son_unificables(LCampos0.tipoNom().tipo(), LCampos1.tipoNom().tipo());
		}
		else if (claseDe(LCampos0, Muchos_campos.class) &&  claseDe(LCampos1, Muchos_campos.class)) {
			return son_unificables(LCampos0.tipoNom().tipo(), LCampos1.tipoNom().tipo()) &&
				   son_unificables_struct(LCampos0.lCampos(), LCampos1.lCampos());
		}
		else {
			return false;
		}
	}
	
	private Tipo campo_struct(LCampos LCampos, String iden) {
		if(claseDe(LCampos, Un_campo.class)) {
			if(iden.equals(LCampos.tipoNom().ID())) {
				return LCampos.tipoNom().tipo();
			}
			else {
				return new Tipo_error();
			}
		}
		else {
			if(iden.equals(LCampos.tipoNom().ID())) {
				return LCampos.tipoNom().tipo();
			}
			else {
				return campo_struct(LCampos.lCampos(), iden);
			}
		}
	}
	
	private boolean num_params(LParamRs paramRs, LParamFs paramFs) {
		if(claseDe(paramRs, Un_param_r.class) &&  claseDe(paramFs, Un_param_f.class)) {
			return true;
		}
		else if (claseDe(paramRs, Muchos_params_r.class) &&  claseDe(paramFs, Muchos_params_f.class)) {
			return num_params(paramRs.lParamRs(), paramFs.lParamFs());
		}
		else {
			return false;
		}
	}
	
	private boolean compatibles_params(LParamRs paramRs, LParamFs paramFs) {
		if(claseDe(paramRs, Un_param_r.class) &&  claseDe(paramFs, Un_param_f.class)) {
			return param_r_f(paramRs.exp(), paramFs.paramF());
		}
		else {
			return param_r_f(paramRs.exp(), paramFs.paramF()) &&
				   compatibles_params(paramRs.lParamRs(), paramFs.lParamFs());
		}
	}

	private boolean param_r_f(Exp exp, ParamF paramF) {
		if (claseDe(paramF, Si_refparam_f.class)) {
			if(es_designador(exp)) {
				if(claseDe(paramF.tipo(), Tipo_real.class)) {
					if(claseDe(exp.getTipo(), Tipo_real.class)) {
						return true;
					}
					else {
						errores.error(exp, "el tipo debe ser real");
						return false;
					}
				}
				else {
					if(compatibles(paramF.tipo(), exp.getTipo())) {
						return true;
					}
					else {
						errores.error(exp, "tipo incompatible con tipo de parametro formal");
						return false;
					}
				}
			}
			else {
				errores.error(exp, "se esperaba un designador");
				return false;
			}
		}
		else {
			if(compatibles(paramF.tipo(), exp.getTipo())) {
				return true;
			}
			else {
				errores.error(exp, "tipo incompatible con tipo de parametro formal");
				return false;
			}
		}
	}
	
}
