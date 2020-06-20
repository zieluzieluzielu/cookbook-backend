package com.mzielinski.cookbook.controller;

import com.mzielinski.cookbook.domain.dto.ProductGroupDto;
import com.mzielinski.cookbook.exception.ProductGroupNotFoundException;
import com.mzielinski.cookbook.mapper.ProductGroupMapper;
import com.mzielinski.cookbook.service.ProductGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
@RestController
@RequestMapping("/v1")
public class ProductGroupController {

    @Autowired
    private ProductGroupService productGroupService;

    @Autowired
    private ProductGroupMapper productGroupMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @RequestMapping(method = RequestMethod.GET, value = "/productgroups")
    public List<ProductGroupDto> getProductGroups() {
        return productGroupMapper.mapToProductGroupsDtoList(productGroupService.getAllProductGroups());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/productgroups/{productGroupId}")
    public ProductGroupDto getProductGroup(@PathVariable Long productGroupId) throws ProductGroupNotFoundException {
        return productGroupMapper.mapToProductGroupDto(productGroupService.getProductGroup(productGroupId).orElseThrow(() -> new ProductGroupNotFoundException("Product group with id: " + productGroupId + " was not found")));

    }

    @RequestMapping(method = RequestMethod.POST, value = "/productgroups", consumes = APPLICATION_JSON_VALUE)
    public ProductGroupDto createProductGroup(@RequestBody ProductGroupDto productGroupDto) {
       // LOGGER.info("Creating new Product group");
       // productGroupService.saveProductGroup(productGroupMapper.mapToProductGroup(productGroupDto))
        return productGroupMapper.mapToProductGroupDto(productGroupService.saveProductGroup(productGroupMapper.mapToProductGroup(productGroupDto)));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/productgroups")
    public ProductGroupDto updateProductGroup(@RequestBody ProductGroupDto productGroupDto) {
        LOGGER.info("Product group has been updated");
        return productGroupMapper.mapToProductGroupDto(productGroupService.saveProductGroup(productGroupMapper.mapToProductGroup(productGroupDto)));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "productgroups/{productGroupId}")
    public void deleteProductGroup(@PathVariable Long productGroupId) {
        LOGGER.info("Deleting product group with id: " + productGroupId);
        productGroupService.deleteProductGroup(productGroupId);
    }


}
