package com.gangzai.ggojcodesandbox.model;

import lombok.Data;

/**
 * @author gangzai
 * @date 2024/3/13
 * 进程执行信息
 */

@Data
public class ExecuteMessage {

    private Integer exitValue;

    private String message;

    private String errorMessage;

    private Long time;

    private Long memory;
}
