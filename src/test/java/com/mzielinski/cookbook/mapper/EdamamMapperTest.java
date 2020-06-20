package com.mzielinski.cookbook.mapper;

import com.mzielinski.cookbook.domain.*;
import com.mzielinski.cookbook.domain.dto.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class EdamamMapperTest {
    @InjectMocks
    EdamamMapper edamamMapper;

    @Test
    public void mapToEdamamTest() {
        //Given
        EdamamDto edamamDto = new EdamamDto(new NutrientsDto(new KcalDto(new BigDecimal("500"), "kcal"), new FatDto(new BigDecimal("20"), "g"), new ProteinDto(new BigDecimal("100"), "g"), new CarbohydratesDto(new BigDecimal("25"), "g")));

        //When
        Edamam edamam = edamamMapper.mapToEdamam(edamamDto);

        //Then
        assertEquals(edamamDto.getNutrientsDto().getCarbohydratesDto().getQuantity(), edamam.getNutrients().getCarbohydrates().getQuantity());
        assertEquals(edamamDto.getNutrientsDto().getKcalDto().getUnit(), edamam.getNutrients().getKcal().getUnit());
        assertEquals(edamamDto.getNutrientsDto().getFatDto().getQuantity(), edamam.getNutrients().getFat().getQuantity());

    }

    public void mapToEdamamDtoTest() {
        //Given
        Edamam edamam = new Edamam(new Nutrients(new Kcal(new BigDecimal("500"), "kcal"), new Fat(new BigDecimal("20"), "g"), new Protein(new BigDecimal("100"), "g"), new Carbohydrates(new BigDecimal("25"), "g")));

        //When
        EdamamDto edamamDto = edamamMapper.mapToEdamamDto(edamam);

        //Then
        assertEquals(edamam.getNutrients().getCarbohydrates().getQuantity(), edamamDto.getNutrientsDto().getCarbohydratesDto().getQuantity());
        assertEquals(edamam.getNutrients().getKcal().getUnit(), edamamDto.getNutrientsDto().getKcalDto().getUnit());
        assertEquals(edamam.getNutrients().getFat().getQuantity(), edamamDto.getNutrientsDto().getFatDto().getQuantity());

    }


    public void mapToKcalTest() {
        //Given
        KcalDto kcalDto = new KcalDto(new BigDecimal("500"), "kcal");

        //When
        Kcal kcal = edamamMapper.mapToKcal(kcalDto);

        //Then
        assertEquals(kcalDto.getQuantity(), kcal.getQuantity());
        assertEquals(kcalDto.getUnit(), kcal.getUnit());


    }

    public void mapToKcalDtoTest() {
        //Given
        Kcal kcal = new Kcal(new BigDecimal("500"), "kcal");

        //When
        KcalDto kcalDto = edamamMapper.mapToKcalDto(kcal);

        //Then
        assertEquals(kcal.getQuantity(), kcalDto.getQuantity());
        assertEquals(kcal.getUnit(), kcalDto.getUnit());

    }

    public void mapToCarbohydratesTest() {
        //Given
        CarbohydratesDto carbohydratesDto = new CarbohydratesDto(new BigDecimal("200"), "g");

        //When
        Carbohydrates carbohydrates = edamamMapper.mapToCarbohydrates(carbohydratesDto);

        //Then
        assertEquals(carbohydratesDto.getQuantity(), carbohydrates.getQuantity());
        assertEquals(carbohydratesDto.getUnit(), carbohydrates.getUnit());
    }

    public void mapToCarbohydratesDtoTest() {
        //Given
        Carbohydrates carbohydrates = new Carbohydrates(new BigDecimal("200"), "g");

        //When
        CarbohydratesDto carbohydratesDto = edamamMapper.mapToCarbohydratesDto(carbohydrates);

        //Then
        assertEquals(carbohydrates.getQuantity(), carbohydratesDto.getQuantity());
        assertEquals(carbohydrates.getUnit(), carbohydratesDto.getUnit());

    }

    public void mapToProteinTest() {
        //Given
        ProteinDto proteinDto = new ProteinDto(new BigDecimal("100"), "g");
        //When
        Protein protein = edamamMapper.mapToProtein(proteinDto);

        //Then
        assertEquals(proteinDto.getQuantity(), protein.getQuantity());
        assertEquals(proteinDto.getUnit(), protein.getUnit());
    }

    public void mapToProteinDtoTest() {
        //Given
        Protein protein = new Protein(new BigDecimal("100"), "g");

        //When
        ProteinDto proteinDto = edamamMapper.mapToProteinDto(protein);

        //Then
        assertEquals(protein.getQuantity(), proteinDto.getQuantity());
        assertEquals(protein.getUnit(), proteinDto.getUnit());
    }

    public void mapToFatTest() {
        //Given
        FatDto fatDto = new FatDto(new BigDecimal("50"), "g");

        //When
        Fat fat = edamamMapper.mapToFat(fatDto);

        //Then
        assertEquals(fatDto.getQuantity(), fat.getQuantity());
        assertEquals(fatDto.getUnit(), fat.getUnit());
    }

    public void mapToFatDtoTest() {
        //Given
        Fat fat = new Fat(new BigDecimal("50"), "g");

        //When
        FatDto fatDto = edamamMapper.mapToFatDto(fat);

        //Then
        assertEquals(fat.getQuantity(), fatDto.getQuantity());
        assertEquals(fat.getUnit(), fatDto.getUnit());
    }


    public void mapToNutrientsTest() {
        //Given
        NutrientsDto nutrientsDto = new NutrientsDto(new KcalDto(new BigDecimal("500"), "kcal"), new FatDto(new BigDecimal("20"), "g"), new ProteinDto(new BigDecimal("100"), "g"), new CarbohydratesDto(new BigDecimal("25"), "g"));

        //When
        Nutrients nutrients = edamamMapper.mapToNutrients(nutrientsDto);

        //Then
        assertEquals(nutrientsDto.getCarbohydratesDto().getQuantity(), nutrients.getCarbohydrates().getQuantity());
    }

    public void mapToNutrientsDtoTest() {
        //Given
        Nutrients nutrients = new Nutrients(new Kcal(new BigDecimal("500"), "kcal"), new Fat(new BigDecimal("20"), "g"), new Protein(new BigDecimal("100"), "g"), new Carbohydrates(new BigDecimal("25"), "g"));

        //When
        NutrientsDto nutrientsDto = edamamMapper.mapToNutrientsDto(nutrients);

        //Then
        assertEquals(nutrients.getCarbohydrates().getQuantity(), nutrientsDto.getCarbohydratesDto().getQuantity());

    }

}
