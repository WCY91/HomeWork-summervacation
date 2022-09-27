package com.example.summervacationhomework.service;


import com.example.summervacationhomework.crawler.KeelungSightsCrawler;
import com.example.summervacationhomework.model.Sight;


import com.example.summervacationhomework.repository.SightRepo;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class SightService {

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
        return sightRepo.findByZone(zone);
    }



}
