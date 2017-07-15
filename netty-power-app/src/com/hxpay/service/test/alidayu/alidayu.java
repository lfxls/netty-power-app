package com.hxpay.service.test.alidayu;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import java.io.PrintStream;

public class alidayu
{
	private String phoneno = "";
  public void SendMsg()
    throws ApiException
  {
    TaobaoClient client = new DefaultTaobaoClient("https://eco.taobao.com/router/rest", "23869681", "484f43509e24dd308d38e339339dd858");
    AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
    req.setExtend("");
    req.setSmsType("normal");
    req.setSmsFreeSignName("注册验证");
    req.setSmsParamString("{code:'12345',product:'66666'}");
    req.setRecNum("18258200977");
    req.setSmsTemplateCode("SMS_68665065");
    AlibabaAliqinFcSmsNumSendResponse rsp = (AlibabaAliqinFcSmsNumSendResponse)client.execute(req);
    System.out.println(rsp.getBody());
  }

  public String SendAliSMS(String signName, String smsParam, String RecNum, String smsTempCode) throws ApiException
  {
    if (!checkParam(signName, smsParam, RecNum, smsTempCode))
    {
      return "1";
    }

    TaobaoClient client = new DefaultTaobaoClient("https://eco.taobao.com/router/rest", "23869681", "484f43509e24dd308d38e339339dd858");
    AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
    req.setExtend("");
    req.setSmsType("normal");
    req.setSmsFreeSignName(signName);
    req.setSmsParamString(smsParam);
    req.setRecNum(RecNum);
    req.setSmsTemplateCode(smsTempCode);
    AlibabaAliqinFcSmsNumSendResponse rsp = (AlibabaAliqinFcSmsNumSendResponse)client.execute(req);
    return rsp.getBody();
  }

  private boolean checkParam(String signName, String smsParam, String RecNum, String smsTempCode)
  {
    return (signName != null) && (!"".equals(signName)) && 
      (smsParam != null) && (!"".equals(smsParam)) && 
      (RecNum != null) && (!"".equals(RecNum)) && 
      (smsTempCode != null) && (!"".equals(smsTempCode));
  }
}
