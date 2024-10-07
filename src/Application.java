import cli.CommandLineInterfaceHelper;
import client.tcp.TcpClient;
import client.udp.UdpClient;

public class Application {

    public static void main(final String[] args) {
        final CommandLineInterfaceHelper commandLineInterfaceHelper = new CommandLineInterfaceHelper();
        final TcpClient tcpClient = new TcpClient();
        final TcpClient.KeepAliveTask keepAliveTask = new TcpClient.KeepAliveTask(tcpClient);
        final UdpClient udpClient = new UdpClient();

        commandLineInterfaceHelper.startApplication();
        commandLineInterfaceHelper.requestLogin(tcpClient);
        keepAliveTask.start();
    }

}
