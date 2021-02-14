package com.hnaqvi.springreact.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hnaqvi.springreact.dbo.CategoryDBO;

public interface CategoryRepository extends JpaRepository<CategoryDBO, Long> {

}
