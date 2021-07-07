package ejercicios;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import formato.FormatoE2PL;
import lectura.DatosE2;
import us.lsi.common.Tuple2;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;

public class E2PL {

	private static List<Tuple2<String, List<Integer>>> abogados;
	private static List<Integer> casos;

	public static Integer getHours(Integer i, Integer j) {
		return abogados.get(i).v2.get(j);
	}

	public static Integer getNumAbogados() {
		return abogados.size();
	}

	public static Integer getNumCasos() {
		return casos.size();
	}

	// Posible pregunta de modificación
	public static Double getHorasMedia() {
		Double totalHoras = 0.;
		for (int i = 0; i < abogados.size(); i++) {
			for (int j = 0; j < casos.size(); j++) {
				totalHoras += getHours(i, j);
			}
		}
		return totalHoras / (abogados.size() * abogados.size());
	}

	// Llamada a Gurobi
	public static void modelo(String ruta) throws IOException {
		DatosE2.read(ruta);
		abogados = DatosE2.getAbogados();
		casos = DatosE2.getCasos();
		String formato = "ficheros/gurobi/E2/" + ruta.replace("ficheros/", "").replace(".txt", "") + ".lp";
		AuxGrammar.generate(E2PL.class, "modelos/E2.lsi", formato);
		GurobiSolution solve = GurobiLp.gurobi(formato);
		Locale.setDefault(new Locale("en", "US"));
		System.out.println(FormatoE2PL.create(solve.objVal, solve.values).toString());
	}

	public static void main(String[] args) throws IOException {
		modelo("ficheros/PI6Ej2DatosEntrada1.txt");
		modelo("ficheros/PI6Ej2DatosEntrada2.txt");
		modelo("ficheros/PI6Ej2DatosEntrada3.txt");
	}

}
