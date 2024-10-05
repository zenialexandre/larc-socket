package client.tcp;

import java.net.Socket;
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

    public static class KeepAliveTask implements Runnable {

        private static final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

        public void start() {
            scheduledExecutorService.scheduleAtFixedRate(this, 0, 6, TimeUnit.SECONDS);
        }

        @Override
        public void run() {
            try {
                System.out.println("Keepalive task initializing...");
                //final Socket socket = new Socket("localhost:8080", PORT);

                System.out.println("Keepalive task ended.");
            } catch (final Exception exception) {
                throw new RuntimeException("");
            }
        }

    }

}
