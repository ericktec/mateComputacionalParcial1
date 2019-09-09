package afd_equivalentes;

import java.util.StringTokenizer;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author erickdavidmendozavelasco
 */
public class AFD_equivalentes {

	public static String[][] AFNtoAFD(String[][] AFN) {
		String[][] AFD = new String[AFN.length*2][AFN[0].length];
		for (int i = 0; i < AFD.length; i++) {
			for (int j = 0; j < AFD[i].length; j++) {
				AFD[i][j] = "";
			}
		}
		Queue<String> queue = new LinkedList<>();
		LinkedList<String> finalizado = new LinkedList<>();
		StringTokenizer st;
		String strTemporal;
		int disponible = 1; 
		int tokens;
		for (int i = 0; i < AFD[1].length; i++) {
			AFD[0][i] = AFN[0][i];
		}
		queue.add(AFN[1][0]);

		while(!queue.isEmpty()) {

			AFD[disponible][0]=queue.remove();
			finalizado.add(AFD[disponible][0]);

			st = new StringTokenizer(AFD[disponible][0],"/");
			tokens = st.countTokens();

			for (int i = 0; i < tokens; i++) {
				strTemporal = st.nextToken();
				if(i < tokens-1) {
					for (int j = 0; j < AFN.length; j++) {
						if(strTemporal.equals(AFN[j][0])) {
							for (int k = 1;  k < AFN[0].length; k++) {
								AFD[disponible][k] += AFN[j][k]+"/";
							}
						}
					}
				}
				else {
					for (int j = 0; j < AFN.length; j++) {
						if(strTemporal.equals(AFN[j][0])) {
							for (int k = 1;  k < AFN[0].length; k++) {


								AFD[disponible][k] += AFN[j][k];
                                                                AFD[disponible][k] = eliminarCeros(AFD[disponible][k]);

                                                                
                                                                

								if(queue.contains(AFD[disponible][k]) == finalizado.contains(AFD[disponible][k])){
									queue.add(AFD[disponible][k]);
								}
							}
						}
					}
				}
			}
			disponible++;

		}

		int contadorArray=0;
		for (int i = 0; i < AFD.length; i++) {
			if(!(AFD[i][0].compareTo("")==0)) {
				contadorArray++;
			}
		}
		String[][] ultimo = new String[contadorArray][AFD[0].length];
		for (int i = 0; i < contadorArray; i++) {
			for (int j = 0; j < AFD[i].length; j++) {
				ultimo[i][j]=AFD[i][j];
			}
		}
		AFD=ultimo;

		for (int i = 0; i < AFD.length; i++) {
			for (int j = 0; j < AFD[i].length; j++) {
				System.out.print("["+AFD[i][j]+"]");
			}
			System.out.println();
		}
                System.out.println("Convertido a AFD\n");
		
		return AFD;
	}
        
private static String eliminarCeros(String cadena) {
		StringTokenizer st= new StringTokenizer(cadena,"/"),
				stContador = new StringTokenizer(cadena,"/");
		int tokens=st.countTokens();
		String resultado="";
		int iteraciones = 0;

		for (int i = 0; i < tokens; i++) {
			String tmp=stContador.nextToken();
			//			System.out.println(tmp);
			if(!tmp.equals("0")) {
				if(!resultado.contains(tmp)) {
					resultado+=tmp;
					iteraciones++;
//					System.out.println("iteracion");
				}
			}
		}
		resultado="";
		if(tokens == iteraciones || tokens == 1) {
			return cadena;
		}
		else if(iteraciones == 0){
			return "0";
		}
		else {
			for (int i = 0; i < tokens; i++) {
				String tmp = st.nextToken();
				if(!tmp.equals("0")) {
					if(!resultado.contains(tmp)) {
						if(iteraciones != 1) {
							resultado += tmp+"/";
							iteraciones--;
						}
						else{
							resultado += tmp;
						}
					}
				}
			}

			return resultado;
		}
	}
        
public static String quitarCeros(String cadena) {
        StringTokenizer str = new StringTokenizer(cadena,",");
        String temp;
        String resultado="";
        int tokens=str.countTokens();
        if(cadena.contains("0")&& cadena.length()>1){
            for(int i=0;i<tokens;i++){
                temp=str.nextToken();
                if(i<tokens-1){
                    
                    if(!temp.equals("0")){
                        resultado+=temp+",";
                    }                    
                }else{

                    if(!temp.equals("0")){
                        resultado+=temp;
                    }else if(((resultado.compareTo("")==0) && resultado.length()==0)){
                        return "0";
                    }                     
                }

            }
            return resultado;
        }
        else{
            return cadena;

        }
        
    }
        
public static String quitarDuplicados(String cadena){
        StringTokenizer str = new StringTokenizer(cadena,"/");
        String [] Array=new String[str.countTokens()];
        for(int i=0;i<Array.length;i++){
            Array[i]=str.nextToken();
        }
        for(int i =0;i<Array.length;i++){
            for(int j=0;j<Array.length-1;j++)
            if(Array[i].compareTo(Array[j])==0 && (i!=j)){
                Array[j]="";
            }
            
        }
        String resultado="";
        for(int i=0;i<Array.length;i++){
            if(!(Array[i].compareTo("")==0)){
                if(i!=Array.length-1){
                    resultado+=Array[i]+"/";
                }
                else{
                    resultado+=Array[i];
                }
                
            }
        }

        return resultado;
        
    }
        
