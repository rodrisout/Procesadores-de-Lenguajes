package asint;



public class ClaseSemanticaTiny extends SintaxisAbstractaTiny {
    public ClaseSemanticaTiny() {
        super();
    }
    public Exp mkopbin(String op, Exp opnd1, Exp opnd2) {
        switch(op) {
		    case "=": return exp_asig(opnd1,opnd2);
		    case "<": return exp_menor(opnd1,opnd2);
		    case "<=": return exp_menor_ig(opnd1,opnd2);
		    case ">": return exp_mayor(opnd1,opnd2);
            case ">=": return exp_mayor_ig(opnd1,opnd2);
            case "==": return exp_ig(opnd1,opnd2);
            case "!=": return exp_dist(opnd1,opnd2);
            case "+": return exp_suma(opnd1,opnd2);
            case "-": return exp_resta(opnd1,opnd2);
            case "<and>": return exp_and(opnd1,opnd2);
            case "<or>": return exp_or(opnd1,opnd2);
            case "*": return exp_mul(opnd1,opnd2);
            case "/": return exp_div(opnd1,opnd2);
            case "%": return exp_mod(opnd1,opnd2);
            default: throw new UnsupportedOperationException("Bad op");
        }
    }
}
