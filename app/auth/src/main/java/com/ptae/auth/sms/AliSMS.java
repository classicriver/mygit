package com.ptae.auth.sms;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.ptae.auth.model.AppSms;
import com.ptae.auth.service.SMSService;

@Component
public class AliSMS {
	
	@Autowired
	private SMSService service;
	
	 //产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

    // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    static final String accessKeyId = "LTAIa01ZTnQX5Ykz";
    static final String accessKeySecret = "eplGve4iC4jsGwFRfCV4tL2iq6tBto";

    public boolean sent(String phoneNumber,String message) throws ClientException {
    	SendSmsResponse sendSmsResponse = null;
    	if(service.canSent()){
    		 //可自助调整超时时间
            System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
            System.setProperty("sun.net.client.defaultReadTimeout", "10000");

            //初始化acsClient,暂不支持region化
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);

            //组装请求对象-具体描述见控制台-文档部分内容
            SendSmsRequest request = new SendSmsRequest();
            //必填:待发送手机号
            request.setPhoneNumbers(phoneNumber);
            //必填:短信签名-可在短信控制台中找到
            request.setSignName("智车驾驶");
            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode("SMS_103575131");
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            request.setTemplateParam("{\"code\":\""+message+"\"}");

            //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
            //request.setSmsUpExtendCode("90997");

            //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
            //request.setOutId("yourOutId");

            //hint 此处可能会抛出异常，注意catch
            sendSmsResponse = acsClient.getAcsResponse(request);
            if("OK".equals(sendSmsResponse.getCode())){
            	AppSms record = new AppSms();
                record.setMessages(message);
                record.setSentTime(new Date());
                record.setType("0");
                record.setUserAccount(phoneNumber);
                service.insert(record);
                return true;
            };
    	}
       
        return false;
    }
}
