package com.example.minicrm.model;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Entity
@Table(name = "facture")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dateEmission;

    private Date dateEcheance;

    private long prixTotal=0;

    private long montantPayé=0;

    private String numFacture;

    private String description;

    private String statut="généré";

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    private Quotation devis;

    private String generateNumFacture() {
        int num = (int) (Math.random() * 10000);
        return "Facture-" + String.format("%04d", num);
    }

    @PrePersist
    private void generateNumFactureBeforePersist() {
        if (numFacture == null || numFacture.isEmpty()) {
            this.numFacture = generateNumFacture();
        }
    }


}
