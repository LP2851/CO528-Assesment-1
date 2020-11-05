import java.lang.Math;
/**
 * The Population class for task 2. This handles a population of 500 candidate solutions and the creation of
 * new populations after each generation.
 * @author Lucas Phillips
 * @version 3/11/20
 */
public class PopulationTask2 {
    // Population Size
    private static int size = 500; //200; //500
    // Chance values for mutation and crossover.
    private static double mutationRate = 0.7D; //0.9D; //0.95
    private static double crossoverRate = 0.9D;

    //Highest possible weight value for a valid solution.
    private static int maxWeight = 500;
    // Counter for adding to new populations.
    private int addPosCounter;

    // All of the candidate solutions (the entire population).
    private Solution2Solution[] candidateSolutions;

    /**
     * Constructor for the PopulationTask2 class which fills up the solutions of the new object with the solutions
     * in a passed Solution2Solution[]
     * @param solutions An array containing all of the solutions for the new population.
     */
    public PopulationTask2(Solution2Solution[] solutions) {
        candidateSolutions = solutions;
        addPosCounter = size;
    }

    /**
     * Constructor for the PopulationTask2 class which creates an empty population of the correct size.
     */
    public PopulationTask2() {
        candidateSolutions = new Solution2Solution[size];
        addPosCounter = 0;
    }

    /**
     * Generates a new (random) population. Only used at the start of the algorithm to generate an
     * initial population.
     * @return A new PopulationTask2 object containing a new (random) population of candidate solutions.
     */
    public PopulationTask2 generatePopulation() {
        Solution2Solution[] solutions = new Solution2Solution[size];
        boolean[] sol1 = new boolean[100];
        // Creating the first generated solution which has everything false- to speed up solution finding.
        for (int i = 0; i < 100; i++) {
            sol1[i] = false;
        }
        solutions[0] = new Solution2Solution(sol1);

        // Generating random solutions.
        for (int i = 1; i < size; i++) { //1
            solutions[i] = new Solution2Solution();

        }
        return new PopulationTask2(solutions);
    }

    /**
     * Generates a new population based on a previous population. This keeps the fittest solution from the passed
     * population and also uses tournament selection, mutation and crossover.
     * @param lastPop The previous generations population
     * @return A new population generated from the previous generation using selection, mutation and crossover.
     */
    public PopulationTask2 generatePopulation(PopulationTask2 lastPop) {
        // Fittest solution from previous generation.
        Solution2Solution lastBestCandidateSolution = lastPop.getFittestSolution();
        PopulationTask2 newPop = new PopulationTask2();

        int pos = 1;
        newPop.add(lastBestCandidateSolution);
        while (pos < size) {
            // Tournament size is 4- getting four random candidate solutions.
            Solution2Solution t1 = lastPop.candidateSolutions[(int) (Math.random() * size)];
            Solution2Solution t2 = lastPop.candidateSolutions[(int) (Math.random() * size)];
            Solution2Solution t3 = lastPop.candidateSolutions[(int) (Math.random() * size)];
            Solution2Solution t4 = lastPop.candidateSolutions[(int) (Math.random() * size)];
            // Getting the fittest solution from the candidate solutions selected
            Solution2Solution newSolution = tournament(t1, t2, t3, t4);
            // Adding tournament winner to next generation
            newPop.add(newSolution); pos++;
            // Adding a mutated version of the new solution if in chance number.
            if (pos < size && Math.random() < mutationRate) {
                newPop.add(newSolution.mutate()); pos++;
            }
            // Adding a crossover version of the new solution if in chance number.
            if (pos < size && Math.random() < crossoverRate) {
                newPop.add(newSolution.crossover(lastPop.candidateSolutions[(int) (Math.random() * size)]));
            }
        }
        return newPop;

    }

    /**
     * Returns the fittest solution from the passed candidate solutions.
     * @param s1 First solution in the tournament
     * @param s2 Second solution in the tournament
     * @param s3 Third solution in the tournament
     * @param s4 Fourth solution in the tournament
     * @return The fittest solution from the four solutions passed.
     */
    public Solution2Solution tournament(Solution2Solution s1, Solution2Solution s2, Solution2Solution s3, Solution2Solution s4) {
        return tournament(tournament(s1, s2), tournament(s3, s4));
    }

    /**
     * Returns the fittest solution from the passed candidate solutions.
     * @param s1 First solution in the tournament.
     * @param s2 Second solution in the tournament
     * @return The fittest solution from the two solutions passed.
     */
    public Solution2Solution tournament(Solution2Solution s1, Solution2Solution s2) {
        double[] fitnessS1 = s1.getFitness();
        double[] fitnessS2 = s2.getFitness();

        // Checking weight is valid for both candidate solutions, if not then the other is returned.
        if(!(fitnessS1[0] <= maxWeight)) {
            return s2;
        } else if (!(fitnessS2[0] <= maxWeight)) {
            return s1;
        } else {
            // If fitness utility of solutions is the same then returns solution with lower weight.
            if (fitnessS1[1] == fitnessS2[1]) {
                return (fitnessS1[0] < fitnessS2[0]) ? s1 : s2;
            }
            // Returns solution with highest utility.
            return (fitnessS1[1] < fitnessS2[1]) ? s2 : s1;
        }
    }

    /**
     * Adding a solution to the population, only used in the generation of new populations.
     * @param sol The solution to be added to the population.
     */
    public void add(Solution2Solution sol) {
        //System.out.println("AddPosCounter: " + addPosCounter);
        if(addPosCounter < size) {
            candidateSolutions[addPosCounter] = sol;
            addPosCounter++;
        }
    }

    /**
     * Returns the fittest solution in the current population.
     * @return The fittest solution in the current population.
     */
    public Solution2Solution getFittestSolution() {
        Solution2Solution fittest = candidateSolutions[0];
        double bestFitness = fittest.getUtility();

        for (Solution2Solution s : candidateSolutions) {
            // If solution is less than max weight and solution has higher utility.
            if (s.getWeight() <= maxWeight && s.getUtility() > bestFitness) {
                fittest = s;
                bestFitness = s.getUtility();
            // If solution has the same utility and less weight.
            } else if (s.getUtility() == bestFitness) {
                if(s.getWeight() < fittest.getWeight()) {
                    fittest = s;
                    bestFitness = s.getUtility();
                }
            }
        }
        return fittest;
    }

}

