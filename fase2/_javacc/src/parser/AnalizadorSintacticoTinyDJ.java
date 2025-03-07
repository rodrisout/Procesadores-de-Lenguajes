package parser;

import java.io.Reader;

public class AnalizadorSintacticoTinyDJ extends AnalizadorSintacticoTiny {
    private void imprime(Token t) {
        switch(t.kind) {
            case bool_: System.out.println("<bool>"); break;
            case int_: System.out.println("<int>"); break;
            case real_: System.out.println("<real>"); break;
            case string_: System.out.println("<string>"); break;
            case and_: System.out.println("<and>"); break;
            case or_: System.out.println("<or>"); break;
            case not_: System.out.println("<not>"); break;
            case true_: System.out.println("<true>"); break;
            case false_: System.out.println("<false>"); break;
            case null_: System.out.println("<null>"); break;
            case proc_: System.out.println("<proc>"); break;
            case if_: System.out.println("<if>"); break;
            case else_: System.out.println("<else>"); break;
            case while_: System.out.println("<while>"); break;
            case struct_: System.out.println("<struct>"); break;
            case new_: System.out.println("<new>"); break;
            case delete_: System.out.println("<delete>"); break;
            case read_: System.out.println("<read>"); break;
            case write_: System.out.println("<write>"); break;
            case nl_: System.out.println("<nl>"); break;
            case type_: System.out.println("<type>"); break;
            case call_: System.out.println("<call>"); break;
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
