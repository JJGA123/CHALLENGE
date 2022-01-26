package com.test.authservice.security;

import com.test.authservice.dto.RequestDto;
import com.test.authservice.entity.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secret;//Parameter secret token

    @Autowired
    RouteValidator routeValidator;
    
    /**
	 * init encryption the secret parameter
	 */
    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    /**
	 * createToken create the new token
	 * @param authUser Object that contains the information to create token
	 * @return The new token
	 */
    public String createToken(UserEntity authUser) {
        Map<String, Object> claims = new HashMap<>();
        claims = Jwts.claims().setSubject(authUser.getNameUser());
        claims.put("id", authUser.getIdUser());
        claims.put("role", authUser.getRole());
        Date now = new Date();
        Date exp = new Date(now.getTime() + 3600000);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    /**
	 * validate validate the token
	 * @param token text string with the token to validate
	 * @param dto object that contains the information of validation
	 * @return The token
	 */
    public boolean validate(String token,RequestDto dto) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            
        }catch (Exception e){
            return false;
        }
        if(!isAdmin(token) && routeValidator.isAdminPath(dto)) {
        	return false;
        }
        
        return true;
    }

    /**
	 * getUserNameFromToken get the subject of token
	 * @param token text string with the token to consult
	 * @return The subject of token
	 */
    public String getUserNameFromToken(String token){
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
        }catch (Exception e) {
            return "bad token";
        }
    }
    
    /**
	 * isAdmin get true if the token was generated for admin user
	 * @param token text string with the token to consult
	 * @return The subject of token
	 */
     private boolean isAdmin(String token) {
    	 return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().get("role").equals("admin");
     }
}
