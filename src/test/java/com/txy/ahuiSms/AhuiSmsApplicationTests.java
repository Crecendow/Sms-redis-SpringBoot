package com.txy.ahuiSms;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import java.util.HashMap;

@SpringBootTest
class AhuiSmsApplicationTests {


    //测试阿里云的短信发送业务
    @Test
    void contextLoads() throws Exception {
        // 连接阿里云
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "", "");
        IAcsClient client = new DefaultAcsClient(profile);

        //构建请求
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com"); //不要动
        request.setSysVersion("2017-05-25"); //不要动
        request.setSysAction("SendSms");

        //自定义的参数（手机号、验证码、签名、模板）
        request.putQueryParameter("PhoneNumbers", "15150681055");
        request.putQueryParameter("SignName", "阿辉说");
        request.putQueryParameter("TemplateCode", "SMS_194060228");

        //构建一个短信的验证码
        HashMap<String,Object> map = new HashMap<>();
        map.put("code", 2323);
        String code = JSONObject.toJSONString(map);
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(map));


        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }

    }

}
