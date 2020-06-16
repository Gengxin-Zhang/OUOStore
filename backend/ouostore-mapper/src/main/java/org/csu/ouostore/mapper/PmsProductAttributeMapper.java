package org.csu.ouostore.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.csu.ouostore.model.entity.PmsProductAttribute;
import org.csu.ouostore.model.vo.ProductAttrInfo;

import java.util.List;

/**
 * <p>
 * 商品属性(规格)参数表,一条记录对应一个规格/参数 Mapper 接口
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
public interface PmsProductAttributeMapper extends BaseMapper<PmsProductAttribute> {

    /**
     * 获取商品属性信息
     */
    List<ProductAttrInfo> getProductAttrInfo(@Param("id") Long productCategoryId);
}
