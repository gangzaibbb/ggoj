package com.gangzai.ggojcodesandbox;

import com.gangzai.ggojcodesandbox.model.ExecuteCodeRequest;
import com.gangzai.ggojcodesandbox.model.ExecuteCodeResponse;

/**
 * @author gangzai
 * @date 2024/3/17
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