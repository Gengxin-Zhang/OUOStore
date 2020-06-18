package org.csu.ouostore.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.csu.ouostore.common.exception.ApiException;
import org.csu.ouostore.mapper.OmsCartItemMapper;
import org.csu.ouostore.model.entity.OmsCartItem;
import org.csu.ouostore.model.entity.PmsProduct;
import org.csu.ouostore.model.entity.PmsSkuStock;
import org.csu.ouostore.model.entity.UmsMember;
import org.csu.ouostore.model.query.OmsCartAddParam;
import org.csu.ouostore.model.vo.OmsCartDetailVo;
import org.csu.ouostore.service.OmsCartItemService;
import org.csu.ouostore.service.PmsProductService;
import org.csu.ouostore.service.PmsSkuStockService;
import org.csu.ouostore.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    @Autowired
    PmsProductService productService;
    @Autowired
    PmsSkuStockService skuStockService;

    @Override
    public OmsCartDetailVo detail(Long memberId) {
        OmsCartDetailVo detailVo = new OmsCartDetailVo();
        UmsMember member = memberService.getById(memberId);
        if (ObjectUtil.isNull(member)) {
            throw new ApiException("会员不存在");
        }
        List<OmsCartItem> cartItems = this.list(new QueryWrapper<OmsCartItem>().eq("member_id", memberId));
        detailVo.setCartItemList(cartItems);
        return detailVo;
    }

    @Override
    public boolean add(OmsCartAddParam param) {
        UmsMember currentMember = memberService.getCurrentMember();
        PmsSkuStock skuStock = skuStockService.getById(param.getProductSkuId());
        if (skuStock.getStock() < param.getQuantity()) {
            throw new ApiException("库存不足");
        }
        Long productId = skuStock.getProductId();
        PmsProduct product = productService.getById(productId);
        //查询数据库是否已有此item
        QueryWrapper<OmsCartItem> wrapper = new QueryWrapper<OmsCartItem>()
                .eq("member_id", currentMember.getId())
                .eq("product_id", productId)
                .eq("delete_status", 0)
                .eq("product_sku_id", param.getProductSkuId());
        OmsCartItem existCartItem = this.list(wrapper).get(0);
        if (existCartItem == null) {
            OmsCartItem cartItem = new OmsCartItem();
            cartItem.setCreateDate(LocalDateTime.now());
            cartItem.setProductId(productId);
            cartItem.setProductSkuId(param.getProductSkuId());
            cartItem.setMemberId(currentMember.getId());
            cartItem.setQuantity(param.getQuantity());
            cartItem.setPrice(skuStock.getPrice().multiply(new BigDecimal(cartItem.getQuantity())));
            cartItem.setProductPic(product.getPic());
            cartItem.setProductName(product.getName());
            cartItem.setProductSkuCode(skuStock.getSkuCode());
            cartItem.setCreateDate(LocalDateTime.now());
            cartItem.setModifyDate(cartItem.getCreateDate());
            cartItem.setDeleteStatus(0);
            cartItem.setProductCategoryId(product.getProductCategoryId());
            cartItem.setProductSn(product.getProductSn());
            cartItem.setProductAttr(skuStock.getSpData());
            this.save(cartItem);
        } else {
            existCartItem.setModifyDate(LocalDateTime.now());
            existCartItem.setQuantity(existCartItem.getQuantity() + param.getQuantity());
            existCartItem.setPrice(skuStock.getPrice().multiply(new BigDecimal(existCartItem.getQuantity())));
            this.updateById(existCartItem);
        }
        return true;
    }

    @Override
    public boolean updateQuantity(Long cartItemId, Long memberId, Integer quantity) {
        OmsCartItem cartItem = this.getById(cartItemId);
        if (cartItem == null) {
            throw new ApiException("购物车内无此商品");
        }
        if (!memberId.equals(cartItem.getMemberId())) {
            throw new ApiException("没有权限");
        }
        PmsSkuStock skuStock = skuStockService.getById(cartItem.getProductSkuId());
        cartItem.setModifyDate(LocalDateTime.now());
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        cartItem.setPrice(skuStock.getPrice().multiply(new BigDecimal(cartItem.getQuantity())));
        this.updateById(cartItem);
        return true;
    }

    @Override
    public boolean updateAttr(Long cartItemId, Long skuId) {
        OmsCartItem cartItem = this.getById(cartItemId);
        if (cartItem == null) {
            throw new ApiException("此购物车商品不存在");
        }
        PmsSkuStock stock = skuStockService.getById(skuId);
        if (stock == null || !stock.getProductId().equals(cartItem.getProductId())) {
            throw new ApiException("skuId非法,请求失败");
        }
        if (stock.getStock() < cartItem.getQuantity()) {
            throw new ApiException("库存不足!");
        }
        cartItem.setProductSkuId(skuId);
        cartItem.setProductSkuCode(stock.getSkuCode());
        cartItem.setModifyDate(LocalDateTime.now());
        cartItem.setProductAttr(stock.getSpData());
        //重新计算价格
        cartItem.setPrice(stock.getPrice().multiply(new BigDecimal(cartItem.getQuantity())));
        return true;
    }


}
