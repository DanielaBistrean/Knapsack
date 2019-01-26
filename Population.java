
import java.io.*;
import java.util.*;

public class Population
{
    private Vector individuals;
    private double avrgFitness;
    private double bestFitness;
    private double worstFitness;
    private Individual best;

    Population()
    {
        individuals = new Vector();
    }

    Population(int numIndividuals, int chromosomeLength)
    {
        individuals = new Vector();
        for (int i = 0; i < numIndividuals; i++)
            individuals.add(new Individual(chromosomeLength));
    }

    void calcStats()
    {
        double sumFitness = 0.0;
        bestFitness = 0.0;
        worstFitness = Double.MAX_VALUE;

        for (int i = 0; i < individuals.size(); i++)
        {
            double fitness = ((Individual)individuals.get(i)).getFitness();
            sumFitness += fitness;

            if (bestFitness < fitness)
            {
                bestFitness = fitness;
                best = (Individual)individuals.get(i);
            }

            if (fitness < worstFitness)
                worstFitness = fitness;
        }
        avrgFitness = sumFitness / individuals.size();
    }

    void selectElite(double eliteRate, Vector elites)
    {
        for (int i = 0; i < individuals.size() * eliteRate; i++)
        {
            elites.add(individuals.get(i));
        }
    }

    Individual selectRoulette()
    {
        int i;
        double sumFitness = 0.0;

        for (i = 0; i < individuals.size(); i++)
            sumFitness += ((Individual)individuals.get(i)).getFitness();
        
        Random rand = new Random();
        double rouletteFitness = rand.nextDouble() * sumFitness;

        sumFitness = 0.0;
        for (i = 0; i < individuals.size(); i++)
        {
            sumFitness += ((Individual)individuals.get(i)).getFitness();
            if (rouletteFitness < sumFitness)
                break;
        }

        if (i == individuals.size()) 
            i--;

        return (Individual)individuals.get(i);
    }

    void reproduction(double eliteRate, double mutationProb)
    {
        Vector next = new Vector();

        Comparator myComparator = new Comparator()
        {
                public int compare(Object o1, Object o2)
                {
                    double fitness1 = ((Individual) o1).getFitness();
                    double fitness2 = ((Individual) o2).getFitness();
                    return Double.compare(fitness2, fitness1);
                }
            };
        Collections.sort(individuals, myComparator);
        
        selectElite(eliteRate, next);

        Random rand = new Random();
        for (int i = next.size(); i < individuals.size(); i++)
        {
            Individual parent1 = selectRoulette();
            Individual parent2 = selectRoulette();
            Individual child = new Individual(parent1.getChromosome().length);
            child.crossover(parent1, parent2);

            if (rand.nextDouble() < mutationProb)
                child.mutation();

            next.add(child);
        }

        individuals = next;
    }


    Vector getIndividuals()
    {
        return individuals;
    }

    double getBestFitness()
    {
        return bestFitness;
    }

    double getAvrgFitness()
    {
        return avrgFitness;
    }

    double getWorstFitness()
    {
        return worstFitness;
    }

    Individual getBestIndividual()
    {
        return best;
    }
}
