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

	@Autowired
	private StaticsViewRepository staticViewRepo;
	
	@Autowired
	private PostRepository postRepo;
	
	@Transactional
	@Override
	public void addStaticViews(StaticsViews staticviews) {
		int checkAlls=staticViewRepo.exitstByAllValues(staticviews.getIpAddress(),staticviews.getPostId(),staticviews.getTemporaryCode());
		if(checkAlls == 0) {
			staticViewRepo.save(staticviews);
			postRepo.updatePageView(staticviews.getPostId());
		} 
		
		
		
	}


}
