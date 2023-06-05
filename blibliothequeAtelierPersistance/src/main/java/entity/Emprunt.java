package entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Emprunt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "date_emprunt")
    private Date dateEmprunt;

    @Column(name = "date_fin_prevue")
    private Date dateFinPrevue;

    @Column(name = "date_retour")
    private Date dateRetour;

    @ManyToOne
    @JoinColumn(name = "livre_id")
    private Livre livre;

    @ManyToOne
    @JoinColumn(name = "adherent_id")
    private Adherent adherent;
}
