package com.hnaqvi.springreact.api;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.hnaqvi.springreact.config.CacheConfig;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("classpath:test.properties")
@Import(CacheConfig.class)
@EnableCaching
class ProductIntegrationTest {

	@Autowired
	private CacheManager cacheManager;

	private TestRestTemplate testRestTemplate;

	@Autowired
	private RestTemplateBuilder restTemplateBuilder;

	@LocalServerPort
	private int port;

	@BeforeAll
	void setup() {
		restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:" + port);
		testRestTemplate = new TestRestTemplate(restTemplateBuilder);

	}

	@Test
	void createProduct_expectCreate() {

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		final var productPayload = "{\n" +
				"    \"name\": \"product1\",\n" +
				"    \"description\":  \"product1\",\n" +
				"    \"creationDate\": \"2020-09-20T00:01:00\",\n" +
				"    \"updateDate\": \"2020-09-20T00:01:00\",\n" +
				"    \"lastPurchasedDate\": \"2020-09-21T00:01:00\",\n" +
				"    \"category\": \"http://localhost:8080/category/1\"\n" +
				"}";

		final HttpEntity<String> request = new HttpEntity<>(productPayload, headers);

		final ResponseEntity<String> response =  testRestTemplate.exchange("/api/product/", HttpMethod.POST, request, String.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());

	}

	@Test
	void lastPurchasedDateValidation_expectBadRequest() {

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		final var productPayload = "{\n" +
				"    \"name\": \"product1\",\n" +
				"    \"description\":  \"product1\",\n" +
				"    \"creationDate\": \"2020-09-20T00:01:00\",\n" +
				"    \"updateDate\": \"2020-09-20T00:01:00\",\n" +
				"    \"lastPurchasedDate\": \"2020-09-19T00:01:00\",\n" + // LastPurchasedDate before the creationDate
				"    \"category\": \"http://localhost:8080/category/1\"\n" +
				"}";

		final HttpEntity<String> request = new HttpEntity<>(productPayload, headers);

		final ResponseEntity<String> response =  testRestTemplate.exchange("/api/product/", HttpMethod.POST, request, String.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

	}

	@Test
	void deletedProduct_expectThemInCache() throws InterruptedException {
		testRestTemplate.delete("/api/product/1");
		testRestTemplate.delete("/api/product/2");
		testRestTemplate.delete("/api/product/3");
		testRestTemplate.delete("/api/product/4");
		testRestTemplate.delete("/api/product/5");
		testRestTemplate.delete("/api/product/6");
		testRestTemplate.delete("/api/product/7");
		var caffeineCache = (CaffeineCache) cacheManager.getCache("deleted_product");
		var nativeCache = caffeineCache.getNativeCache();
		assertEquals(5, nativeCache.asMap().keySet().size());
		assertTrue(nativeCache.asMap().keySet().contains(6l));
		assertTrue(nativeCache.asMap().keySet().contains(7l));
		assertFalse(nativeCache.asMap().keySet().contains(1l));
		assertFalse(nativeCache.asMap().keySet().contains(2l));
	}

}