import java.util.*;

public class Scoreboard implements IScoreboard{

    private final Set<String> teamsList;
    private final Set<String> teamsCurrentlyPlaying;
    private final HashMap<String, Match> board;
    public Scoreboard(Set<String> teamsList) {
        this.teamsList = teamsList;
        board = new HashMap<>();
        teamsCurrentlyPlaying = new HashSet<>();
    }
    @Override
    public void startMatch(String homeTeam, String awayTeam) throws InvalidInputException {
        if(!inputCheckStartMatch(homeTeam, awayTeam)) throw new InvalidInputException(homeTeam, awayTeam);
        teamsCurrentlyPlaying.add(homeTeam);
        teamsCurrentlyPlaying.add(awayTeam);
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

    private boolean inputCheckStartMatch(String homeTeam, String awayTeam) {
        if(inputCheckOnTeam(homeTeam) && inputCheckOnTeam(awayTeam) &&
                !teamsCurrentlyPlaying.contains(homeTeam) && !teamsCurrentlyPlaying.contains(awayTeam))
            return true;
        else
            return false;
    }

    private boolean inputCheckOnTeam(String team) {
        if(team == null || !teamsList.contains(team))
            return false;
        else
            return true;
    }
}
