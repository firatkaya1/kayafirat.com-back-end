package com.firatkaya.service.Impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.firatkaya.model.StaticsViews;
import com.firatkaya.repository.PostRepository;
import com.firatkaya.repository.StaticsViewRepository;
import com.firatkaya.service.StaticsViewService;


@Service
public class StaticsViewServiceImpl implements StaticsViewService {

    private final StaticsViewRepository staticViewRepo;
    private final PostRepository postRepo;

    @Autowired
    public StaticsViewServiceImpl(StaticsViewRepository staticViewRepo,PostRepository postRepository){
        this.staticViewRepo = staticViewRepo;
        this.postRepo = postRepository;
    }

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
