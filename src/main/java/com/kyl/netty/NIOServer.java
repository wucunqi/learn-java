package com.kyl.netty;

import io.lettuce.core.StrAlgoArgs;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

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
import java.util.Iterator;
import java.util.List;

import static java.lang.classfile.ClassFile.MAGIC_NUMBER;

@Slf4j
public class NIOServer {
    private static final short MAGIC_NUMBER = (short) 0xCAFE;
    private static final int MAX_BODY_LENGTH = 10 * 1024 * 1024;

    static void main() {
        try (ServerSocketChannel ssc = ServerSocketChannel.open();
             Selector selector = Selector.open()
        ) {
            // 被监控只能是配置为非阻塞模式，监控内容：【四种标准事件】
            // OP_ACCEPT：有新的客户端连接请求（仅 ServerSocketChannel）
            // OP_CONNECT：客户端连接建立完成（仅 SocketChannel）
            // OP_READ：通道有数据可读
            // OP_WRITE：通道可写入数据
            ssc.configureBlocking(false);
            ssc.bind(new InetSocketAddress(8888));

            ssc.register(selector, SelectionKey.OP_ACCEPT);

            List<SocketChannel> socketChannels = new ArrayList<>();

            while (true) {
                selector.select();
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    it.remove();

                    if (key.isAcceptable()) {
                        SocketChannel sc = ((ServerSocketChannel) key.channel()).accept();
                        sc.configureBlocking(false);
                        socketChannels.add(sc);
                        sc.register(selector, SelectionKey.OP_READ, new ReadState());
                        log.info("客户端{}建立连接", sc.getRemoteAddress());
                    } else if (key.isReadable()) {
                        SocketChannel sc = (SocketChannel) key.channel();
                        ReadState readState = (ReadState) key.attachment();
                        try {
                            String clientMessage = handleClientMessage(sc, key, readState);
                            if (StringUtils.hasText(clientMessage)) {
                                log.info("客户端{}发来消息:{}", sc.getRemoteAddress(), clientMessage);
                            }
                        } catch (Exception e) {
                            log.error("客户端消息处理异常");
                        }
                    }
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String handleClientMessage(SocketChannel sc, SelectionKey key, ReadState readState) throws Exception{
        StringBuilder sb = new StringBuilder();

        // 在非阻塞模式下，当内核缓冲区暂时没有数据时，read() 返回 0，而不是 -1，-1 仅代表对端已关闭连接
        int nBytes = sc.read(readState.byteBuffer);
        if (nBytes == -1) {
            sc.close();
            key.cancel();
            readState.reset();
            log.info("客户端{}关闭", sc.getRemoteAddress());
            return null;
        }

        // 正常情况不会出现：可读但是读出0字节
        if (nBytes == 0) {
            return null;
        }

        readState.byteBuffer.flip();
        if (readState.head == null) {
            if (readState.byteBuffer.remaining() < 6) {
                readState.byteBuffer.compact();
                return null;
            }
            readState.head = new byte[6];
            readState.byteBuffer.get(readState.head);
            readState.bodyLen = parseBodyLength(readState.head);
        }
        if (readState.byteBuffer.remaining() < readState.bodyLen) {
            readState.byteBuffer.compact();
            return null;
        }

        byte[] bodyBytes = new byte[readState.bodyLen];
        readState.byteBuffer.get(bodyBytes);
        readState.reset();
        return new String(bodyBytes, StandardCharsets.UTF_8);
    }

    @Data
    static class ReadState {
        final CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder();
        final ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        // null 还没读满6字节头; 非null 头已就绪
        byte[] head = null;
        // 从头部解析出的body长度，-1表示尚未解析
        int bodyLen = -1;

        public void reset(){
            head = null;
            bodyLen = -1;
        }
    }

    private static int parseBodyLength(byte[] head) throws Exception{
        ByteBuffer headBuf = ByteBuffer.wrap(head);

        // 1. 校验魔数（大端序/网络字节序）
        short magic = headBuf.getShort();
        if (magic != MAGIC_NUMBER) {
            log.error("非法协议头，魔数不匹配");
            throw new Exception("非法协议头，魔数不匹配: " + Integer.toHexString(magic));
        }

        // 2. 读取4字节长度
        int bodyLen = headBuf.getInt();

        // 3. 防御性校验（极其重要！）
        if (bodyLen < 0) {
            throw new Exception("非法的Body长度: " + bodyLen);
        }
        if (bodyLen > MAX_BODY_LENGTH) {
            throw new Exception("Body长度超出限制: " + bodyLen + " > " + MAX_BODY_LENGTH);
        }

        return bodyLen;
    }
}
