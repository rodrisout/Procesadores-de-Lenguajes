package maquinap;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import asint.SintaxisAbstractaTiny.Exp;
import asint.SintaxisAbstractaTiny.Tipo_int;
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
	   this.valor = valor.substring(1, valor.length() - 1);;
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
   private ISuma ISUMA;
   private class ISuma implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorInt(opnd1.valorInt()+opnd2.valorInt()));
         pc++;
      } 
      public String toString() {return "suma";};
   }
   private IResta IRESTA;
   private class IResta implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorInt(opnd1.valorInt()-opnd2.valorInt()));
         pc++;
      } 
      public String toString() {return "resta";};
   }
   private IMul IMUL;
   private class IMul implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorInt(opnd1.valorInt()*opnd2.valorInt()));
         pc++;
      } 
      public String toString() {return "mul";};
   }
   private IDiv IDIV;
   private class IDiv implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorInt(opnd1.valorInt()/opnd2.valorInt()));
         pc++;
      } 
      public String toString() {return "div";};
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
   private IMenos IMENOS;
   private class IMenos implements Instruccion {
      public void ejecuta() {
         Valor opnd = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorInt(-opnd.valorInt()));
         pc++;
      } 
      public String toString() {return "menos";};
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
   
   private IMenor IMENOR;
   private class IMenor implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorBool(opnd1.valorInt()<opnd2.valorInt()));
         pc++;
      } 
      public String toString() {return "menor";};
   }
   
   private IMenorIg IMENORIG;
   private class IMenorIg implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorBool(opnd1.valorInt()<=opnd2.valorInt()));
         pc++;
      } 
      public String toString() {return "menor-ig";};
   }
   
   private IMayor IMAYOR;
   private class IMayor implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorBool(opnd1.valorInt()>opnd2.valorInt()));
         pc++;
      } 
      public String toString() {return "mayor";};
   }
   
   private IMayorIg IMAYORIG;
   private class IMayorIg implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorBool(opnd1.valorInt()>=opnd2.valorInt()));
         pc++;
      } 
      public String toString() {return "mayor-ig";};
   }
   
   private IIg IIG;
   private class IIg implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorBool(opnd1.valorInt()==opnd2.valorInt()));
         pc++;
      } 
      public String toString() {return "ig";};
   }
   
   private IDist IDIST;
   private class IDist implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorBool(opnd1.valorInt()!=opnd2.valorInt()));
         pc++;
      } 
      public String toString() {return "dist";};
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
         pilaEvaluacion.push(null); 
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
      private boolean claseDe(Object o, Class c) {
  		return o == null ? false : o.getClass() == c;
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

   public Instruccion suma() {return ISUMA;}
   public Instruccion mul() {return IMUL;}
   public Instruccion and() {return IAND;}
   public Instruccion apila_int(int val) {return new IApilaInt(val);}
   public Instruccion apila_bool(boolean val) {return new IApilaBool(val);}
   public Instruccion apilad(int nivel) {return new IApilad(nivel);}
   public Instruccion apila_ind() {return IAPILAIND;}
   public Instruccion desapila_ind() {return IDESAPILAIND;}
   public Instruccion copia(int tam) {return new ICopia(tam);}
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
   public Instruccion resta() {return IRESTA;}
   public Instruccion div() {return IDIV;}
   public Instruccion mod() {return IMOD;}
   public Instruccion or() {return IOR;}
   public Instruccion menos() {return IMENOS;}
   public Instruccion not() {return INOT;}
   public Instruccion menor() {return IMENOR;}
   public Instruccion menor_ig() {return IMENORIG;}
   public Instruccion mayor() {return IMAYOR;}
   public Instruccion mayor_ig() {return IMAYORIG;}
   public Instruccion ig() {return IIG;}
   public Instruccion dist() {return IDIST;}
   public Instruccion desapila() {return IDESAPILA;}
   public Instruccion nl() {return INL;}
   public Instruccion salida_std() {return ISALIDASTD;}
   public Instruccion entrada_std(Exp E) {return new IEntradaSTD(E);}
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
      ISUMA = new ISuma();
      IMUL = new IMul();
      IAND = new IAnd();
      IAPILAIND = new IApilaind();
      IDESAPILAIND = new IDesapilaind();
      IIRIND = new IIrind();
      IDUP = new IDup();
      ISTOP = new IStop();
      IAPILANULL = new IApilaNull();
      IRESTA = new IResta();
      IDIV = new IDiv();
      IMOD = new IMod();
      IOR = new IOr();
      IMENOS = new IMenos();
      INOT = new INot();
      IMENOR = new IMenor();
      IMENORIG = new IMenorIg();
      IMAYOR = new IMayor();
      IMAYORIG = new IMayorIg();
      IIG = new IIg();
      IDIST = new IDist();
      IDESAPILA = new IDesapila();
      INL = new INl();
      ISALIDASTD = new ISalidaSTD();
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
