package com.ruoyi.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class RuoyiAdminApplication {

    public static void main(String[] args) {
        {
            SpringApplication.run(RuoyiAdminApplication.class, args);
            System.out.println("(♥◠‿◠)ﾉﾞ  项目启动成功   ლ(´ڡ`ლ)ﾞ");
        }
    }

}
