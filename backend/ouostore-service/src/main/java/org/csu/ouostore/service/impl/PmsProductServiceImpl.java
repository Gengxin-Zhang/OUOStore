package org.csu.ouostore.service.impl;

import org.csu.ouostore.model.entity.PmsProduct;
import org.csu.ouostore.mapper.PmsProductMapper;
import org.csu.ouostore.service.PmsProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
    @Override
    public List<PmsProduct> productList() {
        List<PmsProduct> list = this.list();
        return list;
    }
}
