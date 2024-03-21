package com.gangzai.ggojcodesandbox.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author gangzai
 * @date 2024/3/9
 * 代码执行请求类（判题模块传递给代码沙箱的参数）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteCodeRequest {
    /**
     * 输入用例
     */
    private List<String> inputList;

    /**
     * 提交代码
     */
    private String code;

    /**
     * 编程语言
     */
    private String language;
}
