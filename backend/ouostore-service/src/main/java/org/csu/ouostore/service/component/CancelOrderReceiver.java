package org.csu.ouostore.service.component;

import lombok.extern.slf4j.Slf4j;
import org.csu.ouostore.service.OmsOrderService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 取消订单消息的处理者
 * Created by macro on 2018/9/14.
 */
@Component
@RabbitListener(queues = "OUOStore.order.cancel")
@Slf4j
public class CancelOrderReceiver {

    @Autowired
    private OmsOrderService orderService;

    @RabbitHandler
    public void handle(Long orderId){
        orderService.cancelOrder(orderId);
        log.info("process orderId:{}",orderId);
    }
}
