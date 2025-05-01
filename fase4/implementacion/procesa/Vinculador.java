package procesa;

import java.util.HashMap;
import java.util.Map;

import asint.SintaxisAbstractaTiny;
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
import errors.Errores;

public class Vinculador extends SintaxisAbstractaTiny {
	
    private TablaSimbolos ts;
	private Errores errores; 

	public Vinculador(Errores errores) {
		this.errores = errores;
		this.ts = creaTS();
	}
	
	private boolean claseDe(Object o, Class c) {
        return o == null ? false : o.getClass() == c;
    }   
	
	public void vincula(Bloq bloq) {
        abreAmbito(ts);
        vincula(bloq.secDecs());
        vincula(bloq.secIs());
        cierraAmbito(ts);
    }
    // DECLARACIONES
    private void vincula(SecDecs decs) {
    	if(claseDe(decs,Si_decs.class)) {
    		vincula1(decs.lDecs());
    		vincula2(decs.lDecs());
    	}
    }
    private void vincula1(LDecs decs) {
    	if(claseDe(decs,Muchas_decs.class)) {
    		vincula1(decs.lDecs());
    	}
    	vincula1(decs.dec());
    }
    private void vincula2(LDecs decs) {
    	if(claseDe(decs,Muchas_decs.class)) {
    		vincula2(decs.lDecs());
    	}
    	vincula2(decs.dec());
    }
    private void vincula1(Dec dec) {
    	if(claseDe(dec,Dec_base.class)) {
    		if(contiene(ts, dec.tipoNom().ID())) {
    			errores.error(dec.tipoNom(), "declaracion duplicada:"+dec.tipoNom().ID());
    		}
    		else {
    			inserta(ts, dec.tipoNom().ID(), dec);
    		}
    		vincula1(dec.tipoNom());
    	}
    	else if(claseDe(dec,Dec_type.class)) {
    		if(contiene(ts, dec.tipoNom().ID())) {
    			errores.error(dec.tipoNom(), "declaracion duplicada:"+dec.tipoNom().ID());
    		}
    		else {
    			inserta(ts, dec.tipoNom().ID(), dec);
    		}
    		vincula1(dec.tipoNom());
    	}
    	else if(claseDe(dec,Dec_proc.class)) {
    		if(contiene(ts, dec.ID())) {
    			errores.error(dec, "declaracion duplicada:"+dec.ID());
    		}
    		else {
    			inserta(ts, dec.ID(), dec);
    		}
    	}
    }
    private void vincula2(Dec dec) {
    	if(claseDe(dec,Dec_base.class)) {
    		vincula2(dec.tipoNom());
    	}
    	else if(claseDe(dec,Dec_type.class)) {
    		vincula2(dec.tipoNom());
    	}
    	else if(claseDe(dec,Dec_proc.class)) {
    		abreAmbito(ts);
    		vincula1(dec.paramFs());
    		vincula2(dec.paramFs());
    		vincula(dec.bloq());
    		cierraAmbito(ts);
    	}
    }
    private void vincula1(ParamFs paramfs) {
    	if(claseDe(paramfs,Si_params_f.class)) {
    		vincula1(paramfs.lParamFs());
    	}
    }
    private void vincula2(ParamFs paramfs) {
    	if(claseDe(paramfs,Si_params_f.class)) {
    		vincula2(paramfs.lParamFs());
    	}
    }
    private void vincula1(LParamFs paramfs) {
    	if(claseDe(paramfs,Muchos_params_f.class)) {
    		vincula1(paramfs.lParamFs());
    	}
    	vincula1(paramfs.paramF());
    }
    private void vincula2(LParamFs paramfs) {
    	if(claseDe(paramfs,Muchos_params_f.class)) {
    		vincula2(paramfs.lParamFs());
    	}
    	vincula2(paramfs.paramF());
    }
    private void vincula1(ParamF paramf) {
    	if(contiene(ts, paramf.ID())) {
			errores.error(paramf, "declaracion duplicada:"+paramf.ID());
		}
		else {
			inserta(ts, paramf.ID(), paramf);
		}
    	vincula1(paramf.tipo());
    }
    private void vincula2(ParamF paramf) {
    	vincula2(paramf.tipo());
    }
    // TIPOS
    private void vincula1(TipoNom tiponom) {
		vincula1(tiponom.tipo());
    }
    private void vincula2(TipoNom tiponom) {
    	vincula2(tiponom.tipo());
    }
    private void vincula1(Tipo tipo) {
    	if(claseDe(tipo,Tipo_array.class)) {
    		if(!claseDe(tipo.tipo(), Tipo_type.class)){
    			vincula1(tipo.tipo());
    		}
    	}
    	else if(claseDe(tipo,Tipo_indir.class)) {
    		if(!claseDe(tipo.tipo(), Tipo_type.class)){
    			vincula1(tipo.tipo());
    		}
    	}
    	else if(claseDe(tipo,Tipo_struct.class)) {
    		vincula1(tipo.lCampos());
    	}
    }
    private void vincula2(Tipo tipo) {
    	if(claseDe(tipo,Tipo_array.class)) {
    		if(claseDe(tipo.tipo(), Tipo_type.class)){
    			tipo.tipo().setVinculo(vinculoDe(ts, tipo.tipo().ID()));
    			if(!claseDe(tipo.tipo().getVinculo(), Dec_type.class)){
    				errores.error(tipo.tipo(), "identificador no declarado:"+tipo.tipo().ID());
        		}
    		}
    		else {
    			vincula2(tipo.tipo());
    		}
    	}
    	else if(claseDe(tipo,Tipo_indir.class)) {
    		if(claseDe(tipo.tipo(), Tipo_type.class)){
    			tipo.tipo().setVinculo(vinculoDe(ts, tipo.tipo().ID()));
    			if(!claseDe(tipo.tipo().getVinculo(), Dec_type.class)){
    				errores.error(tipo.tipo(), "identificador no declarado:"+tipo.tipo().ID());
        		}
    		}
    		else {
    			vincula2(tipo.tipo());
    		}
    	}
    	else if(claseDe(tipo,Tipo_struct.class)) {
    		vincula2(tipo.lCampos());
    	}
    	else if(claseDe(tipo,Tipo_type.class)) {
    		tipo.setVinculo(vinculoDe(ts, tipo.ID()));
    	}
    }
    private void vincula1(LCampos campos) {
    	if(claseDe(campos,Muchos_campos.class)) {
    		vincula1(campos.lCampos());
    	}
    	vincula1(campos.tipoNom());
    }
    private void vincula2(LCampos campos) {
    	if(claseDe(campos,Muchos_campos.class)) {
    		vincula2(campos.lCampos());
    	}
    	vincula2(campos.tipoNom());
    }
    // INSTRUCCIONES
    private void vincula(SecIs ins) {
    	if(claseDe(ins,Si_ins.class)) {
    		vincula(ins.lIs());
    	}
    }
    private void vincula(LIs ins) {
    	if(claseDe(ins,Muchas_ins.class)) {
    		vincula(ins.lIs());
    	}
    	vincula(ins.i());
    }
    private void vincula(I in) {
    	if(claseDe(in,Ins_eval.class)) {
    		vincula(in.exp());
    	}
    	else if(claseDe(in,Ins_if.class)) {
    		vincula(in.exp());
    		vincula(in.bloq());
    	}
		else if(claseDe(in,Ins_if_else.class)) {
			vincula(in.i());
			vincula(in.bloq());
		}
		else if(claseDe(in,Ins_while.class)) {
			vincula(in.exp());
			vincula(in.bloq());
		}
		else if(claseDe(in,Ins_read.class)) {
			vincula(in.exp());
		}
		else if(claseDe(in,Ins_write.class)) {
			vincula(in.exp());
		}
		else if(claseDe(in,Ins_new.class)) {
			vincula(in.exp());
		}
		else if(claseDe(in,Ins_delete.class)) {
			vincula(in.exp());
		}
		else if(claseDe(in,Ins_call.class)) {
			in.setVinculo(vinculoDe(ts, in.ID()));
			if(in.getVinculo() == null){
				errores.error(in, "identificador no declarado:"+in.ID());
    		}
			vincula(in.paramRs());
		}
		else if(claseDe(in,Ins_bloque.class)) {
    		vincula(in.bloq());
		}
    }
    private void vincula(ParamRs paramrs) {
    	if(claseDe(paramrs,Si_params_r.class)) {
    		vincula(paramrs.lParamRs());
    	}
    }
    private void vincula(LParamRs paramrs) {
    	if(claseDe(paramrs,Muchos_params_r.class)) {
    		vincula(paramrs.lParamRs());
    	}
    	vincula(paramrs.exp());
    }
    // EXPRESIONES
    private void vincula(Exp exp) {
    	if(claseDe(exp,Exp_asig.class)) {
    		vinculaExpBin(exp.Opnd0(), exp.Opnd1());
    	}
    	else if(claseDe(exp,Exp_menor.class)) {
    		vinculaExpBin(exp.Opnd0(), exp.Opnd1());
    	}
    	else if(claseDe(exp,Exp_menor_ig.class)) {
    		vinculaExpBin(exp.Opnd0(), exp.Opnd1());
    	}
    	else if(claseDe(exp,Exp_mayor.class)) {
    		vinculaExpBin(exp.Opnd0(), exp.Opnd1());
    	}
    	else if(claseDe(exp,Exp_mayor_ig.class)) {
    		vinculaExpBin(exp.Opnd0(), exp.Opnd1());
    	}
    	else if(claseDe(exp,Exp_ig.class)) {
    		vinculaExpBin(exp.Opnd0(), exp.Opnd1());
    	}
    	else if(claseDe(exp,Exp_dist.class)) {
    		vinculaExpBin(exp.Opnd0(), exp.Opnd1());
    	}
    	else if(claseDe(exp,Exp_suma.class)) {
    		vinculaExpBin(exp.Opnd0(), exp.Opnd1());
    	}
    	else if(claseDe(exp,Exp_resta.class)) {
    		vinculaExpBin(exp.Opnd0(), exp.Opnd1());
    	}
    	else if(claseDe(exp,Exp_and.class)) {
    		vinculaExpBin(exp.Opnd0(), exp.Opnd1());
    	}
    	else if(claseDe(exp,Exp_or.class)) {
    		vinculaExpBin(exp.Opnd0(), exp.Opnd1());
    	}
    	else if(claseDe(exp,Exp_mul.class)) {
    		vinculaExpBin(exp.Opnd0(), exp.Opnd1());
    	}
    	else if(claseDe(exp,Exp_div.class)) {
    		vinculaExpBin(exp.Opnd0(), exp.Opnd1());
    	}
    	else if(claseDe(exp,Exp_mod.class)) {
    		vinculaExpBin(exp.Opnd0(), exp.Opnd1());
    	}
    	else if(claseDe(exp,Exp_menos.class)) {
    		vincula(exp.Opnd());
    	}
    	else if(claseDe(exp,Exp_not.class)) {
    		vincula(exp.Opnd());
    	}
    	else if(claseDe(exp,Exp_index.class)) {
    		vincula(exp.Opnd0());
    		vincula(exp.Opnd1());
    	}
    	else if(claseDe(exp,Exp_reg.class)) {
    		vincula(exp.Opnd());
    	}
    	else if(claseDe(exp,Exp_indir.class)) {
    		vincula(exp.Opnd());
    	}
    	else if(claseDe(exp,Exp_iden.class)) {
    		exp.setVinculo(vinculoDe(ts, exp.ID()));
			if(exp.getVinculo() == null){
				errores.error(exp, "identificador no declarado:"+exp.ID());
    		}
    	}

    }
    private void vinculaExpBin(Exp Opnd0, Exp Opnd1) {
    	vincula(Opnd0);
    	vincula(Opnd1);
    }
    
