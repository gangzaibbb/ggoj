package com.gangzai.ggojbackendquestionservice.controller.inner;

import com.gangzai.ggojbackendmodel.entity.Question;
import com.gangzai.ggojbackendmodel.entity.QuestionSubmit;
import com.gangzai.ggojbackendquestionservice.service.QuestionService;
import com.gangzai.ggojbackendquestionservice.service.QuestionSubmitService;
import com.gangzai.ggojbackendserviceclient.QuestionFeignClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author gangzai
 * @date 2024/3/22
 */
@RestController
@RequestMapping("/inner")
public class QuestionInnerController implements QuestionFeignClient {

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @GetMapping("/get/id")
    @Override
    public Question getQuestionById(@RequestParam("questionId") long questionId) {
        return questionService.getById(questionId);
    }

    @GetMapping("/question_submit/get/id")
    @Override
    public QuestionSubmit getQuestionSubmitById(@RequestParam("questionId") long questionSubmitId) {
        return questionSubmitService.getById(questionSubmitId);
    }

    @PostMapping("/question_submit/update")
    @Override
    public boolean updateQuestionSubmitById(@RequestBody QuestionSubmit questionSubmit) {
        return questionSubmitService.updateById(questionSubmit);
    }

}

