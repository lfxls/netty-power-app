/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.hxpay.service.testSSL;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.ssl.ClientAuth;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URI;

/**
 * Simple SSL chat client modified from {@link TelnetClient}.
 */
public final class SecureChatClient {

    static final String HOST = System.getProperty("host", "172.16.101.27");
    static final int PORT = Integer.parseInt(System.getProperty("port", "8890"));

    public static void main(String[] args) throws Exception {
        // Configure SSL.
//    	client.pem为客户端证书链包含客户端证书和根证书
//    	root.cer为CA的根证书，此证书与服务端使用的相同
//    	client为客户端私钥。
//    	File cerChainFile = new File("D:\\Program Files\\OpenSSL-Win64\\bin\\client\\client-cert.pem");
//    	File keyFile = new File("D:\\Program Files\\OpenSSL-Win64\\bin\\client\\client-key.pem");
//    	File rootFile = new File("D:\\Program Files\\OpenSSL-Win64\\bin\\ca\\ca-cert.cer");
//        SslContext sslCtx = SslContextBuilder.forServer(cerChainFile, keyFile)
//        		.trustManager(rootFile)
//        		.clientAuth(ClientAuth.REQUIRE)
//        		.build();
    	
//    	String keyStore = "G:/SSL/clientcert01.jks";
//    	String keyStore_Pwd = "123456";
//    	
//    	System.setProperty("javax.net.ssl.keyStore", keyStore);
//		System.setProperty("javax.net.ssl.keyStorePassword",
//				keyStore_Pwd);
//		System.setProperty("javax.net.ssl.trustStore", keyStore);
//		System.setProperty("javax.net.ssl.trustStorePassword",
//				keyStore_Pwd);
//    	
//        final SslContext sslCtx = SslContextBuilder.forClient()
//            .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
    	
    	
    	File certChainFile=new File("g:/SSL/client.crt");
        File keyFile=new File("g:/SSL/pkcs8_rsa_client.key");
        File rootFile=new File("E:/workspace/netty-power-app/SSL/ca.crt");
        final SslContext sslCtx = SslContextBuilder.forClient().keyManager(certChainFile, keyFile).
                trustManager(rootFile).build();
        
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
             .channel(NioSocketChannel.class)
             .handler(new SecureChatClientInitializer(sslCtx));

            // Start the connection attempt.
            Channel ch = b.connect(HOST, PORT).sync().channel();

            // Read commands from the stdin.
            ChannelFuture lastWriteFuture = null;
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            for (;;) {
                String line = in.readLine();
                if (line == null) {
                    break;
                }

                // Sends the received line to the server.
                lastWriteFuture = ch.writeAndFlush(line + "\r\n");

                // If user typed the 'bye' command, wait until the server closes
                // the connection.
                if ("bye".equals(line.toLowerCase())) {
                    ch.closeFuture().sync();
                    break;
                }
            }
            lastWriteFuture = ch.writeAndFlush("connect server" + "\r\n");

//             Wait until all messages are flushed before closing the channel.
            if (lastWriteFuture != null) {
                lastWriteFuture.sync();
            }
            
        } finally {
            // The connection is closed automatically on shutdown.
            group.shutdownGracefully();
        }
    }
}
