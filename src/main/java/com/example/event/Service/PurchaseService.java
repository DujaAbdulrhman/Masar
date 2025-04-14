package com.example.event.Service;

import com.example.event.Model.Product;
import com.example.event.Model.Purchase;
import com.example.event.Model.Visitor;
import com.example.event.Repository.ProductRepository;
import com.example.event.Repository.PurchaseRepository;
import com.example.event.Repository.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Service
public class PurchaseService {


    final PurchaseRepository purchaseRepository;
    @Autowired
    final VisitorRepository visitorRepository;
    final ProductRepository productRepository;

    public PurchaseService(PurchaseRepository purchaseRepository, VisitorRepository visitorRepository, ProductRepository productRepository) {
        this.purchaseRepository = purchaseRepository;
        this.visitorRepository = visitorRepository;
        this.productRepository = productRepository;
      //  this.visitorRepository= visitorRepository;
    }

    public List get(){
       return purchaseRepository.findAll();
    }

    public Boolean add(Purchase purchase){
        purchaseRepository.save(purchase);
        return true;
    }

    public Boolean update(Purchase purchase,int id){
        Purchase oldeP=purchaseRepository.getReferenceById(id);
        if (oldeP==null){
            return false;
        }
        oldeP.setPId(purchase.getPId());
        oldeP.setDate(purchase.getDate());

        purchaseRepository.save(oldeP);
        return true;
    }

    public Boolean delete(int id){
        Purchase oldP=purchaseRepository.getReferenceById(id);
        if (oldP==null){
            return false;
        }
        purchaseRepository.delete(oldP);
        return true;
    }

    //---------------------------------------------------------------

    public double applyDiscountIfEligible(double originalPrice) {
        LocalDate today = LocalDate.now();

        boolean isNationalHoliday =
                (today.getMonth() == Month.SEPTEMBER && today.getDayOfMonth() == 23) || // اليوم الوطني
                        (today.getMonth() == Month.FEBRUARY && today.getDayOfMonth() == 22) ||  // يوم التأسيس
                        (today.getMonth() == Month.MARCH && today.getDayOfMonth() == 11);       // يوم العلم

        if (isNationalHoliday) {
            return originalPrice * 0.85; // 15٪
        }return originalPrice;
    }


    public String processPurchaseWithDiscount(int vId, int proId) {
        Visitor visitor = visitorRepository.findById(vId);
        if (visitor == null) {
            return "Visitor not found.";
        }
        Product product = productRepository.findById(proId).orElse(null);
        if (product == null) {
            return "Product not found.";
        }
        double originalPrice = product.getPrice();
        double finalPrice = applyDiscountIfEligible(originalPrice);

        if (visitor.getBalance() < finalPrice) {
            return "Insufficient balance.";
        }

        visitor.setBalance(visitor.getBalance() -finalPrice);
        visitorRepository.save(visitor);

        return "Purchase successful Final price: " + finalPrice + ", Remaining balance: " + visitor.getBalance();
    }


    //Check the inventory, balance and visitor balance then purchase if there is nothing wrong
    public String processPurchase(Integer visitorId, Integer productId, Integer quantity) {
        Product product = productRepository.findById(productId).orElse(null);
        Visitor visitor = visitorRepository.findById(visitorId).orElse(null);

        if (product == null || visitor == null) {
            return "Product or Visitor not found!";
        }

        // check the visitor balance
        Double totalCost = product.getPrice() * quantity;
        if (visitor.getBalance() < totalCost) {
            return "Insufficient balance!";
        }

        visitor.setBalance(visitor.getBalance() - totalCost);
        visitorRepository.save(visitor);

        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);

        Purchase purchase = new Purchase();
        purchase.setVId(visitorId);
        purchase.setPId(productId);
        purchase.setQuantity(quantity);
        purchaseRepository.save(purchase);

        return "Purchase successful!";
    }

    //Counts the number of purchases made for a specific product
    public long getPurchaseCountByAId(Integer aId) {
        return purchaseRepository.countPurchaseByAId(aId);
    }


    //products purchased more than once
    public List<String> BoughtMoreThanOnce(Integer productId) {
        return purchaseRepository.BoughtMoreThanOnce(productId);
    }

}
