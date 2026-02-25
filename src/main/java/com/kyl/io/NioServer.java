package com.kyl.io;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * @author wucunqi
 */
@Slf4j
public class NioServer {
    private Selector selector;
    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) throws IOException {
        NioServer nioServer = new NioServer();
        nioServer.runNioServer();
    }

    private void runNioServer() throws IOException {
        selector = Selector.open();
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.configureBlocking(false);
        channel.bind(new InetSocketAddress("127.0.0.1", 8001));
        channel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            // 阻塞，有多少个fd有事件
            int readyChannels = 0;
            readyChannels = selector.select();
            if (readyChannels == 0) {
                continue;
            }

            // selectedKeys 是累积性的集合，处理完了后需要手动移除，返回的是 同一个 Set 实例（不是每次新建的）
            // Selector 不会自动清理 这个 Set，必须由你手动移除 已处理的 key
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> it = selectionKeys.iterator();
            while (it.hasNext()) {
                SelectionKey selectionKey = it.next();
                if (selectionKey.isAcceptable()) {
                    handleAccept(selectionKey);
                } else if(selectionKey.isReadable()){
                    handleRead(selectionKey);
                } else if(selectionKey.isWritable()){
                    handleWrite(selectionKey);
                }
                it.remove();
            }
        }
    }

    private void handleAccept(SelectionKey selectionKey) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
        SocketChannel clientChannel = serverSocketChannel.accept();
        clientChannel.configureBlocking(false);
        clientChannel.register(selector, SelectionKey.OP_READ);
    }

    private void handleRead(SelectionKey selectionKey) throws IOException {
        SocketChannel clientChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        int bytesRead = clientChannel.read(buffer);

        if (bytesRead == -1) {
            clientChannel.close();
            selectionKey.cancel();
            return;
        }

        if (bytesRead > 0) {
            // 切换为读模式，position = 0, limit = bytesRead
            buffer.flip();

            String message = "Echo:" + StandardCharsets.UTF_8.decode(buffer);
            selectionKey.attach(message);
            selectionKey.interestOps(SelectionKey.OP_WRITE);
        }
    }

    private void handleWrite(SelectionKey selectionKey) throws IOException {
        SocketChannel clientChannel = (SocketChannel) selectionKey.channel();
        String response = (String) selectionKey.attachment();

        if (response != null) {
            ByteBuffer buffer = ByteBuffer.wrap(response.getBytes(StandardCharsets.UTF_8));
            clientChannel.write(buffer);

            System.out.println("发送响应: " + response);

            // 清除附加对象，重新注册读事件
            selectionKey.attach(null);
            selectionKey.interestOps(SelectionKey.OP_READ);
        }
    }
}
