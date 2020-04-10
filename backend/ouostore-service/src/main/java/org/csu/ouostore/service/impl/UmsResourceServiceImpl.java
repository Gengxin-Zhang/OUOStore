package org.csu.ouostore.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.csu.ouostore.mapper.UmsResourceMapper;
import org.csu.ouostore.model.entity.UmsResource;
import org.csu.ouostore.service.UmsResourceService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 后台资源表 服务实现类
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
@Service
public class UmsResourceServiceImpl extends ServiceImpl<UmsResourceMapper, UmsResource> implements UmsResourceService {

    @Override
    public List<UmsResource> listAll() {
        return this.list();
    }
}
