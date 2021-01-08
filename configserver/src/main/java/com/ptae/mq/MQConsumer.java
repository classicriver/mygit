/**   
*/ 
package com.ptae.mq;

//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Description: TODO
* @author  xiesc
* @date 2017年12月11日 
* @version V1.0   
 */
//@Component
//@RabbitListener(queues="helloQueue")
public class MQConsumer {
	//@RabbitHandler
    public void process(String hello) {
        System.out.println("Receiver1  : " + hello);
    }
}
