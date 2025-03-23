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

public class ProcesamientoDef implements Procesamiento {

	public void procesa(Bloq bloq) {}

	public void procesa(Si_decs decs) {}

	public void procesa(No_decs decs) {}

	public void procesa(Muchas_decs decs) {}

	public void procesa(Una_dec dec) {}

	public void procesa(Dec_base dec) {}

	public void procesa(Dec_type dec) {}

	public void procesa(Dec_proc dec) {}

	public void procesa(Si_params_f paramfs) {}

	public void procesa(No_params_f paramfs) {}
	
	//...
	// TODO incluir las clases de SintaxisAbstractaTiny
	//...

	public void procesa(Exp_null exp) {}

}
