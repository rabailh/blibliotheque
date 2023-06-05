package controller;

import entity.Auteur;
import entity.Categorie;
import entity.Livre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.AuteurRepository;
import repository.LivreRepository;
import service.EmpruntService;
import service.LivreService;

import java.util.List;
        import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class LivreController {

    @Autowired
    private LivreService livreService;

    @Autowired
    private EmpruntService empruntService;

    @Autowired
    private AuteurRepository auteurRepository;

    @GetMapping("livres")
    public List<Livre> findAll() {
        return (List<Livre>) livreService.findAllLivres();
    }

    @GetMapping("livre/{id}")
    public ResponseEntity<Livre> findLivreById(@PathVariable(value = "id") long id) {
        Optional<Livre> livre = livreService.findLivreById(id);
        return livre.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("livre")
    public ResponseEntity<Livre> createLivre(@RequestBody Livre livre) {
        Livre createdLivre = livreService.createLivre(livre);
        return new ResponseEntity<>(createdLivre, HttpStatus.CREATED);
    }

    @PutMapping("livre/{id}")
    public ResponseEntity<Livre> updateLivre(@PathVariable(value = "id") long id, @RequestBody Livre livreDetails) {
        Livre updatedLivre = livreService.updateLivre(id, livreDetails);
        return ResponseEntity.ok(updatedLivre);
    }

    @DeleteMapping("livre/{id}")
    public ResponseEntity<Void> deleteLivre(@PathVariable(value = "id") long id) {
        livreService.deleteLivre(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("livre/{id}/emprunts/count")
    public ResponseEntity<Integer> countEmpruntsParLivre(@PathVariable(value = "id") long id) {
        Optional<Livre> livre = livreService.findLivreById(id);
        if (livre.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        int nombreEmprunts = empruntService.countEmpruntsParLivre(livre.get());
        return ResponseEntity.ok(nombreEmprunts);
    }

    @GetMapping("livres/auteur/{id}")
    public ResponseEntity<List<Livre>> getLivresParAuteur(@PathVariable(value = "id") long idAuteur) {
        Optional<Auteur> auteur = auteurRepository.findAuteurById(idAuteur);
        if (auteur.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<Livre> livres = livreService.getLivresParAuteur(auteur.get());
        return ResponseEntity.ok(livres);
    }

}