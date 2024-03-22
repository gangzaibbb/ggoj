package com.gangzai.ggojbackenduserservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.gangzai.ggojbackenduserservice.mapper")
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@ComponentScan("com.gangzai")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.gangzai.ggojbackendserviceclient.service"})
public class GgojBackendUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GgojBackendUserServiceApplication.class, args);
    }

}
