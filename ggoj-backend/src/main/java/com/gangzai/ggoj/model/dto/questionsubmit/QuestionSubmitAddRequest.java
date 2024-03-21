package com.gangzai.ggoj.model.dto.questionsubmit;

import lombok.Data;

import java.io.Serializable;


/**
 * 题目提交
 * @TableName question_submit
 */
@Data
public class QuestionSubmitAddRequest implements Serializable {

    /**
     * 题目id
     */
    private Long questionId;

    /**
     * 编程语言
     */
    private String language;

    /**
     * 代码
     */
    private String code;

    private static final long serialVersionUID = 1L;
}