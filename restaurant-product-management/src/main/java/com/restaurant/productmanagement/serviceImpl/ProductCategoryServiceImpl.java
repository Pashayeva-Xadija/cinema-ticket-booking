package com.restaurant.productmanagement.serviceImpl;

import com.restaurant.productmanagement.dto.ProductCategoryDto;
import com.restaurant.productmanagement.mapper.ProductCategoryMapper;
import com.restaurant.productmanagement.model.ProductCategory;
import com.restaurant.productmanagement.repository.ProductCategoryRepository;
import com.restaurant.productmanagement.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {
    private final ProductCategoryRepository categoryRepository;
    private final ProductCategoryMapper categoryMapper;

    @Override
    public List<ProductCategoryDto> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductCategoryDto create(ProductCategoryDto dto) {
        ProductCategory category = categoryMapper.toEntity(dto);
        ProductCategory saved = categoryRepository.save(category);
        return categoryMapper.toDto(saved);
    }
    @Override
    public ProductCategoryDto update(Long id, ProductCategoryDto dto) {
        ProductCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kateqoriya tapılmadı"));

        category.setName(dto.getName());
        category.setDescription(dto.getDescription());

        ProductCategory updated = categoryRepository.save(category);
        return categoryMapper.toDto(updated);
    }

    @Override
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Kateqoriya tapılmadı");
        }
        categoryRepository.deleteById(id);
    }
}

