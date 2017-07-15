package com.hxpay.service.netty.http;

import java.io.FileInputStream;
import java.util.Properties;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import javapns.back.DeviceFactory;
import javapns.back.PushNotificationManager;
import javapns.back.SSLConnectionHelper;
import javapns.data.Device;
import javapns.data.PayLoad;
import javapns.data.PayLoadCustomAlert;
import javapns.notification.PushNotificationPayload;
public class pushService {
	protected static final Logger logger = Logger.getLogger(pushService.class);
	public static void Push(String bodystr) {
		   
		   
		  try {
			  Properties pro = new Properties();
				System.out.println(System.getProperty("user.dir")+"/netty-power.properies");
				FileInputStream in = new FileInputStream(System.getProperty("user.dir")+"/netty-power.properies");
				pro.load(in);
				String certificatePath = System.getProperty("user.dir")+"/SSL/"+pro.getProperty("ioscer");
			  
//			  {"aps":{"sound":"default","alert":"测试我的push消息","badge":1}}
//		           String deviceToken = "daab9f015d115f2c0d7d21f4e070fec257ac0a3dc5b40f4a513705cd0e2dc050";
			  String deviceToken = pro.getProperty("token");
		           //被推送的iphone应用程序标示符      
		           PayLoad payLoad = new PayLoad();
		           
//		           payLoad.addAlert("测试我的push消息");
		           PayLoadCustomAlert paramPayLoadCustomAlert = new PayLoadCustomAlert();
//		           JSONObject body = new JSONObject();
//		           body.put("TITLE", "My title");
//		           body.put("MSG", "My message");
//		           body.put("MSG_PUSH", "00");
//		           body.put("OPERATION", "http://test.com");
//		           String bodystr = body.toJSONString();
		           paramPayLoadCustomAlert.addBody(bodystr);
		           payLoad.addCustomAlert(paramPayLoadCustomAlert);
		           payLoad.addBadge(1);
		           payLoad.addSound("default");
		           
//		           PushNotificationBigPayload payload = PushNotificationBigPayload.complex();
//		           payload.addCustomAlertTitle("Game Request");
//		           payload.addCustomAlertBody("Bob wants to play poker");
//		           payload.addCustomAlertActionLocKey("PLAY");
//		           payload.addBadge(5);
//		           payload.addCustomDictionary("acme1", "bar");
//		           payload.addCustomDictionary("acme2", Arrays.asList("bang", "whiz"));
		           
		           PushNotificationManager pushManager = PushNotificationManager.getInstance();
		           pushManager.addDevice("iphone", deviceToken);
		           
		           		           //测试推送服务器地址：gateway.sandbox.push.apple.com /2195 
		      	   //产品推送服务器地址：gateway.push.apple.com / 2195 
		           String host="gateway.sandbox.push.apple.com";  //测试用的苹果推送服务器
		           int port = 2195;
		           
//		           String certificatePath = "G:/Opay/HEXPAY_DEV_PUSH.p12"; //刚才在mac系统下导出的证书
		           
		           String certificatePassword= "1234";
		          
		           pushManager.initializeConnection(host, port, certificatePath,certificatePassword, SSLConnectionHelper.KEYSTORE_TYPE_PKCS12);
		                     
		           //Send Push
		           Device client = pushManager.getDevice("iphone");
		           pushManager.sendNotification(client, payLoad); //推送消息
		           pushManager.stopConnection();
		           pushManager.removeDevice("iphone");
		          }
		          catch (Exception e) {
		           e.printStackTrace();
		           logger.info("push faild!");
		            return;
		          }
		  			logger.info("push succeed!");
		         }
	
	public static void main(String args[]){
        JSONObject body = new JSONObject();
        JSONObject body2 = new JSONObject();
        // h5地址推送
        body.put("TITLE", "h5 title");
        body.put("MSG", "h5 message");
        body.put("MSG_PUSH", "00");
        body.put("OPERATION", "http://h5test.com");
		Push(body.toString());
		// 打开特定页面
        body.put("TITLE", "page title");
        body.put("MSG", "page message");
        body.put("MSG_PUSH", "01");
        body.put("OPERATION", "grade");
		Push(body.toString());
	}

}