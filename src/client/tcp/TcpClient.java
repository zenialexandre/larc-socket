package client.tcp;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TcpClient implements Runnable {

    /*
    * The TcpClient is responsible for:
    * Running a scheduled task of 'keepalive' at each 6 seconds;
    * Be able to list the server users;
    * Request the oldest message from the server;
    * */

    private static final Integer PORT = 1012;
    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    public void keepAlive() {
        scheduledExecutorService.scheduleAtFixedRate(this, 0, 6, TimeUnit.SECONDS);
    }

    @Override
    public void run() {

    }

}
