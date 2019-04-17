package com.cheng.springcloud.demo.producer.controller;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class ProducerController {
	
	@RequestMapping(value = "/test")
	public String TestHealth() {
        System.out.println("欢迎您使用spring cloud框架开发项目");
        return "server is health";
	}
}
