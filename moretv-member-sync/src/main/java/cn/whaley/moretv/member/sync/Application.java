package cn.whaley.moretv.member.sync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by tangzc 2017/02/10.
 */
@ComponentScan({"cn.whaley.moretv.member.sync", "cn.whaley.moretv.member.base.config"})
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
