package asint.procesamientos;

private class Tabla {
	List<Map<String, SintaxisAbstractaTiny.Nodo>> ambitos;
	public Tabla() {
		this.ambitos = new List<>();
		this.ambitos.add(new Map<>());
	}

	public SintaxisAbstractaTiny.Nodo vinculoDe(String iden) {
		for (int i = this.ambitos.size(); i > 0; --i) {
			SintaxisAbstractaTiny.Nodo n =
				this.ambitos.get(i).find(iden);

			if (n != null)
				return n;
		}

		return null;
	}

	public void inserta(String iden, SintaxisAbstractaTiny.Node n) {
		this.ambitos.get(this.ambitos.size() - 1).insert(iden, n);
	}

	public boolean contiene(String iden) {
		return this.ambitos.get(this.ambitos.size() - 1).contains(iden);
	}

	public void abreAmbito() {
		this.ambitos.add(new Map<>());
	}

	public void cierraAmbito() {
		this.ambitos.pop();
	}
}

private class RecolectaDecs extends ProcesamientoDef {
	private Tabla tabla;
	public void RecolectaDecs(Tabla tabla) {
		this.tabla = tabla;
	}
	public void procesa(Si_decs decs) {
		decs.lDecs().procesa(this);
	}
	public void procesa(Muchas_decs decs) {
		decs.lDecs().procesa(this);
		decs.dec().procesa(this);
	}
	public void procesa(Una_dec dec) {
		dec.dec().procesa(this);
	}
	public void procesa(Dec_base dec) {
		dec.dec().procesa(this);
		if (this.tabla.contiene(dec.tipoNom().id()))
			System.out.println("ERROR TODO");
		else	this.tabla.inserta(dec.tipoNom().id(), dec);
	}
	public void procesa(Dec_type dec) {
		if (this.tabla.contiene(dec.tipoNom().id()))
			System.out.println("ERROR TODO");
		else	this.tabla.inserta(dec.tipoNom().id(), dec);
	}
	public void procesa(Dec_proc dec) {
		if (this.tabla.contiene(dec.id()))
			System.out.println("ERROR TODO");
		else	this.tabla.inserta(dec.id(), dec);
	}
}

