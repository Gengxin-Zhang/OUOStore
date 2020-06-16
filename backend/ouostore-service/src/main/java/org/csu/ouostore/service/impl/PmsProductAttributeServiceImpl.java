package org.csu.ouostore.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.csu.ouostore.common.exception.ApiException;
import org.csu.ouostore.mapper.PmsProductAttributeMapper;
import org.csu.ouostore.model.entity.PmsProductAttribute;
import org.csu.ouostore.model.entity.PmsProductAttributeCategory;
import org.csu.ouostore.model.query.PmsProductAttributeParam;
import org.csu.ouostore.model.vo.ProductAttrInfo;
import org.csu.ouostore.service.PmsProductAttributeCategoryService;
import org.csu.ouostore.service.PmsProductAttributeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品属性(规格)参数表,一条记录对应一个规格/参数 服务实现类
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
@Service
public class PmsProductAttributeServiceImpl extends ServiceImpl<PmsProductAttributeMapper, PmsProductAttribute> implements PmsProductAttributeService {

    @Autowired
    private PmsProductAttributeCategoryService productAttributeCategoryService;

    @Override
    public List<ProductAttrInfo> getProductAttrInfo(Long productCategoryId) {
        return this.baseMapper.getProductAttrInfo(productCategoryId);
    }

    @Override
    public boolean create(PmsProductAttributeParam productAttributeParam) {
        PmsProductAttributeCategory pmsProductAttributeCategory = productAttributeCategoryService.getById(productAttributeParam.getProductAttributeCategoryId());
        if (pmsProductAttributeCategory == null) {
            throw new ApiException("product_attr_category不存在");
        }
        PmsProductAttribute pmsProductAttribute = new PmsProductAttribute();
        BeanUtils.copyProperties(productAttributeParam, pmsProductAttribute);
        boolean success = this.save(pmsProductAttribute);
        if (!success) {
            throw new ApiException("创建失败");
        }
        //新增商品属性以后需要更新商品属性分类数量
        if (pmsProductAttribute.getType() == 0) {
            pmsProductAttributeCategory.setAttributeCount(pmsProductAttributeCategory.getAttributeCount() + 1);
        } else if (pmsProductAttribute.getType() == 1) {
            pmsProductAttributeCategory.setParamCount(pmsProductAttributeCategory.getParamCount() + 1);
        }
        productAttributeCategoryService.updateById(pmsProductAttributeCategory);
        return true;
    }

    @Override
    public boolean delete(Long id) {
        //获取分类
        PmsProductAttribute pmsProductAttribute = this.getById(id);
        Integer type = pmsProductAttribute.getType();
        PmsProductAttributeCategory pmsProductAttributeCategory = productAttributeCategoryService.getById(
                pmsProductAttribute.getProductAttributeCategoryId());
        boolean success = this.removeById(id);
        if (!success) {
            throw new ApiException("删除失败");
        }
        //删除完成后修改数量
        if (type == 0) {
            if (pmsProductAttributeCategory.getAttributeCount() >= 1) {
                pmsProductAttributeCategory.setAttributeCount(pmsProductAttributeCategory.getAttributeCount() - 1);
            } else {
                pmsProductAttributeCategory.setAttributeCount(0);
            }
        } else if (type == 1) {
            if (pmsProductAttributeCategory.getParamCount() >= 1) {
                pmsProductAttributeCategory.setParamCount(pmsProductAttributeCategory.getParamCount() - 1);
            } else {
                pmsProductAttributeCategory.setParamCount(0);
            }
        }
        productAttributeCategoryService.updateById(pmsProductAttributeCategory);
        return true;
    }
}
