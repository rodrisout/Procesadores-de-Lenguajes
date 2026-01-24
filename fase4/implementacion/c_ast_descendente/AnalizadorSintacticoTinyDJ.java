package c_ast_descendente;

import java.io.Reader;


public class AnalizadorSintacticoTinyDJ extends AnalizadorSintacticoTiny {
    private void imprime(Token t) {
        switch(t.kind) {
        case bool: System.out.println("<bool>"); break;
        case INT: System.out.println("<int>"); break;
        case real: System.out.println("<real>"); break;
        case string: System.out.println("<string>"); break;
        case and: System.out.println("<and>"); break;
        case or: System.out.println("<or>"); break;
        case not: System.out.println("<not>"); break;
        case TRUE: System.out.println("<true>"); break;
        case FALSE: System.out.println("<false>"); break;
        case NULL: System.out.println("<null>"); break;
        case proc: System.out.println("<proc>"); break;
        case IF: System.out.println("<if>"); break;
        case ELSE: System.out.println("<else>"); break;
        case WHILE: System.out.println("<while>"); break;
        case struct: System.out.println("<struct>"); break;
        case NEW: System.out.println("<new>"); break;
        case delete: System.out.println("<delete>"); break;
        case read: System.out.println("<read>"); break;
        case write: System.out.println("<write>"); break;
        case nl: System.out.println("<nl>"); break;
        case type: System.out.println("<type>"); break;
        case call: System.out.println("<call>"); break;
        case suma: System.out.println("+"); break;
        case resta: System.out.println("-"); break;
        case mul: System.out.println("*"); break;
        case div: System.out.println("/"); break;
        case mod: System.out.println("%"); break;
        case menor: System.out.println("<"); break;
        case mayor: System.out.println(">"); break;
        case menorIgual: System.out.println("<="); break;
        case mayorIgual: System.out.println(">="); break;
        case igual: System.out.println("=="); break;
        case asig: System.out.println("="); break;
        case distinto: System.out.println("!="); break;
        case punto: System.out.println("."); break;
        case puntoComa: System.out.println(";"); break;
        case coma: System.out.println(","); break;
        case parenApert: System.out.println("("); break;
        case parenCierre: System.out.println(")"); break;
        case arroba: System.out.println("@"); break;
        case llaveApert: System.out.println("{"); break;
        case llaveCierre: System.out.println("}"); break;
        case corcheteApert: System.out.println("["); break;
        case corcheteCierre: System.out.println("]"); break;
        case indireccion: System.out.println("^"); break;
        case finalAsig: System.out.println("&&"); break;
        case paramRef: System.out.println("&"); break;
        case EOF: System.out.println("<EOF>"); break;
        default: System.out.println(t.image);
        }
    }
    
    public AnalizadorSintacticoTinyDJ(Reader r) {
        super(r);
        disable_tracing();
    }
    final protected void trace_token(Token t, String where) {
        imprime(t);
    }
}
