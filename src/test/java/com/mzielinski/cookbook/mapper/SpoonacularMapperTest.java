package com.mzielinski.cookbook.mapper;

import com.mzielinski.cookbook.domain.ProductMatches;
import com.mzielinski.cookbook.domain.Spoonacular;
import com.mzielinski.cookbook.domain.dto.ProductMatchesDto;
import com.mzielinski.cookbook.domain.dto.SpoonacularDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class SpoonacularMapperTest {
    @InjectMocks
    SpoonacularMapper spoonacularMapper;

    @Test
    public void mapToSpoonacularTest() {
        //Given
        List<String> wineTypeList = new ArrayList<>();
        wineTypeList.add("merlot");
        wineTypeList.add("chardonnay");
        List<ProductMatchesDto> productMatchesList = new ArrayList<>();
        productMatchesList.add(new ProductMatchesDto("Wine #1", "description", "10 USD"));
        SpoonacularDto spoonacularDto = new SpoonacularDto(wineTypeList, "test pairing text", productMatchesList);


        //When
        Spoonacular spoonacular = spoonacularMapper.mapToSpoonacular(spoonacularDto);

        //Then
        assertEquals(spoonacularDto.getPairingText(), spoonacular.getPairingText());
    }

    @Test
    public void mapToSpoonacularDtoTest() {
        //Given
        List<String> wineTypeList = new ArrayList<>();
        wineTypeList.add("merlot");
        wineTypeList.add("chardonnay");
        List<ProductMatches> productMatchesList = new ArrayList<>();
        productMatchesList.add(new ProductMatches("Wine #1", "description", "10 USD"));
        Spoonacular spoonacular = new Spoonacular(wineTypeList, "test pairing text", productMatchesList);


        //When
        SpoonacularDto spoonacularDto = spoonacularMapper.mapToSpoonacularDto(spoonacular);
        //Then
        assertEquals(spoonacular.getPairingText(), spoonacularDto.getPairingText());
    }

    @Test
    public void mapToSpoonacularFromResponseEntityTest() {
        List<String> wineTypeList = new ArrayList<>();
        wineTypeList.add("merlot");
        wineTypeList.add("chardonnay");
        List<ProductMatchesDto> productMatchesList = new ArrayList<>();
        productMatchesList.add(new ProductMatchesDto("Wine #1", "description", "10 USD"));
        SpoonacularDto spoonacularDto = new SpoonacularDto(wineTypeList, "test pairing text", productMatchesList);

        ResponseEntity<SpoonacularDto> spoonacularDtoResponseEntity = new ResponseEntity(spoonacularDto, HttpStatus.OK);

        //When
        Spoonacular spoonacular = spoonacularMapper.mapToSpoonacularFromResponseEntity(spoonacularDtoResponseEntity);

        //Then
        assertEquals(spoonacularDtoResponseEntity.getBody().getPairingText(), spoonacular.getPairingText());


    }


    @Test
    public void mapToProductMatchesListTest() {
        //Given
        List<ProductMatchesDto> productMatchesListDto = new ArrayList<>();
        ProductMatchesDto productMatchesDto1 = new ProductMatchesDto("Wine #1", "description1", "10 USD");
        ProductMatchesDto productMatchesDto2 = new ProductMatchesDto("Wine #2", "description2", "20 USD");
        productMatchesListDto.add(productMatchesDto1);
        productMatchesListDto.add(productMatchesDto2);

        //When
        List<ProductMatches> productMatchesList = spoonacularMapper.mapToProductMatchesList(productMatchesListDto);

        //Then
        assertEquals(2, productMatchesList.size());
        assertEquals("10 USD", productMatchesList.get(0).getWinePrice());
        assertEquals("Wine #2", productMatchesList.get(1).getWineTitle());
        assertEquals("description2", productMatchesList.get(1).getWineDescription());

    }

    @Test
    public void mapToProductMatchesDtoListTest() {
        //Given
        List<ProductMatches> productMatchesList = new ArrayList<>();
        ProductMatches productMatches1 = new ProductMatches("Wine #1", "description1", "10 USD");
        ProductMatches productMatches2 = new ProductMatches("Wine #2", "description2", "20 USD");
        productMatchesList.add(productMatches1);
        productMatchesList.add(productMatches2);

        //When
        List<ProductMatchesDto> productMatchesDtoList = spoonacularMapper.mapToProductMatchesListDto(productMatchesList);

        //Then
        assertEquals(2, productMatchesDtoList.size());
        assertEquals("10 USD", productMatchesDtoList.get(0).getWinePrice());
        assertEquals("Wine #2", productMatchesDtoList.get(1).getWineTitle());
        assertEquals("description2", productMatchesDtoList.get(1).getWineDescription());

    }

}
