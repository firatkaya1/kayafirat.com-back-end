package com.kayafirat.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kayafirat.repository.StaticsViewRepository;

@Component
public class Schedules {

    private final StaticsViewRepository swRepo;

    @Autowired
    public Schedules(StaticsViewRepository staticsViewRepository) {
        this.swRepo = staticsViewRepository;
    }

    // Every 10 minutes run this method
    @Scheduled(fixedRate = 600000)
    public void reportCurrentTime() {
        swRepo.updateAllRows();
    }
}