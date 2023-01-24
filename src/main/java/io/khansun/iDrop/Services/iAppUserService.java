package io.khansun.iDrop.Services;

import io.khansun.iDrop.Models.AppUser;
import io.khansun.iDrop.Models.UserRole;

import java.util.List;

public interface iAppUserService {
    AppUser saveAppUser(AppUser appUser);
    UserRole saveUserRole(UserRole userRole);
    void addAppUserRole(String username, String role);
    AppUser getAppUser(String username);
    List<AppUser> getAppUsers();
}
