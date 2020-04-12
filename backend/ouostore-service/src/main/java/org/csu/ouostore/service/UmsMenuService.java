package org.csu.ouostore.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.csu.ouostore.model.entity.UmsMenu;
import org.csu.ouostore.model.query.UmsMenuCreateParam;
import org.csu.ouostore.model.vo.UmsMenuNode;

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
    List<UmsMenuNode> treeList();
}
