package org.csu.ouostore.service;

import org.csu.ouostore.model.entity.UmsRole;
import com.baomidou.mybatisplus.extension.service.IService;
import org.csu.ouostore.model.query.UmsRoleCreateParam;

/**
 * <p>
 * 后台用户角色表 服务类
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
public interface UmsRoleService extends IService<UmsRole> {

    /**
     * 添加角色
     */
    boolean create(UmsRoleCreateParam role);
}
