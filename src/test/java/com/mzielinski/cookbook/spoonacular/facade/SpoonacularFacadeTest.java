package com.mzielinski.cookbook.spoonacular.facade;

import com.mzielinski.cookbook.domain.Product;
import com.mzielinski.cookbook.domain.ProductGroup;
import com.mzielinski.cookbook.domain.ProductMatches;
import com.mzielinski.cookbook.domain.Spoonacular;
import com.mzielinski.cookbook.domain.dto.ProductMatchesDto;
import com.mzielinski.cookbook.domain.dto.SpoonacularDto;
import com.mzielinski.cookbook.mapper.SpoonacularMapper;
import com.mzielinski.cookbook.service.SpoonacularService;
import com.mzielinski.cookbook.spoonacular.validator.SpoonacularValidator;
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
public class SpoonacularFacadeTest {
    @InjectMocks
    private SpoonacularFacade spoonacularFacade;

    @Mock
    private SpoonacularService spoonacularService;

    @Mock
    private SpoonacularValidator spoonacularValidator;

    @Mock
    private SpoonacularMapper spoonacularMapper;


    @Test
    public void shouldGetWineDetails() {
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
        when(spoonacularValidator.validateWineSelector(any(), any())).thenReturn(spoonacularDtoResponseEntity);
        when(spoonacularMapper.mapToSpoonacularFromResponseEntity(any())).thenReturn(spoonacular);

        //When
        ResponseEntity<SpoonacularDto> getWineData = spoonacularFacade.getWineDetailsResponseEntity(product);

        //Then
        assertEquals("merlot", getWineData.getBody().getWineTypeDtoList().get(0));
        assertEquals(2, getWineData.getBody().getWineTypeDtoList().size());
        assertEquals("test pairing text", getWineData.getBody().getPairingText());
        assertEquals("Wine #1", getWineData.getBody().getProductMatchesDto().get(0).getWineTitle());
        assertEquals("description", getWineData.getBody().getProductMatchesDto().get(0).getWineDescription());
        assertEquals("10 USD", getWineData.getBody().getProductMatchesDto().get(0).getWinePrice());


    }

}
