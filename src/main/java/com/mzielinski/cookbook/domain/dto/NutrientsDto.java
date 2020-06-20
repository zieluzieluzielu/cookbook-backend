package com.mzielinski.cookbook.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NutrientsDto {
    @JsonProperty("ENERC_KCAL")
    private KcalDto kcalDto;
    @JsonProperty("FAT")
    private FatDto fatDto;
    @JsonProperty("PROCNT")
    private ProteinDto proteinDto;
    @JsonProperty("CHOCDF")
    private CarbohydratesDto carbohydratesDto;
}
