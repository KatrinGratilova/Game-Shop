package org.katrin.controller;

import org.katrin.MenuMessages;
import org.katrin.entity.Client;
import org.katrin.service.ClientService;

import java.io.PrintStream;
import java.util.Scanner;

public class ClientController {
    private final Scanner in;
    private final PrintStream out;
    private final ClientService clientService;

    public ClientController(Scanner in, PrintStream out, ClientService clientService) {
        this.in = in;
        this.out = out;
        this.clientService = clientService;
    }

    public MenuOption signIn() {
        return () -> {
            Client.ClientBuilder builder = Client.builder();
            out.print(MenuMessages.CONTACT_DATA.getMessage());
            builder.contactData(in.nextLine());
            out.print(MenuMessages.PASSWORD.getMessage());
            builder.password(in.nextLine());

            Client client = clientService.findClient(builder.build());
            out.printf(MenuMessages.WELCOME.getMessage(), client.getFullName());
        };
    }

    public MenuOption signUp() {
        return () -> {
            Client.ClientBuilder builder = Client.builder();
            out.print(MenuMessages.FULL_NAME.getMessage());
            builder.fullName(in.nextLine());
            out.print(MenuMessages.CONTACT_DATA.getMessage());
            builder.contactData(in.nextLine());
            out.print(MenuMessages.PASSWORD.getMessage());
            builder.password(in.nextLine());

            Client client = clientService.createClient(builder.build());
            out.printf(MenuMessages.WELCOME.getMessage(), client.getFullName());
        };
    }
}
