package org.csu.ouostore.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.csu.ouostore.common.exception.ApiException;
import org.csu.ouostore.mapper.UmsMenuMapper;
import org.csu.ouostore.model.entity.UmsMenu;
import org.csu.ouostore.model.query.UmsMenuCreateParam;
import org.csu.ouostore.model.vo.UmsMenuNode;
import org.csu.ouostore.service.UmsMenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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

    @Override
    public boolean create(UmsMenuCreateParam menuCreateParam) {
        UmsMenu menu = new UmsMenu();
        BeanUtil.copyProperties(menuCreateParam, menu);
        boolean parentExist = ObjectUtil.isNotNull(menuCreateParam.getParentId());
        //检查父级id是否正确
        if (parentExist) {
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
        if (ObjectUtil.isNull(one)) {
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
        if (ObjectUtil.isNull(one)) {
            throw new ApiException("已存在此url且父级url相同");
        }

        menu.setCreateTime(LocalDateTime.now());
        return this.save(menu);
    }

    @Override
    public List<UmsMenuNode> treeList() {
        List<UmsMenu> menuList = this.list();
        return menuList.stream()
                .filter(menu -> menu.getParentId().equals(0L))
                .map(menu -> covertMenuNode(menu, menuList)).collect(Collectors.toList());
    }

    /**
     * 将UmsMenu转化为UmsMenuNode并设置children属性
     */
    private UmsMenuNode covertMenuNode(UmsMenu menu, List<UmsMenu> menuList) {
        UmsMenuNode node = new UmsMenuNode();
        BeanUtils.copyProperties(menu, node);
        List<UmsMenuNode> children = menuList.stream()
                .filter(subMenu -> subMenu.getParentId().equals(menu.getId()))
                .map(subMenu -> covertMenuNode(subMenu, menuList)).collect(Collectors.toList());
        node.setChildren(children);
        return node;
    }

}
