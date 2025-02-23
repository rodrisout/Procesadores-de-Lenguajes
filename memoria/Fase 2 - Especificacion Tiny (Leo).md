# Especificacion Tiny  


### Seccion Programa  

programa -> bloque  
bloque -> { seccion_declaraciones_opt seccion_intrucciones_opt }  


### Seccion Tipos  

tipo_nombre -> tipo **identificador**  

tipo -> tipo_base  
tipo -> tipo [ **literalEntero** ]  

tipo_base -> ^ tipo_base  
tipo_base -> **int**  
tipo_base -> **real**  
tipo_base -> **bool**  
tipo_base -> **string**  
tipo_base -> **identificador**  
tipo_base -> **struct** { lista_campos }  
lista_campos -> lista_campos , tipo_nombre  
lista_campos -> tipo_nombre  


### Seccion Declaraciones  
seccion_declaraciones_opt -> seccion_declaraciones &&  
seccion_declaraciones_opt -> ε  

seccion_declaraciones -> seccion_declaraciones ; declaracion  
seccion_declaraciones -> declaracion  

declaracion -> tipo_nombre  
declaracion -> type tipo_nombre  
declaracion -> proc **identificador** parametros_formales bloque  

parametros_formales -> ( lista_parametros_opt )  
lista_parametros_opt -> lista_parametros  
lista_parametros_opt -> ε  
lista_parametros -> lista_parametros , parametro  
lista_parametros -> parametro  

parametro -> tipo & **identificador**  
parametro -> tipo **identificador**  


### Seccion Instrucciones  

seccion_intrucciones_opt -> seccion_intrucciones  
seccion_intrucciones_opt -> ε  

seccion_intrucciones -> lista_instrucciones  
lista_instrucciones -> lista_instrucciones ; instruccion  
lista_instrucciones -> instruccion  

instruccion -> @ expresion  
instruccion -> if expresion bloque  
instruccion -> if expresion bloque else bloque  
instruccion -> while expresion bloque  
instruccion -> read expresion  
instruccion -> write expresion  
instruccion -> nl  
instruccion -> new expresion  
instruccion -> delete expresion  
instruccion -> call **identificador** parametros_reales  
instruccion -> bloque  

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

