package com.gangzai.ggojbackendjudgeservice.judge.strategy;

import com.gangzai.ggojbackendmodel.codesandbox.JudgeInfo;
import com.gangzai.ggojbackendmodel.dto.question.JudgeCase;
import com.gangzai.ggojbackendmodel.entity.Question;
import com.gangzai.ggojbackendmodel.entity.QuestionSubmit;
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
