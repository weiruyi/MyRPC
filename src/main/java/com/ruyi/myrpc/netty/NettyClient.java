package com.ruyi.myrpc.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.Proxy;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NettyClient {

    private static ExecutorService executor = Executors.newFixedThreadPool(4);

    private static NettyClientHandler client;

    //编写方法，使用代理模式获取一个代理对象
    public Object getBean(final Class<?> serviceClass, final String providerName){
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[]{serviceClass}, ((proxy, method, args) -> {
            if(client == null){
                initClient();
            }
            //设置要发送给服务器端的消息
            client.setPara(providerName + args[0]);

            return executor.submit(client).get();
        }));
    }

    public static void initClient(){
        client = new NettyClientHandler();

        //创建EventLoopGroup
        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(client);
                    }
                });

        try {
            bootstrap.connect("127.0.0.1", 6666).sync();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
