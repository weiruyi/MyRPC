package com.ruyi.myrpc.provider;


import com.ruyi.myrpc.netty.NetttyServer;

/**
 * 启动一个服务提供者，NettyServer
 */
public class ServerBootstrap {
    public static void main(String[] args) {

        NetttyServer.startServer("127.0.0.1", 6666);

    }
}
