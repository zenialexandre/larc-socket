package client.udp;

import commons.Servers;
import enumerations.GameFlowEnum;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class UdpClient {

    /*
     * The UdpClient is responsible for:
     * Being able to send a message to a specific user on the server;
     * Being able to send a game request to the server;
     * */

    private static final Integer PORT = 1011;
    private static final String SEND_MESSAGE_REQUEST = "SEND MESSAGE %s:%s:%s:%s";
    private static final String SEND_GAME_REQUEST = "SEND GAME %s:%s:%s";

    public void sendMessage(final String username, final String password, final String addressee, final String message) {
        try (final DatagramSocket datagramSocket = new DatagramSocket()) {
            defaultSendRequest(datagramSocket, SEND_MESSAGE_REQUEST, username, password, addressee, message);
        } catch (final Exception exception) {
            throw new RuntimeException(
                    "Unknown exception while trying to send a message to the server. Error: " + exception.getMessage()
            );
        }
    }

    public void sendGame(final String username, final String password, final GameFlowEnum gameFlowEnum) {
        try (final DatagramSocket datagramSocket = new DatagramSocket()) {
            defaultSendRequest(datagramSocket, SEND_GAME_REQUEST, username, password, gameFlowEnum.name());
        } catch (final Exception exception) {
            throw new RuntimeException(
                    "Unknown exception while trying to send a game to the server. Error: " + exception.getMessage()
            );
        }
    }

    private void defaultSendRequest(final DatagramSocket datagramSocket, final String requestType, final String... requestParameters) {
        try {
            final String request = String.format(requestType, Arrays.stream(requestParameters).toArray());
            final byte[] requestByteBuffer = request.getBytes(StandardCharsets.UTF_8);
            final InetAddress inetAddress = InetAddress.getByName(Servers.LARC_SERVER);
            final DatagramPacket datagramPacket = new DatagramPacket(requestByteBuffer, requestByteBuffer.length, inetAddress, PORT);

            datagramSocket.send(datagramPacket);
        } catch (final Exception exception) {
            throw new RuntimeException("Unknown exception while trying to default send. Error: " + exception.getMessage());
        }
    }

}
