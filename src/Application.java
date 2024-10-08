import cli.CommandLineInterfaceHelper;
import client.tcp.TcpClient;
import client.udp.UdpClient;

import java.util.Scanner;

public class Application {

    public static void main(final String[] args) {
        final CommandLineInterfaceHelper commandLineInterfaceHelper = new CommandLineInterfaceHelper();
        final TcpClient tcpClient = new TcpClient();
        final TcpClient.KeepAliveTask keepAliveTask = new TcpClient.KeepAliveTask(tcpClient);
        final UdpClient udpClient = new UdpClient();

        commandLineInterfaceHelper.startApplication();
        keepAliveTask.start();

        try (final Scanner scanner = new Scanner(System.in)) {
            commandLineInterfaceHelper.requestLogin(scanner, tcpClient);
            while (true) commandLineInterfaceHelper.runMenu(scanner, tcpClient, udpClient);
        } catch (final Exception exception) {
            throw new RuntimeException(
                    "Unknown exception while scanning inputs from the user. Error: " + exception.getMessage()
            );
        }
    }

}
