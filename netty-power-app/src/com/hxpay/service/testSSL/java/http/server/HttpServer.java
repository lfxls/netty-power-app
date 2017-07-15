package com.hxpay.service.testSSL.java.http.server;


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
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

public class HttpServer {
	static final int PORT = Integer.parseInt(System.getProperty("port", "8992"));
	SslContext sslCtx;
    
    public void start(int port) throws Exception {
//    	File certChainFile=new File("g:/SSL/server.crt");
//        File keyFile=new File("g:/SSL/pkcs8_rsa_server.key");
//        File rootFile=new File("g:/SSL/ca.crt");
//        sslCtx = SslContextBuilder.forServer(certChainFile, keyFile).trustManager(rootFile)
//                .clientAuth(ClientAuth.REQUIRE).build();
    	
//    	 System.setProperty("javax.net.ssl.keyStore", "G:/SSL/keytool/tomcat.jks");  
//         System.setProperty("javax.net.ssl.keyStorePassword", "123456");  
//         SelfSignedCertificate ssc = new SelfSignedCertificate();
//         sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey())
//             .build();
    	
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                                @Override
                                public void initChannel(SocketChannel ch) throws Exception {
//                                	ch.pipeline().addLast(sslCtx.newHandler(ch.alloc()));
                                    // server端发送的是httpResponse，所以要使用HttpResponseEncoder进行编码
                                    ch.pipeline().addLast(new HttpResponseEncoder());
                                    // server端接收到的是httpRequest，所以要使用HttpRequestDecoder进行解码
                                    ch.pipeline().addLast(new HttpRequestDecoder());
                                    ch.pipeline().addLast(new HttpServerInboundHandler());
                                }
                            }).option(ChannelOption.SO_BACKLOG, 128) 
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = b.bind(port).sync();

            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        HttpServer server = new HttpServer();
        server.start(8992);
    }
}