package com.hnaqvi.springreact.util;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.hnaqvi.springreact.dbo.Category;
import com.hnaqvi.springreact.dbo.Product;
import com.hnaqvi.springreact.repo.CategoryRepository;
import com.hnaqvi.springreact.repo.ProductRepository;

@Component
@ConditionalOnProperty(name = { "init.data.enabled" })
public class InitialDataLoader implements ApplicationListener<ApplicationReadyEvent> {

	final CategoryRepository categoryRepository;
	final ProductRepository productRepository;

	public InitialDataLoader(CategoryRepository categoryRepository, ProductRepository productRepository) {

		this.categoryRepository = categoryRepository;
		this.productRepository = productRepository;
	}
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
			loadCategories();
			loadProducts();
	}

	private void loadCategories() {
		// to read from classpath use: Files.newBufferedReader(Path.of(this.getClass().getResource("/db/categories.csv").toURI()), Charset.defaultCharset())
		try (var fileReader = Files.newBufferedReader(Path.of("/tmp/categories.csv"), Charset.defaultCharset())) {
			var csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
			csvParser.getRecords().forEach(csvRecord ->
			{
				var category = new Category();
				category.setId(Long.parseLong(csvRecord.get("ID")));
				category.setName(csvRecord.get("CATEGORY_NAME"));
				categoryRepository.save(category);
			});
		} catch (Exception e) {
			throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		}
	}

	private void loadProducts() {

		// Read once.
		final Map<Long, Category> categories = categoryRepository.findAll().stream()
				.collect(Collectors.toMap(Category::getId, entry -> entry));

		//try (var fileReader = Files.newBufferedReader(Path.of(this.getClass().getResource("/db/products.csv").toURI()), Charset.defaultCharset())) {
		try (var fileReader = Files.newBufferedReader(Path.of("/tmp/products.csv"), Charset.defaultCharset())) {
			var csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
			csvParser.getRecords().forEach(csvRecord ->
			{
				var product = new Product();
				product.setId(Long.parseLong(csvRecord.get("ID")));
				product.setName(csvRecord.get("NAME"));
				product.setCategory(categories.get(Long.parseLong(csvRecord.get("CATEGORY_ID"))));
				product.setDescription(csvRecord.get("DESCRIPTION"));
				product.setCreationDate(parseCSVDate(csvRecord.get("CREATION_DATE")));
				product.setUpdateDate(parseCSVDate(csvRecord.get("UPDATE_DATE")));
				product.setLastPurchasedDate(parseCSVDate(csvRecord.get("LAST_PURCHASED_DATE")));
				productRepository.save(product);
			});
		} catch (Exception e) {
			throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		}
	}

	/**
	 * @param dateString format: 10/24/2020 0:01
	 */
	private LocalDateTime parseCSVDate(String dateString) {
		return LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern("M/d/y H:m"));
	}
}
