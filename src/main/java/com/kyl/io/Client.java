package com.kyl.io;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * @author wucunqi
 */
@Slf4j
public class Client {
    private static final String SERVER_HOST = "localhost";
    private static final Integer PORT = 8000;
    private Selector selector;
    private static final Integer BUFFER_SIZE = 1024;

    public static void main(String[] args) {
        Client client = new Client();
        // client.runBioClient();
        client.runNioClient();
    }

    public void runBioClient(){
        // OS自动分配ip和端口
        try (Socket socket = new Socket(SERVER_HOST, PORT)) {
            log.info("===已连接到服务器===");

            OutputStream out = socket.getOutputStream();
            InputStream in = socket.getInputStream();

            // 启动一个线程读取服务端响应（避免阻塞输入）
            Thread readerThread = new Thread(() -> {
                byte[] buffer = new byte[1024];
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        int len = in.read(buffer);
                        if (len == -1) {
                            break;
                        }
                        String response = new String(buffer, 0, len);
                        System.out.println("\n【服务端回复】: " + response);
                        System.out.print("请输入消息: ");
                    }
                } catch (Exception e) {
                    System.err.println("\n与服务器连接断开");
                }
            });
            readerThread.setDaemon(true);
            readerThread.start();

            Scanner scanner = new Scanner(System.in);
            while (true) {
                // 阻塞等待，内部字符缓冲区中有换行符返回true
                if (!scanner.hasNextLine()){
                    break;
                }
                String message = scanner.nextLine();
                out.write((message + "\n").getBytes());
                out.flush();
            }

        } catch (Exception e) {
            log.error("连接失败", e);
        }
    }

    public void runNioClient() {
        try{
            selector = Selector.open();
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress(SERVER_HOST, PORT));
            socketChannel.register(selector, SelectionKey.OP_CONNECT);

            while(true){
                // 阻塞等待
                selector.select();

                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();
                while(it.hasNext()){
                    SelectionKey selectionKey = it.next();
                    if (selectionKey.isConnectable()) {
                        handleConnect(selectionKey);
                    } else if (selectionKey.isReadable()) {
                        handleRead(selectionKey);
                    } else if (selectionKey.isWritable()) {
                        handleWrite(selectionKey);
                    }
                    it.remove();
                }
            }
        } catch (Exception e){
            log.info("连接失败", e);
        }

    }

    private void handleConnect(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();

        if (channel.isConnectionPending()) {
            channel.finishConnect();
        }
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ);

        // 启动发送线程
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            System.out.println("请输入消息（:");

            while (scanner.hasNextLine()) {
                String message = scanner.nextLine();
                key.attach(message);
                key.interestOps(SelectionKey.OP_WRITE);

                // 唤醒selector
                selector.wakeup();
            }
            scanner.close();
        }).start();
    }

    private void handleRead(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

        int bytesRead = channel.read(buffer);

        if (bytesRead == -1) {
            log.info("服务器关闭连接");
            channel.close();
            key.cancel();
            return;
        }

        if (bytesRead > 0) {
            buffer.flip();
            String response = StandardCharsets.UTF_8.decode(buffer).toString();
            System.out.println("收到服务器响应: " + response);
        }
    }

    private void handleWrite(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        String message = (String) key.attachment();

        if (message != null) {
            ByteBuffer buffer = ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8));
            channel.write(buffer);

            // 发送完成后，取消写事件，只监听读事件
            key.attach(null);
            key.interestOps(SelectionKey.OP_READ);
        }
    }
}
