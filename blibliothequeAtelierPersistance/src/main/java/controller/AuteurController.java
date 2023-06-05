package controller;

import entity.Auteur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.AuteurRepository;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class AuteurController {

    @Autowired
    AuteurRepository auteurRepository;

    @GetMapping("auteurs")
    public List<Auteur> findAll() {
        return (List<Auteur>) auteurRepository.findAll();
    }

    @GetMapping("auteur/{id}")
    public ResponseEntity<Optional<Auteur>> findAuteurById(@PathVariable(value = "id") long id) {
        Optional<Auteur> auteur = auteurRepository.findById(id);
        if (auteur.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(auteur);
    }

    @PostMapping("auteur")
    public ResponseEntity<Auteur> createAuteur(@RequestBody Auteur auteur) {
        Auteur createdAuteur = auteurRepository.save(auteur);
        return new ResponseEntity<>(createdAuteur, HttpStatus.CREATED);
    }

    @PutMapping("auteur/{id}")
    public ResponseEntity<Auteur> updateAuteur(@PathVariable(value = "id") long id, @RequestBody Auteur auteurDetails) {
        Optional<Auteur> auteurOptional = auteurRepository.findById(id);
        if (auteurOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Auteur auteur = auteurOptional.get();
        auteur.setNom(auteurDetails.getNom());
        auteur.setPrenom(auteurDetails.getPrenom());

        Auteur updatedAuteur = auteurRepository.save(auteur);
        return ResponseEntity.ok(updatedAuteur);
    }

    @DeleteMapping("auteur/{id}")
    public ResponseEntity<Void> deleteAuteur(@PathVariable(value = "id") long id) {
        Optional<Auteur> auteurOptional = auteurRepository.findById(id);
        if (auteurOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        auteurRepository.delete(auteurOptional.get());
        return ResponseEntity.noContent().build();
    }
}
