package lectura;

import java.util.ArrayList;
import java.util.List;

import us.lsi.common.Files2;
import us.lsi.common.Tuple;
import us.lsi.common.Tuple3;

public class DatosE3 {

	private static List<Tuple3<String, Double, List<String>>> productos;
	private static List<String> funcionalidades;

	public static List<Tuple3<String, Double, List<String>>> getProductos() {
		return productos;
	}

	public static List<String> getFuncionalidades() {
		return funcionalidades;
	}

	public static void read(String ruta) {
		List<String> lectura = Files2.linesFromFile(ruta);
		funcionalidades = new ArrayList<>();
		productos = new ArrayList<>();
		String v1 = lectura.get(0);
		String[] separador1 = v1.split(":");
		String[] funcionalidadesSplit = separador1[1].trim().split(",");
		for (String f : funcionalidadesSplit) {
			funcionalidades.add(f);
		}
		lectura.remove(0);
		for (String linea : lectura) {
			String[] sep1 = linea.split(":");
			String[] sep2 = sep1[0].split(" ");
			String[] sep3 = sep1[1].trim().split(",");

			String producto = sep2[0];
			Double precio = Double.valueOf(sep2[1].replace("(", "").trim());
			List<String> funcionalidades = new ArrayList<>();
			for (String f : sep3) {
				funcionalidades.add(f);
			}
			productos.add(Tuple.create(producto, precio, funcionalidades));
		}
	}

}
