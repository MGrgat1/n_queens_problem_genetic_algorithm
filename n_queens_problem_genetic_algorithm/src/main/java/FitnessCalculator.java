import java.util.List;

public class FitnessCalculator {

    /**
     * Fitness is calculated by checking how spread out the queens are across the board. If a queen is present on
     * every horizontal, that means that no two queens are intersecting horizontally. The same applies to verticals,
     * left diagonals and right diagonals.
     */
    public static double calculateFitness(Chessboard chessboard) {

        int nQueens = chessboard.getnQueens();

        int horizontalFitness = chessboard.getHorizontals().size();
        int verticalFitness = chessboard.getVerticals().size();
        int leftDiagonalFitness = chessboard.getLeftDiagonals().size();
        int rightDiagonalFitness = chessboard.getRightDiagonals().size();

        double totalFitness = (horizontalFitness + verticalFitness + leftDiagonalFitness + rightDiagonalFitness)/(double) (4*nQueens);

        //if there are not the right amount of queens, then it cannot be a solution. But if there are, then it's a better solution
        //the less collisions there are
        if(chessboard.sumSquares() != nQueens) {
            totalFitness = 0;
        }

        return totalFitness;

    }

    public static double getAverageFitness(List<Chessboard> generation) {
        return generation.stream()
                .mapToDouble(Chessboard::getFitness)
                .average()
                .orElse(Double.NaN);
    }

    public static double getMaxFitness(List<Chessboard> generation) {

        return generation.stream()
                .mapToDouble(Chessboard::getFitness)
                .max()
                .orElse(Double.NaN);
    }


}
