package procesa;

import asint.SintaxisAbstractaTiny;

public class AsignadorEspacio extends SintaxisAbstractaTiny {
	
    private int dir;
    private int max_dir;
    private int nivel;
    private int campos;

	public AsignadorEspacio() {
		this.dir = 0;
		this.max_dir = 0;
		this.nivel = 0;
	}
	
	private boolean claseDe(Object o, Class c) {
        return o == null ? false : o.getClass() == c;
    }   
	
	public void asig_espacio(Bloq bloq) {
        int dir_ant = dir;
        asig_espacio(bloq.secDecs());
        asig_espacio(bloq.secIs());
        dir = dir_ant;
    }
    // DECLARACIONES
    private void asig_espacio(SecDecs decs) {
    	if(claseDe(decs,Si_decs.class)) {
    		asig_espacio1(decs.lDecs());
    		asig_espacio2(decs.lDecs());
    	}
    }
    private void asig_espacio1(LDecs decs) {
    	if(claseDe(decs,Muchas_decs.class)) {
    		asig_espacio1(decs.lDecs());
    	}
    	asig_espacio1(decs.dec());
    }
    private void asig_espacio2(LDecs decs) {
    	if(claseDe(decs,Muchas_decs.class)) {
    		asig_espacio2(decs.lDecs());
    	}
    	asig_espacio2(decs.dec());
    }
    private void asig_espacio1(Dec dec) {
    	if(claseDe(dec,Dec_base.class)) {
    		asig_tam1(dec.tipoNom().tipo());
    		dec.setDir(dir);
    		dec.setNivel(nivel);
    		inc_dir(dec.tipoNom().tipo().getTam());
    	}
    	else if(claseDe(dec,Dec_type.class)) {
    		asig_tam1(dec.tipoNom().tipo());
//    		dec.setDir(dir);
//    		dec.setNivel(nivel);
//    		inc_dir(dec.tipoNom().tipo().getTam());
    	}
    	else if(claseDe(dec,Dec_proc.class)) {
    		int dir_ant = dir;
    		int max_dir_ant = max_dir;
    		nivel++;
    		dec.setNivel(nivel);
    		dir = 0;
    		max_dir = 0;
    		asig_espacio1(dec.paramFs());
    		asig_espacio2(dec.paramFs());
    		asig_espacio(dec.bloq());
    		dec.setTam(max_dir);
    		dir = dir_ant;
    		max_dir = max_dir_ant;
    		nivel--;
    	}
    }

