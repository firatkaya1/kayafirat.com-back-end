package com.kayafirat.service.Impl;

import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kayafirat.model.StaticsViews;
import com.kayafirat.repository.PostRepository;
import com.kayafirat.repository.StaticsViewRepository;
import com.kayafirat.service.StaticsViewService;


@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class IStaticsViewService implements StaticsViewService {

    private final StaticsViewRepository staticViewRepo;
    private final PostRepository postRepo;


    @Transactional
    @Override
    public void addStaticViews(StaticsViews statistic) {
        int checkAll = staticViewRepo.exitstByAllValues(statistic.getIpAddress(), statistic.getPostId(), statistic.getTemporaryCode());
        if (checkAll == 0) {
            staticViewRepo.save(statistic);
            postRepo.updatePageView(statistic.getPostId());
        }
    }
}
