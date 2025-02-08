package alex;

public enum ClaseLexica {
 BOOL("<bool>"), 
 INT("<int>"),
 REAL("<real>"),
 STRING("<string>"),
 AND("<and>"),
 OR("<or>"),
 NOT("<not>"),
 TRUE("<true>"),
 FALSE("<false>"),
 NULL("<null>"),
 PROC("<proc>"),
 IF("<if>"),
 ELSE("<else>"),
 WHILE("<while>"),
 STRUCT("<struct>"),
 NEW("<new>"),
 DELETE("<delete>"),
 READ("<read>"),
 WRITE("<write>"),
 NL("<nl>"),
 TYPE("<type>"),
 CALL("<call>"),
 LITENT, 
 LITREAL, 
 LITCADENA,
 IDEN,
 SUMA("+"),
 RESTA("-"),
 MUL("*"),
 DIV("/"),
 MOD("%"),
 MENOR("<"),
 MAYOR(">"),
 IGUAL("=="), 
 DISTINTO("!="),
 MENORIG("<="),
 MAYORIG(">="),
 ASIG("="),
 FINASIG("&&"),
 PAPERT("("), 
 PCIERRE(")"), 
 LLAPERT("{"), 
 LLCIERRE("}"), 
 PCOMA(";"),
 COMA(","),
 PUNTO("."),
 ARROBA("@"),
 INDIR("^"),
 PARAMREF("&"),
 CAPERT("["),
 CCIERRE("]"),
 EOF("EOF");
private String image;
public String getImage() {
     return image;
 }
 private ClaseLexica() {
     image = toString();
 }
 private ClaseLexica(String image) {
    this.image = image;  
 }

}
