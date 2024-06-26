package com.gangzai.ggoj.model.vo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gangzai.ggoj.model.dto.question.JudgeCase;
import com.gangzai.ggoj.model.dto.question.JudgeConfig;
import com.gangzai.ggoj.model.entity.Question;
import lombok.Data;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 题目
 * @TableName question
 */
@Data
public class QuestionVO implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签列表
     */
    private List<String> tags;

    /**
     * 题目提交数
     */
    private Integer submitNum;

    /**
     * 题目通过数
     */
    private Integer acceptedNum;

    /**
     * 判题配置（json 对象）
     */
    private JudgeConfig judgeConfig;

    /**
     * 点赞数
     */
    private Integer thumbNum;

    /**
     * 收藏数
     */
    private Integer favourNum;

    /**
     * 创建用户id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建题目人的信息
     */
    private UserVO userVO;

    /**
     * 做题状态（是否已通过）
     */
    private Integer statue;

    /**
     * 通过率
     */
    private String passRate;

    /**
     * 答案
     */
    private String answer;

    /**
     * VO转Entity
     */
    public static Question voToObj(QuestionVO questionVO){
        if (questionVO == null) {
            return null;
        }
        Question question = new Question();
        BeanUtil.copyProperties(questionVO, question);
        JudgeConfig judgeConfig = questionVO.getJudgeConfig();
        if (judgeConfig != null) {
            question.setJudgeConfig(JSONUtil.toJsonStr(judgeConfig));
        }
        List<String> tags = questionVO.getTags();
        if (tags != null) {
            question.setTags(JSONUtil.toJsonStr(tags));
        }
        return question;
    }

    /**
     * Entity 转 VO
     */

    public static QuestionVO objToVo(Question question) {
        if (question == null) {
            return null;
        }
        QuestionVO questionVO = new QuestionVO();
        BeanUtils.copyProperties(question, questionVO);
        // 数据库中的Json字符串转化为实体对象
        List<String> tagList = JSONUtil.toList(question.getTags(), String.class);
        questionVO.setTags(tagList);
        String judgeConfigStr = question.getJudgeConfig();
        questionVO.setJudgeConfig(JSONUtil.toBean(judgeConfigStr, JudgeConfig.class));
        // 计算通过率
        float acceptedNum = (float)question.getAcceptedNum();
        float submitNum = (float)question.getSubmitNum();
        try{
            String passRate = Float.valueOf(acceptedNum / submitNum).toString();
            questionVO.setPassRate(passRate);
        }catch (Exception e){
            questionVO.setPassRate("0");
        }
        return questionVO;
    }


    private static final long serialVersionUID = 1L;
}