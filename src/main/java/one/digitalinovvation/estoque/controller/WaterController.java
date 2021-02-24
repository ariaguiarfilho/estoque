package one.digitalinovvation.estoque.controller;

import lombok.AllArgsConstructor;
import one.digitalinovvation.estoque.dto.QuantityDto;
import one.digitalinovvation.estoque.dto.WaterDto;
import one.digitalinovvation.estoque.exception.WaterAlreadyRegisteredException;
import one.digitalinovvation.estoque.exception.WaterNotFoundException;
import one.digitalinovvation.estoque.exception.WaterStockExceededException;
import one.digitalinovvation.estoque.service.WaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/waters")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class WaterController {

    private final WaterService waterService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WaterDto createWater(@RequestBody @Valid WaterDto waterDto) throws WaterAlreadyRegisteredException {
        return waterService.createWater(waterDto);
    }

    @GetMapping("/{name}")
    public WaterDto findByName(@PathVariable String name) throws WaterNotFoundException {
        return waterService.findByName(name);
    }

    @GetMapping
    public List<WaterDto> listWaters() {
        return waterService.listAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) throws WaterNotFoundException {
        waterService.deleteById(id);
    }

    @PatchMapping("/{id}/increment")
    public WaterDto increment(@PathVariable Long id, @RequestBody @Valid QuantityDto quantityDTO) throws WaterNotFoundException, WaterStockExceededException {
        return waterService.increment(id, quantityDTO.getQuantity());
    }
    
}
