package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * HttpResult
 *
 * @author wenbiao
 * @date 2022.08.05 上午 11:52
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class HttpResult {

// 响应的状态码

    private int code;

// 响应的响应体

    private String body;


}
