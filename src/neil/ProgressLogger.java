package neil;

public class ProgressLogger
{
	double prevBest = Double.NEGATIVE_INFINITY;
	int genNum = 0;
	
	public void logProgress(Chromosome[] chromes)
	{
		genNum++;
		int bestIndex=0;
		for (int i = 1;i<chromes.length; i++)
			if (chromes[i].getFitness() > chromes[bestIndex].getFitness())
				bestIndex=i;
		if (chromes[bestIndex].getFitness() > prevBest)
		{
			prevBest = chromes[bestIndex].getFitness();
			System.out.println("New best fitness in generation " + genNum +": "+prevBest);
			Program pt = new Program(chromes[bestIndex]);
			System.err.println("Code for it: " + pt);
		}
	}

}
