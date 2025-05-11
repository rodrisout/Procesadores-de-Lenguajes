package procesa;

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
import maquinap.MaquinaP;

public class GeneracionCodigo extends ProcesamientoDef {

	private Stack<Dec_proc> sub_pendientes;
	private MaquinaP m;

	private boolean claseDe(Object o, Class c) {
		return o == null ? false : o.getClass() == c;
	}  
	
	public  GeneracionCodigo(MaquinaP m) {
		this.sub_pendientes = new Stack<>();
		this.m = m;
	}
	
	public void procesa(Bloq bloq){
        bloq.secDecs().procesa(this);
        bloq.secIs().procesa(this);
        if(bloq.esPrograma()) {
        	m.emit(m.stop());
        	while (!sub_pendientes.isEmpty()) {
        		Dec_proc sub = sub_pendientes.pop();
        		m.emit(m.desapilad(sub.getNivel()));
        		sub.bloq().procesa(this);
        		m.emit(m.desactiva(sub.getNivel(), sub.getTam()));
        		m.emit(m.ir_ind());
        	}
        }
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
	
	public void procesa(Dec_proc dec){
		sub_pendientes.push(dec);
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
	
	public void procesa(Ins_eval in){
		in.exp().procesa(this);
		//gen_acc_val(in.exp());
		m.emit(m.desapila());
	}
	
	public void procesa(Ins_if in){
        in.exp().procesa(this);
        gen_acc_val(in.exp());
        m.emit(m.ir_f(in.getSig()));
        in.bloq().procesa(this);
	}
	
	public void procesa(Ins_if_else in){
		in.i().exp().procesa(this);
        gen_acc_val(in.i().exp());
        m.emit(m.ir_f(in.i().getSig() + 1));
        in.i().bloq().procesa(this);
		
		m.emit(m.ir_a(in.getSig()));
		in.bloq().procesa(this);
	}
	
	public void procesa(Ins_while in){
		in.exp().procesa(this);
		gen_acc_val(in.exp());
        m.emit(m.ir_f(in.getSig()));
        in.bloq().procesa(this);
        m.emit(m.ir_a(in.getPrim()));
	}
	
	public void procesa(Ins_read in){
		in.exp().procesa(this);
		//gen_acc_val(in.exp());
		m.emit(m.entrada_std(in.exp()));
        m.emit(m.desapila_ind());
	}

	public void procesa(Ins_write in){
		in.exp().procesa(this);
		gen_acc_val(in.exp());
        m.emit(m.salida_std());
	}
	
	public void procesa(Ins_nl in){
		m.emit(m.nl());
	}
	
	public void procesa(Ins_new in){
		in.exp().procesa(this);
		//gen_acc_val(in.exp());
		Tipo T = ref(in.exp().getTipo());
        m.emit(m.alloc(T.tipo().getTam()));
        m.emit(m.desapila_ind());
	}
	
	public void procesa(Ins_delete in){
		in.exp().procesa(this);
		//gen_acc_val(in.exp());
		m.emit(m.apila_ind());
		Tipo T = ref(in.exp().getTipo());
		m.emit(m.dealloc(T.tipo().getTam()));
	}
	
	public void procesa(Ins_call in){
		Dec_proc vinc = (Dec_proc) in.getVinculo();
        m.emit(m.activa(vinc.getNivel(), vinc.getTam(), in.getSig()));
        if(claseDe(in.paramRs(), Si_params_r.class)) {
        	gen_paso_params(in.paramRs().lParamRs(), vinc.paramFs().lParamFs());       	
        }
        m.emit(m.ir_a(vinc.getPrim()));
	}
	
	public void procesa(Ins_bloque in){
		in.bloq().procesa(this);
	}
	
	public void procesa(Exp_asig e) {
		e.Opnd0().procesa(this);
		m.emit(m.dup());
    	e.Opnd1().procesa(this);
    	//gen_acc_val(e.Opnd1());
        if (es_designador(e.Opnd1()) || claseDe(e.Opnd1(), Exp_asig.class)) {
        	if(claseDe(e.Opnd0(), Tipo_real.class) && claseDe(e.Opnd1(), Tipo_int.class)){
        		m.emit(m.copia_int2real(e.Opnd1().getTipo().getTam()));
        	}
        	else {
        		m.emit(m.copia(e.Opnd1().getTipo().getTam()));
        	}
        } 
        else {
        	if(claseDe(e.Opnd0(), Tipo_real.class) && claseDe(e.Opnd1(), Tipo_int.class)){
        		m.emit(m.int2real());
        	}
            m.emit(m.desapila_ind());
        }
    }

    public void procesa(Exp_menor e) {
        gen_cod_opnds(e.Opnd0(), e.Opnd1());
        if(claseDe(e.Opnd0().getTipo(), Tipo_int.class) && claseDe(e.Opnd1().getTipo(), Tipo_int.class)) {
        	m.emit(m.menor_int());
        }
        else if(claseDe(e.Opnd0().getTipo(), Tipo_real.class) || claseDe(e.Opnd1().getTipo(), Tipo_real.class)) {
        	m.emit(m.menor_real());
		}
        else if(claseDe(e.Opnd0().getTipo(), Tipo_bool.class) && claseDe(e.Opnd1().getTipo(), Tipo_bool.class)) {
			m.emit(m.menor_bool());
		}
        else if(claseDe(e.Opnd0().getTipo(), Tipo_string.class) && claseDe(e.Opnd1().getTipo(), Tipo_string.class)) {
			m.emit(m.menor_string());
		}
        //m.emit(m.menor());
    }

    public void procesa(Exp_menor_ig e) {
        gen_cod_opnds(e.Opnd0(), e.Opnd1());
        if(claseDe(e.Opnd0().getTipo(), Tipo_int.class) && claseDe(e.Opnd1().getTipo(), Tipo_int.class)) {
        	m.emit(m.menor_ig_int());
        }
        else if(claseDe(e.Opnd0().getTipo(), Tipo_real.class) || claseDe(e.Opnd1().getTipo(), Tipo_real.class)) {
        	m.emit(m.menor_ig_real());
		}
        else if(claseDe(e.Opnd0().getTipo(), Tipo_bool.class) && claseDe(e.Opnd1().getTipo(), Tipo_bool.class)) {
			m.emit(m.menor_ig_bool());
		}
        else if(claseDe(e.Opnd0().getTipo(), Tipo_string.class) && claseDe(e.Opnd1().getTipo(), Tipo_string.class)) {
			m.emit(m.menor_ig_string());
		}
        //m.emit(m.menor_ig());
    }

    public void procesa(Exp_mayor e) {
        gen_cod_opnds(e.Opnd0(), e.Opnd1());
        if(claseDe(e.Opnd0().getTipo(), Tipo_int.class) && claseDe(e.Opnd1().getTipo(), Tipo_int.class)) {
        	m.emit(m.mayor_int());
        }
        else if(claseDe(e.Opnd0().getTipo(), Tipo_real.class) || claseDe(e.Opnd1().getTipo(), Tipo_real.class)) {
        	m.emit(m.mayor_real());
		}
        else if(claseDe(e.Opnd0().getTipo(), Tipo_bool.class) && claseDe(e.Opnd1().getTipo(), Tipo_bool.class)) {
			m.emit(m.mayor_bool());
		}
        else if(claseDe(e.Opnd0().getTipo(), Tipo_string.class) && claseDe(e.Opnd1().getTipo(), Tipo_string.class)) {
			m.emit(m.mayor_string());
		}
        //m.emit(m.mayor());
    }

    public void procesa(Exp_mayor_ig e) {
        gen_cod_opnds(e.Opnd0(), e.Opnd1());
        if(claseDe(e.Opnd0().getTipo(), Tipo_int.class) && claseDe(e.Opnd1().getTipo(), Tipo_int.class)) {
        	m.emit(m.mayor_ig_int());
        }
        else if(claseDe(e.Opnd0().getTipo(), Tipo_real.class) || claseDe(e.Opnd1().getTipo(), Tipo_real.class)) {
        	m.emit(m.mayor_ig_real());
		}
        else if(claseDe(e.Opnd0().getTipo(), Tipo_bool.class) && claseDe(e.Opnd1().getTipo(), Tipo_bool.class)) {
			m.emit(m.mayor_ig_bool());
		}
        else if(claseDe(e.Opnd0().getTipo(), Tipo_string.class) && claseDe(e.Opnd1().getTipo(), Tipo_string.class)) {
			m.emit(m.mayor_ig_string());
		}
        //m.emit(m.mayor_ig());
    }

    public void procesa(Exp_ig e) {
        gen_cod_opnds(e.Opnd0(), e.Opnd1());
        if(claseDe(e.Opnd0().getTipo(), Tipo_int.class) && claseDe(e.Opnd1().getTipo(), Tipo_int.class)) {
        	m.emit(m.ig_int());
        }
        else if(claseDe(e.Opnd0().getTipo(), Tipo_real.class) || claseDe(e.Opnd1().getTipo(), Tipo_real.class)) {
        	m.emit(m.ig_real());
		}
        else if(claseDe(e.Opnd0().getTipo(), Tipo_bool.class) && claseDe(e.Opnd1().getTipo(), Tipo_bool.class)) {
			m.emit(m.ig_bool());
		}
        else if(claseDe(e.Opnd0().getTipo(), Tipo_string.class) && claseDe(e.Opnd1().getTipo(), Tipo_string.class)) {
			m.emit(m.ig_string());
		}
		else {
			m.emit(m.ig_indir());
		}
        //m.emit(m.ig());
    }

    public void procesa(Exp_dist e) {
        gen_cod_opnds(e.Opnd0(), e.Opnd1());
        if(claseDe(e.Opnd0().getTipo(), Tipo_int.class) && claseDe(e.Opnd1().getTipo(), Tipo_int.class)) {
        	m.emit(m.dist_int());
        }
        else if(claseDe(e.Opnd0().getTipo(), Tipo_real.class) || claseDe(e.Opnd1().getTipo(), Tipo_real.class)) {
        	m.emit(m.dist_real());
		}
        else if(claseDe(e.Opnd0().getTipo(), Tipo_bool.class) && claseDe(e.Opnd1().getTipo(), Tipo_bool.class)) {
			m.emit(m.dist_bool());
		}
        else if(claseDe(e.Opnd0().getTipo(), Tipo_string.class) && claseDe(e.Opnd1().getTipo(), Tipo_string.class)) {
			m.emit(m.dist_string());
		}
		else {
			m.emit(m.dist_indir());
		}
        //m.emit(m.dist());
    }

    public void procesa(Exp_suma e) {
        gen_cod_opnds(e.Opnd0(), e.Opnd1());
        if(claseDe(e.Opnd0().getTipo(), Tipo_int.class) && claseDe(e.Opnd1().getTipo(), Tipo_int.class)) {
        	m.emit(m.suma_int());
        }
        else if(claseDe(e.Opnd0().getTipo(), Tipo_real.class) || claseDe(e.Opnd1().getTipo(), Tipo_real.class)) {
        	m.emit(m.suma_real());
		}
    }

    public void procesa(Exp_resta e) {
        gen_cod_opnds(e.Opnd0(), e.Opnd1());
        if(claseDe(e.Opnd0().getTipo(), Tipo_int.class) && claseDe(e.Opnd1().getTipo(), Tipo_int.class)) {
        	m.emit(m.resta_int());
        }
        else if(claseDe(e.Opnd0().getTipo(), Tipo_real.class) || claseDe(e.Opnd1().getTipo(), Tipo_real.class)) {
        	m.emit(m.resta_real());
		}
    }

    public void procesa(Exp_and e) {
        gen_cod_opnds(e.Opnd0(), e.Opnd1());
        m.emit(m.and());
    }

    public void procesa(Exp_or e) {
        gen_cod_opnds(e.Opnd0(), e.Opnd1());
        m.emit(m.or());
    }

    public void procesa(Exp_mul e) {
        gen_cod_opnds(e.Opnd0(), e.Opnd1());
        if(claseDe(e.Opnd0().getTipo(), Tipo_int.class) && claseDe(e.Opnd1().getTipo(), Tipo_int.class)) {
        	m.emit(m.mul_int());
        }
        else if(claseDe(e.Opnd0().getTipo(), Tipo_real.class) || claseDe(e.Opnd1().getTipo(), Tipo_real.class)) {
        	m.emit(m.mul_real());
		}
    }

    public void procesa(Exp_div e) {
        gen_cod_opnds(e.Opnd0(), e.Opnd1());
        if(claseDe(e.Opnd0().getTipo(), Tipo_int.class) && claseDe(e.Opnd1().getTipo(), Tipo_int.class)) {
        	m.emit(m.div_int());
        }
        else if(claseDe(e.Opnd0().getTipo(), Tipo_real.class) || claseDe(e.Opnd1().getTipo(), Tipo_real.class)) {
        	m.emit(m.div_real());
		}
    }

    public void procesa(Exp_mod e) {
        gen_cod_opnds(e.Opnd0(), e.Opnd1());
        m.emit(m.mod());
    }

    public void procesa(Exp_menos e) {
        e.Opnd().procesa(this);
		gen_acc_val(e.Opnd());
		if(claseDe(e.Opnd().getTipo(), Tipo_int.class)) {
        	m.emit(m.menos_int());
        }
        else if(claseDe(e.Opnd().getTipo(), Tipo_real.class)) {
        	m.emit(m.menos_real());
		}
    }

    public void procesa(Exp_not e) {
        e.Opnd().procesa(this);
        gen_acc_val(e.Opnd());
        m.emit(m.not());
    }

    public void procesa(Exp_index e) {
    	e.Opnd0().procesa(this);
    	e.Opnd1().procesa(this);
    	gen_acc_val(e.Opnd1());
    	Tipo T = ref(e.Opnd0().getTipo());
        m.emit(m.apila_int(T.tipo().getTam()));
        m.emit(m.mul_int());
        m.emit(m.suma_int());
    }

    public void procesa(Exp_indir e) {
        e.Opnd().procesa(this);
        m.emit(m.apila_ind());
    }
    
    public void procesa(Exp_reg e) {
        e.Opnd().procesa(this);
        Tipo T = ref(e.Opnd().getTipo());
        m.emit(m.apila_int(desplaza_campo(T.lCampos(), e.ID())));
        m.emit(m.suma_int());
    }

    public void procesa(Exp_entero e) {
        m.emit(m.apila_int(Integer.valueOf(e.litEntero())));
    }

    public void procesa(Exp_real e) {
        m.emit(m.apila_real(Double.valueOf(e.litReal())));
    }

    public void procesa(Exp_true e) {
        m.emit(m.apila_bool(true));
    }

    public void procesa(Exp_false e) {
        m.emit(m.apila_bool(false));
    }

    public void procesa(Exp_cadena e) {
        m.emit(m.apila_string(String.valueOf(e.litCadena())));
    }

    public void procesa(Exp_iden e) {
    	if(claseDe(e.getVinculo(), Dec_base.class)) {
            gen_acc_id((Dec_base) e.getVinculo());
    	}
    	else if(claseDe(e.getVinculo(), Si_refparam_f.class)) {
            gen_acc_id((Si_refparam_f) e.getVinculo());
    	}
    	else if(claseDe(e.getVinculo(), No_refparam_f.class)) {
            gen_acc_id((No_refparam_f) e.getVinculo());
    	}
    }

    public void procesa(Exp_null e) {
        m.emit(m.apila_null());
    }
	
	// FUNCIONES AUXILIARES
	
	private void gen_acc_val(Exp E){
		if (es_designador(E)) m.emit(m.apila_ind());
	}
	
    private void gen_cod_opnds(Exp e1, Exp e2) {
        e1.procesa(this);
        gen_acc_val(e1);
        if(claseDe(e1, Tipo_int.class) && claseDe(e2, Tipo_real.class)) {
        	m.emit(m.int2real());
        }
        e2.procesa(this);
        gen_acc_val(e2);
        if(claseDe(e1, Tipo_real.class) && claseDe(e2, Tipo_int.class)) {
        	m.emit(m.int2real());
        }
    }

    private void gen_acc_id(Dec_base dec) { 
    	if (dec.getNivel() == 0) {
    		m.emit(m.apila_int(dec.getDir()));
    	}
    	else {
    		gen_acc_var(dec);
    	}
    }
    
    private void gen_acc_id(No_refparam_f paramf) {
    	gen_acc_var(paramf);
    }
    
    private void gen_acc_id(Si_refparam_f paramf) {
    	gen_acc_var(paramf);
    	m.emit(m.apila_ind());
    }

    private void gen_acc_var(Nodo v) {
        m.emit(m.apilad(v.getNivel()));
        m.emit(m.apila_int(v.getDir()));
        m.emit(m.suma_int());
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
	
	private void gen_paso_params(LParamRs paramRs, LParamFs paramFs) {
		if(claseDe(paramRs, Un_param_r.class) &&  claseDe(paramFs, Un_param_f.class)) {
			param_r_f(paramRs.exp(), paramFs.paramF());
		}
		else {
			param_r_f(paramRs.exp(), paramFs.paramF());
			gen_paso_params(paramRs.lParamRs(), paramFs.lParamFs());
		}
	}
	
	private void param_r_f(Exp exp, ParamF paramF) {
        m.emit(m.dup());
        m.emit(m.apila_int(paramF.getDir()));
        m.emit(m.suma_int());
        exp.procesa(this);
        if(claseDe(paramF, Si_refparam_f.class) || !es_designador(exp)) {
        	if(!es_designador(exp) && claseDe(paramF, Tipo_real.class) && claseDe(exp, Tipo_int.class)) {
        		m.emit(m.int2real());
        	}
        	m.emit(m.desapila_ind());
        }
        else {
        	Tipo T = ref(paramF.tipo());
        	if(claseDe(paramF, Tipo_real.class) && claseDe(exp, Tipo_int.class)) {
        		m.emit(m.copia_int2real(T.getTam()));
        	}
        	else {
            	m.emit(m.copia(T.getTam()));
        	}
        }
	}
    
    private int desplaza_campo(LCampos LCampos, String iden){
    	if(claseDe(LCampos, Un_campo.class)) {
			return LCampos.tipoNom().getDesp();
		}
		else{
			if(iden.equals(LCampos.tipoNom().ID())) {
				return LCampos.tipoNom().getDesp();
			}
			else {
				return desplaza_campo(LCampos.lCampos(), iden);
			}
		}
    }
	
}
