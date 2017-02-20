package cn.whaley.moretv.member.base.util;

import redis.clients.jedis.Jedis;

/**
 * Created by leiwenbin on 15/10/22.
 * Operation object
 */

@SuppressWarnings("ALL")
public final class RedisObjectOperateHelper {

    public static <T> boolean setObjectByRedis(String key, T obj, int expire_in) {
        Jedis j = RedisHelper.getConnection();
        if (j == null)
            return false;
        String res = null;
        try {
            res = j.setex(key.getBytes(), expire_in, SerializeHelper.<T>serialize(obj));
        } catch (Exception e) {
            LogHelper.getLogger().error("Can't serialize null, Exception:" + e);
        } finally {
            j.close();
            return !(res == null || !res.equals("OK"));
        }
    }

    public static <T> T getObjectByRedis(String key) {
        Jedis j = RedisHelper.getConnection();
        if (j == null)
            return null;
        byte[] b = j.get(key.getBytes());
        j.close();
        return SerializeHelper.<T>unserialize(b);
    }

	public static boolean delObjectByRedis(String key) {
		Jedis j = RedisHelper.getConnection();
		if (j == null) {
			return false;
		}
		j.del(key);
		j.close();
		return true;
	}

}
