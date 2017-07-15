package com.hxpay.service.netty.http;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.json.JSONException;
import org.json.XML;
import org.xml.sax.InputSource;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;

public class NettyHttpUtils {

	private String reqContent;
	private Map<String, String> map = new HashMap<String, String>();

	public Map<String, String> getMap() {
		return map;
	}

	public NettyHttpUtils(String reqContent) {
		this.reqContent = reqContent;
	}

	public NettyHttpUtils() {
	}

	public String getParams(String key) {
		if (reqContent == null || reqContent == "" || !map.containsKey(key)) {
			return "";
		}

		return map.get(key);
	}

	// 处理http post请求，&分隔参数（手机个人版）
	public void initData() {
		if (reqContent == null || reqContent == "") {
			return;
		}
		// 包含多个参数
		if (reqContent.contains("&")) {
			String[] arr = reqContent.split("&");
			String string = "";
			for (int i = 0; i < arr.length; i++) {
				string = arr[i];
				if (string.contains("=")) {
					String key = URLDecoder.decode(string.substring(0,
							string.indexOf("=")));
					String value = URLDecoder.decode(string.substring(string
							.indexOf("=") + 1));
					map.put(key, value);
				} else {
					map.clear();
					break;
				}

			}

		} else {
			if (reqContent.contains("=")) {
				String key = URLDecoder.decode(reqContent.substring(0,
						reqContent.indexOf("=")));
				String value = URLDecoder.decode(reqContent
						.substring(reqContent.indexOf("=") + 1));
				map.put(key, value);
			} else {
				map.clear();
				return;
			}
		}

	}

	public JSONObject getJson(String str) {
		return (JSONObject) JSONSerializer.toJSON(str);

	}

	// 客户端请求json字符串转换为xml
	public static String jsontoXml(String json) {
		try {
			XMLSerializer serializer = new XMLSerializer();
			JSON jsonObject = JSONSerializer.toJSON(json);
			String orign = serializer.write(jsonObject);
			int start = orign.indexOf("<o>") + "<o>".length();
			int end = orign.indexOf("</o>");
			if (start < 0 || end < 0 || start > end) {
				return "";
			}
			String reqxml = orign.substring(start, end);
			reqxml = "<ROOT>" + reqxml + "</ROOT>";
			return reqxml;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String maptoXml(Map<String, String> map) {
		if (map.size() <= 0) {
			return "";
		}
		String xmlData = "";
		for (String key : map.keySet()) {
			String value = map.get(key);
			xmlData = xmlData + "<" + key + ">" + value + "</" + key + ">";
		}

		return "<xml>" + xmlData + "</xml>";
	}

	// 服务端返回xml转json
	public static String xmltoJson(String xml) {
		try {
			XMLSerializer xmlSerializer = new XMLSerializer();
			return xmlSerializer.read(xml).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	public static Object getParam(org.json.JSONObject data_null) throws JSONException{
		org.json.JSONObject data = data_null;
		Iterator it = data.keys();  
        while (it.hasNext()) {  
            String key = (String) it.next();  
            Object temp = data.get(key);
            if (temp instanceof org.json.JSONObject) {
				org.json.JSONObject data_1 = (org.json.JSONObject) temp;
				getParam(data_1);
				List aa = new ArrayList();
				aa.add(temp);
				data.put(key, aa);
			} else if (temp instanceof org.json.JSONArray) {
				org.json.JSONArray temp_a = (org.json.JSONArray) temp;
				for (int i = 0; i < temp_a.length(); i++) {
					org.json.JSONObject data_1 = temp_a
							.getJSONObject(i);
					getParam(data_1);
				}
				data.put(key, temp);
			}
        }
		return data; 
	}

	public static String xmltoJson_O(String xml, String tran) {
		try {
			org.json.JSONObject xmlJSONObj = XML.toJSONObject(xml);
			org.json.JSONObject root = (org.json.JSONObject) xmlJSONObj
					.get("ROOT");
			if (root.has("DATA")) {
				Object data_null = root.get("DATA");
				if (data_null instanceof org.json.JSONObject) {
					org.json.JSONObject data = (org.json.JSONObject) data_null;
					getParam(data);
					if (data.has("DATA_1")) {
						Object temp = data.get("DATA_1");
						if (temp instanceof org.json.JSONObject) {
							List aa = new ArrayList();
							aa.add(temp);
							root.put("DATA", aa);
						} else if (temp instanceof org.json.JSONArray) {
							root.put("DATA", temp);
						}
					}
				}
			}
			String jsonPrettyPrintString = root.toString();
			return jsonPrettyPrintString;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public static void main(String[] args) {
		String tesstr = "aaa=bbb&ccc=dd";
		NettyHttpUtils net = new NettyHttpUtils(tesstr);
		net.initData();
		System.out.println(net.getParams("aaa"));

	}
}
