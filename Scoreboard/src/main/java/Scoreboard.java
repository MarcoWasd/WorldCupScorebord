import java.util.*;

public class Scoreboard implements IScoreboard{

    private final Set<String> teamsList;
    private final HashMap<String, Match> board;
    public Scoreboard(Set<String> teamsList) {
        this.teamsList = teamsList;
        board = new HashMap<>();
    }
    @Override
    public void startMatch(String homeTeam, String awayTeam) throws InvalidInputException {
        if(homeTeam == null || awayTeam == null || !teamsList.contains(homeTeam) || !teamsList.contains(awayTeam)) throw new InvalidInputException(homeTeam, awayTeam);
        board.put(homeTeam, new Match(homeTeam, awayTeam));
    }

    @Override
    public void updateScore(String homeTeam, int homeScore, int awayScore) {

    }

    @Override
    public void finishMatch(String homeTeam) {

    }

    @Override
    public List<Match> getSummaryByTotalScore() {
        return board.values().stream().toList();
    }
}
