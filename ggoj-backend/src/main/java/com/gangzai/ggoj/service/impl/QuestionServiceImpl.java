package com.gangzai.ggoj.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gangzai.ggoj.common.ErrorCode;
import com.gangzai.ggoj.constant.CommonConstant;
import com.gangzai.ggoj.exception.BusinessException;
import com.gangzai.ggoj.exception.ThrowUtils;
import com.gangzai.ggoj.judge.codesandbox.model.JudgeInfo;
import com.gangzai.ggoj.mapper.QuestionMapper;
import com.gangzai.ggoj.model.dto.question.QuestionQueryRequest;
import com.gangzai.ggoj.model.entity.Question;
import com.gangzai.ggoj.model.entity.QuestionSubmit;
import com.gangzai.ggoj.model.entity.User;
import com.gangzai.ggoj.model.enums.JudgeInfoMessageEnum;
import com.gangzai.ggoj.model.enums.QuestionSubmitStatusEnum;
import com.gangzai.ggoj.model.enums.QuestionUserStatusEnum;
import com.gangzai.ggoj.model.vo.QuestionSubmitVO;
import com.gangzai.ggoj.model.vo.QuestionVO;
import com.gangzai.ggoj.model.vo.UserVO;
import com.gangzai.ggoj.service.QuestionService;
import com.gangzai.ggoj.service.QuestionSubmitService;
import com.gangzai.ggoj.service.UserService;
import com.gangzai.ggoj.utils.SqlUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
* @author gangzai
* @description 针对表【question(题目)】的数据库操作Service实现
* @createDate 2024-03-04 16:38:15
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService{

    @Resource
    private UserService userService;

    @Resource
    @Lazy
    private QuestionSubmitService questionSubmitService;

    @Override
    public void validQuestion(Question question, boolean add) {
        if (question == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String title = question.getTitle();
        String content = question.getContent();
        String tags = question.getTags();
        String answer = question.getAnswer();
        String judgeCase = question.getJudgeCase();
        String judgeConfig = question.getJudgeConfig();

        // 创建时，参数不能为空
        if (add) {
            ThrowUtils.throwIf(StringUtils.isAnyBlank(title, content, tags), ErrorCode.PARAMS_ERROR);
        }
        // 有参数则校验
        if (StringUtils.isNotBlank(title) && title.length() > 80) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "标题过长");
        }
        if (StringUtils.isNotBlank(content) && content.length() > 8192) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "内容过长");
        }
        if (StringUtils.isNotBlank(answer) && answer.length() > 8192) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "答案过长");
        }
        if (StringUtils.isNotBlank(judgeCase) && judgeCase.length() > 8192) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "判题用例过长");
        }
        if (StringUtils.isNotBlank(judgeConfig) && judgeConfig.length() > 8192) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "判题配置过长");
        }
    }

    /**
     * 获取查询包装类R
     *
     * @param questionQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<Question> getQueryWrapper(QuestionQueryRequest questionQueryRequest) {
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        if (questionQueryRequest == null) {
            return queryWrapper;
        }
        Long id = questionQueryRequest.getId();
        String title = questionQueryRequest.getTitle();
        String content = questionQueryRequest.getContent();
        List<String> tags = questionQueryRequest.getTags();
        String answer = questionQueryRequest.getAnswer();
        Long userId = questionQueryRequest.getUserId();
        String sortField = questionQueryRequest.getSortField();
        String sortOrder = questionQueryRequest.getSortOrder();
        // 拼接查询条件
        queryWrapper.like(StringUtils.isNotBlank(title), "title", title);
        queryWrapper.like(StringUtils.isNotBlank(content), "content", content);
        queryWrapper.like(StringUtils.isNotBlank(answer), "answer", answer);
        if (CollectionUtils.isNotEmpty(tags)) {
            for (String tag : tags) {
                queryWrapper.like("tags", "\"" + tag + "\"");
            }
        }
        queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq("isDelete", false);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    /**
     * 将查询结果封装成VO
     * @param question
     * @param request
     * @return
     */
    @Override
    public QuestionVO getQuestionVO(Question question, HttpServletRequest request) {
        QuestionVO questionVO = QuestionVO.objToVo(question);
        // 1. 查询创建题目的用户信息
        Long userId = question.getUserId();
        User user = null;
        if (userId != null && userId > 0) {
            user = userService.getById(userId);
        }
        // 2. 判断当前查询用户的做题状态
        Long loginUserId = userService.getLoginUser(request).getId();
        List<QuestionSubmit> questionSubmitList = questionSubmitService.query().eq("userId", loginUserId).eq("questionId", question.getId()).list();
        if (questionSubmitList.isEmpty()) {
            questionVO.setStatue(QuestionUserStatusEnum.NOTBEGIN.getValue());
        }
        else {
            for (QuestionSubmit questionSubmit : questionSubmitList) {
                JudgeInfo judgeInfo = JSONUtil.toBean(questionSubmit.getJudgeInfo(), JudgeInfo.class);
                if (judgeInfo.getMessage() != null && judgeInfo.getMessage().equals(JudgeInfoMessageEnum.ACCEPTED.getValue())) {
                    questionVO.setStatue(QuestionUserStatusEnum.PASS.getValue());
                    break;
                }
            }
            questionVO.setStatue(QuestionUserStatusEnum.TRIED.getValue());
        }
        UserVO userVO = userService.getUserVO(user);
        questionVO.setUserVO(userVO);
        return questionVO;
    }

    /**
     * 将MP分页查询结果封装成VO
     * @param questionPage
     * @param request
     * @return
     */
    @Override
    public Page<QuestionVO> getQuestionVOPage(Page<Question> questionPage, HttpServletRequest request, Integer statue) {
        List<Question> questionList = questionPage.getRecords();
        Page<QuestionVO> questionVOPage = new Page<>(questionPage.getCurrent(), questionPage.getSize(), questionPage.getTotal());
        if (CollectionUtils.isEmpty(questionList)) {
            return questionVOPage;
        }
        // 1. 关联查询创建题目的用户信息
        Set<Long> userIdSet = questionList.stream().map(Question::getUserId).collect(Collectors.toSet());
        Map<Long, List<User>> userIdUserListMap = userService.listByIds(userIdSet).stream()
                .collect(Collectors.groupingBy(User::getId));
        // 2. 查询当前查询用户的题目提交信息
        Long loginUserId = userService.getLoginUser(request).getId();
        List<QuestionSubmit> questionSubmitList = questionSubmitService.query().eq("userId", loginUserId).list();
        // 填充信息，根据用户提交状态过滤
        List<QuestionVO> questionVOList = questionList.stream().map(question -> {
            QuestionVO questionVO = QuestionVO.objToVo(question);
            Long userId = question.getUserId();
            User user = null;
            if (userIdUserListMap.containsKey(userId)) {
                user = userIdUserListMap.get(userId).get(0);
            }
            questionVO.setUserVO(userService.getUserVO(user));
            // 判断题目提交状态
            questionVO.setStatue(QuestionUserStatusEnum.NOTBEGIN.getValue());
            for (QuestionSubmit questionSubmit : questionSubmitList){
                if (questionSubmit.getQuestionId().equals(question.getId())) {
                    JudgeInfo judgeInfo = JSONUtil.toBean(questionSubmit.getJudgeInfo(), JudgeInfo.class);
                    if (judgeInfo.getMessage() != null && judgeInfo.getMessage().equals(JudgeInfoMessageEnum.ACCEPTED.getValue())) {
                        questionVO.setStatue(QuestionUserStatusEnum.PASS.getValue());
                        break;
                    }
                    questionVO.setStatue(QuestionUserStatusEnum.TRIED.getValue());
                }
            }
            return questionVO;
        }).filter(questionVO -> {
            // 不需要过滤
            if (statue == null || statue.equals(QuestionUserStatusEnum.ALL.getValue())) {
                return true;
            }
            return questionVO.getStatue().equals(statue);
        }).collect(Collectors.toList());
        questionVOPage.setRecords(questionVOList);
        return questionVOPage;
    }
}




