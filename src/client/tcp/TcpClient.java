package client.tcp;

import cli.CommandLineInterfaceHelper;
import commons.Servers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TcpClient {

    /*
    * The TcpClient is responsible for:
    * Running a scheduled task of 'keepalive' at each 6 seconds;
    * Being able to list the server users;
    * Request the oldest message from the server;
    * Being able to list the blackjack players;
    * Request the card from a player;
    * */

    private static final Integer PORT = 1012;
    private static final String GET_USERS_REQUEST = "GET USERS %s:%s";
    private static final String GET_MESSAGE_REQUEST = "GET MESSAGE %s:%s";
    private static final String GET_PLAYERS_REQUEST = "GET PLAYERS %s:%s";
    private static final String GET_CARD_REQUEST = "GET CARD %s:%s";
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
                    if (Objects.isNull(tcpClient.getSocket()) || tcpClient.getSocket().isClosed())
                        tcpClient.socket = new Socket(Servers.LARC_SERVER, PORT);

                    final PrintWriter printWriter = new PrintWriter(tcpClient.getSocket().getOutputStream(), true);
                    final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(tcpClient.getSocket().getInputStream()));
                    printWriter.println(String.format(GET_USERS_REQUEST, tcpClient.getUsername(), tcpClient.getPassword()));
                    bufferedReader.readLine();
                }
            } catch (final Exception exception) {
                throw new RuntimeException("Keepalive task ended with an exception. Error: " + exception.getMessage());
            }
        }

    }

    public void getUsers() {
        try {
            defaultGetRequest(GET_USERS_REQUEST, getUsername(), getPassword());
        } catch (final Exception exception) {
            throw new RuntimeException(
                    "Unknown exception while trying to get the users from the server. Error: " + exception.getMessage()
            );
        }
    }

    public void getMessage() {
        try {
            defaultGetRequest(GET_MESSAGE_REQUEST, getUsername(), getPassword());
        } catch (final Exception exception) {
            throw new RuntimeException(
                    "Unknown exception while trying to get the message from the server. Error: " + exception.getMessage()
            );
        }
    }

    public void getBlackjackPlayers() {
        try {
            defaultGetRequest(GET_PLAYERS_REQUEST, getUsername(), getPassword());
        } catch (final Exception exception) {
            throw new RuntimeException(
                    "Unknown exception while trying to get the blackjack players from the server. Error: " + exception.getMessage()
            );
        }
    }

    public void getBlackjackCard() {
        try {
            defaultGetRequest(GET_CARD_REQUEST, getUsername(), getPassword());
        } catch (final Exception exception) {
            throw new RuntimeException(
                    "Unknown exception while trying to get the card from the server. Error: " + exception.getMessage()
            );
        }
    }

    private void defaultGetRequest(final String requestType, final String... requestParameters) {
        try {
            if (Objects.nonNull(getSocket()) && !getSocket().isClosed()) {
                System.out.println(CommandLineInterfaceHelper.SECTION_SEPARATOR);
                System.out.println("Starting to get from the server.");
                final PrintWriter printWriter = new PrintWriter(getSocket().getOutputStream(), true);
                final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getSocket().getInputStream()));

                printWriter.println(String.format(requestType, Arrays.stream(requestParameters).toArray()));
                final String serverResponse = bufferedReader.readLine();

                System.out.println("Response: " + serverResponse);
                System.out.println("Default Get Request ended.");
            } else {
                System.out.println(CommandLineInterfaceHelper.SECTION_SEPARATOR);
                System.out.println("Socket unavailable in the moment.");
            }
        } catch (final Exception exception) {
            throw new RuntimeException(
                    "Unknown exception while trying to default Get Request from the server. Error: " + exception.getMessage()
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
