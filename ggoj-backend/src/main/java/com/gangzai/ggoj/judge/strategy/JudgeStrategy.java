package com.gangzai.ggoj.judge.strategy;

import com.gangzai.ggoj.judge.codesandbox.model.JudgeInfo;

/**
 * @author gangzai
 * @date 2024/3/9
 * 判题策略接口（得到沙箱的输出后提供判题服务，而不是在这里调用沙箱）
 */
public interface JudgeStrategy {
    JudgeInfo doJudge(JudgeContext judgeContext);
}
