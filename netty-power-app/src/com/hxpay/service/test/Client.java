package com.hxpay.service.test;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.xml.rpc.ServiceException;

import org.apache.axis.utils.StringUtils;

import tangdi.ws.service.HexingWs;
import tangdi.ws.service.HexingWsProxy;
import tangdi.ws.service.HexingWsService;
import tangdi.ws.service.HexingWsServiceLocator;

public class Client {
	public static void main(String[] args) throws RemoteException {
//		System.out.println(PUpdateAndVerifyTime());//�汾����
		 System.out.println(Login());//��¼
//		System.out.println(PNotifyQuery());//�����ѯ
//		 System.out.println(PBillQuery());//�շѲ�ѯ 
		 //System.out.println(PBillFeeCount());//�۵����Ѽ���
//		 System.out.println(PBillReprintQuery());//ƾ֤��ѯ
//		 System.out.println(PBillReprint());//ƾ֤����
//		 System.out.println(PBillRecoveryQuery());//�����ѯ��
//		 System.out.println(PBillReversalApp());//��������
//		System.out.println(PBillIncomeQuery());//��֧��ϸ��ѯ
//		System.out.println(PBillDailyQuery());//�ս��ѯ
//		System.out.println(PBillDailyConfirm());//�սᴦ��
//		System.out.println(PBillDailyApply());//�����ս�
//		System.out.println(PBillBuy("TtDi1AtGpKpLRZgOX0cF","07042464888"));//�����շ�
//		System.out.println(PBillPay());//�˵��շ�
//		System.out.println(PSettingUpload());//ϵͳ��Ϣ�ϴ�
//		System.out.println(ReadCard());//�Զ�����
//		System.out.println(PCardBanding());//�󿨽ӿ�
//		 test();
//		System.out.println(MWeChatRes());
		
		 
		 
	}
	
	public static String MWeChatRes() throws RemoteException {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formatDateStr=formatter.format(currentTime);
		System.out.println(
				"<ROOT>"
						+ "<TOP><VERSION>1.0</VERSION><SOURCE>0</SOURCE><IMEI>762845024199122</IMEI><REQUEST_TIME>"+formatDateStr+"</REQUEST_TIME><LOCAL_LANGUAGE>zh</LOCAL_LANGUAGE></TOP>"
						+ "<BODY></BODY>"
						+ "</ROOT>");
		String str = conn(//�����ѯ
				"MWeChatRes",
				"<ROOT  class=\"object\">"
				+ "<TOP  class=\"object\"><VERSION type=\"string\">1.0</VERSION><SOURCE type=\"string\">0</SOURCE><IMEI type=\"string\">762845024199122</IMEI><REQUEST_TIME type=\"string\">"+formatDateStr+"</REQUEST_TIME><LOCAL_LANGUAGE type=\"string\">zh</LOCAL_LANGUAGE></TOP>"
				+ "<BODY  class=\"object\"></BODY>"
				+ "</ROOT>");
		return str;
	}

