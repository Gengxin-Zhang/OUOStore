package org.csu.ouostore.service;

import org.csu.ouostore.model.entity.UmsRoleMenuRelation;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 后台角色菜单关系表 服务类
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
public interface UmsRoleMenuRelationService extends IService<UmsRoleMenuRelation> {

    /**
     * 分配菜单
     */
    boolean allocateMenu(Long roleId, Long menuId);
}
