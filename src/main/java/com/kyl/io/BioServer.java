package com.kyl.io;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author wucunqi
 */
@Slf4j
public class BioServer {
    public static void main(String[] args) throws IOException {
        BioServer bioServer = new BioServer();
        bioServer.runBioServer();
    }

    public void runBioServer(){
        try (ServerSocket serverSocket = new ServerSocket(8000)) {
            log.info("===服务器启动===");
            while (true) {
                // 没有线程来连接会阻塞
                Socket acceptSocket = serverSocket.accept();
                log.info("收到连接: " + acceptSocket.getRemoteSocketAddress());

                new Thread(() -> {
                    try (
                        Socket clientSocket = acceptSocket;
                        InputStream in = clientSocket.getInputStream();
                        OutputStream out = clientSocket.getOutputStream()
                    ) {
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = in.read(buffer)) != -1) {
                            String msg = new String(buffer, 0, len);
                            log.info("收到: " + msg);

                            out.write(("Echo: " + msg).getBytes());
                            out.flush();
                        }
                    } catch (Exception e){
                        log.error("服务器异常", e);
                    }
                }).start();
            }
        } catch (Exception e){
            log.error("服务器异常",e);
        }
    }
}
