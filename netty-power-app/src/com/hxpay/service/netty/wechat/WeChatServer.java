package com.hxpay.service.netty.wechat;


import com.hxpay.service.nettyInterface.NettyServerInterface;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture; 
import io.netty.channel.ChannelInitializer; 
import io.netty.channel.ChannelOption; 
import io.netty.channel.EventLoopGroup; 
import io.netty.channel.nio.NioEventLoopGroup; 
import io.netty.channel.socket.SocketChannel; 
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder; 
import io.netty.handler.codec.http.HttpResponseEncoder; 
/*
 * 微信支付完成回调 处理
 * 
 */
public class WeChatServer  implements NettyServerInterface{

    
    public void start(int port) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                                @Override
                                public void initChannel(SocketChannel ch) throws Exception {
                                    // server端发送的是httpResponse，所以要使用HttpResponseEncoder进行编码
                                    ch.pipeline().addLast(new HttpResponseEncoder());
                                    // server端接收到的是httpRequest，所以要使用HttpRequestDecoder进行解码
                                    ch.pipeline().addLast(new HttpRequestDecoder());
                                    ch.pipeline().addLast(new WeChatServerInboundHandler());
                                }
                            }).option(ChannelOption.SO_BACKLOG, 128) 
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            System.out.println("启动回调服务");
            ChannelFuture f = b.bind(port).sync();

            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

   /* public static void main(String[] args) throws Exception {
        WeChatServer server = new WeChatServer();
        server.start(8890);
    }*/
}