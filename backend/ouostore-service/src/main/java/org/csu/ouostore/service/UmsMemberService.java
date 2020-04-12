package org.csu.ouostore.service;

import org.csu.ouostore.model.dto.JwtDto;
import org.csu.ouostore.model.entity.UmsMember;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
public interface UmsMemberService extends IService<UmsMember> {

    /**
     * 登入
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    JwtDto signIn(String username, String password);

    /**
     * 注册
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    JwtDto signUp(String username, String password);
}
