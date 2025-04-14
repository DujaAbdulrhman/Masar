package com.example.event.Service;


import com.example.event.Model.WorkShop;
import com.example.event.Repository.WorkShopRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class WorkShopService {

    final WorkShopRepository workShopRepository;

    public WorkShopService(WorkShopRepository workShopRepository) {
        this.workShopRepository = workShopRepository;
    }


    public List getall(){
       return workShopRepository.findAll();
    }

    public Boolean add(WorkShop workShop){
        workShopRepository.save(workShop);
        return true;
    }

    public Boolean updateWorkShop(WorkShop workShop, int id){
        WorkShop oldevent=workShopRepository.getReferenceById(id);
        if (oldevent==null){
            return false;
        }
        oldevent.setName(workShop.getName());
        oldevent.setDiscreption(workShop.getDiscreption());
        oldevent.setStartDate(workShop.getStartDate());
        workShopRepository.save(oldevent);
        return true;

    }

    public Boolean deleteWorkshop(int id){
        WorkShop oldEvent=workShopRepository.getReferenceById(id);
        if (oldEvent==null){
            return false;
        }
        workShopRepository.delete(oldEvent);
        return true;
    }

    //-------------------------------------------------


    public List<WorkShop> getUpcomingWorkshops() {
        LocalDate currentDate = LocalDate.now();

        Date sqlDate = Date.valueOf(currentDate);
        return workShopRepository.findUpcomingWorkshops(LocalDate.from(sqlDate.toLocalDate().atStartOfDay()));
    }


    public List<WorkShop> getWorkshopsByDate(LocalDate date) {
        return workShopRepository.findByStartDate(date);
    }

}
