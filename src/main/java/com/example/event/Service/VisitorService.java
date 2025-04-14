package com.example.event.Service;


import com.example.event.Model.Visitor;
import com.example.event.Repository.VisitorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitorService {

    final VisitorRepository visitorRepository;


    public VisitorService(VisitorRepository visitorRepository) {
        this.visitorRepository = visitorRepository;
    }

    public List findAll(){
        return visitorRepository.findAll();
    }

    public Boolean addVisitor(Visitor visitor){
        visitorRepository.save(visitor);
        return true;
    }

    public Boolean updateVisitor(Visitor visitor,int id){
        Visitor oldV=visitorRepository.getReferenceById(id);
        if (oldV==null){
            return false;
        }
        visitor.setName(visitor.getName());
        visitor.setEmail(visitor.getEmail());
        oldV.setPhone(visitor.getPhone());
        oldV.setBalance(visitor.getBalance());

        visitorRepository.save(oldV);
        return true;
    }

    public Boolean deleteVisitor(int id){
        Visitor oldV=visitorRepository.getReferenceById(id);
        if (oldV==null){
            return false;
        }
        visitorRepository.delete(oldV);
        return true;
    }

}
