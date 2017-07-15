package com.hxpay.service.netty.http;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.rmi.RemoteException;
import java.util.logging.Logger;

import javax.xml.rpc.ServiceException;

import com.hxpay.service.netty.startServices;

import net.sf.json.JSONObject;
import tangdi.ws.service.HexingWs;
import tangdi.ws.service.HexingWsService;
import tangdi.ws.service.HexingWsServiceLocator;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.HttpHeaders.Values;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder.EndOfDataDecoderException;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.InterfaceHttpData.HttpDataType;

public class KJHttpServerInboundHandler extends ChannelInboundHandlerAdapter {

	Logger logger = Logger.getLogger(KJHttpServerInboundHandler.class.getName());
    private HttpRequest request;
//  半包问题
  private String temHttpContent = "";//部分消息体
  private String AllHttpContent ="";//消息体字符串

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
    	
    	
    	
        if (msg instanceof HttpRequest) {
        	 request = (HttpRequest) msg;
             String uri = request.getUri();
             System.out.println("Uri:" + uri+"-----------------------------------------------------");
             temHttpContent = "";
             AllHttpContent = "";
        }
        if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent) msg;
            ByteBuf buf = content.content();
            
            temHttpContent = buf.toString(io.netty.util.CharsetUtil.UTF_8);
//            System.out.println(temHttpContent);
            buf.release();
            AllHttpContent = AllHttpContent + temHttpContent;
        }
        
        if(msg instanceof LastHttpContent){
//        	body消息必须使用json
        	String orgindata = AllHttpContent;
        	logger.info("原始数据"+orgindata);
//        	String jsonStr = URLDecoder.decode(orgindata);
        	NettyHttpUtils dechttp = new NettyHttpUtils(AllHttpContent);
        	dechttp.initData();
        	String tran = dechttp.getParams("tran");
//        	String jsonStr = dechttp.getParams("data");
////        	String xmldata = dechttp.jsontoXml(jsonStr);
//        	System.out.println("方法"+tran+"\r\n"+"请求："+xmldata);
//        	
//        	String data  = NettyHttpUtils.xmltoJson(conn(tran,xmldata));
        	
        	//空极测试服务
        	JSONObject json = JSONObject.fromObject(orgindata);
        	tran = (String) json.get("tran");
        	if(tran.equals("pushTest")){
        		String operation = (String) json.get("operation");
        		String type = (String) json.get("type");
        		 JSONObject body = new JSONObject();
        	        JSONObject body2 = new JSONObject();
        	        // h5地址推送
        	        body.put("TITLE", "h5 title");
        	        body.put("MSG", "h5 message");
        	        body.put("MSG_PUSH", type);
        	        body.put("OPERATION", operation);
        	        pushService.Push(body.toString());
        			
        		 FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
                         OK, Unpooled.wrappedBuffer("发送完成".getBytes("UTF-8")));
                 response.headers().set(CONTENT_TYPE, "text/plain");
                 response.headers().set(CONTENT_LENGTH,
                         response.content().readableBytes());
                 if (HttpHeaders.isKeepAlive(request)) {
                     response.headers().set(CONNECTION, Values.KEEP_ALIVE);
                 }
                 ctx.write(response);
                 ctx.flush();
                 return;
        	}
        	String data  = "";
        	String auth  = "";
        	if(json.has("data")){
        		data  = json.get("data").toString();
        	}
        	if(json.has("auth")){
        		 auth  = json.get("auth").toString();
        	}else{
        		
        	}
//        	System.out.println("方法"+tran+"\r\n"+"请求："+data+"~"+auth);
        	logger.info("方法"+tran+"\r\n"+"请求："+data+"~"+auth);
        	String xmldata = dechttp.jsontoXml(json.toString());
        	logger.info("请求服务端数据:"+xmldata);
        	String responseXml = conn(tran,xmldata);
        	logger.info("服务端返回数据:"+responseXml);
        	String responseStr  = NettyHttpUtils.xmltoJson_O(responseXml,tran);
        	logger.info("返回客户端数据:"+responseStr);
//        	String responseStr  = NettyHttpUtils.xmltoJson(responseXml);
        	
        	System.out.println("----------------------------------------------------");
        	
             FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
                     OK, Unpooled.wrappedBuffer(responseStr.getBytes("UTF-8")));
             response.headers().set(CONTENT_TYPE, "text/plain");
             response.headers().set(CONTENT_LENGTH,
                     response.content().readableBytes());
             if (HttpHeaders.isKeepAlive(request)) {
                 response.headers().set(CONNECTION, Values.KEEP_ALIVE);
             }
             ctx.write(response);
             ctx.flush();
        	
        }
    }
  

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    	System.out.println("Exception："+cause.toString());
        ctx.close();
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
			System.out.println("respxml:"+str);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return str;
	}

}