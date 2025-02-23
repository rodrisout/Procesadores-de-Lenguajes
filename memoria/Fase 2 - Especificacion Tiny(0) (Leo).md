# Especificacion Tiny(0)

programa -> bloque  
bloque -> { seccion_declaraciones_opt seccion_intrucciones_opt }  
### Seccion Declaraciones
seccion_declaraciones_opt -> seccion_declaraciones &&  
seccion_declaraciones_opt -> ε  
seccion_intrucciones_opt -> seccion_intrucciones  
seccion_intrucciones_opt -> ε  
seccion_declaraciones -> seccion_declaraciones ; declaracion  
seccion_declaraciones -> declaracion  
declaracion -> tipo_nombre  

### Seccion Tipos  
tipo_nombre -> tipo_base **identificador**  
tipo_base -> **int**  
tipo_base -> **real**  
tipo_base -> **bool**  

### Seccion Instrucciones  
seccion_intrucciones -> lista_instrucciones  
lista_instrucciones -> lista_instrucciones ; instruccion  
lista_instrucciones -> instruccion  
instruccion -> @ expresion  

### Seccion Expresiones
expresion -> E0  
E0 -> E1 = E0  
E0 -> E1  
E1 -> E1 op_relacional E2  
E1 -> E2  
E2 -> E2 + E3  
E2 -> E3 - E3  
E2 -> E3  
E3 -> E4 and E3  
E3 -> E4 or E4  
E3 -> E4  
E4 -> E4 op_mult E5  
E4 -> E5  
E5 -> - E5  
E5 -> not E5  
E5 -> E6  
E6 -> expresion_basica  
E6 -> (E0)  
expresion_basica -> **literalEntero**  
expresion_basica -> **literalReal**  
expresion_basica -> **true**  
expresion_basica -> **false**  
expresion_basica -> **identificador**  
### Seccion Operandos  
op_relacional -> <  
op_relacional -> <=  
op_relacional -> >  
op_relacional -> >=  
op_relacional -> ==  
op_relacional -> !=  
op_mult -> *  
op_mult -> /  
op_mult -> %  

# Acondicionamiento descendente Tiny(0)

<b>

<span style="color:deeppink"> *pink* **==** ambos rec_izq y fac_com </span>  
<span style="color:crimson"> *red* **==** recursion a izq </span>  
<span style="color:gold"> *yellow* **==** factores comunes </span>  
<span style="color:royalblue"> *blue* **==** acondicionado </span>  

</b>

programa -> bloque  
bloque -> { seccion_declaraciones_opt seccion_intrucciones_opt }  

### Seccion Declaraciones
seccion_declaraciones_opt -> seccion_declaraciones &&  
seccion_declaraciones_opt -> ε  
seccion_intrucciones_opt -> seccion_intrucciones  
seccion_intrucciones_opt -> ε  
<span style="color:crimson"> 
~~seccion_declaraciones -> seccion_declaraciones ; declaracion~~  
~~seccion_declaraciones -> declaracion~~  
</span> 
<span style="color:royalblue"> 
seccion_declaraciones -> declaracion resto_sd  
resto_sd -> ; declaracion resto_sd  
resto_sd -> ε  
</span>
declaracion -> tipo_nombre  

### Seccion Tipos  

tipo_nombre -> tipo_base **identificador**  
tipo_base -> **int**  
tipo_base -> **real**  
tipo_base -> **bool**  

### Seccion Instrucciones  
seccion_intrucciones -> lista_instrucciones  
<span style="color:crimson"> 
~~lista_instrucciones -> lista_instrucciones ; instruccion~~  
~~lista_instrucciones -> instruccion~~  
</span>
<span style="color:royalblue"> 
lista_instrucciones -> instruccion resto_li  
resto_li -> ; instruccion resto_li  
resto_li -> ε  
</span>
instruccion -> @ expresion  

### Seccion Expresiones  

expresion -> E0  
<span style="color:gold">
~~E0 -> E1 = E0~~  
~~E0 -> E1~~  
</span>
<span style="color:royalblue">
E0 -> E1 resto_E0  
resto_E0 -> = E0  
resto_E0 -> ε  
</span>
<span style="color:crimson">
~~E1 -> E1 op_relacional E2~~  
~~E1 -> E2~~  
</span>
<span style="color:royalblue">
E1 -> E2 resto_E1  
resto_E1 -> op_relacional E2 resto_E1  
resto_E1 -> ε  
</span>
<span style="color:deeppink">
~~E2 -> E2 + E3~~  
~~E2 -> E3 - E3~~  
~~E2 -> E3~~  
</span>
<span style="color:crimson">
~~E2 -> E2 + E3~~  
~~E2 -> E3 resto_E2_F~~  
~~resto_E2_F -> - E3~~  
~~resto_E2_F -> ε~~  
</span>
<span style="color:royalblue">
E2 -> E3 resto_E2_F resto_E2_R  
resto_E2_R -> + E3 resto_E2_R  
resto_E2_R -> ε  
resto_E2_F -> - E3  
resto_E2_F -> ε  
</span>
<span style="color:gold">
~~E3 -> E4 and E3~~  
~~E3 -> E4 or E4~~  
~~E3 -> E4~~  
</span>
<span style="color:royalblue">
E3 -> E4 resto_E3  
resto_E3 -> and E3  
resto_E3 -> or E4  
resto_E3 -> ε  
</span>
<span style="color:crimson">
~~E4 -> E4 op_mult E5~~  
~~E4 -> E5~~  
</span>
<span style="color:royalblue">
E4 -> E5 resto_E4  
resto_E4 -> op_mult E5 resto_E4  
resto_E4 -> ε  
</span>
E5 -> - E5  
E5 -> not E5  
E5 -> E6  
E6 -> expresion_basica  
E6 -> (E0)  

expresion_basica -> **literalEntero**  
expresion_basica -> **literalReal**  
expresion_basica -> **true**  
expresion_basica -> **false**  
expresion_basica -> **identificador**  

### Seccion Operandos  
op_relacional -> <  
op_relacional -> <=  
op_relacional -> >  
op_relacional -> >=  
op_relacional -> ==  
op_relacional -> !=  
op_mult -> *  
op_mult -> /  
op_mult -> %  