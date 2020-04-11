package org.csu.ouostore.service.impl;

import cn.hutool.core.bean.BeanUtil;
import org.csu.ouostore.model.entity.UmsResourceCategory;
import org.csu.ouostore.mapper.UmsResourceCategoryMapper;
import org.csu.ouostore.model.query.UmsResourceCategoryCreateParam;
import org.csu.ouostore.service.UmsResourceCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 资源分类表 服务实现类
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
@Service
public class UmsResourceCategoryServiceImpl extends ServiceImpl<UmsResourceCategoryMapper, UmsResourceCategory> implements UmsResourceCategoryService {

    @Override
    public boolean create(UmsResourceCategoryCreateParam resourceCategoryCreateParam) {
        UmsResourceCategory resourceCategory = new UmsResourceCategory();
        //复制属性
        BeanUtil.copyProperties(resourceCategoryCreateParam, resourceCategory);
        resourceCategory.setCreateTime(LocalDateTime.now());
        return this.save(resourceCategory);
    }
}
