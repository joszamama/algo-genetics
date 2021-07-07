package formato;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lectura.DatosE3;
import us.lsi.common.Tuple3;

public class FormatoE3AG {

	private List<Tuple3<String, Double, List<String>>> productos; // Productos que tomamos

	public static FormatoE3AG create(List<Integer> chrom) {
		return new FormatoE3AG(chrom);
	}

	private FormatoE3AG(List<Integer> chrom) {
		this.productos = new ArrayList<>();
		// Recorremos el cromosoma
		for (int i = 0; i < chrom.size(); i++) {
			// Si get(i) = 1 significa que ese producto lo añadimos
			if (chrom.get(i) == 1) {
				Tuple3<String, Double, List<String>> producto = DatosE3.getProductos().get(i);
				productos.add(producto);
			}
		}
	}

	public String toString() {
		String toString = "Composicion del lote seleccionado:\n";
		for (Tuple3<String, Double, List<String>> producto : productos) {
			toString += "   " + producto.v1 + " ( " + producto.v2 + " euros)" + " => " + producto.v3 + "\n";
		}
		toString += "Funcionalidades de la seleccion: " + getFuncionalidades() + "\n";
		toString += "Precio total del lote seleccionado: " + getPrecioTotal() + "\n";
		return toString;
	}

	private Double getPrecioTotal() {
		return productos.stream().map(x -> x.v2).reduce(0., (accum, x) -> {
			return accum + x;
		});
	}

	private Set<String> getFuncionalidades() {
		return productos.stream().map(x -> x.v3).flatMap(string -> string.stream()).collect(Collectors.toSet());
	}
}
