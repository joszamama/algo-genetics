package lectura;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import us.lsi.common.Files2;

public class DatosE4 {

	private static List<List<Integer>> conjuntos;

	public static List<List<Integer>> getConjuntos() {
		return DatosE4.conjuntos;
	}

	public static void read(String ruta) throws IOException {
		List<String> lectura = Files2.linesFromFile(ruta);
		DatosE4.conjuntos = new ArrayList<>();
		for (String linea : lectura) {
			List<Integer> conjunto = new ArrayList<>();
			String[] sep1 = linea.split(",");
			for (String arg : sep1) {
				conjunto.add(Integer.valueOf(arg.trim()));
			}
			conjuntos.add(conjunto);
		}
	}
}
