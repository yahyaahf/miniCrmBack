package com.example.minicrm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Entity
@Table(name = "bon de commande")
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dateDebut;

    private Date dateExpiration;

    private long prixTotal;

    private String quantities;

    private double TVA= 0.2;

    private String numBonCommande;

    private String description;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToMany()
    private Collection<Product> listProduit= new ArrayList<>();

    private String generateNumBonCommande() {
        int num = (int) (Math.random() * 10000);
        return "BON-" + String.format("%04d", num);
    }


    @PrePersist
    private void generateNumBonCommandeBeforePersist() {
        if (numBonCommande == null || numBonCommande.isEmpty()) {
            this.numBonCommande = generateNumBonCommande();
        }
    }

}
