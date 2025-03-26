import java.util.*;

public class Main {
    private static double[][] distances;
    private static int[] currentSolution;
    private static Random random = new Random();

    public static void main(String[] args) {
        double[][] coordinates = {
                {16.47, 96.10}, {16.47, 94.44}, {20.09, 92.54}, {22.39, 93.37},
                {25.23, 97.24}, {22.00, 96.05}, {20.47, 97.02}, {17.20, 96.29},
                {16.30, 97.38}, {14.05, 98.12}, {16.53, 97.38}, {21.52, 95.59},
                {19.41, 97.13}, {20.09, 94.55}
        };

        initializeDistances(coordinates);
        currentSolution = new int[14];
        for (int i = 0; i < 14; i++) currentSolution[i] = i + 1;
        shuffleArray(currentSolution);

        double temp = 10000;
        double coolingRate = 0.003;
        double bestFitness = calculateFitness(currentSolution);

        while (temp > 1) {
            int[] newSolution = Arrays.copyOf(currentSolution, currentSolution.length);
            int pos1 = random.nextInt(newSolution.length);
            int pos2 = random.nextInt(newSolution.length);
            int tempNode = newSolution[pos1];
            newSolution[pos1] = newSolution[pos2];
            newSolution[pos2] = tempNode;

            double currentEnergy = calculateFitness(currentSolution);
            double newEnergy = calculateFitness(newSolution);

            if (acceptanceProbability(currentEnergy, newEnergy, temp) > Math.random()) {
                currentSolution = newSolution;
            }

            if (calculateFitness(currentSolution) < bestFitness) {
                bestFitness = calculateFitness(currentSolution);
                System.out.println("Best fitness: " + bestFitness);
            }

            temp *= 1 - coolingRate;
        }

        System.out.println("Best solution fitness: " + bestFitness);
    }

    private static void initializeDistances(double[][] coordinates) {
        distances = new double[14][14];
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 14; j++) {
                double dx = coordinates[i][0] - coordinates[j][0];
                double dy = coordinates[i][1] - coordinates[j][1];
                distances[i][j] = Math.sqrt(dx * dx + dy * dy);
            }
        }
    }

    private static double calculateFitness(int[] solution) {
        double fitness = 0;
        for (int i = 0; i < solution.length - 1; i++) {
            fitness += distances[solution[i] - 1][solution[i + 1] - 1];
        }
        fitness += distances[solution[solution.length - 1] - 1][solution[0] - 1];
        return fitness;
    }

    private static void shuffleArray(int[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    private static double acceptanceProbability(double currentEnergy, double newEnergy, double temp) {
        return newEnergy < currentEnergy ? 1.0 : Math.exp((currentEnergy - newEnergy) / temp);
    }
}