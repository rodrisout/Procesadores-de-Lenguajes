package asint;

import asint.SintaxisAbstractaTiny.Bloq;
import asint.SintaxisAbstractaTiny.Dec_base;
import asint.SintaxisAbstractaTiny.Dec_proc;
import asint.SintaxisAbstractaTiny.Dec_type;
import asint.SintaxisAbstractaTiny.Exp_null;
import asint.SintaxisAbstractaTiny.Muchas_decs;
import asint.SintaxisAbstractaTiny.No_decs;
import asint.SintaxisAbstractaTiny.No_params_f;
import asint.SintaxisAbstractaTiny.Si_decs;
import asint.SintaxisAbstractaTiny.Si_params_f;
import asint.SintaxisAbstractaTiny.Una_dec;

public interface Procesamiento {
	void procesa(Bloq bloq);
	void procesa(Si_decs decs);
	void procesa(No_decs decs); //No_decs haria falta??
	void procesa(Muchas_decs decs);
	void procesa(Una_dec dec);
	void procesa(Dec_base dec);
	void procesa(Dec_type dec);
	void procesa(Dec_proc dec);
	void procesa(Si_params_f paramfs);
	void procesa(No_params_f paramfs);
	//...
	// TODO incluir las clases de SintaxisAbstractaTiny
	//...
	void procesa(Exp_null exp);
}
