package org.csu.ouostore.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.csu.ouostore.model.entity.OmsOrder;
import org.csu.ouostore.model.entity.PmsProduct;
import com.baomidou.mybatisplus.extension.service.IService;
import org.csu.ouostore.model.query.OmsOrderQueryParam;
import org.csu.ouostore.model.query.PmsProductQueryParam;
import org.csu.ouostore.model.vo.OmsOrderDetailVo;
import org.csu.ouostore.model.vo.PmsProductDetailVo;

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

   /**
    * 获取商品
    */
   IPage<PmsProduct> productListIpage(Page<PmsProduct> page,PmsProductQueryParam queryParam);

   /**
    * 分页模糊查询
    */
   IPage<PmsProduct> select(Page<PmsProduct> page, PmsProductQueryParam queryParam);

   /**
    * 获取商品详情
    */
   PmsProductDetailVo detail(Long id);

}
