package ejercicios;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import formato.FormatoE2AG;
import lectura.DatosE2;
import us.lsi.ag.ValuesInRangeProblemAG;
import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agchromosomes.ValuesInRangeChromosome;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.common.Sets2;
import us.lsi.common.Tuple2;

public class E2AG implements ValuesInRangeProblemAG<Integer, FormatoE2AG> {

	private Double hours;
	private Double fallos;
	private Double fitness = null;

	public E2AG(String ruta) throws IOException {
		// TODO Auto-generated constructor stub
		DatosE2.read(ruta);
	}

	public List<Integer> getCasos() {
		return DatosE2.getCasos();
	}

	public List<Tuple2<String, List<Integer>>> getAbogados() {
		return DatosE2.getAbogados();
	}

	public ChromosomeType getType() {
		// TODO Auto-generated method stub
		return ChromosomeType.Range;
	}

	public Integer getCellsNumber() {
		// TODO Auto-generated method stub
		return getCasos().size();
	}

	public Integer getMax(Integer i) {
		// TODO Auto-generated method stub
		return getAbogados().size() + 1;
	}

	public Integer getMin(Integer i) {
		// TODO Auto-generated method stub
		return 1;
	}

	private void calcula(List<Integer> ls) {
		this.hours = 0.;
		this.fallos = 0.;
		Set<Integer> set = Sets2.copy(ls);
		for (int i = 0; i < ls.size(); i++) {
			int abogado = ls.get(i);
			this.hours -= getAbogados().get(abogado - 1).v2.get(i);
			// Deben representarse TODOS los abogados
			if (set.size() != getAbogados().size()) {
				this.fallos += 10000;
			}

		}

	}

	public Double fitnessFunction(ValuesInRangeChromosome<Integer> cr) {
		List<Integer> chrom = cr.decode();
		calcula(chrom);
		this.fitness = this.hours - 1000 * this.fallos;
		return this.fitness;
	}

	public static void start(String ruta) throws IOException {
		AlgoritmoAG.ELITISM_RATE = 0.2;
		AlgoritmoAG.CROSSOVER_RATE = 0.9;
		AlgoritmoAG.MUTATION_RATE = 0.75;
		AlgoritmoAG.POPULATION_SIZE = 50;
		StoppingConditionFactory.NUM_GENERATIONS = 50000;
		StoppingConditionFactory.SOLUTIONS_NUMBER_MIN = 1;
		ValuesInRangeProblemAG<Integer, FormatoE2AG> p = new E2AG(ruta);
		AlgoritmoAG<ValuesInRangeChromosome<Integer>> ap = AlgoritmoAG.create(p);
		ap.ejecuta();
		System.out.println(ap.getBestChromosome().decode());
		System.out.println(p.getSolucion(ap.getBestChromosome()));
	}

	public static void main(String[] args) throws IOException {
		start("ficheros/PI6Ej2DatosEntrada1.txt");
		start("ficheros/PI6Ej2DatosEntrada2.txt");
		start("ficheros/PI6Ej2DatosEntrada2.txt");
	}

	@Override
	public FormatoE2AG getSolucion(ValuesInRangeChromosome<Integer> cr) {
		// TODO Auto-generated method stub
		List<Integer> chrom = cr.decode();
		return FormatoE2AG.create(chrom);
	}

}
