package trivia;

import java.util.ArrayList;
import java.util.List;

import static trivia.Category.*;

// REFACTOR ME
public class GameBetter implements IGame {
   private final List<Player> players = new ArrayList<>();
   private final QuestionContext questionContext = new TriviaQuestionContext(50);
   private int currentPlayerIdx;
   private Player currentPlayer;

   public GameBetter() {
      for (Category category : values()) {
         questionContext.initializeDeck(category);
      }
   }

   public boolean add(String playerName) {
      players.add(new Player(playerName));
      System.out.println(playerName + " was added");
      System.out.println("They are player number " + howManyPlayers());
      return true;
   }

   private int howManyPlayers() {
      return players.size();
   }

   public void roll(int roll) {
      currentPlayer = players.get(currentPlayerIdx);
      System.out.println(currentPlayer.getName() + " is the current player");
      System.out.println("They have rolled a " + roll);

      if (currentPlayer.isInPenaltyBox()) {
         if (roll % 2 != 0) {
            currentPlayer.setGettingOutOfPenaltyBox(true);
            System.out.println(currentPlayer.getName() + " is getting out of the penalty box");
            continueToPlay(roll);
         } else {
            System.out.println(currentPlayer.getName() + " is not getting out of the penalty box");
            currentPlayer.setGettingOutOfPenaltyBox(false);
         }
      } else {
         continueToPlay(roll);
      }

   }

   private void continueToPlay(int roll) {
      currentPlayer.calculatePlace(roll);

      System.out.println(currentPlayer.getName()
                         + "'s new location is "
                         + currentPlayer.getPlace());
      System.out.println("The category is " + currentCategory().getName());
      askQuestion();
   }

   private void askQuestion() {
      System.out.println(questionContext.retrieveQuestion(currentCategory()));
   }

   private Category currentCategory() {
      switch (currentPlayer.getPlace() % 4) {
         case 0: return POP;
         case 1: return SCIENCE;
         case 2: return SPORTS;
         default: return ROCK;
      }
   }

   public boolean wasCorrectlyAnswered() {
      boolean isNotAWinner;
      if (currentPlayer.isInPenaltyBox()) {
         if (currentPlayer.isGettingOutOfPenaltyBox()) {
            System.out.println("Answer was correct!!!!");
            receiveCoins();
            isNotAWinner = isNotAWinner();
         } else {
            isNotAWinner = true;
         }
      } else {
         System.out.println("Answer was correct!!!!");
         receiveCoins();
         isNotAWinner = isNotAWinner();
      }

      moveToNextPlayer();
      return isNotAWinner;
   }

   private void receiveCoins() {
      currentPlayer.receiveCoin();
      System.out.println(currentPlayer.getName()
                         + " now has "
                         + currentPlayer.getCoins()
                         + " Gold Coins.");
   }

   private void moveToNextPlayer() {
      currentPlayerIdx++;
      if (currentPlayerIdx == players.size()) currentPlayerIdx = 0;
   }

   public boolean wrongAnswer() {
      System.out.println("Question was incorrectly answered");
      System.out.println(currentPlayer.getName() + " was sent to the penalty box");
      currentPlayer.setInPenaltyBox(true);
      moveToNextPlayer();
      return true;
   }


   private boolean isNotAWinner() {
      return !(currentPlayer.getCoins() == 6);
   }
}
