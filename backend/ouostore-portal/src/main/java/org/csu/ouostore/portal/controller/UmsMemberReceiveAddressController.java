package org.csu.ouostore.portal.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.csu.ouostore.common.api.CommonResult;
import org.csu.ouostore.model.entity.UmsMemberReceiveAddress;
import org.csu.ouostore.service.UmsMemberReceiveAddressService;
import org.csu.ouostore.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 会员收货地址管理
 */
@RestController
@Api(tags = "会员收货地址管理")
@RequestMapping("/api/v1/members/addresses")
public class UmsMemberReceiveAddressController {

    @Autowired
    private UmsMemberReceiveAddressService memberReceiveAddressService;
    @Autowired
    private UmsMemberService memberService;

    @ApiOperation("添加收货地址")
    @PostMapping("")
    public CommonResult<String> add(@RequestBody UmsMemberReceiveAddress address) {
        address.setMemberId(memberService.getCurrentMember().getId());
        boolean success = memberReceiveAddressService.save(address);
        return success ? CommonResult.OK("添加成功") : CommonResult.failed("添加失败,未知错误");
    }

    @ApiOperation("删除收货地址")
    @DeleteMapping("{addressId}")
    public CommonResult<String> delete(@PathVariable Long addressId) {
        boolean success = memberReceiveAddressService.delete(addressId);
        return success ? CommonResult.OK("删除成功") : CommonResult.failed("删除失败,未知错误");
    }

    @ApiOperation("修改收货地址")
    @PutMapping(value = "/{addressId}")
    public CommonResult<String> update(@PathVariable Long addressId, @RequestBody UmsMemberReceiveAddress address) {
        boolean success = memberReceiveAddressService.update(addressId, address);
        return success ? CommonResult.OK("修改成功") : CommonResult.failed("修改失败,未知错误");
    }

    @ApiOperation("获取本人所有收货地址")
    @GetMapping(value = "")
    public CommonResult<List<UmsMemberReceiveAddress>> list() {
        List<UmsMemberReceiveAddress> addressList = memberReceiveAddressService.list();
        return CommonResult.OK(addressList);
    }

    @ApiOperation("根据id获取收货地址详情")
    @GetMapping(value = "/{addressId}")
    public CommonResult<UmsMemberReceiveAddress> getItem(@PathVariable Long addressId) {
        UmsMemberReceiveAddress address = memberReceiveAddressService.getById(addressId);
        return CommonResult.OK(address);
    }
}
