package io.khansun.iDrop.Services;

import io.khansun.iDrop.Models.AppUser;
import io.khansun.iDrop.Models.UserRole;
import io.khansun.iDrop.Repos.AppUserRepo;
import io.khansun.iDrop.Repos.UserRoleRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service @RequiredArgsConstructor @Transactional @Slf4j
public class AppUserService implements iAppUserService{
    private final AppUserRepo appUserRepo;
    private final UserRoleRepo userRoleRepo;


    @Override
    public AppUser saveAppUser(AppUser appUser) {
        log.info("Saving AppUser: "+ appUser.toString());
        return appUserRepo.save(appUser);
    }

    @Override
    public UserRole saveUserRole(UserRole userRole) {
        log.info("Saving AppUserRole: "+ userRole.toString());
        return userRoleRepo.save(userRole);
    }

    @Override
    public void addAppUserRole(String username, String role) {
        AppUser appUser = appUserRepo.findByUsername(username);
        UserRole userRole = userRoleRepo.findByName(role);
        appUser.getRoles().add(userRole);
        //TO-DO: username and role Validation
    }

    @Override
    public AppUser getAppUser(String username) {
        return appUserRepo.findByUsername(username);
    }

    @Override
    public List<AppUser> getAppUsers() {
        return appUserRepo.findAll();
    }
}
