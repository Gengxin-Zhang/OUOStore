package org.csu.ouostore.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.csu.ouostore.model.entity.PmsProductCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import org.csu.ouostore.model.entity.UmsResource;
import org.csu.ouostore.model.query.PmsProductCategoryQueryParam;
import org.csu.ouostore.model.query.UmsResourceQueryParam;

import java.util.List;

/**
 * <p>
 * 宠物一级分类,猫/狗/兔子... 服务类
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
public interface PmsProductCategoryService extends IService<PmsProductCategory> {

    /**
     * 返回商品目录列表
     */
    List<PmsProductCategory> categoryList();

    /**
     * 分页显示商品目录
     * @param page 分页对象,xml中可以从里面进行取值,传递参数 Page 即自动分页,必须放在第一位(你可以继承Page实现自己的分页对象)
     * @return 分页对象
     */
    IPage<PmsProductCategory> categoryListIPage(Page<PmsProductCategory> page, PmsProductCategoryQueryParam productCategoryQueryParam);
}
