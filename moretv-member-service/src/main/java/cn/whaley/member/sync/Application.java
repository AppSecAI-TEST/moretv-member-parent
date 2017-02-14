package cn.whaley.member.sync;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.whaley.member.sync.constant.GlobalConstant;
import cn.whaley.member.sync.util.MessageProducer;

/**
 * Created by tangzc 2017/02/10.
 */

@Controller
@SpringBootApplication
public class Application {
	@Autowired
	private MessageProducer messageProducer;
	
    @Autowired
    private StringRedisTemplate template;
    
    @RequestMapping("/")
    @ResponseBody
    String home() {
    	messageProducer.send(GlobalConstant.MORETV_ADMIN_PUBLISH_GOODS_EXCHANGE, GlobalConstant.MORETV_ADMIN_PUBLISH_GOODS_ROUTER_KEY, "11111222");
    	return "Hello World!";
    }
    
    @RequestMapping("/redis_test")
    @ResponseBody
    String redisTest(@RequestParam String visiter) {
    	return visitCount(visiter);
    }
    
    @Cacheable(value = "visitCountCache",keyGenerator = "commonKeyGenerator")  
    public String visitCount(String visiter){  
        HashOperations<String, String, String> ops = template.opsForHash();  
        ops.put("visit", "visiter1", "1");
        ops.put("visit", "visiter2", "2");
        
        HashOperations<String, String, String> opsGet = template.opsForHash(); 
        
        return opsGet.get("visit", visiter);  
    }  
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
