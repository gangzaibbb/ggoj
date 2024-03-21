package com.gangzai.ggoj.model.dto.question;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 题目添加参数
 * @TableName question
 */
@Data
public class QuestionAddRequest implements Serializable {

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签列表
     */
    private List<String> tags;

    /**
     * 判断用例
     */
    private List<JudgeCase> judgeCase;

    /**
     * 判断配置
     */
    private JudgeConfig judgeConfig;

    /**
     * 答案
     */
    private String answer;


    private static final long serialVersionUID = 1L;
}