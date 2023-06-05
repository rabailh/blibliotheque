package repository;

import entity.Auteur;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AuteurRepository extends CrudRepository<Auteur,Long > {
    Optional<Auteur> findAuteurById(long idAuteur);
}
