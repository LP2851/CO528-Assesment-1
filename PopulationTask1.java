/**
 * The Population class for task 1. This handles a population of 150 candidate solutions and the creation of
 * new populations after each generation.
 * @author Lucas Phillips
 * @version 3/11/20
 */
public class PopulationTask1 {
    // Population Size
    private static int size = 150; //500
    // Chance values for mutation and crossover.
    private static double mutationRate = 0.9D; //0.95
    private static double crossoverRate = 0.8D;
    // Counter for adding to new populations.
    private int addPosCounter = 0;

    // All of the candidate solutions (the entire population).
    private Solution1Solution[] candidateSolutions;

    /**
     * Constructor for the PopulationTask1 class which fills up the solutions of the new object with the solutions
     * in a passed Solution1Solution[]
     * @param solutions An array containing all of the solutions for the new population.
     */
    public PopulationTask1(Solution1Solution[] solutions) {
        candidateSolutions = solutions;
        addPosCounter = size;
    }

    /**
     * Constructor for the PopulationTask1 class which creates an empty population of the correct size.
     */
    public PopulationTask1() {
        addPosCounter = 0;
        candidateSolutions = new Solution1Solution[size];
    }

    /**
     * Returns the fittest solution in the current population.
     * @return The fittest solution in the current population.
     */
    public Solution1Solution getFittestSolution() {
        double bestFitness = candidateSolutions[0].getFitness();
        Solution1Solution fittest = candidateSolutions[0];
        for (Solution1Solution s : candidateSolutions) {
            if(s.getFitness() < bestFitness) {
                fittest = s;
            }
        }
        return fittest;
    }

    /**
     * Generates a new (random) population. Only used at the start of the algorithm to generate an
     * initial population.
     * @return A new PopulationTask1 object containing a new (random) population of candidate solutions.
     */
    public PopulationTask1 generatePopulation() {
        Solution1Solution[] solutions = new Solution1Solution[size];
        for (int i = 0; i < size; i++) {
            solutions[i] = new Solution1Solution();
        }
        return new PopulationTask1(solutions);
    }

    /**
     * Generates a new population based on a previous population. This keeps the fittest solution from the passed
     * population and also uses tournament selection, mutation and crossover (with the previous fittest).
     * @param lastPop The previous generations population
     * @return A new population generated from the previous generation using selection, mutation and crossover.
     */
    public PopulationTask1 generatePopulation(PopulationTask1 lastPop) {
        // Getting fittest solution from last generation
        Solution1Solution lastBestCandidateSolution = lastPop.getFittestSolution();
        PopulationTask1 newPop = new PopulationTask1();
        int pos = 1; // number of slots filled in new population
        newPop.add(lastBestCandidateSolution); // adding the fittest solution from the previous generation
        while (pos < size) {
            // Tournament size is 4- getting four random candidate solutions.
            Solution1Solution t1 = lastPop.candidateSolutions[(int) (Math.random() * size)];
            Solution1Solution t2 = lastPop.candidateSolutions[(int) (Math.random() * size)];
            Solution1Solution t3 = lastPop.candidateSolutions[(int) (Math.random() * size)];
            Solution1Solution t4 = lastPop.candidateSolutions[(int) (Math.random() * size)];
            // Getting the fittest solution from the candidate solutions selected
            Solution1Solution newSolution = tournament(t1, t2, t3, t4);
            // Adding tournament winner to next generation
            newPop.add(newSolution); pos++;
            // Adding a mutated version of the new solution if in chance number.
            if (pos < size && Math.random() < mutationRate) {
                newPop.add(newSolution.mutate()); pos++;
            }
            // Adding a crossover version of the new solution if in chance number.
            if (pos < size && Math.random() < crossoverRate) {
                newPop.add(newSolution.crossover(lastBestCandidateSolution)); pos++;
            }
        }
        return newPop;
    }

    /**
     * Adding a solution to the population, only used in the generation of new populations.
     * @param sol The solution to be added to the population.
     */
    public void add(Solution1Solution sol) {
        //System.out.println("AddPosCounter: " + addPosCounter);
        if(addPosCounter < size) {
            candidateSolutions[addPosCounter] = sol;
            addPosCounter++;
        }
    }

    /**
     * Returns the fittest solution from the passed candidate solutions.
     * @param s1 First solution in the tournament
     * @param s2 Second solution in the tournament
     * @param s3 Third solution in the tournament
     * @param s4 Fourth solution in the tournament
     * @return The fittest solution from the four solutions passed.
     */
    public Solution1Solution tournament(Solution1Solution s1, Solution1Solution s2, Solution1Solution s3, Solution1Solution s4) {
        return tournament(tournament(s1, s2), tournament(s3, s4));
    }

    /**
     * Returns the fittest solution from the passed candidate solutions.
     * @param sol1 First solution in the tournament.
     * @param sol2 Second solution in the tournament
     * @return The fittest solution from the two solutions passed.
     */
    public Solution1Solution tournament(Solution1Solution sol1, Solution1Solution sol2) {
        return (sol1.getFitness() < sol2.getFitness()) ? sol1 : sol2;
    }

}
