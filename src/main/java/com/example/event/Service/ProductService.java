package com.example.event.Service;

import com.example.event.Model.Product;
import com.example.event.Model.Purchase;
import com.example.event.Model.Visitor;
import com.example.event.Repository.ProductRepository;
import com.example.event.Repository.PurchaseRepository;
import com.example.event.Repository.VisitorRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProductService {

    final ProductRepository productRepository;
    final DiscountService discountService;
    final PurchaseRepository purchaseRepository;
    final VisitorRepository visitorRepository;

    public ProductService(ProductRepository productRepository, DiscountService discountService, PurchaseRepository purchaseRepository, VisitorRepository visitorRepository) {
        this.productRepository = productRepository;
        this.discountService = discountService;
        this.purchaseRepository = purchaseRepository;
        this.visitorRepository = visitorRepository;
    }

    public List getall(){
        return productRepository.findAll();
    }

    public boolean add(Product product){
        productRepository.save(product);
        return true;
    }

    public Boolean update(Product product, int id){
        Product oldproduct=productRepository.getReferenceById(id);
        if (oldproduct==null){
            return false;
        }

        oldproduct.setAId(product.getAId());
        oldproduct.setName(product.getName());
        oldproduct.setPrice(product.getPrice());
        oldproduct.setQuantity(product.getQuantity());

        productRepository.save(oldproduct);
        return true;
    }

    public Boolean delete(int id){
        Product oldproduct=productRepository.getReferenceById(id);
        if (oldproduct==null){
            return false;
        }
        productRepository.delete(oldproduct);
        return true;
    }


    public Product applyDiscount(Integer pId) {
        Product product = productRepository.findById(pId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // تحديد اليوم الوطني ويوم التأسيس
        LocalDate today = LocalDate.now();
        LocalDate saudiNationalDay = LocalDate.of(today.getYear(), 9, 23);  // اليوم الوطني
        LocalDate saudiFoundationDay = LocalDate.of(today.getYear(), 4, 14); // يوم التأسيس

        //
        if (today.equals(saudiNationalDay) || today.equals(saudiFoundationDay)) {
            double discountedPrice = discountService.applyDiscount(product.getPrice())*0.15;
            product.setPrice(discountedPrice);
        }

        return productRepository.save(product);
    }

    public List<Product> getBestSellingProducts() {
        return productRepository.findBestSellingProducts();
    }

    public String processPurchase(Integer visitorId, Integer productId, Integer quantity) {
        Product product = productRepository.findById(productId).orElse(null);
        Visitor visitor = visitorRepository.findById(visitorId).orElse(null);

        if (product == null || visitor == null) {
            return "Product or Visitor not found!";
        }

        // checking the balance
        Double totalCost = product.getPrice() * quantity;
        if (visitor.getBalance() < totalCost) {
            return "Insufficient balance!";
        }

        // خصم المبلغ الزائر
        visitor.setBalance(visitor.getBalance() - totalCost);
        visitorRepository.save(visitor);
        productRepository.updateProductQuantity(productId, product.getQuantity() - quantity);

        Purchase purchase = new Purchase();
        purchase.setVId(visitorId);
        purchase.setPId(productId);
        purchase.setQuantity(quantity);
        purchaseRepository.save(purchase);
        return "Purchase successful";
    }

    public List<Product> getProductsSortedByPrice() {
        return productRepository.findAll(Sort.by(Sort.Order.asc("price")));
    }

}
