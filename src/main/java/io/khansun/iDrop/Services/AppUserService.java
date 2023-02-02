package io.khansun.iDrop.Services;

import io.khansun.iDrop.Models.AppUser;
import io.khansun.iDrop.Models.UserRole;
import io.khansun.iDrop.Repos.AppUserRepo;
import io.khansun.iDrop.Repos.UserRoleRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Service @RequiredArgsConstructor @Transactional @Slf4j
public class AppUserService implements iAppUserService, UserDetailsService {
    private final AppUserRepo appUserRepo;
    private final UserRoleRepo userRoleRepo;
    private final PasswordEncoder passwordEncoder;

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepo.findByUsername(username);
        if(appUser == null){
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        }else{
            log.info("User found in the database: "+ username);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            appUser.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            });
            return new org.springframework.security.core.userdetails.User(appUser.getUsername(), passwordEncoder.encode(appUser.getPassword()), authorities);
        }
    }
}
