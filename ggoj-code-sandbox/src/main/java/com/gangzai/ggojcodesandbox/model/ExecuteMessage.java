package com.gangzai.ggojcodesandbox.model;

import lombok.Data;

/**
 * @author gangzai
 * @date 2024/3/13
 * 程序执行信息
 */

@Data
public class ExecuteMessage {
    /**
     * 程序运行状态码 0-正常 1-错误
     */
    private Integer exitValue;
    /**
     * 输出结果
     */
    private String message;
    /**
     * 错误信息
     */
    private String errorMessage;
    /**
     * 程序运行时间
     */
    private Long time;
    /**
     * 运行内存
     */
    private Long memory;
}
