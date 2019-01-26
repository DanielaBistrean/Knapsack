import java.util.Vector;
import java.io.*;

public class KnapsackGaSolver
{
    final static double GA_ELITE_RATE = 0.10;
    final static double GA_MUTATION_PROB = 0.10;

    public static void main(String[] args)
    {
        Knapsack problem;
        int numIterations;
        int numIndividuals;

		if (args.length < 3)
        {
			System.out.println("Usage: java knapsack.KnapsackGaSolver <problem file> <# iterations> <# individuals>");
            System.out.println("Using defaults!");

            problem = Knapsack.parseProblem("input.txt");
            numIterations = 8;
            numIndividuals = 100;
		}
        else
        {
            problem = Knapsack.parseProblem(args[0]);
            numIterations = Integer.parseInt(args[1]);
            numIndividuals = Integer.parseInt(args[2]);
        }


        Population population = new Population(numIndividuals, problem.getNumItems());
        
        for (int i = 0; i < numIterations; i++)
        {
            Vector individuals = population.getIndividuals();

            for (int j = 0; j < individuals.size(); j++)
            {
                Individual individual = (Individual)individuals.get(j);
                double fitness = problem.calcFitness(individual);
            }

            population.calcStats();
            System.out.println("Iteration = " + i +
                                ", Fitness (best = " + population.getBestFitness() +
                                ", avrg = " + population.getAvrgFitness() +
                                ", worst = " + population.getWorstFitness() + ")");
            
            population.reproduction(GA_ELITE_RATE, GA_MUTATION_PROB);
        }

        Individual best = population.getBestIndividual();
        System.out.println();
        System.out.println("Maximum Knapsack Weight: " + problem.getCapacity());
        System.out.println("Total Item Weight: " + best.getTotalWeight());
        System.out.println("Total Item Value: " + best.getTotalValue());
        System.out.println("Selected Items (item ID, weight, value):");

        String[] ids = problem.getIds();
        int[] weights = problem.getWeights();
        int[] values = problem.getValues();
        boolean[] chromosome = best.getChromosome();
        for (int i = 0; i < chromosome.length; i++)
        {
            if (chromosome[i]) 
                System.out.println(ids[i] + ", " + weights[i] + ", " + values[i]);
        }
    }  
}
