package repository;

import entity.Auteur;
import entity.Categorie;
import entity.Livre;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LivreRepository extends CrudRepository<Livre,Long > {


    List<Livre> findByCategorie(Categorie categorie);

    List<Livre> findByAuteur(Auteur auteur);
}
