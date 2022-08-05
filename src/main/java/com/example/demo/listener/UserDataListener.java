package com.example.demo.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * UserDataListener 创建一个监听器，继承自AnalysisEventListener
 *
 * @author wenbiao
 * @date 2022.08.05 下午 4:33
 */
public class UserDataListener extends AnalysisEventListener<Map<Integer, String>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDataListener.class);

    /**
     * 表头数据
     */
    private List<Map<Integer, String>> headList = new ArrayList<>();

    /**
     * 数据体
     */
    private List<Map<Integer, String>> dataList = new ArrayList<>();

    /**
     * 这里会一行行的返回头
     *
     * @param headMap
     * @param context
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        LOGGER.info("解析到一条头数据:{}", JSON.toJSONString(headMap));
        headList.add(headMap);
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data
     *            one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        LOGGER.info("解析到一条数据:{}", JSON.toJSONString(data));
        dataList.add(data);
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        LOGGER.info("所有数据解析完成！");
    }


    public List<Map<Integer, String>> getHeadList() {
        return headList;
    }

    public List<Map<Integer, String>> getDataList() {
        return dataList;
    }

}
