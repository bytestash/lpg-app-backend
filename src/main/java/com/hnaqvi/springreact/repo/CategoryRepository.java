package com.hnaqvi.springreact.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.hnaqvi.springreact.dbo.Category;
import com.hnaqvi.springreact.dbo.view.CategoryView;
import com.hnaqvi.springreact.dbo.view.ProductView;

@RepositoryRestResource(collectionResourceRel = "categories", path = "category", excerptProjection = CategoryView.class)
@CrossOrigin
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
