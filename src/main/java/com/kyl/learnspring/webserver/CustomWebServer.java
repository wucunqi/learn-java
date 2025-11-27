package com.kyl.learnspring.webserver;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomWebServer {
    public static void main(String[] args) {
        // HTTP基于TCP需要建立链接（OS处理？）
        // 接收并解析HTTP请求消息，发送响应数据

        log.info("启动服务");
        log.error("error");
    }
}
