package io.khansun.iDrop.Repos;

import io.khansun.iDrop.Models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepo extends JpaRepository <AppUser, Long> {
    AppUser findByUsername(String username);

}
