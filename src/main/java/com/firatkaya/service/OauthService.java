package com.firatkaya.service;

import com.firatkaya.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface OauthService {



    String oAuthGithubUserAuthenticate(String code) throws Exception;

    String oAuthLinkedinUserAuthenticate(String code) throws Exception;

}
