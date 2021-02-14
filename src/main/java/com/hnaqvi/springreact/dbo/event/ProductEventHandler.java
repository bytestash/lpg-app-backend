package com.hnaqvi.springreact.dbo.event;

import org.springframework.cache.CacheManager;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import com.hnaqvi.springreact.dbo.Product;

import lombok.extern.slf4j.Slf4j;

@Component
@RepositoryEventHandler
@Slf4j
public class ProductEventHandler {

	private CacheManager cacheManager;

	public ProductEventHandler(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	@HandleBeforeDelete
	public void handleProductBeforeDelete(Product product) {
		cacheManager.getCache("deleted_product").put(product.getId(), product);
	}
}
