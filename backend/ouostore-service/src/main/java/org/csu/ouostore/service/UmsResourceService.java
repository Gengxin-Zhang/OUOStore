package org.csu.ouostore.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.csu.ouostore.model.entity.UmsResource;
import org.csu.ouostore.model.query.UmsResourceCreateParam;
import org.csu.ouostore.model.query.UmsResourceQueryParam;

import java.util.List;

/**
 * <p>
 * 后台资源表 服务类
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
public interface UmsResourceService extends IService<UmsResource> {

    /**
     * 查询全部资源
     */
    List<UmsResource> listAll();

    /**
     * 新增资源
     */
    boolean create(UmsResourceCreateParam umsResourceCreateParam);

    /**
     * <p>
     * 查询 : 根据param查询资源列表，分页显示
     * </p>
     *
     * @param page 分页对象,xml中可以从里面进行取值,传递参数 Page 即自动分页,必须放在第一位(你可以继承Page实现自己的分页对象)
     * @param resourceQueryParam 查询条件
     * @return 分页对象
     */
    IPage<UmsResource> selectResourcePage(Page<UmsResource> page, UmsResourceQueryParam resourceQueryParam);
}
