package com.mzielinski.cookbook.spoonacular.client;

import com.mzielinski.cookbook.domain.Product;
import com.mzielinski.cookbook.domain.ProductGroup;
import com.mzielinski.cookbook.domain.dto.ProductMatchesDto;
import com.mzielinski.cookbook.domain.dto.SpoonacularDto;
import com.mzielinski.cookbook.spoonacular.config.SpoonacularConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SpoonacularClientTest {

    @InjectMocks
    private SpoonacularClient spoonacularClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private SpoonacularConfig spoonacularConfig;



    @Before
    public void init() {
        when(spoonacularConfig.getSpoonacularApiEndpoint()).thenReturn("http://test.com");
        when(spoonacularConfig.getSpoonacularApiKey()).thenReturn("test");
    }

    @Test
    public void getWineDataTest() throws URISyntaxException {

        //Given
        List<String> wineTypeList = new ArrayList<>();
        wineTypeList.add("merlot");
        wineTypeList.add("chardonnay");
        List<ProductMatchesDto> productMatchesDtoList = new ArrayList<>();
        productMatchesDtoList.add(new ProductMatchesDto("Wine #1", "description", "10 USD"));
        Product product = new Product(1L, "Chicken", new ProductGroup(), new ArrayList<>());
        SpoonacularDto spoonacularDto = new SpoonacularDto(wineTypeList, "test pairing text", productMatchesDtoList);

        ResponseEntity<SpoonacularDto> spoonacularDtoResponseEntity = new ResponseEntity(spoonacularDto, HttpStatus.OK);

        URI uri = new URI("http://test.com?food=Chicken&apiKey=test");

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        when(restTemplate.exchange(uri, HttpMethod.GET, entity, SpoonacularDto.class)).thenReturn(spoonacularDtoResponseEntity);

        //When
        ResponseEntity<SpoonacularDto> getWineData = spoonacularClient.getWineData(product);

        //Then
        assertEquals("merlot", getWineData.getBody().getWineTypeDtoList().get(0));
        assertEquals(2, getWineData.getBody().getWineTypeDtoList().size());
        assertEquals("test pairing text", getWineData.getBody().getPairingText());
        assertEquals("Wine #1", getWineData.getBody().getProductMatchesDto().get(0).getWineTitle());
        assertEquals("description", getWineData.getBody().getProductMatchesDto().get(0).getWineDescription());
        assertEquals("10 USD", getWineData.getBody().getProductMatchesDto().get(0).getWinePrice());
    }


    @Test
    public void uriTest() {

        Product product = new Product(1L, "Chicken breast", new ProductGroup(6L, "Meat", new ArrayList<>()), new ArrayList<>());

        //When
        URI url = spoonacularClient.prepareUrl(product);
        System.out.println(url);

        //Then
        assertEquals("http://test.com?food=Chicken%20breast&apiKey=test", url.toString());


    }

}
