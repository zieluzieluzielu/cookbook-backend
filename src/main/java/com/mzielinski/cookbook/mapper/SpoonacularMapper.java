package com.mzielinski.cookbook.mapper;

import com.mzielinski.cookbook.domain.ProductMatches;
import com.mzielinski.cookbook.domain.Spoonacular;
import com.mzielinski.cookbook.domain.dto.ProductMatchesDto;
import com.mzielinski.cookbook.domain.dto.SpoonacularDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SpoonacularMapper {

    public Spoonacular mapToSpoonacular(final SpoonacularDto spoonacularDto) {
        return new Spoonacular(spoonacularDto.getWineTypeDtoList(), spoonacularDto.getPairingText(), mapToProductMatchesList(spoonacularDto.getProductMatchesDto()));
    }

    public SpoonacularDto mapToSpoonacularDto(final Spoonacular spoonacular) {
        return new SpoonacularDto(spoonacular.getWineTypeDtoList(), spoonacular.getPairingText(), mapToProductMatchesListDto(spoonacular.getProductMatchesDto()));
    }


    public List<ProductMatchesDto> mapToProductMatchesListDto(final List<ProductMatches> productMatchesList) {
        return productMatchesList.stream()
                .map(productMatchesDto -> new ProductMatchesDto(productMatchesDto.getWineTitle(), productMatchesDto.getWineDescription(), productMatchesDto.getWinePrice()))
                .collect(Collectors.toList());
    }


    public Spoonacular mapToSpoonacularFromResponseEntity(final ResponseEntity<SpoonacularDto> spoonacularDto) {
        return new Spoonacular(spoonacularDto.getBody().getWineTypeDtoList(), spoonacularDto.getBody().getPairingText(), mapToProductMatchesList(spoonacularDto.getBody().getProductMatchesDto()));
    }

/*

    public List<ProductMatches> mapToProductMatchesListFromResponseEntity(final List<ProductMatchesDto> productMatchesListDto) {
        return productMatchesListDto.stream()
                .map(productMatches -> new ProductMatches(productMatches.getWineTitle(), productMatches.getWineDescription(), productMatches.getWinePrice()))
                .collect(Collectors.toList());
    }
*/


    public List<ProductMatches> mapToProductMatchesList(final List<ProductMatchesDto> productMatchesListDto) {
        return productMatchesListDto.stream()
                .map(productMatches -> new ProductMatches(productMatches.getWineTitle(), productMatches.getWineDescription(), productMatches.getWinePrice()))
                .collect(Collectors.toList());
    }


}
