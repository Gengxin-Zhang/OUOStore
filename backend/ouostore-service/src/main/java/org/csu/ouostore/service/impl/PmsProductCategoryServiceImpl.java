package org.csu.ouostore.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.netty.util.internal.StringUtil;
import org.csu.ouostore.model.entity.PmsProductCategory;
import org.csu.ouostore.mapper.PmsProductCategoryMapper;
import org.csu.ouostore.model.query.PmsProductCategoryQueryParam;
import org.csu.ouostore.service.PmsProductCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<PmsProductCategory> categoryList() {
        List<PmsProductCategory> list = this.list();
        return list;
    }

    @Override
    public IPage<PmsProductCategory> categoryListIPage(Page<PmsProductCategory> page, PmsProductCategoryQueryParam queryParam) {
        //显示状态为1的目录
        QueryWrapper<PmsProductCategory> wrapper = new QueryWrapper<PmsProductCategory>()
                .eq(ObjectUtil.isNotNull(queryParam.getStatus()),"show_status",1);
        page.setCurrent(queryParam.getPage());
        page.setSize(queryParam.getPerPage());
        return page(page, wrapper);
    }
}
