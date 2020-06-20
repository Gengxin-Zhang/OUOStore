package org.csu.ouostore.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.csu.ouostore.model.entity.UmsAdminRoleRelation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 后台用户和角色关系表 服务类
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
@Transactional
public interface UmsAdminRoleRelationService extends IService<UmsAdminRoleRelation> {

    boolean create(Long adminId, Long roleId);

    boolean delete(Long adminId, Long roleId);
}
