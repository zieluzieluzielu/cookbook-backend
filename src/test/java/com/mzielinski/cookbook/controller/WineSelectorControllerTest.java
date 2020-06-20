package com.mzielinski.cookbook.controller;

import com.mzielinski.cookbook.domain.Product;
import com.mzielinski.cookbook.domain.ProductGroup;
import com.mzielinski.cookbook.domain.dto.ProductMatchesDto;
import com.mzielinski.cookbook.domain.dto.SpoonacularDto;
import com.mzielinski.cookbook.service.ProductService;
import com.mzielinski.cookbook.spoonacular.facade.SpoonacularFacade;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(WineSelectorController.class)
public class WineSelectorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpoonacularFacade spoonacularFacade;

    @MockBean
    private ProductService productService;

    @Test
    public void shouldFetchWineByProduct() throws Exception {
        //Given

        List<String> wineTypeList = new ArrayList<>();
        wineTypeList.add("merlot");
        wineTypeList.add("chardonnay");
        List<ProductMatchesDto> productMatchesList = new ArrayList<>();
        productMatchesList.add(new ProductMatchesDto("Wine #1", "description", "10 USD"));
        SpoonacularDto spoonacularDto = new SpoonacularDto(wineTypeList, "test pairing text", productMatchesList);

        Product product1 = new Product(1L, "Chicken breast", new ProductGroup(), new ArrayList<>());
        Optional<Product> product = Optional.of(product1);

        final ResponseEntity<SpoonacularDto> result = new ResponseEntity(spoonacularDto, HttpStatus.OK);

        when(productService.getProduct(1L)).thenReturn(product);
        when(spoonacularFacade.getWineDetailsResponseEntity(any())).thenReturn(result);

        //When & Then

        mockMvc.perform(get("/v1/wine/product/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pairingText", is("test pairing text")))
                .andExpect(jsonPath("$.pairedWines.[0]", is("merlot")))
                .andExpect(jsonPath("$.pairedWines", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.pairedWines.[0]", is("merlot")))
                .andExpect(jsonPath("$.productMatches", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.productMatches.[0].title", is("Wine #1")))
                .andExpect(jsonPath("$.productMatches.[0].description", is("description")))
                .andExpect(jsonPath("$.productMatches.[0].price", is("10 USD")));


    }

    @Test
    public void shouldFetchWineByRecipe() throws Exception {
        //Given

        List<String> wineTypeList = new ArrayList<>();
        wineTypeList.add("merlot");
        wineTypeList.add("chardonnay");
        List<ProductMatchesDto> productMatchesList = new ArrayList<>();
        productMatchesList.add(new ProductMatchesDto("Wine #1", "description", "10 USD"));
        SpoonacularDto spoonacularDto = new SpoonacularDto(wineTypeList, "test pairing text", productMatchesList);

        Product product1 = new Product(1L, "Chicken breast", new ProductGroup(), new ArrayList<>());
        Optional<Product> product = Optional.of(product1);


        final ResponseEntity<SpoonacularDto> result = new ResponseEntity(spoonacularDto, HttpStatus.OK);

        when(productService.getMainProductFromRecipe(1L)).thenReturn(product);
        when(spoonacularFacade.getWineDetailsResponseEntity(any())).thenReturn(result);

        //When & Then

        mockMvc.perform(get("/v1/wine/recipe/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pairingText", is("test pairing text")))
                .andExpect(jsonPath("$.pairedWines.[0]", is("merlot")))
                .andExpect(jsonPath("$.pairedWines", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.pairedWines.[0]", is("merlot")))
                .andExpect(jsonPath("$.productMatches", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.productMatches.[0].title", is("Wine #1")))
                .andExpect(jsonPath("$.productMatches.[0].description", is("description")))
                .andExpect(jsonPath("$.productMatches.[0].price", is("10 USD")));


    }


}