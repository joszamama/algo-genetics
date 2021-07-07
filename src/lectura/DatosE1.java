package lectura;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import us.lsi.common.Files2;
import us.lsi.common.Tuple;
import us.lsi.common.Tuple2;

public class DatosE1 {

	private static List<Tuple2<String, List<Integer>>> alumnos;
	private static List<Integer> grupos;
	private static Integer APG;

	public static List<Tuple2<String, List<Integer>>> getAlumnos() {
		return DatosE1.alumnos;
	}

	public static List<Integer> getGrupos() {
		return DatosE1.grupos;
	}

	public static Integer getAPG() {
		return DatosE1.APG;
	}

	public static void read(String ruta) throws IOException {
		List<String> lectura = Files2.linesFromFile(ruta);
		DatosE1.alumnos = new ArrayList<>();
		DatosE1.grupos = new ArrayList<>();
		for (String linea : lectura) {
			String[] sep1 = linea.split(":");
			String alumno = sep1[0].trim(); // tenemos el nombre del alumno
			String[] afinidadPorGrupo = sep1[1].trim().split(",");
			List<Integer> afinidades = new ArrayList<>();
			for (String afinidad : afinidadPorGrupo) {
				int valorGrupo = Integer.valueOf(afinidad.trim());
				afinidades.add(valorGrupo);
			}
			DatosE1.alumnos.add(Tuple.create(alumno, afinidades)); // Cada alumno con su lista de afinidades, posicion =
																	// grupo, valor = afinidad
		}
		int numGrupos = getAlumnos().get(0).getV2().size();
		for (int i = 1; i <= numGrupos; i++) {
			getGrupos().add(i);
		}
		DatosE1.APG = getAlumnos().size() / getGrupos().size(); // De forma equitativa, obtenemos el numero medio de
																// alumnos por grupo
	}

}
