package com.mzielinski.cookbook.spoonacular.validator;


import com.mzielinski.cookbook.domain.Product;
import com.mzielinski.cookbook.domain.ProductGroup;
import com.mzielinski.cookbook.domain.ProductMatches;
import com.mzielinski.cookbook.domain.Spoonacular;
import com.mzielinski.cookbook.domain.dto.ProductMatchesDto;
import com.mzielinski.cookbook.domain.dto.SpoonacularDto;
import com.mzielinski.cookbook.service.SpoonacularService;
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
public class SpoonacularValidatorTest {
    @InjectMocks
    private SpoonacularValidator spoonacularValidator;

    @Mock
    private SpoonacularService spoonacularService;

    @Test
    public void validateSpoonacular() {
        //Given

        List<String> wineTypeList = new ArrayList<>();
        wineTypeList.add("merlot");
        wineTypeList.add("chardonnay");
        List<ProductMatches> productMatchesList = new ArrayList<>();
        productMatchesList.add(new ProductMatches("Wine #1", "description", "10 USD"));

        List<ProductMatchesDto> productMatchesDtoList = new ArrayList<>();
        productMatchesDtoList.add(new ProductMatchesDto("Wine #1", "description", "10 USD"));
        Product product = new Product(1L, "Chicken", new ProductGroup(), new ArrayList<>());

        SpoonacularDto spoonacularDto = new SpoonacularDto(wineTypeList, "test pairing text", productMatchesDtoList);
        Spoonacular spoonacular = new Spoonacular(wineTypeList, "test pairing text", productMatchesList);

        ResponseEntity<SpoonacularDto> spoonacularDtoResponseEntity = new ResponseEntity(spoonacularDto, HttpStatus.OK);

        when(spoonacularService.getWine(any())).thenReturn(spoonacularDtoResponseEntity);

        //When
        ResponseEntity<SpoonacularDto> spoonacularValidated = spoonacularValidator.validateWineSelector(spoonacular,product);

        //Then
        assertEquals("merlot", spoonacularValidated.getBody().getWineTypeDtoList().get(0));
        assertEquals("test pairing text", spoonacularValidated.getBody().getPairingText());
        assertEquals("Wine #1", spoonacularValidated.getBody().getProductMatchesDto().get(0).getWineTitle());
        assertEquals("description", spoonacularValidated.getBody().getProductMatchesDto().get(0).getWineDescription());
        assertEquals("10 USD", spoonacularValidated.getBody().getProductMatchesDto().get(0).getWinePrice());


    }
}
