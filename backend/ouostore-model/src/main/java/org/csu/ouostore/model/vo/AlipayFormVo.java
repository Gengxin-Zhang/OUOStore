package org.csu.ouostore.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 支付宝支付需要请求的表单
 * @author: Guanyu-Cai
 * @Date: 04/16/2020
 */
@ApiModel
@Data
public class AlipayFormVo {

    @ApiModelProperty(value = "请求支付的表单和脚本", example = "<form name=\"punchout_form\" method=\"post\" action=\"https://openapi.alipaydev.com/gateway.do?charset=utf-8&method=alipay.trade.page.pay&sign=X5U46aZhbhMdPl%2BaGyA8cP4hekIP5gU3cfmV80Ei7HlTyVH5U09B5SFE9KVUGiCfQcPJb3dSdlZoOpImbFkjXiFyiDfx4J022G2Y6F7gMk%2FdhqwAq%2FVcxJNsCxlC8fK4kW%2B6uks015TOXs0MS%2FhcaUAoZrTs5JvfVV6qjt%2BXBdjzA548zlxjBBrAI%2BKZRRvS%2FYXLXh%2FIq%2FesO9pqClN9BMbf3f%2BDvAFIQlLZEB64ieUz3TP7A1pDxoGrZSugUUSQcfZA8%2FTWgBfjO0c5jQWCzl%2FI2HC2bo%2Bm52z5Vv2XTOE8%2FHa4d02piedubJj9omL%2BLdB%2FRqHcGG9zOSw2IrtQhg%3D%3D&return_url=http%3A%2F%2Fwww.baidu.com&notify_url=http%3A%2F%2Fwww.baidu.com&version=1.0&app_id=2016102300746673&sign_type=RSA2&timestamp=2020-04-16+16%3A57%3A41&alipay_sdk=alipay-sdk-java-dynamicVersionNo&format=json\">\n" +
            "            <input type=\"hidden\" name=\"biz_content\" value=\"{&quot;out_trade_no&quot;:&quot;20201126172731&quot;,&quot;total_amount&quot;:&quot;2000&quot;,&quot;subject&quot;:&quot;ouostore订单&quot;,&quot;product_code&quot;:&quot;FAST_INSTANT_TRADE_PAY&quot;}\">\n" +
            "            <input type=\"submit\" value=\"立即支付\" style=\"display:none\" >\n" +
            "            </form>\n" +
            "            <script>document.forms[0].submit();</script>")
    private String form;

}
