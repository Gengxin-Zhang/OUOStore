package org.csu.ouostore.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.csu.ouostore.model.entity.PmsProductAttribute;
import org.csu.ouostore.model.query.PmsProductAttributeParam;
import org.csu.ouostore.model.vo.ProductAttrInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 商品属性(规格)参数表,一条记录对应一个规格/参数 服务类
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
@Transactional
public interface PmsProductAttributeService extends IService<PmsProductAttribute> {

    /**
     * 获取某个商品分类下所有属性信息
     * @param productCategoryId 商品分类id
     * @return 属性信息
     */
    List<ProductAttrInfo> getProductAttrInfo(Long productCategoryId);

    boolean create(PmsProductAttributeParam productAttributeParam);

    boolean delete(Long id);
}
