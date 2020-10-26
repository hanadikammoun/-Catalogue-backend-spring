package com.tessi.catalogueservice.web;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tessi.catalogueservice.dao.ProduitRepository;
import com.tessi.catalogueservice.entities.Produit;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.ServletContext;
import java.io.File;
@RestController
@CrossOrigin("*")
public class CatalogueRestController {
    @Autowired
    ServletContext context;
    @Autowired
    ProduitRepository repository;
// TO-do
    @PostMapping("/prodits")
    public ResponseEntity<Response> createArticle (@RequestParam("file") MultipartFile file,
                                                   @RequestParam("article") String article) throws JsonParseException, JsonMappingException, Exception
    {
        System.out.println("Ok .............");
        Produit arti = new ObjectMapper().readValue(article, Produit.class);
        boolean isExit = new File(context.getRealPath("/Images/")).exists();
        if (!isExit)
        {
            new File (context.getRealPath("/Images/")).mkdir();
            System.out.println("mk dir.............");
        }
        String filename = file.getOriginalFilename();
        String newFileName = FilenameUtils.getBaseName(filename)+"."+FilenameUtils.getExtension(filename);
        File serverFile = new File (context.getRealPath("/Images/"+File.separator+newFileName));
        try
        {
            System.out.println("Image");
            FileUtils.writeByteArrayToFile(serverFile,file.getBytes());

        }catch(Exception e) {
            e.printStackTrace();
        }


        arti.setFileName(newFileName);
        Produit art = repository.save(arti);
        if (art != null)
        {
            return new ResponseEntity<Response>(new Response (""), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<Response>(new Response ("Article not saved"),HttpStatus.BAD_REQUEST);
        }
    }
}
