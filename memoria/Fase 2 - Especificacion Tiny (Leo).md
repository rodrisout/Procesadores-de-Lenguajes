# Especificacion Tiny

programa -> bloque  
bloque -> { seccion_declaraciones_opt seccion_intrucciones }  

### Seccion Declaraciones
seccion_declaraciones_opt -> seccion_declaraciones &&  
seccion_declaraciones_opt -> ε  
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
tipo0 -> ^ tipo0  
tipo0 -> tipo1  
tipo1 -> tipo1 tamano_opt  
tipo1 -> tipo_base  
tipo_base -> struct { lista_campos }  
tipo_base -> int  
tipo_base -> real  
tipo_base -> bool  
tipo_base -> string  
tipo_base -> **identificador**  
tamano_opt -> tamano  
tamano_opt -> ε  
tamano -> [ **literalEntero** ]  
lista_campos -> lista_campos , campo  
lista_campos -> campo  
campo -> tipo_nombre  
### Seccion Instrucciones  
seccion_intrucciones -> lista_instrucciones  
lista_instrucciones -> lista_instrucciones ; instruccion  
lista_instrucciones -> lista_instrucciones  
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
lista_expresiones -> lista_expresiones  
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
expresion_basica -> **bool**  
expresion_basica -> **string**  
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
