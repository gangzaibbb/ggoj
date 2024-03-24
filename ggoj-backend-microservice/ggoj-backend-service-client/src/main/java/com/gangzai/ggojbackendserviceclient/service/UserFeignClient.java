package com.gangzai.ggojbackendserviceclient.service;

import cn.hutool.core.bean.BeanUtil;
import com.gangzai.ggojbackendcommon.common.ErrorCode;
import com.gangzai.ggojbackendcommon.exception.BusinessException;
import com.gangzai.ggojbackendmodel.entity.User;
import com.gangzai.ggojbackendmodel.enums.UserRoleEnum;
import com.gangzai.ggojbackendmodel.vo.UserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;


import static com.gangzai.ggojbackendcommon.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @author gangzai
 * @date 2024/3/22
 */
@FeignClient(name = "ggoj-backend-user-service", path = "/api/user/inner")
public interface UserFeignClient {

    /**
     * 根据id获取用户
     * @param userId
     * @return
     */
    @GetMapping("/get/id")
    User getById(@RequestParam("userId") long userId);

    /**
     * 根据id列表获取用户列表
     * @param idList
     * @return
     */
    @GetMapping("/get/ids")
    List<User> listByIds(@RequestParam("idList") Collection<Long> idList);

    /**
     * 获取登录用户（默认方法，不参与远程调用）
     * @param request
     * @return
     */
    default User getLoginUser(HttpServletRequest request){
        Object userObject = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObject;
        if (user == null || user.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR, "未登录");
        }
        return user;
    }

    /**
     * 判断用户是否为管理员（默认方法，不参与远程调用）
     * @param user
     * @return
     */
    default boolean isAdmin(User user){
        return user != null && user.getUserRole().equals(UserRoleEnum.ADMIN.getValue());
    }


    /**
     * 将实体类转换为VO（默认方法，不参与远程调用）
     * @param user
     * @return
     */
    default UserVO getUserVO(User user){
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(user,userVO);
        return userVO;
    }
}
