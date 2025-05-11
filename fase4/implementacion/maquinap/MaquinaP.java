package maquinap;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import asint.SintaxisAbstractaTiny.Exp;
import asint.SintaxisAbstractaTiny.Exp_dist;
import asint.SintaxisAbstractaTiny.Exp_ig;
import asint.SintaxisAbstractaTiny.Tipo_bool;
import asint.SintaxisAbstractaTiny.Tipo_error;
import asint.SintaxisAbstractaTiny.Tipo_indir;
import asint.SintaxisAbstractaTiny.Tipo_int;
import asint.SintaxisAbstractaTiny.Tipo_null;
import asint.SintaxisAbstractaTiny.Tipo_real;
import asint.SintaxisAbstractaTiny.Tipo_string;

public class MaquinaP {
	
   public static class EAccesoIlegitimo extends RuntimeException {} 
   public static class EAccesoAMemoriaNoInicializada extends RuntimeException {
      public EAccesoAMemoriaNoInicializada(int pc,int dir) {
         super("pinst:"+pc+" dir:"+dir); 
      } 
   } 
   public static class EAccesoFueraDeRango extends RuntimeException {} 
   
   private GestorMemoriaDinamica gestorMemoriaDinamica;
   private GestorPilaActivaciones gestorPilaActivaciones;
    
   private class Valor {
      public int valorInt() {throw new EAccesoIlegitimo();}  
      public boolean valorBool() {throw new EAccesoIlegitimo();}
      public String valorStr() {throw new EAccesoIlegitimo();}
      public double valorReal() {throw new EAccesoIlegitimo();}
   } 
   private class ValorInt extends Valor {
      private int valor;
      public ValorInt(int valor) {
         this.valor = valor; 
      }
      public int valorInt() {return valor;}
      public String toString() {
        return String.valueOf(valor);
      }
   }
   private class ValorNull extends Valor {
	      public ValorNull() {
	      }
//	      public int ValorNull() {return valor;}
//	      public String toString() {
//	        return String.valueOf(valor);
//	      }
	   }
   private class ValorBool extends Valor {
      private boolean valor;
      public ValorBool(boolean valor) {
         this.valor = valor; 
      }
      public boolean valorBool() {return valor;}
      public String toString() {
        return String.valueOf(valor);
      }
   }
   private class ValorStr extends Valor {
	   private String valor;
	   public ValorStr(String valor) {
		   if(valor.charAt(0) == '"' && valor.charAt(valor.length() - 1) == '"'){
			   this.valor = valor.substring(1, valor.length() - 1);
		   }
		   else {
			   this.valor = valor;
		   }
	   
	   }
	   public String valorStr() {return valor;}
	   public String toString() {
		  return String.valueOf(valor);
	   }
   }
   private class ValorReal extends Valor {
      private double valor;
      public ValorReal(double valor) {
         this.valor = valor; 
      }
      public double valorReal() {return valor;}
      public String toString() {
        return String.valueOf(valor);
      }
   }
   
   private List<Instruccion> codigoP;
   private Stack<Valor> pilaEvaluacion;
   private Valor[] datos; 
   private int pc;

