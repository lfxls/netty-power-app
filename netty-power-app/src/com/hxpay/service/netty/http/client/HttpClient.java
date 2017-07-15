package com.hxpay.service.netty.http.client;

import io.netty.bootstrap.Bootstrap;
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

import java.net.URI;

import com.hxpay.service.testSSL.java.http.client.HttpClientInboundHandler;

public class HttpClient {
	
	public void connect(String host,int port){
		EventLoopGroup workGroup = new NioEventLoopGroup();
		try{
			Bootstrap b = new Bootstrap();	
			b.group(workGroup);
			b.channel(NioSocketChannel.class);
			b.option(ChannelOption.SO_KEEPALIVE, true);
			b.handler(new ChannelInitializer<SocketChannel>(){

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					// TODO Auto-generated method stub
					ch.pipeline().addLast(new HttpResponseDecoder());
					ch.pipeline().addLast(new HttpRequestEncoder());
					ch.pipeline().addLast(new HttpClientInboundHandler());
					
				}
				
			});
			
			ChannelFuture f = b.connect(host, port).sync();

			URI uri = new URI("http://172.16.101.27:9091/api/services/hexingws?wsdl");
			DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, uri.toString());
			
			request.headers().set(HttpHeaders.Names.HOST,host);
			request.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
            request.headers().set(HttpHeaders.Names.CONTENT_LENGTH, request.content().readableBytes());
            // 发送http请求
            f.channel().write(request);
            f.channel().flush();
            f.channel().closeFuture().sync();
			
		}catch(Exception e){
			
		}
	}
	
	
}
