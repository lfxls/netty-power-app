package com.hxpay.service.netty.socket;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.nio.ByteOrder;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;

import com.hxpay.service.netty.http.NettyHttpUtils;

import tangdi.ws.service.HexingWs;
import tangdi.ws.service.HexingWsService;
import tangdi.ws.service.HexingWsServiceLocator;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

public class SocketServerHandler extends  ChannelInboundHandlerAdapter {
	 

//		  数据格式json字符串 格式 {"json":{},"tran":{}} 
//	{"json":{"TOP":{"SOURCE":"4","ACCOUNT_ID":"A0000000000619","SESSION_ID":"hpByzHpReXDPonE8CUzv","REQUEST_TIME":"2017-03-01 09:35:47","LOCAL_LANGUAGE":"en"},"BODY":{"USER_NO":"1031204402","ENEL_ID":"Y2015020700043"},"TAIL":{"SIGN_TYPE":"1","SIGNATURE":"6db6e6aa78257be251b7eeac7b05f7f8"}},"tran":"MBillQuery"}
	    public void channelRead(ChannelHandlerContext ctx, Object msg) {  
	        try {  
	            String body = (String) msg;  
	            System.out.println("原始数据:"+body);
	            
	          /*  XMLSerializer serializer = new XMLSerializer();  
	            JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(body);  
	        	String tran = jsonObject.getString("tran");
	        	String jsonStr = jsonObject.getString("json");
	        	String xmldata = NettyHttpUtils.jsontoXml(jsonStr);
	        	System.out.println("方法"+tran+"\r\n"+"请求："+xmldata);
	        	
	        	String respJson  = NettyHttpUtils.xmltoJson(conn(tran,xmldata));*/
	            
	            String respJson = "返回\n";
	  
	            byte[] respUtf8 = respJson.getBytes("UTF-8");  
	            int respLength = respUtf8.length;  
	            ByteBuf respLengthBuf = PooledByteBufAllocator.DEFAULT.buffer(4);  
	            respLengthBuf.writeInt(respLength);  
	            respLengthBuf.order(ByteOrder.BIG_ENDIAN);  
	            ctx.write(respLengthBuf);  
	            ByteBuf resp = PooledByteBufAllocator.DEFAULT.buffer(respUtf8.length);  
	            resp.writeBytes(respUtf8);  
	            ctx.write(resp);  
	        } catch (Exception e) {  
	        	SocketServer.logger.error(e.getMessage() + "\r\n");  
	            StringWriter sw = new StringWriter();  
	            PrintWriter pw = new PrintWriter(sw);  
	            e.printStackTrace(pw);  
	            pw.flush();  
	            sw.flush();  
	            SocketServer.logger.error(sw.toString());  
	        }  
	    }  
	  
	 
	    public void channelReadComplete(ChannelHandlerContext ctx) {  
	        ctx.flush();  
	    }  
	  

	    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {  
	       System.out.println(cause);
	    	ctx.close();  
	    }  

    
    /*@Override
    protected void channelRead(ChannelHandlerContext ctx, String msg) throws Exception {
        // 收到消息直接打印输出
        System.out.println(ctx.channel().remoteAddress() + " Say : " + msg);
        
    	String xmldata = msg;
    	String tran = "MWeChatRes";
    	String data  = conn(tran,xmldata);
    	
        // 返回客户端消息 - 我已经接收到了你的消息
        ctx.writeAndFlush(data+"\n");
    }*/
    
    /*
     * 
     * 覆盖 channelActive 方法 在channel被启用的时候触发 (在建立连接的时候)
     * 
     * channelActive 和 channelInActive 在后面的内容中讲述，这里先不做详细的描述
     * */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        
        System.out.println("RamoteAddress : " + ctx.channel().remoteAddress() + " active !");
        
        ctx.writeAndFlush( "Welcome to " + InetAddress.getLocalHost().getHostName() + " service!\n");
        
        super.channelActive(ctx);
    }
    
    public static String conn(String method, String reqxml)
			throws RemoteException {
		HexingWsService service = new HexingWsServiceLocator();
		HexingWs client = null;
		try {
			client = service.getHexingWsPort();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		String str = null;
		try {
			str = client.trans(method, reqxml);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return str;
	}
}