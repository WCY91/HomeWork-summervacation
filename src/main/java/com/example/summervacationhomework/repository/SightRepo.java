package com.example.summervacationhomework.repository;

import com.example.summervacationhomework.model.Sight;

import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface SightRepo extends MongoRepository<Sight,String> {
   List<Sight> findByZone(String zone);
}
