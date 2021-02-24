package one.digitalinovvation.estoque.repository;

import one.digitalinovvation.estoque.entity.Water;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WaterRepository extends JpaRepository<Water,Long> {
    Optional<Water> findByName(String name);
}
