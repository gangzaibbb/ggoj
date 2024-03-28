package com.gangzai.ggoj.judge;

import cn.hutool.json.JSONUtil;
import com.gangzai.ggoj.common.ErrorCode;
import com.gangzai.ggoj.exception.BusinessException;
import com.gangzai.ggoj.judge.codesandbox.CodeSandbox;
import com.gangzai.ggoj.judge.codesandbox.CodeSandboxFactory;
import com.gangzai.ggoj.judge.codesandbox.CodeSandboxProxy;
import com.gangzai.ggoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.gangzai.ggoj.judge.codesandbox.model.ExecuteCodeResponse;
import com.gangzai.ggoj.judge.codesandbox.model.JudgeInfo;
import com.gangzai.ggoj.judge.strategy.JudgeContext;
import com.gangzai.ggoj.model.dto.question.JudgeCase;
import com.gangzai.ggoj.model.entity.Question;
import com.gangzai.ggoj.model.entity.QuestionSubmit;
import com.gangzai.ggoj.model.enums.JudgeInfoMessageEnum;
import com.gangzai.ggoj.model.enums.QuestionSubmitStatusEnum;
import com.gangzai.ggoj.service.QuestionService;
import com.gangzai.ggoj.service.QuestionSubmitService;
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
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private JudgeManager judgeManager;

    @Value("${codesandbox.type}")
    private String type;

    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        //1. 从数据库中拿到题目提交对象与题目对象
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目提交信息不存在");
        }
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionService.getById(questionId);
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
        boolean save = questionSubmitService.updateById(questionSubmitUpdate);
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
        judgeContext.setMessage(executeCodeResponse.getMessage());
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
        boolean update = questionSubmitService.updateById(questionSubmitUpdate);
        // 如果答案正确，通过数+1
        if (judgeInfoResponse.getResult().equals(JudgeInfoMessageEnum.ACCEPTED.getValue())) {
            Integer acceptedNum = question.getAcceptedNum();
            acceptedNum += 1;
            boolean updated = questionService.update().eq("id", questionId).set("acceptedNum", acceptedNum).update();
            if(!updated){
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据更新失败");
            }
        }
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据更新失败");
        }
        QuestionSubmit questionSubmitResponse = questionSubmitService.getById(questionSubmitId);
        return questionSubmitResponse;
    }
}
