import java.util.*;

public class Tournament {

    private static long seed = System.currentTimeMillis();

    int numberOfTournaments;
    int tournamentSize;
    List<Chessboard> contestants;

    public Tournament(int numberOfTournaments, int tournamentSize) {
        this.numberOfTournaments = numberOfTournaments;
        this.tournamentSize = tournamentSize;
    }

    public Tournament chooseContestants(List<Chessboard> population) {
        //pick randomly n contestants out of a population, but make sure that every contestant is unique

        Random random = new Random(seed);

        List<Chessboard> contestants = new ArrayList<>();

        Set<Integer> previousPicks = new HashSet<>();
        int randomPick = -1;
        for (int i = 0; i < tournamentSize; i++) {
            do {
                previousPicks.add(randomPick);
                randomPick = Math.abs(random.nextInt() % population.size());
                //System.out.println("Random pick:");
                //System.out.println(randomPick);
            } while (previousPicks.contains(randomPick));
            contestants.add(population.get(randomPick));
        }
        seed++;

        this.contestants = contestants;

        return this;

    }

    Chessboard determineWinner() {
        return contestants.stream().max(Comparator.comparing(Chessboard::getFitness)).get();
    }

}
