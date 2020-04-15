package org.csu.ouostore.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.csu.ouostore.common.exception.ApiException;
import org.csu.ouostore.mapper.UmsRoleMapper;
import org.csu.ouostore.model.entity.*;
import org.csu.ouostore.model.query.UmsRoleCreateParam;
import org.csu.ouostore.model.query.UmsRoleQueryParam;
import org.csu.ouostore.model.vo.UmsMenuNodeVo;
import org.csu.ouostore.service.UmsAdminRoleRelationService;
import org.csu.ouostore.service.UmsRoleMenuRelationService;
import org.csu.ouostore.service.UmsRoleResourceRelationService;
import org.csu.ouostore.service.UmsRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 后台用户角色表 服务实现类
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
@Service
public class UmsRoleServiceImpl extends ServiceImpl<UmsRoleMapper, UmsRole> implements UmsRoleService {

    @Autowired
    UmsRoleMapper roleMapper;
    @Autowired
    UmsRoleResourceRelationService roleResourceRelationService;
    @Autowired
    UmsRoleMenuRelationService roleMenuRelationService;
    @Autowired
    UmsAdminRoleRelationService adminRoleRelationService;

    @Override
    public boolean create(UmsRoleCreateParam roleCreateParam) {
        List<UmsRole> roleList = this.list(new QueryWrapper<UmsRole>().eq("name", roleCreateParam.getName()));
        if (roleList.size() > 0) {
            throw new ApiException("已有同名角色,创建失败");
        }
        UmsRole role = new UmsRole();
        BeanUtil.copyProperties(roleCreateParam, role);
        role.setCreateTime(LocalDateTime.now());
        role.setAdminCount(0);
        if (ObjectUtil.isNull(role.getStatus())) {
            role.setStatus(1);
        }
        return this.save(role);
    }

    @Override
    public IPage<UmsRole> selectRolePage(Page<UmsRole> page, UmsRoleQueryParam roleQueryParam) {
        //设置page
        page.setCurrent(roleQueryParam.getPage()).setSize(roleQueryParam.getPerPage());
        QueryWrapper<UmsRole> wrapper = new QueryWrapper<>();
        if (StrUtil.isNotEmpty(roleQueryParam.getNameKeyword())) {
            wrapper.like("name", roleQueryParam.getNameKeyword());
        }
        // 分页返回的对象与传入的对象是同一个
        return roleMapper.selectPageVo(page, wrapper);
    }

    @Override
    public List<UmsResource> listResource(Long roleId) {
        return roleMapper.getResourceListByRoleId(roleId);
    }

    @Override
    public List<UmsMenuNodeVo> listMenu(Long roleId) {
        List<UmsMenu> menuList = roleMapper.getMenuListByRoleId(roleId);
        return menuList.stream()
                .filter(menu -> menu.getParentId().equals(0L))
                .map(menu -> UmsMenuServiceImpl.covertMenuNode(menu, menuList)).collect(Collectors.toList());
    }

    @Override
    public boolean delete(Long id) {
        roleResourceRelationService.remove(new QueryWrapper<UmsRoleResourceRelation>().eq("role_id", id));
        roleMenuRelationService.remove(new QueryWrapper<UmsRoleMenuRelation>().eq("role_id", id));
        adminRoleRelationService.remove(new QueryWrapper<UmsAdminRoleRelation>().eq("role_id", id));
        return true;
    }

}
