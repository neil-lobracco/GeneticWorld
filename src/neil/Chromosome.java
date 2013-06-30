package neil;

import java.util.Arrays;

public class Chromosome implements Comparable<Chromosome>
{
	protected double fitness = -1;
	protected int[] sequence = null;
	
	public double getFitness()
	{
		return fitness;
	}
	public void setFitness(double d)
	{
		fitness = d;
	}
	
	@Override
	public String toString()
	{
		return "Chromosome [fitness=" + fitness + ", sequence="
				+ Arrays.toString(sequence) + "]";
	}
	public int[] getSequence()
	{
		return sequence;
	}
	public void setSequence(int[] seq)
	{
		sequence = seq;
	}
	@Override
	public int compareTo(Chromosome other)
	{
		if (Double.isNaN(fitness))
			return -1;
		if (Double.isNaN(other.getFitness()))
			return 1;
		return (fitness >= other.getFitness() ? 1 : -1);
	}
	
}
