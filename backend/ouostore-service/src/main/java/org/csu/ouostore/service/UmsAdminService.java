package org.csu.ouostore.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.csu.ouostore.model.dto.JwtDto;
import org.csu.ouostore.model.entity.UmsAdmin;
import org.csu.ouostore.model.entity.UmsResource;
import org.csu.ouostore.model.entity.UmsRole;
import org.csu.ouostore.model.query.UmsAdminSearchParam;
import org.csu.ouostore.model.query.UmsAdminSignUpParam;
import org.csu.ouostore.model.vo.UmsAdminVo;
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
    JwtDto signUp(UmsAdminSignUpParam adminSignUpParam);

    /**
     * 删除后台用户
     * @param id id
     * @return
     */
    boolean delete(Long id);

    /**
     * <p>
     * 查询 : 根据state状态查询用户列表，分页显示
     * </p>
     *
     * @param page 分页对象,xml中可以从里面进行取值,传递参数 Page 即自动分页,必须放在第一位(你可以继承Page实现自己的分页对象)
     * @param adminSearchParam 查询条件
     * @return 分页对象
     */
    IPage<UmsAdminVo> selectResourcePage(Page<UmsAdminVo> page, UmsAdminSearchParam adminSearchParam);

    /**
     * 获取用户所有角色
     */
    List<UmsRole> getRoleList(Long adminId);
}
