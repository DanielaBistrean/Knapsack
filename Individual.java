import java.io.*;
import java.util.*;

public class Individual
{
    private boolean[] chromosome;
    private double fitness;
    private int totalValue; 
    private int totalWeight;

    Individual()
    {
        chromosome = null;        
        fitness = 0.0;
    }

    Individual(int chromosomeLength)
    {
        chromosome = new boolean[chromosomeLength];
        Random rand = new Random();
        double threshold = rand.nextDouble();

        for (int i = 1; i < chromosome.length; i++)
        {
            chromosome[i] = (rand.nextDouble() < threshold) ? true : false;
        }
    }
        
    void setFitness(double fitness)
    {
        this.fitness = fitness;
    }

    double getFitness()
    {
        return fitness;
    }

    int getTotalValue()
    {
        return totalValue;
    }

    void setTotalValue(int totalValue)
    {
        this.totalValue = totalValue;
    }

    int getTotalWeight()
    {
        return totalWeight;
    }

    void setTotalWeight(int totalWeight)
    {
        this.totalWeight = totalWeight;
    }

    void crossover(Individual parent1, Individual parent2)
    {
        Random rand = new Random();
        int pos = rand.nextInt(parent1.getChromosome().length);

        for (int i = 0; i < pos; i++) 
            chromosome[i] = (parent1.getChromosome())[i];
        for (int i = pos; i < chromosome.length; i++)
            chromosome[i] = (parent2.getChromosome())[i];
    }

    void mutation()
    {
        Random rand = new Random();

        int pos = rand.nextInt(chromosome.length);
        chromosome[pos] = chromosome[pos] ? false : true;
    }

    boolean[] getChromosome()
    {
        return chromosome;
    }

    void print()
    {
        for (int i = 0; i < chromosome.length; i++)
        {
            if (chromosome[i])
                System.out.print("1");
            else
                System.out.print("0");
        }
        System.out.println(", totalValue = " + totalValue + ", totalWeight = " + totalWeight);
    }
}       
