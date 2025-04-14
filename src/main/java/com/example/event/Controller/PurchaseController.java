package com.example.event.Controller;

import com.example.event.ApiResponse.Api;
import com.example.event.Model.Purchase;
import com.example.event.Service.PurchaseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/purchase")
public class PurchaseController {

    final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    // إضافة عملية شراء جديدة
    @PostMapping("/add")
    public ResponseEntity add(@Valid @RequestBody Purchase purchase) {
        purchaseService.add(purchase);
        return ResponseEntity.status(200).body(new Api("Added successfully"));
    }

    // الحصول على جميع المشتريات
    @GetMapping("/getall")
    public ResponseEntity getall() {
        return ResponseEntity.status(200).body(purchaseService.get());
    }

    // تحديث عملية شراء
    @PutMapping("/update/{id}")
    public ResponseEntity update(@Valid @RequestBody Purchase purchase, Errors errors, @PathVariable int id) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        purchaseService.update(purchase, id);
        return ResponseEntity.status(200).body(new Api("Purchase updated successfully"));
    }

    // حذف عملية شراء
    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(Errors errors, @PathVariable int id) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        purchaseService.delete(id);
        return ResponseEntity.status(200).body(new Api("Deleted successfully"));
    }

    //--------------------------------------------------------------------


    //3 discount in specific dates and balance checking
    @PostMapping("/balancee")
    public ResponseEntity purchaseProduct(@RequestBody Map<String, Integer> data) {
        Integer vId = data.get("visitorId");
        Integer pId = data.get("productId");

        if (vId == null || pId == null) {
            return ResponseEntity.status(400).body("visitorId and productId must be provided");
        }
        String result = purchaseService.processPurchaseWithDiscount(vId, pId);

        if (result.startsWith("Purchase successful")) {
            return ResponseEntity.status(200).body(result);
        } else {
            return ResponseEntity.status(400).body(result);
        }
    }

    //4 balance checking
    @PostMapping("/processPurchase")
    public ResponseEntity processPurchaseWithDiscount(@RequestBody Purchase purchase) {
        String result = purchaseService.processPurchaseWithDiscount(purchase.getVId(), purchase.getPId());
        return ResponseEntity.ok(result);
    }
    //11 Check the inventory, balance and visitor balance then purchase if there is nothing wrong
    @PostMapping("/buy")
    public ResponseEntity purchaseProduct(@RequestBody Purchase purchase) {

        String result = purchaseService.processPurchase(purchase.getVId(), purchase.getPId(), purchase.getQuantity());

        if ("Purchase successful".equals(result)) {
            return ResponseEntity.status(200).body(result);
        } else {
            return ResponseEntity.status(400).body(result);
        }
    }
    //12 Count the number of times a product has been purchased for a specific artist
    @GetMapping("/count/{aId}")
    public long getPurchaseCountByAId(@PathVariable Integer aId) {
        return purchaseService.getPurchaseCountByAId(aId);
    }

    //15 get the names of the visitor how buy products more than once
    @GetMapping("/visitors/count/{pId}")
    public ResponseEntity<List<String>> BoughtMoreThanOnce(@PathVariable Integer pId) {
        List<String> visitorNames = purchaseService.BoughtMoreThanOnce(pId);
        return ResponseEntity.status(200).body(visitorNames);
    }
}
