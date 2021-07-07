package ejercicios;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import formato.FormatoE3AG;
import lectura.DatosE3;
import us.lsi.ag.ValuesInRangeProblemAG;
import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;
import us.lsi.ag.agchromosomes.ValuesInRangeChromosome;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.common.Tuple3;

public class E3AG implements ValuesInRangeProblemAG<Integer, FormatoE3AG> {
	private Double precio;
	private Double fallos;
	private Double fitness = null;

	public E3AG(String ruta) throws IOException {
		DatosE3.read(ruta);
	}

	public List<Tuple3<String, Double, List<String>>> getProductos() {
		return DatosE3.getProductos();
	}

	public List<String> getFuncionalidades() {
		return DatosE3.getFuncionalidades();
	}

	// Un cromosoma cuyo valor decodificado es una lista de ceros y unos del tamaño
	// especificado en el problema
	public ChromosomeType getType() {
		return ChromosomeType.Range;
	}

	public Integer getCellsNumber() {
		return this.getProductos().size();
	}

	public Integer getMax(Integer i) {
		return 2;
	}

	public Integer getMin(Integer i) {
		return 0;
	}

	// Aquí van las restricciones constraints que poníamos en el .lsi
	private void calcula(List<Integer> chrom) {
		this.precio = 0.;
		this.fallos = 0.;
		Set<String> funcionalidadesCubiertas = new HashSet<>();
		for (int i = 0; i < chrom.size(); i++) {
			if (chrom.get(i) == 1) {
				Tuple3<String, Double, List<String>> objeto = getProductos().get(i);
				this.precio += objeto.v2;
				funcionalidadesCubiertas.addAll(objeto.v3);
			}
		}
		// Debemos cubrir TODAS las funcionalidades
		if (!funcionalidadesCubiertas.containsAll(getFuncionalidades())) {
			this.fallos += 1000;
		}
	}

	public Double fitnessFunction(ValuesInRangeChromosome<Integer> cr) {
		List<Integer> chrom = cr.decode();
		calcula(chrom);
		this.fitness = -precio - 1000 * fallos;
		return fitness;
	}

	public static void start(String file) throws IOException {
		AlgoritmoAG.ELITISM_RATE = 0.2;
		AlgoritmoAG.CROSSOVER_RATE = 0.9;
		AlgoritmoAG.MUTATION_RATE = 0.75;
		AlgoritmoAG.POPULATION_SIZE = 50;
		StoppingConditionFactory.NUM_GENERATIONS = 9000;
		StoppingConditionFactory.SOLUTIONS_NUMBER_MIN = 1;
		ValuesInRangeProblemAG<Integer, FormatoE3AG> p = new E3AG(file);
		AlgoritmoAG<ValuesInRangeChromosome<Integer>> ap = AlgoritmoAG.create(p);
		ap.ejecuta();
		System.out.println(ap.getBestChromosome().decode());
		System.out.println("Funcionalidades a cubrir: " + DatosE3.getFuncionalidades());
		System.out.println(p.getSolucion(ap.getBestChromosome()));
	}

	public static void main(String[] args) throws IOException {
		start("ficheros/PI6Ej3DatosEntrada1.txt");
		start("ficheros/PI6Ej3DatosEntrada2.txt");
		start("ficheros/PI6Ej3DatosEntrada3.txt");

	}

	@Override
	public FormatoE3AG getSolucion(ValuesInRangeChromosome<Integer> cr) {
		List<Integer> chrom = cr.decode();
		return FormatoE3AG.create(chrom);
	}

}
