package com.hnaqvi.springreact.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.hnaqvi.springreact.dbo.Category;

@RepositoryRestResource(collectionResourceRel = "categories", path = "category")
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
