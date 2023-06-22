package com.main;

import com.auxiliary.Message;
import com.auxiliary.TextColor;

import java.io.*;
import java.net.*;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class Server {
    private static int PORT = 2390;
    private static Listener listener;
    private static Message message = null;

    private static final ExecutorService executorServesForReading = Executors.newFixedThreadPool(100);

    private static void accept() throws IOException {
        ServerSocketChannel server = ServerSocketChannel.open();
        SocketAddress address = new InetSocketAddress(PORT);
        try {
            server.bind(address);
        } catch (BindException ex){
            System.out.println("Try another port");
            System.exit(0);
        }
        server.configureBlocking(true);

        while (true){
            SocketChannel socket = server.accept();
            System.out.println(socket);
            executorServesForReading.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        //TODO:  true here is a really bad choice
                        while (true) {
                            read(socket);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }



    private static void write(SocketChannel sc) throws Exception {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(message);
        objectOutputStream.close();


        sc.write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
        listener.executeCommands();
    }


    private static void read(SocketChannel sc) throws Exception {
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

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    message = processing((Data) objectInputStream.readObject(), sc);
                    write(sc);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    private static Message processing(Data data, SocketChannel socket) throws Exception {
        System.out.println("Command: " + data.commandType + ", user: " + socket.socket().getInetAddress() + ":" + socket.socket().getPort() + ", " + "login: " + data.login + ", current thread: " + Thread.currentThread().getName());
        return listener.commands.get(data.commandType).execute(data, listener);
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
        accept();
        listener.finish();
    }

}
