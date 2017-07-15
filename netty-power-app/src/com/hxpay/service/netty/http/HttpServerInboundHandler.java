package com.hxpay.service.netty.http;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;







import java.net.URLDecoder;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

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
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.HttpHeaders.Values;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;

public class HttpServerInboundHandler extends ChannelInboundHandlerAdapter {


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
        	System.out.println("原始数据"+orgindata);
//        	String jsonStr = URLDecoder.decode(orgindata);
        	NettyHttpUtils dechttp = new NettyHttpUtils(AllHttpContent);
//        	dechttp.initData();
//        	String tran = dechttp.getParams("tran");
//        	String jsonStr = dechttp.getParams("json");
        	JSONObject json = dechttp.getJson(AllHttpContent);
        	
        	String tran = json.getString("tran");
        	String jsonStr = AllHttpContent;
        	
        	String xmldata = dechttp.jsontoXml(jsonStr);
        	System.out.println("方法"+tran+"\r\n"+"请求："+xmldata);
        	
        	String data  = NettyHttpUtils.xmltoJson(conn(tran,xmldata));
        	
        	//空极测试服务
//        	JSONObject json = JSONObject.fromObject(orgindata);
//        	tran = (String) json.get("tran");
//        	String data  = "";
        	
        	System.out.println(data);
        	System.out.println("----------------------------------------------------");
        	
             FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
                     OK, Unpooled.wrappedBuffer(data.getBytes("UTF-8")));
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
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return str;
	}

}