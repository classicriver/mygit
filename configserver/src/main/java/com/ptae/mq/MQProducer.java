/**   
*/ 
package com.ptae.mq;

import java.util.Date;

//import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description: TODO
* @author  xiesc
* @date 2017年12月11日 
* @version V1.0   
 */
//@Component
public class MQProducer {
	
	//@Autowired
	//private AmqpTemplate rabbitTemplate;
	
	public void send() {
        String sendMsg = "hello1 " + new Date();
        System.out.println("Sender1 : " + sendMsg);
       // this.rabbitTemplate.convertAndSend("helloQueue", sendMsg);
    }
}
