package com.example.demo.common.enums;

import com.alibaba.excel.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DataChannelEnum 数据渠道
 *
 * @author wenbiao
 * @date 2022/7/18/0018
 */
@Getter
@AllArgsConstructor
public enum DataChannelEnum {

    /**
     * 基础中台
     */
    BASIS_PLATFORM("BASIS_PLATFORM", "基础中台"),

    /**
     * 邦甸园
     */
    BDY_PLATFORM("BDY_PLATFORM", "邦甸园"),

    ;

    /**
     * 编码
     */
    private final String code;

    /**
     * 描述
     */
    private final String desc;

    public static boolean isValid(String dataChannel) {
        if (StringUtils.isBlank(dataChannel)) {
            return false;
        }

        for (DataChannelEnum dataChannelEnum : values()) {
            if (dataChannelEnum.getCode().equals(dataChannel)) {
                return true;
            }
        }

        return false;
    }

}
