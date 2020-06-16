package org.csu.ouostore.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.csu.ouostore.common.exception.ApiException;
import org.csu.ouostore.mapper.PmsProductMapper;
import org.csu.ouostore.model.entity.PmsProduct;
import org.csu.ouostore.model.entity.PmsProductAttributeValue;
import org.csu.ouostore.model.entity.PmsSkuStock;
import org.csu.ouostore.model.query.PmsProductParam;
import org.csu.ouostore.model.query.PmsProductQueryParam;
import org.csu.ouostore.model.vo.PmsProductDetailVo;
import org.csu.ouostore.service.PmsProductAttributeService;
import org.csu.ouostore.service.PmsProductAttributeValueService;
import org.csu.ouostore.service.PmsProductService;
import org.csu.ouostore.service.PmsSkuStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品信息 服务实现类
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
@Service
@Slf4j
public class PmsProductServiceImpl extends ServiceImpl<PmsProductMapper, PmsProduct> implements PmsProductService {

//    @Autowired
//    PmsProductAttributeCategoryService attributeCategoryService;

    @Autowired
    PmsProductAttributeValueService attributeValueService;
    @Autowired
    PmsSkuStockService stockService;
    @Autowired
    PmsProductAttributeService attributeService;
    @Autowired
    PmsProductAttributeValueService productAttributeValueService;
    @Autowired
    PmsSkuStockService skuStockService;

    @Override
    public IPage<PmsProduct> productListIpage(Page<PmsProduct> page, PmsProductQueryParam queryParam) {
        QueryWrapper<PmsProduct> wrapper = new QueryWrapper<PmsProduct>()
                .eq("publish_status",1)
                .gt("stock", 0);

        //选择特定商品目录的商品
        if (!(queryParam.getProductCategoryId() == null)) {
            wrapper.eq("product_category_id", queryParam.getProductCategoryId());
        }
        page.setCurrent(queryParam.getPage());
        page.setSize(queryParam.getPerPage());
        return page(page, wrapper);
    }

    @Override
    public IPage<PmsProduct> select(Page<PmsProduct> page, PmsProductQueryParam queryParam) {
        QueryWrapper<PmsProduct> wrapper = new QueryWrapper<PmsProduct>()
                .eq("publish_status",1)
                .gt("stock",0);
        if (StrUtil.isNotEmpty(queryParam.getProductKeyword())) {
            wrapper.like("name",queryParam.getProductKeyword());
            //todo:关键字
//            wrapper.like("keywords",queryParam.getProductKeyword());
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

    @Override
    public boolean create(PmsProductParam productParam) {
        //创建商品
        productParam.setId(null);
        boolean success = this.save(productParam);
        if (!success) {
            throw new ApiException("创建失败");
        }
        Long productId = productParam.getId();
        //处理sku的编码
        handleSkuStockCode(productParam.getSkuStockList(),productId);
        //添加sku库存信息
        success = stockService.saveBatch(productParam.getSkuStockList());
        if (!success) {
            log.warn("创建产品出错!");
            throw new ApiException("创建产品出错!");
        }
        success = productAttributeValueService.saveBatch(productParam.getProductAttributeValueList());
        //添加商品参数,添加自定义商品规格
        if (!success) {
            log.warn("创建产品出错!");
            throw new ApiException("创建产品出错!");
        }
        return true;
    }

    @Override
    public boolean update1(Long id, PmsProductParam productParam) {
        //更新商品信息
        productParam.setId(id);
        this.updateById(productParam);
        //修改sku库存信息
        handleUpdateSkuStockList(id, productParam);
        //修改商品参数,添加自定义商品规格
        boolean success = productAttributeValueService.updateBatchById(productParam.getProductAttributeValueList());
        if (!success) {
            log.warn("更新sku出错!");
            throw new ApiException("更新sku出错!");
        }
        return true;
    }

    /**
     * 更新sku信息
     */
    private void handleUpdateSkuStockList(Long id, PmsProductParam productParam) {
        //当前的sku信息
        List<PmsSkuStock> currSkuList = productParam.getSkuStockList();
        //当前没有sku直接删除
        if (CollUtil.isEmpty(currSkuList)) {
            skuStockService.remove(new QueryWrapper<PmsSkuStock>().eq("product_id", id));
            return;
        }
        //获取初始sku信息
        List<PmsSkuStock> oriStuList = skuStockService.list(
                new QueryWrapper<PmsSkuStock>().eq("product_id", id));
        //获取新增sku信息
        List<PmsSkuStock> insertSkuList = currSkuList.stream().filter(item -> item.getId() == null).collect(Collectors.toList());
        //获取需要更新的sku信息
        List<PmsSkuStock> updateSkuList = currSkuList.stream().filter(item -> item.getId() != null).collect(Collectors.toList());
        List<Long> updateSkuIds = updateSkuList.stream().map(PmsSkuStock::getId).collect(Collectors.toList());
        //获取需要删除的sku信息
        List<PmsSkuStock> removeSkuList = oriStuList.stream().filter(item -> !updateSkuIds.contains(item.getId())).collect(Collectors.toList());
        handleSkuStockCode(insertSkuList, id);
        handleSkuStockCode(updateSkuList, id);
        //新增sku
        if (CollUtil.isNotEmpty(insertSkuList)) {
            boolean success = stockService.saveBatch(productParam.getSkuStockList());
            if (!success) {
                log.warn("创建sku出错!");
                throw new ApiException("创建sku出错!");
            }
        }
        //删除sku
        if (CollUtil.isNotEmpty(removeSkuList)) {
            List<Long> removeSkuIds = removeSkuList.stream().map(PmsSkuStock::getId).collect(Collectors.toList());
            skuStockService.removeByIds(removeSkuIds);
        }
        //修改sku
        if (CollUtil.isNotEmpty(updateSkuList)) {
            for (PmsSkuStock pmsSkuStock : updateSkuList) {
                skuStockService.updateById(pmsSkuStock);
            }
        }
    }

    /**
     * 生成sku_code
     */
    private void handleSkuStockCode(List<PmsSkuStock> skuStockList, Long productId) {
        if(CollectionUtils.isEmpty(skuStockList)) {
            return;
        }
        for (int i = 0; i < skuStockList.size(); i++) {
            PmsSkuStock skuStock = skuStockList.get(i);
            if (StringUtils.isEmpty(skuStock.getSkuCode())) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                StringBuilder sb = new StringBuilder();
                //日期
                sb.append(sdf.format(new Date()));
                //四位商品id
                sb.append(String.format("%04d", productId));
                //三位索引id
                sb.append(String.format("%03d", i + 1));
                skuStock.setSkuCode(sb.toString());
            }
        }
    }

}
