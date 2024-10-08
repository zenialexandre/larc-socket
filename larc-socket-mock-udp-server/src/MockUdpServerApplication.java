import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class MockUdpServerApplication {

    /*
     * Code to start a mocked udp server on port 1011 and host 127.0.0.1
     * */

    public static void main(final String[] args) {
        try (final DatagramSocket datagramSocket = new DatagramSocket(1011)) {
            System.out.println("Starting to build a local UDP Server on port 1011.");

            while (true) {
                final byte[] byteBuffer = new byte[1024];
                final DatagramPacket datagramPacket = new DatagramPacket(byteBuffer, byteBuffer.length);
                datagramSocket.receive(datagramPacket);

                final String message = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
                System.out.println("Message received: " + message);
            }
        } catch (final Exception exception) {
            throw new RuntimeException(
                    "Unknown exception while trying to initialize UDP Server. Error: " + exception.getMessage()
            );
        }
    }

}
