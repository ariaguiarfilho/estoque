package one.digitalinovvation.estoque.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WaterNotFoundException extends  Exception {
    public WaterNotFoundException(String waterName) {
        super(String.format("Water with name %s not found in the system.", waterName));
    }

    public WaterNotFoundException(Long id) {
        super(String.format("Water with id %s not found in the system.", id));
    }
}
