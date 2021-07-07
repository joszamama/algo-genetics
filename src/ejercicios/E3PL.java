package ejercicios;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import formato.FormatoE3PL;
import lectura.DatosE3;
import us.lsi.common.Tuple3;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;

public class E3PL {

	private static List<Tuple3<String, Double, List<String>>> productos;
	private static List<String> funcionalidades;

	public static Double getPrecio(Integer i) {
		return productos.get(i).v2;
	}

	public static Integer getNumProductos() {
		return productos.size();
	}

	public static Integer getNumFuncionalidades() {
		return funcionalidades.size();
	}

	public static Integer contieneFuncionalidad(Integer i, Integer j) {
		return productos.get(i).v3.stream().anyMatch(p -> p.equals(funcionalidades.get(j))) ? 1 : 0;
	}

	public static List<String> funcionalidadesProducto(Integer i) {
		return productos.get(i).v3;
	}

	public static void modelo(String ruta) throws IOException {
		DatosE3.read(ruta);
		productos = DatosE3.getProductos();
		funcionalidades = DatosE3.getFuncionalidades();
		String formato = "ficheros/gurobi/E3/" + ruta.replace("ficheros/", "").replace(".txt", "") + ".lp";
		AuxGrammar.generate(E3PL.class, "modelos/E3.lsi", formato);
		GurobiSolution solve = GurobiLp.gurobi(formato);
		Locale.setDefault(new Locale("en", "US"));
		System.out.println(FormatoE3PL.create(solve.objVal, solve.values));
	}

	public static void main(String[] args) throws IOException {
		modelo("ficheros/PI6Ej3DatosEntrada1.txt");
		modelo("ficheros/PI6Ej3DatosEntrada2.txt");
		modelo("ficheros/PI6Ej3DatosEntrada3.txt");
	}

}
