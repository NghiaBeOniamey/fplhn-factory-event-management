package com.portalevent.infrastructure.security;

import com.portalevent.infrastructure.constant.Message;
import com.portalevent.infrastructure.constant.SessionConstant;
import com.portalevent.infrastructure.session.PortalEventsSession;
import com.portalevent.util.AreRolesEqual;
import com.portalevent.util.CallApiIdentity;
import com.portalevent.util.IdentityValidation;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author thangncph26123
 */
@Component
public class JwtTokenProvider {

    @Value("${identity.clientId}")
    private String clientId;

    @Value("${identity.clientSecret}")
    private String clientSecret;

    private final HttpSession session;

    private final CallApiIdentity callApiIdentity;

    private final PortalEventsSession ss;

    public JwtTokenProvider(CallApiIdentity callApiIdentity, HttpSession session, PortalEventsSession ss) {
        this.callApiIdentity = callApiIdentity;
        this.session = session;
        this.ss = ss;
    }

//    public static String generateToken() {
//        // Thời gian hiện tại
//        long nowMillis = System.currentTimeMillis();
//        Date now = new Date(nowMillis);
//
//        // Thời gian hết hạn của token (1 năm)
//        long expirationMillis = nowMillis + (365 * 24 * 60 * 60 * 1000);
//        Date expiration = new Date(expirationMillis);
//
//        // Thông tin payload của token
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("id", "7bc9ba0f-e70f-412c-864b-ad13fa3622ea");
//        claims.put("name", "Admin");
//        claims.put("email", "admin@gmail.com");
//        claims.put("picture", "https://lh3.googleusercontent.com/a/ACg8ocI234F6l9DDAC7QAPNzlHONoHQC5IMhPJTMf96TwH5QSA=s96-c");
//        claims.put("userName", "admin");
//        claims.put("idTrainingFacility", "6396a4a4-4e65-419d-a112-8cf0c9eb1b54");
//        claims.put("localHost", "http://localhost:9999/");
//        claims.put("role", new String[]{"APPROVER", "ORGANIZER", "PARTICIPANT", "ADMINISTRATIVE"});
//        claims.put("roleNames", new String[]{"Người Phê Duyệt", "Người Tổ Chức", "Người Tham Gia", "Quản Lý Hành Chính"});
//        claims.put("userCode", "Nghiathph32178");
//        claims.put("majorCode", "PTPM");
//        claims.put("subjectCode", "C# _ JAVA1");
//        claims.put("trainingFacilityCode", "HCM");
//
//        String secretKey = "keytoken_NVT25102002@123456789abcdefgh";
//        // Tạo token
//        return Jwts.builder()
//                .setClaims(claims)
//                .setIssuedAt(now)
//                .setExpiration(expiration)
//                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
//                .compact();
//    }

    public Authentication getAuthentication(String token) {
        SecretKey secretKey = IdentityValidation.generateJwtSecretKey(clientId, clientSecret);

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        String name = claims.get("fullName", String.class);
        String userName = claims.get("userName", String.class);
        String email = claims.get("email", String.class);
        String userCode = claims.get("userCode", String.class);
        String subjectCode = claims.get("subjectCode", String.class);
        String trainingFacilityCode = claims.get("trainingFacilityCode", String.class);
        String pictureUrl = claims.get("pictureUrl", String.class);

        session.setAttribute(SessionConstant.EMAIL, email);
        session.setAttribute(SessionConstant.IDUSER, userCode);
        session.setAttribute(SessionConstant.USERNAME, userName );
        session.setAttribute(SessionConstant.NAME, name);
        session.setAttribute(SessionConstant.USERCODE, userCode);
        session.setAttribute(SessionConstant.SUBJECTCODE, subjectCode);
        session.setAttribute(SessionConstant.TRAININGFACILITYCODE, trainingFacilityCode);
        session.setAttribute(SessionConstant.PICTUREURL, pictureUrl);

        Object roleClaim = claims.get("rolesCode");
        List<String> lstRole = new ArrayList<>();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (roleClaim instanceof String) {
            lstRole.add((String) roleClaim);
            authorities.add(new SimpleGrantedAuthority((String) roleClaim));
        } else if (roleClaim instanceof List<?>) {
            lstRole = (List<String>) roleClaim;
            for (String role : lstRole) {
                authorities.add(new SimpleGrantedAuthority(role));
            }
        }
        session.setAttribute(SessionConstant.ROLE, lstRole);
//        System.out.println("EMAIL: " + ss.getCurrentEmail());
//        System.out.println("IDUSER: " + ss.getCurrentIdUser());
//        System.out.println("USERNAME: " + ss.getCurrentUserName());
//        System.out.println("NAME: " + ss.getCurrentName());
//        System.out.println("USERCODE: " + ss.getCurrentUserCode());
//        System.out.println("SUBJECTCODE: " + ss.getCurrentSubjectCode());
//        System.out.println("TRAININGFACILITYCODE: " + ss.getCurrentTrainingFacilityCode());
//        System.out.println("PICTUREURL" + ss.getCurrentPictureURL());
        return new UsernamePasswordAuthenticationToken(userCode, token, authorities);
    }

    public String validateToken(String token) {
        try {
            SecretKey secretKey = IdentityValidation.generateJwtSecretKey(clientId, clientSecret);
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            Date expirationDate = claims.getBody().getExpiration();
            if (expirationDate.before(new Date())) {
                return Message.SESSION_EXPIRED.getMessage();
            }
            return "";
        } catch (JwtException | IllegalArgumentException e) {
            e.printStackTrace(System.out);
            return null;
        }
    }
}
