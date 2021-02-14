package com.hnaqvi.springreact.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.hnaqvi.springreact.dbo.Product;

@RepositoryRestResource(collectionResourceRel = "products", path = "product")
public interface ProductRepository extends JpaRepository<Product, Long> {

}
