//This is my example Solution
import java.lang.Math;
import java.util.*;

class Example {

		public static void main(String[] args){
			//Do not delete/alter the next line
		    long startT=System.currentTimeMillis();

			//Edit this according to your name and login
			String name="Lucas Phillips";
			String login = "lp496";

			System.out.println("These are the instructions of how to use the problem library.  Please make sure you follow the instructions carefully.");


			System.out.println("For the first problem, you need to use Assess.getTest1(double[]).");
		
			//An example solution consists of an array  of doubles of size 20 
			//Allowed values are between -5 and +5 (this is not actually checked, but no point in going beyond these values)
			//Lower fitness is better. The optimal fitness is 0 (i.e. no negative fitness values). 
			//int solSize=20;
			//generate a sample solution like so:
			 
			//double[] sol1= new double[solSize];
			//for(int j=0; j<solSize;j++){sol1[j] = Math.random()*Math.round(5.12*(Math.random() - Math.random()));}
			

			//get the fitness for a candidate solution in problem 1 like so
			Solution1Solution fittest = solution1();
			double[] sol1 = fittest.getSolution();
			double fit = Assess.getTest1(sol1);

			
			System.out.println("The fitness of your example Solution is: "+ fit);

				
			System.out.println(" ");	
			System.out.println(" ");	
			System.out.println("Now let us turn to the second problem:");
			System.out.println("A sample solution in this case is a boolean array of size 100.");
			System.out.println("I now create a random sample solution and get the weight and utility:");



			//Creating a sample solution for the second problem
			//The higher the fitness, the better, but be careful of  the weight constraint!
			boolean[] sol2;

			Solution2Solution fittestS2 = solution2();
			sol2 = fittestS2.getSolution();
			//Now checking the fitness of the candidate solution
			double[] tmp =(Assess.getTest2(sol2));

			//The index 0 of tmp gives the weight. Index 1 gives the utility
			System.out.println("The weight is: " + tmp[0]);
			System.out.println("The utility is: " + tmp[1]);

			//Once completed, your code must submit the results you generated, including your name and login: 
			//Use and adapt  the function below:
			//Assess.checkIn(name,login,sol1,sol2);
      
		    //Do not delete or alter the next line
		    	long endT= System.currentTimeMillis();
			System.out.println("Total execution time was: " +  ((endT - startT)/1000.0) + " seconds");
	  }

	  private static Solution1Solution solution1() {
			PopulationTask1 population = new PopulationTask1();
			population = population.generatePopulation();
			while (population.getFittestSolution().getFitness() > 0) {
				population = population.generatePopulation(population);
			}

			return population.getFittestSolution();
	  }

	  private static Solution2Solution solution2() {
			PopulationTask2 population = new PopulationTask2().generatePopulation();
		  	//System.out.println("Fittest Solution: " + population.getFittestSolution().getUtility());
			double startTime = System.currentTimeMillis();
			while (population.getFittestSolution().getUtility() < 209 || population.getFittestSolution().getFitness()[0] > 500) {
				//System.out.println("Fittest Solution: " + population.getFittestSolution().getUtility() + "  Weight: " + population.getFittestSolution().getWeight());
				population = population.generatePopulation(population);
				double currentTime = System.currentTimeMillis();
				if ((currentTime-startTime) /1000 > 2.5 && population.getFittestSolution().getUtility() > 208) {
					return population.getFittestSolution();
				}
			}

			return population.getFittestSolution();
	  }
}
