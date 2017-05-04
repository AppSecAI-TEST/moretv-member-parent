package cn.whaley.moretv.member.sync.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RedisResetResponseUtil {
    public static Map<String, Object> getResetRedisMap(List<String> addList, List<String> deleteList){
        Map<String, Object> map = new HashMap<>(2);
        map.put("add", addList);
        map.put("del", deleteList);
        
        return map;
    }
}
