import java.util.List;

public interface IScoreboard {

    public void startMatch(String homeTeam, String awayTeam) throws InvalidInputException;
    public void updateScore(String homeTeam, int homeScore, int awayScore) throws InvalidInputException;
    public void finishMatch(String homeTeam) throws InvalidInputException;
    public List<Match> getSummaryByTotalScore();
}
