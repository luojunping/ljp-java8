package com.ljp.test.nio;

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
public class Haha {

    public static void main(String[] args) throws IOException {
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
                        socketChannel.configureBlocking(false);
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
}
