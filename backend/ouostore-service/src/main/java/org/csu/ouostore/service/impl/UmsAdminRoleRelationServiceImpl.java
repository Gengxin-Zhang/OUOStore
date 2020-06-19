package org.csu.ouostore.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.csu.ouostore.common.exception.ApiException;
import org.csu.ouostore.mapper.UmsAdminRoleRelationMapper;
import org.csu.ouostore.model.entity.UmsAdmin;
import org.csu.ouostore.model.entity.UmsAdminRoleRelation;
import org.csu.ouostore.model.entity.UmsRole;
import org.csu.ouostore.service.UmsAdminRoleRelationService;
import org.csu.ouostore.service.UmsAdminService;
import org.csu.ouostore.service.UmsRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台用户和角色关系表 服务实现类
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
@Service
public class UmsAdminRoleRelationServiceImpl extends ServiceImpl<UmsAdminRoleRelationMapper, UmsAdminRoleRelation> implements UmsAdminRoleRelationService {

    @Autowired
    UmsAdminService adminService;
    @Autowired
    UmsRoleService roleService;

    @Override
    public boolean create(Long adminId, Long roleId) {
        UmsAdmin adminOne = adminService.getOne(new QueryWrapper<UmsAdmin>().eq("id", adminId));
        if (ObjectUtil.isNull(adminOne)) {
            throw new ApiException("该用户不存在");
        }
        UmsRole roleOne = roleService.getOne(new QueryWrapper<UmsRole>().eq("id", roleId));
        if (ObjectUtil.isNull(roleOne)) {
            throw new ApiException("该角色不存在");
        }
        UmsAdminRoleRelation relation = new UmsAdminRoleRelation();
        relation.setAdminId(adminId);
        relation.setRoleId(roleId);
        UmsAdminRoleRelation one = this.getOne(new QueryWrapper<>(relation));
        if (ObjectUtil.isNotNull(one)) {
            throw new ApiException("该用户已拥有此权限");
        }
        return this.save(relation);
    }

    @Override
    public boolean delete(Long adminId, Long roleId) {
        UmsAdmin adminOne = adminService.getOne(new QueryWrapper<UmsAdmin>().eq("id", adminId));
        if (ObjectUtil.isNull(adminOne)) {
            throw new ApiException("该用户不存在");
        }
        UmsRole roleOne = roleService.getOne(new QueryWrapper<UmsRole>().eq("id", roleId));
        if (ObjectUtil.isNull(roleOne)) {
            throw new ApiException("该角色不存在");
        }
        UmsAdminRoleRelation relation = new UmsAdminRoleRelation();
        relation.setAdminId(adminId);
        relation.setRoleId(roleId);
        UmsAdminRoleRelation one = this.getOne(new QueryWrapper<>(relation));
        if (!ObjectUtil.isNotNull(one)) {
            throw new ApiException("该用户未拥有此权限");
        }
        return this.remove(new QueryWrapper<>(relation));
    }
}
