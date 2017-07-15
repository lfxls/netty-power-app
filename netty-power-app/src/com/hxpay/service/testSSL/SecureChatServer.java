package com.hxpay.service.testSSL;

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

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.ClientAuth;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import java.io.File;

/**
 * Simple SSL chat server modified from {@link TelnetServer}.
 */
public final class SecureChatServer {
    static final int PORT = Integer.parseInt(System.getProperty("port", "8992"));

    public static void main(String[] args) throws Exception {
//    	chaim.pem为服务端证书链包含服务端证书和根证书
//    	root.cer为CA的根证书
//    	key为服务端私钥。
//        * @param keyCertChainFile an X.509 certificate chain file in PEM format
//        * @param keyFile a PKCS#8 private key file in PEM format
//    	File cerChainFile = new File("D:\\Program Files\\OpenSSL-Win64\\bin\\server\\server-cert.pem");
//    	File keyFile = new File("D:\\Program Files\\OpenSSL-Win64\\bin\\server\\pkcs8_key.txt");
//    	File rootFile = new File("D:\\Program Files\\OpenSSL-Win64\\bin\\ca\\ca-cert.cer");
//        SslContext sslCtx = SslContextBuilder.forServer(cerChainFile, keyFile)
//        		.trustManager(rootFile)
//        		.clientAuth(ClientAuth.REQUIRE)
//        		.build();
    	
//        SelfSignedCertificate ssc = new SelfSignedCertificate();
//        SslContext sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey())
//            .build();
    	
    	File certChainFile=new File("g:/SSL/server.crt");
        File keyFile=new File("g:/SSL/pkcs8_rsa_server.key");
        File rootFile=new File("g:/SSL/ca.crt");
        SslContext sslCtx = SslContextBuilder.forServer(certChainFile, keyFile).trustManager(rootFile)
                .clientAuth(ClientAuth.REQUIRE).build();

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class)
             .handler(new LoggingHandler(LogLevel.INFO))
             .childHandler(new SecureChatServerInitializer(sslCtx));

            b.bind(PORT).sync().channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
