package org.csu.ouostore.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.csu.ouostore.common.exception.ApiException;
import org.csu.ouostore.mapper.UmsMenuMapper;
import org.csu.ouostore.model.entity.UmsMenu;
import org.csu.ouostore.model.entity.UmsRoleMenuRelation;
import org.csu.ouostore.model.query.UmsMenuCreateParam;
import org.csu.ouostore.model.query.UmsMenuQueryParam;
import org.csu.ouostore.model.vo.UmsMenuNodeVo;
import org.csu.ouostore.service.UmsMenuService;
import org.csu.ouostore.service.UmsRoleMenuRelationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 后台菜单表 服务实现类
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
@Service
public class UmsMenuServiceImpl extends ServiceImpl<UmsMenuMapper, UmsMenu> implements UmsMenuService {

    @Autowired
    UmsMenuMapper menuMapper;
    @Autowired
    UmsRoleMenuRelationService roleMenuRelationService;

    @Override
    public boolean create(UmsMenuCreateParam menuCreateParam) {
        UmsMenu menu = new UmsMenu();
        BeanUtil.copyProperties(menuCreateParam, menu);
        boolean parentExist = ObjectUtil.isNotNull(menuCreateParam.getParentId());
        //检查父级id是否正确
        if (parentExist && menuCreateParam.getParentId() != 0) {
            UmsMenu parent = this.getOne(new QueryWrapper<UmsMenu>().eq("id", menuCreateParam.getParentId()));
            if (ObjectUtil.isNull(parent)) {
                throw new ApiException("父级id不存在!");
            }
            menu.setLevel(parent.getLevel() + 1);
        } else {
            menu.setLevel(0);
        }
        //检查title是否重复
        UmsMenu one = this.getOne(new QueryWrapper<UmsMenu>().eq("title", menuCreateParam.getTitle()).last("LIMIT 1"));
        if (ObjectUtil.isNotNull(one)) {
            throw new ApiException("title重复");
        }
        //检查url是否重复
        QueryWrapper<UmsMenu> wrapper = new QueryWrapper<UmsMenu>()
                .eq("name", menuCreateParam.getName())
                .last("LIMIT 1");
        if (parentExist) { //存在父级
            wrapper.eq("parent_id", menuCreateParam.getParentId());
        }
        one = this.getOne(wrapper);
        if (ObjectUtil.isNotNull(one)) {
            throw new ApiException("已存在此url且父级url相同");
        }

        menu.setCreateTime(LocalDateTime.now());
        return this.save(menu);
    }

    @Override
    public IPage<UmsMenu> selectMenuPage(Page<UmsMenu> page, UmsMenuQueryParam menuQueryParam) {
        QueryWrapper<UmsMenu> wrapper = new QueryWrapper<UmsMenu>().eq("parent_id", menuQueryParam.getParentId());
        page.setCurrent(menuQueryParam.getPage());
        page.setSize(menuQueryParam.getPerPage());
        menuMapper.selectPageVo(page, wrapper);
        return page;
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        this.removeById(id);
        roleMenuRelationService.remove(new UpdateWrapper<UmsRoleMenuRelation>().eq("menu_id", id));
        //递归删除
        List<UmsMenu> menuList = this.list(new QueryWrapper<UmsMenu>().select("id").eq("parent_id", id));
        while (menuList.size() != 0) {
            Set<Long> ids = menuList.stream().map(UmsMenu::getId).collect(Collectors.toSet());
            this.removeByIds(ids);
            for (Long i : ids) {
                roleMenuRelationService.remove(new UpdateWrapper<UmsRoleMenuRelation>().eq("menu_id", i));
            }
            ArrayList<UmsMenu> toRemove = new ArrayList<>();
            for (UmsMenu menu : menuList) {
                toRemove.addAll(this.list(new QueryWrapper<UmsMenu>().select("id").eq("parent_id", menu.getId())));
            }
            menuList = toRemove;
        }
        return true;
    }

    @Override
    public List<UmsMenuNodeVo> treeList() {
        List<UmsMenu> menuList = this.list();
        return menuList.stream()
                .filter(menu -> menu.getParentId().equals(0L))
                .map(menu -> covertMenuNode(menu, menuList)).collect(Collectors.toList());
    }

    /**
     * 将UmsMenu转化为UmsMenuNode并设置children属性
     */
    public static UmsMenuNodeVo covertMenuNode(UmsMenu menu, List<UmsMenu> menuList) {
        UmsMenuNodeVo node = new UmsMenuNodeVo();
        BeanUtils.copyProperties(menu, node);
        List<UmsMenuNodeVo> children = menuList.stream()
                .filter(subMenu -> subMenu.getParentId().equals(menu.getId()))
                .map(subMenu -> covertMenuNode(subMenu, menuList)).collect(Collectors.toList());
        node.setChildren(children);
        return node;
    }

}
