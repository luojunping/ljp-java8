package com.ljp.test.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author 罗俊平
 * @email 591402399@qq.com
 * @date 2021/6/12
 * @since 1.0.0
 **/
public class Hehe {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 9090));
        while (true) {
            boolean connected = socketChannel.isConnected();
            if (connected) {
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                byteBuffer.put("hello world !!!".getBytes(StandardCharsets.UTF_8));
                byteBuffer.flip();
                socketChannel.write(byteBuffer);
                break;
            }
        }
    }

}
