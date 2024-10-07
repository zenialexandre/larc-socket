package cli;

import client.tcp.TcpClient;

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

    public void requestLogin(final TcpClient tcpClient) {
        try (final Scanner scanner = new Scanner(System.in)) {
            System.out.print("> Username: ");
            tcpClient.setUsername(scanner.nextLine());

            System.out.print("> Password: ");
            tcpClient.setPassword(scanner.nextLine());

            System.out.println("Login stored successfully.");
            System.out.println(SECTION_SEPARATOR);
        }
    }

}
