package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * BaseReq
 *
 * @author wenbiao
 * @date 2022/4/26/0026
 */
@Getter
@Setter
@ToString
public class BaseReqDTO implements Serializable {

    private static final long serialVersionUID = 2992078910910217733L;


    /**
     * 请求方系统名
     */
    private String reqSystem;

    /**
     * 日志id
     */
    private String traceId;
}
