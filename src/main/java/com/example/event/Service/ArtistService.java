package com.example.event.Controller;


import com.example.event.ApiResponse.Api;
import com.example.event.Model.Artist;
import com.example.event.Service.ArtistService;
import com.example.event.Service.PurchaseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/artist")
public class ArtistController {

    final ArtistService artistService;
    final PurchaseService purchaseService;

    public ArtistController(ArtistService artistService, PurchaseService purchaseService) {
        this.artistService = artistService;
        this.purchaseService = purchaseService;
    }


    @PostMapping("/add")
    public ResponseEntity add(@Valid @RequestBody Artist artist){
        artistService.add(artist);
        return ResponseEntity.status(200).body(new Api("Added successfully"));
    }
    @GetMapping("/getall")
    public ResponseEntity getall(){
        return ResponseEntity.status(200).body(artistService.getall());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@Valid @RequestBody Artist artist, Errors errors, @PathVariable int id){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        artistService.updateArtist(artist,id);
        return ResponseEntity.status(200).body(new Api("Artist updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(Errors errors,@PathVariable int id){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        artistService.deleteArtist(id);
        return ResponseEntity.status(200).body(new Api("Deleted successfully"));
    }

    //-----------------------------------------------


    //1 Promoting the Artist after hitting the products sales target
    @PostMapping("/promote/{threshold}")
    public ResponseEntity upgradeArtists(@PathVariable Double threshold) {
        String result = artistService.upgradeFeaturedArtists(threshold);
        return ResponseEntity.ok(result);
    }






}
