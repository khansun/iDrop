package io.khansun.iDrop.Controllers;

import io.khansun.iDrop.Models.AppUser;
import io.khansun.iDrop.Models.UserRole;
import io.khansun.iDrop.Services.AppUserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController @RequiredArgsConstructor @RequestMapping("/auth")
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
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(appUser);
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
//            log.info("Error", err.toString());

        }
        System.out.println(roleToAppUserForm.getUsername());
        System.out.println(roleToAppUserForm.getRole());
        appUserService.addAppUserRole(roleToAppUserForm.getUsername(), roleToAppUserForm.getRole());
        return ResponseEntity.ok().build() ;
    }

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