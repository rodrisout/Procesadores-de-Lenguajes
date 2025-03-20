package ast;

public abstract class AST {
    //
    // PROGRAM
    //
    class Prog {
        Bloq bloq;
        Prog(Bloq bloq) { this.bloq = bloq; }
    }

    class Bloq {
        LDec decl;
        LIns ins;
        Bloq(LDec decl, LIns ins) {
            this.decl = decl;
            this.ins = ins;
        }
    }

    //
    // DECLARATIONS
    //
    class Dec {}
    class LDec extends Dec {
        LDec prev;
        Dec decl;
        LDec(LDec prev, Dec decl) {
            this.decl = decl;
            this.prev = prev;
        }
    }

    class DecVar extends Dec {
        Tipo tipo;
        String ident;
        DecVar(Tipo tipo, String ident) {
            this.tipo = tipo;
            this.ident = ident;
        }
    }

    class DecTipo extends Dec {
        Tipo tipo;
        String ident;
        DecTipo(Tipo tipo, String ident) {
            this.tipo = tipo;
            this.ident = ident;
        }
    }

    class DecProc extends Dec {
        String ident;
        LParamF args;
        Bloq bloq;
        DecProc(String ident, LParamF args, Bloq bloq) {
            this.ident = ident;
            this.args = args;
            this.bloq = bloq;
        }
    }

    class LParamF {
        LParamF prev;
        ParamF param;
        LParamF(LParamF prev, ParamF param) {
            this.prev = prev;
            this.param = param;
        }
    }

    class ParamF {
        Tipo tipo;
        boolean isRef;
        String ident;
        ParamF(Tipo tipo, boolean isRef, String ident) {
            this.tipo = tipo;
            this.isRef = isRef;
            this.ident = ident;
        }
    }

    //
    // TYPES
    //
    class Tipo { }
    class TipoIndir extends Tipo {
        Tipo tipo;
        TipoIndir(Tipo tipo) {
            this.tipo = tipo;
        }
    }
    class TipoArray extends Tipo {
        Tipo tipo;
        int tam;
        TipoArray(Tipo tipo, int tam) {
            this.tipo = tipo;
            this.tam = tam;
        }
    }

    enum TTipoBasico { INT, REAL, BOOL, STRING };
    class TipoBasico extends Tipo {
        TTipoBasico t;
        TipoBasico(TTipoBasico t) { this.t = t; }
    }
    class TipoIdent extends Tipo {
        String ident;
        TipoIdent(String ident) {
            this.ident = ident;
        }
    }
    class TipoStruct extends Tipo {
        LCampos campos;
        TipoStruct(LCampos campos) {
            this.campos = campos;
        }
    }

    class Campo {
        Tipo tipo;
        String ident;
        Campo(Tipo tipo, String ident) {
            this.tipo = tipo;
            this.ident = ident;
        }
    }

    class LCampos {
        Campo campo;
        LCampos rest;
        LCampos(Campo campo, LCampos rest) {
            this.campo = campo;
            this.rest = rest;
        }
    }

    //
    // INSTRUCTIONS
    //
    class LIns {
        Ins instr;
        LIns rest;
        LIns(Ins instr, LIns rest) {

        }
    }

    class Ins { }
    class InsExp {
        Exp expr;
        InsExp(Exp expr) {
            this.expr = expr;
        }
    }
    class InsIfElse {
        Exp expr;
        Bloq bloqtrue, bloqfalse;
        InsIfElse(Exp expr, Bloq bloqtrue, Bloq bloqfalse) {
            this.expr = expr;
            this.bloqtrue = bloqtrue;
            this.bloqfalse = bloqfalse;
        }
    }
    class InsWhile {
        Exp expr;
        Bloq bloq;
        InsWhile(Exp expr, Bloq bloq) {
            this.expr = expr;
            this.bloq = bloq;
        }
    }

    enum TInsBuiltin { READ, WRITE, NL, NEW, DELETE };
    class InsBuiltin extends Ins {
        Exp expr = null;
        InsBuiltin(TInsBuiltin t, Exp expr) { this.expr = expr; }
    }

    class InsCall extends Ins {
        String ident;
        LParamR param;
        InsCall(String ident, LParamR param) {
            this.ident = ident;
            this.param = param;
        }
    }

    class LParamR {
        Exp expr;
        LParamR rest;
        LParamR(Exp expr, LParamR rest) {
            this.expr = expr;
            this.rest = rest;
        }
    }

    //
    // EXPRESIONS
    //
    class Exp { }
    enum TExpBin {
        SUMA, RESTA, MUL, DIV, MOD,
        MENOR, MENOR_IGUAL, MAYOR, MAYOR_IGUAL,
        IGUAL, DISTINTO, ASIG,
        AND, OR;
    };
    class ExpBin extends Exp {
        TExpBin tipo;
        Exp op0, op1;

        ExpBin(Exp op0, TExpBin tipo, Exp op1) {
            this.op0 = op0;
            this.tipo = tipo;
            this.op1 = op1;
        }
    }

    // unary expressions
    abstract class ExpUnary extends Exp { }
    class ExpNeg extends ExpUnary {
        Exp expr;
        ExpNeg(Exp expr) {
            this.expr = expr;
        }
    }

    class ExpNot extends ExpUnary {
        Exp expr;
        ExpNot(Exp expr) {
            this.expr = expr;
        }
    }

    class ExpIndirect extends ExpUnary {
        abstract class Modo { }
        class Campo extends Modo {
            String campo;
            Campo(String campo) { this.campo = campo; }
        }
        class Indice extends Modo {
            Exp expr;
            Indice(Exp expr) { this.expr = expr; }
        }
        class Deref extends Modo { }

        Exp expr;
        Modo mode;
        ExpIndirect(Exp expr, Modo mode) {
            this.expr = expr;
            this.mode = mode;
        }
    }

    class ExpBasic extends Exp {}
    class ExpBasicV<T> extends ExpBasic {
        T v;
        ExpBasicV(T v) { this.v = v; }
    }
    enum TExpBasicUni { TRUE, FALSE, NULL };
    class ExpBasicUni extends ExpBasic {
        TExpBasicUni t;
        ExpBasicUni(TExpBasicUni t) { this.t = t; } 
    }
    class ExpBasicIdent extends ExpBasic {
        String ident;
        ExpBasicIdent(String ident) { this.ident = ident; }
    }
}
