import java.lang.Math;

/**
 * Class for the candidate solutions for task 2.
 * @author Lucas Phillips
 * @version 3/11/20
 */
public class Solution2Solution {
    // Length of the array for each solution.
    private static int size = 100;

    // The solution.
    private boolean[] solution;

    /**
     * Constructor for Solution2Solution that generates a random solution.
     */
    public Solution2Solution() {
        solution = new boolean[size];
        for (int i = 0; i < solution.length; i++) {
            solution[i] = (Math.random() > 0.5);
        }
    }

    /**
     * Constructor for Solution2Solution that represents the passed solution.
     * @param sol The solution represented by the passed object.
     */
    public Solution2Solution(boolean[] sol) {
        solution = sol;
    }

    /**
     * Handles mutation of a Solution2Solution object creating a new Solution2Solution object.
     * @return A mutated version of the current candidate solution.
     */
    public Solution2Solution mutate() {
        boolean[] mutatedSolution = new boolean[size];
        int mutationPos = (int) (Math.random() * size);
        int mutationPos2 = (int) (Math.random() * size);
        int mutationPos3 = (int) (Math.random() * size);

        for (int i = 0; i < size; i++) {
            if (i == mutationPos) {
                mutatedSolution[i] = !solution[i];
            } else if (i == mutationPos2 && getUtility() < 208) { // 200
                mutatedSolution[i] = !solution[i];
            }  else if (i == mutationPos3 && getUtility() < 210) { //205
                mutatedSolution[i] = !solution[i];
            } else {
                mutatedSolution[i] = solution[i];
            }
        }
        return new Solution2Solution(mutatedSolution);
    }

    /**
     * Handles crossover of two candidate solutions.
     * @param sol The other solution to be crossed over with.
     * @return A new solution made from this object and the passed object.
     */
    public Solution2Solution crossover(Solution2Solution sol) {
        int crossoverPos = 10;
        boolean[] newSolution = new boolean[size];
        boolean[] solution2 = sol.getSolution();
        for (int i = 0; i < size; i++) {
            if(i < crossoverPos) {
                newSolution[i] = solution[i];
            } else {
                newSolution[i] = solution2[i];
            }
        }
        return new Solution2Solution(newSolution);
    }

    /**
     * Returns the candidate solution.
     * @return The candidate solution.
     */
    public boolean[] getSolution() {
        return solution;
    }

    /**
     * Returns the fitness of the candidate solution.
     * @return The fitness of the candidate solution.
     */
    public double[] getFitness() {
        return Assess.getTest2(solution);
    }

    /**
     * Returns the weight of the candidate solution.
     * @return The weight of the candidate solution.
     */
    public double getWeight() {
        return getFitness()[0];
    }

    /**
     * Returns the utility of the candidate solution.
     * @return The utility of the candidate solution.
     */
    public double getUtility() {
        return getFitness()[1];
    }



}
