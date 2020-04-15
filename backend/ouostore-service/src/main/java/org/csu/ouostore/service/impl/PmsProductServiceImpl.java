package org.csu.ouostore.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.netty.util.internal.StringUtil;
import org.csu.ouostore.common.exception.ApiException;
import org.csu.ouostore.model.entity.*;
import org.csu.ouostore.mapper.PmsProductMapper;
import org.csu.ouostore.model.query.PmsProductQueryParam;
import org.csu.ouostore.model.vo.PmsProductDetailVo;
import org.csu.ouostore.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品信息 服务实现类
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
@Service
public class PmsProductServiceImpl extends ServiceImpl<PmsProductMapper, PmsProduct> implements PmsProductService {

//    @Autowired
//    PmsProductAttributeCategoryService attributeCategoryService;

    @Autowired
    PmsProductAttributeValueService attributeValueService;

    @Autowired
    PmsSkuStockService stockService;

    @Autowired
    PmsProductAttributeService attributeService;

    @Override
    public IPage<PmsProduct> productListIpage(Page<PmsProduct> page, PmsProductQueryParam queryParam) {
        QueryWrapper<PmsProduct> wrapper = new QueryWrapper<PmsProduct>()
                .eq("publish_status",1)
                .gt("stock",0)
                .eq("product_category_id",queryParam.getProductCategoryId());
        page.setCurrent(queryParam.getPage());
        page.setSize(queryParam.getPerPage());
        return page(page, wrapper);
    }

    @Override
    public IPage<PmsProduct> select(Page<PmsProduct> page, PmsProductQueryParam queryParam) {
        QueryWrapper<PmsProduct> wrapper = new QueryWrapper<PmsProduct>()
                .eq("publish_status",1)
                .gt("stock",0);
        if (StrUtil.isNotBlank(queryParam.getProductKeyword())){
            wrapper.like("product_sn",queryParam.getProductSn());
        }
        if (StrUtil.isNotBlank(queryParam.getProductKeyword())){
            wrapper.like("name",queryParam.getProductKeyword());
            wrapper.like("description",queryParam.getProductKeyword());
            wrapper.like("keywords",queryParam.getProductKeyword());
        }
        page.setCurrent(queryParam.getPage());
        page.setSize(queryParam.getPerPage());
        return page(page, wrapper);
    }

    //进入商品详情页面
    @Override
    public PmsProductDetailVo detail(Long id) {
        PmsProductDetailVo detailVo = new PmsProductDetailVo();
        PmsProduct product = this.getOne(new QueryWrapper<PmsProduct>().eq("id", id).last("limit 1"));
        if (ObjectUtil.isNull(product)){
            throw new ApiException("商品不存在");
        }
        List<PmsProductAttributeValue> attributeValueList = attributeValueService.list(new QueryWrapper<PmsProductAttributeValue>().eq("product_id", id));
        detailVo.setProductAttributeValue(attributeValueList);
        //todo:商品详情
//        List<PmsProductAttribute> attributeList = attributeService.list(new QueryWrapper<PmsProductAttribute>().eq());
//        detailVo.setProductAttribute();
        List<PmsSkuStock> skuStockList = stockService.list(new QueryWrapper<PmsSkuStock>().eq("product_id",id));
        detailVo.setSkuStock(skuStockList);
        return detailVo;
    }
}
