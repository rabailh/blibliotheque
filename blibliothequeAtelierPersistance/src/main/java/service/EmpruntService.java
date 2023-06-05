package service;

import entity.Emprunt;
import entity.Livre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.EmpruntRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EmpruntService {

    @Autowired
    private EmpruntRepository empruntRepository;


    public List<Emprunt> findAllEmprunts() {
        return (List<Emprunt>) empruntRepository.findAll();
    }

    public Optional<Emprunt> findEmpruntById(long id) {
        return empruntRepository.findById(id);
    }

    public Emprunt createEmprunt(Emprunt emprunt) {
        return empruntRepository.save(emprunt);
    }

    public Emprunt updateEmprunt(long id, Emprunt empruntDetails) {
        Optional<Emprunt> empruntOptional = empruntRepository.findById(id);
        if (empruntOptional.isPresent()) {
            Emprunt emprunt = empruntOptional.get();
            emprunt.setDateEmprunt(empruntDetails.getDateEmprunt());
            emprunt.setDateFinPrevue(empruntDetails.getDateFinPrevue());
            emprunt.setDateRetour(empruntDetails.getDateRetour());
            return empruntRepository.save(emprunt);
        }
        return null;
    }

    public void deleteEmprunt(long id) {
        empruntRepository.deleteById(id);
    }

    public int getNombreEmpruntsSurPlageDates(LocalDate dateDebut, LocalDate dateFin) {
        List<Emprunt> emprunts = empruntRepository.findByDateEmpruntBetween(dateDebut, dateFin);
        return emprunts.size();
    }

    public int countEmpruntsParLivre(Livre livre) {
        return empruntRepository.countByLivre(livre);
    }
}
