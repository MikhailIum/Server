package com.main;

import com.auxiliary.Message;
import com.auxiliary.TextColor;

import java.io.*;
import java.net.*;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.List;
import java.util.Set;

public class Server {
    private static int PORT = 23909;
    private static Listener listener;
    private static Message message;


    private static void select() throws Exception {
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
                    if (key.isReadable()) { read(key);}
                    if (key.isWritable()) { write(key); }
                }
            }
        }
    }


    private static void write(SelectionKey key) throws Exception {
        var sc = (SocketChannel) key.channel();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(message);
        objectOutputStream.close();


        sc.write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
        sc.register(key.selector(), SelectionKey.OP_READ);
        listener.executeCommands();
    }

    private static void read(SelectionKey key) throws Exception {
        var sc = (SocketChannel) key.channel();
        byte[] info = new byte[20048];


        try {
            sc.read(ByteBuffer.wrap(info));
        } catch (IOException ex){
            return;
        }

        ObjectInputStream objectInputStream;
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(info);
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
        } catch (Exception ex){
            return;
        }
        message = processing((Data) objectInputStream.readObject(), sc);
        sc.register(key.selector(), SelectionKey.OP_WRITE);
    }

    private static Message processing(Data data, SocketChannel socket) throws Exception {
        System.out.println("Command: " + data.commandType + ", user: " + socket.socket().getInetAddress() + ":" + socket.socket().getPort());
        return listener.commands.get(data.commandType).execute(data, listener);
    }

    private static void accept(SelectionKey key) throws IOException {
        var ssc = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = ssc.accept();
        System.out.println(socketChannel);

        socketChannel.configureBlocking(false);
        socketChannel.register(key.selector(), SelectionKey.OP_READ);
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 0){
            System.out.println(TextColor.ANSI_YELLOW + "Please add a port as an argument" + TextColor.ANSI_RESET);
            System.exit(0);
        } else {
            PORT = Integer.parseInt(args[0]);
        }
        listener = new Listener();
        listener.start();
        select();
        listener.finish();
    }

}
