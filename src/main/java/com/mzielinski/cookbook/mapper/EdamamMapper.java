package com.mzielinski.cookbook.mapper;

import com.mzielinski.cookbook.domain.*;
import com.mzielinski.cookbook.domain.dto.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EdamamMapper {

    public List<EdamamDto> mapToEdamamDtoList(final List<Edamam> edamamList) {
        return edamamList.stream()
                .map(this::mapToEdamamDto)
                .collect(Collectors.toList());
    }

    public List<Edamam> mapToEdamamList(final List<EdamamDto> edamamDtoList) {
        return edamamDtoList.stream()
                .map(this::mapToEdamam)
                .collect(Collectors.toList());
    }


    public Edamam mapToEdamam(final EdamamDto edamamDto) {
        return new Edamam(mapToNutrients(edamamDto.getNutrientsDto()));
    }

    public Kcal mapToKcal(final KcalDto kcalDto) {
        return new Kcal(kcalDto.getQuantity(), kcalDto.getUnit());
    }

    public Carbohydrates mapToCarbohydrates(final CarbohydratesDto carbohydratesDto) {
        return new Carbohydrates(carbohydratesDto.getQuantity(), carbohydratesDto.getUnit());
    }

    public Protein mapToProtein(final ProteinDto proteinDto) {
        return new Protein(proteinDto.getQuantity(), proteinDto.getUnit());
    }

    public Fat mapToFat(final FatDto fatDto) {
        return new Fat(fatDto.getQuantity(), fatDto.getUnit());
    }

    public Nutrients mapToNutrients(final NutrientsDto nutrientsDto) {
        return new Nutrients(mapToKcal(nutrientsDto.getKcalDto()), (mapToFat(nutrientsDto.getFatDto())), mapToProtein(nutrientsDto.getProteinDto()), mapToCarbohydrates(nutrientsDto.getCarbohydratesDto()));
    }

    public EdamamDto mapToEdamamDto(final Edamam edamam) {
        return new EdamamDto(mapToNutrientsDto(edamam.getNutrients()));

    }

    public KcalDto mapToKcalDto(final Kcal kcal) {
        return new KcalDto(kcal.getQuantity(), kcal.getUnit());
    }

    public CarbohydratesDto mapToCarbohydratesDto(final Carbohydrates carbohydrates) {
        return new CarbohydratesDto(carbohydrates.getQuantity(), carbohydrates.getUnit());
    }

    public ProteinDto mapToProteinDto(final Protein protein) {
        return new ProteinDto(protein.getQuantity(), protein.getUnit());
    }

    public FatDto mapToFatDto(final Fat fat) {
        return new FatDto(fat.getQuantity(), fat.getUnit());
    }


    public NutrientsDto mapToNutrientsDto(final Nutrients nutrients) {
        return new NutrientsDto(mapToKcalDto(nutrients.getKcal()), (mapToFatDto(nutrients.getFat())), mapToProteinDto(nutrients.getProtein()), mapToCarbohydratesDto(nutrients.getCarbohydrates()));
    }


}
