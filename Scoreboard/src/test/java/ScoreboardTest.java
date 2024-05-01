
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


public class ScoreboardTest  {

    private final Set<String> teamsList = Set.of("Italy" , "Japan", "Norway", "France", "Spain");
    private Scoreboard board = new Scoreboard(teamsList);


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
                {"Italy", "Norway", 5, 0, true}, // this has to fail because scores can't decrease
        };
    }

    @Test(dataProvider = "matchList2")
    public void updateScoreTest (String homeTeam, String awayTeam, int homeScore, int awayScore, boolean hasToFail) {
        assert(true);
    }


}
