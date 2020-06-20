package com.mzielinski.cookbook.controller;

import com.google.gson.Gson;
import com.mzielinski.cookbook.domain.ProductGroup;
import com.mzielinski.cookbook.domain.dto.ProductGroupDto;
import com.mzielinski.cookbook.mapper.ProductGroupMapper;
import com.mzielinski.cookbook.service.ProductGroupService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(ProductGroupController.class)
public class ProductGroupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductGroupService productGroupService;

    @MockBean
    private ProductGroupMapper productGroupMapper;

    @Test
    public void shouldRetrieveProductGroupList() throws Exception {
        //Given
        List<ProductGroupDto> productGroupsListDto = new ArrayList<>();
        ProductGroupDto productGroupDto1 = ProductGroupDto
                .builder()
                .productGroupId(1L)
                .groupName("Vegetables")
                .build();

        ProductGroupDto productGroupDto2 = ProductGroupDto
                .builder()
                .productGroupId(2L)
                .groupName("Meat")
                .build();
        productGroupsListDto.add(productGroupDto1);
        productGroupsListDto.add(productGroupDto2);


        when(productGroupMapper.mapToProductGroupsDtoList(productGroupService.getAllProductGroups())).thenReturn(productGroupsListDto);

        //When & Then
        mockMvc.perform(get("/v1/productgroups").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].productGroupId", is(productGroupsListDto.get(0).getProductGroupId().intValue())))
                .andExpect(jsonPath("$[1].groupName", is("Meat")))
                .andExpect(jsonPath("$[0].groupName", is("Vegetables")));
    }

    @Test
    public void shouldRetrieveProductGroup() throws Exception {
        //Given
        ProductGroupDto productGroupDto = ProductGroupDto
                .builder()
                .productGroupId(1L)
                .groupName("Vegetables")
                .build();
        ProductGroup productGroup1 = new ProductGroup(1L, "Vegetables", new ArrayList<>());
        Optional<ProductGroup> productGroup = Optional.of(productGroup1);

        when(productGroupService.getProductGroup(ArgumentMatchers.any())).thenReturn(productGroup);
        when(productGroupMapper.mapToProductGroupDto(ArgumentMatchers.any())).thenReturn(productGroupDto);

        //When & Then
        mockMvc.perform(get("/v1/productgroups/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productGroupId", is(productGroupDto.getProductGroupId().intValue())))
                .andExpect(jsonPath("$.groupName", is("Vegetables")));

    }

    @Test
    public void shouldUpdateProductGroup() throws Exception {
        //Given
        ProductGroupDto productGroupDto = ProductGroupDto
                .builder()
                .productGroupId(1L)
                .groupName("Vegetables")
                .build();
        ProductGroup productGroup1 = new ProductGroup(1L, "Vegetables", new ArrayList<>());

        Optional<ProductGroup> productGroup = Optional.of(productGroup1);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(productGroupDto);

        when(productGroupService.getProductGroup(ArgumentMatchers.any())).thenReturn(productGroup);
        when(productGroupMapper.mapToProductGroupDto(ArgumentMatchers.any())).thenReturn(productGroupDto);

        //When & Then
        mockMvc.perform(put("/v1/productgroups").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8").content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productGroupId", is(productGroupDto.getProductGroupId().intValue())))
                .andExpect(jsonPath("$.groupName", is("Vegetables")));

    }


    @Test
    public void shouldCreateProductGroup() throws Exception {
        //Given
        ProductGroupDto productGroupDto = ProductGroupDto
                .builder()
                .productGroupId(1L)
                .groupName("Vegetables")
                .build();
        ProductGroup productGroup1 = new ProductGroup(1L, "Vegetables", new ArrayList<>());

        Gson gson = new Gson();
        String jsonContent = gson.toJson(productGroupDto);

        when(productGroupMapper.mapToProductGroup(any(ProductGroupDto.class))).thenReturn(productGroup1);
        when(productGroupMapper.mapToProductGroupDto(productGroup1)).thenReturn(productGroupDto);
        when(productGroupService.saveProductGroup(any(ProductGroup.class))).then(returnsFirstArg());

        System.out.println(jsonContent);
        //When & Then
        mockMvc.perform(post("/v1/productgroups")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productGroupId", is(1)))
                .andExpect(jsonPath("$.groupName", is("Vegetables")));


    }

    @Test
    public void shouldDeleteProductGroup() throws Exception {
        //When&Then
        mockMvc.perform(delete("/v1/productgroups/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(productGroupService, times(1)).deleteProductGroup(1L);
    }


}