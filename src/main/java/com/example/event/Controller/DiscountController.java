package com.example.event.Controller;

import com.example.event.ApiResponse.Api;
import com.example.event.Model.Discount;
import com.example.event.Model.Product;
import com.example.event.Service.ArtistService;
import com.example.event.Service.DiscountService;
import com.example.event.Service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rating")
public class DiscountController {

    final DiscountService discountService;
    final ProductService productService;
    final ArtistService artistService;


    public DiscountController(DiscountService discountService, ProductService productService, ArtistService artistService) {
        this.discountService = discountService;
        this.productService = productService;
        this.artistService = artistService;
    }

    @PostMapping("/add")
    public ResponseEntity addDiscount(@RequestBody Discount discount){

        discountService.applyDiscount(28.5);
        return ResponseEntity.status(200).body(new Api("rating added successfully"));
    }
    @GetMapping("/getall")
    public ResponseEntity getall(){
        return ResponseEntity.status(200).body(artistService.getall());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@Valid @RequestBody Discount discount, Errors errors, @PathVariable int id){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        discountService.updateRate(discount,id);
        return ResponseEntity.status(200).body(new Api("Artist updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(Errors errors,@PathVariable int id){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        discountService.deleteR(id);
        return ResponseEntity.status(200).body(new Api("Deleted successfully"));
    }





    //2 discount in specific date
    @PostMapping("/apply/{pId}")
    public ResponseEntity<Product> applyDiscount(@PathVariable Integer pId) {
        Product updatedProduct = productService.applyDiscount(pId);
        return ResponseEntity.ok(updatedProduct);
    }



}
