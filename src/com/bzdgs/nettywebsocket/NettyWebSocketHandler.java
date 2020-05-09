package com.bzdgs.nettywebsocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;

public class NettyWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("1111111111111");
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("收到消息："+msg);
        ctx.writeAndFlush( new TextWebSocketFrame("当前时间为："+(LocalDateTime.now())));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(cause.getMessage());
    }
}
