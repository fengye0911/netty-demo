//package com.bzdgs.netty.server;
//
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;
//import io.netty.channel.ChannelHandlerAdapter;
//import io.netty.channel.ChannelHandlerContext;
//
//import java.util.Date;
//
///**
// * 继承自ChannelHandlerAdapter,用于对象网络事件进行读写操作，通常只需关注channelRead和exceptionCaught两个方法；
// */
//
//public class TimeServerHandler extends ChannelHandlerAdapter {
//
//
//    @Override
//    public void channelRead(ChannelHandlerContext context, Object msg) throws Exception{
//        //1 将msg转成ByteBuf对象，
//        ByteBuf byteBuf = (ByteBuf) msg;
//        //2 ByteBuf可以通过readableBytes()方法获取缓冲区可读的字节数，根据可读字节数创建byte数组，
//        byte[] bytes = new byte[byteBuf.readableBytes()];
//        //3 通过readBytes()方法将缓冲区中的字节数组复制到新建的byte数组中
//        byteBuf.readBytes(bytes);
//        //4 最后通过new String构造函数获取请求消息
//        String s = new String(bytes, "utf-8");
//        //5 判断请求消息，
//        System.out.println("time server recevie order:"+s);
//        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(s) ? new Date(System.currentTimeMillis()).toString() : "bad query";
//        ByteBuf buf = Unpooled.copiedBuffer(currentTime.getBytes());
//        context.write(buf);
//    }
//
//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        //调用ChannelHandlerContext的flush()方法，将消息发送队列中的消息写入到SocketChannel中发送给客户端
//        ctx.flush();
//    }
//
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        //发生异常时，关闭ChannelHandlerContext
//        ctx.close();
//    }
//}
