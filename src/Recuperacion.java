import java.util.Scanner;
import java.io.File;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.*;
import java.util.stream.Collector;
import java.lang.*;
import java.util.*;
import java.util.HashMap;
import java.io.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.*;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Comparator;

public class Recuperacion{


void recupera()throws IOException{

HashMap<String,tupla> Indice=new HashMap<>();

HashMap<String,Double> longDoc = new HashMap<>();


//recuperar indice
String texto = new String(Files.readAllBytes(Paths.get("archivoIndice.txt")));

ArrayList<String> aux = new ArrayList<>(Arrays.asList(texto.split("\n")));

for(String i: aux){
  
    ArrayList<String> terminoslinea=new ArrayList<>(Arrays.asList(i.split(" ")));

    ArrayList<String> idpeso=new ArrayList<>(Arrays.asList(terminoslinea.get(2).split(",")));
    HashMap<String,Double> docpesoaux=new HashMap<>();
    for(String j:idpeso){
        ArrayList<String> idpesoseparado=new ArrayList<>(Arrays.asList(j.split("-")));
        if(idpesoseparado.size()!=1){
        docpesoaux.put(idpesoseparado.get(0),Double.parseDouble(idpesoseparado.get(1)));
        }      
    }
    tupla tup=new tupla(Double.parseDouble(terminoslinea.get(1)),docpesoaux);
    Indice.put(terminoslinea.get(0),tup);
    
}


//recuperar longdoc
String textolongdoc = new String(Files.readAllBytes(Paths.get("archivolongDoc.txt")));
ArrayList<String> auxlongdoc = new ArrayList<>(Arrays.asList(textolongdoc.split("\n")));
for(String i:auxlongdoc){
    
    ArrayList<String> idlong=new ArrayList<>(Arrays.asList(i.split(" ")));
    longDoc.put(idlong.get(0),Double.parseDouble(idlong.get(1)));

}


//leer consulta y procesarla
Scanner sc=new Scanner(System.in);
System.out.println("Introduce la consulta:");
String consulta=sc.nextLine();


System.out.println("Introduce el numero de resultados a mostrar:");
int numRes=sc.nextInt();


EjecutorFiltros ef=new EjecutorFiltros();
ef.add(new Remplazar("[^-\\w]"," "));
ef.add(new Remplazar("\\b[0-9]+\\b"," "));
ef.add(new Remplazar("-+ | -+"," "));
ef.add(new Remplazar(" +"," "));
ef.add(new minuscula());

String textoprocesado=ef.execute(consulta);

ArrayList<String> terminosconsulta = new ArrayList<>(Arrays.asList(textoprocesado.split(" ")));


//quitar palabras vacias
File archivo = new File ("terminosmalos.txt");
URI archivoterminosmalos=archivo.toURI();
String textoterminosmalos = new String(Files.readAllBytes(Paths.get(archivoterminosmalos)));
ArrayList<String> terminosmalos = new ArrayList<>(Arrays.asList(textoterminosmalos.split(" ")));

terminosconsulta.removeAll(terminosmalos);


//recuperacion y muestreo de documentos, ranking
HashMap<String,Double> puntuacion=new HashMap<>();

for(String doc:longDoc.keySet()){
    puntuacion.put(doc,0.0);
}

TreeMap<String, Double> ordenado=new TreeMap<String, Double>();

for(String termino:terminosconsulta){
    for(String doc:puntuacion.keySet()){
        double idf=0;
		HashMap<String, Double> t=new HashMap<String, Double>();
        if(Indice.containsKey(termino)){
            idf=Indice.get(termino).IDF;
            t=Indice.get(termino).docpeso;
            if(t.containsKey(doc)){
                    Double p=puntuacion.get(doc);
                    puntuacion.put(doc,p+(idf*t.get(doc)/longDoc.get(doc)));
            }  
                      
        }
    }
    Comparator<String> comparator = new ValueComparator(puntuacion);
	ordenado = new TreeMap<String, Double>(comparator);
	ordenado.putAll(puntuacion);
}




//muestreo

for(String doc:ordenado.keySet()){
    if(numRes!=0){
        if(puntuacion.get(doc)>0.0){
        System.out.println("Documento ID: "+doc+" (Peso: "+puntuacion.get(doc)+")");
        String documento = new String(Files.readAllBytes(Paths.get(doc)));
        System.out.println("Texto: "+documento);

        }
        numRes--;
    }    
}













}








}