	public static String Login() throws RemoteException {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formatDateStr=formatter.format(currentTime);
//		System.out.println(
//				"<ROOT>"
//						+ "<TOP><VERSION>1.0</VERSION><SOURCE>0</SOURCE><IMEI>762845024199122</IMEI><REQUEST_TIME>"+formatDateStr+"</REQUEST_TIME><LOCAL_LANGUAGE>zh</LOCAL_LANGUAGE></TOP>"
//						+ "<BODY><OPER_ID>admin</OPER_ID><SYS_PID>1031204498</SYS_PID><LOGIN_PWD>9f91a454c218b386ee872d7458a934f3</LOGIN_PWD></BODY>"
//						+ "</ROOT>");
		//���ԣ�4100014ca92f07d6ee872d7458a934f3  ����9f91a454c218b386ee872d7458a934f3
//		String str = conn(//��¼
//				"PLogin",
//				"<ROOT>"
//				+ "<TOP><VERSION>1.0</VERSION><SOURCE>0</SOURCE><IMEI>862845024199122</IMEI><REQUEST_TIME>"+formatDateStr+"</REQUEST_TIME><LOCAL_LANGUAGE>zh</LOCAL_LANGUAGE></TOP>"
//				+ "<BODY><OPER_ID>admin</OPER_ID><SYS_PID>1031204498</SYS_PID><LOGIN_PWD>4100014ca92f07d6ee872d7458a934f3</LOGIN_PWD></BODY>"
//				+ "</ROOT>");
		System.out.println("<ROOT>"
				+ "<TOP><VERSION>1.0</VERSION><SOURCE>0</SOURCE><IMEI>762845024199122</IMEI><REQUEST_TIME>"+formatDateStr+"</REQUEST_TIME><LOCAL_LANGUAGE>en</LOCAL_LANGUAGE></TOP>"
				+ "<BODY><OPER_ID>admin</OPER_ID><SYS_PID>1031204498</SYS_PID><LOGIN_PWD>4100014ca92f07d6ee872d7458a934f3</LOGIN_PWD></BODY>"
				+ "</ROOT>");
//		String str = conn(//��¼
//				"PLogin",
//				"<ROOT>"
//				+ "<TOP><VERSION>1.0</VERSION><SOURCE>0</SOURCE><IMEI>762845024199122</IMEI><REQUEST_TIME>"+formatDateStr+"</REQUEST_TIME><LOCAL_LANGUAGE>en</LOCAL_LANGUAGE></TOP>"
//				+ "<BODY><OPER_ID>admin</OPER_ID><SYS_PID>1031204498</SYS_PID><LOGIN_PWD>4100014ca92f07d6ee872d7458a934f3</LOGIN_PWD></BODY>"
//				+ "</ROOT>");
//		String str = conn(//��¼
//				"HLogin",
//				"<ROOT><data><COUNTRY_CODE>df</COUNTRY_CODE><DEVICE_TOKEN>dsfsdgsdgsdgdsg</DEVICE_TOKEN><DEVICE_TYPE>android</DEVICE_TYPE><LOGIN_PWD>dsfsdfdsf</LOGIN_PWD><MOBILE_NO>18258228248</MOBILE_NO></data><tran>HLogin</tran></ROOT>");
		
		String str = conn(//��¼
				"HQryMeter",
				"<ROOT><auth class=\"object\"><SOURCE type=\"string\">5</SOURCE></auth><data class=\"object\"><ENEL_ID type=\"string\">Y2016021900074</ENEL_ID><METER_NO type=\"string\">014000000019</METER_NO><SESSION_ID type=\"string\">dsfdsfdsfdsfdsf</SESSION_ID></data><tran type=\"string\">HQryMeter</tran></ROOT>");
		return str;
	}
	public static String PUpdateAndVerifyTime() throws RemoteException {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		currentTime="";
		String formatDateStr=formatter.format(currentTime);
		formatDateStr="2016-01-06 10:12:21";
//		System.out.println(
//				"<ROOT>"
//						+ "<TOP><VERSION>1001</VERSION><SOURCE>0</SOURCE><IMEI>762845024199122</IMEI><REQUEST_TIME>"+formatDateStr+"</REQUEST_TIME><LOCAL_LANGUAGE>zh</LOCAL_LANGUAGE></TOP>"
//						+ "<BODY><TEMP_VERSION>2.0</TEMP_VERSION></BODY>"
//						+ "</ROOT>");
		String str = conn(//�汾����
				"PUpdateAndVerifyTime",
				"<ROOT>"
				+ "<TOP><VERSION>5000</VERSION><SOURCE>0</SOURCE><IMEI>762845024199122</IMEI><REQUEST_TIME>"
				+ formatDateStr+"</REQUEST_TIME><LOCAL_LANGUAGE>zh</LOCAL_LANGUAGE></TOP>"
				+ "<BODY><TEMP_VERSION>0</TEMP_VERSION></BODY>"
				+ "</ROOT>");
		return str;
	}
	public static String PNotifyQuery() throws RemoteException {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formatDateStr=formatter.format(currentTime);
		System.out.println(
				"<ROOT>"
						+ "<TOP><VERSION>1.0</VERSION><SOURCE>0</SOURCE><IMEI>762845024199122</IMEI><REQUEST_TIME>"+formatDateStr+"</REQUEST_TIME><LOCAL_LANGUAGE>zh</LOCAL_LANGUAGE></TOP>"
						+ "<BODY></BODY>"
						+ "</ROOT>");
		String str = conn(//�����ѯ
				"PNotifyQuery",
				"<ROOT  class=\"object\">"
				+ "<TOP  class=\"object\"><VERSION type=\"string\">1.0</VERSION><SOURCE type=\"string\">0</SOURCE><IMEI type=\"string\">762845024199122</IMEI><REQUEST_TIME type=\"string\">"+formatDateStr+"</REQUEST_TIME><LOCAL_LANGUAGE type=\"string\">zh</LOCAL_LANGUAGE></TOP>"
				+ "<BODY  class=\"object\"></BODY>"
				+ "</ROOT>");
		return str;
	}
	//�շѲ�ѯ
	public static String PBillQuery() throws RemoteException {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formatDateStr=formatter.format(currentTime);
		 TreeMap<String, String> paramMap = new TreeMap<String, String>();
			paramMap.put("IMEI", "762845024199122");
			paramMap.put("REQUEST_TIME", formatDateStr);
			paramMap.put("SESSION_ID","tp4szp3LxQYb5FlIHXAU");
			paramMap.put("LOCAL_LANGUAGE", "zh");
			paramMap.put("SIGN_TYPE", "1");
			paramMap.put("USER_NO", "17042464888");
//			paramMap.put("METER_NO", "07042464888");
			paramMap.put("PAGENUM", "1");
			paramMap.put("NUMPERPAG", "3");
			String signStr = bulidParam(paramMap);//key
			String sign = Md5Algorithm.getInstance().md5Digest(signStr.getBytes());
			System.out.println("SIGN: "+sign);
//			System.out.println(
//					"<ROOT>"
//							+  "<TOP><IMEI>762845024199122</IMEI><SESSION_ID>cez1dBZjDnBw9pZgGx6v</SESSION_ID><REQUEST_TIME>"+formatDateStr+"</REQUEST_TIME><LOCAL_LANGUAGE>zh</LOCAL_LANGUAGE></TOP>"
//							+ "<BODY><METER_NO>1031204402</METER_NO><PAGENUM>1</PAGENUM></BODY>"
//							+ "<TAIL><SIGN_TYPE>1</SIGN_TYPE><SIGNATURE>"+sign+"</SIGNATURE></TAIL>"
//							+ "</ROOT>");
			String str = conn(//�շѲ�ѯ
					"PBillQuery",
					"<ROOT>"
					+  "<TOP><IMEI>762845024199122</IMEI><SESSION_ID>tp4szp3LxQYb5FlIHXAU</SESSION_ID><REQUEST_TIME>"+formatDateStr+"</REQUEST_TIME><LOCAL_LANGUAGE>zh</LOCAL_LANGUAGE></TOP>"
					+ "<BODY><USER_NO>17042464888</USER_NO><PAGENUM>1</PAGENUM><NUMPERPAG>3</NUMPERPAG></BODY>"
					+ "<TAIL><SIGN_TYPE>1</SIGN_TYPE><SIGNATURE>"+sign+"</SIGNATURE></TAIL>"
					+ "</ROOT>");
			return str;
	}
	//�۵����Ѽ���
		public static String PBillFeeCount() throws RemoteException {
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String formatDateStr=formatter.format(currentTime);
			 TreeMap<String, String> paramMap = new TreeMap<String, String>();
				paramMap.put("IMEI", "762845024199122");
				paramMap.put("REQUEST_TIME", formatDateStr);
				paramMap.put("SESSION_ID","7jlZXD9gZKnoTBnxNMkQ");
				paramMap.put("LOCAL_LANGUAGE", "zh");
				paramMap.put("SIGN_TYPE", "1");
				paramMap.put("AMT", "100000");
//				paramMap.put("RECE_ID", "3201512041641052|3201512041641053|3201512041641054");
				String signStr = bulidParam(paramMap);//key
				String sign = Md5Algorithm.getInstance().md5Digest(signStr.getBytes());
				System.out.println("SIGN: "+sign);
//				System.out.println(
//						"<ROOT>"
//								+ "<TOP><IMEI>762845024199122</IMEI><SESSION_ID>OSJO0zC6j2Opnmn1A5ab</SESSION_ID><REQUEST_TIME>"+formatDateStr+"</REQUEST_TIME><LOCAL_LANGUAGE>zh</LOCAL_LANGUAGE></TOP>"
//								+ "<BODY><AMT>2000</AMT></BODY>"
//								+ "<TAIL><SIGN_TYPE>1</SIGN_TYPE><SIGNATURE>"+sign+"</SIGNATURE></TAIL>"
//								+ "</ROOT>");
				String str = conn(//�۵����Ѽ���
						"PBillFeeCount",
						"<ROOT>"
						+ "<TOP><IMEI>762845024199122</IMEI><SESSION_ID>7jlZXD9gZKnoTBnxNMkQ</SESSION_ID><REQUEST_TIME>"+formatDateStr+"</REQUEST_TIME><LOCAL_LANGUAGE>zh</LOCAL_LANGUAGE></TOP>"
						+ "<BODY><AMT>100000</AMT></BODY>"
						+ "<TAIL><SIGN_TYPE>1</SIGN_TYPE><SIGNATURE>"+sign+"</SIGNATURE></TAIL>"
						+ "</ROOT>");
				return str;
		}
		//ƾ֤��ѯ
				public static String PBillReprintQuery() throws RemoteException {
					Date currentTime = new Date();
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String formatDateStr=formatter.format(currentTime);
					 TreeMap<String, String> paramMap = new TreeMap<String, String>();
						paramMap.put("IMEI", "762845024199122");
						paramMap.put("REQUEST_TIME", formatDateStr);
						paramMap.put("SESSION_ID","yRVUB1AWihyMISbkRZqR");
						paramMap.put("LOCAL_LANGUAGE", "zh");
						paramMap.put("SIGN_TYPE", "1");
						paramMap.put("METER_NO", "007042464888");
//						paramMap.put("USER_NO", "1031204471");
						paramMap.put("PAGENUM", "1");
						paramMap.put("NUMPERPAG", "3");
						String signStr = bulidParam(paramMap);//key
						String sign = Md5Algorithm.getInstance().md5Digest(signStr.getBytes());
						System.out.println("SIGN: "+sign);
//						System.out.println(
//								"<ROOT>"
//										+ "<TOP><IMEI>762845024199122</IMEI><SESSION_ID>E4ZbMmX7TngsEywlvT3g</SESSION_ID><REQUEST_TIME>"+formatDateStr+"</REQUEST_TIME><LOCAL_LANGUAGE>zh</LOCAL_LANGUAGE></TOP>"
//										+ "<BODY><METER_NO>1031204425</METER_NO><PAGENUM>1</PAGENUM><NUMPERPAG>2</NUMPERPAG></BODY>"
//										+ "<TAIL><SIGN_TYPE>1</SIGN_TYPE><SIGNATURE>"+sign+"</SIGNATURE></TAIL>"
//										+ "</ROOT>");
						String str = conn(//ƾ֤��ѯ��ѯ
								"PBillReprintQuery",
								"<ROOT>"
								+ "<TOP><IMEI>762845024199122</IMEI><SESSION_ID>yRVUB1AWihyMISbkRZqR</SESSION_ID><REQUEST_TIME>"+formatDateStr+"</REQUEST_TIME><LOCAL_LANGUAGE>zh</LOCAL_LANGUAGE></TOP>"
								+ "<BODY><METER_NO>007042464888</METER_NO><PAGENUM>1</PAGENUM><NUMPERPAG>3</NUMPERPAG></BODY>"
								+ "<TAIL><SIGN_TYPE>1</SIGN_TYPE><SIGNATURE>"+sign+"</SIGNATURE></TAIL>"
								+ "</ROOT>");
						return str;
				}
				//ƾ֤����
				public static String PBillReprint() throws RemoteException {
					Date currentTime = new Date();
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String formatDateStr=formatter.format(currentTime);
					 TreeMap<String, String> paramMap = new TreeMap<String, String>();
						paramMap.put("IMEI", "762845024199122");
						paramMap.put("REQUEST_TIME", formatDateStr);
						paramMap.put("SESSION_ID","w6SZgYk2gimFfalgOdeM");
						paramMap.put("LOCAL_LANGUAGE", "zh");
						paramMap.put("SIGN_TYPE", "1");
						paramMap.put("PRDORDNO", "D016031400165223");
						String jsonstring =  
								"{" +  
								    "   \"read\" : {\"offset\" : [0]," +  
								    "   \"value\" : [\"92231091F0AC149817833742010000000010999900000000000000FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF0ABCFA01A71BFFFFFFFFFFFF01009413077786996250FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFC45A68028387ECA7D58488879F1927BEFA26C4563D8D6471FA26C4563D8D64719F7B761B4986EEF6C3D65E1ABC38369B45A0812A6369CD0C50D50B939D3F9F3350D50B939D3F9F3350D50B939D3F9F3350D50B939D3F9F3350D50B939D3F9F33876C405166EE37D627B0DB68F158E4D550D50B939D3F9F3350D50B939D3F9F3350D50B939D3F9F3350D50B939D3F9F3350D50B939D3F9F33876C405166EE37D63A3FA8EF7FB8DE3D50D50B939D3F9F3350D50B939D3F9F3350D50B939D3F9F3350D50B939D3F9F3350D50B939D3F9F33245C364ED9A2864D6DC278808D86EE1F50D50B939D3F9F3350D50B939D3F9F3350D50B939D3F9F3350D50B939D3F9F3350D50B939D3F9F3363A5F1927D4CE3EF0773B5A00E1D185B50D50B939D3F9F3350D50B939D3F9F3350D50B939D3F9F3350D50B939D3F9F3350D50B939D3F9F33F01F52DFAFB0B862A3A3226D1D68C64C50D50B939D3F9F3350D50B939D3F9F3350D50B939D3F9F3350D50B939D3F9F3350D50B939D3F9F3363A5F1927D4CE3EF50D50B939D3F9F3350D50B939D3F9F3350D50B939D3F9F3350D50B939D3F9F33AE8E0C0A2DBE69A26301B219164E6C03AF40B42610B6AB8432644CEBA3447DC450D50B939D3F9F3350D50B939D3F9F3350D50B939D3F9F3350D50B939D3F9F3350D50B939D3F9F3350D50B939D3F9F3350D50B939D3F9F3350D50B939D3F9F3350D50B939D3F9F3350D50B939D3F9F3350D50B939D3F9F3350D50B939D3F9F3354051917121541620211115671766315FAA980FFFFFFFFFF76F8E8BDA7EAEC20FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF0000\"] }"+
								"}";
//						paramMap.put("IC_JSON_REQ", jsonstring);
//						paramMap.put("IC_TYPE", "1");
						String signStr = bulidParam(paramMap);//key
						String sign = Md5Algorithm.getInstance().md5Digest(signStr.getBytes());
						System.out.println("SIGN: "+sign);
//						System.out.println(
//								"<ROOT>"
//										+ "<TOP><IMEI>762845024199122</IMEI><SESSION_ID>E4ZbMmX7TngsEywlvT3g</SESSION_ID><REQUEST_TIME>"+formatDateStr+"</REQUEST_TIME><LOCAL_LANGUAGE>zh</LOCAL_LANGUAGE></TOP>"
//										+ "<BODY><PRDORDNO>D015102800015022</PRDORDNO></BODY>"
//										+ "<TAIL><SIGN_TYPE>1</SIGN_TYPE><SIGNATURE>"+sign+"</SIGNATURE></TAIL>"
//										+ "</ROOT>");
						String str = conn(//ƾ֤����
								"PBillReprint",
								"<ROOT>"
								+ "<TOP><IMEI>762845024199122</IMEI><SESSION_ID>w6SZgYk2gimFfalgOdeM</SESSION_ID><REQUEST_TIME>"+formatDateStr+"</REQUEST_TIME><LOCAL_LANGUAGE>zh</LOCAL_LANGUAGE></TOP>"
//								+ "<BODY><PRDORDNO>D015121700072072</PRDORDNO><IC_JSON_REQ>"+jsonstring+"</IC_JSON_REQ><IC_TYPE>1</IC_TYPE></BODY>"
								+ "<BODY><PRDORDNO>D016031400165223</PRDORDNO></BODY>"
								+ "<TAIL><SIGN_TYPE>1</SIGN_TYPE><SIGNATURE>"+sign+"</SIGNATURE></TAIL>"
								+ "</ROOT>");
						return str;
				}
				
	
	public static String PBillRecoveryQuery() throws RemoteException {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formatDateStr=formatter.format(currentTime);
		 TreeMap<String, String> paramMap = new TreeMap<String, String>();
			paramMap.put("IMEI", "762845024199122");
			paramMap.put("REQUEST_TIME", formatDateStr);
			paramMap.put("SESSION_ID","ZJkywwqQwP4BMYOjkiRe");
			paramMap.put("LOCAL_LANGUAGE", "zh");
			paramMap.put("SIGN_TYPE", "1");
			paramMap.put("USER_NO", "1031204462");
			paramMap.put("PAGENUM", "1");
			paramMap.put("NUMPERPAG", "5");
			String signStr = bulidParam(paramMap);//key
			String sign = Md5Algorithm.getInstance().md5Digest(signStr.getBytes());
			System.out.println("SIGN: "+sign);
//			System.out.println(
//					"<ROOT>"
//							+ "<TOP><IMEI>762845024199122</IMEI><SESSION_ID>vbNKfGVAiKH08WgtcPy9</SESSION_ID><REQUEST_TIME>"+formatDateStr+"</REQUEST_TIME><LOCAL_LANGUAGE>zh</LOCAL_LANGUAGE></TOP>"
//							+ "<BODY><USER_NO>1031204462</USER_NO><PAGENUM>1</PAGENUM><NUMPERPAG>5</NUMPERPAG></BODY>"
//							+ "<TAIL><SIGN_TYPE>1</SIGN_TYPE><SIGNATURE>"+sign+"</SIGNATURE></TAIL>"
//							+ "</ROOT>");
			String str = conn(//�շѼ�¼��ѯ������
					"PBillRecoveryQuery",
					"<ROOT>"
					+ "<TOP><IMEI>762845024199122</IMEI><SESSION_ID>ZJkywwqQwP4BMYOjkiRe</SESSION_ID><REQUEST_TIME>"+formatDateStr+"</REQUEST_TIME><LOCAL_LANGUAGE>zh</LOCAL_LANGUAGE></TOP>"
					+ "<BODY><USER_NO>1031204462</USER_NO><PAGENUM>1</PAGENUM><NUMPERPAG>5</NUMPERPAG></BODY>"
					+ "<TAIL><SIGN_TYPE>1</SIGN_TYPE><SIGNATURE>"+sign+"</SIGNATURE></TAIL>"
					+ "</ROOT>");
			return str;
	}
//	��������
	public static String PBillReversalApp() throws RemoteException {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formatDateStr=formatter.format(currentTime);
		 TreeMap<String, String> paramMap = new TreeMap<String, String>();
			paramMap.put("IMEI", "762845024199122");
			paramMap.put("REQUEST_TIME", formatDateStr);
			paramMap.put("SESSION_ID","bG94ldBVfNoCdeQNo0Pp");
			paramMap.put("LOCAL_LANGUAGE", "zh");
			paramMap.put("SIGN_TYPE", "1");
			paramMap.put("PRDORDNO", "D016012900126373");
			paramMap.put("TYPE", "1");
			paramMap.put("REASON", "d");
			String signStr = bulidParam(paramMap);//key
			String sign = Md5Algorithm.getInstance().md5Digest(signStr.getBytes());
			System.out.println("SIGN: "+sign);
//			System.out.println(
//					"<ROOT>"
//							+ "<TOP><IMEI>762845024199122</IMEI><SESSION_ID>L56HKoya2U2VHsXRDNz2</SESSION_ID><REQUEST_TIME>"+formatDateStr+"</REQUEST_TIME><LOCAL_LANGUAGE>zh</LOCAL_LANGUAGE></TOP>"
//							+ "<BODY><TYPE>1</TYPE><REASON>d</REASON><PRDORDNO>D015103000021498</PRDORDNO></BODY>"
//							+ "<TAIL><SIGN_TYPE>1</SIGN_TYPE><SIGNATURE>"+sign+"</SIGNATURE></TAIL>"
//							+ "</ROOT>");
			String str = conn(//��������
					"PBillReversalApp",
					"<ROOT>"
					+ "<TOP><IMEI>762845024199122</IMEI><SESSION_ID>bG94ldBVfNoCdeQNo0Pp</SESSION_ID><REQUEST_TIME>"+formatDateStr+"</REQUEST_TIME><LOCAL_LANGUAGE>zh</LOCAL_LANGUAGE></TOP>"
					+ "<BODY><TYPE>1</TYPE><REASON>d</REASON><PRDORDNO>D016012900126373</PRDORDNO></BODY>"
					+ "<TAIL><SIGN_TYPE>1</SIGN_TYPE><SIGNATURE>"+sign+"</SIGNATURE></TAIL>"
					+ "</ROOT>");
			return str;
	}
	public static String PBillIncomeQuery() throws RemoteException {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formatDateStr=formatter.format(currentTime);
		 TreeMap<String, String> paramMap = new TreeMap<String, String>();
			paramMap.put("IMEI", "762845024199122");
			paramMap.put("REQUEST_TIME", formatDateStr);
			paramMap.put("SESSION_ID","yJ4Qx59pTxZDyK6w5XIq");
			paramMap.put("LOCAL_LANGUAGE", "zh");
			paramMap.put("SIGN_TYPE", "1");
			paramMap.put("PAGENUM", "1");
			paramMap.put("NUMPERPAG", "10");
			paramMap.put("FROMDATE", "2015-11-01");
//			paramMap.put("TODATE", "2015-11-22");
			String signStr = bulidParam(paramMap);//key
			String sign = Md5Algorithm.getInstance().md5Digest(signStr.getBytes());
			System.out.println("SIGN: "+sign);
//			System.out.println(
//					"<ROOT>"
//							+ "<TOP><IMEI>762845024199122</IMEI><SESSION_ID>wFSuEVBzHKydrhgdRcBf</SESSION_ID><REQUEST_TIME>"+formatDateStr+"</REQUEST_TIME><LOCAL_LANGUAGE>zh</LOCAL_LANGUAGE></TOP>"
//							+ "<BODY><FROMDATE>2015-10-05</FROMDATE><PAGENUM>1</PAGENUM><NUMPERPAG>10</NUMPERPAG></BODY>"
//							+ "<TAIL><SIGN_TYPE>1</SIGN_TYPE><SIGNATURE>"+sign+"</SIGNATURE></TAIL>"
//							+ "</ROOT>");
			String str = conn(//��֧��ϸ
					"PBillIncomeQuery",
					"<ROOT>"
					+ "<TOP><IMEI>762845024199122</IMEI><SESSION_ID>yJ4Qx59pTxZDyK6w5XIq</SESSION_ID><REQUEST_TIME>"+formatDateStr+"</REQUEST_TIME><LOCAL_LANGUAGE>zh</LOCAL_LANGUAGE></TOP>"
					+ "<BODY><PAGENUM>1</PAGENUM><NUMPERPAG>10</NUMPERPAG><FROMDATE>2015-11-01</FROMDATE></BODY>"
					+ "<TAIL><SIGN_TYPE>1</SIGN_TYPE><SIGNATURE>"+sign+"</SIGNATURE></TAIL>"
					+ "</ROOT>");
			return str;
	}
	public static String PBillDailyQuery() throws RemoteException {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formatDateStr=formatter.format(currentTime);
		 TreeMap<String, String> paramMap = new TreeMap<String, String>();
			paramMap.put("IMEI", "762845024199122");
			paramMap.put("REQUEST_TIME", formatDateStr);
			paramMap.put("SESSION_ID","w6SZgYk2gimFfalgOdeM");
			paramMap.put("LOCAL_LANGUAGE", "zh");
			paramMap.put("SIGN_TYPE", "1");
			paramMap.put("OPER_ID", "admin");
			paramMap.put("STATUS", "0");
//			paramMap.put("PAGENUM", "1");
//			paramMap.put("NUMPERPAG", "5");
			String signStr = bulidParam(paramMap);//key
			String sign = Md5Algorithm.getInstance().md5Digest(signStr.getBytes());
//			System.out.println(
//					"<ROOT>"
//							+ "<TOP><IMEI>762845024199122</IMEI><SESSION_ID>5m2C7F5inznUk8tCwuzN</SESSION_ID><REQUEST_TIME>"+formatDateStr+"</REQUEST_TIME><LOCAL_LANGUAGE>zh</LOCAL_LANGUAGE></TOP>"
//							+ "<BODY><OPER_ID>admin</OPER_ID><STATUS>0</STATUS></BODY>"
//							+ "<TAIL><SIGN_TYPE>1</SIGN_TYPE><SIGNATURE>"+sign+"</SIGNATURE></TAIL>"
//							+ "</ROOT>");
			String str = conn(//�ս��ѯ
					"PBillDailyQuery",
					"<ROOT>"
					+ "<TOP><IMEI>762845024199122</IMEI><SESSION_ID>w6SZgYk2gimFfalgOdeM</SESSION_ID><REQUEST_TIME>"+formatDateStr+"</REQUEST_TIME><LOCAL_LANGUAGE>zh</LOCAL_LANGUAGE></TOP>"
					+ "<BODY><OPER_ID>admin</OPER_ID><STATUS>0</STATUS></BODY>"
					+ "<TAIL><SIGN_TYPE>1</SIGN_TYPE><SIGNATURE>"+sign+"</SIGNATURE></TAIL>"
					+ "</ROOT>");
			return str;
	}
	//�սᴦ��
	public static String PBillDailyConfirm() throws RemoteException {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formatDateStr=formatter.format(currentTime);
		 TreeMap<String, String> paramMap = new TreeMap<String, String>();
			paramMap.put("IMEI", "762845024199122");
			paramMap.put("REQUEST_TIME", formatDateStr);
			paramMap.put("SESSION_ID","SKHLCgpEscMxwXLmag6B");
			paramMap.put("LOCAL_LANGUAGE", "zh");
			paramMap.put("SIGN_TYPE", "1");
			paramMap.put("TOF_NO", "T016022300001540");
			paramMap.put("STATUS", "0");
			String signStr = bulidParam(paramMap);//key
			String sign = Md5Algorithm.getInstance().md5Digest(signStr.getBytes());
			System.out.println("SIGN: "+sign);
//			System.out.println(
//					"<ROOT>"
//							+ "<TOP><IMEI>762845024199122</IMEI><SESSION_ID>5m2C7F5inznUk8tCwuzN</SESSION_ID><REQUEST_TIME>"+formatDateStr+"</REQUEST_TIME><LOCAL_LANGUAGE>zh</LOCAL_LANGUAGE></TOP>"
//							+ "<BODY><TOF_NO>T015102900000228</TOF_NO><STATUS>0</STATUS></BODY>"
//							+ "<TAIL><SIGN_TYPE>1</SIGN_TYPE><SIGNATURE>"+sign+"</SIGNATURE></TAIL>"
//							+ "</ROOT>");
			String str = conn(//�սᴦ��
					"PBillDailyConfirm",
					"<ROOT>"
					+ "<TOP><IMEI>762845024199122</IMEI><SESSION_ID>SKHLCgpEscMxwXLmag6B</SESSION_ID><REQUEST_TIME>"+formatDateStr+"</REQUEST_TIME><LOCAL_LANGUAGE>zh</LOCAL_LANGUAGE></TOP>"
					+ "<BODY><TOF_NO>T016022300001540</TOF_NO><STATUS>0</STATUS></BODY>"
					+ "<TAIL><SIGN_TYPE>1</SIGN_TYPE><SIGNATURE>"+sign+"</SIGNATURE></TAIL>"
					+ "</ROOT>");
			return str;
	}
	//�����ս�
		public static String PBillDailyApply() throws RemoteException {
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String formatDateStr=formatter.format(currentTime);
			 TreeMap<String, String> paramMap = new TreeMap<String, String>();
				paramMap.put("IMEI", "762845024199122");
				paramMap.put("REQUEST_TIME", formatDateStr);
				paramMap.put("SESSION_ID","w6SZgYk2gimFfalgOdeM");
				paramMap.put("LOCAL_LANGUAGE", "zh");
				paramMap.put("SIGN_TYPE", "1");
				String signStr = bulidParam(paramMap);//key
				String sign = Md5Algorithm.getInstance().md5Digest(signStr.getBytes());
				System.out.println("SIGN: "+sign);
//				System.out.println(
//						"<ROOT>"
//								+ "<TOP><IMEI>762845024199122</IMEI><SESSION_ID>0eRtuaPY1oG3Wpgc4ECe</SESSION_ID><REQUEST_TIME>"+formatDateStr+"</REQUEST_TIME><LOCAL_LANGUAGE>zh</LOCAL_LANGUAGE></TOP>"
//								+ "<BODY></BODY>"
//								+ "<TAIL><SIGN_TYPE>1</SIGN_TYPE><SIGNATURE>"+sign+"</SIGNATURE></TAIL>"
//								+ "</ROOT>");
				String str = conn(//�����ս�
						"PBillDailyApply",
						"<ROOT>"
						+ "<TOP><IMEI>762845024199122</IMEI><SESSION_ID>w6SZgYk2gimFfalgOdeM</SESSION_ID><REQUEST_TIME>"+formatDateStr+"</REQUEST_TIME><LOCAL_LANGUAGE>zh</LOCAL_LANGUAGE></TOP>"
						+ "<BODY></BODY>"
						+ "<TAIL><SIGN_TYPE>1</SIGN_TYPE><SIGNATURE>"+sign+"</SIGNATURE></TAIL>"
						+ "</ROOT>");
				return str;
		}
		//ϵͳ��Ϣ�����ϴ�
				public static String PSettingUpload() throws RemoteException {
					Date currentTime = new Date();
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String formatDateStr=formatter.format(currentTime);
					 TreeMap<String, String> paramMap = new TreeMap<String, String>();
						paramMap.put("IMEI", "762845024199122");
						paramMap.put("REQUEST_TIME", formatDateStr);
						paramMap.put("SESSION_ID","LnJ6GRR2wQYjHpbfdLIH");
						paramMap.put("LOCAL_LANGUAGE", "en");
						paramMap.put("SIGN_TYPE", "1");
						paramMap.put("DISPLAY", "2");
						paramMap.put("VOICE", "1");
						paramMap.put("LANGUAGE", "1");
						paramMap.put("LOCKTIME", "1");
						String signStr = bulidParam(paramMap);//key
						String sign = Md5Algorithm.getInstance().md5Digest(signStr.getBytes());
						System.out.println("SIGN: "+sign);
//						System.out.println(
//								"<ROOT>"
//										+ "<TOP><IMEI>762845024199122</IMEI><SESSION_ID>uThWwdZ7EL1VZl5hco8b</SESSION_ID><REQUEST_TIME>"+formatDateStr+"</REQUEST_TIME><LOCAL_LANGUAGE>zh</LOCAL_LANGUAGE></TOP>"
//										+ "<BODY><DISPLAY>1</DISPLAY><VOICE>1</VOICE><LANGUAGE>1</LANGUAGE><LOCKTIME>1</LOCKTIME></BODY>"
//										+ "<TAIL><SIGN_TYPE>1</SIGN_TYPE><SIGNATURE>"+sign+"</SIGNATURE></TAIL>"
//										+ "</ROOT>");
						String str = conn(
								"PSettingUpload",
								"<ROOT>"
								+ "<TOP><IMEI>762845024199122</IMEI><SESSION_ID>LnJ6GRR2wQYjHpbfdLIH</SESSION_ID><REQUEST_TIME>"+formatDateStr+"</REQUEST_TIME><LOCAL_LANGUAGE>en</LOCAL_LANGUAGE></TOP>"
								+ "<BODY><DISPLAY>2</DISPLAY><VOICE>1</VOICE><LANGUAGE>1</LANGUAGE><LOCKTIME>1</LOCKTIME></BODY>"
								+ "<TAIL><SIGN_TYPE>1</SIGN_TYPE><SIGNATURE>"+sign+"</SIGNATURE></TAIL>"
								+ "</ROOT>");
						return str;
				}
		
