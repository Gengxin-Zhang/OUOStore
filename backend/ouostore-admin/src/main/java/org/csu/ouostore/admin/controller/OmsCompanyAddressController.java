package org.csu.ouostore.admin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.csu.ouostore.common.api.CommonResult;
import org.csu.ouostore.model.entity.OmsCompanyAddress;
import org.csu.ouostore.service.OmsCompanyAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 发货地址地址管理Controller
 * @author zack
 */
@RestController
@Api(tags = "发货地址管理")
@RequestMapping("/api/v1/company_addresses")
public class OmsCompanyAddressController {

    @Autowired
    private OmsCompanyAddressService companyAddressService;

    @ApiOperation("获取所有发货地址")
    @GetMapping(value = "")
    public CommonResult<List<OmsCompanyAddress>> list() {
        List<OmsCompanyAddress> companyAddressList = companyAddressService.list();
        return CommonResult.OK(companyAddressList);
    }

    @ApiOperation("添加发货地址")
    @PostMapping(value = "")
    public CommonResult<String> create(@RequestBody OmsCompanyAddress address) {
        boolean success = companyAddressService.save(address);
        return success ? CommonResult.OK("创建成功") : CommonResult.failed("创建失败,未知错误");
    }
}
