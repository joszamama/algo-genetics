package lectura;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import us.lsi.common.Files2;
import us.lsi.common.Tuple;
import us.lsi.common.Tuple2;

public class DatosE2 {

	private static List<Tuple2<String, List<Integer>>> abogados;
	private static List<Integer> casos;

	public static List<Tuple2<String, List<Integer>>> getAbogados() {
		return DatosE2.abogados;
	}

	public static List<Integer> getCasos() {
		return DatosE2.casos;
	}

	public static void read(String ruta) throws IOException {
		List<String> lectura = Files2.linesFromFile(ruta);
		DatosE2.abogados = new ArrayList<>();
		DatosE2.casos = new ArrayList<>();
		for (String linea : lectura) {
			String[] sep1 = linea.split(":");
			String abogado = sep1[0].trim();
			String[] horas = sep1[1].trim().split(",");
			List<Integer> horasPorCaso = new ArrayList<>();
			for (String caso : horas) {
				horasPorCaso.add(Integer.valueOf(caso.trim()));
			}
			DatosE2.abogados.add(Tuple.create(abogado, horasPorCaso));
		}
		int numCasos = getAbogados().get(0).v2.size();
		for (int i = 1; i <= numCasos; i++) {
			DatosE2.casos.add(i);
		}

	}

}
