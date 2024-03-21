package com.gangzai.ggoj.judge.codesandbox;

import com.gangzai.ggoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.gangzai.ggoj.judge.codesandbox.model.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gangzai
 * @date 2024/3/9
 * 代码沙箱日志增强
 */
@Slf4j
public class CodeSandboxProxy implements CodeSandbox{

    private final CodeSandbox codeSandbox;

    public CodeSandboxProxy(CodeSandbox codeSandbox) {
        this.codeSandbox = codeSandbox;
    }

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("代码沙箱请求参数：",executeCodeRequest);
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        log.info("代码沙箱响应参数：",executeCodeResponse);
        return executeCodeResponse;
    }
}
