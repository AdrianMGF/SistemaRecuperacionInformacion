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

public class tupla{
    double IDF;
    HashMap<String,Double> docpeso =new HashMap<>();
    tupla(Double idf){this.IDF=idf;};
    tupla(Double idf, HashMap<String,Double> docpeso){this.IDF=idf;this.docpeso=docpeso;}
}