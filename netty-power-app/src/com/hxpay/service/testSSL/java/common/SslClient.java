package com.hxpay.service.testSSL.java.common;

import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class SslClient {  
    public static void main(String[] args) throws Exception {  
//        System.setProperty("javax.net.debug", "ssl,handshake");  

        
        System.setProperty("javax.net.ssl.trustStore", "G:/SSL/keytool/client.truststore");    
	    System.setProperty("javax.net.ssl.trustStorePassword","123456");    
	    System.setProperty("javax.net.ssl.keyStoreType","PKCS12") ;    
	    System.setProperty("javax.net.ssl.keyStore","G:/SSL/keytool/client.p12") ;    
	    System.setProperty("javax.net.ssl.keyStorePassword","123456") ;  
    	
//    	System.setProperty("javax.net.ssl.trustStore", "G:/SSL/keytool/tomcat.jks");  
//        System.setProperty("javax.net.ssl.trustStorePassword", "123456");  

        SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory  
                .getDefault();  
        SSLSocket sslsocket = (SSLSocket) sslsocketfactory.createSocket(  
                "127.0.0.1", 9100);  

        OutputStream outputStream = sslsocket.getOutputStream();  
        BufferedWriter bufferedWriter = new BufferedWriter(  
                new OutputStreamWriter(outputStream));  
        bufferedWriter.write("client消息\n");  
        bufferedWriter.flush();  
        
        InputStreamReader inputStreamReader = new InputStreamReader(sslsocket.getInputStream());
        while(true){
        	char[] cbuf = new char[512];
        	inputStreamReader.read(cbuf);
        	System.out.println(new String(cbuf));
        }

//        TimeUnit.SECONDS.sleep(2000);  
    }  
}  