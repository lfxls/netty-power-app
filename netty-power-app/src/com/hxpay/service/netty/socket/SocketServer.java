package com.hxpay.service.netty.socket;

import com.hxpay.service.nettyInterface.NettyServerInterface;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

public class SocketServer  implements NettyServerInterface{
	public static InternalLogger logger = InternalLoggerFactory.getInstance(SocketServer.class);  
    /**
     * 服务端监听的端口地址
     */
    private static final int portNumber = 9093;
    
    public void start(int port){
    	
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup);
            b.channel(NioServerSocketChannel.class);
            b.childHandler(new SocketServerInitializer());

            // 服务器绑定端口监听
            ChannelFuture f = b.bind(port).sync();
            // 监听服务器关闭监听
            f.channel().closeFuture().sync();

            // 可以简写为
            /* b.bind(portNumber).sync().channel().closeFuture().sync(); */
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    
    	
    }
    
   /* public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup);
            b.channel(NioServerSocketChannel.class);
            b.childHandler(new SocketServerInitializer());

            // 服务器绑定端口监听
            ChannelFuture f = b.bind(portNumber).sync();
            // 监听服务器关闭监听
            f.channel().closeFuture().sync();

            // 可以简写为
             b.bind(portNumber).sync().channel().closeFuture().sync(); 
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }*/
}