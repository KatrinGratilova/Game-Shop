package org.katrin.controller;

import org.katrin.GameMessages;
import org.katrin.MenuMessages;
import org.katrin.entity.Game;
import org.katrin.service.GameService;

import java.io.PrintStream;
import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class GameController {
    private final Scanner in;
    private final PrintStream out;
    private final GameService service;

    public GameController(Scanner in, PrintStream out, GameService service) {
        this.in = in;
        this.out = out;
        this.service = service;
    }

    public MenuOption showAllGames() {
        return () -> {
            List<Game> games = service.findAll();
            if (!games.isEmpty())
                games.forEach(out::println);
            else out.println(GameMessages.NO_GAMES_IN_DATABASE.getMessage());
        };
    }

    public MenuOption addNewGame() {
        return () -> {
            Game.GameBuilder builder = Game.builder();
            out.print(GameMessages.NAME.getMessage());
            builder.name(in.nextLine());
            out.print(GameMessages.TYPE.getMessage());
            builder.type(in.nextLine());
            out.print(GameMessages.RELEASE_DATE.getMessage());
            builder.releaseDate(Date.valueOf(in.nextLine()));
            out.print(GameMessages.RATING.getMessage());
            builder.rating(Integer.parseInt(in.nextLine()));
            out.print(GameMessages.COST.getMessage());
            builder.cost(Double.parseDouble(in.nextLine()));
            out.print(GameMessages.DESCRIPTION.getMessage());
            builder.description(in.nextLine());

            Game game = service.create(builder.build());
            out.printf(GameMessages.ADDED_GAME.getMessage(), game.getId());
        };
    }

    public MenuOption deleteGame() {
        return () -> {
            showAllGames().optionAction();
            out.print(GameMessages.INPUT_ID_TO_DELETE.getMessage());
            service.deleteById(Integer.parseInt(in.nextLine()));
        };
    }

    public MenuOption filterByName() {
        return () -> {
            out.print(GameMessages.NAME.getMessage());
            List<Game> games = service.findByName(in.nextLine());
            if (!games.isEmpty())
                games.forEach(out::println);
            else out.println(GameMessages.GAMES_NOT_FOUND.getMessage());
        };
    }

    public MenuOption filterByCost() {
        return () -> {
            out.print(GameMessages.MIN_COST.getMessage());
            double min = Double.parseDouble(in.nextLine());
            out.print(GameMessages.MAX_COST.getMessage());
            double max = Double.parseDouble(in.nextLine());

            List<Game> games = service.findByCostRange(min, max);
            if (!games.isEmpty())
                games.forEach(out::println);
            else out.println(GameMessages.GAMES_NOT_FOUND.getMessage());
        };
    }

    public MenuOption filterByType() {
        return () -> {
            out.print(GameMessages.TYPE.getMessage());
            List<Game> games = service.findByType(in.nextLine());
            if (!games.isEmpty())
                games.forEach(out::println);
            else out.println(GameMessages.GAMES_NOT_FOUND.getMessage());
        };
    }

    public MenuOption sortByCreationDate() {
        return () -> {
            List<Game> games = service.sortByCreationDate();
            if (!games.isEmpty())
                games.forEach(out::println);
            else out.println(GameMessages.NO_GAMES_IN_DATABASE.getMessage());
        };
    }

    public MenuOption exit() {
        return () -> out.println(MenuMessages.GOODBYE.getMessage());
    }
}
