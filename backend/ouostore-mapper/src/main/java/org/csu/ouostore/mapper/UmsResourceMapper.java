package org.csu.ouostore.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.csu.ouostore.model.entity.UmsResource;

/**
 * <p>
 * 后台资源表 Mapper 接口
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
public interface UmsResourceMapper extends BaseMapper<UmsResource> {

    /**
     * <p>
     * 查询 : 根据state状态查询用户列表，分页显示
     * </p>
     *
     * @param page 分页对象,xml中可以从里面进行取值,传递参数 Page 即自动分页,必须放在第一位(你可以继承Page实现自己的分页对象)
     * @param wrapper 条件构造器
     * @return 分页对象
     */
    IPage<UmsResource> selectPageVo(Page<?> page, @Param(Constants.WRAPPER) Wrapper<UmsResource> wrapper);
}
