/**   
*/
package com.ptae.configserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ptae.mq.MQProducer;

/**
 * @Description: TODO
 * @author xiesc
 * @date 2017年12月11日
 * @version V1.0  
 */
@RestController
@RequestMapping("/rabbit")
public class MQController {

	//@Autowired
	//private MQProducer sender;

	@PostMapping("/hello")
	public void hello() {
		//sender.send();
	}
}
