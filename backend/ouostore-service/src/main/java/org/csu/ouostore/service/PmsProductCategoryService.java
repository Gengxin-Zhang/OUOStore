package org.csu.ouostore.service;

import org.csu.ouostore.model.entity.PmsProductCategory;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
