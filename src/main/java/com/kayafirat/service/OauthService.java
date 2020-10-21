package com.kayafirat.service;

public interface OauthService {

    String oAuthGithubUserAuthenticate(String code) throws Exception;

    String oAuthLinkedinUserAuthenticate(String code) throws Exception;

}
