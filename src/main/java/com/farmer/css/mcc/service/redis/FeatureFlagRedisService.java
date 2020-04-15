package com.farmer.css.mcc.service.redis;

import com.farmer.css.redisConnection.model.FeatureFlag;
import com.farmer.css.redisConnection.model.TransactionNotification;

import java.util.List;

public interface FeatureFlagRedisService {

    public TransactionNotification setConfigurationProperties(String key, String value);
    public List<FeatureFlag> getConfigurationProperties();

}

