package client.tcp;

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
    protected String username;
    protected String password;

    public static class KeepAliveTask implements Runnable {

        private static final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        private final TcpClient tcpClient;

        public KeepAliveTask(final TcpClient tcpClient) {
            this.tcpClient = tcpClient;
        }

        public void start() {
            scheduledExecutorService.scheduleAtFixedRate(this, 2, 6, TimeUnit.SECONDS);
        }

        @Override
        public void run() {
            try {
                if (Objects.nonNull(tcpClient.getUsername()) && Objects.nonNull(tcpClient.getPassword())) {
                    System.out.println("Keepalive task initializing...");
                    String serverResponse = "";
                    final Socket socket = new Socket(Servers.LARC_SERVER, PORT);
                    final PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                    final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    printWriter.println(tcpClient.getUsername());
                    printWriter.println(tcpClient.getPassword());

                    while (Objects.nonNull(serverResponse = bufferedReader.readLine()))
                        System.out.println("Server Response: " + serverResponse);

                    System.out.println("Keepalive task ended.");
                }
            } catch (final Exception exception) {
                throw new RuntimeException("Keepalive task ended with an exception. Error: " + exception.getMessage());
            }
        }

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
