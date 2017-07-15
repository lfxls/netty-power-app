package com.hxpay.service.netty.http;


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
import io.netty.handler.ssl.ClientAuth;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;

import java.io.File;
import java.util.Map;

import javax.net.ssl.SSLException;

import com.hxpay.service.netty.startServices;
import com.hxpay.service.nettyInterface.NettyServerInterface;

public class HttpServer implements NettyServerInterface{
	private SslContext sslCtx;
    
    public void start(int port){

    	if(startServices.HEXPAY_SSL){
//    		File certChainFile=new File("g:/MTN/HEXING.pem");
//    	    File keyFile=new File("g:/MTN/hexing_eneo_cm.key");
//    	    File rootFile=new File("g:/MTN/MTNHROOT.pem");
    		
    		Map<String,String> map = startServices.map;
    		File certChainFile=null;
    	    File keyFile=null;
    	    File rootFile=null;
    		if(null != map){
        		certChainFile=new File(map.get("servercer"));
        	    keyFile=new File(map.get("serverkey"));
        	    rootFile=new File(map.get("rootcer"));
    		}else{
    			return;
    		}
    	      try {
//    			sslCtx = SslContextBuilder.forServer(certChainFile, keyFile).trustManager(rootFile)
//    			          .clientAuth(ClientAuth.REQUIRE).build();
    			sslCtx = SslContextBuilder.forServer(certChainFile, keyFile).build();
    		} catch (SSLException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
    	}else{
    		sslCtx = null;
    	}
    	
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                                @Override
                                public void initChannel(SocketChannel ch) throws Exception {
                                	if(sslCtx != null){
                                		ch.pipeline().addLast(sslCtx.newHandler(ch.alloc()));
                                	}
                                    // server端发送的是httpResponse，所以要使用HttpResponseEncoder进行编码
                                    ch.pipeline().addLast(new HttpResponseEncoder());
                                    // server端接收到的是httpRequest，所以要使用HttpRequestDecoder进行解码
                                    ch.pipeline().addLast(new HttpRequestDecoder());
                                    ch.pipeline().addLast(new KJHttpServerInboundHandler());
                                }
                            }).option(ChannelOption.SO_BACKLOG, 128) 
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            System.out.println("启动http服务");
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
        HttpServer server = new HttpServer();
        server.start(9094);
    }*/
}