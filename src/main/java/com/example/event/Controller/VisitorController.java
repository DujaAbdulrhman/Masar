package com.example.event.Controller;

import com.example.event.ApiResponse.Api;
import com.example.event.Model.Visitor;
import com.example.event.Service.VisitorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/visitor")
public class VisitorController {

    final VisitorService visitorService;

    public VisitorController(VisitorService visitorService) {
        this.visitorService = visitorService;
    }

    @PostMapping("/add")
    public ResponseEntity addVisitor(@Valid @RequestBody Visitor visitor){
        visitorService.addVisitor(visitor);
        return ResponseEntity.status(200).body(new Api("Added successfully"));
    }
    @GetMapping("/getall")
    public ResponseEntity getallV(){
        return ResponseEntity.status(200).body(visitorService.findAll());
    }
    @PutMapping("/update/{id}")
    public ResponseEntity updateVisitor(@Valid @RequestBody Visitor visitor, Errors errors,@PathVariable int id){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        visitorService.updateVisitor(visitor,id);
        return ResponseEntity.status(200).body(new Api("Visitor updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteV(Errors errors,@PathVariable int id){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        visitorService.deleteVisitor(id);
        return ResponseEntity.status(200).body(new Api("Deleted successfully"));
    }

    //--------------------------------------------


}
