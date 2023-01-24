package io.khansun.iDrop.Controllers;

import io.khansun.iDrop.Models.AppUser;
import io.khansun.iDrop.Models.UserRole;
import io.khansun.iDrop.Services.AppUserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController @RequiredArgsConstructor @RequestMapping("/auth")
public class AppUserController {
    private final AppUserService appUserService;
    @GetMapping("/users")
    public ResponseEntity<List<AppUser>> getAppUsers(){
        return ResponseEntity.ok().body(appUserService.getAppUsers());
    }
    @PostMapping("user/save")
    public ResponseEntity<AppUser> saveAppUser(AppUser appUser){
        return ResponseEntity.ok().body(appUserService.saveAppUser(appUser));
    }
    @PostMapping("role/save")
    public ResponseEntity<UserRole> saveAppUser(UserRole userRole){
        return ResponseEntity.ok().body(appUserService.saveUserRole(userRole));
    }

    @PostMapping("role/addtouser")
    public ResponseEntity<?> addUserRole(AppUser appUser, UserRole userRole){
        String username = appUser.getUsername();
        appUserService.addAppUserRole(username,userRole.getName());
        return ResponseEntity.ok().build() ;
    }
}
@Data
class RoleToAppUserForm {
    private String username;
    private String role;
}