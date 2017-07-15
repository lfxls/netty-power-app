package com.hxpay.service.netty;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import com.hxpay.service.netty.http.HttpServer;
import com.hxpay.service.netty.socket.SocketServer;
import com.hxpay.service.netty.wechat.WeChatServer;
import com.hxpay.service.nettyInterface.NettyServerInterface;

public class startServices {
	Logger logger = Logger.getLogger(startServices.class.getName());
	
	public static String HEXPAY_ADDRESS;
	public static boolean HEXPAY_SSL = false;
	public static Map<String,String> map= new HashMap<String, String>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        try {
			new startServices().runNetty();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void runNetty(){  
		try{
			int port1 = 0 ,port2 ;
			
			Properties pro = new Properties();
			System.out.println(System.getProperty("user.dir")+"/netty-power.properies");
			FileInputStream in = new FileInputStream(System.getProperty("user.dir")+"/netty-power.properies");
			pro.load(in);
			String servercer = System.getProperty("user.dir")+"/SSL/"+pro.getProperty("ssl.servercer");
			String serverkey =  System.getProperty("user.dir")+"/SSL/"+pro.getProperty("ssl.serverkey");
			String rootcer = System.getProperty("user.dir")+"/SSL/"+pro.getProperty("ssl.rootcer");
			System.out.println(servercer);
			System.out.println(serverkey);
			System.out.println(rootcer);
			
			String hexadd = pro.getProperty("Hexaddress");
			
			String httport = pro.getProperty("http.port");
			String notifyport = pro.getProperty("notify.port");
			
			in.close();
			
			if(null == hexadd || "".equals(hexadd)){
				logger.info("主站地址未设置，启动失败");
				System.out.println("主站地址未设置，启动失败");
				return;
			}else{
				HEXPAY_ADDRESS = hexadd;
			}
			
			File serverfile = new File(servercer);
			File keyfile = new File(serverkey);
			File rootfile = new File(rootcer);
			
			if(!serverfile.exists() || !keyfile.exists() || !rootfile.exists()){
				logger.info("SSL缺少配置，使用http协议");
				System.out.println("SSL缺少配置，使用http协议");
			}else{
				logger.info("https协议");
				System.out.println("https协议");
				HEXPAY_SSL = true;
				map.put("servercer", servercer);
				map.put("serverkey", serverkey);
				map.put("rootcer", rootcer);
			}
			
			if(null == httport || "".equals(httport)){
				logger.info("服务端口未设置，启动失败");
				System.out.println("服务端口未设置，启动失败");
				return;
			}else{
				port2 = Integer.parseInt(httport);
			}
//			http服务
			 HttpServer hserver = new HttpServer();
			 NettyThread hthread = new NettyThread(hserver,port2);
			 hthread.start();
			
			if(null == notifyport || "".equals(notifyport)){
				logger.info("回调服务端口未设置");
				System.out.println("回调服务端口未设置");
				
			}else{
				port1 = Integer.parseInt(notifyport);
//	        	微信回调 webservice服务
	        	WeChatServer wserver = new WeChatServer();
	        	NettyThread wthread = new NettyThread(wserver,port1);
	        	wthread.start();
			}
			
        	
//			socket服务
//			SocketServer sserver = new SocketServer();
//			NettyThread sthread = new NettyThread(sserver,8892);
//			sthread.start();
			
			
		}catch(IOException e){
			e.printStackTrace();
			logger.info(e.toString());
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	public class NettyThread extends Thread{  
	    private NettyServerInterface nettyServerInterface; 
	    private int port;
	    public NettyThread(NettyServerInterface nettyServerInterface, int port) {  
	       this.nettyServerInterface=nettyServerInterface; 
	       this.port = port;
	    }  
	    public void run() {
	    	nettyServerInterface.start(port);
	    }  
	}  

}
