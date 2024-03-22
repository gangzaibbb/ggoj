package com.gangzai.ggojbackendserviceclient;

import com.gangzai.ggojbackendmodel.entity.QuestionSubmit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author gangzai
 * @date 2024/3/22
 */
@FeignClient(name = "ggoj-backend-judge-service", url = "/api/judge/inner")
public interface JudgeFeignClient {
    /**
     * 判题
     * @param questionSubmitId
     * @return
     */
    @PostMapping("/do")
    QuestionSubmit doJudge(@RequestParam("questionSubmitId") long questionSubmitId);
}
