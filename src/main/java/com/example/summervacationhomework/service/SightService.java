package com.example.summervacationhomework.service;


import com.example.summervacationhomework.dao.SightDao;

import com.example.summervacationhomework.model.Sight;


import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class SightService {

    @Autowired
    private SightDao sightDao;
    @Transactional
    public List<Sight> read(String zone) {
        return sightDao.read(zone);
    }



}
