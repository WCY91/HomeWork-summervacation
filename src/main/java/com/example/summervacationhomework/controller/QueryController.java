package com.example.summervacationhomework.controller;

import com.example.summervacationhomework.model.Sight;
import com.example.summervacationhomework.service.SightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class QueryController {

    @Autowired
    private SightService sightService;

    @GetMapping("SightAPI")
    public ResponseEntity<List<Sight>> SightAPI(@RequestParam(defaultValue = "七堵區") String zone) {

       List<Sight> tmp = sightService.read(zone);

        if (tmp.isEmpty()) {
            System.out.println("Wrong zone");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(tmp);
        }

    }
}