    private static boolean TablaEquivalente(String[][] automata1, String[][] automata2,String []lenguajeAutomata) {
        String[][] tablaDeEquivalencia =new String[automata1.length+automata2.length][lenguajeAutomata.length+1];
        int columna;
        int indice1;
        int indice2;
        int contador=1;
        int[] pendiente=new int[2];
        
        boolean finalizado=false;
        tablaDeEquivalencia[0][0]=(automata1[0][0]+","+automata2[0][0]);
        
        
        for(columna=0;columna<lenguajeAutomata.length+1;columna++){
            indice1=getIndice(automata1[0][columna],automata1);
            indice2=getIndice(automata2[0][columna],automata2);
            tablaDeEquivalencia[0][columna]=(automata1[0][columna]+","+automata2[0][columna]);

            if((setPendiente(automata1[0][columna]+","+automata2[0][columna], tablaDeEquivalencia)) ){
                tablaDeEquivalencia[contador][0] = tablaDeEquivalencia[0][columna];
                contador++;
            }
          
    

        }
        
        while(!finalizado){
            pendiente= getIndexes(tablaDeEquivalencia);
            if(pendiente[0]==-1) {
                finalizado=true;
                break;
            }
            if(tablaDeEquivalencia[pendiente[0]][0] != null){
                StringTokenizer str= new StringTokenizer(tablaDeEquivalencia[pendiente[0]][0],",");
            String str1 = str.nextToken();
            String str2 = str.nextToken();
            indice1=getIndice(str1,automata1);
            indice2=getIndice(str2,automata2);

            for(columna=1;columna<=lenguajeAutomata.length;columna++){
                tablaDeEquivalencia[pendiente[0]][pendiente[1]-1+columna]=automata1[indice1][columna]+","+automata2[indice2][columna];
                if(setPendiente(automata1[indice1][columna]+","+automata2[indice2][columna], tablaDeEquivalencia)){
    
                    tablaDeEquivalencia[contador][0]=automata1[indice1][columna]+","+automata2[indice2][columna];
                    contador++;
    
                }
            }
            
            }
            
            
            
            
            
            //System.out.println(tablaDeEquivalencia);
            
        }
        printTabla(tablaDeEquivalencia);
        return Equivalente(tablaDeEquivalencia);
    }
    
    
    
    private static boolean setPendiente(String cadena, String[][] tabla){
        
        for (int i=0;i<tabla.length;i++){
            if(tabla[i][0]!=null){
                if(tabla[i][0].compareTo(cadena) == 0){
                    return false;
                }  
            }

        }
        return true;

        
    }
    
    private static int getIndice(String cadena, String[][] array){
        int indice;
        for(int i=0; i<array.length;i++){
            if(cadena.compareTo(array[i][0])==0){
                return i;
            }
        }
        return 0;
    }
    
