package com.kayafirat.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CookieUtil {

    private final Environment env;
    private final JwtUtil jwtUtil;

    public Cookie createCookie(String key,String value,int maxAge,String domain,String path,boolean isSecure,boolean isHttpOnly){
        Cookie cookie = new Cookie(key,value);
        cookie.setMaxAge(maxAge);
        cookie.setDomain(domain);
        cookie.setPath(path);
        cookie.setSecure(isSecure);
        cookie.setHttpOnly(isHttpOnly);
        return cookie;
    }

    public Cookie createCookie(String key,String value,boolean isSecure,boolean isHttpOnly) throws Exception {
        Cookie cookie = new Cookie(key,value);
        cookie.setMaxAge(86400);
        cookie.setDomain(env.getProperty("cookie.default.domain"));
        cookie.setPath(env.getProperty("cookie.default.path"));
        cookie.setSecure(isSecure);
        cookie.setHttpOnly(isHttpOnly);
        return cookie;
    }

    public Cookie deleteCookie(String key,boolean isSecure,boolean isHttpOnly ){
        Cookie cookie = new Cookie(key,"");
        cookie.setDomain(env.getProperty("cookie.default.domain"));
        cookie.setPath(env.getProperty("cookie.default.path"));
        cookie.setSecure(isSecure);
        cookie.setHttpOnly(isHttpOnly);
        cookie.setMaxAge(0);
        return cookie;
    }


}