   public interface Instruccion {
      void ejecuta();  
   }
   private ISumaInt ISUMAINT;
   private class ISumaInt implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         
         pilaEvaluacion.push(new ValorInt(opnd1.valorInt() + opnd2.valorInt()));
         pc++;
      } 
      public String toString() {return "suma_int";};
   }
   private ISumaReal ISUMAREAL;
   private class ISumaReal implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         
         pilaEvaluacion.push(new ValorReal(opnd1.valorReal() + opnd2.valorReal()));
         pc++;
      } 
      public String toString() {return "suma_real";};
   }
   private IRestaInt IRESTAINT;
   private class IRestaInt implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         
         pilaEvaluacion.push(new ValorInt(opnd1.valorInt() - opnd2.valorInt()));
         pc++;
      } 
      public String toString() {return "resta_int";};
   }
   private IRestaReal IRESTAREAL;
   private class IRestaReal implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         
         pilaEvaluacion.push(new ValorReal(opnd1.valorReal() - opnd2.valorReal()));
         pc++;
      } 
      public String toString() {return "resta_real";};
   }
   private IMulInt IMULINT;
   private class IMulInt implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         
         pilaEvaluacion.push(new ValorInt(opnd1.valorInt() * opnd2.valorInt()));
         pc++;
      } 
      public String toString() {return "mul_int";};
   }
   private IMulReal IMULREAL;
   private class IMulReal implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         
         pilaEvaluacion.push(new ValorReal(opnd1.valorReal() * opnd2.valorReal()));
         pc++;
      } 
      public String toString() {return "mul_real";};
   }
   private IDivInt IDIVINT;
   private class IDivInt implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         
         pilaEvaluacion.push(new ValorInt(opnd1.valorInt() / opnd2.valorInt()));
         pc++;
      } 
      public String toString() {return "div_int";};
   }
   private IDivReal IDIVREAL;
   private class IDivReal implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         
         pilaEvaluacion.push(new ValorReal(opnd1.valorReal() / opnd2.valorReal()));
         pc++;
      } 
      public String toString() {return "div_real";};
   }
   private IMod IMOD;
   private class IMod implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorInt(opnd1.valorInt()%opnd2.valorInt()));
         pc++;
      } 
      public String toString() {return "mod";};
   }
   private IMenosInt IMENOSINT;
   private class IMenosInt implements Instruccion {
      public void ejecuta() {
         Valor opnd = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorInt(-opnd.valorInt()));
         pc++;
      } 
      public String toString() {return "menos_int";};
   }
   private IMenosReal IMENOSREAL;
   private class IMenosReal implements Instruccion {
      public void ejecuta() {
         Valor opnd = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorReal(-opnd.valorReal()));
         pc++;
      } 
      public String toString() {return "menos_real";};
   }
   private IAnd IAND;
   private class IAnd implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorBool(opnd1.valorBool()&&opnd2.valorBool()));
         pc++;
      } 
      public String toString() {return "and";};
   }
   
   private IOr IOR;
   private class IOr implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorBool(opnd1.valorBool()||opnd2.valorBool()));
         pc++;
      } 
      public String toString() {return "or";};
   }
 
   private IMenorInt IMENORINT;
   private class IMenorInt implements Instruccion {
	   public void ejecuta() {
		   Valor opnd2 = pilaEvaluacion.pop(); 
		   Valor opnd1 = pilaEvaluacion.pop();
		   pilaEvaluacion.push(new ValorBool(opnd1.valorInt()<opnd2.valorInt()));
		   pc++;
	   } 
	   public String toString() {return "menor_int";};
   }
   
   private IMenorReal IMENORREAL;
   private class IMenorReal implements Instruccion {
	   public void ejecuta() {
		   Valor opnd2 = pilaEvaluacion.pop(); 
		   Valor opnd1 = pilaEvaluacion.pop();
		   pilaEvaluacion.push(new ValorBool(opnd1.valorReal()<opnd2.valorReal()));
		   pc++;
	   } 
	   public String toString() {return "menor_real";};
   }
   private IMenorBool IMENORBOOL;
   private class IMenorBool implements Instruccion {
	   public void ejecuta() {
		   Valor opnd2 = pilaEvaluacion.pop(); 
		   Valor opnd1 = pilaEvaluacion.pop();
	       Valor resultado = null;
		   if(!opnd1.valorBool()) { //first false
			   if(opnd2.valorBool()) { // second true
				   resultado = new ValorBool(true);
			   }
			   else { // second false
				   resultado = new ValorBool(false);
			   }
		   }
		   else { //first true
			   if(opnd2.valorBool()) { // second true
				   resultado = new ValorBool(false);
			   }
			   else { // second false
				   resultado = new ValorBool(false);
			   }
		   }
 		   pilaEvaluacion.push(resultado);
		   pc++;
	   } 
	   public String toString() {return "menor_bool";};
   }
   
   private IMenorString IMENORSTRING;
   private class IMenorString implements Instruccion {
	   public void ejecuta() {
		   Valor opnd2 = pilaEvaluacion.pop(); 
		   Valor opnd1 = pilaEvaluacion.pop();
	       Valor resultado = null;
		   int resul = opnd1.valorStr().compareTo(opnd2.valorStr());
		   if(resul < 0) {
			   resultado = new ValorBool(true);
		   }
		   else {
			   resultado = new ValorBool(false);
		   }
		   pilaEvaluacion.push(resultado);
		   pc++;
	   } 
	   public String toString() {return "menor_string";};
   }
   
   private IMenorIgInt IMENORIGINT;
   private class IMenorIgInt implements Instruccion {
	   public void ejecuta() {
		   Valor opnd2 = pilaEvaluacion.pop(); 
		   Valor opnd1 = pilaEvaluacion.pop();
		   pilaEvaluacion.push(new ValorBool(opnd1.valorInt()<=opnd2.valorInt()));
		   pc++;
	   } 
	   public String toString() {return "menor_ig_int";};
   }
   
   private IMenorIgReal IMENORIGREAL;
   private class IMenorIgReal implements Instruccion {
	   public void ejecuta() {
		   Valor opnd2 = pilaEvaluacion.pop(); 
		   Valor opnd1 = pilaEvaluacion.pop();
		   pilaEvaluacion.push(new ValorBool(opnd1.valorReal()<=opnd2.valorReal()));
		   pc++;
	   } 
	   public String toString() {return "menor_ig_real";};
   }
   private IMenorIgBool IMENORIGBOOL;
   private class IMenorIgBool implements Instruccion {
	   public void ejecuta() {
		   Valor opnd2 = pilaEvaluacion.pop(); 
		   Valor opnd1 = pilaEvaluacion.pop();
	       Valor resultado = null;
		   if(!opnd1.valorBool()) { //first false
			   if(opnd2.valorBool()) { // second true
				   resultado = new ValorBool(true);
			   }
			   else { // second false
				   resultado = new ValorBool(true);
			   }
		   }
		   else { //first true
			   if(opnd2.valorBool()) { // second true
				   resultado = new ValorBool(true);
			   }
			   else { // second false
				   resultado = new ValorBool(false);
			   }
		   }
 		   pilaEvaluacion.push(resultado);
		   pc++;
	   } 
	   public String toString() {return "menor_ig_bool";};
   }
   
   private IMenorIgString IMENORIGSTRING;
   private class IMenorIgString implements Instruccion {
	   public void ejecuta() {
		   Valor opnd2 = pilaEvaluacion.pop(); 
		   Valor opnd1 = pilaEvaluacion.pop();
	       Valor resultado = null;
		   int resul = opnd1.valorStr().compareTo(opnd2.valorStr());
		   if(resul <= 0) {
			   resultado = new ValorBool(true);
		   }
		   else {
			   resultado = new ValorBool(false);
		   }
		   pilaEvaluacion.push(resultado);
		   pc++;
	   } 
	   public String toString() {return "menor_ig_string";};
   }
  
   
   private IMayorInt IMAYORINT;
   private class IMayorInt implements Instruccion {
	   public void ejecuta() {
		   Valor opnd2 = pilaEvaluacion.pop(); 
		   Valor opnd1 = pilaEvaluacion.pop();
		   pilaEvaluacion.push(new ValorBool(opnd1.valorInt()>opnd2.valorInt()));
		   pc++;
	   } 
	   public String toString() {return "mayor_int";};
   }
   
   private IMayorReal IMAYORREAL;
   private class IMayorReal implements Instruccion {
	   public void ejecuta() {
		   Valor opnd2 = pilaEvaluacion.pop(); 
		   Valor opnd1 = pilaEvaluacion.pop();
		   pilaEvaluacion.push(new ValorBool(opnd1.valorReal()>opnd2.valorReal()));
		   pc++;
	   } 
	   public String toString() {return "mayor_real";};
   }
   private IMayorBool IMAYORBOOL;
   private class IMayorBool implements Instruccion {
	   public void ejecuta() {
		   Valor opnd2 = pilaEvaluacion.pop(); 
		   Valor opnd1 = pilaEvaluacion.pop();
	       Valor resultado = null;
		   if(!opnd1.valorBool()) { //first false
			   if(opnd2.valorBool()) { // second true
				   resultado = new ValorBool(false);
			   }
			   else { // second false
				   resultado = new ValorBool(false);
			   }
		   }
		   else { //first true
			   if(opnd2.valorBool()) { // second true
				   resultado = new ValorBool(false);
			   }
			   else { // second false
				   resultado = new ValorBool(true);
			   }
		   }
 		   pilaEvaluacion.push(resultado);
		   pc++;
	   } 
	   public String toString() {return "mayor_bool";};
   }
   
   private IMayorString IMAYORSTRING;
   private class IMayorString implements Instruccion {
	   public void ejecuta() {
		   Valor opnd2 = pilaEvaluacion.pop(); 
		   Valor opnd1 = pilaEvaluacion.pop();
	       Valor resultado = null;
		   int resul = opnd1.valorStr().compareTo(opnd2.valorStr());
		   if(resul > 0) {
			   resultado = new ValorBool(true);
		   }
		   else {
			   resultado = new ValorBool(false);
		   }
		   pilaEvaluacion.push(resultado);
		   pc++;
	   } 
	   public String toString() {return "mayor_string";};
   }
   
   private IMayorIgInt IMAYORIGINT;
   private class IMayorIgInt implements Instruccion {
	   public void ejecuta() {
		   Valor opnd2 = pilaEvaluacion.pop(); 
		   Valor opnd1 = pilaEvaluacion.pop();
		   pilaEvaluacion.push(new ValorBool(opnd1.valorInt()>=opnd2.valorInt()));
		   pc++;
	   } 
	   public String toString() {return "mayor_ig_int";};
   }
   
   private IMayorIgReal IMAYORIGREAL;
   private class IMayorIgReal implements Instruccion {
	   public void ejecuta() {
		   Valor opnd2 = pilaEvaluacion.pop(); 
		   Valor opnd1 = pilaEvaluacion.pop();
		   pilaEvaluacion.push(new ValorBool(opnd1.valorReal()>=opnd2.valorReal()));
		   pc++;
	   } 
	   public String toString() {return "mayor_ig_real";};
   }
   private IMayorIgBool IMAYORIGBOOL;
   private class IMayorIgBool implements Instruccion {
	   public void ejecuta() {
		   Valor opnd2 = pilaEvaluacion.pop(); 
		   Valor opnd1 = pilaEvaluacion.pop();
	       Valor resultado = null;
		   if(!opnd1.valorBool()) { //first false
			   if(opnd2.valorBool()) { // second true
				   resultado = new ValorBool(false);
			   }
			   else { // second false
				   resultado = new ValorBool(true);
			   }
		   }
		   else { //first true
			   if(opnd2.valorBool()) { // second true
				   resultado = new ValorBool(true);
			   }
			   else { // second false
				   resultado = new ValorBool(true);
			   }
		   }
 		   pilaEvaluacion.push(resultado);
		   pc++;
	   } 
	   public String toString() {return "mayor_ig_bool";};
   }
   
   private IMayorIgString IMAYORIGSTRING;
   private class IMayorIgString implements Instruccion {
	   public void ejecuta() {
		   Valor opnd2 = pilaEvaluacion.pop(); 
		   Valor opnd1 = pilaEvaluacion.pop();
	       Valor resultado = null;
		   int resul = opnd1.valorStr().compareTo(opnd2.valorStr());
		   if(resul >= 0) {
			   resultado = new ValorBool(true);
		   }
		   else {
			   resultado = new ValorBool(false);
		   }
		   pilaEvaluacion.push(resultado);
		   pc++;
	   } 
	   public String toString() {return "mayor_ig_string";};
   }
   
   private IIgInt IIGINT;
   private class IIgInt implements Instruccion {
	   public void ejecuta() {
		   Valor opnd2 = pilaEvaluacion.pop(); 
		   Valor opnd1 = pilaEvaluacion.pop();
		   pilaEvaluacion.push(new ValorBool(opnd1.valorInt()==opnd2.valorInt()));
		   pc++;
	   } 
	   public String toString() {return "ig_int";};
   }
   
   private IIgReal IIGREAL;
   private class IIgReal implements Instruccion {
	   public void ejecuta() {
		   Valor opnd2 = pilaEvaluacion.pop(); 
		   Valor opnd1 = pilaEvaluacion.pop();
		   pilaEvaluacion.push(new ValorBool(opnd1.valorReal()==opnd2.valorReal()));
		   pc++;
	   } 
	   public String toString() {return "ig_real";};
   }
   private IIgBool IIGBOOL;
   private class IIgBool implements Instruccion {
	   public void ejecuta() {
		   Valor opnd2 = pilaEvaluacion.pop(); 
		   Valor opnd1 = pilaEvaluacion.pop();
	       Valor resultado = null;
		   if(!opnd1.valorBool()) { //first false
			   if(opnd2.valorBool()) { // second true
				   resultado = new ValorBool(false);
			   }
			   else { // second false
				   resultado = new ValorBool(true);
			   }
		   }
		   else { //first true
			   if(opnd2.valorBool()) { // second true
				   resultado = new ValorBool(true);
			   }
			   else { // second false
				   resultado = new ValorBool(false);
			   }
		   }
 		   pilaEvaluacion.push(resultado);
		   pc++;
	   } 
	   public String toString() {return "ig_bool";};
   }
   
   private IIgString IIGSTRING;
   private class IIgString implements Instruccion {
	   public void ejecuta() {
		   Valor opnd2 = pilaEvaluacion.pop(); 
		   Valor opnd1 = pilaEvaluacion.pop();
	       Valor resultado = null;
		   int resul = opnd1.valorStr().compareTo(opnd2.valorStr());
		   if(resul == 0) {
			   resultado = new ValorBool(true);
		   }
		   else {
			   resultado = new ValorBool(false);
		   }
		   pilaEvaluacion.push(resultado);
		   pc++;
	   } 
	   public String toString() {return "ig_string";};
   }
   
   private IIgIndir IIGINDIR;
   private class IIgIndir implements Instruccion {
	   public void ejecuta() {
		   Valor opnd2 = pilaEvaluacion.pop(); 
		   Valor opnd1 = pilaEvaluacion.pop();
	       Valor resultado = null;
	       if(claseDe(opnd1, ValorInt.class) && claseDe(opnd2, ValorInt.class)) {
			   resultado = new ValorBool(opnd1.valorInt()==opnd2.valorInt());
	       }
	       else if(claseDe(opnd1, ValorNull.class) && claseDe(opnd2, ValorNull.class)){
	    	   resultado = new ValorBool(true);
	       }
	       else {
	    	   resultado = new ValorBool(false);
	       }
		   pilaEvaluacion.push(resultado);
		   pc++;
	   } 
	   public String toString() {return "ig_indir";};
   }
   
   private IDistInt IDISTINT;
   private class IDistInt implements Instruccion {
	   public void ejecuta() {
		   Valor opnd2 = pilaEvaluacion.pop(); 
		   Valor opnd1 = pilaEvaluacion.pop();
		   pilaEvaluacion.push(new ValorBool(opnd1.valorInt()!=opnd2.valorInt()));
		   pc++;
	   } 
	   public String toString() {return "dist_int";};
   }
   
   private IDistReal IDISTREAL;
   private class IDistReal implements Instruccion {
	   public void ejecuta() {
		   Valor opnd2 = pilaEvaluacion.pop(); 
		   Valor opnd1 = pilaEvaluacion.pop();
		   pilaEvaluacion.push(new ValorBool(opnd1.valorReal()!=opnd2.valorReal()));
		   pc++;
	   } 
	   public String toString() {return "dist_real";};
   }
   private IDistBool IDISTBOOL;
   private class IDistBool implements Instruccion {
	   public void ejecuta() {
		   Valor opnd2 = pilaEvaluacion.pop(); 
		   Valor opnd1 = pilaEvaluacion.pop();
	       Valor resultado = null;
		   if(!opnd1.valorBool()) { //first false
			   if(opnd2.valorBool()) { // second true
				   resultado = new ValorBool(true);
			   }
			   else { // second false
				   resultado = new ValorBool(false);
			   }
		   }
		   else { //first true
			   if(opnd2.valorBool()) { // second true
				   resultado = new ValorBool(false);
			   }
			   else { // second false
				   resultado = new ValorBool(true);
			   }
		   }
 		   pilaEvaluacion.push(resultado);
		   pc++;
	   } 
	   public String toString() {return "dist_bool";};
   }
   
   private IDistString IDISTSTRING;
   private class IDistString implements Instruccion {
	   public void ejecuta() {
		   Valor opnd2 = pilaEvaluacion.pop(); 
		   Valor opnd1 = pilaEvaluacion.pop();
	       Valor resultado = null;
		   int resul = opnd1.valorStr().compareTo(opnd2.valorStr());
		   if(resul != 0) {
			   resultado = new ValorBool(true);
		   }
		   else {
			   resultado = new ValorBool(false);
		   }
		   pilaEvaluacion.push(resultado);
		   pc++;
	   } 
	   public String toString() {return "dist_string";};
   }
   
   private IDistIndir IDISTINDIR;
   private class IDistIndir implements Instruccion {
	   public void ejecuta() {
		   Valor opnd2 = pilaEvaluacion.pop(); 
		   Valor opnd1 = pilaEvaluacion.pop();
	       Valor resultado = null;
	       if(claseDe(opnd1, ValorInt.class) && claseDe(opnd2, ValorInt.class)) {
			   resultado = new ValorBool(opnd1.valorInt()!=opnd2.valorInt());
	       }
	       else if(claseDe(opnd1, ValorNull.class) && claseDe(opnd2, ValorNull.class)){
	    	   resultado = new ValorBool(false);
	       }
	       else {
	    	   resultado = new ValorBool(true);
	       }
		   pilaEvaluacion.push(resultado);
		   pc++;
	   } 
	   public String toString() {return "dist_indir";};
   }
   
   private INot INOT;
   private class INot implements Instruccion {
      public void ejecuta() {
         Valor opnd = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorBool(!opnd.valorBool()));
         pc++;
      } 
      public String toString() {return "not";};
   }
   
   private class IApilaInt implements Instruccion {
      private int valor;
      public IApilaInt(int valor) {
        this.valor = valor;  
      }
      public void ejecuta() {
         pilaEvaluacion.push(new ValorInt(valor)); 
         pc++;
      } 
      public String toString() {return "apila-int("+valor+")";};
   }
   
   private class IApilaBool implements Instruccion {
	   private boolean valor;
	   public IApilaBool(boolean valor) {
		   this.valor = valor;  
	   }
	   public void ejecuta() {
		   pilaEvaluacion.push(new ValorBool(valor)); 
		   pc++;
	   } 
	   public String toString() {return "apila-bool("+valor+")";};
   }

   private class IApilaString implements Instruccion {
      private String valor;
      public IApilaString(String valor) {
        this.valor = valor;  
      }
      public void ejecuta() {
         pilaEvaluacion.push(new ValorStr(valor)); 
         pc++;
      } 
      public String toString() {return "apila-string("+valor+")";};
   }
   
   private class IApilaReal implements Instruccion {
      private double valor;
      public IApilaReal(double valor) {
        this.valor = valor;  
      }
      public void ejecuta() {
         pilaEvaluacion.push(new ValorReal(valor)); 
         pc++;
      } 
      public String toString() {return "apila-real("+valor+")";};
   }
   
   private IApilaNull IAPILANULL;
   private class IApilaNull implements Instruccion {
      public void ejecuta() {
         pilaEvaluacion.push(new ValorNull()); 
         pc++;
      } 
      public String toString() {return "apila-null";};
   }
   
   private INl INL;
   private class INl implements Instruccion {
      public void ejecuta() {
    	 System.out.print('\n');
         pc++;
      } 
      public String toString() {return "nl";};
   }
   
   private ISalidaSTD ISALIDASTD;
   private class ISalidaSTD implements Instruccion {
      public void ejecuta() {
    	 String str = pilaEvaluacion.pop().toString(); //?
    	 System.out.print(str);
         pc++;
      } 
      public String toString() {return "salida-std";};
   }
   
   private class IEntradaSTD implements Instruccion {
      private Exp exp;
      public IEntradaSTD(Exp exp) {
        this.exp = exp;  
      }
      public void ejecuta() {
    	 if(claseDe(exp.getTipo(), Tipo_int.class)) {
    		 pilaEvaluacion.push(new ValorInt(input.nextInt())); 
    		 input.nextLine();
    	 }
    	 else if(claseDe(exp.getTipo(), Tipo_real.class)) {
    		 pilaEvaluacion.push(new ValorReal(input.nextDouble())); 
    		 input.nextLine();
    	 }
    	 else if(claseDe(exp.getTipo(), Tipo_string.class)) {
    		 pilaEvaluacion.push(new ValorStr(input.nextLine())); 
    	 }
         pc++;
      } 
      public String toString() {return "entrada-std";};
   }
   
   private class IIrA implements Instruccion {
      private int dir;
      public IIrA(int dir) {
        this.dir = dir;  
      }
      public void ejecuta() {
            pc=dir;
      } 
      public String toString() {return "ir-a("+dir+")";};
   }

   private class IIrF implements Instruccion {
      private int dir;
      public IIrF(int dir) {
        this.dir = dir;  
      }
      public void ejecuta() {
         if(! pilaEvaluacion.pop().valorBool()) { 
            pc=dir;
         }   
         else {
            pc++; 
         }
      } 
      public String toString() {return "ir-f("+dir+")";};
   }
   
   private class ICopia implements Instruccion {
      private int tam;
      public ICopia(int tam) {
        this.tam = tam;  
      }
      public void ejecuta() {
            int dirOrigen = pilaEvaluacion.pop().valorInt();
            int dirDestino = pilaEvaluacion.pop().valorInt();
            if ((dirOrigen + (tam-1)) >= datos.length)
                throw new EAccesoFueraDeRango();
            if ((dirDestino + (tam-1)) >= datos.length)
                throw new EAccesoFueraDeRango();
            for (int i=0; i < tam; i++) 
                datos[dirDestino+i] = datos[dirOrigen+i]; 
            pc++;
      } 
      public String toString() {return "copia("+tam+")";};
   }
   
   private class ICopiaInt2Real implements Instruccion {
	      private int tam;
	      public ICopiaInt2Real(int tam) {
	        this.tam = tam;  
	      }
	      public void ejecuta() {
	            int dirOrigen = pilaEvaluacion.pop().valorInt();
	            int dirDestino = pilaEvaluacion.pop().valorInt();
	            if ((dirOrigen + (tam-1)) >= datos.length)
	                throw new EAccesoFueraDeRango();
	            if ((dirDestino + (tam-1)) >= datos.length)
	                throw new EAccesoFueraDeRango();
	            for (int i=0; i < tam; i++) {
	            	datos[dirDestino+i] = new ValorReal((double) datos[dirOrigen+i].valorInt()); 
	            }
	                
	            pc++;
	      } 
	      public String toString() {return "copia("+tam+")";};
	   }
   
   private IApilaind IAPILAIND;
   private class IApilaind implements Instruccion {
      public void ejecuta() {
        int dir = pilaEvaluacion.pop().valorInt();
        if (dir >= datos.length) throw new EAccesoFueraDeRango();
        if (datos[dir] == null)  throw new EAccesoAMemoriaNoInicializada(pc,dir);
        pilaEvaluacion.push(datos[dir]);
        pc++;
      } 
      public String toString() {return "apila-ind";};
   }

   private IDesapila IDESAPILA;
   private class IDesapila implements Instruccion {
      public void ejecuta() {
        pilaEvaluacion.pop();
        pc++;
      } 
      public String toString() {return "desapila";};
   }
   
   private IDesapilaind IDESAPILAIND;
   private class IDesapilaind implements Instruccion {
      public void ejecuta() {
        Valor valor = pilaEvaluacion.pop();
        int dir = pilaEvaluacion.pop().valorInt();
        if (dir >= datos.length) throw new EAccesoFueraDeRango();
        datos[dir] = valor;
        pc++;
      } 
      public String toString() {return "desapila-ind";};
   }

   private class IAlloc implements Instruccion {
      private int tam;
      public IAlloc(int tam) {
        this.tam = tam;  
      }
      public void ejecuta() {
        int inicio = gestorMemoriaDinamica.alloc(tam);
        pilaEvaluacion.push(new ValorInt(inicio));
        pc++;
      } 
      public String toString() {return "alloc("+tam+")";};
   }

   private class IDealloc implements Instruccion {
      private int tam;
      public IDealloc(int tam) {
        this.tam = tam;  
      }
      public void ejecuta() {
        int inicio = pilaEvaluacion.pop().valorInt();
        gestorMemoriaDinamica.free(inicio,tam);
        pc++;
      } 
      public String toString() {return "dealloc("+tam+")";};
   }
   
   private class IActiva implements Instruccion {
       private int nivel;
       private int tamdatos;
       private int dirretorno;
       public IActiva(int nivel, int tamdatos, int dirretorno) {
           this.nivel = nivel;
           this.tamdatos = tamdatos;
           this.dirretorno = dirretorno;
       }
       public void ejecuta() {
          int base = gestorPilaActivaciones.creaRegistroActivacion(tamdatos);
          datos[base] = new ValorInt(dirretorno);
          datos[base+1] = new ValorInt(gestorPilaActivaciones.display(nivel));
          pilaEvaluacion.push(new ValorInt(base+2));
          pc++;
       }
       public String toString() {
          return "activa("+nivel+","+tamdatos+","+dirretorno+")";                 
       }
   }
   
   private class IDesactiva implements Instruccion {
       private int nivel;
       private int tamdatos;
       public IDesactiva(int nivel, int tamdatos) {
           this.nivel = nivel;
           this.tamdatos = tamdatos;
       }
       public void ejecuta() {
          int base = gestorPilaActivaciones.liberaRegistroActivacion(tamdatos);
          gestorPilaActivaciones.fijaDisplay(nivel,datos[base+1].valorInt());
          pilaEvaluacion.push(datos[base]); 
          pc++;
       }
       public String toString() {
          return "desactiva("+nivel+","+tamdatos+")";                 
       }

   }
   
   private class IDesapilad implements Instruccion {
       private int nivel;
       public IDesapilad(int nivel) {
         this.nivel = nivel;  
       }
       public void ejecuta() {
         gestorPilaActivaciones.fijaDisplay(nivel,pilaEvaluacion.pop().valorInt());  
         pc++;
       }
       public String toString() {
          return "desapilad("+nivel+")";                 
       }
   }
   private IDup IDUP;
   private class IDup implements Instruccion {
       public void ejecuta() {
           pilaEvaluacion.push(pilaEvaluacion.peek());
           pc++;
       }
       public String toString() {
          return "dup";                 
       }
   }
   private Instruccion ISTOP;
   private class IStop implements Instruccion {
       public void ejecuta() {
           pc = codigoP.size();
       }
       public String toString() {
          return "stop";                 
       }
   }
   
   private class IApilad implements Instruccion {
       private int nivel;
       public IApilad(int nivel) {
         this.nivel = nivel;  
       }
       public void ejecuta() {
         pilaEvaluacion.push(new ValorInt(gestorPilaActivaciones.display(nivel)));
         pc++;
       }
       public String toString() {
          return "apilad("+nivel+")";                 
       }

   }
   
   private Instruccion IIRIND;
   private class IIrind implements Instruccion {
       public void ejecuta() {
          pc = pilaEvaluacion.pop().valorInt();  
       }
       public String toString() {
          return "ir-ind";                 
       }
   }
   
   private Instruccion IINT2REAL;
   private class Int2Real implements Instruccion {
       public void ejecuta() {
          int valor = pilaEvaluacion.pop().valorInt();  
          pilaEvaluacion.push(new ValorReal((double) valor));
       }
       public String toString() {
          return "int2real";                 
       }
   }

   public Instruccion suma_int() {return ISUMAINT;}
   public Instruccion suma_real() {return ISUMAREAL;}
   public Instruccion mul_int() {return IMULINT;}
   public Instruccion mul_real() {return IMULREAL;}
   public Instruccion and() {return IAND;}
   public Instruccion apila_int(int val) {return new IApilaInt(val);}
   public Instruccion apila_bool(boolean val) {return new IApilaBool(val);}
   public Instruccion apilad(int nivel) {return new IApilad(nivel);}
   public Instruccion apila_ind() {return IAPILAIND;}
   public Instruccion desapila_ind() {return IDESAPILAIND;}
   public Instruccion copia(int tam) {return new ICopia(tam);}
   public Instruccion copia_int2real(int tam) {return new ICopiaInt2Real(tam);}
   public Instruccion ir_a(int dir) {return new IIrA(dir);}
   public Instruccion ir_f(int dir) {return new IIrF(dir);}
   public Instruccion ir_ind() {return IIRIND;}
   public Instruccion alloc(int tam) {return new IAlloc(tam);} 
   public Instruccion dealloc(int tam) {return new IDealloc(tam);} 
   public Instruccion activa(int nivel,int tam, int dirretorno) {return new IActiva(nivel,tam,dirretorno);}
   public Instruccion desactiva(int nivel, int tam) {return new IDesactiva(nivel,tam);}
   public Instruccion desapilad(int nivel) {return new IDesapilad(nivel);}
   public Instruccion dup() {return IDUP;}
   public Instruccion stop() {return ISTOP;}
   public Instruccion apila_string(String val) {return new IApilaString(val);}
   public Instruccion apila_real(double val) {return new IApilaReal(val);}
   public Instruccion apila_null() {return IAPILANULL;}
   public Instruccion resta_int() {return IRESTAINT;}
   public Instruccion resta_real() {return IRESTAREAL;}
   public Instruccion div_int() {return IDIVINT;}
   public Instruccion div_real() {return IDIVREAL;}
   public Instruccion mod() {return IMOD;}
   public Instruccion or() {return IOR;}
   public Instruccion menos_int() {return IMENOSINT;}
   public Instruccion menos_real() {return IMENOSREAL;}
   public Instruccion not() {return INOT;}
   public Instruccion menor_int() {return IMENORINT;}
   public Instruccion menor_real() {return IMENORREAL;}
   public Instruccion menor_bool() {return IMENORBOOL;}
   public Instruccion menor_string() {return IMENORSTRING;}
   public Instruccion menor_ig_int() {return IMENORIGINT;}
   public Instruccion menor_ig_real() {return IMENORIGREAL;}
   public Instruccion menor_ig_bool() {return IMENORIGBOOL;}
   public Instruccion menor_ig_string() {return IMENORIGSTRING;}
   public Instruccion mayor_int() {return IMAYORINT;}
   public Instruccion mayor_real() {return IMAYORREAL;}
   public Instruccion mayor_bool() {return IMAYORBOOL;}
   public Instruccion mayor_string() {return IMAYORSTRING;}
   public Instruccion mayor_ig_int() {return IMAYORIGINT;}
   public Instruccion mayor_ig_real() {return IMAYORIGREAL;}
   public Instruccion mayor_ig_bool() {return IMAYORIGBOOL;}
   public Instruccion mayor_ig_string() {return IMAYORIGSTRING;}
   public Instruccion ig_int() {return IIGINT;}
   public Instruccion ig_real() {return IIGREAL;}
   public Instruccion ig_bool() {return IIGBOOL;}
   public Instruccion ig_string() {return IIGSTRING;}
   public Instruccion ig_indir() {return IIGINDIR;}
   public Instruccion dist_int() {return IDISTINT;}
   public Instruccion dist_real() {return IDISTREAL;}
   public Instruccion dist_bool() {return IDISTBOOL;}
   public Instruccion dist_string() {return IDISTSTRING;}
   public Instruccion dist_indir() {return IDISTINDIR;}
   public Instruccion desapila() {return IDESAPILA;}
   public Instruccion nl() {return INL;}
   public Instruccion salida_std() {return ISALIDASTD;}
   public Instruccion entrada_std(Exp E) {return new IEntradaSTD(E);}
   public Instruccion int2real() {return IINT2REAL;}
   public void emit(Instruccion i) {
      codigoP.add(i); 
   }

   private int tamdatos;
   private int tamheap;
   private int ndisplays;
   private Scanner input;
   
   public MaquinaP(Reader input, int tamdatos, int tampila, int tamheap, int ndisplays) {
	  this.input = new Scanner(input);
      this.tamdatos = tamdatos;
      this.tamheap = tamheap;
      this.ndisplays = ndisplays;
      this.codigoP = new ArrayList<>();  
      pilaEvaluacion = new Stack<>();
      datos = new Valor[tamdatos+tampila+tamheap];
      this.pc = 0;
      ISUMAINT = new ISumaInt();
      ISUMAREAL = new ISumaReal();
      IMULINT = new IMulInt();
      IMULREAL = new IMulReal();
      IAND = new IAnd();
      IAPILAIND = new IApilaind();
      IDESAPILAIND = new IDesapilaind();
      IIRIND = new IIrind();
      IDUP = new IDup();
      ISTOP = new IStop();
      IAPILANULL = new IApilaNull();
      IRESTAINT = new IRestaInt();
      IRESTAREAL = new IRestaReal();
      IDIVINT = new IDivInt();
      IDIVREAL = new IDivReal();
      IMOD = new IMod();
      IOR = new IOr();
      IMENOSINT = new IMenosInt();
      IMENOSREAL = new IMenosReal();
      INOT = new INot();
      IMENORINT = new IMenorInt();
      IMENORREAL = new IMenorReal();
      IMENORBOOL = new IMenorBool();
      IMENORSTRING = new IMenorString();
      IMENORIGINT = new IMenorIgInt();
      IMENORIGREAL = new IMenorIgReal();
      IMENORIGBOOL = new IMenorIgBool();
      IMENORIGSTRING = new IMenorIgString();
      IMAYORINT = new IMayorInt();
      IMAYORREAL = new IMayorReal();
      IMAYORBOOL = new IMayorBool();
      IMAYORSTRING = new IMayorString();
      IMAYORIGINT = new IMayorIgInt();
      IMAYORIGREAL = new IMayorIgReal();
      IMAYORIGBOOL = new IMayorIgBool();
      IMAYORIGSTRING = new IMayorIgString();
      IIGINT = new IIgInt();
      IIGREAL = new IIgReal();
      IIGBOOL = new IIgBool();
      IIGSTRING = new IIgString();
      IIGINDIR = new IIgIndir();
      IDISTINT = new IDistInt();
      IDISTREAL = new IDistReal();
      IDISTBOOL = new IDistBool();
      IDISTSTRING = new IDistString();
      IDISTINDIR = new IDistIndir();
      IDESAPILA = new IDesapila();
      INL = new INl();
      ISALIDASTD = new ISalidaSTD();
      IINT2REAL = new Int2Real();
      this.gestorMemoriaDinamica = new GestorMemoriaDinamica(tamdatos+tampila, tamdatos+tampila+tamheap);
      this.gestorPilaActivaciones = new GestorPilaActivaciones(tamdatos, tamdatos+tampila, ndisplays);
   }
   public void ejecuta() {
      while(pc != codigoP.size()) {
          codigoP.get(pc).ejecuta();
      } 
   }
   public void muestraCodigo() {
     System.out.println("CodigoP");
     for(int i=0; i < codigoP.size(); i++) {
        System.out.println(" "+i+":"+codigoP.get(i));
     }
   }
   public void muestraEstado() {
     System.out.println("Tam datos:"+tamdatos);  
     System.out.println("Tam heap:"+tamheap); 
     System.out.println("PP:"+gestorPilaActivaciones.pp());      
     System.out.print("Displays:");
     for (int i=1; i <= ndisplays; i++)
         System.out.print(i+":"+gestorPilaActivaciones.display(i)+" ");
     System.out.println();
     System.out.println("Pila de evaluacion");
     for(int i=0; i < pilaEvaluacion.size(); i++) {
        System.out.println(" "+i+":"+pilaEvaluacion.get(i));
     }
     System.out.println("Datos");
     for(int i=0; i < datos.length; i++) {
        System.out.println(" "+i+":"+datos[i]);
     }
     System.out.println("PC:"+pc);
   }
   
   private boolean claseDe(Object o, Class c) {
 		return o == null ? false : o.getClass() == c;
 	  }  
   
//   public static void main(String[] args) {
//       MaquinaP m = new MaquinaP(5,10,10,2);
//        
//          /*
//            int x;
//            proc store(int v) {
//             x = v
//            }
//           &&
//            call store(5)
//          */
//            
//       
//       m.emit(m.activa(1,1,8));
//       m.emit(m.dup());
//       m.emit(m.apila_int(0));
//       m.emit(m.suma());
//       m.emit(m.apila_int(5));
//       m.emit(m.desapila_ind());
//       m.emit(m.desapilad(1));
//       m.emit(m.ir_a(9));
//       m.emit(m.stop());
//       m.emit(m.apila_int(0));
//       m.emit(m.apilad(1));
//       m.emit(m.apila_int(0));
//       m.emit(m.suma());
//       m.emit(m.copia(1));
//       m.emit(m.desactiva(1,1));
//       m.emit(m.ir_ind());       
//       m.muestraCodigo();
//       m.ejecuta();
//       m.muestraEstado();
//   }
}
