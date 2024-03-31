package com.gangzai.ggoj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gangzai.ggoj.model.dto.question.QuestionQueryRequest;
import com.gangzai.ggoj.model.entity.Question;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gangzai.ggoj.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author gangzai
* @description 针对表【question(题目)】的数据库操作Service
* @createDate 2024-03-04 16:38:15
*/
public interface QuestionService extends IService<Question> {

    /**
     * 校验
     *
     * @param question
     * @param add
     */
    void validQuestion(Question question, boolean add);

    /**
     * 获取查询条件
     *
     * @param questionQueryRequest
     * @return
     */
    QueryWrapper<Question> getQueryWrapper(QuestionQueryRequest questionQueryRequest);

    /**
     * 获取问题封装
     *
     * @param question
     * @param request
     * @return
     */
    QuestionVO getQuestionVO(Question question, HttpServletRequest request);

    /**
     * 分页获取问题封装
     *
     * @param questionPage
     * @param request
     * @return
     */
    Page<QuestionVO> getQuestionVOPage(Page<Question> questionPage, HttpServletRequest request, Integer statue);
}
