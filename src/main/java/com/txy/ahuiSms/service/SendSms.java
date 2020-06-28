package com.txy.ahuiSms.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

public interface SendSms {
    public boolean send(String phoneNumber, String templateCode, HashMap<String,Object> code);
}
