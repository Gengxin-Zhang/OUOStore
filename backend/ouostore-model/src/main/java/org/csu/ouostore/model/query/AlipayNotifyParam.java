package org.csu.ouostore.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 支付宝异步请求参数
 * @author: Guanyu-Cai
 * @Date: 04/16/2020
 */
@ApiModel
@Data
public class AlipayNotifyParam {

    @ApiModelProperty(value = "通知时间", example = "2015-14-27 15:45:58")
    String notify_time;

    @ApiModelProperty(value = "通知类型", example = "trade_status_sync")
    String notify_type;

    @ApiModelProperty(value = "通知校验ID", example = "ac05099524730693a8b330c5ecf72da978")
    String notify_id;

    @ApiModelProperty(value = "编码格式", example = "utf-8")
    String charset;

    @ApiModelProperty(value = "接口版本", example = "1.0")
    String version;

    @ApiModelProperty(value = "签名类型", example = "RSA2")
    String sign_type;

    @ApiModelProperty(value = "签名", example = "601510b7970e52cc63db0f44997cf70e")
    String sign;

    @ApiModelProperty(value = "支付宝交易号", example = "2013112011001004330000121536")
    String trade_no;

    @ApiModelProperty(value = "开发者的app_id", example = "2014072300007148")
    String app_id;

    @ApiModelProperty(value = "商户订单号", example = "6823789339978248")
    String out_trade_no;

    @ApiModelProperty(value = "商户业务号", example = "HZRF001")
    String out_biz_no;

    @ApiModelProperty(value = "买家支付宝用户号", example = "2088102122524333")
    String buyer_id;

    @ApiModelProperty(value = "买家支付宝账号", example = "15901825620")
    String buyer_logon_id;

    @ApiModelProperty(value = "卖家支付宝用户号", example = "2088101106499364")
    String seller_id;

    @ApiModelProperty(value = "卖家支付宝账号", example = "zhuzhanghu@alitest.com")
    String seller_email;

    @ApiModelProperty(value = "交易状态", example = "TRADE_CLOSED")
    String trade_status;

    @ApiModelProperty(value = "订单金额", example = "20.00")
    String total_amount;

    @ApiModelProperty(value = "实收金额", example = "15.00")
    String receipt_amount;

    @ApiModelProperty(value = "开票金额", example = "10.00")
    String invoice_amount;

    @ApiModelProperty(value = "付款金额", example = "13.88")
    String buyer_pay_amount;

    @ApiModelProperty(value = "交易创建时间", example = "2015-04-27 15:45:57")
    String gmt_create;

    @ApiModelProperty(value = "交易付款时间", example = "2015-04-27 15:45:57")
    String gmt_payment;
}
