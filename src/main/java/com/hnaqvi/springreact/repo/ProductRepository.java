package com.hnaqvi.springreact.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.hnaqvi.springreact.dbo.Product;
import com.hnaqvi.springreact.dbo.view.ProductView;

@RepositoryRestResource(collectionResourceRel = "products", path = "product", excerptProjection = ProductView.class)
@CrossOrigin
public interface ProductRepository extends JpaRepository<Product, Long> {

}
