package org.csu.ouostore.service.impl;

import org.csu.ouostore.model.entity.PmsSkuStock;
import org.csu.ouostore.mapper.PmsSkuStockMapper;
import org.csu.ouostore.service.PmsSkuStockService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

}
