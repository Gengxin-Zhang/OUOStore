package org.csu.ouostore.service.impl;

import org.csu.ouostore.model.entity.PmsProductCategoryAttributeRelation;
import org.csu.ouostore.mapper.PmsProductCategoryAttributeRelationMapper;
import org.csu.ouostore.service.PmsProductCategoryAttributeRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 产品的分类和属性的关系表，用于设置分类筛选条件（只支持一级分类） 服务实现类
 * </p>
 *
 * @author zack
 * @since 2020-06-16
 */
@Service
public class PmsProductCategoryAttributeRelationServiceImpl extends ServiceImpl<PmsProductCategoryAttributeRelationMapper, PmsProductCategoryAttributeRelation> implements PmsProductCategoryAttributeRelationService {

}
