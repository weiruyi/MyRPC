package com.ruyi.myrpc.provider;

import com.ruyi.myrpc.api.HelloService;

public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String msg) {
        System.out.println("收到客户端消息：" + msg);
        if(msg != null){
            return "你好客户端，我已经收到你的消息[" + msg + "]";
        }else{
            return "你好客户端，我已经收到你的消息";
        }
    }
}
