package com.farmer.css.mcc.service.redis;

import com.farmer.css.mcc.config.CacheConfiguration;
import com.farmer.css.mcc.controller.ResponseHandler;
import com.farmer.css.redisConnection.model.FeatureFlag;
import com.farmer.css.redisConnection.model.TransactionNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
@Service
public class FeatureFlagRedisImpl implements FeatureFlagRedisService {
    @Autowired(required = false)
    private JedisPool jedisPool;
    private ResponseHandler responseHandler = new ResponseHandler();
    private TransactionNotification transactionNotification = new TransactionNotification();
    private static final Logger logger = LoggerFactory.getLogger(CacheConfiguration.class);


    public TransactionNotification  setConfigurationProperties(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            String result = jedis.set(key, value);
            System.out.println(result);
            logger.info("CACHE: Key {} & Value {}", key, value);
            return responseHandler.constructSucessResponse();
        }catch (Exception exception) {
            logger.error("CACHE: Exception {} on storing Key {} & Value {} to redis", exception.getMessage(), key, value);
            return responseHandler.constructGenericExceptionResponse(exception.getMessage());

        }
    }
    public List<FeatureFlag> getConfigurationProperties() {
        Set<String> keys = null;
        List<FeatureFlag> allfeatureFlagList = new ArrayList<>();
        System.out.println("CACHE");
        if (jedisPool == null) {
            logger.info("CACHE:  JedisPool is null");
            return null;
        }
        try (Jedis jedis = jedisPool.getResource()) {
            keys = jedis.keys("*");
            if (keys != null && keys.size() > 0) {
                System.out.println(keys);
                for (String key : keys) {
                    FeatureFlag featureFlag = new FeatureFlag();
                    featureFlag.setFeatureFlagName(key);
                    featureFlag.setFeatureFlagValue(jedis.get(key));
                    allfeatureFlagList.add(featureFlag);
                }
            }
        } catch (Exception exception) {
            logger.error("CACHE: Exception  on retrieving value");
        }
        return allfeatureFlagList;
    }
}
