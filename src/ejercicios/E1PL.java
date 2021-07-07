package ejercicios;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import formato.FormatoE1PL;
import lectura.DatosE1;
import us.lsi.common.Tuple2;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;

public class E1PL {

	private static List<Tuple2<String, List<Integer>>> alumnos;
	private static List<Integer> grupos;
	private static Integer APG;

	// Del alumno i (representado por una tupla, tomamos la lista de integers que es
	// su afinidad en base a los grupos, y tomamos el indice j
	public static Integer getAfinidad(Integer i, Integer j) {
		return alumnos.get(i).v2.get(j);
	}

	public static Integer getNumAlumnos() {
		return alumnos.size();
	}

	public static Integer getNumGrupos() {
		return grupos.size();
	}

	public static Integer getAPG() {
		return APG;
	}

	public static void modelo(String file) throws IOException {
		DatosE1.read(file);
		alumnos = DatosE1.getAlumnos();
		grupos = DatosE1.getGrupos();
		APG = DatosE1.getAPG();
		String formato = "ficheros/gurobi/E1/" + file.replace("ficheros/", "").replace(".txt", "") + ".lp";
		AuxGrammar.generate(E1PL.class, "modelos/E1.lsi", formato);
		GurobiSolution solve = GurobiLp.gurobi(formato);
		System.out.println(FormatoE1PL.create(solve.objVal, solve.values));
	}

	public static void main(String[] args) throws IOException {
		modelo("ficheros/PI6Ej1DatosEntrada1.txt");
		modelo("ficheros/PI6Ej1DatosEntrada2.txt");
		modelo("ficheros/PI6Ej1DatosEntrada3.txt");
	}

}
