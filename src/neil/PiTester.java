package neil;

public class PiTester implements FitnessTester
{

	@Override
	public void evaluateFitness(Chromosome[] chromes)
	{
		double totalFitness = 0;
		int numValid = 0;
		double maxFitness = Double.NEGATIVE_INFINITY;
		for (int i = 0; i< chromes.length; i++)
		{
			evaluateFitness(chromes[i]);
			double fit = chromes[i].getFitness();
			if (!Double.isNaN(fit))
			{
				numValid++;
				totalFitness += chromes[i].getFitness();
				maxFitness = max(maxFitness,chromes[i].getFitness());
			}
		}
		//System.out.println("Generation average fitness:"+(totalFitness/numValid));
		//System.out.println("Generation max fitness:"+maxFitness);
	}
	
	private static double max(double a, double b)
	{
		return a>b?a:b;
	}

	public void evaluateFitness(Chromosome c)
	{
		int maxInstructions = 1000;
		Program p = new Program(c);
		p.execute(maxInstructions);
		double diff = Math.abs(Math.PI - p.getFinalValue());
		c.setFitness(1.0d / min(Math.PI,diff));
	}

	private static double min(double a, double b)
	{
		return a<b?a:b;
	}
}
