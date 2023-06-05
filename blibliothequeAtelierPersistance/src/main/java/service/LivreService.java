package service;

import entity.Auteur;
import entity.Categorie;
import entity.Livre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.AuteurRepository;
import repository.CategorieRepository;
import repository.LivreRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LivreService {
    private final LivreRepository livreRepository;
    private final AuteurRepository auteurRepository;
    private final CategorieRepository categorieRepository;

    @Autowired
    public LivreService(LivreRepository livreRepository, AuteurRepository auteurRepository,
                        CategorieRepository categorieRepository) {
        this.livreRepository = livreRepository;
        this.auteurRepository = auteurRepository;
        this.categorieRepository = categorieRepository;
    }

    public Iterable<Livre> findAllLivres() {
        return livreRepository.findAll();
    }

    public Optional<Livre> findLivreById(long id) {
        return livreRepository.findById(id);
    }

    public Livre createLivre(Livre livre) {
        Optional<Auteur> auteurOptional = auteurRepository.findById((long) livre.getAuteur().getId());
        Optional<Categorie> categorieOptional = categorieRepository.findById((long) livre.getCategorie().getId());

        if (auteurOptional.isEmpty() || categorieOptional.isEmpty()) {
            throw new IllegalArgumentException("L'auteur ou la catégorie spécifiée n'existe pas.");
        }

        livre.setAuteur(auteurOptional.get());
        livre.setCategorie(categorieOptional.get());

        return livreRepository.save(livre);
    }

    public Livre updateLivre(long id, Livre livreDetails) {
        Optional<Livre> livreOptional = livreRepository.findById(id);
        if (livreOptional.isEmpty()) {
            throw new IllegalArgumentException("Le livre spécifié n'existe pas.");
        }

        Livre livre = livreOptional.get();

        Optional<Auteur> auteurOptional = auteurRepository.findById((long) livreDetails.getAuteur().getId());
        Optional<Categorie> categorieOptional = categorieRepository.findById((long) livreDetails.getCategorie().getId());

        if (auteurOptional.isEmpty() || categorieOptional.isEmpty()) {
            throw new IllegalArgumentException("L'auteur ou la catégorie spécifiée n'existe pas.");
        }

        livre.setTitre(livreDetails.getTitre());
        livre.setDateDeParution(livreDetails.getDateDeParution());
        livre.setNombreDePages(livreDetails.getNombreDePages());
        livre.setAuteur(auteurOptional.get());
        livre.setCategorie(categorieOptional.get());

        return livreRepository.save(livre);
    }

    public void deleteLivre(long id) {
        Optional<Livre> livreOptional = livreRepository.findById(id);
        if (livreOptional.isEmpty()) {
            throw new IllegalArgumentException("Le livre spécifié n'existe pas.");
        }

        livreRepository.delete(livreOptional.get());
    }

    public List<Livre> getLivresParAuteur(Auteur auteur) {
        return livreRepository.findByAuteur(auteur);
    }
}
