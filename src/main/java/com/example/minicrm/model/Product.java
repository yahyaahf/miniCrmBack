package com.example.minicrm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Entity
@Table(name = "produit")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String intitule;

    private String description;

    private String categorie;

    private int prix;

    @ManyToMany()
    @JsonIgnore
    private Collection<Quotation> devis;

    @ManyToMany()
    @JsonIgnore
    private Collection<PurchaseOrder> bonCommande;





}
