package oracle.sysman.emaas.platform.emcpdf.cache.support;

import oracle.sysman.emaas.platform.emcpdf.cache.api.ICache;
import oracle.sysman.emaas.platform.emcpdf.cache.api.ICacheManager;
import oracle.sysman.emaas.platform.emcpdf.cache.config.CacheConfig;
import oracle.sysman.emaas.platform.emcpdf.cache.support.lru.LinkedHashMapCache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by chehao on 2016/12/22.
 */
public abstract class AbstractCacheManager implements ICacheManager{

    private final ConcurrentMap<String, ICache> cacheMap = new ConcurrentHashMap<>(16);

    private final Logger LOGGER= LogManager.getLogger(AbstractCacheManager.class);

    private Timer timer=new Timer();

    /**
     * Return the cache associated with the given name.
     *
     * @param name
     * @return
     */
    @Override
    public ICache getCache(String name) {
        return getCache(name,null,null);
    }
    @Override
    public ICache getCache(String name, Integer capacity, Long timeToLive){
        ICache cache = this.cacheMap.get(name);
        if (cache != null) {
            return cache;
        }
        else {
            //create a new cache if cache is not existing.
            synchronized (this.cacheMap) {
                cache = this.cacheMap.get(name);
                if (cache == null) {
                    if(capacity == null && timeToLive == null){
                        cache = createNewCache(name);
                    }else{
                        cache = createNewCache(name,capacity,timeToLive);
                    }
                    if (cache != null) {
                        this.cacheMap.put(name, cache);
                    }
                }
                return cache;
            }
        }
    }
    @Override
    public abstract ICache createNewCache(String name, Integer capacity, Long timeToLive);

    @Override
    public abstract ICache createNewCache(String name);

    @Override
    public void init(){
        //log cache status
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                LOGGER.info("Log Cache Statistics Begin...");
                Iterator<Map.Entry<String, ICache>> it = cacheMap.entrySet().iterator();
                while(it.hasNext()){
                    Map.Entry<String,ICache> entry=it.next();
                    AbstractCache ac=(AbstractCache)entry.getValue();
                    LOGGER.info("[Cache Statistics] Cache name is [{}], "
                                    + "Cache capacity is [{}], "
                                    + "Cache usage is [{}], "
                                    + "Cache usage rate is [{}], " +
                                    "Cache total request count is [{}], "
                                    + "Cache hit count is [{}], "
                                    + "Cache hit rate is [{}], "
                                    + "Eviction Count is [{}]",
                            ac.getName(),
                            ac.cacheStatistics.getCapacity(),
                            ac.cacheStatistics.getUsage(),
                            ac.cacheStatistics.getUsageRate(),
                            ac.cacheStatistics.getRequestCount(),
                            ac.cacheStatistics.getHitCount(),
                            ac.cacheStatistics.getHitRate(),
                            ac.cacheStatistics.getEvictionCount());
                }
            }
        },CacheConfig.LOG_INTERVAL, CacheConfig.REFRESH_CACHE_INTERVAL);
    }

    @Override
    public void close() throws IOException{
        this.cacheMap.clear();
    }

}
