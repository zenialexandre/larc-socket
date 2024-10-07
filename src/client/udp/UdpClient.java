package client.udp;

import commons.Servers;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class UdpClient {

    private static final Integer PORT = 1011;

    public void sendMessage(final String username, final String password, final String addressee, final String message) {
        try (final DatagramSocket datagramSocket = new DatagramSocket()) {
            final String request = String.format("SEND MESSAGE %s:%s:%s:%s", username, password, addressee, message);
            final byte[] requestByteBuffer = request.getBytes(StandardCharsets.UTF_8);
            final InetAddress inetAddress = InetAddress.getByName(Servers.LARC_SERVER);
            final DatagramPacket datagramPacket = new DatagramPacket(requestByteBuffer, requestByteBuffer.length, inetAddress, PORT);

            datagramSocket.send(datagramPacket);
        } catch (final Exception exception) {
            throw new RuntimeException(
                    "Unknown exception while trying to send a message to the server. Error: " + exception.getMessage()
            );
        }
    }

}
