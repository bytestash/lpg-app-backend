package com.hnaqvi.springreact.dbo.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.hnaqvi.springreact.dbo.Product;

@Component("beforeCreateProductValidator")
public class ProductValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Product.class.equals(clazz);
	}

	@Override
	public void validate(final Object o, final Errors errors) {
		var product = (Product) o;
		if (product.getLastPurchasedDate().isBefore(product.getCreationDate())) {
			errors.rejectValue("lastPurchasedDate", "lastPurchasedDate.before.creationDate");
		}
	}
}
