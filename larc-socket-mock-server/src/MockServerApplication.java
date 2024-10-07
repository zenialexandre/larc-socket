import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

public class MockServerApplication {

    /*
    * Code to start a mocked server on port 1012 and host 127.0.0.1
    * Receive the username and password and responds.
    * */

    public static void main(String[] args) {
        try (final ServerSocket serverSocket = new ServerSocket(1012)) {
            System.out.println("Starting to build a local TCP Server on port 1012.");

            final Socket socket = serverSocket.accept();
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            final PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            final String username = bufferedReader.readLine();
            final String password = bufferedReader.readLine();

            System.out.println("Username received: " + username);
            System.out.println("Password received: " + password);

            printWriter.println("Logged in.");

            while (Objects.nonNull(serverSocket));
        } catch (final Exception exception) {
            throw new RuntimeException(
                    "Unknown exception while trying to initialize TCP Server. Error: " + exception.getMessage()
            );
        }
    }

}
