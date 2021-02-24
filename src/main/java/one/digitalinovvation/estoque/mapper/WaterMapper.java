package one.digitalinovvation.estoque.mapper;

import one.digitalinovvation.estoque.dto.WaterDto;
import one.digitalinovvation.estoque.entity.Water;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import static org.mapstruct.factory.Mappers.*;

@Mapper
public interface WaterMapper {

    WaterMapper INSTANCE = getMapper(WaterMapper.class);

    Water toModel(WaterDto waterDto);

    WaterDto toDTO(Water water);

}
