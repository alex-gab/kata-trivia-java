package trivia;

public class Player {
    private final String name;
    private int place;
    private int coins;
    private boolean inPenaltyBox;
    private boolean isGettingOutOfPenaltyBox;


    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getPlace() {
        return place;
    }

    public void calculatePlace(int roll) {
        place = place + roll;
        if (place >= 12) {
            place = place - 12;
        }
    }

    public int getCoins() {
        return coins;
    }

    public void receiveCoin() {
        coins++;
    }

    public boolean isInPenaltyBox() {
        return inPenaltyBox;
    }

    public void setInPenaltyBox(boolean inPenaltyBox) {
        this.inPenaltyBox = inPenaltyBox;
    }

    public boolean isGettingOutOfPenaltyBox() {
        return isGettingOutOfPenaltyBox;
    }

    public void setGettingOutOfPenaltyBox(boolean gettingOutOfPenaltyBox) {
        isGettingOutOfPenaltyBox = gettingOutOfPenaltyBox;
    }
}
