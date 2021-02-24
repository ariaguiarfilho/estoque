package one.digitalinovvation.estoque.builder;

import lombok.Builder;
import one.digitalinovvation.estoque.dto.WaterDto;
import one.digitalinovvation.estoque.enums.WaterType;

@Builder
public class WaterDTOBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String name = "Brahma";

    @Builder.Default
    private String brand = "Ambev";

    @Builder.Default
    private int max = 50;

    @Builder.Default
    private int quantity = 10;

    @Builder.Default
    private WaterType type = WaterType.WITH_GAS;

    public WaterDto toWaterDTO() {
        return new WaterDto(id,
                name,
                brand,
                max,
                quantity,
                type);
    }
}
