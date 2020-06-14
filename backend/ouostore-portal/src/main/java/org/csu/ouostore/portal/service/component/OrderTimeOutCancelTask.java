package org.csu.ouostore.portal.service.component;

import lombok.extern.slf4j.Slf4j;
import org.csu.ouostore.service.OmsOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务取消超时订单
 */
@Component
@Slf4j
public class OrderTimeOutCancelTask {
    @Autowired
    private OmsOrderService orderService;

    /**
     * cron表达式：Seconds Minutes Hours DayofMonth Month DayofWeek [Year]
     * 每10分钟扫描一次，扫描设定超时时间之前下的订单，如果没支付则取消该订单
     */
    @Scheduled(cron = "0 0/10 * ? * ?")
    private void cancelTimeOutOrder(){
        Integer count = orderService.cancelTimeOutOrder();
        log.info("取消订单，并根据sku编号释放锁定库存，取消订单数量：{}",count);
    }

}
