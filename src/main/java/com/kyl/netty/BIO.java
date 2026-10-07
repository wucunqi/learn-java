package com.kyl.netty;

import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

@Slf4j
public class BIO {
    static void main() {
        try (FileChannel fileChannel = FileChannel.open(Path.of("data.txt"))) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(10);
            StringBuilder sb = new StringBuilder();
            // 识别字节是否完整，decode 不会消费掉不完整的尾部字节，它们留在 buffer 中
            CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder();

            while (fileChannel.read(byteBuffer) != -1) {
                byteBuffer.flip();
                // CharBuffer底层是char[],无符号16位Unicode码元,Java 的字符串处理体系只认 char，不认 byte
                CharBuffer charBuffer = decoder.decode(byteBuffer);
                sb.append(charBuffer);
                // compact() 把未消费的字节（不完整字符的残余）复制到缓冲区头部,并将 position 设为这些字节之后，准备接收下一次 read 的数据
                byteBuffer.compact();
            }

            System.out.println(sb);

            // 小文件一步到位
            // String content = Files.readString(Path.of("data.txt"), StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.info("异常{}", e.getMessage());
        }
    }
}
