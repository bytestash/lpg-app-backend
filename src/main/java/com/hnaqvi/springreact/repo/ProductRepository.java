package com.hnaqvi.springreact.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hnaqvi.springreact.dbo.ProductDBO;

public interface ProductRepository extends JpaRepository<ProductDBO, Long> {

}
