package client.tcp;

import cli.CommandLineInterfaceHelper;
import commons.Servers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TcpClient {

    /*
    * The TcpClient is responsible for:
    * Running a scheduled task of 'keepalive' at each 6 seconds;
    * Be able to list the server users;
    * Request the oldest message from the server;
    * */

    private static final Integer PORT = 1012;
    protected Socket socket;
    protected String username;
    protected String password;

    public static class KeepAliveTask implements Runnable {

        private static final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        private final TcpClient tcpClient;

        public KeepAliveTask(final TcpClient tcpClient) {
            this.tcpClient = tcpClient;
        }

        public void start() {
            scheduledExecutorService.scheduleAtFixedRate(this, 0, 6, TimeUnit.SECONDS);
        }

        @Override
        public void run() {
            try {
                if (Objects.nonNull(tcpClient.getUsername()) && Objects.nonNull(tcpClient.getPassword())) {
                    //System.out.println("Keepalive task initializing...");
                    String serverResponse = "";
                    tcpClient.socket = new Socket(Servers.LOCAL_SERVER, PORT);
                    final PrintWriter printWriter = new PrintWriter(tcpClient.socket.getOutputStream(), true);
                    final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(tcpClient.socket.getInputStream()));

                    printWriter.println(tcpClient.getUsername());
                    printWriter.println(tcpClient.getPassword());

                    //while (Objects.nonNull(serverResponse = bufferedReader.readLine()))
                    //    System.out.println("Server Response: " + serverResponse);

                    //System.out.println("Keepalive task ended.");
                    //System.out.println(CommandLineInterfaceHelper.SECTION_SEPARATOR);
                }
            } catch (final Exception exception) {
                throw new RuntimeException("Keepalive task ended with an exception. Error: " + exception.getMessage());
            }
        }

    }

    public void getUsers() {
        try {
            if (Objects.nonNull(getSocket())) {
                System.out.println(CommandLineInterfaceHelper.SECTION_SEPARATOR);
                System.out.println("Starting to get the users from the server.");
                final PrintWriter printWriter = new PrintWriter(getSocket().getOutputStream(), true);
                final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getSocket().getInputStream()));

                printWriter.println(String.format("GET USERS %s:%s", getUsername(), getPassword()));

                while (Objects.nonNull(bufferedReader.readLine()))
                    System.out.println("Users: " + bufferedReader.readLine());

                System.out.println("Get users ended.");
            }
        } catch (final Exception exception) {
            throw new RuntimeException(
                    "Unknown exception while trying to get the users from the server. Error: " + exception.getMessage()
            );
        }
    }

    public void getMessage() {
        try {
            if (Objects.nonNull(getSocket())) {
                System.out.println(CommandLineInterfaceHelper.SECTION_SEPARATOR);
                System.out.println("Starting to get the message from the server.");
                final PrintWriter printWriter = new PrintWriter(getSocket().getOutputStream(), true);
                final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getSocket().getInputStream()));

                printWriter.println(String.format("GET MESSAGE %s:%s", getUsername(), getPassword()));

                while (Objects.nonNull(bufferedReader.readLine()))
                    System.out.println("Message: " + bufferedReader.readLine());

                System.out.println("Get message ended.");
            }
        } catch (final Exception exception) {
            throw new RuntimeException(
                    "Unknown exception while trying to get the message from the server. Error: " + exception.getMessage()
            );
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

}
