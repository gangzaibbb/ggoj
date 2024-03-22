package com.gangzai.ggojbackendjudgeservice.judge.codesandbox.impl;


import com.gangzai.ggojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.gangzai.ggojbackendmodel.codesandbox.ExecuteCodeRequest;
import com.gangzai.ggojbackendmodel.codesandbox.ExecuteCodeResponse;
import com.gangzai.ggojbackendmodel.codesandbox.JudgeInfo;
import com.gangzai.ggojbackendmodel.enums.JudgeInfoMessageEnum;
import com.gangzai.ggojbackendmodel.enums.QuestionSubmitStatusEnum;

import java.util.List;

/**
 * @author gangzai
 * @date 2024/3/9
 * 实例代码沙箱，用于开发阶段调通代码
 */
public class ExampleCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("测试执行成功");
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(100L);
        executeCodeResponse.setJudgeInfo(judgeInfo);
        return executeCodeResponse;
    }
}
