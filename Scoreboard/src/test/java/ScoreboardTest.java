
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


public class ScoreboardTest  {

    private final Set<String> teamsList = Set.of("Italy" , "Japan", "Norway", "France", "Spain");
    private Scoreboard board = new Scoreboard(teamsList);


    @DataProvider(name = "matchList1")
    public static Object[][] primeNumbers() {
        return new Object[][] {
                {"Italy", "Norway", false},
                {"Japan", "Spain", false},
                {"Japan", "not invited", true},
                {"", "not invited", true},
                {"Japan", "", true},
                {null, null, true}};
    }

    @Test(dataProvider = "matchList1")
    public void startNewMatchTest(String homeTeam, String awayTeam, boolean fail){
        if(fail) {
            assertThrows(InvalidInputException.class, () -> board.startMatch(homeTeam, awayTeam), "Invalid input is:" + homeTeam + " " + awayTeam);
        } else {
            assertDoesNotThrow(() -> board.startMatch(homeTeam, awayTeam));
        }

    }
}
