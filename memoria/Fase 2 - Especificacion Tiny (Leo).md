# Especificacion Tiny

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
declaracion -> type tipo_nombre  
declaracion -> proc **identificador** parametros_formales bloque  
parametros_formales -> ( lista_parametros_opt )  
lista_parametros_opt - > lista_parametros  
lista_parametros_opt - > ε  
lista_parametros -> lista_parametros , parametro  
lista_parametros -> parametro  
parametro -> tipo ref_opt **identificador**  
ref_opt -> &  
ref_opt -> ε  
### Seccion Tipos  
tipo_nombre -> tipo **identificador**  
tipo ->  tipo0  
tipo0 -> array tipo0  
tipo0 -> tipo1  
tipo1 -> ^ tipo1  
tipo1 -> tipo_base  
tipo_base -> **struct** { lista_campos }  
tipo_base -> **int**  
tipo_base -> **real**  
tipo_base -> **bool**  
tipo_base -> **string**  
tipo_base -> **identificador**  
array -> tipo_base tamano_opt  
tamano_opt -> tamano  
tamano_opt -> ε  
tamano -> [ **literalEntero** ]  
lista_campos -> lista_campos , campo  
lista_campos -> campo  
campo -> tipo_nombre  
### Seccion Instrucciones  
seccion_intrucciones -> lista_instrucciones  
lista_instrucciones -> lista_instrucciones ; instruccion  
lista_instrucciones -> instruccion  
instruccion -> @ expresion  
instruccion -> if_ins  
instruccion -> if_ins else_ins  
instruccion -> while exp_bloque  
instruccion -> read expresion  
instruccion -> write expresion  
instruccion -> nl  
instruccion -> new expresion  
instruccion -> delete expresion  
instruccion -> call **identificador** parametros_reales  
instruccion -> bloque  
if_ins -> if exp_bloq  
else_ins -> else bloque  
exp_bloq -> expresion bloque  
parametros_reales -> ( lista_expresiones_opt )  
lista_expresiones_opt -> lista_expresiones  
lista_expresiones_opt -> ε  
lista_expresiones -> lista_expresiones , expresion  
lista_expresiones -> expresion  
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
E6 -> E6 op_dirs  
E6 -> E7  
E7 -> expresion_basica  
E7 -> (E0)  
expresion_basica -> **literalEntero**  
expresion_basica -> **literalReal**  
expresion_basica -> **true**  
expresion_basica -> **false**  
expresion_basica -> **literalCadena**  
expresion_basica -> **identificador**  
expresion_basica -> **null**  
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
op_dirs -> [ expresion ]  
op_dirs -> . **identificador**  
op_dirs -> ^  

# Acondicionamiento descendente Tiny

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
declaracion -> type tipo_nombre  
declaracion -> proc **identificador** parametros_formales bloque  
parametros_formales -> ( lista_parametros_opt )  
lista_parametros_opt - > lista_parametros  
lista_parametros_opt - > ε  
<span style="color:crimson"> 
~~lista_parametros -> lista_parametros , parametro~~  
~~lista_parametros -> parametro~~  
</span>
<span style="color:royalblue"> 
lista_parametros -> parametro resto_lp  
resto_lp -> , parametro resto_lp  
resto_lp -> ε  
</span>
parametro -> tipo ref_opt **identificador**  
ref_opt -> &  
ref_opt -> ε  
### Seccion Tipos  

tipo_nombre -> tipo **identificador**  
tipo ->  tipo0  
tipo0 -> array tipo0  
tipo0 -> tipo1  
tipo1 -> ^ tipo1  
tipo1 -> tipo_base  
tipo_base -> **struct** { lista_campos }  
tipo_base -> **int**  
tipo_base -> **real**  
tipo_base -> **bool**  
tipo_base -> **string**  
tipo_base -> **identificador**  
array -> tipo_base tamano_opt  
tamano_opt -> tamano  
tamano_opt -> ε  
tamano -> [ **literalEntero** ]  
<span style="color:crimson"> 
~~lista_campos -> lista_campos , campo~~  
~~lista_campos -> campo~~  
</span>
<span style="color:royalblue">
lista_campos -> campo resto_lc  
resto_lc -> , campo resto_lc  
resto_lc -> ε  
</span>  
campo -> tipo_nombre  

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
<span style="color:gold">
~~instruccion -> if_ins~~  
~~instruccion -> if_ins else_ins~~  
</span>
<span style="color:royalblue">
instruccion -> if_ins resto_ii  
resto_ii -> else_ins  
resto_ii -> ε  
</span>
instruccion -> while exp_bloque  
instruccion -> read expresion  
instruccion -> write expresion  
instruccion -> nl  
instruccion -> new expresion  
instruccion -> delete expresion  
instruccion -> call **identificador** parametros_reales  
instruccion -> bloque  
if_ins -> if exp_bloq  
else_ins -> else bloque  
exp_bloq -> expresion bloque  
parametros_reales -> ( lista_expresiones_opt )  
lista_expresiones_opt -> lista_expresiones  
lista_expresiones_opt -> ε  
<span style="color:crimson"> 
~~lista_expresiones -> lista_expresiones , expresion~~  
~~lista_expresiones -> expresion~~  
</span>
<span style="color:royalblue"> 
lista_expresiones -> expresion resto_le  
resto_le -> , expresion resto_le  
resto_le -> ε  
</span>
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
<span style="color:crimson">
~~E6 -> E6 op_dirs~~  
~~E6 -> E7~~  
</span>
<span style="color:royalblue"> 
E6 -> E7 resto_E6  
resto_E6 -> op_dirs resto_E6  
resto_E6 -> ε  
</span>
E7 -> expresion_basica  
E7 -> (E0)  

expresion_basica -> **literalEntero**  
expresion_basica -> **literalReal**  
expresion_basica -> **true**  
expresion_basica -> **false**  
expresion_basica -> **literalCadena**  
expresion_basica -> **identificador**  
expresion_basica -> **null**  

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
op_dirs -> [ expresion ]  
op_dirs -> . **identificador**  
op_dirs -> ^  