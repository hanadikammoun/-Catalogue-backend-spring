package com.tessi.catalogueservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Data  @NoArgsConstructor @AllArgsConstructor @ToString
public class Produit implements Serializable {
@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String designation;
    private double price;
    private int quantite;
    private String fileName;
    @Column(length = 1000)
    private String descriptionTech;
}

