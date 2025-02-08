package alex;

public enum ClaseLexica {
 IDEN,
 LIT_ENT,
 LIT_REAL,
 INT("<int>"), 
 REAL("<real>"),
 BOOL("<bool>"), 
 TRUE("<true>"),
 FALSE("<false>"),
 AND("<and>"),
 OR("<or>"),
 NOT("<not>"),
 PAP("("), 
 PCIERRE(")"),
 LLAVEAP("{"),
 LLAVECIERRE("}"), 
 MAS("+"), 
 MENOS("-"), 
 MUL("*"), 
 DIV("/"), 
 MENOR("<"),
 MAYOR(">"),
 MENOR_IGUAL("<="),
 MAYOR_IGUAL(">="),
 IGUAL("=="), 
 ASIG("="),
 ARROBA("@"),
 PCOMA(";"),
 FINAL_ASIG("&&"),
 DISTINTO("!="),
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
