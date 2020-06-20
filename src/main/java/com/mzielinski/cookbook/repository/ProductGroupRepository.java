package com.mzielinski.cookbook.repository;

import com.mzielinski.cookbook.domain.ProductGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ProductGroupRepository extends JpaRepository<ProductGroup, Long> {
    @Override
    List<ProductGroup> findAll();

    @Override
    ProductGroup save(ProductGroup productGroup);

    @Override
    Optional<ProductGroup> findById(Long productGroupId);


}
