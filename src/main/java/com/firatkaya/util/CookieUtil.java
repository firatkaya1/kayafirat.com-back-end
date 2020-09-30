package com.firatkaya.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CookieUtil {

    private final Environment env;

    public Cookie createCookie(String key,String value,int maxAge,String domain,String path,boolean isSecure,boolean isHttpOnly){
        Cookie cookie = new Cookie(key,value);
        cookie.setMaxAge(maxAge);
        cookie.setDomain(domain);
        cookie.setPath(path);
        cookie.setSecure(isSecure);
        cookie.setHttpOnly(isHttpOnly);
        return cookie;
    }

    public Cookie createCookie(String key,String value,boolean isSecure,boolean isHttpOnly){
        Cookie cookie = new Cookie(key,value);
        cookie.setMaxAge(86400);
        cookie.setDomain(env.getProperty("cookie.default.domain"));
        cookie.setPath(env.getProperty("cookie.default.max-age"));
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
