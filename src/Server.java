import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.security.Key;
import java.util.Set;

public class Server {
    private static int PORT = 23909;
    public static Data data = new Data("hello", 2);


    private static void select() throws IOException, ClassNotFoundException {
        Selector selector = Selector.open();
        ServerSocketChannel server = ServerSocketChannel.open();
        SocketAddress address = new InetSocketAddress(PORT);
        try {
            server.bind(address);
        } catch (BindException ex){
            System.out.println("Try another port");
            System.exit(0);
        }
        server.configureBlocking(false);
        server.register(selector, SelectionKey.OP_ACCEPT);
        while(true) {
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            for (var iter = keys.iterator(); iter.hasNext(); ) {
                SelectionKey key = iter.next(); iter.remove();
                if (key.isValid()) {
                    if (key.isAcceptable()) { accept(key); }
                    if (key.isReadable()) { read(key); }
                    if (key.isWritable()) { write(key); }
                }
            }
        }
    }


    private static void write(SelectionKey key) throws IOException {
        var sc = (SocketChannel) key.channel();


        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(data);
        objectOutputStream.close();


        sc.write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
        sc.register(key.selector(), SelectionKey.OP_READ);
    }

    private static void read(SelectionKey key) throws IOException, ClassNotFoundException {
        var sc = (SocketChannel) key.channel();
        byte[] info = new byte[2048];


        try {
            sc.read(ByteBuffer.wrap(info));
        } catch (IOException ex){
            return;
        }

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(info);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Data result = (Data) objectInputStream.readObject();


        sc.register(key.selector(), SelectionKey.OP_WRITE);

        processing(result);
    }

    private static void processing(Data result){
        System.out.println(result.buffer + " " + result.a + " " + result.b);
        data.b = 1;
        data.a = "bye";
        data.buffer = "Услышал вас!";
    }

    private static void accept(SelectionKey key) throws IOException {
        var ssc = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = ssc.accept();
        System.out.println(socketChannel);
        key.attach(new Data("helllo", 2));
        socketChannel.configureBlocking(false);
        socketChannel.register(key.selector(), SelectionKey.OP_READ);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        select();
    }

}
