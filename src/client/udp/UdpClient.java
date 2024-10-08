package client.udp;

import commons.Servers;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class UdpClient {

    /*
     * The UdpClient is responsible for:
     * Being able to send a message to a specific user on the server;
     * */

    private static final Integer PORT = 1011;
    private static final String SEND_MESSAGE_REQUEST = "SEND MESSAGE %s:%s:%s:%s";

    public void sendMessage(final String username, final String password, final String addressee, final String message) {
        try (final DatagramSocket datagramSocket = new DatagramSocket()) {
            final String request = String.format(SEND_MESSAGE_REQUEST, username, password, addressee, message);
            final byte[] requestByteBuffer = request.getBytes(StandardCharsets.UTF_8);
            final InetAddress inetAddress = InetAddress.getByName(Servers.LOCAL_SERVER);
            final DatagramPacket datagramPacket = new DatagramPacket(requestByteBuffer, requestByteBuffer.length, inetAddress, PORT);

            datagramSocket.send(datagramPacket);
        } catch (final Exception exception) {
            throw new RuntimeException(
                    "Unknown exception while trying to send a message to the server. Error: " + exception.getMessage()
            );
        }
    }

}
