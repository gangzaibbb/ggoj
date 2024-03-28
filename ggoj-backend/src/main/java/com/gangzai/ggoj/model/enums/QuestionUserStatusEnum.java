package com.gangzai.ggoj.model.enums;

/**
 * @author gangzai
 * @date 2024/3/28
 */
public enum QuestionUserStatusEnum {
    ALL("全部", 0),
    NOTBEGIN("未开始",1),
    TRIED("尝试过", 2),
    PASS("已解答", 3);
    private final String text;
    private final Integer value;

    QuestionUserStatusEnum(String text, Integer value) {
        this.text = text;
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
