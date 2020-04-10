package com.bzdgs.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TimeServer {
    public void bind(int port){
        /*
            1 创建两个NioEventLoopGroup实例，NioEventLoopGroup是一个线程组，包含了一组NIO线程，专门用来处理网络事件处理；本质上就是Reactor线程组
            2 服务端创建两个NioEventGroup对象，一个用于服务端接收客户端的连接；
                另一个用于进行SocketChannel的网络读写
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //ServerBootstrap对象时Netty用于启动服务端的辅助启动类，目的是降低服务端的开发复杂度
            ServerBootstrap bootstrap = new ServerBootstrap();
            /*
             3 调用ServerBootstrap对象的group()方法，将两个NioEventLoopGroup对象传递到ServerBootstrap中；
             4 配置Channel为NioServerSocketCahnnel,对应于JDK中的ServerSocketChannel类
             5 配置NioServerSocketChannel的TCP参数，设置backlog为1024
             6 配置ChildChannelHandler,作用类似于Reactor模式中的handler，主要处理网络I/O事件，例如记录日志，对消息进行编解码等
             */
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,1024)
                    .childHandler(new ChildChannelHandler());
            //7 调用bootstrap的bind()方法绑定监听端口，在调用同步阻塞方法sync()等待绑定完成并返回一个ChannelFuture，用于异步操作的通知回调；
            ChannelFuture future = null;
            future = bootstrap.bind(port).sync();
            //8 调用future对象的channel().closeFuture().sync()方法，进行阻塞，等待服务端链路关闭后main函数才退出
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            //9 关闭资源；调用shutdownGracefuuly()优雅退出，它会释放跟shutdownGracefully相关的资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(new TimeServerHandler());
        }

    }
    public static void main(String[] args) {
        int port = 8081;
        if(args != null && args.length > 1){
            port = Integer.valueOf(args[0]);
        }

        new TimeServer().bind(port);
    }
}
