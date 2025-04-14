package com.example.event.Controller;


import com.example.event.ApiResponse.Api;
import com.example.event.Model.Product;
import com.example.event.Service.ProductService;
import com.example.event.Service.PurchaseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    final ProductService productService;
    final PurchaseService purchaseService;

    public ProductController(ProductService productService, PurchaseService purchaseService) {
        this.productService = productService;
        this.purchaseService = purchaseService;
    }

    @PostMapping("/add")
    public ResponseEntity add(@Valid @RequestBody Product product){
        productService.add(product);
        return ResponseEntity.status(200).body(new Api("Added successfully"));
    }
    @GetMapping("/getall")
    public ResponseEntity getall(){
        return ResponseEntity.status(200).body(productService.getall());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@Valid @RequestBody Product product, Errors errors, @PathVariable int id){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        productService.update(product,id);
        return ResponseEntity.status(200).body(new Api("product updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(Errors errors,@PathVariable int id){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        productService.delete(id);
        return ResponseEntity.status(200).body(new Api("product deleted successfully"));
    }

    //-----------------------------------------------

    //2 applying a discount on a product
    @PostMapping("/applyDiscount")
    public ResponseEntity<Product> applyDiscount(@RequestBody Integer productId) {
        //  productId
        Product updatedProduct = productService.applyDiscount(productId);
        return ResponseEntity.status(200).body(updatedProduct);
    }
    //9 gets the best selling products
    @GetMapping("/best-sellers")
    public List<Product> getBestSellers() {
        return productService.getBestSellingProducts();
    }
    //14 sort price from low to height
    @GetMapping("/sortedprice")
    public ResponseEntity getProductsSortedByPrice() {
        List<Product> products = productService.getProductsSortedByPrice();
        return ResponseEntity.status(200).body(products);
    }

}
