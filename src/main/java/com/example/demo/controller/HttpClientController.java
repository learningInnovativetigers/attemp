package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.model.HttpResult;
import com.example.demo.service.HttpAPIService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * HttpClientController
 *
 * @author wenbiao
 * @date 2022.08.05 上午 11:46
 */
@RestController
public class HttpClientController {

    @Resource
    private HttpAPIService httpAPIService;

    @RequestMapping("httpclient")
    public String test() throws Exception {
        String str = httpAPIService.doGet("http://www.baidu.com");
        System.out.println(str);
        return "hello";
    }

    @RequestMapping("/queryRpDicDataDetail")
    public JSONObject testQueryRpDicDataDetail()  throws Exception{
        Map<String, Object> params = new HashMap<>();
        params.put("classKey", "HIGH_WARNING_DRUG");
        params.put("dataChannel", "BASIS_PLATFORM");
        params.put("reqSystem", "demo");
        params.put("traceId", "0000");
        HttpResult httpResult= httpAPIService.doPost("https://boss.dev.fosun-creative.com/be/api/bosscloud-fosunhealth-rp-dic/rp/dic/queryRpDicDataDetail", params);
        System.out.println(httpResult);
        return JSONObject.parseObject(httpResult.getBody());
    }


}
