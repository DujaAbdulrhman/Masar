package com.example.event.Service;

import com.example.event.Model.Discount;
import com.example.event.Repository.DiscountRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DiscountService {

    final DiscountRepository ratingRepository;

    public DiscountService(DiscountRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public Boolean addRate(Discount rating){
        ratingRepository.save(rating);
        return true;
    }

    public List getall(){
        return ratingRepository.findAll();
    }

    public Boolean updateRate(Discount rating, int id){
        Discount oldR=ratingRepository.getReferenceById(id);
        if (oldR==null){
            return false;
        }
        oldR.setReview(rating.getReview());
        oldR.setStars(rating.getStars());
        oldR.setWId(rating.getWId());
        ratingRepository.save(oldR);
        return true;
    }

    public Boolean deleteR(int id){
        Discount oldR=ratingRepository.getReferenceById(id);
        if (oldR==null){
            return false;
        }
        ratingRepository.delete(oldR);
        return true;
    }

    //-----------------------------------------------------------------

    private static final List<LocalDate> DISCOUNT_DATES = List.of(
            LocalDate.of(2025, 9, 23), // Saudi National Day (Sept 23)
            LocalDate.of(2025, 2, 22), // Saudi Founding Day (Feb 22)
            LocalDate.of(2025, 3, 11)  // Saudi Flag Day (Mar 11)
    );

    public boolean isDiscountApplicable() {
        LocalDate currentDate = LocalDate.now();
        return DISCOUNT_DATES.contains(currentDate);
    }

    public double applyDiscount(double originalPrice) {
        if (isDiscountApplicable()) {
            return originalPrice * 0.85; // Apply 15% discount
        }
        return originalPrice;
    }

}
