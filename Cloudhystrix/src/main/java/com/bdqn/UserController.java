package com.bdqn;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class UserController {


    @Bean
    @LoadBalanced
    public RestTemplate getResttemplate() {
        return new RestTemplate();
    }

    @Autowired
    private RestTemplate resttemplate;


    @RequestMapping("/getdata")
    @HystrixCommand(fallbackMethod = "helloFallback")//失败时调用默认返回,
    public Object hello() {
        //指出服务地址   http://{服务提供者应用名名称}/{具体的controller}
        String url = "http://provider-user/user/Data";

        //返回值类型和我们的业务返回值一致
        return resttemplate.getForObject(url, String.class);
    }

    /**
     * 对应上面的方法，参数必须一致，当访问失败时，hystrix直接回调用此方法
     *
     * @return 失败调用时，返回默认值:
     */
    public String helloFallback() {
        return "您请求的数据没拿到，我是hystrix返回的默认数据--helloxxxx";
    }
}
