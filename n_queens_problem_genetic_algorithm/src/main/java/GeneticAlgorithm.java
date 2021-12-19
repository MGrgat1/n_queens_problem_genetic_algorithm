import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GeneticAlgorithm {

    private List<Chessboard> generation;

    private Tournament tournament;
    private CrossoverAndMutation crossoverAndMutation;

    private int numberOfIterations = 0;
    private Chessboard solution;

    private double averageFitness;
    private double maxFitness;

    /**
     * Generate a population of chessboard with random configurations of n Queens
     * @param nQueens
     * @param populationSize
     * @return Returns the algorithm so it can be continued
     */
    public GeneticAlgorithm startInitialPopulation(int nQueens, int populationSize) {


        generation = Stream.generate(() -> Chessboard.initializeWithRandomQueens(nQueens))
                .limit(populationSize)
                .collect(Collectors.toList());

        //System.out.println("First generation:");
        //System.out.println("Average and max fitness: " + FitnessCalculator.getAverageFitness(generation) + "," + FitnessCalculator.getMaxFitness(generation));

        //double averageFitness;
        //double maxFitness;

        return this;
    }

    /**
     * Select a few chessboards to attend a tournament, and sort them in it
     * @param tournamentSize Number of chessboards selected for the tournament
     * @return
     */
    public GeneticAlgorithm startTournaments(int numberOfTournaments, int tournamentSize) {

        tournament = new Tournament(numberOfTournaments, tournamentSize);

        return this;
    }

    /**
     * Sets crossovers and mutations
     */
    public GeneticAlgorithm startCrossoversAndMutations(int nextGenerationSize, int numberOfMutants, int numberOfMutationsOnOneMutant) {

        crossoverAndMutation = new CrossoverAndMutation(nextGenerationSize, numberOfMutants, numberOfMutationsOnOneMutant);

        return this;
    }

    /**
     * Runs the algorithm
     * @return
     */
    public GeneticAlgorithm run(int maxNumberOfIterations) {

        do {

            List<Chessboard> tournamentWinners = new ArrayList<>();

            for (int i = 0; i < tournament.numberOfTournaments; i++) {
                tournamentWinners.add(tournament.chooseContestants(generation).determineWinner());
            }


            generation = crossoverAndMutation
                    .createNextGeneration(tournamentWinners)
                    .createMutations()
                    .getNextGeneration();

            //System.out.println("Next generation:");
            //generation.forEach(Chessboard::print);
            averageFitness = FitnessCalculator.getAverageFitness(generation);
            maxFitness = FitnessCalculator.getMaxFitness(generation);
            //System.out.println("Average and max fitness: " + averageFitness + "," + maxFitness);

            numberOfIterations++;

            if(numberOfIterations >= maxNumberOfIterations)
                break;

        } while (maxFitness < 1.0);

        solution = generation.stream().max(Comparator.comparing(Chessboard::getFitness))
                .orElseGet(() -> new Chessboard(4, 0));

        return this;

    }

    public Chessboard getSolution() {
        return solution;
    }

    public int getNumberOfIterations() {
        return numberOfIterations;
    }

    public double getAverageFitness() {
        return averageFitness;
    }

    public double getMaxFitness() {
        return maxFitness;
    }
}
