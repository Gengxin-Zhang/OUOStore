package org.csu.ouostore.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.csu.ouostore.mapper.UmsAdminRoleRelationMapper;
import org.csu.ouostore.model.entity.UmsAdminRoleRelation;
import org.csu.ouostore.service.UmsAdminRoleRelationService;
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
}
