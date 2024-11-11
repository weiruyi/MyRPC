package com.ruyi.myrpc.netty;

import com.ruyi.myrpc.provider.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 获取客户端发送的消息，并调用服务
        System.out.println("msg=" + msg);

        //客户端调用服务器API时定一个协议，比如满足以某个字符串开头，“HelloService#hello#"
        if(msg.toString().startsWith("HelloService#hello#")){

            String result = new HelloServiceImpl().hello(msg.toString().substring(msg.toString().lastIndexOf('#') + 1));

            ctx.writeAndFlush(result);
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
