package neil;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ChromosomeMutator
{
	static Random rand = new Random();
	
	public static void mutateChromosomes(Chromosome[] chromes)
	{
		Arrays.sort(chromes); //ascending fitness
		List<Chromosome> topGenes = Arrays.asList(chromes).subList(15, 20);
		Chromosome[] splicedGenes = splice(topGenes);
		Chromosome[] mutatedGenes = mutate(topGenes);
		Chromosome[] splicedMutatedGenes = mutate(Arrays.asList(splicedGenes));
		int base= 0;
		for (int index = base;index<(base+5);index++)
			chromes[index]=splicedGenes[index];
		base+=5;
		for (int index = base;index<(base+5);index++)
			chromes[index]=mutatedGenes[index-base];
		base+=5;
		for (int index = base;index<(base+5);index++)
			chromes[index]=splicedMutatedGenes[index-base];
		//System.out.println("Mutated Genes!");
	}
	public static Chromosome[] mutate(List<Chromosome> l)
	{
		Chromosome[] a = new Chromosome[l.size()];
		for (int i=0;i<a.length;i++)
		{
			a[i] = new Chromosome();
			int[] seq = l.get(i).getSequence().clone();
			int numMutations = rand.nextInt(seq.length / 10);
			for (int j = 0; j < numMutations; j++)
			{
				int indexToBother = rand.nextInt(seq.length);
				switch (rand.nextInt(3))
				{
				case 0:
					seq[indexToBother]++;
					break;
				case 1:
					seq[indexToBother]--;
					break;
				case 2:
					seq[indexToBother] = rand.nextInt(Integer.MAX_VALUE);
					break;
				}
			}
			a[i].setSequence(seq);
		}
		return a;
	}
	public static Chromosome[] splice(List<Chromosome> l)
	{
		Chromosome[] a = new Chromosome[l.size()];
		for (int i=0 ; i < a.length; i++)
		{
			a[i] = new Chromosome();
			int[] seq = l.get(i).getSequence().clone();
			int src =0;
			do
				{
				src = rand.nextInt(a.length);
				} while(src == i);
			int theirStartIndex = alignOn(rand.nextInt(l.get(src).getSequence().length),3);
			int myStartIndex = alignOn(rand.nextInt(seq.length),3);
			int numToCopy = min(rand.nextInt(seq.length / 2), (seq.length - myStartIndex));
			numToCopy = alignOn(min(numToCopy,l.get(src).getSequence().length - theirStartIndex),3);
			for (int j = 0; j < numToCopy; j++)
				seq[myStartIndex + j] = l.get(src).getSequence()[theirStartIndex + j]; 
			a[i].setSequence(seq);
		}
		return a;
	}
	private static int alignOn(int num, int alignment)
	{
		int i = (int) Math.floor(num / alignment);
		return alignment * i;
	}
	private static int min(int i, int j)
	{
		return i<j?i:j;
	}
}
