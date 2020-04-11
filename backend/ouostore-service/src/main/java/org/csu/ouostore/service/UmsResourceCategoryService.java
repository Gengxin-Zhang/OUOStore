package org.csu.ouostore.service;

import org.csu.ouostore.model.entity.UmsResourceCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import org.csu.ouostore.model.query.UmsResourceCategoryCreateParam;

/**
 * <p>
 * 资源分类表 服务类
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
public interface UmsResourceCategoryService extends IService<UmsResourceCategory> {

    /**
     * 创建资源分类
     */
    boolean create(UmsResourceCategoryCreateParam resourceCategoryCreateParam);
}
