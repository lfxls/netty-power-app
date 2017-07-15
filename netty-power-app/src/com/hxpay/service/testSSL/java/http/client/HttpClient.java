package com.hxpay.service.testSSL.java.http.client;


import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import java.io.File;
import java.net.URI;

public class HttpClient {
	SslContext sslCtx;
//    static final String HOST = System.getProperty("host", "127.0.0.1");
//    static final int PORT = Integer.parseInt(System.getProperty("port", "9091"));
    public void connect(String host, int port) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
//        File certChainFile=new File("g:/SSL/client.crt");
//        File keyFile=new File("g:/SSL/pkcs8_rsa_client.key");
//        File rootFile=new File("g:/SSL/ca.crt");
//        final SslContext sslCtx = SslContextBuilder.forClient().keyManager(certChainFile, keyFile).
//                trustManager(rootFile).build();
        
//        System.setProperty("javax.net.ssl.trustStore", "G:/SSL/keytool/client.truststore");    
//	    System.setProperty("javax.net.ssl.trustStorePassword","123456");  
//        File rootFile = new File("G:/SSL/keytool/tomcat.jks");
//      final SslContext sslCtx = SslContextBuilder.forClient()
//      .trustManager(rootFile).build();
      
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
//                	ch.pipeline().addLast(sslCtx.newHandler(ch.alloc(), HttpClient.HOST, HttpClient.PORT));
                    // 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
                    ch.pipeline().addLast(new HttpResponseDecoder());
                    // 客户端发送的是httprequest，所以要使用HttpRequestEncoder进行编码
                    ch.pipeline().addLast(new HttpRequestEncoder());
                    ch.pipeline().addLast(new HttpClientInboundHandler());
                    

                    // Add SSL handler first to encrypt and decrypt everything.
                    // In this example, we use a bogus certificate in the server side
                    // and accept any invalid certificates in the client side.
                    // You will need something more complicated to identify both
                    // and server in the real world.
//                    ch.pipeline().addLast(sslCtx.newHandler(ch.alloc(), HttpClient.HOST, HttpClient.PORT));

                    // On top of the SSL handler, add the text line codec.
//                    ch.pipeline().addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
//                    ch.pipeline().addLast(new StringDecoder());
//                    ch.pipeline().addLast(new StringEncoder());
                }
            });

            // Start the client.
            ChannelFuture f = b.connect(host, port).sync();

//            URI uri = new URI("https://127.0.0.1:8890");
            URI uri = new URI("http://172.16.101.27:9091/api/services/hexingws?wsdl");
//            String msg = "json=&tran=PNotifyQuery";
//            DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET,
//                    uri.toASCIIString(), Unpooled.wrappedBuffer(msg.getBytes("UTF-8")));
            DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "http://172.16.101.27:9091/api/services/hexingws?wsdl");

            // 构建http请求
            request.headers().set(HttpHeaders.Names.HOST, host);
            request.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
            request.headers().set(HttpHeaders.Names.CONTENT_LENGTH, request.content().readableBytes());
            // 发送http请求
            f.channel().write(request);
            f.channel().flush();
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws Exception {
        HttpClient client = new HttpClient();
        client.connect("172.16.101.27", 9091);
    }
}
