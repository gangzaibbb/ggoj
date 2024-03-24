package com.gangzai.ggojbackendjudgeservice.judge.codesandbox.impl;


import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.gangzai.ggojbackendcommon.exception.BusinessException;
import com.gangzai.ggojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.gangzai.ggojbackendmodel.codesandbox.ExecuteCodeRequest;
import com.gangzai.ggojbackendmodel.codesandbox.ExecuteCodeResponse;
import com.gangzai.ggojbackendcommon.common.ErrorCode;
import org.apache.commons.lang3.StringUtils;

/**
 * @author gangzai
 * @date 2024/3/9
 * 远程代码沙箱（自己开发的代码沙箱）
 */
public class RemoteCodeSandbox implements CodeSandbox {
    // 定义鉴权请求头和密钥
    private static final String AUTH_REQUEST_HEADER = "auth";

    private static final String AUTH_REQUEST_SECRET = "secretKey";


    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程代码沙箱");
        String url = "http://localhost:8090/executeCode";
        String json = JSONUtil.toJsonStr(executeCodeRequest);
        String responseStr = HttpUtil.createPost(url)
                .header(AUTH_REQUEST_HEADER, AUTH_REQUEST_SECRET)
                .body(json)
                .execute()
                .body();
        if (StringUtils.isBlank(responseStr)) {
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR, "executeCode remoteSandbox error, message = " + responseStr);
        }
        ExecuteCodeResponse executeCodeResponse = JSONUtil.toBean(responseStr, ExecuteCodeResponse.class);
        return executeCodeResponse;
    }
}
