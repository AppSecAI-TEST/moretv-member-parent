package cn.whaley.member.base.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * 缓存错误处理
 * </pre>
 *
 * @author wwh
 * @date 2015年7月6日 下午4:31:39
 *
 */
public class MyCacheErrorHandler implements CacheErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(MyCacheErrorHandler.class);

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.cache.interceptor.CacheErrorHandler#handleCacheGetError
     * (java.lang.RuntimeException, org.springframework.cache.Cache,
     * java.lang.Object)
     */
    @Override
    public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
        logger.error("缓存异常处理：", exception);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.cache.interceptor.CacheErrorHandler#handleCachePutError
     * (java.lang.RuntimeException, org.springframework.cache.Cache,
     * java.lang.Object, java.lang.Object)
     */
    @Override
    public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
        logger.error("缓存异常处理：", exception);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.cache.interceptor.CacheErrorHandler#handleCacheEvictError
     * (java.lang.RuntimeException, org.springframework.cache.Cache,
     * java.lang.Object)
     */
    @Override
    public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
        logger.error("缓存异常处理：", exception);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.cache.interceptor.CacheErrorHandler#handleCacheClearError
     * (java.lang.RuntimeException, org.springframework.cache.Cache)
     */
    @Override
    public void handleCacheClearError(RuntimeException exception, Cache cache) {
        logger.error("缓存异常处理：", exception);
    }

}
