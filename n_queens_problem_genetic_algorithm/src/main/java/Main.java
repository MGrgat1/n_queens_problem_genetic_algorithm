public class Main {

    public static double totalTime = 0.0;
    public static int totalIterations = 0;

    private static void runGeneticAlgorithm(int nQueens, int populationSize, int runNumber) {

        int numberOfTournaments = 60;
        int tournamentSize = 5;
        int numberOfMutants = 10;
        int numberOfMutationsOnOneMutant = 5;

        int maxIterations = 100000;


        System.out.println("[INFO] Entering run number " + runNumber);

        System.out.println("[INFO] Initializing genetic algorithm:");
        System.out.println("[INFO] initial population size: " + nQueens + "," + populationSize);
        System.out.println("[INFO] Number of tournaments, tournament size: " + numberOfTournaments + "," + tournamentSize);
        System.out.println("[INFO] Number of mutants in one generation: " + numberOfMutants);
        System.out.println("[INFO] Number of mutations on one mutant: " + numberOfMutationsOnOneMutant);

        long startTime = System.currentTimeMillis();

        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm().startInitialPopulation(nQueens, populationSize)
                .startTournaments(numberOfTournaments, tournamentSize)
                .startCrossoversAndMutations(populationSize, numberOfMutants, numberOfMutationsOnOneMutant)
                .run(maxIterations);

        long stopTime = System.currentTimeMillis();
        long runningTime = stopTime - startTime;

        Chessboard solution = geneticAlgorithm.getSolution();
        int numberOfIterations = geneticAlgorithm.getNumberOfIterations();
        double averageFitness = geneticAlgorithm.getAverageFitness();
        double maxFitness = geneticAlgorithm.getMaxFitness();

        boolean solutionFound = false;
        if (solution.getFitness() == 1) {
            solutionFound = true;
        }

        System.out.println("");
        System.out.println("[INFO] Algorithm finished!");
        if(solutionFound) {
            System.out.println("[INFO] Solution found!");
        } else {
            System.out.println("[INFO] Solution not found...");
        }
        System.out.println("[INFO] Calculation time: " + runningTime + "ms");
        System.out.println("[INFO] Number of iterations until solution found: " + numberOfIterations);
        System.out.println("[INFO] Average fitness and max fitness of the final population: " + averageFitness + "," + maxFitness);
        System.out.println("");
        System.out.println("[INFO] Solution:");
        solution.print();


        totalTime += runningTime;
        totalIterations += numberOfIterations;
    }
    
    public static void main(String[] args) {

        int nQueens = 8;
        int populationSize = 100;
        int numberOfRuns = 10;


        System.out.println("[INFO] Entering genetic algorithm runs:");
        for (int i = 1; i <= numberOfRuns; i++) {
            runGeneticAlgorithm(nQueens, populationSize, i);
        }

        System.out.println("[INFO] Runs finished!");
        double averageTime = totalTime/(double) numberOfRuns;
        double averageIterations = totalIterations/(double) numberOfRuns;

        System.out.println("[INFO] Number of runs: " + numberOfRuns);
        System.out.println("Average runtime: " + averageTime + " ms");
        System.out.println("Average iteration of a single run: " + averageIterations);


    }



}
