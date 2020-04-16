package org.csu.ouostore.portal.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.csu.ouostore.common.api.CommonResult;
import org.csu.ouostore.model.entity.OmsCartItem;
import org.csu.ouostore.model.vo.OmsCartDetailVo;
import org.csu.ouostore.service.OmsCartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 商品目录
 */
@RestController
@Api(tags = "前台购物车展示")
@RequestMapping("/api/v1/member/{id}")
public class OmsCartController {

    @Autowired
    OmsCartItemService cartItemService;

    @ApiOperation("显示用户购物车列表")
    @GetMapping("/cart")
    public CommonResult<OmsCartDetailVo> detail(@PathVariable Long memberid){
        OmsCartDetailVo detailVo = cartItemService.detail(memberid);
        return CommonResult.OK(detailVo);
    }

//    @ApiOperation("删除购物车商品")
//    @DeleteMapping("/cart/{itemid}")
//    public CommonResult delete(@PathVariable Long memberId,@PathVariable Long itemid){
//
//    }

//    @ApiOperation("生成订单")
//    @PostMapping
}
