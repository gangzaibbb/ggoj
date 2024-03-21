package com.gangzai.ggoj.model.dto.question;

import lombok.Data;

/**
 * @author gangzai
 * @date 2024/3/4
 */
@Data
public class JudgeConfig {
    /**
     * 时间限制（ms）
     */
    private Long timeLimit;

    /**
     * 内存限制（KB）
     */
    private Long memoryLimit;

    /**
     * 堆栈限制（KB）
     */
    private Long stackLimit;
}
