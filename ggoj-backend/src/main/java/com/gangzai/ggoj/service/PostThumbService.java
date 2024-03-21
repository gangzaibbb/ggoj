package com.gangzai.ggoj.service;

import com.gangzai.ggoj.model.entity.PostThumb;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gangzai.ggoj.model.entity.User;

/**
 * 帖子点赞服务
 *
 * @author <a href="https://github.com/gangzaibbb">李兆港</a>
 * 
 */
public interface PostThumbService extends IService<PostThumb> {

    /**
     * 点赞
     *
     * @param postId
     * @param loginUser
     * @return
     */
    int doPostThumb(long postId, User loginUser);

    /**
     * 帖子点赞（内部服务）
     *
     * @param userId
     * @param postId
     * @return
     */
    int doPostThumbInner(long userId, long postId);
}
