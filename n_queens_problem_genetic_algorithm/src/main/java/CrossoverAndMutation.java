import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CrossoverAndMutation {

    private static long seed = System.currentTimeMillis();

    private List<Chessboard> nextGeneration;
    private int nextGenerationSize;
    private int numberOfMutants;
    private int numberOfMutationsOnOneMutant;


    public CrossoverAndMutation(int nextGenerationSize, int numberOfMutants, int numberOfMutationsOnOneMutant) {
        this.nextGenerationSize = nextGenerationSize;
        this.numberOfMutants = numberOfMutants;
        this.numberOfMutationsOnOneMutant = numberOfMutationsOnOneMutant;
    }

    public CrossoverAndMutation createNextGeneration(List<Chessboard> winners) {

        //pick randomly 2 contestants, and cross them over
        Random random = new Random(seed);
        seed++;
        List<Chessboard> nextGeneration = new ArrayList<>();

        for (int i = 0; i < nextGenerationSize; i++) {
            Chessboard winner1 = winners.get(Math.abs(random.nextInt() % winners.size()));
            Chessboard winner2 = winners.get(Math.abs(random.nextInt() % winners.size()));

            nextGeneration.add(createOffspring(winner1, winner2));
        }

        this.nextGeneration = nextGeneration;

        return this;

    }

    public Chessboard createOffspring(Chessboard parent1, Chessboard parent2) {

        Random random = new Random(seed);

        int crossoverMax = parent1.getBoardSize();
        int crossoverIndex = Math.abs(random.nextInt() % crossoverMax);

        List<Integer> offspringSquares = new ArrayList<>();
        offspringSquares.addAll(parent1.getSquares().subList(0, crossoverIndex));
        offspringSquares.addAll(parent2.getSquares().subList(crossoverIndex, crossoverMax));


        return new Chessboard(parent1.getnQueens(), offspringSquares, "offspring");
    }

    public CrossoverAndMutation createMutations() {

        Random random = new Random(seed);
        seed++;
        //System.out.println("Mutations at: ");
        for (int i = 0; i < numberOfMutants; i++) {

            //pick the chessboard
            int mutantChessboardIndex = Math.abs(random.nextInt() % nextGeneration.size());
            Chessboard chessboardToBeMutated = nextGeneration.get(mutantChessboardIndex);

            //mutate the Square
            Chessboard mutantChessboard = createMutant(chessboardToBeMutated, numberOfMutationsOnOneMutant);

            nextGeneration.add(mutantChessboardIndex, mutantChessboard);

        }


        return this;
    }

    /**
     * Creates a mutated chessboard. Mutating a square will change the HVLR and fitness function values
     * of the whole chessbaord. This is taken care of by returning a constructor.
     * The HVLR and fitness values will be modified through the constructor.
     * @return
     */
    public Chessboard createMutant (Chessboard chessboard, int numberOfMutationsOnOneMutant) {

        Random random = new Random(seed);
        seed++;

        List<Integer> squares = chessboard.getSquares();

        for (int i = 0; i < numberOfMutationsOnOneMutant; i++) {
            //we swap the values of two squares
            int indexOfSquare1 = Math.abs(random.nextInt() % chessboard.getBoardSize());
            int indexOfSquare2 = Math.abs(random.nextInt() % chessboard.getBoardSize());
            int valueOfSquare1 = squares.get(indexOfSquare1);
            int valueOfSquare2 = squares.get(indexOfSquare2);
            squares.set(indexOfSquare1, valueOfSquare2);
            squares.set(indexOfSquare2, valueOfSquare1);
        }

        return new Chessboard(chessboard.getnQueens(), squares, "mutant");


    }

    public List<Chessboard> getNextGeneration() {
        return nextGeneration;
    }
}
