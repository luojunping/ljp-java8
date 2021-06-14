package com.ljp.test.nio;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * @author 罗俊平
 * @email 591402399@qq.com
 * @date 2021/6/12
 * @since 1.0.0
 **/
public class NioServerTest {

    @Test
    public void testOne() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(9090));
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            int selectNum = selector.select();
            if (selectNum > 0) {
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey selectionKey = keyIterator.next();
                    if (selectionKey.isAcceptable()) {
                        ServerSocketChannel selectableChannel = (ServerSocketChannel) selectionKey.channel();
                        SocketChannel socketChannel = selectableChannel.accept();
                        socketChannel.register(selector, SelectionKey.OP_READ);
                        System.out.println("serverSocketChannel == selectableChannel :: " + (serverSocketChannel == selectableChannel));
                    } else if (selectionKey.isReadable()) {
                        SocketChannel selectableChannel = (SocketChannel) selectionKey.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        selectableChannel.read(byteBuffer);
                        byteBuffer.flip();
                        System.out.println(new String(byteBuffer.array(), StandardCharsets.UTF_8));
                        System.out.println("selectableChannel :: " + selectableChannel);
                    }
                    keyIterator.remove();
                }
            }
        }
        // serverSocketChannel.close();
        // selector.close();
    }

    @Test
    public void testTwo() throws IOException {
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
