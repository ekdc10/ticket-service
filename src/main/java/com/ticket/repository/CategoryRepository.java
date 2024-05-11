package com.ticket.repository;

import org.springframework.data.repository.CrudRepository;

import com.ticket.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    Category findByCategoryId(Long categoryId);
}