		public static String PBillPay() throws RemoteException {
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String formatDateStr=formatter.format(currentTime);
			TreeMap<String, String> paramMap = new TreeMap<String, String>();
			paramMap.put("IMEI", "762845024199122");
			paramMap.put("REQUEST_TIME",formatDateStr);
			paramMap.put("LOCAL_LANGUAGE","zh");
			paramMap.put("SESSION_ID","ipRYoIgk8X6h1jBjKuBv");
			paramMap.put("SIGN_TYPE", "1");
			
			paramMap.put("RECE_ID", "200000014|200000014");   //�˵��շѡ���Ӧ���˿�ID
			paramMap.put("AMT", "1.000");   //�˵��շѡ����շѽ��
			
			String signStr = bulidParam(paramMap);
			String sign = Md5Algorithm.getInstance().md5Digest(signStr.getBytes());
//			System.out.println(
//					"<ROOT>"   //�˵��շ�
//					+ "<TOP><IMEI>762845024199122</IMEI><SESSION_ID>HXD0XtPpAjOHUO17NByN</SESSION_ID><REQUEST_TIME>"+formatDateStr+"</REQUEST_TIME><LOCAL_LANGUAGE>zh</LOCAL_LANGUAGE></TOP>"
//					+ "<BODY><RECE_ID>32015111218321723|32015111218321724</RECE_ID><AMT>311.020</AMT></BODY>"
//					+ "<TAIL><SIGN_TYPE>1</SIGN_TYPE><SIGNATURE>"+sign+"</SIGNATURE></TAIL>"
//					+ "</ROOT>");
			String str = conn(
					"PBillPay",
					"<ROOT>"   //�˵��շ�
					+ "<TOP><IMEI>762845024199122</IMEI><SESSION_ID>ipRYoIgk8X6h1jBjKuBv</SESSION_ID><REQUEST_TIME>"+formatDateStr+"</REQUEST_TIME><LOCAL_LANGUAGE>zh</LOCAL_LANGUAGE></TOP>"
					+ "<BODY><RECE_ID>200000014|200000014</RECE_ID><AMT>1.000</AMT></BODY>"
					+ "<TAIL><SIGN_TYPE>1</SIGN_TYPE><SIGNATURE>"+sign+"</SIGNATURE></TAIL>"
					+ "</ROOT>");
			return str;
		}

