package com.example.demo.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.model.HttpResult;
import com.example.demo.model.SaveRpDicDataDescReqDTO;
import com.example.demo.model.SaveRpDicDataReqDTO;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * HttpAPIService
 *
 * @author wenbiao
 * @date 2022.08.05 上午 11:48
 */
@Service
public class HttpAPIService {

    @Autowired
    private CloseableHttpClient httpClient;

    @Autowired
    private RequestConfig config;

    // TODO: 2022.08.08 不同环境对应不同地址
    // private String host = "https://boss.test.fosun-creative.com";

    public JSONObject dealDicData() {

        return null;
    }

    public String saveRpDicData(SaveRpDicDataReqDTO saveRpDicDataReqDTO, String env) throws Exception {
        String url = getHostByEnv(env) + "/be/api/bosscloud-fosunhealth-rp-dic/rp/dic/saveRpDicData";
        HttpResult httpResult = doPost(url, JSONObject.parseObject(JSON.toJSONString(saveRpDicDataReqDTO), Map.class));
        JSONObject jsonObject = JSONObject.parseObject(httpResult.getBody());
        if (null == jsonObject) {
            throw new Exception();
        }
        JSONObject result = (JSONObject)jsonObject.get("result");
        return (String) result.get("dataId");
    }

    public String saveRpDicDataDesc(SaveRpDicDataDescReqDTO saveRpDicDataDescReqDTO, String env) throws Exception {
        String url = getHostByEnv(env) + "/be/api/bosscloud-fosunhealth-rp-dic/rp/dic/saveRpDicDataDesc";
        HttpResult httpResult = doPost(url, JSONObject.parseObject(JSON.toJSONString(saveRpDicDataDescReqDTO), Map.class));
        JSONObject jsonObject = JSONObject.parseObject(httpResult.getBody());
        if (null == jsonObject) {
            throw new Exception();
        }

        JSONObject result = (JSONObject)jsonObject.get("result");
        return (String) result.get("dataDescId");
    }

    public String getHostByEnv(String env) {
        switch (env) {
            case "dev":
                return "https://boss.dev.fosun-creative.com";
            case "test":
                return "https://boss.test.fosun-creative.com";
            case "pro":
                return "https://boss.fosun-creative.com";
            default:
                return "https://boss.dev.fosun-creative.com";
        }
    }



    /**
     * 不带参数的get请求，如果状态码为200，则返回body，如果不为200，则返回null
     *
     * @param url
     * @return
     * @throws Exception
     */
    public String doGet(String url) throws Exception {
        // 声明 http get 请求
        HttpGet httpGet = new HttpGet(url);

        // 装载配置信息
        httpGet.setConfig(config);

        // 发起请求
        CloseableHttpResponse response = this.httpClient.execute(httpGet);

        // 判断状态码是否为200
        if (response.getStatusLine().getStatusCode() == 200) {
            // 返回响应体的内容
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        }
        return null;
    }

    /**
     * 带参数的get请求，如果状态码为200，则返回body，如果不为200，则返回null
     *
     * @param url
     * @return
     * @throws Exception
     */
    public String doGet(String url, Map<String, Object> map) throws Exception {
        URIBuilder uriBuilder = new URIBuilder(url);

        if (map != null) {
            // 遍历map,拼接请求参数
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
            }
        }

        // 调用不带参数的get请求
        return this.doGet(uriBuilder.build().toString());

    }

    /**
     * 带参数的post请求
     *
     * @param url
     * @param map
     * @return
     * @throws Exception
     */
    public HttpResult doPost(String url, Map map) throws Exception {
        // 声明httpPost请求
        HttpPost httpPost = new HttpPost(url);
        // 加入配置信息
        httpPost.setConfig(config);

        // 方式一：判断map是否为空，不为空则进行遍历，封装from表单对象
        /*if (map != null) {
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                list.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }
            // 构造from表单对象
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list, "UTF-8");

            // 把表单放到post里
            httpPost.setEntity(urlEncodedFormEntity);
        }*/

        // 方式二：json格式请求参数
        StringEntity entity = new StringEntity(JSONObject.toJSONString(map), "UTF-8");
        httpPost.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        httpPost.setEntity(entity);

        // 发起请求
        CloseableHttpResponse response = this.httpClient.execute(httpPost);
        return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(
                response.getEntity(), "UTF-8"));
    }

    /**
     * 不带参数post请求
     *
     * @param url
     * @return
     * @throws Exception
     */
    public HttpResult doPost(String url) throws Exception {
        return this.doPost(url, null);
    }


}
