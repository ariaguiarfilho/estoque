package one.digitalinovvation.estoque.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WaterAlreadyRegisteredException extends  Exception {
    public WaterAlreadyRegisteredException(String waterName) {
        super(String.format("Water with name %s already registered in the system.", waterName));
    }
}
