
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


public class ScoreboardTest  {

    private final Set<String> teamsList = Set.of("Italy" , "Japan", "Norway", "France", "Spain", "Ireland");
    private final Scoreboard board = new Scoreboard(teamsList);


    @DataProvider(name = "matchList1")
    public static Object[][] matchListStartNewMatch() {
        return new Object[][] {
                {"Italy", "Norway", false},
                {"Italy", "Norway", true},
                {"Japan", "Spain", false},
                {"Japan", "not invited", true},
                {"", "not invited", true},
                {"Japan", "", true},
                {null, null, true}};
    }

    @Test(dataProvider = "matchList1")
    public void startNewMatchTest(String homeTeam, String awayTeam, boolean hasToFail){
        int previousBoardSize = board.getSummaryByTotalScore().size();
        if(hasToFail) {
            assertThrows(InvalidInputException.class, () -> board.startMatch(homeTeam, awayTeam), "Invalid input is:" + homeTeam + " " + awayTeam);
            assert(board.getSummaryByTotalScore().size() == previousBoardSize);
        } else {
            assertDoesNotThrow(() -> board.startMatch(homeTeam, awayTeam));
            assert(board.getSummaryByTotalScore().contains(new Match(homeTeam, awayTeam)));
            assert(board.getSummaryByTotalScore().size() == previousBoardSize+1);
        }
    }

    @DataProvider(name = "matchList2")
    public static Object[][] matchListUpdateScore() {
        return new Object[][] {
                {"Italy", "Norway", 6, 0, false},
                {"Japan", "Spain", 2, 5, false},
                {"Spain", "Japan", 2, 5, true},
                {"France", "Ireland", -2, 5, true},
                {"Italy", "Norway", 5, 0, true}, // this has to fail because scores can't decrease
                {"Italy", "Norway", -1, 0, true},
                {"notinList", "Norway", 11, 0, true} ,
                {null, null, 0, 0, true},
                {null, null, 0, -11, true}
        };
    }

    @Test(dataProvider = "matchList2")
    public void updateScoreTest (String homeTeam, String awayTeam, int homeScore, int awayScore, boolean hasToFail) {
        try {
            board.startMatch(homeTeam, awayTeam);
        } catch (Exception e) {
            //I ignore this exception because I am not testing startMatch here
        }
        Match matchToUpdate = findMatch(homeTeam);
        if(hasToFail) {
            assertThrows(InvalidInputException.class, () -> board.updateScore(homeTeam, homeScore, awayScore));
            if(matchToUpdate != null) {
                Match updatedMatch = findMatch(homeTeam);
                assert(updatedMatch.getHomeScore() == matchToUpdate.getHomeScore());
                assert(updatedMatch.getAwayScore() == matchToUpdate.getAwayScore());
            }
        } else {
            assertDoesNotThrow(() -> board.updateScore(homeTeam, homeScore, awayScore));
            Match updatedMatch = findMatch(homeTeam);
            assert(updatedMatch.getHomeScore() == homeScore);
            assert(updatedMatch.getAwayScore() == awayScore);
        }
    }


    @DataProvider(name = "matchList3")
    public static Object[][] matchListFinishMatch() {
        return new Object[][] {
                {"Italy", "Norway", false, true},
                {"Japan", "Spain", false, true},
                {"S", "J", true, true},
                {"Frnce", "Ired", true, true},
                {"Italy", "Norway", true, false},
                {"Frnce", "Ired", true, false},
                {null, "Ired", true, true},
                {null, null, true, true},
                {null, null, true, false}
        };
    }

    @Test(dataProvider = "matchList3")
    public void finishMatchTest (String homeTeam, String awayTeam, boolean hasToFail, boolean hasToStart) {
        if(hasToStart) {
            try {
                board.startMatch(homeTeam, awayTeam);
            } catch (Exception e) {
                //I ignore this exception because I am not testing startMatch here
            }
        }

        if(hasToFail) {
            assertThrows(InvalidInputException.class, () -> board.finishMatch(homeTeam));
            assert(findMatch(homeTeam) == null);
        } else {
            assertDoesNotThrow(() -> board.finishMatch(homeTeam));
            assert(findMatch(homeTeam) == null);
        }

    }

    @DataProvider(name = "matchList4")
    public static Object[][] matchListGetSummaryByTotalScore() {
        return new Object[][] {
                {returnList1(), returnList2()},
                {returnList3(), returnList4()}

        };
    }

    @Test(dataProvider = "matchList4")
    public void getSummaryByTotalScoreTest (List<Match> listInput, List<Match> listOutput) {

        for(Match match: listInput) {
            try {
                board.startMatch(match.getHomeTeam(), match.getAwayTeam());
                board.updateScore(match.getHomeTeam(), match.getHomeScore(), match.getAwayScore());
                Thread.sleep(1); // this is needed to give each match a different start time
            } catch (Exception e) {
                //I ignore this exception because I am not testing startMatch here
            }
        }
        List<Match> sortedList = board.getSummaryByTotalScore();
        assert(sortedList.equals(listOutput));

        for(Match match: listInput) {
            try {
                board.finishMatch(match.getHomeTeam());
            } catch (Exception e) {
                //I ignore this exception because I am not testing startMatch here
            }
        }
    }

    private static List<Match> returnList1() {
        return Arrays.asList(new Match("Italy", "Norway", 5, 0),
                new Match("Spain", "Japan", 0, 5),
                new Match("France", "Ireland", 10, 10));
    }
    private static List<Match> returnList2() {
        return Arrays.asList(new Match("France", "Ireland", 10, 10),
        new Match("Italy", "Norway", 5, 0),
        new Match("Spain", "Japan", 0, 5));
    }

    private static List<Match> returnList3() {
        return Arrays.asList(new Match("Italy", "Norway", 0, 0),
                new Match("Spain", "Japan", 0, 0),
                new Match("France", "Ireland", 0, 0));
    }

    private static List<Match> returnList4() {
        return Arrays.asList(new Match("Italy", "Norway", 0, 0),
                new Match("Spain", "Japan", 0, 0),
                new Match("France", "Ireland", 0, 0));
    }
    private Match findMatch(String homeTeam) {
        for(Match match: board.getSummaryByTotalScore()) {
           if(match.getHomeTeam() == homeTeam) return match;
        }
        return null;
    }


}
