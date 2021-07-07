package formato;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ejercicios.E4PL;

public class FormatoE4PL {

	private static Map<Integer, List<Integer>> conjuntos;
	private static Integer size;

	public static FormatoE4PL create(Double objVal, Map<String, Double> values) {
		return new FormatoE4PL(objVal, values);
	}

	private FormatoE4PL(Double objVal, Map<String, Double> values) {
		conjuntos = new HashMap<>();
		size = objVal.intValue();
		for (Entry<String, Double> val : values.entrySet()) {
			if (val.getValue() > 0 && val.getKey().startsWith("x")) {
				String[] fields = val.getKey().split("_");
				Integer atribute = E4PL.getValor(Integer.valueOf(fields[1].trim()));
				Integer group = Integer.valueOf(fields[2].trim()) + 1;
				if (conjuntos.containsKey(group)) {
					List<Integer> vals = conjuntos.get(group);
					vals.add(atribute);
				} else {
					List<Integer> vals = new ArrayList<>();
					vals.add(atribute);
					conjuntos.put(group, vals);
				}
			}
		}

	}

	public String toString() {
		String toString = "Suma objetivo: " + E4PL.getSumaEntre3() + "\n";
		toString += "=======================\n";
		toString += "El menor conjunto contiene " + size + " elemento\n";
		for (Entry<Integer, List<Integer>> conjunto : conjuntos.entrySet()) {
			toString += "Elementos del conjunto " + conjunto.getKey() + ":" + conjunto.getValue() + "\n";
		}
		toString += "================";
		return toString;
	}

}
