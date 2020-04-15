package org.csu.ouostore.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.csu.ouostore.common.exception.ApiException;
import org.csu.ouostore.model.entity.*;
import org.csu.ouostore.mapper.OmsCartItemMapper;
import org.csu.ouostore.model.vo.OmsCartDetailVo;
import org.csu.ouostore.model.vo.OmsOrderDetailVo;
import org.csu.ouostore.service.OmsCartItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.csu.ouostore.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.Oneway;
import java.util.List;

/**
 * <p>
 * 购物车表 服务实现类
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
@Service
public class OmsCartItemServiceImpl extends ServiceImpl<OmsCartItemMapper, OmsCartItem> implements OmsCartItemService {

    @Autowired
    UmsMemberService memberService;

    @Override
    public OmsCartDetailVo detail(Long memberId) {
        OmsCartDetailVo detailVo = new OmsCartDetailVo();
        UmsMember member = memberService.getById(memberId);
        if (ObjectUtil.isNull(member)){
            throw new ApiException("会员不存在");
        }
        List<OmsCartItem> cartItems = this.list(new QueryWrapper<OmsCartItem>().eq("member_id", memberId));
        detailVo.setCartItemList(cartItems);
        return detailVo;
    }


}
