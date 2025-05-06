package org.katrin;

public enum GameMessages {
    NAME("Name: "),
    TYPE("Type: "),
    RELEASE_DATE("Release date: "),
    RATING("Rating (1 - 5): "),
    COST("Cost: "),
    DESCRIPTION("Description: "),
    ADDED_GAME("Game was successfully added with number %d!\n"),
    INPUT_ID_TO_DELETE("Input game id to delete: "),
    MIN_COST("Min cost: "),
    MAX_COST("Max cost: "),
    GAMES_NOT_FOUND("Sorry, there are no games to your request."),
    NO_GAMES_IN_DATABASE("Sorry, we don't have games yet, but you can add one!");

    private final String message;

    GameMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
