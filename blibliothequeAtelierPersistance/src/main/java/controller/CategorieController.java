package controller;

import entity.Categorie;
import entity.Livre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.CategorieRepository;
import repository.LivreRepository;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class CategorieController {

    @Autowired
    CategorieRepository categorieRepository;

    @Autowired
    LivreRepository livreRepository;

    @GetMapping("categories")
    public List<Categorie> findAll() {
        return (List<Categorie>) categorieRepository.findAll();
    }

    @GetMapping("categorie/{id}")
    public ResponseEntity<Optional<Categorie>> findCategorieById(@PathVariable(value = "id") long id) {
        Optional<Categorie> categorie = categorieRepository.findById(id);
        if (categorie.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(categorie);
    }

    @PostMapping("categorie")
    public ResponseEntity<Categorie> createCategorie(@RequestBody Categorie categorie) {
        Categorie createdCategorie = categorieRepository.save(categorie);
        return new ResponseEntity<>(createdCategorie, HttpStatus.CREATED);
    }

    @PutMapping("categorie/{id}")
    public ResponseEntity<Categorie> updateCategorie(@PathVariable(value = "id") long id, @RequestBody Categorie categorieDetails) {
        Optional<Categorie> categorieOptional = categorieRepository.findById(id);
        if (categorieOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Categorie categorie = categorieOptional.get();
        categorie.setNom(categorieDetails.getNom());

        Categorie updatedCategorie = categorieRepository.save(categorie);
        return ResponseEntity.ok(updatedCategorie);
    }

    @DeleteMapping("categorie/{id}")
    public ResponseEntity<Void> deleteCategorie(@PathVariable(value = "id") long id) {
        Optional<Categorie> categorieOptional = categorieRepository.findById(id);
        if (categorieOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Categorie categorie = categorieOptional.get();
        List<Livre> livres = livreRepository.findByCategorie(categorie);


        for (Livre livre : livres) {
            livre.setCategorie(null);
            livreRepository.save(livre);
        }

        categorieRepository.delete(categorie);
        return ResponseEntity.noContent().build();
    }

}