	private void asig_espacio2(Dec dec) {
    	if(claseDe(dec,Dec_base.class)) {
    		asig_tam2(dec.tipoNom().tipo());
    	}
    	else if(claseDe(dec,Dec_type.class)) {
    		asig_tam2(dec.tipoNom().tipo());
    	}
    }
    private void asig_espacio1(ParamFs paramfs) {
    	if(claseDe(paramfs,Si_params_f.class)) {
    		asig_espacio1(paramfs.lParamFs());
    	}
    }
    private void asig_espacio2(ParamFs paramfs) {
    	if(claseDe(paramfs,Si_params_f.class)) {
    		asig_espacio2(paramfs.lParamFs());
    	}
    }
    private void asig_espacio1(LParamFs paramfs) {
    	if(claseDe(paramfs,Muchos_params_f.class)) {
    		asig_espacio1(paramfs.lParamFs());
    	}
    	asig_espacio1(paramfs.paramF());
    }
    private void asig_espacio2(LParamFs paramfs) {
    	if(claseDe(paramfs,Muchos_params_f.class)) {
    		asig_espacio2(paramfs.lParamFs());
    	}
    	asig_espacio2(paramfs.paramF());
    }
    private void asig_espacio1(ParamF paramf) {
    	asig_tam1(paramf.tipo());
    	paramf.setDir(dir);
    	paramf.setNivel(nivel);
    	if(claseDe(paramf, Si_refparam_f.class)) {
    		inc_dir(1);
		}
		else if(claseDe(paramf, No_refparam_f.class)) {
			inc_dir(paramf.tipo().getTam());
		}
    }
    private void asig_espacio2(ParamF paramf) {
    	asig_tam2(paramf.tipo());
    }
    // TIPOS
    private void asig_tam1(Tipo tipo) {
    	if(claseDe(tipo,Tipo_array.class)) {
    		asig_tam1(tipo.tipo());
    		tipo.setTam(tipo.tipo().getTam()*Integer.valueOf(tipo.litEntero()));
    	}
    	else if(claseDe(tipo,Tipo_indir.class)) {
    		if(!claseDe(tipo.tipo(), Tipo_type.class)){
    			asig_tam1(tipo.tipo());
    		}
    		tipo.setTam(1);
    	}
    	else if(claseDe(tipo,Tipo_struct.class)) {
    		int tmp = campos;
    		campos = 0;
    		asig_tam1(tipo.lCampos());
    		tipo.setTam(campos);
    		campos = tmp;
    	}
    	else if(claseDe(tipo,Tipo_int.class)) {
    		tipo.setTam(1);
    	}
    	else if(claseDe(tipo,Tipo_real.class)) {
    		tipo.setTam(1);
    	}
    	else if(claseDe(tipo,Tipo_bool.class)) {
    		tipo.setTam(1);
    	}
    	else if(claseDe(tipo,Tipo_string.class)) {
    		tipo.setTam(1);
    	}
    	else if(claseDe(tipo,Tipo_type.class)) {
    		tipo.setTam(((Dec_type) tipo.getVinculo()).tipoNom().tipo().getTam());
    	}
    }
    private void asig_tam2(Tipo tipo) {
    	if(claseDe(tipo,Tipo_indir.class)) {
    		if(claseDe(tipo.tipo(), Tipo_type.class)){
    			tipo.tipo().setTam(((Dec_type) tipo.tipo().getVinculo()).tipoNom().tipo().getTam());
    		}
    		else {
    			asig_tam2(tipo.tipo());
    		}
    	}
    	else if(claseDe(tipo,Tipo_struct.class)) {
    		asig_tam2(tipo.lCampos());
    	}
    }
    private void asig_tam1(LCampos campos) {
    	if(claseDe(campos,Muchos_campos.class)) {
    		asig_tam1(campos.lCampos());
    	}
    	campos.tipoNom().setDesp(this.campos);
    	asig_tam1(campos.tipoNom().tipo());
    	this.campos += campos.tipoNom().tipo().getTam();
    }
    private void asig_tam2(LCampos campos) {
    	if(claseDe(campos,Muchos_campos.class)) {
    		asig_tam2(campos.lCampos());
    	}
    	asig_tam2(campos.tipoNom().tipo());
    }
    // INSTRUCCIONES
    private void asig_espacio(SecIs ins) {
    	if(claseDe(ins,Si_ins.class)) {
    		asig_espacio(ins.lIs());
    	}
    }
    private void asig_espacio(LIs ins) {
    	if(claseDe(ins,Muchas_ins.class)) {
    		asig_espacio(ins.lIs());
    	}
    	asig_espacio(ins.i());
    }
    private void asig_espacio(I in) {
    	if(claseDe(in,Ins_if.class)) {
    		asig_espacio(in.bloq());
    	}
		else if(claseDe(in,Ins_if_else.class)) {
			asig_espacio(in.i());
			asig_espacio(in.bloq());
		}
		else if(claseDe(in,Ins_while.class)) {
			asig_espacio(in.bloq());
		}
		else if(claseDe(in,Ins_bloque.class)) {
			asig_espacio(in.bloq());
		}
    }

    
    private void inc_dir(int inc) {
		dir += inc;
		if(dir > max_dir) {
			max_dir = dir;
		}
	}

}