    private static int[] getIndexes(String [][] tabla){
        int []indeces=new int[2];
        for(int i=1;i<tabla.length;i++){
            for(int j=0;j<tabla[i].length;j++){
                if(tabla[i][j]==null&&(j>0)&&tabla[i][j-1]!=null){
                    indeces[0]=i;
                    indeces[1]=j;
                    //System.out.println("i "+j+"j "+j);
                    return indeces;
                }
            }

        }
        indeces[0]=-1;
        return indeces;
    }
    
    private static void printTabla(String[][] tabla){
        for(int i=0;i<tabla.length;i++){
            for(int j=0;j<tabla[i].length;j++){
                if(tabla[i][j]!=null){
                    System.out.print("["+tabla[i][j]+"]");
                }
            }
            if(tabla[i][0]!=null){
                System.out.println();
            }
        }
    }
    
    
    private static boolean Equivalente(String [][] tabla){
        
        for(int i=0;i<tabla.length;i++){
            for(int j=0;j<tabla[i].length;j++){
                if(tabla[i][j]!=null){
                    StringTokenizer str=new StringTokenizer(tabla[i][j],",");
                    String str1=str.nextToken();
                    String str2=str.nextToken();
                    //System.out.println(str1+" "+str2);
                    if(str2.contains("*")&& str1.equals("0")){
                    return true;
                }
                    else if(str1.contains("*")&& str2.equals("0")){
                    return true;
                }
                    else if(!str1.contains("*") && (str2.contains("*"))){
                        return false;  
                        
                }else if(str1.contains("*") && (!str2.contains("*"))){
                        return false;  
                        
                }

                }
            }

        }
        return true;
    }
    
    
    
