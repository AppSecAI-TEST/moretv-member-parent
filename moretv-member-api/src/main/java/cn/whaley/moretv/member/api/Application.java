package cn.whaley.moretv.member.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import cn.whaley.moretv.member.base.config.ApplicationContextUtil;
import cn.whaley.moretv.member.base.config.LongConnectionProperty;
import cn.whaley.moretv.member.base.util.longconnect.LongConnectionUtil;


/**
 * Created by Bob Jiang on 2017/3/9.
 */
@ComponentScan({"cn.whaley.moretv.member.api", "cn.whaley.moretv.member.base", "cn.whaley.moretv.member.service"})
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
        
        //加载长连接的配置
        LongConnectionProperty longConnectionProperty = ApplicationContextUtil.getBean(LongConnectionProperty.class);
        LongConnectionUtil.setLongConnectionProperty(longConnectionProperty);
    }

}
