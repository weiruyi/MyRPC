package com.ruyi.myrpc.customer;

import com.ruyi.myrpc.api.HelloService;
import com.ruyi.myrpc.netty.NettyClient;

public class ClientBootstrap {

    //定义协议头
    private static String providerName = "HelloService#hello#";

    public static void main(String[] args) {
        //创建一个消费者
        NettyClient customer = new NettyClient();
        // 创建一个代理对象
        HelloService helloService   = (HelloService) customer.getBean(HelloService.class, providerName);

        // 通过代理对象调用服务提供者提供服务
        String result = helloService.hello("wry");
        System.out.println("调用的结果result="+result);
    }


}
