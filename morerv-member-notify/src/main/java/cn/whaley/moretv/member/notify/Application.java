package cn.whaley.moretv.member.notify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by Bob Jiang on 2017/3/9.
 */
@ComponentScan({"cn.whaley.moretv.member.notify", "cn.whaley.moretv.member.base", "cn.whaley.moretv.member.service"})
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
