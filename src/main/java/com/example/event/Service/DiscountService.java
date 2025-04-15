package com.example.event.Service;

import com.example.event.Model.Discount;
import com.example.event.Repository.DiscountRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DiscountService {

    final DiscountRepository discountRepository;

    public DiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    public double applyDiscount(double originalPrice, Integer wId) {
        if (isDiscountApplicable(wId)) {
            Double discountRate = getDiscountRateByWorkshop(wId);
            if (discountRate != null) {
                return originalPrice * (1 - discountRate); 
            }
        }
        return originalPrice;
    }

    public boolean isDiscountApplicable(Integer wId) {
        LocalDate currentDate = LocalDate.now();
       
        return discountRepository.findBywId(Long.valueOf(wId)).stream()
                .anyMatch(discount -> discount.getDiscountRate() > 0 && discount.getDiscountRate() != null);
    }

    // Get the discount rate for a specific workshop
    public Double getDiscountRateByWorkshop(Integer wId) {
        List<Discount> discounts = discountRepository.findBywId(Long.valueOf(wId));
        if (!discounts.isEmpty()) {
            return discounts.get(0).getDiscountRate(); 
        }
        return null;
    }

   
    public Boolean addDiscount(Discount discount) {
        discountRepository.save(discount);
        return true;
    }


    public List<Discount> getAllDiscounts() {
        return discountRepository.findAll();
    }


    
    public Boolean updateDiscount(Discount discount, int id) {
        Discount existingDiscount = discountRepository.getReferenceById(id);
        if (existingDiscount == null) {
            return false;
        }
        discountRepository.save(existingDiscount);
        return true;
    }

    public Boolean deleteDiscount(int id) {
        Discount existingDiscount = discountRepository.getReferenceById(id);
        if (existingDiscount == null) {
            return false;
        }
        discountRepository.delete(existingDiscount);
        return true;
    }



    //-----------------------------------------------------------------

    private static final List<LocalDate> DISCOUNT_DATES = List.of(
            LocalDate.of(2025, 9, 23),
            LocalDate.of(2025, 2, 22), 
            LocalDate.of(2025, 3, 11)  
    );

    public boolean isDiscountApplicable() {
        LocalDate currentDate = LocalDate.now();
        return DISCOUNT_DATES.contains(currentDate);
    }

    public double applyDiscount(double originalPrice) {
        if (isDiscountApplicable()) {
            return originalPrice * 0.85; 
        }
        return originalPrice;
    }

}
