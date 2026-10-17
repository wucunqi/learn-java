package com.kyl.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Client {
    private static final short MAGIC_NUMBER = (short) 0xCAFE;

    static void main() throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost", 8888));

        String msg = "Hello你好👋";
        byte[] bodyBytes = msg.getBytes(StandardCharsets.UTF_8);
        ByteBuffer byteBuffer = ByteBuffer.allocate(6 + bodyBytes.length);
        byteBuffer.putShort(MAGIC_NUMBER);
        byteBuffer.putInt(bodyBytes.length);
        byteBuffer.put(bodyBytes);
        byteBuffer.flip();

        sc.write(byteBuffer);
    }
}
