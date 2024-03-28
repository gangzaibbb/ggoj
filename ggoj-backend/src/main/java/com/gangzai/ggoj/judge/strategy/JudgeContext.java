package com.gangzai.ggoj.judge.strategy;

import com.gangzai.ggoj.judge.codesandbox.model.JudgeInfo;
import com.gangzai.ggoj.model.dto.question.JudgeCase;
import com.gangzai.ggoj.model.entity.Question;
import com.gangzai.ggoj.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * @author gangzai
 * @date 2024/3/9
 * 判题上下文（管理者调用不同判题策略时传入的参数）
 */
@Data
public class JudgeContext {

    /**
     * 程序运行信息
     */
    private JudgeInfo judgeInfo;

    /**
     * 代码沙箱执行信息
     */
    private String message;

    /**
     * 输入用例
     */
    private List<String> inputList;

    /**
     * 程序运行输出
     */
    private List<String> outputList;

    /**
     * 判题用例
     */
    private List<JudgeCase> judgeCaseList;

    /**
     * 问题实体
     */
    private Question question;

    /**
     * 题目提交
     */
    private QuestionSubmit questionSubmit;
}
