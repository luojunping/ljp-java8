package com.ljp.test.oio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author 罗俊平
 * @email 591402399@qq.com
 * @date 2021/9/19
 * @since 1.0.0
 **/
public class Server implements Runnable {

    private static final int PORT = 7777;

    public static void main(String[] args) throws IOException, InterruptedException {
        new Thread(new Server()).start();
        TimeUnit.SECONDS.sleep(2);
        Socket socket = new Socket("127.0.0.1", 7777);
        System.out.println(socket.isConnected());
        OutputStream os = socket.getOutputStream();
        os.write("hell world\r\n".getBytes(StandardCharsets.UTF_8));
        os.write("hell china".getBytes(StandardCharsets.UTF_8));
        socket.shutdownOutput();
        InputStream is = socket.getInputStream();
        System.out.println("---------------------------------");
        byte[] bytes = is.readAllBytes();
        System.out.println(new String(bytes, StandardCharsets.UTF_8));
        socket.shutdownInput();
        socket.close();
        // SocketChannel socketChannel = socket.getChannel();
        // socketChannel.configureBlocking(true);
        // socketChannel.write(ByteBuffer.allocate(11).put("hell world".getBytes(StandardCharsets.UTF_8)));
        // ByteBuffer byteBuffer = ByteBuffer.allocate(11);
        // socketChannel.read(byteBuffer);
        // System.out.println(new String(byteBuffer.array(), StandardCharsets.UTF_8));
    }

    @Override
    public void run() {
        try {
            ExecutorService executorService = Executors.newFixedThreadPool(10);
            ServerSocket serverSocket = new ServerSocket(PORT);
            while (!Thread.interrupted()) {
                executorService.execute(new Handler(serverSocket.accept()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class Handler implements Runnable {

        final Socket socket;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (InputStream is = socket.getInputStream(); OutputStream os = socket.getOutputStream()) {
                System.out.println(22222);
                byte[] bytes = is.readAllBytes();
                System.out.println(33333);
                os.write(process(bytes));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public byte[] process(byte[] bytes) {
            String inStr = new String(bytes, StandardCharsets.UTF_8);
            System.out.println(inStr);
            String outStr = "server has received: " + inStr + "\r\n";
            return outStr.getBytes(StandardCharsets.UTF_8);
        }

    }

}
