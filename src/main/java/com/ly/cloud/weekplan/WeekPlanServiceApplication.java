package com.ly.cloud.weekplan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/** 
 * Class Name: BaseServiceApplication  
 * Description: 
 * 			公共基础服务
 * @author: WenboLi
 * @mail: wenboli@ly-sky.com 
 * @date: 2017年4月11日
 * @version: 1.0
 *
 */  
@SpringBootApplication(scanBasePackages={"com.ly.cloud"})
@EnableTransactionManagement  
@EnableEurekaClient
//@EnableFeignClients
//@EnableCircuitBreaker 
public class WeekPlanServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeekPlanServiceApplication.class, args);
	}
}

	