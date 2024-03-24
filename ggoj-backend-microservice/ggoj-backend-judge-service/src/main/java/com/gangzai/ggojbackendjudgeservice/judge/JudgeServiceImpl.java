package com.gangzai.ggojbackendjudgeservice.judge;

import cn.hutool.json.JSONUtil;
import com.gangzai.ggojbackendcommon.common.ErrorCode;
import com.gangzai.ggojbackendcommon.exception.BusinessException;
import com.gangzai.ggojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.gangzai.ggojbackendjudgeservice.judge.codesandbox.CodeSandboxFactory;
import com.gangzai.ggojbackendjudgeservice.judge.codesandbox.CodeSandboxProxy;
import com.gangzai.ggojbackendjudgeservice.judge.strategy.JudgeContext;
import com.gangzai.ggojbackendmodel.codesandbox.ExecuteCodeRequest;
import com.gangzai.ggojbackendmodel.codesandbox.ExecuteCodeResponse;
import com.gangzai.ggojbackendmodel.codesandbox.JudgeInfo;
import com.gangzai.ggojbackendmodel.dto.question.JudgeCase;
import com.gangzai.ggojbackendmodel.entity.Question;
import com.gangzai.ggojbackendmodel.entity.QuestionSubmit;
import com.gangzai.ggojbackendmodel.enums.QuestionSubmitStatusEnum;
import com.gangzai.ggojbackendserviceclient.service.QuestionFeignClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gangzai
 * @date 2024/3/9
 */
@Service
public class JudgeServiceImpl implements JudgeService {

    @Resource
    private QuestionFeignClient questionFeignClient;

    @Resource
    private JudgeManager judgeManager;

    @Value("${codesandbox.type:example}")
    private String type;

    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        //1. 从数据库中拿到题目提交对象与题目对象
        QuestionSubmit questionSubmit = questionFeignClient.getQuestionSubmitById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目提交信息不存在");
        }
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionFeignClient.getQuestionById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }
        //2. 如果题目提交状态不是等待中，就没有必要重复判题了
        Integer status = questionSubmit.getStatus();
        if (!QuestionSubmitStatusEnum.WAITING.getValue().equals(status)) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "请勿重复判题");
        }
        //3. 设置判题状态为等待中，防止重复判题以及给用户及时反馈
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean save = questionFeignClient.updateQuestionSubmitById(questionSubmitUpdate);
        if (!save) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据更新失败");
        }
        //4. 参数校验并封装（从数据库中取出来的数据一定是合法的）
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        String judgeCaseStr = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(judgeCase -> judgeCase.getInput()).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder().language(language).code(code).inputList(inputList).build();
        //5. 调用代码沙箱
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
        CodeSandboxProxy codeSandboxProxy = new CodeSandboxProxy(codeSandbox);
        ExecuteCodeResponse executeCodeResponse = codeSandboxProxy.executeCode(executeCodeRequest);
        //6. 封装判题上下文对象
        JudgeInfo judgeInfo = executeCodeResponse.getJudgeInfo();
        List<String> outputList = executeCodeResponse.getOutputList();
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(judgeInfo);
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setQuestion(question);
        judgeContext.setQuestionSubmit(questionSubmit);
        //7. 调用判题策略，更新数据库，封装返回结果
        JudgeInfo judgeInfoResponse = judgeManager.doJudge(judgeContext);
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfoResponse));
        questionSubmitUpdate.setId(questionSubmitId);
        boolean update = questionFeignClient.updateQuestionSubmitById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据更新失败");
        }
        QuestionSubmit questionSubmitResponse = questionFeignClient.getQuestionSubmitById(questionSubmitId);
        return questionSubmitResponse;
    }
}
