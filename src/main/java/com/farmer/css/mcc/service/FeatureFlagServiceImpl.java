package com.farmer.css.mcc.service;

import com.farmer.css.mcc.controller.ResponseHandler;
import com.farmer.css.mcc.service.redis.FeatureFlagRedisImpl;
import com.farmer.css.redisConnection.model.FeatureFlag;
import com.farmer.css.redisConnection.model.TransactionNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;
import java.util.ArrayList;
import java.util.List;

@Service
public class FeatureFlagServiceImpl implements FeatureFlagService {

    @Autowired(required = false)
    private JedisPool jedisPool;
    @Autowired
    @Qualifier("featureFlagRedisImpl")
    private FeatureFlagRedisImpl featureFlagRedisImpl;
    private static final Logger logger = LoggerFactory.getLogger(FeatureFlagServiceImpl.class);
    private ResponseHandler responseHandler = new ResponseHandler();
    private TransactionNotification transactionNotification = new TransactionNotification();
    private static final String CACHE_STRATEGY_REDIS ="redis";
    @Value("${cache.strategy}")
    private String cacheStrategy;

    public TransactionNotification setConfigurationProperties(String key, String value) {
       System.out.println(cacheStrategy);
        if(cacheStrategy.equalsIgnoreCase(CACHE_STRATEGY_REDIS)) {
            transactionNotification = featureFlagRedisImpl.setConfigurationProperties( key,  value);
        }
        return transactionNotification;
    }

    public List<FeatureFlag> getConfigurationProperties() {
        System.out.println(cacheStrategy);

        List<FeatureFlag> allfeatureFlagList = new ArrayList<>();
        if(cacheStrategy.equalsIgnoreCase(CACHE_STRATEGY_REDIS)) {
            allfeatureFlagList = featureFlagRedisImpl.getConfigurationProperties();
        }if (allfeatureFlagList == null){
            //responseHandler.constructGenericExceptionResponse(null);
        }
        return allfeatureFlagList;
    }


}