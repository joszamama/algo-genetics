package ejercicios;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import formato.FormatoE1AG;
import lectura.DatosE1;
import us.lsi.ag.ValuesInRangeProblemAG;
import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agchromosomes.ValuesInRangeChromosome;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.common.Tuple2;

public class E1AG implements ValuesInRangeProblemAG<Integer, FormatoE1AG> {
	private Double afinidad;
	private Integer fallos;
	private Double fitness = null;

	public E1AG(String ruta) throws IOException {
		DatosE1.read(ruta);
	}

	public List<Integer> getGrupos() {
		return DatosE1.getGrupos();
	}

	public List<Tuple2<String, List<Integer>>> getAlumnos() {
		return DatosE1.getAlumnos();
	}

	private void calcula(List<Integer> ls) {
		this.afinidad = 0.;
		this.fallos = 0;
		for (int i = 0; i < ls.size(); i++) {
			// Penaliza si la afinidad es 0
			int val = getAlumnos().get(i).v2.get(ls.get(i) - 1);
			if (val == 0) {
				this.fallos += 1;
			}
			this.afinidad += val;
		}
		Map<Integer, Integer> contador = new HashMap<>();
		for (int i = 0; i < ls.size(); i++) {
			Integer suma = contador.getOrDefault(ls.get(i), 0);
			suma++;
			contador.put(ls.get(i), suma);
		}
		// Penalización importante!! Si los alumnos x grupo no son 4, penaliza!
		for (Entry<Integer, Integer> filtro : contador.entrySet()) {
			if (filtro.getValue() != (getAlumnos().size() / getGrupos().size())) {
				this.fallos += 100;
			}
		}
	}

	// Adecuado para codificar problemas de subconjuntos de multiconjuntos.
	public ChromosomeType getType() {
		return ChromosomeType.Range;
	}

	public Integer getCellsNumber() {
		return this.getAlumnos().size();
	}

	public Integer getMax(Integer i) {
		return this.getGrupos().size() + 1;
	}

	public Integer getMin(Integer i) {
		return 1;
	}

	// Aquí hacemos de forma sucesiva el calculo y añadimos las penalizaciones
	public Double fitnessFunction(ValuesInRangeChromosome<Integer> cr) {
		List<Integer> chrom = cr.decode();
		calcula(chrom);
		this.fitness = this.afinidad - 10000 * this.fallos;
		return this.fitness;
	}

	public static void start(String ruta) throws IOException {
		AlgoritmoAG.ELITISM_RATE = 0.2;
		AlgoritmoAG.CROSSOVER_RATE = 0.9; // debe estar entre 0.8 y 0.95
		AlgoritmoAG.MUTATION_RATE = 0.75; // debe estar entre 0.5 y 1.0
		AlgoritmoAG.POPULATION_SIZE = 50; // probé con 10, 15, 100, 75. Mejor resultado con 50
		StoppingConditionFactory.NUM_GENERATIONS = 50000; // probé con 100, 1000, 10000. Mejor resultado 50000
		StoppingConditionFactory.SOLUTIONS_NUMBER_MIN = 1;
		ValuesInRangeProblemAG<Integer, FormatoE1AG> p = new E1AG(ruta);
		AlgoritmoAG<ValuesInRangeChromosome<Integer>> ap = AlgoritmoAG.create(p);
		ap.ejecuta();
		System.out.println(ap.getBestChromosome().decode());
		System.out.println(p.getSolucion(ap.getBestChromosome()));
	}

	public static void main(String[] args) throws IOException {
		start("ficheros/PI6Ej1DatosEntrada1.txt");
		start("ficheros/PI6Ej1DatosEntrada2.txt");
		start("ficheros/PI6Ej1DatosEntrada3.txt");
	}

	@Override
	public FormatoE1AG getSolucion(ValuesInRangeChromosome<Integer> cr) {
		// TODO Auto-generated method stub
		List<Integer> chrom = cr.decode();
		return FormatoE1AG.create(chrom);
	}
}
