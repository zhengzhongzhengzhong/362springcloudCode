package com.bdqn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableZuulProxy  //开启zuul代理
@EnableEurekaClient        //zuul服务要注册到Eureka上
public class ZuulStart {

    @Bean
    public TokenFilter tokenFilter() {
        return new TokenFilter();
    }

    @Bean
    public PasswordFilter PasswordFilter() {
        return new PasswordFilter();
    }

    public static void main(String[] args) {
        SpringApplication.run(ZuulStart.class);
    }
}
