package controller;

import entity.Emprunt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.EmpruntService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class EmpruntController {

    @Autowired
    EmpruntService empruntService;


    @GetMapping("emprunts")
    public List<Emprunt> findAll() {
        return (List<Emprunt>) empruntService.findAllEmprunts();
    }

    @GetMapping("emprunt/{id}")
    public ResponseEntity<Emprunt> findEmpruntById(@PathVariable(value = "id") long id) {
        Optional<Emprunt> emprunt = empruntService.findEmpruntById(id);
        return emprunt.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("emprunt")
    public ResponseEntity<Emprunt> createEmprunt(@RequestBody Emprunt emprunt) {
        Emprunt createdLivre = empruntService.createEmprunt(emprunt);
        return new ResponseEntity<>(createdLivre, HttpStatus.CREATED);
    }

    @PutMapping("emprunt/{id}")
    public ResponseEntity<Emprunt> updateEmprunt(@PathVariable(value = "id") long id, @RequestBody Emprunt empruntDetails) {
        Emprunt updatedEmprunt = empruntService.updateEmprunt(id, empruntDetails);
        return ResponseEntity.ok(updatedEmprunt);
    }

    @DeleteMapping("emprunt/{id}")
    public ResponseEntity<Void> deleteEmprunt(@PathVariable(value = "id") long id) {
        empruntService.deleteEmprunt(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("emprunts/dates")
    public ResponseEntity<Integer> getNombreEmpruntsSurPlageDates(
            @RequestParam(value = "dateDebut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam(value = "dateFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {

        int nombreEmprunts = empruntService.getNombreEmpruntsSurPlageDates(dateDebut, dateFin);
        return ResponseEntity.ok(nombreEmprunts);
    }



}
