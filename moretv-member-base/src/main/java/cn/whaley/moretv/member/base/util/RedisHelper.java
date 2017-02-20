package cn.whaley.moretv.member.base.util;

import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leiwenbin on 15/10/20.
 * Redis Client
 */

public class RedisHelper {
    protected JedisPool jedisPool;//非切片连接池
    protected ShardedJedisPool shardedJedisPool;//切片连接池

    private static volatile RedisHelper instance = null;

    private RedisHelper() {
        this.initialPool();
        //initialSharedPool();
    }

    protected static JedisPool getPool() {
        if(instance == null) {
            synchronized(RedisHelper.class) {
                if(instance == null) {
                    instance = new RedisHelper();
                }
            }
        }
        return instance.jedisPool;
    }

    /**
     * 初始化非切片池
     */
    private void initialPool()
    {
        // 池基本配置
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(100);
        config.setMaxIdle(100);
        config.setMaxWaitMillis(3000l);
        config.setTestOnBorrow(true);
        config.setTestWhileIdle(true);

        jedisPool = new JedisPool(config, PropertiyHelp.getContextProperty("REDIS_HOST"), Integer.parseInt(PropertiyHelp.getContextProperty("REDIS_PORT")));
        LogHelper.getLogger().info("redis pool init success");
    }

    /**
     * 初始化切片池
     */
    private void initialSharedPool()
    {
        // 池基本配置
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(10);
        config.setMaxIdle(10);
        config.setMaxWaitMillis(1000l);
        config.setTestOnBorrow(true);
        config.setTestWhileIdle(true);
        // slave链接
        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
        shards.add(new JedisShardInfo(PropertiyHelp.getContextProperty("REDIS_HOST"), Integer.parseInt(PropertiyHelp.getContextProperty("REDIS_PORT")), "master"));

        // 构造池
        shardedJedisPool = new ShardedJedisPool(config, shards);
    }

    public static Jedis getConnection() {
        try {
            return RedisHelper.getPool().getResource();
        } catch (Exception e) {
            LogHelper.getLogger().error("get redis connection error. exception:" + e.toString());
            return null;
        }
    }
}
