package com.gangzai.ggojcodesandbox.model;

import lombok.Data;

/**
 * @author gangzai
 * @date 2024/3/4
 * 判题信息
 */
@Data
public class JudgeInfo {
    /**
     * 程序执行信息
     */
    private String message;

    /**
     * 消耗内存
     */
    private Long memory;

    /**
     * 消耗时间（KB）
     */
    private Long time;
}
