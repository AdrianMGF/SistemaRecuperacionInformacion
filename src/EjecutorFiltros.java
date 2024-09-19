import java.util.ArrayList;

public class EjecutorFiltros{
	
	private ArrayList<Filtro> filtros;

	public EjecutorFiltros(){
		filtros=new ArrayList<Filtro>();
	}
	
	public void add(Filtro f){
		filtros.add(f);
	}

	public String execute(String s) {
		
		for(int i=0;i<filtros.size();i++){
			s=filtros.get(i).execute(s);
		}
		return s;
	}
}