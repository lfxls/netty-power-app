package com.hxpay.service.netty.wechat;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;





import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import com.hxpay.service.netty.http.NettyHttpUtils;

import tangdi.ws.service.HexingWs;
import tangdi.ws.service.HexingWsService;
import tangdi.ws.service.HexingWsServiceLocator;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpHeaders.Values;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.LastHttpContent;

public class WeChatServerInboundHandler extends ChannelInboundHandlerAdapter {


    private HttpRequest request;
    
//    半包问题
    private String temHttpContent = "";//部分消息体
    private String AllHttpContent ="";//消息体字符串

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
    	System.out.println(msg);
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
        	
        	System.out.println(AllHttpContent);
//        	String xmldata = AllHttpContent.replace("<xml>", "<ROOT>").replace("</xml>", "</ROOT>");
        	String xmldata = "";
        	String tran = "";
//        	微信回调xml格式
        	if(AllHttpContent.contains("<xml>")){
        		xmldata = AllHttpContent;
            	tran = "MWeChatRes";
        	}else{//支付宝回调 字符串格式 :key=value &分隔
        		NettyHttpUtils netutil = new NettyHttpUtils(AllHttpContent);
        		netutil.initData();
        		xmldata = NettyHttpUtils.maptoXml(netutil.getMap());
//        		xmldata = xmldata + "<ORGINDATA>" + AllHttpContent + "</ORGINDATA>";
        		StringBuilder sb = new StringBuilder(xmldata);//构造一个StringBuilder对象
                sb.insert(xmldata.indexOf("</xml>"), "<orgindata>" + AllHttpContent + "</orgindata>");//在指定的位置1，插入指定的字符串
                xmldata = sb.toString().replaceAll("&", "&amp;");
            	tran = "MAlipayRes";
        		
        	}
        	System.out.println("请求hexpay交易码："+tran);
        	System.out.println("hexpay 请求xml参数"+xmldata);
        	String data  = conn(tran,xmldata);
        	
        	if(!AllHttpContent.contains("<xml>")){//支付宝成功信息 只返回success
        		if(data.contains("SUCCESS")){
        			data = "success";
        		}
        	}
        	
        	System.out.println("----------------------------------------------------");
        	System.out.println(data);
        	
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
    

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }

}