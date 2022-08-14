package com.example.summervacationhomework.dao;

import com.example.summervacationhomework.crawler.KeelungSightsCrawler;
import com.example.summervacationhomework.model.Sight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SightDao {

    @Autowired
    SightRepo sightRepo;

    @Bean
    CommandLineRunner create(SightRepo sightDao){
        if(sightDao.count() != 0){
            System.out.println("already exist");
            sightDao.deleteAll();
        }
        KeelungSightsCrawler crawler = new KeelungSightsCrawler();
        return args -> {
            sightDao.saveAll(crawler.getSightList());
            System.out.println("successful save");
        };
    }

    public List<Sight> read(String zone) {
        return sightRepo.findUserByZone(zone);
    }

}
