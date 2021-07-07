package formato;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class FormatoE1PL {
	private Map<String, List<String>> alumnosDict;
	private Double afinidadMedia;

	public static FormatoE1PL create(Double objVal, Map<String, Double> values) {
		return new FormatoE1PL(objVal, values);
	}

	// objVal es la suma de todas las afinidades, values es la solucion de gurobi al
	// problema!
	private FormatoE1PL(Double objVal, Map<String, Double> values) {
		alumnosDict = new HashMap<>();
		Set<String> alumnos = new HashSet<>();
		System.out.println("objVAL = " + objVal);
		System.out.println("values = " + values);
		for (Entry<String, Double> p : values.entrySet()) {
			if (p.getValue() > 0 && p.getKey().startsWith("x")) {
				String[] fields = p.getKey().split("_");
				int key = Integer.valueOf(fields[2].trim());
				key++;
				int value = Integer.valueOf(fields[1].trim());
				value++;
				if (alumnosDict.containsKey(String.valueOf(key))) {
					List<String> list = alumnosDict.get(String.valueOf(key));
					list.add(String.valueOf(value));
					alumnos.add(String.valueOf(value));
				} else {
					List<String> list = new ArrayList<>();
					list.add(String.valueOf(value));
					alumnosDict.put(String.valueOf(key), list);
					alumnos.add(String.valueOf(value));
				}
			}
		}
		afinidadMedia = objVal / alumnos.size();
	}

	public String toString() {
		String toString = "Reparto obtenido:\n";
		for (Map.Entry<String, List<String>> par : alumnosDict.entrySet()) {
			toString += "Grupo " + par.getKey() + ": " + par.getValue() + "\n";
		}
		toString += "Afinidad media: " + afinidadMedia;
		return toString;
	}

}
