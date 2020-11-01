package com.tessi.catalogueservice.web;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tessi.catalogueservice.dao.ProduitRepository;
import com.tessi.catalogueservice.entities.Produit;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.ServletContext;
import javax.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.zip.Deflater;

@CrossOrigin("*")
@RestController

public class CatalogueRestController {
    @Autowired
    ServletContext context;
    @Autowired
    ProduitRepository repository;
// TO-do


    @PostMapping("/produitsCatalogue")
    @Transactional
    public ResponseEntity<Response> createArticle (@RequestParam("file") MultipartFile file,
                                                   @RequestParam("article") String article) throws JsonParseException , JsonMappingException , Exception
    {
        System.out.println("Ok .............");
        Produit arti = new ObjectMapper().readValue(article, Produit.class);
        boolean isExit = new File(context.getRealPath("user.home")+"/cinema/images/").exists();
        if (!isExit)
        {
            new File (context.getRealPath("user.home")+"/cinema/images/").mkdir();
            System.out.println("mk dir.............");
        }
        String filename = file.getOriginalFilename();
        String newFileName = FilenameUtils.getBaseName(filename)+"."+FilenameUtils.getExtension(filename);
        File serverFile = new File(System.getProperty("user.home")+"/cinema/images/"+File.separator+newFileName);
        try
        {
            System.out.println("Image"+newFileName);
            FileUtils.writeByteArrayToFile(serverFile,file.getBytes());

        }catch(Exception e) {
            e.printStackTrace();
        }


        arti.setFileName(newFileName);
        Produit art = repository.save(arti);
        if (art != null)
        {
            return new ResponseEntity<Response>(new Response (""),HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<Response>(new Response ("Article not saved"),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping(path = "/imageProduit/{id}",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[]images(@PathVariable(name="id") Long id) throws IOException {
        Produit p =repository.findById(id).get();
        String photoName=p.getFileName();
        File file = new File(System.getProperty("user.home")+"/cinema/images/"+photoName);
        Path path = Paths.get(file.toURI());
        return Files.readAllBytes(path);
    }



    @PutMapping("/updateCatalogue/{id}")
    public ResponseEntity<Produit> updateArticle(@PathVariable("id") long id, @RequestBody Produit Article) {
        System.out.println("Update Article with ID = " + id + "...");
        Optional<Produit> ArticleInfo = repository.findById(id);
        if (ArticleInfo.isPresent()) {
            Produit article = ArticleInfo.get();
            article.setDesignation(Article.getDesignation());
            article.setQuantite(Article.getQuantite());
            article.setPrice(Article.getPrice());
            article.setDescriptionTech(Article.getDescriptionTech());
            return new ResponseEntity<>(repository.save(article), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
