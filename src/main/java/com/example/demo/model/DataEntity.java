package com.example.demo.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DataEntity
 *
 * @author wenbiao
 * @date 2022.08.08 下午 2:53
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataEntity {

    @ExcelProperty(value = "药品id")
    private String dataKey;

    @ExcelProperty(value = "药品通用名")
    private String dataValue;

    @ExcelProperty(value = "药品等级")
    private String dataLevel;

    @ExcelProperty(value = "高警示提示")
    private String desc;

}
