package com.gangzai.ggojbackendquestionservice.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gangzai.ggojbackendmodel.dto.questionsubmit.QuestionSubmitAddRequest;
import com.gangzai.ggojbackendmodel.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.gangzai.ggojbackendmodel.entity.QuestionSubmit;
import com.gangzai.ggojbackendmodel.entity.User;
import com.gangzai.ggojbackendmodel.vo.QuestionSubmitVO;

/**
* @author gangzai
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* @createDate 2024-03-04 16:38:32
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {
    /**
     * 提交
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);

    /**
     * 获取题目封装
     *
     * @param questionSubmit
     * @param loginUser
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser);

    /**
     * 分页获取题目封装
     *
     * @param questionSubmitPage
     * @param loginUser
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser);
}
