package com.ldidioni.classifiedads.services;

import com.ldidioni.classifiedads.models.Category;
import com.ldidioni.classifiedads.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("categoryService")
public class CategoryService
{
	@Autowired
	CategoryRepository categoryRepository;

	public void update(int id, Category category) {
		categoryRepository.findById(id).ifPresent(existingCategory -> {
			existingCategory.setName(category.getName());
			categoryRepository.save(existingCategory);
		});
	};
}
