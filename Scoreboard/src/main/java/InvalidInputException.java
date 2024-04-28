public class InvalidInputException extends Exception {
    public InvalidInputException(String homeTeam, String awayTeam) {
        System.out.println("Invalid input is: " + homeTeam + " " + awayTeam);
    }
}
