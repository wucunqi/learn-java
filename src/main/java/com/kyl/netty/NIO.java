package com.kyl.netty;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class NIO {
    static void main() {
        try (ServerSocketChannel ssc = ServerSocketChannel.open();
             Selector selector = Selector.open()
        ) {
            ssc.configureBlocking(false);
            ssc.bind(new InetSocketAddress(8888));
            ssc.register(selector, SelectionKey.OP_ACCEPT);

            List<SocketChannel> socketChannels = new ArrayList<>();
            ByteBuffer byteBuffer = ByteBuffer.allocate(16);
            CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder();

            while (true) {
                selector.select();
                socketChannels.add(sc);

                for (SocketChannel socketChannel : socketChannels) {
                    StringBuilder sb = new StringBuilder();
                    while (socketChannel.read(byteBuffer) != -1) {
                        byteBuffer.flip();
                        sb.append(readBytesToString(byteBuffer, decoder));
                    }
                    log.info("{}:{}", socketChannel.getRemoteAddress(), sb);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String readBytesToString(ByteBuffer byteBuffer, CharsetDecoder decoder) throws CharacterCodingException {
        CharBuffer charBuffer = decoder.decode(byteBuffer);
        byteBuffer.compact();
        return charBuffer.toString();
    }
}
