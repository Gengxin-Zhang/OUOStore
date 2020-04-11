package org.csu.ouostore.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.csu.ouostore.common.exception.ApiException;
import org.csu.ouostore.model.entity.UmsRole;
import org.csu.ouostore.mapper.UmsRoleMapper;
import org.csu.ouostore.model.query.UmsRoleCreateParam;
import org.csu.ouostore.service.UmsRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
}
