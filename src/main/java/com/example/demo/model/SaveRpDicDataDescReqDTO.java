package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * SaveRpDicDataDescReqDTO
 *
 * @author wenbiao
 * @date 2022.07.27
 */
@Getter
@Setter
@ToString(callSuper = true)
public class SaveRpDicDataDescReqDTO extends BaseReqDTO{

    private static final long serialVersionUID = -3756451655842091946L;

    /**
     * 数据描述
     */
    private String dataDesc;

    /**
     * 数据id
     */
    private String dataId;

    /**
     * 数据描述优先级
     */
    private Integer dataDescPriority;

}
