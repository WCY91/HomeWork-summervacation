package com.example.summervacationhomework.dao;

import com.example.summervacationhomework.model.Sight;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface SightRepo extends MongoRepository<Sight,String> {
   List<Sight> findUserByZone(String zone);
}
