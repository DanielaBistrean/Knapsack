import java.io.*;

public class Knapsack
{
    private int numItems = 0;
    private String[] ids;
    private int[] weights;
    private int[] values;
    private int capacity = 0;

    Knapsack(int numItems, int capacity)
    {
        this.numItems = numItems;
        this.ids = new String[numItems + 1];
        this.weights = new int[numItems + 1];
        this.values = new int[numItems + 1];
        this.capacity = capacity;
    }

    static Knapsack parseProblem(String file)
    {
        Knapsack problem = null;

		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
            int capacity = Integer.parseInt(in.readLine());
			int numItems = Integer.parseInt(in.readLine());
            problem = new Knapsack(numItems, capacity);

			int i = 0;
			String str;
	    	while ((str = in.readLine())!= null) {
                String[] item = str.split(",");
                problem.ids[i] = item[0];
                problem.weights[i] = Integer.parseInt(item[1]);
                problem.values[i] = Integer.parseInt(item[2]);

                i++;
			}
			in.close();
		} catch (IOException ex) {
			System.err.println("Error: Can't open the file " + file + " for reading.");
		}
        return problem;
    }

    double calcFitness(Individual individual)
    {
        int totalValue = 0, totalWeight = 0;
        double fitness = 0.0;
        boolean overweight = false;
        boolean[] solution = individual.getChromosome();

        for (int i = 0; i < solution.length; i++)
        {
            if (solution[i])
            {
                totalValue += values[i];
                totalWeight += weights[i];
            }

            if (capacity < totalWeight)
            {
                overweight = true;
                break;
            }
        }

        fitness = (overweight) ? 0.0 : (double)totalValue;

        individual.setFitness(fitness);
        individual.setTotalWeight(totalWeight);
        individual.setTotalValue((int)fitness);

        return fitness;
    }

    int getNumItems()
    {
        return numItems;
    }

    String[] getIds()
    {
        return ids;
    }

    int[] getWeights()
    {
        return weights;
    }

    int[] getValues()
    {
        return values;
    }

    int getCapacity()
    {
        return capacity;
    } 
}
