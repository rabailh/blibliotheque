package controller;

import entity.Adherent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.AdherentRepository;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class AdherentController {

    @Autowired
    AdherentRepository adherentRepository;

    @GetMapping("adherents")
    public List<Adherent> findAll() {
        return (List<Adherent>) adherentRepository.findAll();
    }

    @GetMapping("adherent/{id}")
    public ResponseEntity<Optional<Adherent>> findAdherentById(@PathVariable(value = "id") long id) {
        Optional<Adherent> adherent = adherentRepository.findById(id);
        if (adherent.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(adherent);
    }

    @PostMapping("adherent")
    public ResponseEntity<Adherent> createAdherent(@RequestBody Adherent adherent) {
        Adherent createdAdherent = adherentRepository.save(adherent);
        return new ResponseEntity<>(createdAdherent, HttpStatus.CREATED);
    }

    @PutMapping("adherent/{id}")
    public ResponseEntity<Adherent> updateAdherent(@PathVariable(value = "id") long id, @RequestBody Adherent adherentDetails) {
        Optional<Adherent> adherentOptional = adherentRepository.findById(id);
        if (adherentOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Adherent adherent = adherentOptional.get();
        adherent.setNom(adherentDetails.getNom());
        adherent.setPrenom(adherentDetails.getPrenom());
        adherent.setEmail(adherentDetails.getEmail());
        adherent.setDateInscription(adherentDetails.getDateInscription());

        Adherent updatedAdherent = adherentRepository.save(adherent);
        return ResponseEntity.ok(updatedAdherent);
    }

    @DeleteMapping("adherent/{id}")
    public ResponseEntity<Void> deleteAdherent(@PathVariable(value = "id") long id) {
        Optional<Adherent> adherentOptional = adherentRepository.findById(id);
        if (adherentOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        adherentRepository.delete(adherentOptional.get());
        return ResponseEntity.noContent().build();
    }
}

