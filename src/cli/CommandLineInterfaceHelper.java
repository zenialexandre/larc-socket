package cli;

import client.tcp.TcpClient;
import client.udp.UdpClient;

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
        System.out.println(SECTION_SEPARATOR);
    }

    public void runMenu(final Scanner scanner, final TcpClient tcpClient, final UdpClient udpClient) {
        System.out.println("Welcome to the LARC SOCKET. Choose one of the options shown in the menu...");
        System.out.println("0 - Get Users from Larc Server. (TCP)");
        System.out.println("1 - Get Message from Larc Server. (TCP)");
        System.out.println("2 - Send Message to the Larc Server. (UDP)");
        System.out.println("3 - Quit.");
        System.out.println("============================================================");
        System.out.print("> Your option (number): ");

        final String userOption = scanner.nextLine();

        try {
            final Integer userOptionParsed = Integer.parseInt(userOption);

            if (!List.of(0, 1, 2, 3).contains(userOptionParsed))
                throw new RuntimeException("The input must be 0, 1, 2 or 3.");

            switch (userOptionParsed) {
                case 0: { tcpClient.getUsers(); }
                case 1: { tcpClient.getMessage(); }
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
                }
                case 3: { System.exit(0); }
            }
        } catch (final NumberFormatException numberFormatException) {
            throw new RuntimeException("The input must be a number! Error: " + numberFormatException.getMessage());
        }
    }

}
