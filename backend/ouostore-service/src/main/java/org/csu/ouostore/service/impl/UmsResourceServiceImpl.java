package org.csu.ouostore.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.csu.ouostore.common.exception.ApiException;
import org.csu.ouostore.mapper.UmsResourceMapper;
import org.csu.ouostore.model.entity.UmsResource;
import org.csu.ouostore.model.entity.UmsResourceCategory;
import org.csu.ouostore.model.query.UmsResourceCreateParam;
import org.csu.ouostore.model.query.UmsResourceQueryParam;
import org.csu.ouostore.service.UmsResourceCategoryService;
import org.csu.ouostore.service.UmsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Autowired
    UmsResourceCategoryService resourceCategoryService;
    @Autowired
    UmsResourceMapper resourceMapper;

    @Override
    public List<UmsResource> listAll() {
        return this.list();
    }

    @Override
    public boolean create(UmsResourceCreateParam umsResourceCreateParam) {
        List<UmsResourceCategory> categoryList = resourceCategoryService.list(new QueryWrapper<UmsResourceCategory>()
                .eq("id", umsResourceCreateParam.getCategoryId()));
        if (categoryList.size() < 1) {
            throw new ApiException("资源分类不存在,操作失败");
        }
        List<UmsResource> resourceList = this.list(new QueryWrapper<UmsResource>()
                .eq("name", umsResourceCreateParam.getName()));
        if (resourceList.size() > 0) {
            throw new ApiException("资源名重复,操作失败");
        }
        UmsResource umsResource = new UmsResource();
        //拷贝属性值
        BeanUtil.copyProperties(umsResourceCreateParam, umsResource);
        umsResource.setCreateTime(LocalDateTime.now());
        return this.save(umsResource);    }

    @Override
    public IPage<UmsResource> selectResourcePage(Page<UmsResource> page, UmsResourceQueryParam resourceQueryParam) {
        //设置page
        page.setCurrent(resourceQueryParam.getPage()).setSize(resourceQueryParam.getPerPage());
        QueryWrapper<UmsResource> wrapper = new QueryWrapper<>();
        if (StrUtil.isNotEmpty(resourceQueryParam.getCategoryId())) {
            wrapper.eq("category_id", resourceQueryParam.getCategoryId());
        }
        if (StrUtil.isNotEmpty(resourceQueryParam.getNameKeyword())) {
            wrapper.like("name", resourceQueryParam.getNameKeyword());
        }
        if (StrUtil.isNotEmpty(resourceQueryParam.getUrlKeyword())) {
            wrapper.like("url", resourceQueryParam.getUrlKeyword());
        }
        // 分页返回的对象与传入的对象是同一个
        return resourceMapper.selectPageVo(page, wrapper);
    }
}
