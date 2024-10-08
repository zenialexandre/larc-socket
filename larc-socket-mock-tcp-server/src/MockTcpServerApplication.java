import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

public class MockTcpServerApplication {

    /*
    * Code to start a mocked tcp server on port 1012 and host 127.0.0.1
    * */

    public static void main(final String[] args) {
        try (final ServerSocket serverSocket = new ServerSocket(1012)) {
            System.out.println("Starting to build a local TCP Server on port 1012.");

            while (true) {
                final Socket socket = serverSocket.accept();
                final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                final PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);

                String readedLine;
                while (Objects.nonNull(readedLine = bufferedReader.readLine())) {
                    getUsersListener(printWriter, readedLine);
                    getMessageListener(printWriter, readedLine);
                }
            }
        } catch (final Exception exception) {
            throw new RuntimeException(
                    "Unknown exception while trying to initialize TCP Server. Error: " + exception.getMessage()
            );
        }
    }

    public static void getUsersListener(final PrintWriter printWriter, final String readedLine) {
        System.out.println("Get Users Listener: Listening to: " + readedLine);

        if (readedLine.contains("GET USERS"))
            printWriter.println("2756:João da Silva:4:1235:José da Silva:0:1243:Manuel da Silva:2:");
    }

    public static void getMessageListener(final PrintWriter printWriter, final String readedLine) {
        System.out.println("Get Message Listener: Listening to: " + readedLine);

        if (readedLine.contains("GET MESSAGE"))
            printWriter.println("3825:Oi!");
    }

}
