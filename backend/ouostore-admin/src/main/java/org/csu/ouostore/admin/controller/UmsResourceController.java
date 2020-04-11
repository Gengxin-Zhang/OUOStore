package org.csu.ouostore.admin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.csu.ouostore.common.api.CommonResult;
import org.csu.ouostore.model.query.UmsResourceCreateParam;
import org.csu.ouostore.security.component.DynamicSecurityMetadataSource;
import org.csu.ouostore.service.UmsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 后台资源管理
 */
@Api(tags = "后台资源管理")
@RestController
@RequestMapping("/api/v1/resources")
public class UmsResourceController {

    @Autowired
    private UmsResourceService resourceService;
    @Autowired
    private DynamicSecurityMetadataSource dynamicSecurityMetadataSource;

    @ApiOperation("新增一个后台资源")
    @PostMapping("")
    public CommonResult create(@RequestBody @Validated UmsResourceCreateParam umsResourceCreateParam) {
        boolean success = resourceService.create(umsResourceCreateParam);
        //清除资源缓存,下次权限控制时重新加载
        dynamicSecurityMetadataSource.clearDataSource();
        return success ? CommonResult.success(null, "创建成功") : CommonResult.failed("创建失败,未知错误");
    }
}
