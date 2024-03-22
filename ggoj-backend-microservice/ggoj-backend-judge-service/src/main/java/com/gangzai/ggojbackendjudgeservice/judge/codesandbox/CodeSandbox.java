package com.gangzai.ggojbackendjudgeservice.judge.codesandbox;


import com.gangzai.ggojbackendmodel.codesandbox.ExecuteCodeRequest;
import com.gangzai.ggojbackendmodel.codesandbox.ExecuteCodeResponse;

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
