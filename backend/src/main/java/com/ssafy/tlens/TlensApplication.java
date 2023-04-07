package com.ssafy.tlens;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootApplication
public class TlensApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(TlensApplication.class, args);
        StringRedisTemplate template = ctx.getBean(StringRedisTemplate.class);
        String val = template.opsForValue().get("backup1");
        System.out.println(val);

    }

}