		public static String PBillBuy(String SESSION_ID,String METER_NO) throws RemoteException {
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String formatDateStr=formatter.format(currentTime);
			TreeMap<String, String> paramMap = new TreeMap<String, String>();
			paramMap.put("IMEI", "762845024199122");
			paramMap.put("REQUEST_TIME",formatDateStr);
			paramMap.put("LOCAL_LANGUAGE","zh");
			paramMap.put("SESSION_ID",SESSION_ID);
			paramMap.put("SIGN_TYPE", "1");
//			String jsonstring =  
//					"{" +  
//					    "   \"read\" : {\"offset\" : [0]," +  
//					    "   \"value\" : [\"92231091F0AC141418833742010000000010999900000000000000FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF0ABCFA02A71CFFFFFFFFFFFF4264151607101362651895501610014294646828FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF8C17B2230F3314A7D3FE5FF1C909E76EB34AD15CB7493B01B34AD15CB7493B0124C6AFE91B4F931F79802338EBBC41CD9A5E67C1F433AA1B3FE4AB7E2D9305033FE4AB7E2D9305033FE4AB7E2D9305033FE4AB7E2D9305033FE4AB7E2D9305039BC622D59E4A22BCEA2AD85319B206D33FE4AB7E2D9305033FE4AB7E2D9305033FE4AB7E2D9305033FE4AB7E2D9305033FE4AB7E2D9305037D608297578A4F8F00A291E59744D5A83FE4AB7E2D9305033FE4AB7E2D9305033FE4AB7E2D9305033FE4AB7E2D9305033FE4AB7E2D9305037BD51DD0E5B8122C61F250E9C7A0BA263FE4AB7E2D9305033FE4AB7E2D9305033FE4AB7E2D9305033FE4AB7E2D9305033FE4AB7E2D9305034F638A4E9CC53846B0F6B1B1D83340AD3FE4AB7E2D9305033FE4AB7E2D9305033FE4AB7E2D9305033FE4AB7E2D9305033FE4AB7E2D9305037BD51DD0E5B8122CB34F4A902B311EF19CF60398F3221E863FE4AB7E2D9305033FE4AB7E2D9305033FE4AB7E2D93050358EE3594E5AB4E46A3FD6BB0A9310ED83FE4AB7E2D9305033FE4AB7E2D9305033FE4AB7E2D9305033FE4AB7E2D9305032EE8178F70C95DB514DD5DCE398A6A232A0D5894CAE7EC740C8905C876D5FD973FE4AB7E2D9305033FE4AB7E2D9305033FE4AB7E2D9305033FE4AB7E2D9305033FE4AB7E2D9305033FE4AB7E2D9305033FE4AB7E2D9305033FE4AB7E2D9305033FE4AB7E2D9305033FE4AB7E2D9305033FE4AB7E2D9305033FE4AB7E2D93050305051530111560815174436324939662FAAAC4FFFFFFFFFF66AFAFD187088229FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF0000\"] }"+
//					"}";
			String jsonstring =  
					"{" +  
					    "   \"read\" : {\"offset\" : [0]," +  
					    "   \"value\" : [\"92231091FFFF8113884846427000FFFF02FFFFFFFFD27600000400234567890101026100012345678901FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF\"] }"+
					"}";
			paramMap.put("IC_JSON_REQ", jsonstring);
			paramMap.put("IC_TYPE", "2");
			paramMap.put("AMT", "1220");   //�۵��շѡ������
			paramMap.put("METER_NO", METER_NO);   //�۵��շѡ������
		
			String signStr = bulidParam(paramMap);
			String sign = Md5Algorithm.getInstance().md5Digest(signStr.getBytes());
//			System.out.println(
//					"<ROOT>"   
//							+ "<TOP><IMEI>762845024199122</IMEI><SESSION_ID>z1acEivs0HEITZuAidcr</SESSION_ID><REQUEST_TIME>"+formatDateStr+"</REQUEST_TIME><LOCAL_LANGUAGE>zh</LOCAL_LANGUAGE></TOP>"
//							+ "<BODY><METER_NO>07042464888</METER_NO><AMT>200</AMT></BODY>"
//							+ "<TAIL><SIGN_TYPE>1</SIGN_TYPE><SIGNATURE>"+sign+"</SIGNATURE></TAIL>"
//							+ "</ROOT>");
			String str = conn(
					"PBillBuy",  //�۵��շ�
					"<ROOT>"   
					+ "<TOP><IMEI>762845024199122</IMEI><SESSION_ID>"+SESSION_ID+"</SESSION_ID><REQUEST_TIME>"+formatDateStr+"</REQUEST_TIME><LOCAL_LANGUAGE>zh</LOCAL_LANGUAGE></TOP>"
//					+ "<BODY><METER_NO>704246488813</METER_NO><AMT>50</AMT><IC_TYPE>1</IC_TYPE><IC_JSON_REQ>"+jsonstring+"</IC_JSON_REQ></BODY>"
					+ "<BODY><METER_NO>"+METER_NO+"</METER_NO><AMT>1220</AMT><IC_TYPE>2</IC_TYPE><IC_JSON_REQ>"+jsonstring+"</IC_JSON_REQ></BODY>"
					+ "<TAIL><SIGN_TYPE>1</SIGN_TYPE><SIGNATURE>"+sign+"</SIGNATURE></TAIL>"
					+ "</ROOT>");
			return str;
		}
		public static String ReadCard() throws RemoteException {
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String formatDateStr=formatter.format(currentTime);
			TreeMap<String, String> paramMap = new TreeMap<String, String>();
			paramMap.put("IMEI", "762845024199122");
			paramMap.put("REQUEST_TIME",formatDateStr);
			paramMap.put("LOCAL_LANGUAGE","zh");
			paramMap.put("SESSION_ID","MEQJYL8xY6Ssmc5liPEW");
			paramMap.put("SIGN_TYPE", "1");
			String jsonstring =  
					"{" +  
					    "   \"read\" : {\"offset\" : [0]," +  
					    "   \"value\" : [\"92231091F0AC144400000040010000000010999900000000000000FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF0ABCFF0120A7FFFFFFFFFFFF32345480337314094016FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF33A68D929B6552A8FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF7F0000\"] }"+
					"}";
//			String jsonstring ="D015121800072978"; // "%012345678901234567890123456789ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTU?|;0123456789012345678901234567890123456?|;012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234?";
			paramMap.put("IC_JSON_REQ", jsonstring);
			paramMap.put("IC_TYPE", "1");
		
			String signStr = bulidParam(paramMap);
			String sign = Md5Algorithm.getInstance().md5Digest(signStr.getBytes());
//			System.out.println(
//					"<ROOT>"   
//							+ "<TOP><IMEI>762845024199122</IMEI><SESSION_ID>z1acEivs0HEITZuAidcr</SESSION_ID><REQUEST_TIME>"+formatDateStr+"</REQUEST_TIME><LOCAL_LANGUAGE>zh</LOCAL_LANGUAGE></TOP>"
//							+ "<BODY><METER_NO>07042464888</METER_NO><AMT>200</AMT></BODY>"
//							+ "<TAIL><SIGN_TYPE>1</SIGN_TYPE><SIGNATURE>"+sign+"</SIGNATURE></TAIL>"
//							+ "</ROOT>");
			String str = conn(
					"ReadCard",  
					"<ROOT>"   
					+ "<TOP><IMEI>762845024199122</IMEI><SESSION_ID>MEQJYL8xY6Ssmc5liPEW</SESSION_ID><REQUEST_TIME>"+formatDateStr+"</REQUEST_TIME><LOCAL_LANGUAGE>zh</LOCAL_LANGUAGE></TOP>"
					+ "<BODY><IC_TYPE>1</IC_TYPE><IC_JSON_REQ>"+jsonstring+"</IC_JSON_REQ></BODY>"
					+ "<TAIL><SIGN_TYPE>1</SIGN_TYPE><SIGNATURE>"+sign+"</SIGNATURE></TAIL>"
					+ "</ROOT>");
			return str;
		}
		public static String PCardBanding() throws RemoteException {
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String formatDateStr=formatter.format(currentTime);
			TreeMap<String, String> paramMap = new TreeMap<String, String>();
			paramMap.put("IMEI", "762845024199122");
			paramMap.put("REQUEST_TIME",formatDateStr);
			paramMap.put("LOCAL_LANGUAGE","zh");
			paramMap.put("SESSION_ID","yJ4Qx59pTxZDyK6w5XIq");
			paramMap.put("SIGN_TYPE", "1");
//			String jsonstring =  
//					"{" +  
//					    "   \"read\" : {\"offset\" : [0]," +  
//					    "   \"value\" : [\"0000000000000000000000000000000000000000000000000000000000000000000000000000000;6226220703697267=25122200072327600000?0000000000000000000000000000000000000000;996226220703697267=1561560000000000001003000000040404025120=0000000\"] }"+
//					"}";
			String jsonstring ="HexpayD015121000061210"; // "%012345678901234567890123456789ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTU?|;0123456789012345678901234567890123456?|;012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234?";
			paramMap.put("CARD_NO", jsonstring);
			paramMap.put("CARD_TYPE", "4");
			paramMap.put("PRDORDNO", "D015121500068011");
			
		
			String signStr = bulidParam(paramMap);
			String sign = Md5Algorithm.getInstance().md5Digest(signStr.getBytes());
//			System.out.println(
//					"<ROOT>"   
//							+ "<TOP><IMEI>762845024199122</IMEI><SESSION_ID>z1acEivs0HEITZuAidcr</SESSION_ID><REQUEST_TIME>"+formatDateStr+"</REQUEST_TIME><LOCAL_LANGUAGE>zh</LOCAL_LANGUAGE></TOP>"
//							+ "<BODY><METER_NO>07042464888</METER_NO><AMT>200</AMT></BODY>"
//							+ "<TAIL><SIGN_TYPE>1</SIGN_TYPE><SIGNATURE>"+sign+"</SIGNATURE></TAIL>"
//							+ "</ROOT>");
			String str = conn(
					"PCardBanding",  
					"<ROOT>"   
					+ "<TOP><IMEI>762845024199122</IMEI><SESSION_ID>yJ4Qx59pTxZDyK6w5XIq</SESSION_ID><REQUEST_TIME>"+formatDateStr+"</REQUEST_TIME><LOCAL_LANGUAGE>zh</LOCAL_LANGUAGE></TOP>"
					+ "<BODY><CARD_TYPE>4</CARD_TYPE><CARD_NO>"+jsonstring+"</CARD_NO><PRDORDNO>D015121500068011</PRDORDNO></BODY>"
					+ "<TAIL><SIGN_TYPE>1</SIGN_TYPE><SIGNATURE>"+sign+"</SIGNATURE></TAIL>"
					+ "</ROOT>");
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

	public static String bulidParam(TreeMap paramMap) {
		String str = "";
		Set<Map.Entry<String, String>> set = paramMap.entrySet();
		for (Map.Entry<String, String> entry : set) {
			if(!StringUtils.isEmpty(entry.getValue())){
			str += (entry.getKey().toUpperCase() + "=" + entry.getValue() + "&");
			}
		}
		str += "KEY=WeChatHxingHexpay141805144144144";
//		str += "KEY=qFpmL09zx8n7";
//		str += "KEY=B99120B7D066FEB7B45E8283CF995F0F";//IMkXpbUVhvTI
		System.out.println("Sign str:"+str);
		return str;
	}
	public static void test(){
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
//		System.out.println(df.format(new Date()));// new Date()Ϊ��ȡ��ǰϵͳʱ��
//		System.out.println("Sign str:"+new Date());
		
		TreeMap<String, String> paramMap = new TreeMap<String, String>();
//		paramMap.put("METER_NO", "07042464888");
//		paramMap.put("PAY_AMT","2");
//		paramMap.put("ENEL_ID","Y2015020700043");
//		paramMap.put("LOCAL_LANGUAGE","zh");
//		paramMap.put("SESSION_ID","yJ4Qx59pTxZDyK6w5XIq");
//		paramMap.put("SIGN_TYPE", "1");
//		String jsonstring ="HexpayD015121000061210"; // "%012345678901234567890123456789ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTU?|;0123456789012345678901234567890123456?|;012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234?";
//		paramMap.put("CARD_NO", jsonstring);
//		paramMap.put("CARD_TYPE", "4");
//		paramMap.put("PRDORDNO", "D015121500068011");
		
		
		
		paramMap.put("appid","wx3968a063225dc510");
		paramMap.put("mch_id","1429362702");
		paramMap.put("body","Electricity recharge");
		paramMap.put("nonce_str","EF8J6rkJtNpXKV5p");
		paramMap.put("attach","备用字段");
		paramMap.put("out_trade_no","D017022300007264");
		paramMap.put("total_fee","100");
		paramMap.put("time_start","20170223114503");
		paramMap.put("time_expire","20170223115503");
		paramMap.put("notify_url","127.0.0.1:8888/pay");
		paramMap.put("trade_type","APP");
		paramMap.put("spbill_create_ip","192.168.5.252");
//		paramMap.put("SIGNATURE","79744DE17135E7645F296ECEFA0876F8");
//	System.out.println(paramMap.toString());
	
		String str = "";
		Set<Map.Entry<String, String>> set = paramMap.entrySet();
		for (Map.Entry<String, String> entry : set) {
			if(!StringUtils.isEmpty(entry.getValue())){
			str += (entry.getKey() + "=" + entry.getValue() + "&");
			}
		}
		str += "key=WeChatHxingHexpay141805144144144";
		System.out.println("Sign str:"+str);
	
//		String signStr = bulidParam(paramMap);
		String sign = Md5Algorithm.getInstance().md5Digest(str.getBytes());
		System.out.println(sign);
		
		String tem="appid=wx3968a063225dc510&attach=备用字段&body=Electricity recharge&mch_id=1429362702&nonce_str=8rIltLeDpaongcey&notify_url=127.0.0.1:8888/pay&out_trade_no=D017022300007264&spbill_create_ip=192.168.5.252&time_expire=20170223141354&time_start=20170223140354&total_fee=100&trade_type=APP&key=WeChatHxingHexpay141805144144144";
	System.out.println(Md5Algorithm.getInstance().md5Digest(tem.getBytes())); 
		
		
		
	}
}
