package com.gangzai.ggoj.judge.codesandbox;

import com.gangzai.ggoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.gangzai.ggoj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * @author gangzai
 * @date 2024/3/9
 * 代码沙箱接口定义
 */
public interface CodeSandbox {

    /**
     * 执行代码
     *
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
