package com.hxpay.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.rpc.ServiceException;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;
import tangdi.ws.service.HexingWs;
import tangdi.ws.service.HexingWsService;
import tangdi.ws.service.HexingWsServiceLocator;

public class TransService extends HttpServlet {

   
    private static final long serialVersionUID = 1L;
    public  static final String RESP_ERROR_DATA1="900000";//请求数据为空
    public  static final String RESP_ERROR_DATA2="900001";//数据处理异常

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String age = req.getParameter("age");
        resp.setCharacterEncoding("UTF-8");

        resp.setContentType("text/html;charset=utf-8");

        PrintWriter out = resp.getWriter();

        out.println("<html><head><title>name&age</title></head");

        out.println("<body> name:" + name + "<br>");

        out.println("age:" + age + "<br></body></html>");

        System.out.println("name=" + name);
        System.out.println("age=" + age);
        out.flush();
    }
    
//    protected void doPost(HttpServletRequest req,HttpServletResponse resp){
//    	
//    	 StringBuffer url = req.getRequestURL();
//    	 if (req.getQueryString() != null) {
//    	  url.append('?');
//    	  url.append(req.getQueryString());
//    	 }
//    	 System.out.println("url"+url.toString());
//    	BufferedReader br;
//		try {
//			br = req.getReader();
//			String str, wholeStr = "";
//	    	while((str = br.readLine()) != null){
//	    	wholeStr += str;
//	    	}
//	    	System.out.println(wholeStr);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    	
//
//    	
//    }

   protected void doPost(HttpServletRequest req, HttpServletResponse resp){
    	 try {  
		        String jsondata = req.getParameter("json");
		        String tran = req.getParameter("tran");
		        resp.setContentType("text/html;charset=utf-8");
		        resp.setCharacterEncoding("utf-8");
		        PrintWriter out = resp.getWriter();
		        System.out.println("请求数据"+jsondata);
		        
		        // 请求数据为空
		        if(jsondata == null || "".equals(jsondata) || tran == null || "".equals(tran)){
		        	out.println(RESP_ERROR_DATA1);
		            out.flush();
		        }
		        
		        String xmldata = jsontoXml(jsondata);
		        if(xmldata.equals("")){
		        	out.println(RESP_ERROR_DATA2);
		            out.flush();
		        }
		        
		        String data  = conn(tran,xmldata);
		        String reqxmldata = xmltoJson(data);
		        
		        out.println(reqxmldata);
		        System.out.println("返回数据"+reqxmldata);
		        out.flush();
    	 }catch (Exception e) {
			e.printStackTrace();
			log(e.toString());
		} 
    }
    
    //
    public static String PNotifyQuery(String xmldata,String tran) throws RemoteException {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formatDateStr=formatter.format(currentTime);
		String str = conn(tran,xmldata);
		return str;
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
    
    
    // 客户端请求json字符串转换为xml
    public static String jsontoXml(String json) {  
        try {  
            XMLSerializer serializer = new XMLSerializer();  
            JSON jsonObject = JSONSerializer.toJSON(json);  
            String orign = serializer.write(jsonObject);  
            int start = orign.indexOf("<o>")+"<o>".length();
            int end  = orign.indexOf("</o>");
            if(start<0 || end<0 || start>end){
            	return "";
            }
            String reqxml = orign.substring(start, end);
            reqxml = "<ROOT>"+reqxml+"</ROOT>";
            return reqxml;
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
    
    //服务端返回xml转json
    public static String xmltoJson(String xml) {  
    	try{
    		XMLSerializer xmlSerializer = new XMLSerializer();  
            return xmlSerializer.read(xml).toString();  
    	} catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
        
    }  
    

}
