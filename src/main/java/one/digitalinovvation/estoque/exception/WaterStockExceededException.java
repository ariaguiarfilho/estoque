package one.digitalinovvation.estoque.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WaterStockExceededException extends  Exception {
    public WaterStockExceededException(Long id, int quantityToIncrement) {
        super(String.format("Water with %s ID to increment informed exceeds the max stock capacity: %s", id, quantityToIncrement));
    }


}
