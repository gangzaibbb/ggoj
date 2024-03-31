package com.gangzai.ggoj.model.dto.questionsubmit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


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

    /**
     * 输入用例
     */
    private List<String> inputList;

    private static final long serialVersionUID = 1L;
}