    public static void main(String[] args) {
        //  ---------- Equivalentes --------------
        String[][] automata1 = {
				{"a0*","a2*","a1"},
				{"a1","a1","a1"},
				{"a2*","a0*","a1"}};
        
	String [][] automata2 = {
				{"b0*","b0*","b1"},
				{"b1","b1","b1"}};
        
        String [] lenguajeAutomata={"a","b"};
        
        
        
        
        
		
		
//		// ---------- No Equivalentes ------------
//		String[] lenguaje3={"a","b"};
//                String [][] automata3 = {
//				 				 {"q0","q0","q1"},
//				 				 {"q1","q0","q2*"},
//				 				 {"q2*","q2*","q2*"}};
//		
//		String [][] automata4 = {
//								 {"r0*","r0*","r1"},
//								 {"r1","r0*","r2*"},
//								 {"r2*","r2*","r2*"}};
//                
//                
//		
//		// ---------- Equivalentes ---------------
//		
		String[][] automata5 = {
								{"x0","x1*","x2","x2"},
								{"x1*","x3*","x4","x1*"},
								{"x2","x5*","x6","x6"},
								{"x3*","x3*","x6","x6"},
								{"x4","x7*","x4","x8"},
								{"x5*","x3*","x6","x6"},
								{"x6","x6","x6","x6"},
								{"x7*","x3*","x4","x6"},
								{"x8","x3*","x4","x6"}};
		
		String[][] automata6 = {
								{"y0","y3*","y1","y1"},
								{"y1","y8*","y2","y2"},
								{"y2","y2","y2","y2"},
								{"y3*","y5*","y6","y4*"},
								{"y4*","y5*","y6","y4*"},
								{"y5*","y5*","y2","y2"},
								{"y6","y9*","y6","y7"},
								{"y7","y5*","y6","y2"},
								{"y8*","y5*","y2","y2"},
								{"y9*","y5*","y6","y2"}};
		
		String[][] automata7 = {
								{"z0","z1*","z2","z2"},
								{"z1*","z3*","z4","z1*"},
								{"z2","z3*","z5","z5"},
								{"z3*","z3*","z5","z5"},
								{"z4","z6*","z4","z7"},
								{"z5","z5","z5","z5"},
								{"z6*","z3*","z4","z5"},
								{"z7","z3*","z4","z5"}};
                String[] lenguaje7  ={"1","2","3"};
//                System.out.println(TablaEquivalente(automata6, automata5, lenguaje7));
//                
//		
//		// ---------- No Equivalentes ------------
//		String [] lenguaje ={"0","1"};
//		String[][] automata8 = {
//								{"c0*","c0*","c1"},
//								{"c1","c0*","c1"}};
//		
//		String[][] automata9 = {
//								{"d0*","d1","d2*"},
//								{"d1","d1","d2*"},
//								{"d2*","d0*","d2*"}};
//                
//		
//		// ---------- Equivalentes ---------------
//		
//		String[][] automata10 = {{"","0","1"},
//								 {"e0*","e0*","e1"},
//								 {"e1","e0*","e1"}};
//
//		String[][] automata11 = {{"","0","1"},
//								 {"f0*","f1*","f2"},
//								 {"f1*","f1*","f2"},
//								 {"f2","f0*","f2"}};

//String[][] automata12 = {
//				{"g0*","g3","g6"},
//				{"g1","g6","g0*"},
//				{"g2","g1","g5*"},
//				{"g3","g1","g5*"},
//				{"g4*","g2","g5*"},
//				{"g5*","g4*","g6"},
//				{"g6","g6","g6"}};
//
//		String[][] automata13 = {
//				{"h0*","h1/h2/h4","0"},
//				{"h1","0","h0*"},
//				{"h2","h3","0"},
//				{"h3","0","h0*"},
//				{"h4","0","h5"},
//				{"h5","h0*","0"},
//				{"0","0","0"}};     
//
//                
//                
//                String[] lenguaje13 ={"a","b"};
                
                
//                String[][] automata14 ={
//				{"g0","g1/g4*","g3*"},
//				{"g1","g1","g2*"},
//				{"g2*","0","0"},
//				{"g3*","0","0"},
//				{"g4*","0","g4*"},
//				{"0","0","0"}};
//		
//		String[][] automata15= {
//				{"q0","q2*","q1*"},
//				{"q1*","q5","q5"},
//				{"q2*","q3","q6"},
//				{"q3","q3","q4"},
//				{"q4","q5","q5"},
//				{"q5","q5","q5"},
//				{"q6","q5","q6"}};
                
//                String[]lenguaje15={"a","b","c"};
//                String[][] automata15= {
//				{"s0","s1/s3","0","0"},
//				{"s1","0","s2","0"},
//				{"s2","0","0","0"},
//				{"s3","0","0","s4*"},
//				{"s4*","0","0","0"},
//                                {"0","0","0","0"}
//				};
//                String[][] automata16= {
//				{"s0","s1","0","0"},
//				{"s1","0","s2*","s3*"},
//				{"s2*","0","0","0"},
//				{"s3*","0","0","0"},
//				};
//                
//                System.out.println(TablaEquivalente(AFNtoAFD(automata15), automata16, lenguaje15));
//         String[][] automata17= {
//				{"q1","0","q2/q3*"},
//				{"q2","q1/q3*","0"},
//				{"q3*","0","0"},
//                                {"0","0","0"},
//				};
//                  String[][] automata18= {
//				{"a1","a4","a2*"},
//				{"a2*","a3*","a4"},
//				{"a3*","a4","a2*"},
//                                {"a4","a4","a4"},
//				};
//                  String [] lenguaje18={"a","b"};
//                //System.out.println(TablaEquivalente(AFNtoAFD(automata17), automata18, lenguaje18));
//
               		String[][] automata16 = {
				{"r1","0","r2/r3*"},
				{"r2","r1/r3*","0"},
				{"r3*","0","0"},
				{"0","0","0"}};
		
		String[][] automata17= {
				{"q0","q1","q2*"},
				{"q2*","q3*","q1"},
				{"q3*","q1","q2*"},
				{"q1","q1","q1"}
                                };
                String[]lenguaje17={"a","b"};
                System.out.println(TablaEquivalente( automata17,AFNtoAFD(automata16), lenguaje17));
//String[][] automata19= {
//				{"r0*","r1","0"},
//				{"r1","r2","r3/r0*"},
//				{"r2","0","r0*"},
//				{"r3","r0*","0"},
//				{"0","0","0"}};
//String []lenguaje19={"a","b"};
//String[][] automata18= {
//				{"q0*","q1/q2/q4","0"},
//				{"q1","0","q0*"},
//				{"q2","q3","0"},
//				{"q3","0","q0*"},
//				{"q4","0","q5"},
//				{"q5","q0*","0"},
//				{"0","0","0"}};
//
//        System.out.println(TablaEquivalente(AFNtoAFD(automata19),AFNtoAFD(automata18),lenguaje19));
//                
    }
    
    
    
    
    
}
