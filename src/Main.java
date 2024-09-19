import java.util.ArrayList;
import java.util.Scanner;
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
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.lang.*;
import java.util.ArrayList;

public class Main { 
  public static void main(String[] args) throws Exception { 
    Scanner sc=new Scanner(System.in);
    int op=1;
    System.out.println("Indexando...");
    Indexacion i=new Indexacion();
    i.Indexa();
    System.out.println("Indexacion terminada");

    do {
    Recuperacion r=new Recuperacion();
    r.recupera();
    System.out.println("Introduce 0 si quieres salir o 1 para continuar con una nueva busqueda");
    op=sc.nextInt();
    } while (op!=0);
    System.out.println("Saliendo....");

    

    

  }
}