package ast;

public abstract class AST {
    //
    // PROGRAM
    //
    public class Prog {
        Bloq bloq;
        public Prog(Bloq bloq) { this.bloq = bloq; }
    }

    public class Bloq {
        LDec decl;
        LIns ins;
        public Bloq(LDec decl, LIns ins) {
            this.decl = decl;
            this.ins = ins;
        }
    }

    //
    // DECLARATIONS
    //
    public class Dec {}
    public class LDec {
        LDec prev;
        Dec decl;
        public LDec(LDec prev, Dec decl) {
            this.decl = decl;
            this.prev = prev;
        }
    }

    public class DecVar extends Dec {
        Tipo tipo;
        String ident;
        public DecVar(Tipo tipo, String ident) {
            this.tipo = tipo;
            this.ident = ident;
        }
    }

    public class DecTipo extends Dec {
        Tipo tipo;
        String ident;
        public DecTipo(Tipo tipo, String ident) {
            this.tipo = tipo;
            this.ident = ident;
        }
    }

    public class DecProc extends Dec {
        String ident;
        LParamF args;
        Bloq bloq;
        public DecProc(String ident, LParamF args, Bloq bloq) {
            this.ident = ident;
            this.args = args;
            this.bloq = bloq;
        }
    }

    public class LParamF {
        LParamF prev;
        ParamF param;
        public LParamF(LParamF prev, ParamF param) {
            this.prev = prev;
            this.param = param;
        }
    }

    public class ParamF {
        Tipo tipo;
        boolean isRef;
        String ident;
        public ParamF(Tipo tipo, boolean isRef, String ident) {
            this.tipo = tipo;
            this.isRef = isRef;
            this.ident = ident;
        }
    }

    //
    // TYPES
    //
    public class Tipo { }
    public class TipoIndir extends Tipo {
        Tipo tipo;
        public TipoIndir(Tipo tipo) {
            this.tipo = tipo;
        }
    }
    public class TipoArray extends Tipo {
        Tipo tipo;
        int tam;
        public TipoArray(Tipo tipo, int tam) {
            this.tipo = tipo;
            this.tam = tam;
        }
    }

    public enum TTipoBasico { INT, REAL, BOOL, STRING };
    public class TipoBasico extends Tipo {
        TTipoBasico t;
        public TipoBasico(TTipoBasico t) { this.t = t; }
    }
    public class TipoIdent extends Tipo {
        String ident;
        public TipoIdent(String ident) {
            this.ident = ident;
        }
    }
    public class TipoStruct extends Tipo {
        LCampo campos;
        public TipoStruct(LCampo campos) {
            this.campos = campos;
        }
    }

    public class Campo {
        Tipo tipo;
        String ident;
        public Campo(Tipo tipo, String ident) {
            this.tipo = tipo;
            this.ident = ident;
        }
    }

    public class LCampo {
        LCampo prev;
        Campo campo;
        public LCampo(LCampo prev, Campo campo) {
            this.prev = prev;
            this.campo = campo;
        }
    }

    //
    // INSTRUCTIONS
    //
    public class LIns {
        LIns prev;
        Ins instr;
        public LIns(LIns prev, Ins instr) {
            this.prev = prev;
            this.instr = instr;
        }
    }

    public class Ins { }
    public class InsExp extends Ins {
        Exp exp;
        public InsExp(Exp exp) {
            this.exp = exp;
        }
    }
    public class InsIfElse extends Ins {
        Exp exp;
        Bloq bloqtrue, bloqfalse;
        public InsIfElse(Exp exp, Bloq bloqtrue, Bloq bloqfalse) {
            this.exp = exp;
            this.bloqtrue = bloqtrue;
            this.bloqfalse = bloqfalse;
        }
    }
    public class InsWhile extends Ins {
        Exp exp;
        Bloq bloq;
        public InsWhile(Exp exp, Bloq bloq) {
            this.exp = exp;
            this.bloq = bloq;
        }
    }

    public enum TInsBuiltin { READ, WRITE, NL, NEW, DELETE };
    public class InsBuiltin extends Ins {
        Exp exp = null;
        public InsBuiltin(TInsBuiltin t, Exp exp) { this.exp = exp; }
    }

    public class InsCall extends Ins {
        String ident;
        LParamR param;
        public InsCall(String ident, LParamR param) {
            this.ident = ident;
            this.param = param;
        }
    }

    public class InsBloq extends Ins {
        Bloq bloq;
        public InsBloq(Bloq bloq) {
            this.bloq = bloq;
        }
    }

    public class LParamR {
        LParamR prev;
        Exp exp;
        public LParamR(LParamR prev, Exp exp) {
            this.prev = prev;
            this.exp = exp;
        }
    }

    //
    // expESIONS
    //
    public class Exp { }
    public enum TExpBin {
        SUMA, RESTA, MUL, DIV, MOD,
        MENOR, MENOR_IGUAL, MAYOR, MAYOR_IGUAL,
        IGUAL, DISTINTO, ASIG,
        AND, OR;
    };
    public class ExpBin extends Exp {
        TExpBin tipo;
        Exp op0, op1;

        public ExpBin(Exp op0, TExpBin tipo, Exp op1) {
            this.op0 = op0;
            this.tipo = tipo;
            this.op1 = op1;
        }
    }

    // unary expessions
    abstract public class ExpUnary extends Exp { }
    public class ExpNeg extends ExpUnary {
        Exp exp;
        public ExpNeg(Exp exp) {
            this.exp = exp;
        }
    }

    public class ExpNot extends ExpUnary {
        Exp exp;
        public ExpNot(Exp exp) {
            this.exp = exp;
        }
    }

    public class ExpIndirect extends Exp {
        Exp exp;
        public ExpIndirect(Exp exp) {
            this.exp = exp;
        }
    }

    public class ExpIndirectCampo extends ExpIndirect {
        String campo;
        public ExpIndirectCampo(Exp exp, String campo) {
            super(exp);
            this.campo = campo;
        }
    }

    public class ExpIndirectArray extends ExpIndirect {
        Exp index;
        public ExpIndirectArray(Exp exp, Exp index) {
            super(exp);
            this.index = index;
        }
    }

    public class ExpIndirectDeref extends ExpIndirect {
        public ExpIndirectDeref(Exp exp) { super(exp); }
    }

    public class ExpBasic extends Exp {}
    public class ExpBasicV<T> extends ExpBasic {
        T v;
        public ExpBasicV(T v) { this.v = v; }
    }
    public enum TExpBasicUni { TRUE, FALSE, NULL };
    public class ExpBasicUni extends ExpBasic {
        TExpBasicUni t;
        public ExpBasicUni(TExpBasicUni t) { this.t = t; } 
    }
    public class ExpBasicIdent extends ExpBasic {
        String ident;
        public ExpBasicIdent(String ident) { this.ident = ident; }
    }
}
