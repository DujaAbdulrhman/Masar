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

import java.util.List;

@RestController
@RequestMapping("/api/v1/discount")
public class DiscountController {

    final DiscountService discountService;
    final ProductService productService;

    public DiscountController(DiscountService discountService, ProductService productService) {
        this.discountService = discountService;
        this.productService = productService;
    }

    @PostMapping("/add")
    public ResponseEntity addDiscount(@RequestBody Discount discount) {
        discountService.addDiscount(discount);
        return ResponseEntity.status(200).body(new Api("Discount added successfully"));
    }


    @GetMapping("/getall")
    public ResponseEntity getAllDiscounts() {
        return ResponseEntity.status(200).body(discountService.getAllDiscounts());
    }


    @PutMapping("/update/{id}")
    public ResponseEntity update(@RequestBody Discount discount, @PathVariable int id) {
        discountService.updateDiscount(discount, id);
        return ResponseEntity.status(200).body(new Api("Discount updated successfully"));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable int id) {
        discountService.deleteDiscount(id);
        return ResponseEntity.status(200).body(new Api("Discount deleted successfully"));
    }


    @PostMapping("/apply/{pId}")
    public ResponseEntity<Product> applyDiscount(@PathVariable Integer pId) {
        Product updatedProduct = productService.applyDiscount(pId); // This assumes applyDiscount is implemented in ProductService
        return ResponseEntity.ok(updatedProduct);
    }

    // 2
    @GetMapping("/workshop/{wId}")
    public ResponseEntity getDiscountsByWorkshop(@PathVariable Integer wId) {
        Double discounts = discountService.getDiscountRateByWorkshop(wId);
        return ResponseEntity.status(200).body(discounts);
    }
}
