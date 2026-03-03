package com.ruoyi.xmbj;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class XmJwtUtil {

    public static String getSubject(String key,String jwtStr) {
        Claims be = Jwts.parser().setSigningKey(key)
                .parseClaimsJws(jwtStr.startsWith("Bearer ") ? jwtStr.substring(7) : jwtStr).getBody();
        return  be.getSubject();
    }
}
