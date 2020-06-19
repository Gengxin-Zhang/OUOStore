package org.csu.ouostore.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.csu.ouostore.common.exception.ApiException;
import org.csu.ouostore.model.entity.UmsMember;
import org.csu.ouostore.model.entity.UmsMemberReceiveAddress;
import org.csu.ouostore.mapper.UmsMemberReceiveAddressMapper;
import org.csu.ouostore.service.UmsMemberReceiveAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.csu.ouostore.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 会员收货地址表 服务实现类
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
@Service
public class UmsMemberReceiveAddressServiceImpl extends ServiceImpl<UmsMemberReceiveAddressMapper, UmsMemberReceiveAddress> implements UmsMemberReceiveAddressService {

    @Autowired
    private UmsMemberService memberService;
    @Autowired
    private UmsMemberReceiveAddressService memberReceiveAddressService;
//    @Autowired
//    private UmsMemberReceiveAddressMapper addressMapper;

    @Override
    public List<UmsMemberReceiveAddress> list(){
        UmsMember currentMember = memberService.getCurrentMember();
        return memberReceiveAddressService.list(
                new QueryWrapper<UmsMemberReceiveAddress>()
                        .eq("member_id", currentMember.getId()));
    }

    @Override
    public int add(UmsMemberReceiveAddress address) {
        UmsMember currentMember = memberService.getCurrentMember();
        address.setMemberId(currentMember.getId());
//        return addressMapper.insert(address);
        return 0;
    }

    @Override
    public boolean delete(Long id) {
        UmsMemberReceiveAddress address = memberReceiveAddressService.getById(id);
        if (!address.getMemberId().equals(memberService.getCurrentMember().getId())) {
            throw new ApiException("权限不足");
        }
        return this.removeById(id);
    }

    @Override
    public boolean update(Long id, UmsMemberReceiveAddress address) {
        UmsMemberReceiveAddress existAddress = memberReceiveAddressService.getById(id);
        if (!existAddress.getMemberId().equals(memberService.getCurrentMember().getId())) {
            throw new ApiException("权限不足");
        }
        address.setMemberId(id);
        return this.updateById(address);
    }

    @Override
    public UmsMemberReceiveAddress getItem(Long id) {
        return null;
    }
}
