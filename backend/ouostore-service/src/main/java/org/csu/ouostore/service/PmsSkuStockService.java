package org.csu.ouostore.service;

import org.csu.ouostore.model.entity.PmsSkuStock;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * sku(库存保有单位)的库存 服务类
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
@Transactional
public interface PmsSkuStockService extends IService<PmsSkuStock> {

    /**
     * 根据id删除sku并修改product的库存等信息
     */
    boolean delete(Long id);
}
