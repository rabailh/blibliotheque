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
public class Livre {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "titre")
    private String titre;
    @Column(name = "date_de_parution")
    private Date dateDeParution;

    @Column(name = "nombre_de_pages")
    private int nombreDePages;

    @ManyToOne
    @JoinColumn(name = "auteur_id")
    private Auteur auteur;

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;
}
