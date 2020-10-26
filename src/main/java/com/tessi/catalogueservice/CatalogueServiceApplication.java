package com.tessi.catalogueservice;

import com.tessi.catalogueservice.dao.ProduitRepository;
import com.tessi.catalogueservice.entities.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class CatalogueServiceApplication implements CommandLineRunner {
    @Autowired
    private ProduitRepository produitRepository;
    @Autowired
    private RepositoryRestConfiguration restConfiguration;

    public static void main(String[] args) {
        SpringApplication.run(CatalogueServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        restConfiguration.exposeIdsFor(Produit.class);
       /* produitRepository.save(new Produit(null,"ordinateur",67500,3));
        produitRepository.save(new Produit(null,"portable",800,3));
        produitRepository.save(new Produit(null,"TV",1500,3));
        produitRepository.save(new Produit(null,"climatisseur",2200,3));
        produitRepository.findAll().forEach(p->{
            System.out.println(p.toString());
        });*/


    }
}
