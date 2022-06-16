package edu.gdou.gym_java.shiro.cache;


import edu.gdou.gym_java.shiro.redis.Constant;
import edu.gdou.gym_java.shiro.redis.JedisUtil;
import edu.gdou.gym_java.shiro.jwt.JWTUtil;
import edu.gdou.gym_java.utils.SerializableUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import java.util.*;

/**
 * 重写Shiro的Cache保存读取
 * @author liuyuanfeng
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class CustomCache<K,V> implements Cache<K,V> {

    /**
     * redis-key-前缀-shiro:cache:
     */
    public final static String PREFIX_SHIRO_CACHE = "shiro:cache:";



    /**
     * 缓存的key名称获取为shiro:cache:username
     * @param key key
     * @return String
     */
    private String getKey(Object key){
        return PREFIX_SHIRO_CACHE + JWTUtil.getUsername(key.toString());
    }

    /**
     * 获取缓存
     */
    @Override
    public Object get(Object key) throws CacheException {
        if(!JedisUtil.exists(this.getKey(key))){
            return null;
        }
        return JedisUtil.getObject(this.getKey(key));
    }

    /**
     * 保存缓存
     */
    @Override
    public Object put(Object key, Object value) throws CacheException {
        // 设置Redis的Shiro缓存
        return JedisUtil.setObject(this.getKey(key), value, Constant.cacheExpireTime);
    }

    /**
     * 移除缓存
     */
    @Override
    public Object remove(Object key) throws CacheException {
        if(!JedisUtil.exists(this.getKey(key))){
            return null;
        }
        JedisUtil.delKey(this.getKey(key));
        return null;
    }

    /**
     * 清空所有缓存
     */
    @Override
    public void clear() throws CacheException {
        Objects.requireNonNull(JedisUtil.getJedis()).flushDB();
    }

    /**
     * 缓存的个数
     */
    @Override
    public int size() {
        Long size = Objects.requireNonNull(JedisUtil.getJedis()).dbSize();
        return size.intValue();
    }

    /**
     * 获取所有的key
     */
    @Override
    public Set keys() {
        Set<byte[]> keys = Objects.requireNonNull(JedisUtil.getJedis()).keys("*".getBytes());
        Set<Object> set = new HashSet<>();
        for (byte[] bs : keys) {
            set.add(SerializableUtil.unserializable(bs));
        }
        return set;
    }

    /**
     * 获取所有的value
     */
    @Override
    public Collection values() {
        Set keys = this.keys();
        List<Object> values = new ArrayList<>();
        for (Object key : keys) {
            values.add(JedisUtil.getObject(this.getKey(key)));
        }
        return values;
    }
}
