package com.gangzai.ggojbackendmodel.dto.questionsubmit;

import com.gangzai.ggojbackendcommon.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 题目提交
 * @TableName question_submit
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionSubmitQueryRequest extends PageRequest implements Serializable {

    /**
     * 提交用户id
     */
    private Long userId;

    /**
     * 题目id
     */
    private Long questionId;

    /**
     * 编程语言
     */
    private String language;

    /**
     * 判题状态
     */
    private Integer status;

    private static final long serialVersionUID = 1L;
}