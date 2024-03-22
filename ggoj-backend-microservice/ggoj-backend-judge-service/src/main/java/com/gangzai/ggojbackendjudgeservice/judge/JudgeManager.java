package com.gangzai.ggojbackendjudgeservice.judge;

import com.gangzai.ggojbackendjudgeservice.judge.strategy.DefaultJudgeStrategy;
import com.gangzai.ggojbackendjudgeservice.judge.strategy.JavaLanguageJudgeStrategy;
import com.gangzai.ggojbackendjudgeservice.judge.strategy.JudgeContext;
import com.gangzai.ggojbackendjudgeservice.judge.strategy.JudgeStrategy;
import com.gangzai.ggojbackendmodel.codesandbox.JudgeInfo;
import org.springframework.stereotype.Component;

/**
 * @author gangzai
 * @date 2024/3/9
 * 代替service判断使用哪种判题逻辑
 */
@Component
public class JudgeManager {
    public JudgeInfo doJudge(JudgeContext judgeContext){
        String language = judgeContext.getQuestionSubmit().getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if(language.equals("java")){
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
