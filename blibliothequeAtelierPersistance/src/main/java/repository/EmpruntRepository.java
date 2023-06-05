package repository;

import entity.Emprunt;
import entity.Livre;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface EmpruntRepository extends CrudRepository<Emprunt,Long > {
    List<Emprunt> findByDateEmpruntBetween(LocalDate dateDebut, LocalDate dateFin);

    int countByLivre(Livre livre);
}
