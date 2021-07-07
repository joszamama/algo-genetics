package formato;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lectura.DatosE1;

public class FormatoE1AG {
	private Map<Integer, List<Integer>> alumnosDict;
	private Double afinidadMedia;

	public static FormatoE1AG create(List<Integer> chrom) {
		return new FormatoE1AG(chrom);
	}

	private FormatoE1AG(List<Integer> chrom) {
		this.alumnosDict = new HashMap<>();
		this.afinidadMedia = 0.;
		Double sumaAfinidad = 0.;
		// Recorremos el cromosoma
		for (int i = 0; i < chrom.size(); i++) {
			int key = chrom.get(i);
			sumaAfinidad += DatosE1.getAlumnos().get(i).v2.get(key - 1);
			if (alumnosDict.containsKey(key)) {
				List<Integer> vals = alumnosDict.get(key);
				vals.add(i + 1);
			} else {
				List<Integer> vals = new ArrayList<>();
				vals.add(i + 1);
				alumnosDict.put(key, vals);
			}
		}
		afinidadMedia = sumaAfinidad / DatosE1.getAlumnos().size();
	}

	public String toString() {
		String toString = "Reparto obtenido:\n";
		for (Map.Entry<Integer, List<Integer>> par : alumnosDict.entrySet()) {
			toString += "Grupo " + par.getKey() + ": " + par.getValue() + "\n";
		}
		toString += "Afinidad media: " + afinidadMedia;
		return toString;
	}
}
