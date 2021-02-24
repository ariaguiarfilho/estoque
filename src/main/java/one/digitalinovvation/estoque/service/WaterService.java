package one.digitalinovvation.estoque.service;

import lombok.AllArgsConstructor;
import one.digitalinovvation.estoque.dto.WaterDto;
import one.digitalinovvation.estoque.entity.Water;
import one.digitalinovvation.estoque.exception.WaterAlreadyRegisteredException;
import one.digitalinovvation.estoque.exception.WaterNotFoundException;
import one.digitalinovvation.estoque.exception.WaterStockExceededException;
import one.digitalinovvation.estoque.mapper.WaterMapper;
import one.digitalinovvation.estoque.repository.WaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class WaterService {

    private final WaterRepository waterRepository;
    private final WaterMapper waterMapper = WaterMapper.INSTANCE;



    public WaterDto createWater(WaterDto waterDto) throws WaterAlreadyRegisteredException {
        verifyIfIsAlreadyRegistered(waterDto.getName());
        Water water = waterMapper.toModel(waterDto);
        Water savedWater = waterRepository.save(water);
        return waterMapper.toDTO(savedWater);
    }

    public WaterDto findByName(String name) throws WaterNotFoundException {
        Water foundWater = waterRepository.findByName(name)
                .orElseThrow(() -> new WaterNotFoundException(name));
        return waterMapper.toDTO(foundWater);
    }

    public List<WaterDto> listAll() {
        return waterRepository.findAll()
                .stream()
                .map(waterMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) throws WaterNotFoundException {
        verifyIfExists(id);
        waterRepository.deleteById(id);
    }



    public WaterDto increment(Long id, int quantityToIncrement) throws WaterNotFoundException, WaterStockExceededException {
        Water WaterToIncrementStock = verifyIfExists(id);
        int quantityAfterIncrement = quantityToIncrement + WaterToIncrementStock.getQuantity();
        if (quantityAfterIncrement <= WaterToIncrementStock.getMax()) {
            WaterToIncrementStock.setQuantity(WaterToIncrementStock.getQuantity() + quantityToIncrement);
            Water incrementedWaterStock = waterRepository.save(WaterToIncrementStock);
            return waterMapper.toDTO(incrementedWaterStock);
        }
        throw new WaterStockExceededException(id, quantityToIncrement);
    }


    private void verifyIfIsAlreadyRegistered(String name) throws WaterAlreadyRegisteredException {
        Optional<Water> optSavedWater = waterRepository.findByName(name);
        if (optSavedWater.isPresent()) {
            throw new WaterAlreadyRegisteredException(name);
        }
    }

    private Water verifyIfExists(Long id) throws WaterNotFoundException {
        return waterRepository.findById(id)
                .orElseThrow(() -> new WaterNotFoundException(id));
    }
}
