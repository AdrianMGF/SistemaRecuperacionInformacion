import java.io.File;
import java.io.PrintWriter;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.*;
import java.lang.*;
import java.util.*;
import java.util.HashMap;
import java.io.*;
import java.io.IOException;

public class Indexacion{

String carpetacorpus="C:/Users/Adrian/Desktop/uni/RECINF/proyecto/corpus"; //cambiar ruta de carpeta corpus


void Indexa() throws IOException{ 
File carpeta =new File(carpetacorpus);
HashMap<String,Double> mapaFrecuenciaTerminos= new HashMap<>();

HashMap<String,tupla> Indice=new HashMap<>();

HashMap<String,Double> longDoc = new HashMap<>();
    int N=0;
    for(File ficheroentrada : carpeta.listFiles()){ //recorrer todos los documentos
        URI fich=ficheroentrada.toURI();
        String docID=ficheroentrada.toString();
        String texto = new String(Files.readAllBytes(Paths.get(fich))); //recoger texto del archivo
        
        
        EjecutorFiltros ef=new EjecutorFiltros();
        ef.add(new Remplazar("[^-\\w]"," "));
        ef.add(new Remplazar("\\b[0-9]+\\b"," "));
        ef.add(new Remplazar("-+ | -+"," "));
        ef.add(new Remplazar(" +"," "));
        ef.add(new minuscula());

        String textoprocesado=ef.execute(texto);
        
        ArrayList<String> vTerms = new ArrayList<>(Arrays.asList(textoprocesado.split(" ")));
        
       
        
        //quitar palabras vacias
        File archivo = new File ("terminosmalos.txt");
        URI archivoterminosmalos=archivo.toURI();
        String textoterminosmalos = new String(Files.readAllBytes(Paths.get(archivoterminosmalos)));
        ArrayList<String> terminosmalos = new ArrayList<>(Arrays.asList(textoterminosmalos.split(" ")));
        
        vTerms.removeAll(terminosmalos);
        
          
        //calculo tf
        
        for (String termino : vTerms) {
            if(mapaFrecuenciaTerminos.containsKey(termino)){
                Double f=mapaFrecuenciaTerminos.get(termino);
                f++;
                mapaFrecuenciaTerminos.put(termino, f);
            }
            else{
                mapaFrecuenciaTerminos.put(termino, 1.0);
            }         
          }
        
        
        for (String t: mapaFrecuenciaTerminos.keySet()) {     
            tupla aux;
           Double tf=1+Math.log(mapaFrecuenciaTerminos.get(t))/Math.log(2);
           if(Indice.containsKey(t)){
             aux=Indice.get(t);
           }
           else{
            aux=new tupla(0.0);
           }
           aux.docpeso.put(docID,tf);
           Indice.put(t,aux);
        }

        //vaciar estructura para sig documento
        mapaFrecuenciaTerminos.clear();

        N++;
        
    }//recorrer documentos


    //calcular IDF y long doc
    for(String t: Indice.keySet()){
        double n=Indice.get(t).docpeso.size();
        Indice.get(t).IDF=Math.log(N/n)/Math.log(2);
        for(String d : Indice.get(t).docpeso.keySet()){
            Double waux=Indice.get(t).docpeso.get(d)*Indice.get(t).IDF;
            Indice.get(t).docpeso.put(d,waux);
            if(longDoc.containsKey(d)){
                Double w=longDoc.get(d);
                w+=Math.pow(waux, 2);
                longDoc.put(d,w);
            }
            else{
                Double w=0.0;
                longDoc.put(d,w);
            }
        }
    }

    for(String i: longDoc.keySet()){
        longDoc.put(i,Math.sqrt(longDoc.get(i)));
    }
    

    //escribir indice y longDoc en archivos
    FileWriter archivoIndice=new FileWriter("archivoIndice.txt");
    PrintWriter pw=new PrintWriter(archivoIndice);
    for(String t : Indice.keySet()){
        pw.print(t+" "+Indice.get(t).IDF+" ");
        for(String d : Indice.get(t).docpeso.keySet()){
            pw.print(d+"-"+Indice.get(t).docpeso.get(d)+",");
        }
        pw.println("");
        
    }
    pw.close();

    FileWriter archivolongDoc=new FileWriter("archivolongDoc.txt");
    PrintWriter pw1=new PrintWriter(archivolongDoc);
    for(Map.Entry<String,Double> t : longDoc.entrySet()){
        pw1.println(t.getKey()+" "+t.getValue());
    }
    pw1.close();















}


   











}
