package org.csu.ouostore.service;

import org.csu.ouostore.model.query.AlipayNotifyParam;
import org.csu.ouostore.model.vo.AlipayFormVo;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;


public interface AlipayService {

   /**
    * 支付宝支付调用接口
    *
    * @param orderId
    */
   AlipayFormVo alipay(Long orderId);

   /**
    * 异步支付宝支付成功回调接口
    */
   @Transactional
   void alipayCallback(AlipayNotifyParam param);

   /**
    * 同步支付宝支付成功回调接口
    */
   @Transactional
   void alipayCallback(HttpServletRequest request);
}
