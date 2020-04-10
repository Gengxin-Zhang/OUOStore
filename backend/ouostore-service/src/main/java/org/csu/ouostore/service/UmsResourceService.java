package org.csu.ouostore.service;

import org.csu.ouostore.model.entity.UmsResource;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
