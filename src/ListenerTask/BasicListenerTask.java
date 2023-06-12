package ListenerTask;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

public class BasicListenerTask implements ListenerTask{

    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private ServerSocket serverSocket;

    public BasicListenerTask(Integer port) {

        try {
            this.selector = Selector.open();
            this.serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            this.serverSocket = serverSocketChannel.socket();
            InetSocketAddress inetSocketAddress = new InetSocketAddress(port);

            serverSocket.bind(inetSocketAddress);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        } catch (IOException e) {
            System.out.println("Fail to setup. search properties and environment variable");
            throw new RuntimeException(e);
        }

    }

    @Override
    public void run() {
        System.out.println("===========================");
        System.out.println("run Task");

        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        try {
            selector.select();

            for (SelectionKey selectionKey : selectionKeys) {

                if (selectionKey.isAcceptable()) {
                    registerChannel();
                }

                if (selectionKey.isReadable()) {
                    //todo read logic
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("hihi");
    }

    private void registerChannel() throws IOException {
        SocketChannel client = serverSocketChannel.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
        System.out.println("new client connect");
    }
}
