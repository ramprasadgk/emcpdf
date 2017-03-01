package oracle.sysman.emaas.platform.emcpdf.cache.support.lru;

import oracle.sysman.emaas.platform.emcpdf.cache.api.CacheLoader;
import oracle.sysman.emaas.platform.emcpdf.cache.exception.ExecutionException;
import oracle.sysman.emaas.platform.emcpdf.cache.support.AbstractCache;
import oracle.sysman.emaas.platform.emcpdf.cache.support.CachedItem;
import oracle.sysman.emaas.platform.emcpdf.cache.util.CacheConstants;
import oracle.sysman.emaas.platform.emcpdf.cache.util.CacheStatus;
import oracle.sysman.emaas.platform.emcpdf.cache.util.TimeUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedHashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by chehao on 2016/12/9.
 */
public class LinkedHashMapCache extends AbstractCache{
    Logger LOGGER= LogManager.getLogger(LinkedHashMapCache.class);

    private CacheStatus cacheStatus;
    private LinkedHashMap<Object, CachedItem> cacheMap;
    private String name;
    private Integer capacity;
    private Long timeToLive;
    private Long creationTime;
    Timer timer;

    public LinkedHashMapCache(String name, Integer capacity, Long timeToLive){
        this.name=name;
        this.capacity = capacity == null ? CacheConstants.DEFAULT_CAPACITY : capacity;
        this.timeToLive = timeToLive == null ? CacheConstants.DEFAULT_EXPIRATION : timeToLive;
        this.creationTime=System.currentTimeMillis();
        int hashTableSize = (int) Math.ceil(capacity/0.75f) + 1;
        cacheStatus = CacheStatus.AVAILABLE;
        cacheMap = new LinkedHashMap<Object,CachedItem>(hashTableSize, 0.75f, true) {//ordered by access time
            private static final long serialVersionUID = 1L;
            @Override
            protected boolean removeEldestEntry(java.util.Map.Entry<Object,CachedItem> eldest) {
                return size() > LinkedHashMapCache.this.capacity-2;
            }
        };
        // log cache status at fixed rate
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                logCacheStatus();
            }
        },1000L, CacheConstants.LOG_INTERVAL);
        LOGGER.info("Cache group named {} is created with capacity {} and timeToLive {}",name,capacity,timeToLive);
    }

    public LinkedHashMapCache(String name) {
        this(name, CacheConstants.DEFAULT_CAPACITY,CacheConstants.DEFAULT_EXPIRATION);
    }


    @Override
    public void clear() {
        super.clear();
        cacheMap.clear();
    }

    @Override
    public Object get(Object key, CacheLoader factory) throws ExecutionException {

        if (checkCacheStatusNotAvailable()) return null;
        Object obj=super.get(key, factory);
       if(obj!=null){
           LOGGER.debug("CachedItem with key {} and value {} is retrieved from cache group {}",key,obj,name);
           return obj;
       }
       return null;
}

    @Override
    public void put(Object key, Object value) {
        if (checkCacheStatusNotAvailable()) return;
        cacheMap.put(key, new CachedItem(key,value));
        LOGGER.debug("CachedItem with key {} and value {} is cached into cache group {}",key,value,name);
    }

    @Override
    public void evict(Object key) {
        if (checkCacheStatusNotAvailable()) return;
        super.evict(key);
        cacheMap.remove(key);
        LOGGER.debug("Cached Item with key {} is evicted from cache group {}",key,name);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    protected CachedItem lookup(Object key) {
        return cacheMap.get(key);
    }

    @Override
    public boolean isExpired(CachedItem cachedItem) {
//    	LOGGER.debug("time to live is {}, creation time is {}, current time is {}",timeToLive,cachedItem.getCreationTime(),System.currentTimeMillis());
        if(timeToLive<=0){
            return false;
        }
        return (System.currentTimeMillis()-cachedItem.getCreationTime())>TimeUtil.toMillis(timeToLive);
    }

    private void logCacheStatus(){
        LOGGER.info("[Cache Status] Cache group name is {}, " +
                        "cache group capacity is {}, " +
                        "cache group usage is {}, " +
                        "total request count is {}, " +
                        "cache hit count is {}, " +
                        "cache hit rate is {}, " +
                        "cache eviction count is {}", name, capacity, cacheMap.size(),
                cacheCounter.getRequestCount(), cacheCounter.getHitCount(), cacheCounter.getHitRate(), cacheCounter.getEvictionCount());
    }

    private boolean checkCacheStatusNotAvailable() {
        if(!cacheStatus.equals(CacheStatus.AVAILABLE)){
            LOGGER.info("Cache group {} is not available now!", name);
            return true;
        }
        return false;
    }

    public CacheStatus getCacheStatus() {
        return cacheStatus;
    }

    public void setCacheStatus(CacheStatus cacheStatus) {
        this.cacheStatus = cacheStatus;
    }


}

