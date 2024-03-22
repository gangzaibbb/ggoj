package com.gangzai.ggojbackendjudgeservice.judge.codesandbox;

import com.gangzai.ggojbackendjudgeservice.judge.codesandbox.impl.ExampleCodeSandbox;
import com.gangzai.ggojbackendjudgeservice.judge.codesandbox.impl.RemoteCodeSandbox;
import com.gangzai.ggojbackendjudgeservice.judge.codesandbox.impl.ThirdPartyCodeSandbox;

/**
 * @author gangzai
 * @date 2024/3/9
 * 静态工厂类，根据配置文件读取使用的代码沙箱类型并提供
 */
public class CodeSandboxFactory {
    /**
     * 参数要从判题模块传入，不能在这里注入，这是一个独立的模块
     * @param type
     * @return
     */
    public static CodeSandbox newInstance(String type){
        switch (type){
            case "example":
                return new ExampleCodeSandbox();
            case "remote":
                return new RemoteCodeSandbox();
            case "thirdParty":
                return new ThirdPartyCodeSandbox();
            default:
                return new ExampleCodeSandbox();
        }
    }
}
