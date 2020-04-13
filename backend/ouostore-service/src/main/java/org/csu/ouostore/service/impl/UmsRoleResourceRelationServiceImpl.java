package org.csu.ouostore.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.csu.ouostore.common.exception.ApiException;
import org.csu.ouostore.mapper.UmsRoleResourceRelationMapper;
import org.csu.ouostore.model.entity.UmsResource;
import org.csu.ouostore.model.entity.UmsRole;
import org.csu.ouostore.model.entity.UmsRoleResourceRelation;
import org.csu.ouostore.service.UmsResourceService;
import org.csu.ouostore.service.UmsRoleResourceRelationService;
import org.csu.ouostore.service.UmsRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台角色资源关系表 服务实现类
 * </p>
 *
 * @author zack4
 * @since 2020-04-09
 */
@Service
public class UmsRoleResourceRelationServiceImpl extends ServiceImpl<UmsRoleResourceRelationMapper, UmsRoleResourceRelation> implements UmsRoleResourceRelationService {

    @Autowired
    UmsResourceService resourceService;
    @Autowired
    UmsRoleService roleService;

    @Override
    public boolean allocateResource(Long roleId, Long resourceId) {
        UmsRole roleOne = roleService.getOne(new QueryWrapper<UmsRole>().eq("id", roleId).last("limit 1"));
        if (ObjectUtil.isNull(roleOne)) {
            throw new ApiException("角色不存在");
        }
        UmsResource resourceOne = resourceService.getOne(new QueryWrapper<UmsResource>().eq("id", resourceId).last("limit 1"));
        if (ObjectUtil.isNull(resourceOne)) {
            throw new ApiException("资源不存在");
        }
        UmsRoleResourceRelation relation = new UmsRoleResourceRelation();
        relation.setResourceId(resourceId);
        relation.setRoleId(roleId);
        UmsRoleResourceRelation one = this.getOne(new QueryWrapper<>(relation).last("limit 1"));
        if (ObjectUtil.isNotNull(one)) {
            throw new ApiException("该角色已拥有此资源");
        }
        return this.save(relation);
    }
}
