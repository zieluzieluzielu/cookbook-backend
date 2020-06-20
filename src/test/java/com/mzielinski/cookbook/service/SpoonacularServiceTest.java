package com.mzielinski.cookbook.service;

import com.mzielinski.cookbook.domain.Product;
import com.mzielinski.cookbook.domain.ProductGroup;
import com.mzielinski.cookbook.domain.dto.ProductMatchesDto;
import com.mzielinski.cookbook.domain.dto.SpoonacularDto;
import com.mzielinski.cookbook.spoonacular.client.SpoonacularClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SpoonacularServiceTest {

    @InjectMocks
    SpoonacularService spoonacularService;

    @Mock
    SpoonacularClient spoonacularClient;

    @Test
    public void getWineDetails() {
        //Given
        List<String> wineTypeList = new ArrayList<>();
        wineTypeList.add("merlot");
        wineTypeList.add("chardonnay");
        List<ProductMatchesDto> productMatchesList = new ArrayList<>();
        productMatchesList.add(new ProductMatchesDto("Wine #1", "description", "10 USD"));
        SpoonacularDto spoonacularDto = new SpoonacularDto(wineTypeList, "test pairing text", productMatchesList);
        Product product = new Product(1L, "Chicken", new ProductGroup(), new ArrayList<>());

        ResponseEntity<SpoonacularDto> spoonacularDtoResponseEntity = new ResponseEntity(spoonacularDto, HttpStatus.OK);

        when(spoonacularClient.getWineData(any())).thenReturn(spoonacularDtoResponseEntity);

        //When
        ResponseEntity<SpoonacularDto> getWineData = spoonacularService.getWine(product);

        //Then
        assertEquals("merlot", getWineData.getBody().getWineTypeDtoList().get(0));
        assertEquals(2, getWineData.getBody().getWineTypeDtoList().size());
        assertEquals("test pairing text", getWineData.getBody().getPairingText());
        assertEquals("Wine #1", getWineData.getBody().getProductMatchesDto().get(0).getWineTitle());
        assertEquals("description", getWineData.getBody().getProductMatchesDto().get(0).getWineDescription());
        assertEquals("10 USD", getWineData.getBody().getProductMatchesDto().get(0).getWinePrice());


    }


}

