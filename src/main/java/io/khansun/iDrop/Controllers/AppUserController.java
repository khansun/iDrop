package io.khansun.iDrop.Controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.khansun.iDrop.Models.AppUser;
import io.khansun.iDrop.Models.UserRole;
import io.khansun.iDrop.Services.AppUserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController @RequiredArgsConstructor @RequestMapping("/auth") @Slf4j
public class AppUserController {
    private final AppUserService appUserService;
    @GetMapping("/users")
    public ResponseEntity<List<AppUser>> getAppUsers(){
        return ResponseEntity.ok().body(appUserService.getAppUsers());
    }
    @GetMapping("/roles")
    public ResponseEntity<List<UserRole>> getUserRoles(){
        return ResponseEntity.ok().body(appUserService.getUserRoles());
    }
    @PostMapping("user/save")
    public ResponseEntity<AppUser> saveAppUser(@RequestBody AppUser appUser){
        if (appUser.getUsername() == null || appUser.getUsername().isEmpty() || appUser.getPassword() == null || appUser.getPassword().isEmpty())
            return ResponseEntity.badRequest().body(appUser);
        else if(appUserService.getAppUser(appUser.getUsername()) != null)
            return ResponseEntity.status(FORBIDDEN).body(appUser);
        return ResponseEntity.ok().body(appUserService.saveAppUser(appUser));
    }
    @PostMapping("role/save")
    public ResponseEntity<UserRole> saveAppUser(@RequestBody UserRole userRole){
        return ResponseEntity.ok().body(appUserService.saveUserRole(userRole));
    }

    @PostMapping("role/addtouser")
    public ResponseEntity<?> addUserRole(@RequestBody String formData){
        System.out.println(formData);
        RoleToAppUserForm roleToAppUserForm = new RoleToAppUserForm();
        try {
            JSONObject jsonObject = new JSONObject(formData);
            String username = (String)jsonObject.get("username");
            roleToAppUserForm.setUsername(username);
            String role = (String)jsonObject.get("role");
            roleToAppUserForm.setRole(role);

        }catch (JSONException err){
            log.info("Error", err.toString());

        }
        System.out.println(roleToAppUserForm.getUsername());
        System.out.println(roleToAppUserForm.getRole());
        appUserService.addAppUserRole(roleToAppUserForm.getUsername(), roleToAppUserForm.getRole());
        return ResponseEntity.ok().build() ;
    }

    @GetMapping("/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader("Authorization");
        log.info("Authorization Header: " + authorizationHeader);

//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(token);
                String username = decodedJWT.getSubject();
                log.info("Username: " + username);
                AppUser appUser = appUserService.getAppUser(username);

                String accessToken = JWT.create()
                        .withSubject(appUser.getUsername())
                        .withExpiresAt(new java.util.Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", appUser.getRoles().stream().map(UserRole::getName).collect(Collectors.toList()))
                        .sign(algorithm);


                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                String jsonRes = "{ \"status\": 200,"+ "\"access_token\": \""+accessToken + "\" ,"
                        +  "\"refresh_token\": \""+token + "\" }";
                response.getWriter().write(jsonRes);

            }
            catch (Exception e) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                String jsonRes = "{ \"status\": \"403\"," +  "\"error\": \""+ e.getMessage()+ "\" }";
                response.getWriter().write(jsonRes);
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
        }
//        else {
//            throw new RuntimeException("Refresh token is missing");
//        }
//    }
}
@Data
class RoleToAppUserForm {
    private String username;
    private String role;



    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}