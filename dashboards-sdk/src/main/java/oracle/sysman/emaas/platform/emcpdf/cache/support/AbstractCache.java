package oracle.sysman.emaas.platform.emcpdf.cache.support;

import oracle.sysman.emaas.platform.emcpdf.cache.api.ICache;
import oracle.sysman.emaas.platform.emcpdf.cache.api.CacheLoader;
import oracle.sysman.emaas.platform.emcpdf.cache.exception.*;
import oracle.sysman.emaas.platform.emcpdf.cache.exception.ExecutionException;
import oracle.sysman.emaas.platform.emcpdf.cache.tool.CacheStatistics;
import oracle.sysman.emaas.platform.emcpdf.cache.tool.CacheThreadPools;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.TimerTask;
import java.util.concurrent.*;

/**
 * Created by chehao on 2016/12/22.
 */
public abstract class AbstractCache implements ICache{
    Logger LOGGER=LogManager.getLogger(AbstractCache.class);

    public CacheStatistics cacheStatistics =new CacheStatistics();

    @Override
    public Object get(Object key) throws ExecutionException {
        return get(key,null);
    }

    @Override
    public Object get(Object key, CacheLoader factory)throws ExecutionException {
        checkNotNull(key);
        cacheStatistics.setRequestCount(cacheStatistics.getRequestCount()+1);
        CachedItem value=lookup(key);
        if(value == null){
            if(factory!=null){
                Object valueFromFactory= null;
                try {
                    valueFromFactory = factory.load(key);
                } catch (Exception e) {
                    LOGGER.error(e.getLocalizedMessage());
                    throw new ExecutionException(e);
                }
                CachedItem ci=new CachedItem(key,valueFromFactory);
                    put(key,ci);
                    return valueFromFactory;
            }
        }else{
            if(isExpired(value)){
                evict(key);
            }else{
                cacheStatistics.setHitCount(cacheStatistics.getHitCount()+1);
                return value.getValue();
            }
        }
        return null;
    }

    @Override
    public Object refreshAfterGet(final Object key, final CacheLoader factory) throws ExecutionException {
        ScheduledExecutorService pool= CacheThreadPools.getThreadPool();
        LOGGER.info("Refresh after get action begin...");
        pool.schedule(new TimerTask() {
            @Override
            public void run() {
                LOGGER.info("Refresh...");
                Object obj= null;
                try {
                    obj = get(key,factory);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                put(key,new CachedItem(key,obj));
            }
        }, 30, TimeUnit.SECONDS);
        LOGGER.info("Refresh after get action end...");
        return get(key,factory);
    }

    @Override
    public void put(Object key, Object value) {
        cacheStatistics.setUsage(cacheStatistics.getUsage()+1);
    }

    @Override
    public void evict(Object key) {
        cacheStatistics.setEvictionCount(cacheStatistics.getEvictionCount()+1);
        cacheStatistics.setUsage(cacheStatistics.getUsage()-1);
    }

    protected abstract CachedItem lookup(Object key);

    private  <T> T checkNotNull(T reference) {
        if (reference == null) {
            LOGGER.error("Null Pointer Exception occurred!");
            throw new NullPointerException();
        }
        return reference;
    }
}
