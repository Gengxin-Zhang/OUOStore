package org.csu.ouostore.service.impl;

import org.csu.ouostore.model.entity.PmsProductAttribute;
import org.csu.ouostore.mapper.PmsProductAttributeMapper;
import org.csu.ouostore.service.PmsProductAttributeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
