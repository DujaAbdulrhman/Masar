package com.example.event.Controller;


import com.example.event.ApiResponse.Api;
import com.example.event.Model.WorkShop;
import com.example.event.Service.WorkShopService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/workshop")
public class WorkShopController {

    final WorkShopService workShopService;

    public WorkShopController(WorkShopService workShopService) {
        this.workShopService = workShopService;
    }


    @PostMapping("/add")
    public ResponseEntity addWorkshop(@Valid @RequestBody WorkShop workShop){
        workShopService.add(workShop);
        return ResponseEntity.status(200).body(new Api("Workshop added "));
    }

    @GetMapping("/get")
    public ResponseEntity getWorkShop(){
        return ResponseEntity.status(200).body(workShopService.getall());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateWorkShop(@RequestBody WorkShop workShop, Errors errors, @PathVariable int id){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        workShopService.updateWorkShop(workShop,id);
        return ResponseEntity.status(200).body(new Api("Updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(Errors errors ,@PathVariable int id){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        workShopService.deleteWorkshop(id);
        return ResponseEntity.status(200).body(new Api("deleted successfully"));
    }

    //-----------------------------------------


    //8 get the upcoming workshop for the current date
    @GetMapping("/upcoming")
    public ResponseEntity<List<WorkShop>> getUpcomingWorkshops() {
        List<WorkShop> upcomingWorkshops = workShopService.getUpcomingWorkshops();
        return ResponseEntity.ok(upcomingWorkshops);
    }

    //13 get the workShops by a specific date
    @GetMapping("/bydate")
    public ResponseEntity getWorkshopsByDate(@PathVariable LocalDate date) {
        List<WorkShop> workshops = workShopService.getWorkshopsByDate(date);

        if (workshops.isEmpty()) {
            return ResponseEntity.status(400).body(new Api("not found"));  // إذا لم توجد ورش عمل
        }

        return ResponseEntity.status(200).body(workshops);
    }

}
