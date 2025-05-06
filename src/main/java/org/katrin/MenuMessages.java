package org.katrin;

public enum MenuMessages {
    FULL_NAME("Full name: "),
    CONTACT_DATA("Contact data: "),
    PASSWORD("Password: "),
    AUTHORIZATION_OPTIONS("""
            
            Choose an option:
            1 -> sign in
            2 -> sign up
            
            """),
    MENU_OPTIONS("""
            
            Choose an option:
            1 -> View all games
            2 -> Add new game
            3 -> Delete game
            4 -> Filter by name
            5 -> Filter by cost
            6 -> Filter by type
            7 -> Sort by creation date
            8 -> Exit app
            
            """),
    INVALID_OPTION("Invalid option! Try again."),
    CLIENT_ALREADY_EXISTS("Such client already exists! Try again or sign in."),
    OPTION("Option: "),
    WELCOME("Welcome, %s!\n"),
    CLIENT_DOES_NOT_EXIST("There is no client with such credentials! Try again ot sign up."),
    GAME_DOES_NOT_EXIST("There is no game with such id! Try again."),
    GOODBYE("Bye bye!");
    private final String message;

    MenuMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
