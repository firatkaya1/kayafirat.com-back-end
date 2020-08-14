package com.firatkaya.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.firatkaya.repository.StaticsViewRepository;

@Component
public class Schedules {

    @Autowired
    StaticsViewRepository swRepo;

    // Every 10 minutes run this method
    @Scheduled(fixedRate = 600000)
    public void reportCurrentTime() {
        swRepo.updateAllRows();
    }
}