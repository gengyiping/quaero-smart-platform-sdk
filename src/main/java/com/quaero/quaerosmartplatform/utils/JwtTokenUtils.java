package com.quaero.quaerosmartplatform.utils;

import com.quaero.quaerosmartplatform.domain.bo.SecurityUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * @since wuhanzhang
 * @since 2020/11/11
 */
public class JwtTokenUtils {

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    private static final String SECRET = "quaero";
    private static final String ISS = "wu";

    // 角色的key
    private static final String ROLE_CLAIMS = "role";

    // 权限的key
    private static final String AUTHORITY_CLAIMS = "authority";

    // 过期时间是3600秒，既是1个小时 *24h
    private static final long EXPIRATION = 3600L*24;

    // 创建token
    public static String createToken(UserDetails userDetails) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(AUTHORITY_CLAIMS, userDetails.getAuthorities());
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .setClaims(map)
                .setIssuer(ISS)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION * 1000))
                .compact();
    }

    // 从token中获取用户名
    public static String getUsername(String token){
        return getTokenBody(token).getSubject();
    }

    // 获取用户角色
    public static String getUserRole(String token){
        //return JSONArray.parseArray(JSONArray.toJSONString(getTokenBody(token).get(AUTHORITY_CLAIMS)), String.class);
        return getTokenBody(token).get(ROLE_CLAIMS).toString();
    }

    // 获取用户权限
    public static List<String> getUserAuthority(String token){
        List<String> list = new ArrayList<>();
        List<LinkedHashMap<String,String>> hashMapList = (List<LinkedHashMap<String, String>>) getTokenBody(token).get(AUTHORITY_CLAIMS);
        hashMapList.forEach(s->{
            for (Map.Entry<String, String> entry : s.entrySet()) {
                list.add(entry.getValue());
            }
        });
        return list;
    }

    // 是否已过期
    public static boolean isExpiration(String token) {
        try {
            return getTokenBody(token).getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    /**
     * 验证token
     *
     * @param token
     * @param userDetails
     * @return
     */
    public static boolean validateToken(String token, UserDetails userDetails) {
        SecurityUserDetails user = (SecurityUserDetails) userDetails;
        final String username = getUsername(token);
        return (username.equals(user.getUsername()) && !isExpiration(token));
    }

    private static Claims getTokenBody(String token){
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }
}
