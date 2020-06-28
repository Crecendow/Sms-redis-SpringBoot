package com.txy.ahuiSms.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.txy.ahuiSms.service.SendSms;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SendSmsImpl implements SendSms {

    @Override
    public boolean send(String phoneNumber, String templateCode, HashMap<String, Object> code) {
        // 连接阿里云
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4Fy39w2k1Da2atVpew3z", "MYTMd0rPLVy3OsJsfo5FmvAMpQrGGt");
        IAcsClient client = new DefaultAcsClient(profile);

        //构建请求
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com"); //不要动
        request.setSysVersion("2017-05-25"); //不要动
        request.setSysAction("SendSms");

        //自定义的参数（手机号、验证码、签名、模板）
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", "阿辉说");
        request.putQueryParameter("TemplateCode", templateCode);

        //构建一个短信的验证码
        String codeStr = JSONObject.toJSONString(code);
        request.putQueryParameter("TemplateParam", codeStr);


        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response.getHttpResponse().isSuccess();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }

        return false;
    }
}
