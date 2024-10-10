package cli;

import client.tcp.TcpClient;
import client.udp.UdpClient;
import enumerations.GameFlowEnum;

import java.util.List;
import java.util.Scanner;

public class CommandLineInterfaceHelper {

    public static final String SECTION_SEPARATOR = "--------------------------------------------------";

    public void startApplication() {
        System.out.println(" __      ________  ______   ______      \n" +
                "/_/\\    /_______/\\/_____/\\ /_____/\\     \n" +
                "\\:\\ \\   \\::: _  \\ \\:::_ \\ \\\\:::__\\/     \n" +
                " \\:\\ \\   \\::(_)  \\ \\:(_) ) )\\:\\ \\  __   \n" +
                "  \\:\\ \\___\\:: __  \\ \\: __ `\\ \\:\\ \\/_/\\  \n" +
                "   \\:\\/___/\\:.\\ \\  \\ \\ \\ `\\ \\ \\:\\_\\ \\ \\ \n" +
                "    \\_____\\/\\__\\/\\__\\/\\_\\/ \\_\\/\\_____\\/ ");
        System.out.println(SECTION_SEPARATOR);
    }

    public void requestLogin(final Scanner scanner, final TcpClient tcpClient) {
        System.out.print("> Username: ");
        tcpClient.setUsername(scanner.nextLine());

        System.out.print("> Password: ");
        tcpClient.setPassword(scanner.nextLine());

        System.out.println("Login stored successfully.");
    }

    public void runMenu(final Scanner scanner, final TcpClient tcpClient, final UdpClient udpClient) {
        drawMenu(
                "LARC SOCKET",
                "0 - Get Users from Larc Server. (TCP)",
                "1 - Get Message from Larc Server. (TCP)",
                "2 - Send Message to the Larc Server. (UDP)",
                "3 - Blackjack.",
                "4 - Quit."
        );
        final String userOption = scanner.nextLine();

        try {
            final Integer userOptionParsed = Integer.parseInt(userOption);

            if (!List.of(0, 1, 2, 3, 4).contains(userOptionParsed))
                throw new RuntimeException("The input must be 0, 1, 2, 3 or 4.");

            switch (userOptionParsed) {
                case 0:
                    tcpClient.getUsers();
                    break;
                case 1:
                    tcpClient.getMessage();
                    break;
                case 2: {
                    System.out.print("> The ID of the addressee: ");
                    final String addressee = scanner.nextLine();

                    System.out.print("> The message that you want to send: ");
                    final String message = scanner.nextLine();

                    udpClient.sendMessage(
                            tcpClient.getUsername(),
                            tcpClient.getPassword(),
                            addressee,
                            message
                    );
                    break;
                }
                case 3:
                    runBlackjackMenu(scanner, tcpClient, udpClient);
                    break;
                case 4:
                    System.exit(0);
                    break;
            }
        } catch (final NumberFormatException numberFormatException) {
            throw new RuntimeException("The input must be a number! Error: " + numberFormatException.getMessage());
        }
    }

    private void runBlackjackMenu(final Scanner scanner, final TcpClient tcpClient, final UdpClient udpClient) {
        Integer blackjackOption = 0;

        while (!blackjackOption.equals(3)) {
            drawMenu(
                    "Blackjack",
                    "0 - Get Players from Larc Server. (TCP)",
                    "1 - Get Card from Larc Server. (TCP)",
                    "2 - Send Game to the Larc Server. (UDP)",
                    "3 - Quit."
            );

            try {
                blackjackOption = Integer.parseInt(scanner.nextLine());

                switch (blackjackOption) {
                    case 0: {
                        tcpClient.getBlackjackPlayers();
                        break;
                    }
                    case 1: {
                        tcpClient.getBlackjackCard();
                        break;
                    }
                    case 2: {
                        System.out.print("> Select the game flow option (ENTER, STOP, QUIT): ");
                        final GameFlowEnum gameFlowEnum = GameFlowEnum.valueOf(scanner.nextLine());

                        udpClient.sendGame(tcpClient.getUsername(), tcpClient.getPassword(), gameFlowEnum);
                        break;
                    }
                }
            } catch (final NumberFormatException numberFormatException) {
                throw new RuntimeException("The input must be a number! Error: " + numberFormatException.getMessage());
            }
        }
    }

    private void drawMenu(final String title, final String... optionsDescriptions) {
        System.out.println(SECTION_SEPARATOR);
        System.out.println(String.format("Welcome to the %s. Choose one of the options shown in the menu...", title));

        for (final String optionDescription : optionsDescriptions) {
            System.out.println(optionDescription);
        }
        System.out.println("============================================================");
        System.out.print("> Your option (number): ");
    }

}
