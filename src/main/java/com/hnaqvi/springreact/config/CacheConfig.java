package com.hnaqvi.springreact.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
public class CacheConfig {

	@Bean
	public Caffeine caffeineConfig() {
		return Caffeine.newBuilder().maximumSize(5);
	}

	@Bean
	public CacheManager cacheManager(Caffeine caffeine) {
		CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
		caffeineCacheManager.setCaffeine(caffeine);
		return caffeineCacheManager;
	}

}
