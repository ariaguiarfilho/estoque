package one.digitalinovvation.estoque.controller;

import one.digitalinovvation.estoque.builder.WaterDTOBuilder;
import one.digitalinovvation.estoque.dto.WaterDto;
import one.digitalinovvation.estoque.entity.Water;
import one.digitalinovvation.estoque.exception.WaterNotFoundException;
import one.digitalinovvation.estoque.service.WaterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import one.digitalinovvation.estoque.utils.JsonConvertionUtils;

import static one.digitalinovvation.estoque.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class WaterControllerTest {

    private static final String BEER_API_URL_PATH = "/api/v1/waters";
    private static final long VALID_WATER_ID = 1L;
    private static final long INVALID_WATER_ID = 2l;
    private static final String BEER_API_SUBPATH_INCREMENT_URL = "/increment";
    private static final String BEER_API_SUBPATH_DECREMENT_URL = "/decrement";


    private MockMvc mockMvc;

    @Mock
    private WaterService waterService;

    @InjectMocks
    private WaterController waterController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(waterController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenAWaterIsCreated() throws Exception {
        // given
        WaterDto waterDTO = WaterDTOBuilder.builder().build().toWaterDTO();

        // when
        when(waterService.createWater(waterDTO)).thenReturn(waterDTO);

        // then
        mockMvc.perform(post(BEER_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(waterDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(waterDTO.getName())))
                .andExpect(jsonPath("$.brand", is(waterDTO.getBrand())))
                .andExpect(jsonPath("$.type", is(waterDTO.getType().toString())));
    }


    @Test
    void whenPOSTIsCalledWithoutRequiredFieldThenAnErrorIsReturned() throws Exception {
        // given
        WaterDto waterDTO = WaterDTOBuilder.builder().build().toWaterDTO();
        waterDTO.setBrand(null);

        // then
        mockMvc.perform(post(BEER_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(waterDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGETIsCalledWithValidNameThenOkStatusIsReturned() throws Exception {
        // given
        WaterDto waterDTO = WaterDTOBuilder.builder().build().toWaterDTO();

        //when
        when(waterService.findByName(waterDTO.getName())).thenReturn(waterDTO);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(BEER_API_URL_PATH + "/" + waterDTO.getName())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(waterDTO.getName())))
                .andExpect(jsonPath("$.brand", is(waterDTO.getBrand())))
                .andExpect(jsonPath("$.type", is(waterDTO.getType().toString())));
    }

    @Test
    void whenDELETEIsCalledWithValidIdThenNoContentStatusIsReturned() throws Exception {
        // given
        WaterDto waterDTO = WaterDTOBuilder.builder().build().toWaterDTO();

        //when
        doNothing().when(waterService).deleteById(waterDTO.getId());

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(BEER_API_URL_PATH + "/" + waterDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }


}