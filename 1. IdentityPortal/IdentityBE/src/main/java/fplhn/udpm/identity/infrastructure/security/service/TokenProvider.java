package fplhn.udpm.identity.infrastructure.security.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fplhn.udpm.identity.entity.AccessToken;
import fplhn.udpm.identity.entity.Campus;
import fplhn.udpm.identity.entity.DepartmentCampus;
import fplhn.udpm.identity.entity.Module;
import fplhn.udpm.identity.entity.Staff;
import fplhn.udpm.identity.entity.Student;
import fplhn.udpm.identity.infrastructure.constant.CommonStatus;
import fplhn.udpm.identity.infrastructure.constant.EntityStatus;
import fplhn.udpm.identity.infrastructure.constant.UserType;
import fplhn.udpm.identity.infrastructure.security.repository.AccessTokenAuthRepository;
import fplhn.udpm.identity.infrastructure.security.repository.ModuleAuthRepository;
import fplhn.udpm.identity.infrastructure.security.repository.StaffAuthRepository;
import fplhn.udpm.identity.infrastructure.security.repository.StaffModuleRoleAuthRepository;
import fplhn.udpm.identity.infrastructure.security.repository.StudentAuthRepository;
import fplhn.udpm.identity.infrastructure.security.response.ModuleAvailableResponse;
import fplhn.udpm.identity.infrastructure.security.response.TokenSubjectResponse;
import fplhn.udpm.identity.infrastructure.security.user.UserPrincipal;
import fplhn.udpm.identity.util.DateTimeUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class TokenProvider {

    @Setter
    private SecretKey tokenSecret;

    //Time: 2 hours for production
    private final long TOKEN_EXP = System.currentTimeMillis() + 2 * 60 * 60 * 1000;

    @Setter(onMethod_ = @Autowired)
    private StudentAuthRepository studentRepository;

    @Setter(onMethod_ = @Autowired)
    private StaffAuthRepository staffRepository;

    @Setter(onMethod_ = @Autowired)
    private StaffModuleRoleAuthRepository staffModuleRoleRepository;

    @Setter(onMethod_ = @Autowired)
    private AccessTokenAuthRepository accessTokenRepository;

    @Setter(onMethod_ = @Autowired)
    private ModuleAuthRepository moduleAuthRepository;

    public String[] createToken(
            Authentication authentication,
            String host,
            List<ModuleAvailableResponse> moduleAvailableResponses
    ) throws BadRequestException, JsonProcessingException {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Object user = getCurrentUserLogin(userPrincipal.getEmail());
        Module module = moduleAuthRepository
                .findByUrl(host)
                .orElseThrow(() -> new BadRequestException("Module not found"));

        if (user == null) throw new BadRequestException("User not found");

        TokenSubjectResponse tokenSubjectResponse = createTokenSubjectResponse(user, host, moduleAvailableResponses, module);
        Object[] tokenInfo = createJwtToken(tokenSubjectResponse);
        String accessToken = tokenInfo[0].toString();
        Date expiredAt = (Date) tokenInfo[1];
        UserType userType = (user instanceof Student) ? UserType.SINH_VIEN : UserType.CAN_BO;

        boolean isCampusActive = isCampusActive(user);
        Optional<AccessToken> accessTokenOptional = accessTokenRepository.findByUserId(userPrincipal.getId(), userType);
        if (accessTokenOptional.isPresent()) {
            AccessToken existingToken = accessTokenOptional.get();
            updateAccessToken(existingToken, accessToken, expiredAt, userType);
            return buildTokenResponse(accessToken, userType, user, isCampusActive);
        }
        AccessToken newToken = createAccessToken(userPrincipal.getId(), accessToken, expiredAt, userType);
        accessTokenRepository.save(newToken);

        return buildTokenResponse(accessToken, userType, user, isCampusActive);
    }

    private TokenSubjectResponse createTokenSubjectResponse(
            Object user,
            String host,
            List<ModuleAvailableResponse> moduleAvailableResponses,
            Module module
    ) {
        TokenSubjectResponse response = new TokenSubjectResponse();
        if (user instanceof Student student) {
            response = buildStudentTokenSubjectResponse(student, host);
        } else if (user instanceof Staff staff) {
            response = buildStaffTokenSubjectResponse(staff, host, moduleAvailableResponses, module);
        }
        return response;
    }

    private TokenSubjectResponse buildStudentTokenSubjectResponse(Student student, String host) {
        TokenSubjectResponse response = new TokenSubjectResponse();
        response.setFullName(student.getFullName());
        response.setUserId(student.getId());
        response.setUserCode(student.getStudentCode());
        response.setRolesCode(Collections.singletonList("SINH_VIEN"));
        response.setRolesName(Collections.singletonList("SINH_VIEN"));
        response.setPictureUrl(student.getAvatar());
        response.setHost(host);
        response.setEmail(student.getEmailFpt());
        response.setUserType("SINH_VIEN");

        DepartmentCampus departmentCampus = student.getDepartmentCampus();
        if (departmentCampus != null) {
            response.setTrainingFacilityCode(departmentCampus.getCampus().getCode());
            response.setSubjectCode(departmentCampus.getDepartment().getCode());
            response.setTrainingFacilityId(departmentCampus.getCampus().getId());
            response.setTrainingFacilityName(departmentCampus.getCampus().getName());
            response.setSubjectName(departmentCampus.getDepartment().getName());
        }
        return response;
    }

    private TokenSubjectResponse buildStaffTokenSubjectResponse(Staff staff, String host, List<ModuleAvailableResponse> moduleAvailableResponses, Module module) {
        TokenSubjectResponse response = new TokenSubjectResponse();
        response.setFullName(staff.getFullName());
        response.setUserId(staff.getId());
        response.setUserCode(staff.getStaffCode());
        response.setPictureUrl(staff.getAvatar());
        response.setUserType("CAN_BO");
        response.setEmail(staff.getAccountFPT());
        response.setModuleAvailableResponses(moduleAvailableResponses);

        DepartmentCampus departmentCampus = staff.getDepartmentCampus();
        if (departmentCampus != null) {
            if (departmentCampus.getCampus().getEntityStatus().equals(EntityStatus.DELETED)) {
                response.setTrainingFacilityCode(null);
                response.setTrainingFacilityId(null);
                response.setTrainingFacilityName(null);
            } else {
                response.setTrainingFacilityCode(departmentCampus.getCampus().getCode());
                response.setTrainingFacilityId(departmentCampus.getCampus().getId());
                response.setTrainingFacilityName(departmentCampus.getCampus().getName());
            }
            response.setSubjectCode(departmentCampus.getDepartment().getCode());
            response.setSubjectName(departmentCampus.getDepartment().getName());
        } else {
            setCampusInfoForStaff(response, staff);
        }

        List<String> rolesCode = staffModuleRoleRepository.getListRoleCodeByUserIdAndModuleId(staff.getId(), module.getId());
        if (rolesCode.isEmpty()) {
            response.setRolesCode(Collections.singletonList("GIAO_VIEN"));
            response.setRolesName(Collections.singletonList("GIAO_VIEN"));
        } else {
            response.setRolesCode(rolesCode);
            response.setRolesName(staffModuleRoleRepository.getListRoleNameByUserId(staff.getId(), module.getId()));
        }
        response.setHost(host);
        return response;
    }

    private void setCampusInfoForStaff(TokenSubjectResponse response, Staff staff) {
        if (staff.getCampus() == null) {
            response.setTrainingFacilityCode(null);
            response.setTrainingFacilityId(null);
            response.setTrainingFacilityName(null);
        } else {
            Optional<Campus> campusOptional = staffModuleRoleRepository.findCampusById(staff.getCampus().getId());
            if (campusOptional.isPresent()) {
                Campus campus = campusOptional.get();
                if (campus.getEntityStatus().equals(EntityStatus.DELETED)) {
                    response.setTrainingFacilityCode(null);
                    response.setTrainingFacilityId(null);
                    response.setTrainingFacilityName(null);
                } else {
                    response.setTrainingFacilityCode(campus.getCode());
                    response.setTrainingFacilityId(campus.getId());
                    response.setTrainingFacilityName(campus.getName());
                }
            } else {
                response.setTrainingFacilityCode(null);
                response.setTrainingFacilityId(null);
                response.setTrainingFacilityName(null);
            }
        }
        response.setSubjectCode(null);
        response.setSubjectName(null);
    }

    private boolean isCampusActive(Object user) {
        if (user instanceof Student student) {
            DepartmentCampus departmentCampus = student.getDepartmentCampus();
            return departmentCampus == null || !departmentCampus.getCampus().getEntityStatus().equals(EntityStatus.DELETED);
        } else if (user instanceof Staff staff) {
            DepartmentCampus departmentCampus = staff.getDepartmentCampus();
            if (departmentCampus != null && departmentCampus.getCampus().getEntityStatus().equals(EntityStatus.DELETED)) {
                return false;
            }
            if (staff.getCampus() != null) {
                Optional<Campus> campusOptional = staffModuleRoleRepository.findCampusById(staff.getCampus().getId());
                return campusOptional.isEmpty() || !campusOptional.get().getEntityStatus().equals(EntityStatus.DELETED);
            }
        }
        return true;
    }

    private void updateAccessToken(AccessToken token, String accessToken, Date expiredAt, UserType userType) {
        token.setAccessToken(accessToken);
        token.setUpdatedAt(DateTimeUtil.convertDateToTimeStampSecond(new Date()));
        token.setExpiredAt(DateTimeUtil.convertDateToTimeStampSecond(expiredAt));
        token.setUserType(userType);
        token.setRevokedAt(null);
        accessTokenRepository.save(token);
    }

    private AccessToken createAccessToken(Long userId, String accessToken, Date expiredAt, UserType userType) {
        AccessToken token = new AccessToken();
        token.setExpiredAt(DateTimeUtil.convertDateToTimeStampSecond(expiredAt));
        token.setCreatedAt(DateTimeUtil.convertDateToTimeStampSecond(new Date()));
        token.setAccessToken(accessToken);
        token.setUserType(userType);
        token.setUserId(userId);
        return token;
    }

    private String[] buildTokenResponse(String accessToken, UserType userType, Object user, boolean isCampusActive) {
        String userStatus = (user instanceof Student) ? ((Student) user).getEntityStatus().name() : ((Staff) user).getEntityStatus().name();
        String campusStatus = isCampusActive ? CommonStatus.ACTIVE.name() : CommonStatus.INACTIVE.name();
        return new String[]{accessToken, userType.name(), userStatus, campusStatus};
    }

    public String createToken(Long userId, String host, String userType) throws BadRequestException {

        Optional<Module> module = moduleAuthRepository.findByUrl(host);

        if (module.isEmpty()) throw new BadRequestException("Module not found");

        TokenSubjectResponse tokenSubjectResponse = null;
        if (userType.equals("CAN_BO")) {
            Optional<Staff> staffOptional = staffRepository.findById(userId);
            if (staffOptional.isEmpty()) throw new BadRequestException("User not found");
            Staff staff = staffOptional.get();
            tokenSubjectResponse = new TokenSubjectResponse();
            tokenSubjectResponse.setFullName(staff.getFullName());
            tokenSubjectResponse.setUserId(staff.getId());
            tokenSubjectResponse.setUserCode(staff.getStaffCode());
            tokenSubjectResponse.setPictureUrl(staff.getAvatar());
            tokenSubjectResponse.setEmail(staff.getAccountFPT());
            if (staff.getDepartmentCampus() == null) {
                if (staff.getCampus() == null) {
                    tokenSubjectResponse.setTrainingFacilityCode(null);
                    tokenSubjectResponse.setTrainingFacilityId(null);
                    tokenSubjectResponse.setTrainingFacilityName(null);
                } else {
                    Optional<Campus> campusOptional = staffModuleRoleRepository.findCampusById((staff).getCampus().getId());
                    if (campusOptional.isPresent()) {
                        tokenSubjectResponse.setTrainingFacilityCode(campusOptional.get().getCode());
                        tokenSubjectResponse.setTrainingFacilityId(campusOptional.get().getId());
                        tokenSubjectResponse.setTrainingFacilityName(campusOptional.get().getName());
                    } else {
                        tokenSubjectResponse.setTrainingFacilityCode(null);
                        tokenSubjectResponse.setTrainingFacilityId(null);
                        tokenSubjectResponse.setTrainingFacilityName(null);
                    }
                }
                tokenSubjectResponse.setSubjectCode(null);
                tokenSubjectResponse.setSubjectName(null);
            } else {
                tokenSubjectResponse.setTrainingFacilityCode(staff.getDepartmentCampus().getCampus().getCode());
                tokenSubjectResponse.setSubjectCode(staff.getDepartmentCampus().getDepartment().getCode());
                tokenSubjectResponse.setTrainingFacilityId(staff.getDepartmentCampus().getCampus().getId());
                tokenSubjectResponse.setTrainingFacilityName(staff.getDepartmentCampus().getCampus().getName());
                tokenSubjectResponse.setSubjectName(staff.getDepartmentCampus().getDepartment().getName());
            }
            tokenSubjectResponse.setUserType("CAN_BO");
            List<String> rolesCode = staffModuleRoleRepository.getListRoleCodeByUserIdAndModuleId(staff.getId(), module.get().getId());
            if (rolesCode.isEmpty()) {
                tokenSubjectResponse.setRolesCode(Collections.singletonList("GIAO_VIEN"));
                tokenSubjectResponse.setRolesName(Collections.singletonList("GIAO_VIEN"));
            } else {
                tokenSubjectResponse.setRolesCode(rolesCode);
                tokenSubjectResponse.setRolesName(staffModuleRoleRepository.getListRoleNameByUserId(staff.getId(), module.get().getId()));
            }
            tokenSubjectResponse.setHost(host);
        }

        if (userType.equals("SINH_VIEN")) {
            Optional<Student> studentOptional = studentRepository.findById(userId);
            if (studentOptional.isEmpty()) throw new BadRequestException("User not found");
            Student student = studentOptional.get();
            tokenSubjectResponse = new TokenSubjectResponse();
            tokenSubjectResponse.setFullName(student.getFullName());
            tokenSubjectResponse.setUserId(student.getId());
            tokenSubjectResponse.setUserCode(student.getStudentCode());
            tokenSubjectResponse.setRolesCode(Collections.singletonList("SINH_VIEN"));
            tokenSubjectResponse.setRolesName(Collections.singletonList("SINH_VIEN"));
            tokenSubjectResponse.setPictureUrl(student.getAvatar());
            tokenSubjectResponse.setHost(host);
            tokenSubjectResponse.setEmail(student.getEmailFpt());
            tokenSubjectResponse.setUserType("SINH_VIEN");
            if (student.getDepartmentCampus() == null) {
                tokenSubjectResponse.setTrainingFacilityCode("");
                tokenSubjectResponse.setSubjectCode("");
                tokenSubjectResponse.setTrainingFacilityId(null);
                tokenSubjectResponse.setTrainingFacilityName("");
                tokenSubjectResponse.setSubjectName("");
            } else {
                tokenSubjectResponse.setTrainingFacilityCode(student.getDepartmentCampus().getCampus().getCode());
                tokenSubjectResponse.setSubjectCode(student.getDepartmentCampus().getDepartment().getCode());
                tokenSubjectResponse.setTrainingFacilityId(student.getDepartmentCampus().getCampus().getId());
                tokenSubjectResponse.setTrainingFacilityName(student.getDepartmentCampus().getCampus().getName());
                tokenSubjectResponse.setSubjectName(student.getDepartmentCampus().getDepartment().getName());
            }
        }

        Date now = new Date();
        Date expiryDate = new Date(System.currentTimeMillis() + 2 * 60 * 60 * 1000);
        JwtBuilder builder = Jwts.builder();
        assert tokenSubjectResponse != null;
        Map<String, Object> claims = getBodyClaims(tokenSubjectResponse);
        for (Map.Entry<String, Object> entry : claims.entrySet()) {
            builder.claim(entry.getKey(), entry.getValue());
        }
        builder.setClaims(claims);
        builder.setIssuedAt(now);
        builder.setExpiration(expiryDate);
        builder.signWith(tokenSecret);
        String accessToken = builder.compact();
        Optional<AccessToken> accessTokenOptional = accessTokenRepository.findByUserId(userId, UserType.valueOf(userType));

        if (accessTokenOptional.isPresent()) {
            accessTokenOptional.get().setAccessToken(accessToken);
            accessTokenOptional.get().setUpdatedAt(DateTimeUtil.convertDateToTimeStampSecond(new Date()));
            accessTokenOptional.get().setExpiredAt(DateTimeUtil.convertDateToTimeStampSecond(expiryDate));
            accessTokenOptional.get().setUserType(UserType.valueOf(userType));
            accessTokenOptional.get().setRevokedAt(null);
            accessTokenRepository.save(accessTokenOptional.get());
            return accessToken;
        }
        AccessToken token = new AccessToken();
        token.setExpiredAt(DateTimeUtil.convertDateToTimeStampSecond(expiryDate));
        token.setCreatedAt(DateTimeUtil.convertDateToTimeStampSecond(new Date()));
        token.setAccessToken(accessToken);
        token.setUserType(UserType.valueOf(userType));
        token.setUserId(userId);
        AccessToken accessTokenSaved = accessTokenRepository.save(token);
        return accessTokenSaved.getAccessToken();
    }

    private static Map<String, Object> getBodyClaims(TokenSubjectResponse tokenSubjectResponse) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("fullName", tokenSubjectResponse.getFullName());
        claims.put("userId", tokenSubjectResponse.getUserId());
        claims.put("userName", tokenSubjectResponse.getUserCode());
        claims.put("userCode", tokenSubjectResponse.getUserCode());
        claims.put("email", tokenSubjectResponse.getEmail());
        claims.put("trainingFacilityCode", tokenSubjectResponse.getTrainingFacilityCode());
        claims.put("subjectCode", tokenSubjectResponse.getSubjectCode());
        claims.put("trainingFacilityId", tokenSubjectResponse.getTrainingFacilityId());
        claims.put("trainingFacilityName", tokenSubjectResponse.getTrainingFacilityName());
        claims.put("subjectName", tokenSubjectResponse.getSubjectName());
        claims.put("pictureUrl", tokenSubjectResponse.getPictureUrl());
        claims.put("userType", tokenSubjectResponse.getUserType());
        claims.put("moduleAvailableResponses", tokenSubjectResponse.getModuleAvailableResponses());
        List<String> rolesCode = tokenSubjectResponse.getRolesCode();
        List<String> rolesName = tokenSubjectResponse.getRolesName();
        if (rolesCode.size() == 1) {
            claims.put("rolesCode", rolesCode.get(0));
        } else {
            claims.put("rolesCode", rolesCode);
        }
        if (rolesName.size() == 1) {
            claims.put("rolesName", rolesName.get(0));
        } else {
            claims.put("rolesName", rolesName);
        }
        claims.put("host", tokenSubjectResponse.getHost());
        return claims;
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(tokenSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.get("userId").toString());
    }

    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(tokenSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("email").toString();
    }

    public String getHostFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(tokenSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        TokenSubjectResponse tokenSubjectResponse = null;
        try {
            tokenSubjectResponse = objectMapper.readValue(claims.getSubject(), TokenSubjectResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace(System.out
            );
        }
        assert tokenSubjectResponse != null;
        return tokenSubjectResponse.getHost();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(tokenSecret)
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }

    private Object getCurrentUserLogin(String email) {
        Optional<Staff> staffFPT = staffRepository.findByAccountFPT(email);
        if (staffFPT.isPresent()) return staffFPT.get();
        Optional<Staff> staffFE = staffRepository.findByAccountFE(email);
        if (staffFE.isPresent()) return staffFE.get();
        Optional<Student> studentFpt = studentRepository.findByEmailFpt(email);
        return studentFpt.orElse(null);
    }

    private Object[] createJwtToken(TokenSubjectResponse tokenSubjectResponse) {
        Date now = new Date();
        Date expiryDate = new Date(System.currentTimeMillis() + 2 * 60 * 60 * 1000);
        JwtBuilder builder = Jwts.builder();
        Map<String, Object> claims = getBodyClaims(tokenSubjectResponse);
        for (Map.Entry<String, Object> entry : claims.entrySet()) {
            builder.claim(entry.getKey(), entry.getValue());
        }
        builder.setClaims(claims);
        builder.setIssuedAt(now);
        builder.setExpiration(expiryDate);
        builder.signWith(tokenSecret);
        return new Object[]{
                builder.compact(),
                expiryDate
        };
    }

}
