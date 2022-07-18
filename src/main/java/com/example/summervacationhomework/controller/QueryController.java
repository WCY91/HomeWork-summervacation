package com.example.summervacationhomework.controller;

import com.example.summervacationhomework.crawler.KeelungSightsCrawler;
import com.example.summervacationhomework.model.Sight;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueryController {

    @GetMapping("SightAPI")
    public Sight[] SightAPI(@RequestParam(defaultValue = "七堵區") String zone){
        KeelungSightsCrawler crawler = new KeelungSightsCrawler();
        for(Sight temp:crawler.getItems(zone)){
            System.out.println(temp);
        }
        return crawler.getItems(zone);
    }
}
