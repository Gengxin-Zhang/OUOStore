package org.csu.ouostore.service;

import org.csu.ouostore.model.entity.PmsProduct;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品信息 服务类
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
public interface PmsProductService extends IService<PmsProduct> {
   List<PmsProduct> productList();



}
