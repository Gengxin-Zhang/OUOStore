package org.csu.ouostore.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.csu.ouostore.common.api.CommonResult;
import org.csu.ouostore.model.entity.UmsResource;
import org.csu.ouostore.model.query.UmsResourceCreateParam;
import org.csu.ouostore.model.query.UmsResourceQueryParam;
import org.csu.ouostore.security.component.DynamicSecurityMetadataSource;
import org.csu.ouostore.service.UmsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


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
        return success ? CommonResult.OK(null, "创建成功") : CommonResult.failed("创建失败,未知错误");
    }

    @ApiOperation("修改后台资源")
    @PutMapping("/{id}")
    public CommonResult update(@PathVariable Long id, @RequestBody @Validated UmsResourceCreateParam umsResourceCreateParam) {
        UmsResource resource = new UmsResource();
        BeanUtil.copyProperties(umsResourceCreateParam, resource);
        resource.setId(id);
        boolean success = resourceService.updateById(resource);
        dynamicSecurityMetadataSource.clearDataSource();
        return success ? CommonResult.OK(null, "更新成功") : CommonResult.failed("更新失败,未知错误");
    }

    @ApiOperation("根据ID删除后台资源")
    @DeleteMapping("/{id}")
    public CommonResult delete(@PathVariable Long id) {
        boolean success = resourceService.removeById(id);
        return success ? CommonResult.OK(null, "删除成功") : CommonResult.failed("删除失败,未知错误");
    }

    @ApiOperation("根据ID获取资源详情")
    @GetMapping("/{id}")
    public CommonResult<UmsResource> getItem(@PathVariable Long id) {
        UmsResource resource = resourceService.getById(id);
        return CommonResult.OK(resource);
    }

    @ApiOperation("分页模糊查询资源详情")
    @GetMapping("")
    public CommonResult<Page<UmsResource>> getItems(@Validated UmsResourceQueryParam umsResourceQueryParam) {
        Page<UmsResource> resourcePage = new Page<>();
        resourceService.selectResourcePage(resourcePage, umsResourceQueryParam);
        return CommonResult.OK(resourcePage);
    }
}
