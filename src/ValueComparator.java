import java.util.Comparator;
import java.util.HashMap;

public class ValueComparator implements Comparator<String>{

	HashMap<String, Double> map = new HashMap<String, Double>();

	
	public ValueComparator(HashMap<String, Double> map){
		this.map.putAll(map);
	}

	public int compare(String s1, String s2) {
		if(map.get(s1) == map.get(s2)){
			return 1;
		}else{
			return (map.get(s2).compareTo(map.get(s1)));
		}	
	}
}