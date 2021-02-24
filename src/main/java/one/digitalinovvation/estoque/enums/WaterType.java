package one.digitalinovvation.estoque.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WaterType {

    WITH_GAS("With Gas"),
    WITHOUT_GAS("Without Gas"),
    ;

    private final String description;
}
