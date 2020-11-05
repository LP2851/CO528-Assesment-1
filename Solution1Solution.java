import java.lang.Math;
import java.util.*;

/**
 * Class for the candidate solutions for task 1.
 * @author Lucas Phillips
 * @version 3/11/20
 */
public class Solution1Solution {
    // Length of each solution array
    private static int solutionSize = 20;
    // The solution
    private double[] solution;

    /**
     * Constructor for the Solution1Solution class generating a random solution.
     */
    public Solution1Solution() {
        solution = new double[solutionSize];
        for(int j=0; j<solutionSize;j++){
            solution[j] = Math.random()*Math.round(5.12*(Math.random() - Math.random()));
        }
    }

    /**
     * Constructor for the Solution1Solution class that is passed a solution to represent.
     * @param solution The solution for the new object to represent.
     */
    public Solution1Solution(double[] solution) {
        this.solution = solution;
    }


    /**
     * Generates a single value for a solution (one gene).
     * This method is more random than the generateSubSolution(double currentValue) method.
     * @return A double value representing a gene in the candidate solution.
     */
    private double generateSubSolution() {
        return Math.random()*Math.round(5.12*(Math.random() - Math.random()));
    }

    /**
     * Generates a single value for a solution based on the current value.
     * @param currentValue The current value of the gene.
     * @return Value that is close to the current value of the gene passed.
     */
    private double generateSubSolution(double currentValue) {
        double out;
        double smallPercent = currentValue * 0.00001 * Math.random();
        if (Math.random() < 0.5) {
            out = currentValue + smallPercent;
        } else {
            out = currentValue - smallPercent;
        }
        if (out > 5) out = 5;
        else if (out < -5) out = -5;
        return out;
        //return Math.random()*Math.round(5.12*(Math.random() - Math.random()));
    }

    /**
     * Handles mutation of a Solution1Solution object creating a new Solution1Solution object.
     * @return A mutated version of the current candidate solution.
     */
    public Solution1Solution mutate() {
        double[] mutatedSolution = new double[solutionSize];
        // Position of the mutation
        int mutationPosition = (int) (Math.random() *solutionSize );
        for(int i = 0; i < solutionSize; i++) {
            if (i == mutationPosition) {
                // Changes the strength of the mutation based on how close to best possible solution it is .
                mutatedSolution[i] = (getFitness() > 0.01) ? generateSubSolution() : generateSubSolution(solution[i]);
            } else {
                // Copying values from last solution to new solution
                mutatedSolution[i] = solution[i];
            }
        }
        return new Solution1Solution(mutatedSolution);
    }

    /**
     * Handles crossover of two candidate solutions.
     * @param s The other solution to be crossed over with.
     * @return A new solution made from this object and the passed object.
     */
    public Solution1Solution crossover(Solution1Solution s) {
        int crossoverPos = 10; // position of the crossover
        double[] newSolution = new double[solutionSize];
        double[] solution2 = s.getSolution();
        for (int i = 0; i < solutionSize; i++) {
            if(i < crossoverPos) {
                newSolution[i] = solution[i];
            } else {
                newSolution[i] = solution2[i];
            }
        }
        return new Solution1Solution(newSolution);
    }

    /**
     * Returns the candidate solution.
     * @return The candidate solution.
     */
    public double[] getSolution() {
        return solution;
    }

    /**
     * Returns the fitness of the candidate solution.
     * @return The fitness of the candidate solution.
     */
    public double getFitness() {
        return Assess.getTest1(solution);
    }

}