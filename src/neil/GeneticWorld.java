package neil;

public class GeneticWorld
{

	/**
	 * @param args
	 */

	public static void main(String[] args)
	{
		int numGenerations = 100000;
		ChromosomeInitializer ci = new ChromosomeInitializer();
		FitnessTester ft = new PiTester();
		// parse options
		ProgressLogger pl = new ProgressLogger();
		Chromosome[] chromes = new Chromosome[20];
		ci.initializeChromosomes(chromes, 600);
		ft.evaluateFitness(chromes);
		for (int i = 0; i < numGenerations - 1 ; i++)
		{
			ChromosomeMutator.mutateChromosomes(chromes);
			ft.evaluateFitness(chromes);
			pl.logProgress(chromes);
		}
	}

}
