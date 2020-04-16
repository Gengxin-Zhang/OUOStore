package org.csu.ouostore.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.csu.ouostore.model.entity.UmsMember;
import org.springframework.security.core.userdetails.UserDetails;

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
     * 获取用户信息
     */
    UserDetails loadUserByUsername(String username);

    /**
     * 通过username获取UmsMember
     */
    UmsMember getByUsername(String username);

    UmsMember getCurrentMember();
}
