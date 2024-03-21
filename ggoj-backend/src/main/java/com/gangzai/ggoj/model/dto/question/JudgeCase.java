package com.gangzai.ggoj.model.dto.question;

import lombok.Data;

/**
 * @author gangzai
 * @date 2024/3/4
 */
@Data
public class JudgeCase {
    /**
     * 输入用例
     */
    private String input;

    /**
     * 输出用例
     */
    private String output;
}
