import java.io.*;

public class KnapsackDpSolver
{
    private static int capacity;
    private static int numItems;
    private static int[] ids;
    private static int[] weights;
    private static int[] values;

    public static void parseProblem(String file) {
		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
            capacity = Integer.parseInt(in.readLine());
			numItems = Integer.parseInt(in.readLine());
            ids = new int[numItems + 1];
            weights = new int[numItems + 1];
            values = new int[numItems + 1];

			int i = 1;
			String str;
	    	while ((str = in.readLine())!= null)
            {
                String[] item = str.split(",");
                ids[i] = Integer.parseInt(item[0]);
                weights[i] = Integer.parseInt(item[1]);
                values[i] = Integer.parseInt(item[2]);

                i++;
			}
			in.close();
		} catch (IOException ex)
        {
			System.err.println("Error: Can't open the file " + file + " for reading.");
		}
    }

    public static void main(String[] args)
    {
        if (args.length < 1)
        {
			System.out.println("Usage: java knapsack.KnapsackDpSolver <problem file>");
			return;
		}          

        parseProblem(args[0]);

        int[][] opt = new int[numItems+1][capacity+1];
        boolean[][] sol = new boolean[numItems+1][capacity+1];

        for (int n = 1; n <= numItems; n++)
        {
            for (int w = 1; w <= capacity; w++)
            {
                int option1 = opt[n-1][w];

                int option2 = Integer.MIN_VALUE;
                if (weights[n] <= w) option2 = values[n] + opt[n-1][w-weights[n]];

                opt[n][w] = Math.max(option1, option2);
                sol[n][w] = (option2 > option1);
            }
        }

        boolean[] take = new boolean[numItems+1];
        for (int n = numItems, w = capacity; n > 0; n--)
        {
            if (sol[n][w])
            {
                take[n] = true;
                w = w - weights[n];
            }
            else
            {
                take[n] = false;
            }
        }

        System.out.println("Maximum Knapsack Weight: " + capacity);
        int totalWeight = 0, totalValue = 0;
        for (int n = 1; n <= numItems; n++)
        {
            if (take[n])
            {
                totalWeight += weights[n];
                totalValue += values[n];
            }
        }
        System.out.println("Total Item Weight: " + totalWeight);
        System.out.println("Total Item Value: " + totalValue);
        System.out.println("Selected Items (item ID, weight, value):");
        for (int n = 1; n <= numItems; n++)
        {
            if (take[n]) 
                System.out.println(ids[n] + ", " + weights[n] + ", " + values[n]);
        }
    }
}
