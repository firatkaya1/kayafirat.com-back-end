package com.firatkaya.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityUtil {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public SecurityUtil(){
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2A,10);

    }
    public String encode(String password){
        return bCryptPasswordEncoder.encode(password);

    }

}
