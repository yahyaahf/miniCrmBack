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
@Table(name = "Devis")
public class Quotation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dateDebut;

    private Date dateExpiration;

    private long prixTotal;

   private String products;

   private String quantities;

    private double TVA= 0.2;

    private String numDevis;

    private String description;

    private String statut="Non Validé";

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToMany()
    private Collection<Product> listProduit= new ArrayList<>();

    private String generateNumDevis() {
        int num = (int) (Math.random() * 10000);
        return "DEV-" + String.format("%04d", num);
    }


    @PrePersist
    private void generateNumDevisBeforePersist() {
        if (numDevis == null || numDevis.isEmpty()) {
            this.numDevis = generateNumDevis();
        }
    }

    public static long calculatePrix(Quotation quotation){
        String[] quantityStrings = quotation.getQuantities().split(",");


        long totalPrice = 0;

        for (int i = 0; i < quantityStrings.length; i++) {
            int quantity = Integer.parseInt(quantityStrings[i]);
            long prix = ((ArrayList<Product>)quotation.getListProduit()).get(i).getPrix();
            totalPrice += (long) (quantity * prix*(1+quotation.getTVA()));
        }

        return totalPrice;
    }

}
