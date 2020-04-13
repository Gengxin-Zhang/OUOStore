package org.csu.ouostore.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.csu.ouostore.common.exception.ApiException;
import org.csu.ouostore.mapper.UmsRoleMenuRelationMapper;
import org.csu.ouostore.model.entity.UmsMenu;
import org.csu.ouostore.model.entity.UmsRole;
import org.csu.ouostore.model.entity.UmsRoleMenuRelation;
import org.csu.ouostore.service.UmsMenuService;
import org.csu.ouostore.service.UmsRoleMenuRelationService;
import org.csu.ouostore.service.UmsRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台角色菜单关系表 服务实现类
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
@Service
public class UmsRoleMenuRelationServiceImpl extends ServiceImpl<UmsRoleMenuRelationMapper, UmsRoleMenuRelation> implements UmsRoleMenuRelationService {

    @Autowired
    UmsRoleService roleService;
    @Autowired
    UmsMenuService menuService;

    @Override
    public boolean allocateMenu(Long roleId, Long menuId) {
        UmsRole roleOne = roleService.getOne(new QueryWrapper<UmsRole>().eq("id", roleId).last("limit 1"));
        if (ObjectUtil.isNull(roleOne)) {
            throw new ApiException("角色不存在");
        }
        UmsMenu menuOne = menuService.getOne(new QueryWrapper<UmsMenu>().eq("id", menuId).last("limit 1"));
        if (ObjectUtil.isNull(menuOne)) {
            throw new ApiException("菜单不存在");
        }
        UmsRoleMenuRelation relation = new UmsRoleMenuRelation();
        relation.setRoleId(roleId);
        relation.setMenuId(menuId);
        UmsRoleMenuRelation one = this.getOne(new QueryWrapper<>(relation).last("limit 1"));
        if (ObjectUtil.isNotNull(one)) {
            throw new ApiException("该角色已拥有此菜单");
        }
        return this.save(relation);
    }
}
