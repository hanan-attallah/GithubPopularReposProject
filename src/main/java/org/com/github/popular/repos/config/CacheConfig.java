package org.com.github.popular.repos.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cache.interceptor.SimpleKeyGenerator;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    // Define cache names as constants for better maintainability
    public static final String POPULAR_REPOSITORIES_CACHE = "popularRepositoriesCache";

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(POPULAR_REPOSITORIES_CACHE);
        cacheManager.setCaffeine(Caffeine.newBuilder()
                        .expireAfterWrite(20, TimeUnit.MINUTES) // General configuration
        );
        return cacheManager;
    }

    // Optional: Custom KeyGenerator
    @Bean
    public SimpleKeyGenerator keyGenerator() {
        return new SimpleKeyGenerator();
    }
}
