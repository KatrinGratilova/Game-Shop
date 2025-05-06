package org.katrin;

import org.katrin.controller.ClientController;
import org.katrin.controller.GameController;
import org.katrin.controller.MenuOption;
import org.katrin.exception.ClientAlreadyExistsException;
import org.katrin.exception.EntityInstanceDoesNotExist;
import org.katrin.repository.ClientRepositoryImpl;
import org.katrin.repository.GameRepositoryImpl;
import org.katrin.repository.SessionFactorySingleton;
import org.katrin.service.ClientService;
import org.katrin.service.GameService;

import java.io.PrintStream;
import java.util.Map;
import java.util.Scanner;

public class GameShopApplication {
    private final PrintStream out = System.out;
    private final ClientRepositoryImpl clientRepository = new ClientRepositoryImpl(SessionFactorySingleton.getSessionFactory());
    private final ClientService clientService = new ClientService(clientRepository);
    private final ClientController clientController;
    private final GameRepositoryImpl gameRepository = new GameRepositoryImpl(SessionFactorySingleton.getSessionFactory());
    private final GameService gameService = new GameService(gameRepository);
    private final GameController gameController;
    private static Map<Integer, MenuOption> authorizationOptions;
    private static Map<Integer, MenuOption> menuOptions;

    public GameShopApplication(Scanner in) {
        clientController = new ClientController(in, out, clientService);
        gameController = new GameController(in, out, gameService);

        authorizationOptions = Map.of(
                1, clientController.signIn(),
                2, clientController.signUp());

        menuOptions = Map.of(
                1, gameController.showAllGames(),
                2, gameController.addNewGame(),
                3, gameController.deleteGame(),
                4, gameController.filterByName(),
                5, gameController.filterByCost(),
                6, gameController.filterByType(),
                7, gameController.sortByCreationDate(),
                8, gameController.exit());
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        GameShopApplication main = new GameShopApplication(in);
        main.userAuthorization(in);
        main.menuOptionSelection(in);
    }

    public void userAuthorization(Scanner in) {
        int option;
        do {
            out.print(MenuMessages.AUTHORIZATION_OPTIONS.getMessage());
            option = getOption(authorizationOptions, in);
        }
        while (!authorizationOptions.containsKey(option));
    }

    public void menuOptionSelection(Scanner in) {
        int option;
        do {
            out.print(MenuMessages.MENU_OPTIONS.getMessage());
            option = getOption(menuOptions, in);
        }
        while (!menuOptions.containsKey(option) || option != menuOptions.size());
    }

    private int getOption(Map<Integer, MenuOption> menuOptions, Scanner in) {
        int option;
        out.print(MenuMessages.OPTION.getMessage());
        option = Integer.parseInt(in.nextLine());
        try {
            menuOptions.getOrDefault(option, () -> out.println(MenuMessages.INVALID_OPTION.getMessage())).optionAction();
        } catch (ClientAlreadyExistsException | EntityInstanceDoesNotExist e) {
            out.println(e.getMessage());
            option = 0;
        }
        return option;
    }
}