public class Vinculacion extends ProcesamientoDef {
	private Tabla tabla;
	public void Vinculacion() {
		this.tabla = new Tabla();
	}
	public void procesa(Bloq bloq) {
		tabla.abreAmbito();
		bloq.secDecs().procesa(new RecolectaDecs(this.tabla));
		bloq.secDecs().procesa(this);
		bloq.secIs().procesa(this);
		tabla.cierraAmbito();
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
	public void procesa(Dec_base dec){
		dec.tipoNom().procesa(this);
	}
	public void procesa(Dec_type dec){
		dec.tipoNom().procesa(this);
	}
	public void procesa(Dec_proc dec){
		this.tabla.abreAmbito();
		if (this.tabla.contiene(dec.id()))
			System.out.println("ERROR TODO");
		else	this.tabla.inserta(dec.id(), dec)
		dec.paramFs().procesa(this);
		dec.bloq().procesa(this);
		this.tabla.cierraAmbito();
	}
	public void procesa(Si_params_f paramfs){
		paramfs.lParamFs().procesa(this);
	}
	public void procesa(No_params_f paramfs){}
	public void procesa(Muchos_params_f paramfs){
		paramfs.lParamFs().procesa(this);
		paramfs.paramF().procesa(this);
	}
	public void procesa(Un_param_f paramf){
		paramf.paramF().procesa(this);
	}
	public void procesa(Si_refparam_f paramf){
		paramf.tipo().procesa(this);
		if (this.tabla.contiene(paramf.id()))
			System.out.println("ERROR TODO");
		else	this.tabla.inserta(paramf.id(), paramf);
	}
	public void procesa(No_refparam_f paramf){
		paramf.tipo().procesa(this);
		if (this.tabla.contiene(paramf.id()))
			System.out.println("ERROR TODO");
		else	this.tabla.inserta(paramf.id(), paramf);
	}
	public void procesa(TipoNom tiponom){
		tiponom.tipo().procesa(this);
	}
	public void procesa(Tipo_array tipo){
		tipo.tipo().procesa(this);
		if (tipo.litEnteroInt() < 0)
			System.out.println("ERROR TODO");
	}
	public void procesa(Tipo_indir tipo){
		tipo.tipo().procesa(this);
	}
	public void procesa(Tipo_struct tipo){
	}
	public void procesa(Tipo_int tipo){}
	public void procesa(Tipo_real tipo){}
	public void procesa(Tipo_bool tipo){}
	public void procesa(Tipo_string tipo){}
	public void procesa(Tipo_type tipo){
		tipo.vinculo = this.tabla.vinculoDe(tipo.id());
		if (tipo.vinculo == null || !(tipo.vinculo instanceof Dec_type))
			System.out.println("ERROR TODO");
	}
	public void procesa(Muchos_campos campos){
		campos.lCampos().procesa(this);
		if (this.idens.contains(campos.tipoNom().id()))
			System.out.println("ERROR TODO");
		else	this.idens.add(idens, campos.tipoNom().id());
		campos.tipoNom().procesa(this);
	}
	public void procesa(Un_campo campo){
		campo.tipoNom().procesa(this);
		if (this.idens.contains(campo.tipoNom().id()))
			System.out.println("ERROR TODO");
	}
	public void procesa(Si_ins ins){
		ins.lIs.procesa(this);
	}
	public void procesa(No_ins ins){}
	public void procesa(Muchas_ins ins){
		ins.lIs().procesa(this);
		ins.i().procesa(this);
	}
	public void procesa(Una_ins in){
		in.i().procesa(this);
	}
	public void procesa(Ins_eval in){
		in.i().procesa(this);
	}
	public void procesa(Ins_if in){
		in.exp().procesa(this);
		in.bloq().procesa(this);
	}
	public void procesa(Ins_if_else in){
		in.i().procesa(this);
		in.bloq().procesa(this);
	}
	public void procesa(Ins_while in){
		in.exp().procesa(this);
		in.bloq().procesa(this);
	}
	public void procesa(Ins_read in){
		in.exp().procesa(this);
	}
	public void procesa(Ins_write in){
		in.exp().procesa(this);
	}
	public void procesa(Ins_nl in){
	}
	public void procesa(Ins_new in){
		in.exp().procesa(this);
	}
	public void procesa(Ins_delete in){
		in.exp().procesa(this);
	}
	public void procesa(Ins_call in){
		in.vinculo = this.tabla.vinculoDe(in.id());
		if (in.vinculo == null)
			System.out.println("ERROR TODO");
	}
	public void procesa(Ins_bloque in){
		in.bloq().procesa(this);
	}
	public void procesa(Si_params_r paramrs){
		paramrs.lParamRs().procesa(this);
	}
	public void procesa(No_params_r paramrs){
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
	}
	public void procesa(Exp_menor exp){
		exp.Opnd0().procesa(this);
		exp.Opnd1().procesa(this);
	}
	public void procesa(Exp_menor_ig exp){
		exp.Opnd0().procesa(this);
		exp.Opnd1().procesa(this);
	}
	public void procesa(Exp_mayor exp){
		exp.Opnd0().procesa(this);
		exp.Opnd1().procesa(this);
	}
	public void procesa(Exp_mayor_ig exp){
		exp.Opnd0().procesa(this);
		exp.Opnd1().procesa(this);
	}
	public void procesa(Exp_ig exp){
		exp.Opnd0().procesa(this);
		exp.Opnd1().procesa(this);
	}
	public void procesa(Exp_dist exp){
		exp.Opnd0().procesa(this);
		exp.Opnd1().procesa(this);
	}
	public void procesa(Exp_suma exp){
		exp.Opnd0().procesa(this);
		exp.Opnd1().procesa(this);
	}
	public void procesa(Exp_resta exp){
		exp.Opnd0().procesa(this);
		exp.Opnd1().procesa(this);
	}
	public void procesa(Exp_and exp){
		exp.Opnd0().procesa(this);
		exp.Opnd1().procesa(this);
	}
	public void procesa(Exp_or exp){
		exp.Opnd0().procesa(this);
		exp.Opnd1().procesa(this);
	}
	public void procesa(Exp_mul exp){
		exp.Opnd0().procesa(this);
		exp.Opnd1().procesa(this);
	}
	public void procesa(Exp_div exp){
		exp.Opnd0().procesa(this);
		exp.Opnd1().procesa(this);
	}
	public void procesa(Exp_mod exp){
		exp.Opnd0().procesa(this);
		exp.Opnd1().procesa(this);
	}
	public void procesa(Exp_menos exp){
		exp.Opnd().procesa(this);
	}
	public void procesa(Exp_not exp){
		exp.Opnd().procesa(this);
	}
	public void procesa(Exp_index exp){
		exp.Opnd().procesa(this);
	}
	public void procesa(Exp_reg exp){
		exp.Opnd().procesa(this);
	}
	public void procesa(Exp_indir exp){
		exp.Opnd().procesa(this);
	}
	public void procesa(Exp_entero exp){
	}
	public void procesa(Exp_real exp){
	}
	public void procesa(Exp_true exp){
	}
	public void procesa(Exp_false exp){
	}
	public void procesa(Exp_cadena exp){
	}
	public void procesa(Exp_iden exp){
	}
	public void procesa(Exp_null exp){
	}
}
