import java.util.List;

public interface IScoreboard {

    public void startMatch(String homeTeam, String awayTeam) throws InvalidInputException;
    public void updateScore(String homeTeam, int homeScore, int awayScore);
    public void finishMatch(String homeTeam);
    public List<Match> getSummaryByTotalScore();
}
