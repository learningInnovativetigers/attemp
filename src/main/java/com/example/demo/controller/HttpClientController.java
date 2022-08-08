package com.example.demo.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.listener.UserDataListener;
import com.example.demo.model.HttpResult;
import com.example.demo.model.SaveRpDicDataDTO;
import com.example.demo.model.SaveRpDicDataDescReqDTO;
import com.example.demo.model.SaveRpDicDataReqDTO;
import com.example.demo.service.HttpAPIService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    public JSONObject testQueryRpDicDataDetail() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("classKey", "HIGH_WARNING_DRUG");
        params.put("dataChannel", "BASIS_PLATFORM");
        params.put("reqSystem", "demo");
        params.put("traceId", "0000");
        HttpResult httpResult = httpAPIService.doPost("https://boss.dev.fosun-creative.com/be/api/bosscloud-fosunhealth-rp-dic/rp/dic/queryRpDicDataDetail", params);
        System.out.println(httpResult);
        return JSONObject.parseObject(httpResult.getBody());
    }

    public JSONObject testDealDicData() {
        return httpAPIService.dealDicData();
    }

    @RequestMapping("/saveRpDicData")
    public void testSaveRpDicData(@RequestBody SaveRpDicDataDTO saveRpDicDataDTO) throws Exception {
        if (null == saveRpDicDataDTO || StringUtils.isBlank(saveRpDicDataDTO.getClassId()) || StringUtils.isBlank(saveRpDicDataDTO.getDataChannel())
        || StringUtils.isBlank(saveRpDicDataDTO.getEnv())) {
            throw new Exception();
        }
        String filePath = "C:/Users/wenbiao/Desktop/highDrugDesc.xlsx";
        UserDataListener userDataListener = new UserDataListener();
        EasyExcel.read(filePath, userDataListener).sheet().doRead();
        System.out.println("表头：" + JSONArray.toJSONString(userDataListener.getHeadList()));
        System.out.println("数据体：" + JSONArray.toJSONString(userDataListener.getDataList()));
        List<Map<Integer, String>> dataList = userDataListener.getDataList();

        for (Map<Integer, String> integerStringMap : dataList) {
            String dataValue = integerStringMap.get(0);
            String dataKey = integerStringMap.get(1);
            String[] keys = dataKey.split("\\n");
            String dataLevel = integerStringMap.get(2);
            for (String key : keys) {
                SaveRpDicDataReqDTO saveRpDicDataReqDTO = new SaveRpDicDataReqDTO();
                saveRpDicDataReqDTO.setDataKey(key);
                saveRpDicDataReqDTO.setDataValue(dataValue);
                saveRpDicDataReqDTO.setDataLevel(dataLevel);
                saveRpDicDataReqDTO.setClassId(saveRpDicDataDTO.getClassId()); // TODO: 2022.08.08  直接按各环境的classId来
                saveRpDicDataReqDTO.setDataChannel(saveRpDicDataDTO.getDataChannel()); // TODO: 2022.08.08 需要两个都要加  BDY_PLATFORM BASIS_PLATFORM
                saveRpDicDataReqDTO.setReqSystem("program");
                saveRpDicDataReqDTO.setTraceId(UUID.randomUUID().toString());
                System.out.println(JSONObject.toJSONString(saveRpDicDataReqDTO));
                String dataId = httpAPIService.saveRpDicData(saveRpDicDataReqDTO, saveRpDicDataDTO.getEnv());
                System.out.println("dataId: " + dataId);

                if (StringUtils.isBlank(dataId)) {
                    throw new Exception();
                }
                String descListStr = integerStringMap.get(3);
                String[] descList = descListStr.split("\\n");
                for (String desc : descList) {
                    String dataDesc = desc.substring(2);
                    System.out.println(dataDesc);
                    String dataDescPriority = desc.substring(0, 1);
                    System.out.println(dataDescPriority);
                    SaveRpDicDataDescReqDTO saveRpDicDataDescReqDTO = new SaveRpDicDataDescReqDTO();
                    saveRpDicDataDescReqDTO.setDataDesc(dataDesc);
                    saveRpDicDataDescReqDTO.setDataId(dataId);
                    saveRpDicDataDescReqDTO.setDataDescPriority(Integer.parseInt(dataDescPriority) - 1);
                    saveRpDicDataDescReqDTO.setReqSystem("program");
                    saveRpDicDataDescReqDTO.setTraceId(UUID.randomUUID().toString());
                    String dataDescId = httpAPIService.saveRpDicDataDesc(saveRpDicDataDescReqDTO, saveRpDicDataDTO.getEnv());
                    System.out.println("dataDescId = " + dataDescId);
                }
            }
        }
    }

}
