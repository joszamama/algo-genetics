package formato;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import lectura.DatosE2;

public class FormatoE2AG {
	private Map<Integer, List<Integer>> dict;
	private Map<Integer, Integer> abogadosHoras;

	public static FormatoE2AG create(List<Integer> chrom) {
		return new FormatoE2AG(chrom);
	}

	private FormatoE2AG(List<Integer> chrom) {
		this.dict = new HashMap<>();
		this.abogadosHoras = new HashMap<>();
		for (int i = 0; i < chrom.size(); i++) {
			int key = chrom.get(i);
			if (dict.containsKey(key)) {
				List<Integer> vals = dict.get(key);
				vals.add(i + 1);
			} else {
				List<Integer> vals = new ArrayList<>();
				vals.add(i + 1);
				dict.put(key, vals);
			}
			if (abogadosHoras.containsKey(key)) {
				Integer horas = abogadosHoras.get(key);
				horas += DatosE2.getAbogados().get(key - 1).v2.get(i);
				abogadosHoras.replace(key, horas);
			} else {
				abogadosHoras.put(key, DatosE2.getAbogados().get(key - 1).v2.get(i));
			}
		}
	}

	public String toString() {
		String toString = "";
		for (Entry<Integer, List<Integer>> abogado : dict.entrySet()) {
			toString += "Abogado " + abogado.getKey() + "\n";
			toString += "	Horas Empleadas: " + abogadosHoras.get(abogado.getKey()) + "\n";
			toString += "	Casos estudiados: " + abogado.getValue() + "\n";
			toString += "	Media (horas/caso): "
					+ (double) ((double) abogadosHoras.get(abogado.getKey()) / abogado.getValue().size()) + "\n";
			toString += "===================================================================\n";
		}
		toString += "El estudio de todos los casos ha supuesto un total de " + getTotalHoras()
				+ " horas de trabajo \r\n"
				+ "para el bufete, que al trabajar en paralelo se ha podido llevar a cabo en " + getMaxHoras()
				+ " horas.";
		return toString;
	}

	private Double getMaxHoras() {
		// TODO Auto-generated method stub
		return abogadosHoras.entrySet().stream().map(x -> x.getValue()).max(Comparator.naturalOrder()).get()
				.doubleValue();
	}

	private Double getTotalHoras() {
		// TODO Auto-generated method stub
		return abogadosHoras.entrySet().stream().map(x -> x.getValue()).reduce(0, (accum, x) -> {
			return accum + x;
		}).doubleValue();
	}
}
