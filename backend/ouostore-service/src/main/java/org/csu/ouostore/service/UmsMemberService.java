package org.csu.ouostore.service;

import org.csu.ouostore.model.dto.JwtDto;
import org.csu.ouostore.model.entity.UmsMember;
import com.baomidou.mybatisplus.extension.service.IService;
import org.csu.ouostore.model.query.UmsMemberPatchParam;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
public interface UmsMemberService extends IService<UmsMember> {

//    /**
//     * 登入
//     * @param username 用户名
//     * @param password 密码
//     * @return 生成的JWT的token
//     */
//    JwtDto signIn(String username, String password);

    /**
     * 注册
     * @return 生成的JWT的token
     */
    JwtDto SignUp(String telephone, String authCode);

    /**
     * 根据用户名获取会员
     */
    UmsMember getByUsername(String username);

    /**
     * 根据会员编号获取会员
     */
    UmsMember getById(Long id);

    /**
     * 修改密码
     */
    @Transactional
    void updatePassword(String telephone, String password, String authCode);

    /**
     * 获取当前登录会员
     */
    UmsMember getCurrentMember();

    /**
     * 获取用户信息
     */
    UserDetails loadUserByUsername(String username);

    /**
     * 生成验证码
     */
    String generateAuthCode();


    /**
     * 修改用户信息
     */
    boolean patch(UmsMemberPatchParam param);

}
