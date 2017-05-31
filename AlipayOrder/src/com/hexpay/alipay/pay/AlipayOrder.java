package com.hexpay.alipay.pay;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import javax.inject.Named;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;

import tangdi.engine.context.Etf;
import tangdi.engine.context.Log;

public class AlipayOrder {
	
	public static void main(String[] args){
		String ORIGNSTR = "discount=0.00&payment_type=1&subject=Electricity+recharge&trade_no=2017030121001004690250159078&buyer_email=1748810515%40qq.com&gmt_create=2017-03-01+11%3A56%3A01&notify_type=trade_status_sync&quantity=1&out_trade_no=D017030100010250&seller_id=2088421971326315&notify_time=2017-03-01+12%3A02%3A43&body=Electricity+recharge&trade_status=TRADE_SUCCESS&is_total_fee_adjust=N&total_fee=0.10&gmt_payment=2017-03-01+11%3A56%3A03&seller_email=finance_3%40hxgroup.co&price=0.10&buyer_id=2088022210539691&notify_id=976dc66bb66f986d697c8b74ef916c6lbq&use_coupon=N&sign_type=RSA&sign=QYGlZtcQvH7EcTXUpPx9FpRJwaDs%2FQCMexfXUkXN8ruRRuvP2FEbev6JUKozAmZx6ZZ4ULpbY5rHmvbRxSZXubQ3%2FU7%2BeBOBLrHKVgF18NjWIRM6kk4uNbVYgKOA1yTrDPvAnjAO0x3D66f5Rf2YuKAGgposeCzPvEEHVJr2B2s%3D";
		String PUBLICKEY =  "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
		checkAlipaySign(ORIGNSTR,PUBLICKEY);
		
	}
	
	@Named("CheckAlipaySign")
	public static int checkAlipaySign(@Named("ORIGNSTR") String ORIGNSTR,@Named("PUBLICKEY") String PUBLICKEY){
		//获取支付宝POST过来反馈信息
		if(ORIGNSTR == null || ORIGNSTR == ""){
			return 1;
		}
		try {
			Map<String,String> params = new HashMap<String,String>();
			Map requestParams = getParameterMap(ORIGNSTR);
//			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
//			    String name = (String) iter.next();
//			    String values =  (String) requestParams.get(name);
//			    String valueStr = "";
//			    for (int i = 0; i < values.length; i++) {
//			        valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
//			  }
			  //乱码解决，这段代码在出现乱码时使用。
			  //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
//			  params.put(name, values);
//			 }
			//切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
			//boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
			System.out.println("参数"+requestParams);
//			Log.info(requestParams.toString());
			boolean flag = AlipaySignature.rsaCheckV1(requestParams, PUBLICKEY, "utf-8");
			System.out.println(flag);
//			Log.info("校验结果:"+flag);
			if(flag){
				return 0;
			}
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 2;
		}
		
		return 3;
	}
	public static Map<String, String> getParameterMap(String reqContent){
		Map<String,String> map = new HashMap<String,String>();
		if(reqContent == null || reqContent == ""){
			return null;
		}
//		包含多个参数
		if(reqContent.contains("&")){
			String[] arr = reqContent.split("&");
			String string = "";
			for(int i=0;i<arr.length;i++){
				string = arr[i];
				if(string.contains("=")){
					String key = URLDecoder.decode(string.substring(0, string.indexOf("=")));
					String value = URLDecoder.decode(string.substring(string.indexOf("=")+1));
					map.put(key, value);
				}else{
					map.clear();
					break;
				}
			}
		}else{
			if(reqContent.contains("=")){
				String key = URLDecoder.decode(reqContent.substring(0, reqContent.indexOf("=")));
				String value = URLDecoder.decode(reqContent.substring(reqContent.indexOf("=")+1));
				map.put(key, value);
			}else{
				map.clear();
			}
		}
		return map;
	}
	
	@Named("GetAlipayOrder")
	public static int getAlipayOrder(@Named("PARTNER") String PARTNER,@Named("SELLER") String SELLER
			,@Named("RSA_PRIVATE") String RSA_PRIVATE,@Named("NOTIFY_URL") String NOTIFY_URL,@Named("SERVICE") String SERVICE
			,@Named("PAYMENT_TYPE") String PAYMENT_TYPE,@Named("_INPUT_CHARSET") String _INPUT_CHARSET,@Named("IT_B_PAY") String IT_B_PAY
			,@Named("RETURN_URL") String RETURN_URL,@Named("SUBJECT") String SUBJECT,@Named("BODY") String BODY
			,@Named("TXAMT") String TXAMT,@Named("OUT_TRADE_NO") String OUT_TRADE_NO) {
		
		if(!ISNullSta(_INPUT_CHARSET)){
			_INPUT_CHARSET = "utf-8";
		}
		if(!(ISNullSta(PARTNER) && ISNullSta(SELLER) && ISNullSta(RSA_PRIVATE) && ISNullSta(NOTIFY_URL) && ISNullSta(SERVICE) && 
				ISNullSta(PAYMENT_TYPE) && ISNullSta(_INPUT_CHARSET) && ISNullSta(IT_B_PAY) && ISNullSta(SUBJECT) && 
				ISNullSta(BODY) && ISNullSta(TXAMT))){
			return 2;//参数问题
		}
		
		// 签约合作者身份ID
				String orderInfo = "partner=" + "\"" + PARTNER + "\"";

				// 签约卖家支付宝账号
				orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

				if(OUT_TRADE_NO == null || OUT_TRADE_NO == ""){
					OUT_TRADE_NO = getOutTradeNo();
				}
				// 商户网站唯一订单号
				orderInfo += "&out_trade_no=" + "\"" + OUT_TRADE_NO + "\"";

				// 商品名称
				orderInfo += "&subject=" + "\"" + SUBJECT + "\"";

				// 商品详情
				orderInfo += "&body=" + "\"" + BODY + "\"";

				// 商品金额
				orderInfo += "&total_fee=" + "\"" + TXAMT + "\"";

				// 服务器异步通知页面路径
				orderInfo += "&notify_url=" + "\"" + NOTIFY_URL + "\"";

				// 服务接口名称， 固定值
				orderInfo += "&service=\""+SERVICE+"\"";

				// 支付类型， 固定值
				orderInfo += "&payment_type=\""+PAYMENT_TYPE+"\"";

				// 参数编码， 固定值
				orderInfo += "&_input_charset=\""+_INPUT_CHARSET+"\"";

				// 设置未付款交易的超时时间
				// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
				// 取值范围：1m～15d。
				// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
				// 该参数数值不接受小数点，如1.5h，可转换为90m。
				orderInfo += "&it_b_pay=\""+IT_B_PAY+"\"";

				// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
				// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

				// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
				orderInfo += "&return_url=\""+RETURN_URL+"\"";
		
				String sign = sign(orderInfo,RSA_PRIVATE);
				try {
					/**
					 * 仅需对sign 做URL编码
					 */
					sign = URLEncoder.encode(sign, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					return 1;
				}
				/**
				 * 完整的符合支付宝参数规范的订单信息
				 */
				final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();
				Etf.setChildValue("PAYINFO", payInfo);
				return 0;
		
	}
	
	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	private static String sign(String content,String RSA_PRIVATE) {
		return SignUtils.sign(content, RSA_PRIVATE);
	}
	
	/**
	 * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
	 * 
	 */
	private static String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
	}
	
	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	private static String getSignType() {
		return "sign_type=\"RSA\"";
	}
	
	private static boolean ISNullSta(String s){
		   if(s == null || s.length() <= 0){
			   return false;
		   }
		   return true;
	}
}
