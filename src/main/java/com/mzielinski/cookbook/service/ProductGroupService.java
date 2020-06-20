package com.mzielinski.cookbook.service;

import com.mzielinski.cookbook.domain.ProductGroup;
import com.mzielinski.cookbook.repository.ProductGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductGroupService {

    @Autowired
    private ProductGroupRepository productGroupRepository;

    public List<ProductGroup> getAllProductGroups() {
        return productGroupRepository.findAll();
    }

    public ProductGroup saveProductGroup(final ProductGroup productGroup) {
        return productGroupRepository.save(productGroup);
    }

    public Optional<ProductGroup> getProductGroup(final Long id) {
        return productGroupRepository.findById(id);
    }

    public void deleteProductGroup(final Long id) {
        productGroupRepository.deleteById(id);
    }


}
