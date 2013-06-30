package neil;
import java.util.Random;

public class ChromosomeInitializer
{
	public void initializeChromosomes(Chromosome[] chromes, int chromeSize)
	{
		Random rand = new Random();
		for (int i = 0; i < chromes.length ; i++)
		{
			int seq[] = new int[chromeSize];
			for (int j=0; j< seq.length ; j++)
			{
				seq[j] = Math.round(rand.nextInt(Integer.MAX_VALUE));
			}
			chromes[i] = new Chromosome();
			chromes[i].setSequence(seq);
		}
	}
}
