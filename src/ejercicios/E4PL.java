package ejercicios;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import formato.FormatoE4PL;
import lectura.DatosE4;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;

public class E4PL {

	private static List<Integer> conjunto;

	public static Integer getSize() {
		return conjunto.size();
	}

	public static Integer getValor(Integer i) {
		return conjunto.get(i);
	}

	public static Integer getSumaEntre3() {
		return conjunto.parallelStream().reduce(0, (acum, x) -> {
			return acum + x;
		}) / 3;
	}

	public static void modelo(String file) throws IOException {
		DatosE4.read(file);
		int i = 1;
		for (List<Integer> conj : DatosE4.getConjuntos()) {
			conjunto = conj;
			String formato = "ficheros/gurobi/E4/" + file.replace("ficheros/", "").replace(".txt", "") + i + ".lp";
			AuxGrammar.generate(E4PL.class, "modelos/E4.lsi", formato);
			GurobiSolution solve = GurobiLp.gurobi(formato);
			Locale.setDefault(new Locale("en", "US"));
			System.out.println("Conjunto de entrada: " + conjunto);
			System.out.println(FormatoE4PL.create(solve.objVal, solve.values));
		}
	}

	public static void main(String[] args) throws IOException {
		modelo("ficheros/PI6Ej4DatosEntrada.txt");
	}

}
