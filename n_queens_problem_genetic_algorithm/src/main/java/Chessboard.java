import java.util.*;

public final class Chessboard {

    private final int nQueens;
    private final int boardSize;
    private final List<Integer> squares;
    private final String status;

    private boolean isEmpty;

    private double fitness;

    /**
     * HVLR (Horizontals, Verticals, Left diagonals, Right diagonals) - sets that contain information about how
     * the queens are spread horizontally, vertically and diagonally
     */
    Set<Integer> horizontals = new HashSet<>();
    Set<Integer> verticals = new HashSet<>();
    Set<Integer> rightDiagonals = new HashSet<>();
    Set<Integer> leftDiagonals = new HashSet<>();

    //constructing a board filled with a value
    public Chessboard(int nQueens, int zeroValue) {
        this.nQueens = nQueens;
        this.boardSize = nQueens*nQueens;
        this.status = "zero";
        ArrayList<Integer> squares = new ArrayList<>(Arrays.asList(new Integer[boardSize]));
        Collections.fill(squares, zeroValue);
        this.squares = squares;
        this.isEmpty = true;
        this.fitness = -1;

    }

    /**
     * Initializing without status.
     * @param nQueens
     * @param squares
     */
    public Chessboard(int nQueens, List<Integer> squares) {
        this.nQueens = nQueens;
        this.boardSize = nQueens*nQueens;
        this.squares = squares;
        this.status = "no status";
        this.isEmpty = false;
        setHVLR();
        this.fitness = FitnessCalculator.calculateFitness(this);

    }

    /**
     * Initializing with status
     * @param nQueens
     * @param squares
     * @param status
     */
    public Chessboard(int nQueens, List<Integer> squares, String status) {
        this.nQueens = nQueens;
        this.boardSize = nQueens*nQueens;
        this.squares = squares;
        this.status = status;
        this.isEmpty = false;
        setHVLR();
        this.fitness = FitnessCalculator.calculateFitness(this);

    }

    private void setHVLR() {
        for (int i = 0; i < nQueens; i++) {
            for (int j = 0; j < nQueens; j++) {
                if (isThereAQueenAt(i, j)) {
                    horizontals.add(i);
                    verticals.add(j);
                    rightDiagonals.add(j - i);
                    leftDiagonals.add(i + j);
                }
            }
        }
    }

    public int getnQueens() {
        return nQueens;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public boolean isThereAQueenAt(int x, int y) {
        int fieldValue = squares.get(x*nQueens + y);
        if(fieldValue == 1)
            return true;
        else
            return false;
    }


    /**
     * The seed value for the initialization of random boards.
     */
    private static long seed = System.currentTimeMillis();

    /**
     * Creates a board randomly filled with the right amount of queens.
     */
    public static Chessboard initializeWithRandomQueens(int nQueens) {

        Random random = new Random(seed);

        int boardSize = nQueens*nQueens;

        ArrayList<Integer> squares = new ArrayList<>(Arrays.asList(new Integer[boardSize]));
        Collections.fill(squares, 0);

        //pick randomly n places to place a queen on, but make sure that every place is unique
        Set<Integer> previousPicks = new HashSet<>();
        Integer randomPick = -1;
        for (int i = 0; i < nQueens; i++) {
            do {
                previousPicks.add(randomPick);
                randomPick = Math.abs(random.nextInt() % boardSize);
                //System.out.println("Random pick:");
                //System.out.println(randomPick);
            } while (previousPicks.contains(randomPick));
            squares.set(randomPick, 1);
        }

        seed++;
        return new Chessboard(nQueens, squares);

    }


    /**
     * Checks if the queens on the board are not attacking each other.
     * Checking algorithm inspired by:
     * https://www.tutorialspoint.com/program-to-check-whether-a-board-is-valid-n-queens-solution-or-not-in-python
     *
     * @param chessboard
     * @return
     */
    public static boolean isItASolution(Chessboard chessboard) {


        if(chessboard.getHorizontals().size() < chessboard.nQueens || chessboard.getVerticals().size() < chessboard.nQueens
                || chessboard.getRightDiagonals().size() < chessboard.nQueens || chessboard.getLeftDiagonals().size() < chessboard.nQueens) {
            //there was a duplicate somewhere, which means that a queen is attacking another queen
            //System.out.println("A queen is attacking another queen. This is not a solution.");
            return false;
        } else {
            //System.out.println("No queen attacked any other queen. THIS IS A SOLUTION.");
            //chessboard.print();
            return true;
        }

    }

    public Set<Integer> getHorizontals() {
        return horizontals;
    }

    public Set<Integer> getVerticals() {
        return verticals;
    }

    public Set<Integer> getRightDiagonals() {
        return rightDiagonals;
    }

    public Set<Integer> getLeftDiagonals() {
        return leftDiagonals;
    }

    public double getFitness() {

        return fitness;

    }

    public void updateFitness() {
        this.fitness = FitnessCalculator.calculateFitness(this);
    }


    public int sumSquares() {
        return squares.stream().reduce(0, Integer::sum);
    }

    public List<Integer> getSquares(){
        return squares;
    }


    public void print(){
        /*
        System.out.println("Chessboard:");
        System.out.println("Calculated fitness: " + fitness);
        System.out.println("Status: " + status);
        System.out.println();

         */


        if(!status.equals("zero")) {
            for (int i = 0; i < nQueens; i++) {
                for (int j = 0; j < nQueens; j++) {
                    System.out.print(squares.get(i * nQueens + j) + " ");
                }
                System.out.println("");
            }
            System.out.println("");

        } else {
            System.out.println("This is a zero chessboard.");
        }


    }


}