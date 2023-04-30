package trivia;

public interface QuestionContext {
    void initializeDeck(Category category);

    String retrieveQuestion(Category category);
}
