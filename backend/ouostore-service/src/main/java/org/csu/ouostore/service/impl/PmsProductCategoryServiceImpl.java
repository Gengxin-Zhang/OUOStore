package org.csu.ouostore.service.impl;

import org.csu.ouostore.model.entity.PmsProductCategory;
import org.csu.ouostore.mapper.PmsProductCategoryMapper;
import org.csu.ouostore.service.PmsProductCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 宠物一级分类,猫/狗/兔子... 服务实现类
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
@Service
public class PmsProductCategoryServiceImpl extends ServiceImpl<PmsProductCategoryMapper, PmsProductCategory> implements PmsProductCategoryService {

}
