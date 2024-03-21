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
 * 题目编辑参数（管理员）
 * @TableName question
 */
@Data
public class QuestionUpdateRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签列表（json 数组）
     */
    private List<String> tags;

    /**
     * 判断用例（json数组：输入用例，输出用例）
     */
    private List<JudgeCase> judgeCase;

    /**
     * 判断配置（json数组：时间限制，内存限制）
     */
    private JudgeConfig judgeConfig;

    /**
     * 答案
     */
    private String answer;

    private static final long serialVersionUID = 1L;
}