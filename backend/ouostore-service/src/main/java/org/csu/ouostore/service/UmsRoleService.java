package org.csu.ouostore.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.csu.ouostore.model.entity.UmsResource;
import org.csu.ouostore.model.entity.UmsRole;
import org.csu.ouostore.model.query.UmsRoleCreateParam;
import org.csu.ouostore.model.query.UmsRoleQueryParam;
import org.csu.ouostore.model.vo.UmsMenuNode;

import java.util.List;

/**
 * <p>
 * 后台用户角色表 服务类
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
public interface UmsRoleService extends IService<UmsRole> {

    /**
     * 添加角色
     */
    boolean create(UmsRoleCreateParam role);

    /**
     * <p>
     * 查询 : 根据state状态查询用户列表，分页显示
     * </p>
     *
     * @param page 分页对象,xml中可以从里面进行取值,传递参数 Page 即自动分页,必须放在第一位(你可以继承Page实现自己的分页对象)
     * @param roleQueryParam 查询条件
     * @return 分页对象
     */
    IPage<UmsRole> selectRolePage(Page<UmsRole> page, UmsRoleQueryParam roleQueryParam);

    /**
     * 获取角色相关资源
     */
    List<UmsResource> listResource(Long roleId);

    /**
     * 获取角色相关菜单
     */
    List<UmsMenuNode> listMenu(Long roleId);

    /**
     * 删除角色及相关数据
     */
    boolean delete(Long id);
}
