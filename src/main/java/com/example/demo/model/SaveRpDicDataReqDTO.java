package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * SaveRpDicDataReqDTO
 *
 * @author wenbiao
 * @date 2022.07.27
 */
@Getter
@Setter
@ToString(callSuper = true)
public class SaveRpDicDataReqDTO extends BaseReqDTO {

    private static final long serialVersionUID = -3121467899510875599L;

    /**
     * 数据key
     */
    private String dataKey;

    /**
     * 数据值
     */
    private String dataValue;

    /**
     * 数据等级
     */
    private String dataLevel;

    /**
     * 分类id
     */
    private String classId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 数据渠道
     */
    private String dataChannel;

}
