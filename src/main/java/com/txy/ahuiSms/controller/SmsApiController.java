package com.txy.ahuiSms.controller;

import com.txy.ahuiSms.service.SendSms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin  //跨域的支持
public class SmsApiController {

    @Autowired
    private SendSms sendSms;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @GetMapping("/send/{phone}")
    public String code(@PathVariable("phone")String phone){
        //调用方法发送（模拟真实业务）
        String code= redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)){
            return phone + ":" + code + "已存在,还没有过期";
        }

        //生成验证码并存储到redis中
        code = UUID.randomUUID().toString().substring(0, 4);
        HashMap<String, Object> param = new HashMap<>();
        param.put("code", code);

        boolean isSend = sendSms.send(phone, "SMS_194060228", param);
        if(isSend){
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            return phone + ":" + code + "发送成功";
        }else{
            return "发送失败";
        }

    }

}
