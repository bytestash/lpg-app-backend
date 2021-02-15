package com.hnaqvi.springreact.dbo.view;


import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import com.hnaqvi.springreact.dbo.Category;
import com.hnaqvi.springreact.dbo.Product;

@Projection(name = "productView", types = Product.class)
public interface ProductView {

	@Value("#{target.id}")
	String getId();

	String getName();

	String getDescription();

	LocalDateTime getLastPurchasedDate();

	Category getCategory();

}