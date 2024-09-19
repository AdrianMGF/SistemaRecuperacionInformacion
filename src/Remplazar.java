import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Remplazar implements Filtro{

	private String remplazada;
	private String remplazo;
	
	
	public Remplazar(String a, String b){
		remplazada=a; remplazo=b;
	}
	

	public String execute(String s) {
		Pattern p1=Pattern.compile(remplazada);
		Matcher m=p1.matcher(s);
		
		s=m.replaceAll(remplazo);
		return s;
	}

}