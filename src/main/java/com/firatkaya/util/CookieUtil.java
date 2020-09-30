package com.firatkaya.util;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;

@Component
public class CookieUtil {

    public Cookie createCookie(String key,String value,int maxAge,String domain,String path,boolean isSecure,boolean isHttpOnly){
        Cookie cookie = new Cookie(key,value);
        cookie.setMaxAge(maxAge);
        cookie.setDomain(domain);
        cookie.setPath(path);
        cookie.setSecure(isSecure);
        cookie.setHttpOnly(isHttpOnly);
        return cookie;
    }

    public Cookie deleteCookie(String key){
        Cookie cookie = new Cookie(key,"");
        cookie.setMaxAge(0);
        return cookie;
    }

}
