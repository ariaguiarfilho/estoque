package one.digitalinovvation.estoque.service;

import one.digitalinovvation.estoque.builder.WaterDTOBuilder;
import one.digitalinovvation.estoque.dto.WaterDto;
import one.digitalinovvation.estoque.entity.Water;
import one.digitalinovvation.estoque.exception.WaterAlreadyRegisteredException;
import one.digitalinovvation.estoque.mapper.WaterMapper;
import one.digitalinovvation.estoque.repository.WaterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WaterServiceTest {

    private static final long INVALID_BEER_ID = 1L;

    @Mock
    private WaterRepository waterRepository;

    private WaterMapper waterMapper = WaterMapper.INSTANCE;

    @InjectMocks
    private WaterService waterService;

    @Test
    void whenBeerInformedThenItShouldBeCreated() throws WaterAlreadyRegisteredException {
        // given
        WaterDto expectedWaterDTO = WaterDTOBuilder.builder().build().toWaterDTO();
        Water expectedSavedWater = waterMapper.toModel(expectedWaterDTO);

        // when
        when(waterRepository.findByName(expectedWaterDTO.getName())).thenReturn(Optional.empty());
        when(waterRepository.save(expectedSavedWater)).thenReturn(expectedSavedWater);

        //then
        WaterDto createdWaterDTO = waterService.createWater(expectedWaterDTO);

        assertThat(createdWaterDTO.getId(), is(equalTo(expectedWaterDTO.getId())));
        assertThat(createdWaterDTO.getName(), is(equalTo(expectedWaterDTO.getName())));
        assertThat(createdWaterDTO.getQuantity(), is(equalTo(expectedWaterDTO.getQuantity())));
    }

}
