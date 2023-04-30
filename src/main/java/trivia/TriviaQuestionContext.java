package trivia;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class TriviaQuestionContext implements QuestionContext {
    private final int questionsSize;
    private final Map<Category, Deque<String>> questionDecks;

    public TriviaQuestionContext(int questionsSize) {
        this.questionsSize = questionsSize;
        questionDecks = new HashMap<>();
    }

    @Override
    public void initializeDeck(Category category) {
        final Deque<String> deck = new LinkedList<>();
        for (int i = 0; i < questionsSize; i++) {
            deck.addLast(category.getName() + " Question " + i);
        }
        questionDecks.put(category, deck);
    }

    @Override
    public String retrieveQuestion(Category category) {
        Deque<String> deck = questionDecks.get(category);
        return deck.removeFirst();
    }
}
