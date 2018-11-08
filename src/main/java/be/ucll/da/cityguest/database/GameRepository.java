package be.ucll.da.cityguest.database;

import be.ucll.da.cityguest.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GameRepository extends JpaRepository<Game, UUID> {
}
