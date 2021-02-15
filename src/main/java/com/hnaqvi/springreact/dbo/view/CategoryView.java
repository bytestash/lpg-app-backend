package com.hnaqvi.springreact.dbo.view;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import com.hnaqvi.springreact.dbo.Category;

@Projection(name = "categoryView", types = Category.class)
public interface CategoryView {

	@Value("#{target.id}")
	String getId();

	String getName();

}