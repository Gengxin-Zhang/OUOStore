package org.csu.ouostore.portal.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.csu.ouostore.common.api.CommonResult;
import org.csu.ouostore.common.exception.ApiException;
import org.csu.ouostore.model.entity.OmsCartItem;
import org.csu.ouostore.model.query.OmsCartAddParam;
import org.csu.ouostore.service.OmsCartItemService;
import org.csu.ouostore.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品目录
 * @author tutu zack
 */
@RestController
@Api(tags = "购物车管理")
@RequestMapping("/api/v1/cart_items")
public class OmsCartController {

    @Autowired
    OmsCartItemService cartItemService;
    @Autowired
    UmsMemberService memberService;

    @ApiOperation("获取购物车列表")
    @GetMapping("")
    public CommonResult<List<OmsCartItem>> detail(){
        List<OmsCartItem> cartItems = cartItemService.list(new QueryWrapper<OmsCartItem>()
                .eq("member_id", memberService.getCurrentMember().getId()));
        return CommonResult.OK(cartItems);
    }

    @ApiOperation("添加商品到购物车,有则增加数量无则创建")
    @PostMapping("")
    public CommonResult<String> add(@RequestBody OmsCartAddParam param) {
        boolean success = cartItemService.add(param);
        return success ? CommonResult.OK("添加成功") : CommonResult.failed("添加失败,未知错误");
    }

    @ApiOperation("修改购物车中某个商品的数量")
    @PatchMapping("/quantities/{cartItemId}")
    public CommonResult<String> updateQuantity(@RequestParam Long cartItemId, @RequestParam Integer quantity) {
        boolean success = cartItemService.updateQuantity(cartItemId, memberService.getCurrentMember().getId(), quantity);
        return success ? CommonResult.OK("修改成功") : CommonResult.failed("修改失败,未知错误");
    }

    @ApiOperation("修改购物车中商品的规格")
    @PatchMapping("/attrs/{cartItemId}")
    public CommonResult<String> updateAttr(@PathVariable Long cartItemId, @RequestParam Long skuId) {
        boolean success = cartItemService.updateAttr(cartItemId, skuId);
        return success ? CommonResult.OK("修改成功") : CommonResult.failed("修改失败,未知错误");
    }

    @ApiOperation("删除购物车中的某个商品")
    @DeleteMapping("/{cartItemId}")
    public CommonResult<String> delete(@PathVariable Long cartItemId) {
        OmsCartItem cartItem = cartItemService.getById(cartItemId);
        if (cartItem == null) {
            throw new ApiException("商品不存在");
        }
        if (!cartItem.getMemberId().equals(memberService.getCurrentMember().getId())) {
            throw new ApiException("权限不足");
        }
        boolean success = cartItemService.removeById(cartItemId);
        return success ? CommonResult.OK("删除成功") : CommonResult.failed("删除失败,未知错误");
    }

    @ApiOperation("清空购物车")
    @DeleteMapping(value = "/all")
    public CommonResult<String> clear() {
        List<OmsCartItem> cartItems = cartItemService.list(new QueryWrapper<OmsCartItem>()
                .eq("member_id", memberService.getCurrentMember().getId()));
        for (OmsCartItem cartItem : cartItems) {
            cartItem.setDeleteStatus(1);
        }
        boolean success = cartItemService.updateBatchById(cartItems);
        return success ? CommonResult.OK("删除成功") : CommonResult.failed("删除失败,未知错误");
    }
}
