package ast;

public class AST {
    public Programa programa;

    public class Programa {
        Bloque bloque;
        Programa(Bloque bloque) { this.bloque = bloque; }
    }

    public class Bloque {
        SeccionDeclaraciones decl;
        SeccionInstrucciones ins;
        Bloque(SeccionDeclaraciones decl, SeccionInstrucciones ins) {
            this.decl = decl;
            this.ins = ins;
        }
    }

    public class Declaracion {}
    public class SeccionDeclaraciones extends Declaracion {
        Declaracion decl;
        SeccionDeclaraciones rest;
        SeccionDeclaraciones(Declaracion decl, SeccionDeclaraciones rest) {
            this.decl = decl;
            this.rest = rest;
        }
    }

    public class DeclaracionVariable extends Declaracion {
        Tipo tipo;
        String ident;
        DeclaracionVariable(Tipo tipo, String ident) {
            this.tipo = tipo;
            this.ident = ident;
        }
    }

    public class DeclaracionTipo extends Declaracion {
        Tipo tipo;
        String ident;
        DeclaracionTipo(Tipo tipo, String ident) {
            this.tipo = tipo;
            this.ident = ident;
        }
    }

    public class DeclaracionProc extends Declaracion {
        String identificador;
        ParametrosFormales parametros;
        Bloque bloque;
        DeclaracionProc(String identificador, ParametrosFormales parametros, Bloque bloque) {
            this.identificador = identificador;
            this.parametros = parametros;
            this.bloque = bloque;
        }
    }

    public class ParametrosFormales {
        ListaParametros param;
        ParametrosFormales(ListaParametros param) {
            this.param = param;
        }
    }

    public class ListaParametros {
        Parametro param;
        ListaParametros rest;
        ListaParametros(Parametro param, ListaParametros rest) {
            this.param = param;
            this.rest = rest;
        }
    }

    public class Parametro {
        Tipo tipo;
        boolean isRef;
        String ident;
        Parametro(Tipo tipo, boolean isRef, String ident) {
            this.tipo = tipo;
            this.isRef = isRef;
            this.ident = ident;
        }
    }

    public class Tipo { }

    public class TipoIndir extends Tipo {
        Tipo tipo;
        TipoIndir(Tipo tipo) {
            this.tipo = tipo;
        }
    }
    public class TipoArray extends Tipo {
        Tipo tipo;
        int tam;
        TipoArray(Tipo tipo, int tam) {
            this.tipo = tipo;
            this.tam = tam;
        }
    }
    public class TipoInt extends Tipo { }
    public class TipoReal extends Tipo { }
    public class TipoBool extends Tipo { }
    public class TipoString extends Tipo { }
    public class TipoIdent extends Tipo { }
    public class TipoStruct extends Tipo {
        ListaCampos campos;
        TipoStruct(ListaCampos campos) {
            this.campos = campos;
        }
    }

    public class Campo {
        Tipo tipo;
        String ident;
        Campo(Tipo tipo, String ident) {
            this.tipo = tipo;
            this.ident = ident;
        }
    }

    public class ListaCampos {
        Campo campo;
        ListaCampos rest;
        ListaCampos(Campo campo, ListaCampos rest) {
            this.campo = campo;
            this.rest = rest;
        }
    }

    public class SeccionInstrucciones {
        Instruccion instr;
        SeccionInstrucciones rest;
        SeccionInstrucciones(Instruccion instr, SeccionInstrucciones rest) {

        }
    }

    public class Instruccion { }
    public class InstruccionExpr {
        Expresion expr;
        InstruccionExpr(Expresion expr) {
            this.expr = expr;
        }
    }
    public class InstruccionIf {
        InstruccionIf(Expresion expr, Bloque bloque) {

        }
    }
    public class InstruccionIfElse {
        Expresion expr;
        Bloque bloquetrue, bloquefalse;
        InstruccionIfElse(Expresion expr, Bloque bloquetrue, Bloque bloquefalse) {
            this.expr = expr;
            this.bloquetrue = bloquetrue;
            this.bloquefalse = bloquefalse;
        }
    }
    public class InstruccionWhile {
        Expresion expr;
        Bloque bloque;
        InstruccionWhile(Expresion expr, Bloque bloque) {
            this.expr = expr;
            this.bloque = bloque;
        }
    }
    public class InstruccionBuiltin extends Instruccion {
        enum TInstruccionBuiltin { READ, WRITE, NL, NEW, DELETE };
        Expresion expr = null;
        InstruccionBuiltin(TInstruccionBuiltin t, Expresion expr) { this.expr = expr; }
    }

    public class InstruccionCall extends Instruccion {
        String ident;
        Parametros param;
        InstruccionCall(String ident, Parametros param) {
            this.ident = ident;
            this.param = param;
        }
    }

    public class Parametros {
        Expresion expr;
        Parametros rest;
        Parametros(Expresion expr, Parametros rest) {
            this.expr = expr;
            this.rest = rest;
        }
    }

    public class Expresion { }
    public class ExpresionBin extends Expresion {
        public enum TExpresionBin {
            SUMA, RESTA, MUL, DIV, MOD,
            MENOR, MENOR_IGUAL, MAYOR, MAYOR_IGUAL,
            IGUAL, DISTINTO, ASIG,
            NOT, AND, OR;
        };

        TExpresionBin tipo;
        Expresion op0, op1;

        ExpresionBin(Expresion op0, TExpresionBin tipo, Expresion op1) {
            this.op0 = op0;
            this.tipo = tipo;
            this.op1 = op1;
        }
    }
    public class ExpresionUna extends Expresion { }
    public class ExpresionUnaNeg extends ExpresionUna {
        Expresion expr;
        ExpresionUnaNeg(Expresion expr) {
            this.expr = expr;
        }
    }
    public class ExpresionUnaIndir extends ExpresionUna {
        Expresion expr;
        ExpresionUnaIndir(Expresion expr) {
            this.expr = expr;
        }
    }
    public class ExpresionUnaIndex extends ExpresionUna {
        Expresion expr, expr_idx;
        ExpresionUnaIndex(Expresion expr, Expresion expr_idx) {
            this.expr = expr;
            this.expr_idx = expr_idx;
        }
    }
    public class ExpresionUnaField extends ExpresionUna {
        Expresion expr;
        String field;
        ExpresionUnaField(Expresion expr, String field) {
            this.expr = expr;
            this.field = field;
        }
    }

    public class ExpresionBasica extends Expresion {}
    public class ExpresionBasicaV<T> extends ExpresionBasica {
        T v;
        ExpresionBasicaV(T v) { this.v = v; }
    }
    public class ExpresionBasicaUni extends ExpresionBasica {
        enum TExpresionBasicaUni { TRUE, FALSE, NULL };
        TExpresionBasicaUni t;
        ExpresionBasicaUni(TExpresionBasicaUni t) { this.t = t; } 
    }
    public class ExpresionBasicaIdent extends ExpresionBasica {
        String ident;
        ExpresionBasicaIdent(String ident) { this.ident = ident; }
    }
}
