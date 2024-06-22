package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Category;
import com.springboot.blog.exception.ResourceNotFountException;
import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {

        // below we are converting DTO to Entity , using model mapper
        Category category = modelMapper.map(categoryDto, Category.class);

        // below we are saving the converted entity to the database
        categoryRepository.save(category);

        // below we are again converting the Entity into DTO and returning it in the response , using model mapper
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public CategoryDto getCategory(Long categoryId) {

        // getting the category from database using id
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFountException("not found Category ", " id", categoryId));

        // converting and returning the category into Dto
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> allCategories = categoryRepository.findAll();

//        return allCategories.stream().map(this::maptoDto).toList();
        return allCategories.stream().map(category -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto updateCategory(Long categoryID, CategoryDto categoryDto) {

        // Getting the Category from the database by id , if not available then throws the exception
        Category category = categoryRepository.findById(categoryID).orElseThrow(() ->
                new ResourceNotFountException("not found the category", " this id", categoryID));

        // Updating the content
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        // Saving the Updated Content into database
        categoryRepository.save(category);

        // Converting Entity to Dto and returning it
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public String deleteById(Long categoryId) {

        // Getting the Category from the database by id , if not available then throws the exception
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFountException("Category is not found ", " by the id of ", categoryId));

        // If we found the object then we can delete it by below method
        categoryRepository.delete(category);

        return "Category is Deleted Successfully";
    }


 

}
