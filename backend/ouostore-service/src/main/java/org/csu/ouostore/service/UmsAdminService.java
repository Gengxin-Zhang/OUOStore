package org.csu.ouostore.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.csu.ouostore.model.dto.JwtDto;
import org.csu.ouostore.model.entity.UmsAdmin;
import org.csu.ouostore.model.entity.UmsResource;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * <p>
 * 后台用户表 服务类
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
public interface UmsAdminService extends IService<UmsAdmin> {

    /**
     * 获取用户所有可访问资源
     */
    List<UmsResource> getResourceList(Long adminId);

    /**
     * 根据用户名获取后台管理员
     */
    UmsAdmin getAdminByUsername(String username);

    /**
     * 获取用户信息
     */
    UserDetails loadUserDetailsByUsername(String username);

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
