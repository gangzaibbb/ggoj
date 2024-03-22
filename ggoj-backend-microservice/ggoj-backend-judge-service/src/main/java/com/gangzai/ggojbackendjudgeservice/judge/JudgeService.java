package com.gangzai.ggojbackendjudgeservice.judge;


import com.gangzai.ggojbackendmodel.entity.QuestionSubmit;

/**
 * @author gangzai
 * @date 2024/3/9
 * 判题模块实现接口
 */
public interface JudgeService {
    QuestionSubmit doJudge(long questionSubmitId);
}
