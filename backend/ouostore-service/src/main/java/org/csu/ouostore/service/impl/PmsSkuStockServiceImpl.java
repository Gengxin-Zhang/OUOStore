package org.csu.ouostore.service.impl;

import org.csu.ouostore.common.exception.ApiException;
import org.csu.ouostore.model.entity.PmsProduct;
import org.csu.ouostore.model.entity.PmsSkuStock;
import org.csu.ouostore.mapper.PmsSkuStockMapper;
import org.csu.ouostore.service.PmsProductService;
import org.csu.ouostore.service.PmsSkuStockService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * sku(库存保有单位)的库存 服务实现类
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
@Service
public class PmsSkuStockServiceImpl extends ServiceImpl<PmsSkuStockMapper, PmsSkuStock> implements PmsSkuStockService {

    @Autowired
    PmsProductService productService;

    @Override
    public boolean delete(Long id) {
        PmsSkuStock skuStock = this.getById(id);
        Long productId = skuStock.getProductId();
        Integer stock = skuStock.getStock();
        //删除sku信息
        boolean success = this.removeById(id);
        if (!success) {
            throw new ApiException("删除失败");
        }
        PmsProduct product = productService.getById(productId);
        product.setStock(product.getStock() - stock);
        success = productService.updateById(product);
        return success;
    }
}
