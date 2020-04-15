package org.csu.ouostore.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.csu.ouostore.model.entity.UmsMenu;
import org.csu.ouostore.model.query.UmsMenuCreateParam;
import org.csu.ouostore.model.query.UmsMenuQueryParam;
import org.csu.ouostore.model.vo.UmsMenuNodeVo;

import java.util.List;

/**
 * <p>
 * 后台菜单表 服务类
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
public interface UmsMenuService extends IService<UmsMenu> {

    /**
     * 创建后台菜单
     */
    boolean create(UmsMenuCreateParam menuCreateParam);

    /**
     * 树形结构返回所有菜单列表
     */
    List<UmsMenuNodeVo> treeList();

    /**
     * <p>
     * 查询 : 根据param查询menu列表，分页显示
     * </p>
     *
     * @param page 分页对象,xml中可以从里面进行取值,传递参数 Page 即自动分页,必须放在第一位(你可以继承Page实现自己的分页对象)
     * @param menuQueryParam 查询条件
     * @return 分页对象
     */
    IPage<UmsMenu> selectMenuPage(Page<UmsMenu> page, UmsMenuQueryParam menuQueryParam);

    /**
     * 根据menuId删除menu及相关数据
     */
    boolean delete(Long id);
}
