package org.csu.ouostore.service;

import org.csu.ouostore.model.entity.UmsMemberReceiveAddress;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 会员收货地址表 服务类
 * </p>
 *
 * @author zack
 * @since 2020-04-09
 */
public interface UmsMemberReceiveAddressService extends IService<UmsMemberReceiveAddress> {
    /**
     * 添加收货地址
     */
    int add(UmsMemberReceiveAddress address);

    /**
     * 删除收货地址
     * @param id 地址表的id
     */
    int delete(Long id);

    /**
     * 修改收货地址
     * @param id 地址表的id
     * @param address 修改的收货地址信息
     */
    int update(Long id, UmsMemberReceiveAddress address);

    /**
     * 返回当前用户的收货地址
     */
    List<UmsMemberReceiveAddress> list();

    /**
     * 获取地址详情
     * @param id 地址id
     */
    UmsMemberReceiveAddress getItem(Long id);
}
