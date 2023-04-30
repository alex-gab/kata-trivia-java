package trivia;

import java.util.ArrayList;
import java.util.List;

import static trivia.Category.*;

// REFACTOR ME
public class GameBetter implements IGame {
   private final List<String> players = new ArrayList<>();
   private final int[] places = new int[6];
   private final int[] purses = new int[6];
   private final boolean[] inPenaltyBox = new boolean[6];

   private final QuestionContext questionContext = new TriviaQuestionContext(50);
   private int currentPlayer = 0;
   private boolean isGettingOutOfPenaltyBox;

   public GameBetter() {
      for (Category category : values()) {
         questionContext.initializeDeck(category);
      }
   }

   public boolean add(String playerName) {
      players.add(playerName);
      places[howManyPlayers()] = 0;
      purses[howManyPlayers()] = 0;
      inPenaltyBox[howManyPlayers()] = false;

      System.out.println(playerName + " was added");
      System.out.println("They are player number " + howManyPlayers());
      return true;
   }

   private int howManyPlayers() {
      return players.size();
   }

   public void roll(int roll) {
      System.out.println(players.get(currentPlayer) + " is the current player");
      System.out.println("They have rolled a " + roll);

      if (inPenaltyBox[currentPlayer]) {
         if (roll % 2 != 0) {
            isGettingOutOfPenaltyBox = true;

            System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
            places[currentPlayer] = places[currentPlayer] + roll;
            if (places[currentPlayer] > 11) {
               places[currentPlayer] = places[currentPlayer] - 12;
            }

            System.out.println(players.get(currentPlayer)
                               + "'s new location is "
                               + places[currentPlayer]);
            System.out.println("The category is " + currentCategory().getName());
            askQuestion();
         } else {
            System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
            isGettingOutOfPenaltyBox = false;
         }

      } else {

         places[currentPlayer] = places[currentPlayer] + roll;
         if (places[currentPlayer] > 11) {
            places[currentPlayer] = places[currentPlayer] - 12;
         }

         System.out.println(players.get(currentPlayer)
                            + "'s new location is "
                            + places[currentPlayer]);
         System.out.println("The category is " + currentCategory().getName());
         askQuestion();
      }

   }

   private void askQuestion() {
      System.out.println(questionContext.retrieveQuestion(currentCategory()));
   }

   private Category currentCategory() {
      if (places[currentPlayer] % 4 == 0) {
         return POP;
      } else if (places[currentPlayer] % 4 == 1) {
         return SCIENCE;
      } else if (places[currentPlayer] % 4 == 2) {
         return SPORTS;
      } else {
         return ROCK;
      }
   }

   public boolean wasCorrectlyAnswered() {
      if (inPenaltyBox[currentPlayer]) {
         if (isGettingOutOfPenaltyBox) {
            return isPlayerNotAWinner();
         } else {
            incrementCurrentPlayer();
            return true;
         }
      } else {
         return isPlayerNotAWinner();
      }
   }

   private boolean isPlayerNotAWinner() {
      System.out.println("Answer was correct!!!!");
      purses[currentPlayer]++;
      System.out.println(players.get(currentPlayer)
                         + " now has "
                         + purses[currentPlayer]
                         + " Gold Coins.");

      boolean isNotAWinner = isNotAWinner();
      incrementCurrentPlayer();

      return isNotAWinner;
   }

   private void incrementCurrentPlayer() {
      currentPlayer++;
      if (currentPlayer == players.size()) currentPlayer = 0;
   }

   public boolean wrongAnswer() {
      System.out.println("Question was incorrectly answered");
      System.out.println(players.get(currentPlayer) + " was sent to the penalty box");
      inPenaltyBox[currentPlayer] = true;

      incrementCurrentPlayer();
      return true;
   }


   private boolean isNotAWinner() {
      return !(purses[currentPlayer] == 6);
   }
}