    // Tabla de Símbolos
	private class TablaSimbolos {
        private Map<String,Nodo> tabla;
        private TablaSimbolos padre;
      
        public TablaSimbolos() {
        	this.tabla = new HashMap<>(); 
        	this.padre = null;
        }
        public TablaSimbolos(TablaSimbolos ts) {
        	this.tabla = new HashMap<>(); 
        	this.padre = ts;
        }
        public boolean contiene(String id) {
        	for (String key : this.tabla.keySet()) {
        	    if(key.equals(id)) {
        	    	return true;
        	    }
        	}
        	return false;
        }
        public void inserta(String id, Nodo dec) {
        	this.tabla.put(id, dec);
        }
        public Nodo vinculoDe(String id) {
        	for (String key : this.tabla.keySet()) {
        	    if(key.equals(id)) {
        	    	return this.tabla.get(key);
        	    }
        	}
        	if(this.padre != null) {
        		return this.padre.vinculoDe(id);
        	}
        	else {
        		return null;
        	}
        }
        public TablaSimbolos getPadre() {
        	return this.padre;
        }
	}
	
	private TablaSimbolos creaTS() {
		return new TablaSimbolos();
    }
    private void abreAmbito(TablaSimbolos ts) {
    	this.ts = new TablaSimbolos(ts);
    }
    private boolean contiene(TablaSimbolos ts, String id) {
    	return ts.contiene(id);
    }
    private void inserta(TablaSimbolos ts, String id, Nodo dec) {
    	ts.inserta(id, dec);
    }
    private Nodo vinculoDe(TablaSimbolos ts, String id) {
    	return ts.vinculoDe(id);
    }
    private void cierraAmbito(TablaSimbolos ts) {
    	this.ts = ts.getPadre();
    }

}
